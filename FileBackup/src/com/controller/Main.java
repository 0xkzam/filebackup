/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.controller;

import com.view.View;

/**
 *
 * @author Kasun Amarasena
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    	new Main().runApp();
      
    }
    
    
    /**
     * Initialize the View & Controller
     */
    public void init(){    	
    	View view = new View();
        Controller controller = new Controller(view, null);
        
        view.setViewListener(controller);
        view.setVisible(true);
           	
    }
    
    /** 
     * Set the Windows look and feel     
     * If Windows is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    public void runApp(){        
    	
    	//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    	  
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {           
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	init();
            }
        });    	
    	
    } 
    
}
