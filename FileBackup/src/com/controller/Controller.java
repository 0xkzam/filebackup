package com.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.model.Model;
import com.view.View;

/**
 *
 * @author Kasun Amarasena
 */
public class Controller implements ViewListener {

	@SuppressWarnings("unused")
	private View view;

	@SuppressWarnings("unused")
	private Model model;

	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
	}

	/**
	 * To Do: Handle exception case
	 * 		  Solve the return type issue,
	 * 		  A way to figure out failed files.
	 * 
	 * @return true if all the files are copied successfully, false otherwise
	 */

	@Override
	public boolean copyButtonClicked(ViewEvent evt) throws IOException{

		final File dest = new File(evt.getDestinationDir());
		List<String> sourceDirs = evt.getSourceDirs();
		//final boolean result;

		for (final String dir : sourceDirs) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						FileUtils.copyDirectoryToDirectory(new File(dir), dest);
					} catch (IOException e) {
						//result = false;
					}
				}
			}).start();
		}
		//return result;
		return true;
	}

	/**
	 * Load the default source dirs/files
	 * 
	 * @return true list of all the dirs are valid, false otherwise
	 */
	@Override
	public void defaultSourcesMenuItemClicked(ViewEvent evt)
			throws IOException, InvalidPathException {
		Path path = Paths.get("log\\default").toAbsolutePath();
		List<String> lines = FileUtils.readLines(path.toFile());
		evt.setSourceDirs(lines);
	}

}
