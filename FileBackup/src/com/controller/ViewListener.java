package com.controller;

import java.io.IOException;
import java.nio.file.InvalidPathException;


/**
 * @author Kasun Amarasena
 *
 */
public interface ViewListener {	
	
	/**
	 * Task of coping the files
	 * @return true if all the files are copied successfully, false otherwise
	 */
	public boolean copyButtonClicked(ViewEvent evt) throws IOException, InvalidPathException;	
	
	/** 
	 * Load the default source dirs/files 
	 */
	public void defaultSourcesMenuItemClicked(ViewEvent evt) throws IOException,InvalidPathException ;
	
	/** 
	 * Load the default destinations dirs/files 
	 */
	public void defaultDestinationsMenuItemClicked(ViewEvent evt)  throws IOException, InvalidPathException;
}
