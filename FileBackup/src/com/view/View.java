package com.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import com.controller.ViewEvent;
import com.controller.ViewListener;

/**
 * Main GUI class
 *
 * @author Kasun Amarasena
 */
public class View extends javax.swing.JFrame {

	/**
	 * 
	 */
	private String title = "FileBackup";
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
	private ViewListener viewListener;
	private ViewEvent viewEvent;

	/**
	 * Creates new form FileCopy
	 */
	public View() {
		initComponents();
		initEvents();
		this.setLocationRelativeTo(null);

	}

	/**
	 * Adding Listeners to swing components
	 */
	private void initEvents() {
		closeButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				closeButtonActionPerformed(e);
			}
		});

		removeButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeButtonActionPerformed(e);
			}
		});

		addButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addButtonActionPerformed(e);
			}
		});

		copyButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				copyButtonActionPerformed(e);
			}
		});

		aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aboutMenuItemActionPerformed(e);
			}
		});

		defaultSourcesMenuItem
				.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						defaultSourcesMenuItemActionPerformed(e);
					}
				});
		
		defaultDestinationsMenuItem
		.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultSDestinationsMenuItemActionPerformed(e);
			}
		});

		progressBar.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent e) {
				progressBarPropertyChanged(e);

			}
		});

	}

	/**	 
	 * Load default destination dirs
	 * 
	 * NOTE: Since this is displayed in a text box only one destination or the
	 * first destination of the returned list of destination dirs can be used.
	 * Therefore the main GUI must be updated to view more than one destination dir.
	 */
	protected void defaultSDestinationsMenuItemActionPerformed(ActionEvent e) {
		if (viewEvent == null) {
			viewEvent = new ViewEvent();
		}
		try {
			viewListener.defaultDestinationsMenuItemClicked(viewEvent);			
			List<String> dirs = viewEvent.getDestinationDirs();		
			
			// Only the first dir of the list is used.
			if(!dirs.isEmpty()){
				textFieldDestination.setText(dirs.get(0));
			}			
		}catch (InvalidPathException e1) {
			clearTable();			
			JOptionPane.showMessageDialog(this,
					"There is an invalid directory/file in the default list!", "Error!",
					JOptionPane.ERROR_MESSAGE);
		}catch (IOException e1) {
			clearTable();			
			JOptionPane.showMessageDialog(this,
					"Error loading default data!", "Error!",
					JOptionPane.ERROR_MESSAGE);
		}		
		
	}

	/**
	 * Load default sources
	 */
	protected void defaultSourcesMenuItemActionPerformed(ActionEvent e) {
		if (viewEvent == null) {
			viewEvent = new ViewEvent();
		}
		
		try {
			viewListener.defaultSourcesMenuItemClicked(viewEvent);
			clearTable();
			List<String> dirs = viewEvent.getSourceDirs();
			if(dirs.size() > 0){ removeButton.setEnabled(true);}
			for (String ob:dirs) {
				Object[] temp = {ob};
				tableModel.addRow(temp);
			}				
		}catch (InvalidPathException e1) {
			clearTable();			
			JOptionPane.showMessageDialog(this,
					"There is an invalid directory/file in the default list!", "Error!",
					JOptionPane.ERROR_MESSAGE);
		}catch (IOException e1) {
			clearTable();			
			JOptionPane.showMessageDialog(this,
					"Error loading default data!", "Error!",
					JOptionPane.ERROR_MESSAGE);
		}		
	}
	

	/**
	 * Progress Bar update
	 */
	private void progressBarPropertyChanged(PropertyChangeEvent e) {
		if ("progress" == e.getPropertyName()) {
			progressBar.setValue((Integer)e.getNewValue());			
		}
	}

	/**
	 * Copy Button task
	 */
	private void copyButtonActionPerformed(ActionEvent e) {
				
		//Validate destination dir
		String dest = textFieldDestination.getText();
		if (dest.equals("")) {
			JOptionPane.showMessageDialog(this,
					"Enter destination directory & continue...", "Error!",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		try {
			viewEvent.setDestinationDir(dest);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this,
					"Please enter a valid directory!", "Error!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		copyButton.setEnabled(false);	
		
		//Start progress bar		
		SwingWorker<Void,Void> task = new SwingWorker<Void,Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				progressBar.setValue(progressBar.getMinimum());
				progressBar.setStringPainted(true);
				
				long sizeDest = 0;
				long sizeSrc = viewEvent.sizeOfSources();
				double sizeSrcMB = (double)Math.round(100*sizeSrc/(1024.0*1024.0))/100;
				
				
				while (sizeDest  != sizeSrc) {
	                sizeDest = viewEvent.sizeOfDestination();
	                double ratio= (100.0*sizeDest)/sizeSrc;	    
	                double sizeDestTemp = ((double)Math.round(100.0*sizeDest/(1024.0*1024.0))/100);
	                
	                setTitle(title+": "+sizeDestTemp+"MB/"+sizeSrcMB+"MB");
	                
	                setProgress((int)ratio);       
	                try { 
	                    Thread.sleep(500);
	                } catch (Exception ignore) {
	                }
	            }
	            setProgress(100);				
				return null;
			}			
		
			@Override
		    public void done() {
		        Toolkit.getDefaultToolkit().beep();  
		        progressBar.setValue(progressBar.getMinimum());
		        progressBar.setStringPainted(false);
		        clearTable();
		        copyButton.setEnabled(true);
		        setTitle(title+": Completed");		        
		    }
			
		};
		
		PropertyChangeListener propertyChangeListener = getPropertyChangeListener();       
        task.addPropertyChangeListener(propertyChangeListener);
        task.execute();		
		
        
		// Start copying process 
		new SwingWorker<Void,Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				boolean result = viewListener.copyButtonClicked(viewEvent);		
				if(result){
					viewEvent.removeSourceDirAll();
				}else{
					System.err.println("Error: "+viewEvent.getSourceDirs().toString());
				}
				return null;
			}}.execute();		

	}

	
	/**
	 * @return PropertyChangeListener associated with the progress bar
	 */
	private PropertyChangeListener getPropertyChangeListener() {
		PropertyChangeListener propertyChangeListener = null;

        for (PropertyChangeListener p : progressBar.getPropertyChangeListeners()) {
            if (p.toString().contains("com.view")) {
                propertyChangeListener = p;
                break;
            }
        }
		return propertyChangeListener;
	}
	

	/**
	 * Close Button task
	 */
	private void closeButtonActionPerformed(ActionEvent e) {
		System.exit(0);
	}

	/**
	 * Remove button task
	 */
	private void removeButtonActionPerformed(ActionEvent e) {
		int rowCount = tableModel.getRowCount();
		int selectedRow = table.getSelectedRow();
		if (rowCount > 0 && selectedRow > -1) {
			String dir = tableModel.getValueAt(selectedRow, 0).toString();
			viewEvent.removeSourceDir(dir);
			tableModel.removeRow(selectedRow);
			if (rowCount == 1) {
				removeButton.setEnabled(false);
			}
		}
	}

	/**
	 * Add button task
	 */
	private void addButtonActionPerformed(ActionEvent e) {
		if (viewEvent == null) {
			viewEvent = new ViewEvent();
		}
		String dir = JOptionPane.showInputDialog(this,
				"Enter destination directory:", "Enter destination directory:",
				JOptionPane.INFORMATION_MESSAGE);

		if (dir != null && !dir.equals("") && !viewEvent.containsDir(dir)) {
			boolean successful;
			try {				
				viewEvent.addSourceDir(dir);
				successful = true;
			} catch (InvalidPathException exception) {
				successful = false;
			}

			if (successful) {
				String[] temp = { dir };
				tableModel.addRow(temp);
				removeButton.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(this,
						"Please enter a valid directory!", "Error!",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * AboutMenuItem task
	 */
	private void aboutMenuItemActionPerformed(ActionEvent e) {
		AboutPanel aboutPanel = new AboutPanel();
		aboutPanel.show(this);
	}

	public void setViewListener(ViewListener listener) {
		this.viewListener = listener;
	}	
	
	private void clearTable(){
		int rowCount = table.getRowCount();
		for(int i=0;i<rowCount;i++){
			tableModel.removeRow(0);
		}		
		removeButton.setEnabled(false);
	}
	
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		textFieldDestination = new javax.swing.JTextField();
		copyButton = new javax.swing.JButton();
		closeButton = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		addButton = new javax.swing.JButton();
		removeButton = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		table = new javax.swing.JTable();
		progressBar = new javax.swing.JProgressBar();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		defaultSourcesMenuItem = new javax.swing.JMenuItem();
		defaultDestinationsMenuItem = new javax.swing.JMenuItem();
		aboutMenuItem = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("FileBackup");
		setResizable(false);

		textFieldDestination.setCursor(new java.awt.Cursor(
				java.awt.Cursor.TEXT_CURSOR));

		copyButton.setText("Copy");

		closeButton.setText("Close");

		jLabel1.setText("Destination Directory:");

		jLabel2.setText("Source Directories/ Files:");

		addButton.setMaximumSize(new java.awt.Dimension(34, 34));
		addButton.setMinimumSize(new java.awt.Dimension(34, 34));
		addButton.setPreferredSize(new java.awt.Dimension(34, 34));

		removeButton.setMaximumSize(new java.awt.Dimension(34, 34));
		removeButton.setMinimumSize(new java.awt.Dimension(34, 34));
		removeButton.setPreferredSize(new java.awt.Dimension(34, 34));
		removeButton.setEnabled(false);

		Path path = Paths.get("images").toAbsolutePath();
		addButton.setIcon(new ImageIcon(path.toString() + "\\add.png"));
		removeButton.setIcon(new ImageIcon(path.toString()
				+ "\\remove.png"));

		// Jtable setup
		table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class<?>[] types = new Class<?>[] { java.lang.String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jScrollPane1.setViewportView(table);
		tableModel = (DefaultTableModel) table.getModel();

		// progress bar setup		

		jMenu1.setText("File");
		
		// Menu Items
		defaultSourcesMenuItem.setText("Load default source dirs");
		jMenu1.add(defaultSourcesMenuItem);
		
		defaultDestinationsMenuItem.setText("Load default destination dirs");
		jMenu1.add(defaultDestinationsMenuItem);

		aboutMenuItem.setText("About");
		jMenu1.add(aboutMenuItem);

		jMenuBar1.add(jMenu1);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(10, 10,
																		10)
																.addComponent(
																		jLabel2)
																.addGap(4, 4, 4)
																.addComponent(
																		jScrollPane1,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		405,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(6, 6, 6)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						addButton,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						34,
																						javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						removeButton,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						34,
																						javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(399,
																		399,
																		399)
																.addComponent(
																		copyButton,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		66,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		closeButton,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		67,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(24, 24,
																		24)
																.addComponent(
																		jLabel1)
																.addGap(4, 4, 4)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				false)
																				.addComponent(
																						progressBar,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						405,
																						Short.MAX_VALUE)
																				.addComponent(
																						textFieldDestination))))
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(progressBar,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(13, 13, 13)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(3, 3, 3)
																.addComponent(
																		jLabel1))
												.addComponent(
														textFieldDestination,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(11, 11, 11)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel2)
												.addComponent(
														jScrollPane1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														110,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		addButton,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		34,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(15, 15,
																		15)
																.addComponent(
																		removeButton,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		34,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(copyButton)
												.addComponent(closeButton))
								.addGap(0, 11, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JMenuItem aboutMenuItem;
	private javax.swing.JButton addButton;
	private javax.swing.JButton closeButton;
	private javax.swing.JButton copyButton;
	private javax.swing.JMenuItem defaultSourcesMenuItem;
	private javax.swing.JMenuItem defaultDestinationsMenuItem;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JProgressBar progressBar;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JButton removeButton;
	private javax.swing.JTable table;
	private javax.swing.JTextField textFieldDestination;
	// End of variables declaration//GEN-END:variables
}
