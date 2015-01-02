
package com.view;

import java.awt.Font;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * About panel
 * 
 * @author Kasun Amarasena
 */
public class AboutPanel {

    public AboutPanel() {
       
    }

   public void show(JFrame frame) {
        // for copying style
        JLabel label = new JLabel();
        Font font = label.getFont();

        // create some css from the label's font
        StringBuffer style = new StringBuffer("font-family:" + font.getFamily() + ";");
        style.append("font-weight:normal"+";");
        style.append("font-size:" + font.getSize() + "pt;");

        // html content
        JEditorPane ep = new JEditorPane("text/html", "<html><body style=\"" + style + "\">" //
                + "FileCopy 1.0v<br/>"
                + "Author: Kasun Amarasena<br/>"
                + "Email: kasunAmarasena@gmail.com<br/>"
                + "Blog: <a href=\"http://csviews.blogspot.com/\">http://csviews.blogspot.com/</a><br/>"
                + "Developed using JDK 1.7.0_45" //
                + "</body></html>");

        // handle link events
        ep.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                    java.net.URI uri;
                    java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                    try {
                        uri = new java.net.URI( "http://csviews.blogspot.com/" );                        
                        desktop.browse( uri );
                        
                    } catch (URISyntaxException ex) {
                        System.err.println("java.net.URI: "+ex);
                    } catch (IOException ex) {
                        System.err.println("deskop.browse(): "+ex);
                    }                 
                }
            }     
        });
        
        ep.setEditable(false);
        ep.setBackground(label.getBackground());

        // show joptionpane
        JOptionPane.showMessageDialog(frame, ep);
        
    }  
}
