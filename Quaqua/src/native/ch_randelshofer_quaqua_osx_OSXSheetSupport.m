#import "ch_randelshofer_quaqua_osx_OSXSheetSupport.h"
#import <Cocoa/Cocoa.h>
// #import <JavaNativeFoundation/JavaNativeFoundation.h> // not supported in Tiger
#import <JavaVM/jawt_md.h>

#pragma mark Header part
// This part would normally go in a header, but the standard header for this file is automatically generated.
NSWindow * GetWindowFromComponent(jobject parent, JNIEnv *env);
jint GetJNIEnv(JNIEnv **env, bool *mustDetach);

@interface SheetSupport : NSObject
{
    NSWindow *sheetWindow;
    NSWindow *parentWindow;
}

- (id)initWithSheet:(jobject)s onWindow:(jobject)p jniEnv:(JNIEnv *)env;
- (void)showSheet;
// Callback support removed - not needed
//- (void)sheetDidEnd:(NSWindow *)sheet returnCode:(int)returnCode contextInfo:(void *)contextInfo;

@end

#pragma mark Implementation part
static JavaVM *jvm;
static jclass sheetSupportClass = nil;
// Callback support removed - not needed
// static jmethodID fireFinishedMethodID = nil;

/*
 Called when the lib is loaded.
*/
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
	jvm = vm;

	return JNI_VERSION_1_4;
}

/*
 * Return the code version for this native source. It is needed to check the support of a specific JNI library.
 * Class and method IDs are initialized here; we can be sure that this method is called.
 * Class:     ch_randelshofer_quaqua_osx_OSXSheetSupport
 * Method:    nativeGetNativeCodeVersion
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_ch_randelshofer_quaqua_osx_OSXSheetSupport_nativeGetNativeCodeVersion (JNIEnv *env, jclass clazz) {
    sheetSupportClass = clazz;
    // Callback support removed - not needed
    // fireFinishedMethodID = (*env)->GetStaticMethodID(env, clazz, "fireSheetFinished", "(Lch/randelshofer/quaqua/JSheet;)V");
    return 0;
}

/*
 * Show a given Java window as a Cocoa sheet.
 *
 * Class:     ch_randelshofer_quaqua_osx_OSXSheetSupport
 * Method:    nativeShowSheet
 * Signature: (Lch/randelshofer/quaqua/JSheet;Ljava/awt/Window;)V
 */
JNIEXPORT void JNICALL Java_ch_randelshofer_quaqua_osx_OSXSheetSupport_nativeShowSheet (JNIEnv *env, jclass clazz, jobject sheet, jobject parent) {
    SheetSupport *sheetSupport = [[SheetSupport alloc] initWithSheet:sheet onWindow:parent jniEnv:env];
    [sheetSupport performSelectorOnMainThread:@selector(showSheet) withObject:nil waitUntilDone:NO];
}

/*
 * Hide a given Java sheet.
 *
 * Class:     ch_randelshofer_quaqua_osx_OSXSheetSupport
 * Method:    nativeHideSheet
 * Signature: (Lch/randelshofer/quaqua/JSheet;)V
 */
JNIEXPORT void JNICALL Java_ch_randelshofer_quaqua_osx_OSXSheetSupport_nativeHideSheet (JNIEnv *env, jclass clazz, jobject sheet) {
    [NSApp performSelectorOnMainThread:@selector(endSheet:) withObject:GetWindowFromComponent(sheet, env) waitUntilDone:NO];
}

@implementation SheetSupport

- (id)initWithSheet:(jobject)s onWindow:(jobject)p jniEnv:(JNIEnv *)env
{
    [super init];

    sheetWindow = GetWindowFromComponent(s, env);
    parentWindow = GetWindowFromComponent(p, env);

    return self;
}

- (void)showSheet
{
    [NSApp beginSheet:sheetWindow
       modalForWindow:parentWindow
        modalDelegate:nil // self
       didEndSelector:nil // @selector(sheetDidEnd:returnCode:contextInfo:)
          contextInfo:NULL];
    // release in diese Methode verschieben, da Java_ch_randelshofer_quaqua_osx_OSXSheetSupport_nativeShowSheet vielleicht schon beendet ist
    [self release];
}

// Callback support removed - not needed
/*
 - (void) sheetDidEnd:(NSWindow *)window returnCode:(int)returnCode contextInfo:(void *)contextInfo {
    JNIEnv *env = NULL;
	bool shouldDetach = false;

	// Find out if we actually need to attach the current thread to obtain a JNIEnv,
	// or if one is already in place
	// This will determine whether DetachCurrentThread should be called later
	if (GetJNIEnv(&env, &shouldDetach) != JNI_OK) {
		NSLog(@"sheetDidEnd: could not attach to JVM");
		return;
	}

	// If we have file results, translate them to Java strings and tell the Java JSheetDelegate
	// class to notify our listener
    if (fireFinishedMethodID != NULL) {
		// Callback; Java will invoke fireSheetFinished on Java sheet support
        // That's where it hang:
		(*env)->CallStaticVoidMethod(env, sheetSupportClass, fireFinishedMethodID, sheet);
	}

	// We're done with the sheet and its owner; release the global refs
	(*env)->DeleteGlobalRef(env, sheet);
	(*env)->DeleteGlobalRef(env, parent);

	// IMPORTANT: if GetJNIEnv attached for us, we need to detach when done
	if (shouldDetach) {
        (*jvm)->DetachCurrentThread(jvm);
	}
	// This delegate was was retained in nativeShowSheet; since this callback occurs on the AppKit thread,
	// which always has a pool in place, it can be autoreleased rather than released
	[self autorelease];
}
*/

@end

/*
 Determines whether the current thread is already attached to the VM,
 and tells the caller if it needs to later DetachCurrentThread

 CALL THIS ONCE WITHIN A FUNCTION SCOPE and use a local boolean
 for mustDetach; if you do not, the first call might attach, setting
 mustDetach to true, but the second will misleadingly set mustDetach
 to false, leaving a dangling JNIEnv
*/
jint GetJNIEnv(JNIEnv **env, bool *mustDetach) {
	jint getEnvErr = JNI_OK;
	*mustDetach = false;
	if (jvm) {
		getEnvErr = (*jvm)->GetEnv(jvm, (void **)env, JNI_VERSION_1_4);
		if (getEnvErr == JNI_EDETACHED) {
			getEnvErr = (*jvm)->AttachCurrentThread(jvm, (void **)env, NULL);
			if (getEnvErr == JNI_OK) {
				*mustDetach = true;
			}
		}
	}
	return getEnvErr;
}

// Given a Java component, return a NSWindow*
NSWindow * GetWindowFromComponent(jobject parent, JNIEnv *env) {
	JAWT awt;
	JAWT_DrawingSurface* ds;
	JAWT_DrawingSurfaceInfo* dsi;
	JAWT_MacOSXDrawingSurfaceInfo* dsi_mac;
	jboolean result;
	jint lock;

    /*
        JAWT_GetAWT is deprecated in JDK 7. This class should not be used in JDK 7.
    */

	// Get the AWT
	awt.version = JAWT_VERSION_1_4;
	result = JAWT_GetAWT(env, &awt);
	assert(result != JNI_FALSE);

	// Get the drawing surface
	ds = awt.GetDrawingSurface(env, parent);
	assert(ds != NULL);

	// Lock the drawing surface
	lock = ds->Lock(ds);
	assert((lock & JAWT_LOCK_ERROR) == 0);

	// Get the drawing surface info
	dsi = ds->GetDrawingSurfaceInfo(ds);

	// Get the platform-specific drawing info
	dsi_mac = (JAWT_MacOSXDrawingSurfaceInfo*)dsi->platformInfo;

	// Get the NSView corresponding to the component that was passed
	NSView *view = dsi_mac->cocoaViewRef;

	// Free the drawing surface info
	ds->FreeDrawingSurfaceInfo(dsi);
	// Unlock the drawing surface
	ds->Unlock(ds);

	// Free the drawing surface
	awt.FreeDrawingSurface(ds);

	// Get the view's parent window; this is what we need to show a sheet
	return [view window];
}
