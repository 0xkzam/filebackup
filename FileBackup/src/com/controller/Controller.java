package com.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
	 * Task of coping the files		
	 */
	@Override
	public void copyButtonClicked(ViewEvent evt){

		final File dest = new File(evt.getDestinationDir());
		List<String> sourceDirs = evt.getSourceDirs();
		final List<String> failedDirs = new ArrayList<>();				
		

		for (final String dir : sourceDirs) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Utils.copy(new File(dir), dest);
					} catch (IOException e) {
						failedDirs.add(dir);
					}
				}
			}).start();
		}		
		
		if(failedDirs.isEmpty()){
			evt.removeSourceDirAll();
		}else{
			for (String dir: failedDirs) {
				evt.removeSourceDir(dir);
			}
		}		
	}

	/**
	 * Load the default source dirs/files
	 * 	
	 */
	@Override
	public void defaultSourcesMenuItemClicked(ViewEvent evt)
			throws IOException, InvalidPathException {
		Path path = Paths.get("log\\default_sources").toAbsolutePath();
		List<String> lines = Utils.readFile(path);
		evt.setSourceDirs(lines);
	}
	
	/**
	 * Load the default destination dirs/files
	 * 	 
	 */
	@Override
	public void defaultDestinationsMenuItemClicked(ViewEvent evt)
			throws IOException, InvalidPathException {
		Path path = Paths.get("log\\default_destinations").toAbsolutePath();
		List<String> lines = Utils.readFile(path);
		evt.setDestinationDirs(lines);
	}

}
