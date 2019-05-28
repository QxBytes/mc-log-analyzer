package com.qxbytes.searcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ScanningGUI {

	private JFrame frame;
	public  static StringBuilder LOGS = new StringBuilder();
	public  static StringBuilder ERRORS = new StringBuilder();
	private Path location = new File  
			(System.getProperty("user.home") + System.getProperty("file.separator")+ "AppData" + System.getProperty("file.separator") + "Roaming" + System.getProperty("file.separator") + ".minecraft" + System.getProperty("file.separator") + "logs").toPath();
	private static JTextArea consoleArea = new JTextArea();
	private static JTextArea errorArea = new JTextArea();
	private JTextField charSetField;

	/**
	 * Launch the application.
	 * @author QxBytes
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScanningGUI window = new ScanningGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ScanningGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Minecraft Log File Analyzer");
		frame.setBounds(100, 100, 854, 480);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Settings", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(2, 2, 2, 2)));
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		
		JLabel lblPresets = new JLabel("Presets");
		panel_3.add(lblPresets);
		
		JPanel panel_9 = new JPanel();
		panel_3.add(panel_9);
		
		JPanel panel_8 = new JPanel();
		panel_2.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_11 = new JPanel();
		panel_8.add(panel_11);
		panel_11.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_11.add(scrollPane_1, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		scrollPane_1.setViewportView(textArea);
		
		JLabel lblSeparateTermsYou = new JLabel("Separate Terms you would like to search for with a comma:");
		panel_11.add(lblSeparateTermsYou, BorderLayout.NORTH);
		lblSeparateTermsYou.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JPanel panel_12 = new JPanel();
		panel_8.add(panel_12);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_3 = new JScrollPane();
		panel_12.add(scrollPane_3, BorderLayout.CENTER);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setLineWrap(true);
		scrollPane_3.setViewportView(textArea_1);
		
		for (PB presetButton : PresetConstants.PRESET)
		{
			presetButton.setLinkedEx(textArea_1);
			presetButton.setLinkedIn(textArea);
			panel_3.add(presetButton);
		}
		
		JLabel lblSeparateTermsYou_1 = new JLabel("Separate Terms you would like to exclude with a comma:");
		lblSeparateTermsYou_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_12.add(lblSeparateTermsYou_1, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Console", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		
		scrollPane.setViewportView(consoleArea);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		panel_1.add(btnRefresh, BorderLayout.NORTH);
		
		JButton processBtn = new JButton("Process!");
		processBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		processBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consoleArea.setText("");
				errorArea.setText("");
				ERRORS = new StringBuilder();
				LOGS = new StringBuilder();
				if (charSetField.getText() == null || charSetField.getText().trim().equals("")) 
				{
					AwesomeFileVisitor.CHARSET = Charset.defaultCharset();
				}
				else
				{
					AwesomeFileVisitor.CHARSET = Charset.forName(charSetField.getText());
				}
				tabbedPane.setSelectedIndex(1);
				processBtn.setEnabled(false);
				
				try {
					Thread t = new Thread() {
						public void run() {
							Searcher.search(location,textArea.getText().split(","),textArea_1.getText().split(","));
							processBtn.setEnabled(true);
						}
					};
					t.start();
				} catch (Exception ee) {
					addLog("Error: " + ee.toString());
				}
				
				consoleArea.setText(LOGS.toString());
				
			}
		});
		panel.add(processBtn, BorderLayout.SOUTH);
		
		JPanel panel_10 = new JPanel();
		panel.add(panel_10, BorderLayout.NORTH);
		panel_10.setLayout(new GridLayout(1, 1, 0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_10.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel label = new JLabel("Where are your log files located?");
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_6.add(label);
		
		JLabel label_1 = new JLabel("What charset should be used (if not default)?");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_6.add(label_1);
		
		JPanel panel_7 = new JPanel();
		panel_10.add(panel_7);
		panel_7.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton button = new JButton("Choose Log Folder");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_7.add(button);
		
		
		charSetField = new JTextField();
		panel_7.add(charSetField);
		charSetField.setColumns(10);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File  
						(System.getProperty("user.home") + System.getProperty("file.separator")+ "AppData" + System.getProperty("file.separator") + "Roaming" + System.getProperty("file.separator") + ".minecraft" + System.getProperty("file.separator") + "logs"));
			    chooser.setFileHidingEnabled(false);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//				FileNameExtensionFilter filter = new FileNameExtensionFilter(
//						"Older Excel Files Only", "xls");
//				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					ScanningGUI.addLog("You chose to open this file: " +
							chooser.getSelectedFile().getName());
					location = chooser.getSelectedFile().toPath();
				}
				
			}
		});
		panel_7.add(charSetField);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Errors", null, panel_4, null);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_4.add(scrollPane_2, BorderLayout.CENTER);
		
		
		scrollPane_2.setViewportView(errorArea);
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("About", null, panel_5, null);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JTextArea txtrCreatedByQxbytes = new JTextArea();
		txtrCreatedByQxbytes.setEditable(false);
		txtrCreatedByQxbytes.setText("Created by QxBytes\r\nVersion 1.0.0\r\nGNU General Public License 3.0.\r\nSoftware provided AS-IS with No Warranties.\r\nIf you misuse the software I am not responsible for any damages!\r\n\r\nKnown Issues:\r\nIf you don't know charset leave it blank\r\nIf you don't know location leave it blank (User/AppData/Roaming/.minecraft/logs)\r\nYou must enter a list separated by commas\r\nLarge files take a very long time and will be skipped");
		panel_5.add(txtrCreatedByQxbytes, BorderLayout.CENTER);
		
		
	}
	public static void addLog(String x) {
		LOGS.append("\n"+x);
	}
	public static void refresh() {
		consoleArea.setText(LOGS.toString());
	}
	public static void addError(String x) {
		ERRORS.append("\n"+x);
		errorArea.setText(ERRORS.toString());
	}
}
