/*
 * @(#)ButtonTest.java  1.0  
 *
 * Copyright (c) 2004 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package test;

import ch.randelshofer.quaqua.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
/**
 * ButtonTest.
 *
 * @author  Werner Randelshofer
 * @version $Id$
 */
public class SpecialButtonTest extends javax.swing.JPanel {
    
    /** Creates new form. */
    public SpecialButtonTest() {
        initComponents();
        placardButton.putClientProperty("JButton.buttonType", "toolbar");
        smallPlacardButton.putClientProperty("JButton.buttonType", "toolbar");
        tableHeaderButton.putClientProperty("JButton.buttonType", "toolbar");

        // Quaqua client properties
        placardButton.putClientProperty("Quaqua.Button.style", "placard");
        smallPlacardButton.putClientProperty("Quaqua.Button.style","placard");
        colorWellButton.putClientProperty("Quaqua.Button.style","colorWell");
        colorWellButton.setBackground(Color.white);
        smallColorWellButton.putClientProperty("Quaqua.Button.style","colorWell");
        smallColorWellButton.setBackground(Color.white);
        tableHeaderButton.putClientProperty("Quaqua.Button.style", "tableHeader");
        
        // Apple AquaLookAndFeel client properties
        if (QuaquaManager.getDesign() >= QuaquaManager.LEOPARD) {
            placardButton.putClientProperty("JButton.buttonType", "gradient");
            smallPlacardButton.putClientProperty("JButton.buttonType", "gradient");
            smallPlacardButton.putClientProperty("JComponent.sizeVariant","small");
            smallColorWellButton.putClientProperty("JComponent.sizeVariant","small");
        }
    }
    /*
    public void paint(Graphics g) {
        long start = System.currentTimeMillis();
        super.paint(g);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }*/
    
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(QuaquaManager.getLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame f = new JFrame("Quaqua Special Button Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new SpecialButtonTest());
        ((JComponent) f.getContentPane()).setBorder(new EmptyBorder(9,17,17,17));
        f.pack();
        f.setVisible(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        placardButton = new javax.swing.JButton();
        placardLabel = new javax.swing.JLabel();
        colorWellButton = new javax.swing.JButton();
        colorWellLabel = new javax.swing.JLabel();
        tableHeaderButton = new javax.swing.JToggleButton();
        tableHeaderLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        smallPlacardButton = new javax.swing.JButton();
        smallLabel = new javax.swing.JLabel();
        smallColorWellButton = new javax.swing.JButton();
        springPanel = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 17, 17, 17));
        setLayout(new java.awt.GridBagLayout());

        placardButton.setText("Ångström H");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(placardButton, gridBagConstraints);

        placardLabel.setText("Placard Style");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        add(placardLabel, gridBagConstraints);

        colorWellButton.setText("Ångström H");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(colorWellButton, gridBagConstraints);

        colorWellLabel.setText("Color Well Style");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        add(colorWellLabel, gridBagConstraints);

        tableHeaderButton.setFont(new java.awt.Font("Lucida Grande", 0, 11));
        tableHeaderButton.setText("Ångström H");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(tableHeaderButton, gridBagConstraints);

        tableHeaderLabel.setText("Table Header Style");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        add(tableHeaderLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 12, 0);
        add(jSeparator2, gridBagConstraints);

        smallPlacardButton.setFont(new java.awt.Font("Lucida Grande", 0, 11));
        smallPlacardButton.setText("Ångström H");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(smallPlacardButton, gridBagConstraints);

        smallLabel.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        smallLabel.setText("Small Size");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        add(smallLabel, gridBagConstraints);

        smallColorWellButton.setFont(new java.awt.Font("Lucida Grande", 0, 11));
        smallColorWellButton.setText("Ångström H");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        add(smallColorWellButton, gridBagConstraints);

        springPanel.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(springPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton colorWellButton;
    private javax.swing.JLabel colorWellLabel;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton placardButton;
    private javax.swing.JLabel placardLabel;
    private javax.swing.JButton smallColorWellButton;
    private javax.swing.JLabel smallLabel;
    private javax.swing.JButton smallPlacardButton;
    private javax.swing.JPanel springPanel;
    private javax.swing.JToggleButton tableHeaderButton;
    private javax.swing.JLabel tableHeaderLabel;
    // End of variables declaration//GEN-END:variables
    
}
