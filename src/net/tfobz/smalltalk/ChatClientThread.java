package net.tfobz.smalltalk;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ChatClientThread extends Thread {
	private BufferedReader in = null;
	private JTextPane pane;
	private Style style;

	public ChatClientThread(BufferedReader in, JTextPane pane) {
		this.in = in;
		this.pane = pane;
	}

	@Override
	public void run() {
		try {
			StyledDocument doc = pane.getStyledDocument();
			style = pane.addStyle("I'm a Style", null);
			while (true) {
				String line = in.readLine();
				try {
					int index = 0;
					for (int i = 0; i < line.length(); i++) {
						if (line.charAt(i) == ':') {
							index = i;
							break;
						}
					}
					if (!line.contains("signed in")) {
						StyleConstants.setForeground(style, dframe.ColorPalette.BLAU);
						doc.insertString(doc.getLength(), line.substring(0, index + 1), style);
						StyleConstants.setForeground(style, Color.WHITE);
						doc.insertString(doc.getLength(), line.substring(index + 1) + "\n", style);
					} else {
						StyleConstants.setForeground(style, dframe.ColorPalette.GRUEN);
						doc.insertString(doc.getLength(), line + "\n", style);
					}
					
				} catch (BadLocationException e) {
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
