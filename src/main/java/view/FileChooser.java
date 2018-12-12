package main.java.view;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

public class FileChooser extends JFrame {

	private JFileChooser chooser;

	/**
	 * Create the frame.
	 */
	public FileChooser() {
		
		FileSystemView fileSystemView = FileSystemView.getFileSystemView();
		JFileChooser chooser = new JFileChooser(fileSystemView);
		JPanel fileChooserPanel = new JPanel();
		fileChooserPanel.add(chooser);
		this.getContentPane().add(fileChooserPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		
	}
	
	public JFileChooser getChooser() {
		return chooser;
	}

}
