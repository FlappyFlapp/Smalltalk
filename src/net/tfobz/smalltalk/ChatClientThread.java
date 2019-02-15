package net.tfobz.smalltalk;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ChatClientThread extends Thread
{
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
//					SimpleAttributeSet attribs = new SimpleAttributeSet();
//					StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_RIGHT);
//					pane.setParagraphAttributes(attribs, true);

					int textlaenge = 200;
					if (!line.contains("signed in")) {
						StyleConstants.setForeground(style, dframe.ColorPalette.BLAU);
						doc.insertString(doc.getLength(), "  " + line.substring(0, index + 1) + "\n", style);
						line = line.substring(index + 2);
						if (line.length() > textlaenge) {
							for (int i = 0; i < line.length(); i = i + textlaenge) {
								if (i + textlaenge < line.length()) {
									StyleConstants.setForeground(style, Color.WHITE);
									doc.insertString(doc.getLength(), "      " + line.substring(i, i + textlaenge) + "\n", style);
								} else {
									StyleConstants.setForeground(style, Color.WHITE);
									doc.insertString(doc.getLength(), "      " + line.substring(i) + "\n", style);
								}

							}
						} else {
							StyleConstants.setForeground(style, Color.WHITE);
							doc.insertString(doc.getLength(), "      " + line + "\n", style);
						}

					} else {
						StyleConstants.setForeground(style, dframe.ColorPalette.GRUEN);
						doc.insertString(doc.getLength(), line + "\n", style);
					
					}
					pane.setSelectionEnd(pane.getStyledDocument().getLength());
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
