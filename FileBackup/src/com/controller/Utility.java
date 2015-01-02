package com.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;

public class Utility {
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

	public static File getTempDir() {
		return FileUtils.getTempDirectory();
	}
}
