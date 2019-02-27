package Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.sun.javafx.scene.control.skin.ColorPalette;

import dframe.DButton;

public class ChatClientThread extends Thread {
	private BufferedReader in = null;
	private MyTextPane pane;
	private Style style;
	private GUI gui;
	private ArrayList<DButton> buttons = new ArrayList<DButton>();

	public ChatClientThread(BufferedReader in, GUI gui, MyTextPane pane) {
		this.in = in;
		this.pane = pane;
		this.gui = gui;
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

					int textlaenge = 150;
					if (!line.contains("signed in") && !line.contains("signed out") && !line.startsWith("-")
							&& !line.contains("!§$%&/()=") && !line.contains("=)(/&%$§!")) {
						StyleConstants.setForeground(style, dframe.ColorPalette.BLAU);
						doc.insertString(doc.getLength(), "  " + line.substring(0, index + 1) + "\n", style);
						line = line.substring(index + 2);
						if (line.length() > textlaenge) {
							int p = 1;
							for (int i = 0; i < line.length(); i = i + textlaenge) {
								if (i + textlaenge < line.length()) {
									StyleConstants.setForeground(style, Color.WHITE);
									doc.insertString(doc.getLength(),
											"      " + line.substring(i, i + textlaenge) + "\n", style);
								} else {
									StyleConstants.setForeground(style, Color.WHITE);
									doc.insertString(doc.getLength(), "      " + line.substring(i) + "\n", style);
								}
								p++;
							}
							pane.setLine(p);
						} else {
							StyleConstants.setForeground(style, Color.WHITE);
							doc.insertString(doc.getLength(), "      " + line + "\n", style);
							pane.setLine(2);
						}

					} else if (line.contains("!§$%&/()=")) {
						StyleConstants.setForeground(style, dframe.ColorPalette.BLAU);
						doc.insertString(doc.getLength(), "     " + line.substring(0, line.indexOf(":")), style);
						StyleConstants.setForeground(style, Color.white);
						doc.insertString(doc.getLength(), " started a new voting: ", style);

						String lh[] = new String[5];

						String line1 = line.substring(line.indexOf("!§$%&/()=") + 9);
						for (int i = 0; i < 5; i++) {
							int j = line1.indexOf("///");
							lh[i] = line1.substring(0, j);
							line1 = line1.substring(j + 3);
						}
						StyleConstants.setForeground(style, dframe.ColorPalette.VIOLETT);
						doc.insertString(doc.getLength(), lh[0] + "\n", style);
						StyleConstants.setForeground(style, Color.WHITE);
						for (int i = 1; i < 5; i++) {
							doc.insertString(doc.getLength(), "        " + lh[i] + "\n", style);
						}
						pane.setLine(5);
						VoteDialogListener vdl = new VoteDialogListener(gui, gui.getOut());
						vdl.setLocation((int) gui.getLocation().getX() + 250 + 10,
								(int) gui.getLocation().getY() + 365);
						vdl.setModal(true);
						vdl.setVotingTable(line);
						vdl.setVisible(true);
					} else if (line.contains("=)(/&%$§!")) {
						StyleConstants.setForeground(style, dframe.ColorPalette.BLAU);
						doc.insertString(doc.getLength(), "     " + line.substring(0, line.indexOf(":")), style);
						StyleConstants.setForeground(style, Color.white);
						doc.insertString(doc.getLength(), " has voted: ", style);
						String lh[] = new String[9];

						String line1 = line.substring(line.indexOf("=)(/&%$§!") + 9);
						for (int i = 0; i < 8; i++) {
							int j = line1.indexOf("///");
							lh[i] = line1.substring(0, j);
							line1 = line1.substring(j + 3);
						}
						lh[8] = line1;
						StyleConstants.setForeground(style, dframe.ColorPalette.VIOLETT);
						doc.insertString(doc.getLength(), lh[0] + "\n", style);
						StyleConstants.setForeground(style, Color.WHITE);
						for (int i = 1; i <= 7; i = i + 2) {
							doc.insertString(doc.getLength(), "        " + lh[i + 1] + "    " + lh[i] + "\n", style);
						}
						pane.setLine(5);
					} else if (line.contains("signed out")) {
						StyleConstants.setForeground(style, dframe.ColorPalette.ROT);
						doc.insertString(doc.getLength(), line + "\n", style);
						pane.setLine(2);
						
						for (DButton b : buttons) {
							if (b.getText().contains(line.substring(0, line.indexOf(" signed out")))) {
								gui.getChats_bar().remove(b);
								gui.getChats_bar().validate();
								gui.getChats_bar().repaint();
								buttons.remove(b);
								break;
							}
						}
						
					} else if (line.contains("signed in")) {
						StyleConstants.setForeground(style, dframe.ColorPalette.GRUEN);
						doc.insertString(doc.getLength(), line + "\n", style);
						pane.setLine(2);
						DButton j = new DButton(line.substring(0, line.indexOf("signed in")));
						j.setPreferredSize(new Dimension(gui.getChat_jsc().getWidth(), 45));
						j.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
						j.setContentAreaFilled(false);
						j.addMouseListener(new MouseAdapter() {
							public void mouseReleased(MouseEvent e) {
								gui.getChat_jsc().repaint();
								gui.getChat_jsc().getVerticalScrollBar().repaint();
							}

							public void mousePressed(MouseEvent e) {
								gui.getChat_jsc().repaint();
								gui.getChat_jsc().getVerticalScrollBar().repaint();
							}

							public void mouseClicked(MouseEvent e) {
								gui.getChat_jsc().repaint();
								gui.getChat_jsc().getVerticalScrollBar().repaint();
							}

							public void mouseEntered(MouseEvent e) {
								gui.getChat_jsc().repaint();
								gui.getChat_jsc().getVerticalScrollBar().repaint();
							}

							public void mouseExited(MouseEvent e) {
								gui.getChat_jsc().repaint();
								gui.getChat_jsc().getVerticalScrollBar().repaint();
							}
						});
						gui.getChats_bar().add(j);
						buttons.add(j);
						gui.getChats_bar().validate();
						gui.getChats_bar().repaint();
						j.repaint();
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
