package com.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * This class contains static methods of utility functions and acts as 
 * a bridge between external utility libraries such as Apache Commons IO
 * 
 * 
 * @author Kasun Amarasena
 *
 */
public class Utils {
	
	/**
	 * Copies the whole source directory or file inside the destination folder
	 * 
	 * @param src Directory/File to be copied
	 * @param dest Destination directory
	 * @throws IOException
	 */
	public static void copy(File src, File dest) throws IOException {
		FileUtils.copyDirectoryToDirectory(src, dest);
	}

	/**
	 * Validate the specified directory or file name
	 * 
	 * @param file File
	 * @return true if the dir/file exists, false otherwise
	 */
	public static boolean fileExists(File file) {
		return Files.exists(file.toPath());
	}

	/**
	 * @return Temp directory used by the system
	 */
	public static File getTempDir() {
		return FileUtils.getTempDirectory();
	}
	
	/**
	 * 
	 * @param file
	 * @return size of the file or directory specified.
	 */
	public static long fileSize(File file){
		return FileUtils.sizeOfDirectory(file);		
	}
	
	/**
	 * 
	 * @param path
	 * @return List<String> of lines read from a file specified by the Path
	 * @throws IOException
	 */
	public static List<String> readFile(Path path) throws IOException{
		return FileUtils.readLines(path.toFile());		
	}
}
