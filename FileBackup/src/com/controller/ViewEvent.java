/**
 * 
 */
package com.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * ViewEvent bean class
 * 
 * @author Kasun Amarasena
 *
 */
public class ViewEvent {

	private String destinationDir;
	private List<String> sourceDirs;

	/**
	 * 
	 */
	public ViewEvent() {
	}

	/**
	 * @param sourceDir
	 *            Source directory
	 * @param destinationDirs
	 *            List of destination directories
	 */
	public ViewEvent(String sourceDir, List<String> destinationDirs) {
		this.destinationDir = sourceDir;
		this.sourceDirs = destinationDirs;
	}

	/**
	 * @return Destination directory
	 */
	public String getDestinationDir() {
		return destinationDir;
	}

	/**	 
	 * @param Destination directory
	 * @throws InvalidPathException
	 */
	public void setDestinationDir(String destinationDir)
			throws InvalidPathException {
		boolean exists = Files.exists(new File(destinationDir).toPath());
		if (exists) {
			this.destinationDir = destinationDir;
		}else{
			throw new InvalidPathException(destinationDir,"Invalied dir/file name");
		}
	}

	/**
	 * 
	 * @return List of directories/files to be copied
	 */
	public List<String> getSourceDirs() {
		return sourceDirs;
	}

	/**
	 * Add the directory to the list of directories to be copied
	 * 
	 * @param dir
	 *            Destination directory
	 * @throws InvalidPathException
	 */
	public void addSourceDir(String dir) throws InvalidPathException {
		if (sourceDirs == null)
			sourceDirs = new ArrayList<String>();

		boolean exists = Files.exists(new File(dir).toPath());
		if (exists) {
			sourceDirs.add(dir);			
		}else{
			throw new InvalidPathException(destinationDir,"Invalid dir/file name");
		}
	}

	/**
	 * Remove directory from the list of directories/files to be copied
	 * 
	 * @param Destination
	 *            directory
	 */
	public void removeSourceDir(String dir) {
		sourceDirs.remove(dir);
	}
	
	/**
	 * Remove all the directories/files to be copied	
	 */
	public void removeSourceDirAll() {
		sourceDirs.clear();
	}

	/**
	 * 
	 * @param dir
	 *            Directory/File
	 * @return true if dir exists in the list of source directories, false
	 *         otherwise
	 */
	public boolean containsDir(String dir) {
		if (sourceDirs == null) {
			return false;
		} else {
			return sourceDirs.contains(dir);
		}
	}

	/**
	 * Get size of the Destination directory
	 */
	public long sizeOfDestination() {
		return FileUtils.sizeOfDirectory(new File(destinationDir));
	}

	/**
	 * Get sum of the size of the source directories/files
	 */
	public long sizeOfSources() {
		long size = 0;
		for (String dir : sourceDirs) {
			size += FileUtils.sizeOfDirectory(new File(dir));
		}
		return size;
	}

	/**
	 * @param List
	 *            of source dirs/files
	 */
	public void setSourceDirs(List<String> lines) throws InvalidPathException{	
		for (String dir : lines) {
			boolean b =Files.exists(new File(dir).toPath());
			if(!b){
				new InvalidPathException(dir, "Invalid dir/file name!");
			}
		}
		this.sourceDirs = lines;		
	}

}
