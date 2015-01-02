/**
 * 
 */
package com.controller;

import java.awt.Toolkit;
import javax.swing.SwingWorker;

/**
 * @author Kasun Amarasena
 *
 */
public abstract class FileCopyTask extends SwingWorker<Void, Void>{

	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	abstract protected Void doInBackground() throws Exception;	
	
	/*
     * Executed in event dispatching thread
     */
    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();  
    }
    
    //TO DO
}
