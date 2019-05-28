package com.qxbytes.searcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class PB extends JButton {
	private String in;
	private String ex;
	private JTextArea linkedIn;
	private JTextArea linkedEx;
	public void setLinkedIn(JTextArea linkedIn) {
		this.linkedIn = linkedIn;
	}

	public void setLinkedEx(JTextArea linkedEx) {
		this.linkedEx = linkedEx;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PB(String arg0, String datain, String dataex) {
		super(arg0);
		this.in = datain;
		this.ex = dataex;
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				linkedIn.setText(linkedIn.getText() + in);
				linkedEx.setText(linkedEx.getText() + ex);
			}
		});
	}

	public String getIn() {
		return in;
	}

	public String getEx() {
		return ex;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
