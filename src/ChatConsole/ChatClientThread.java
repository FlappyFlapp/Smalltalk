package ChatConsole;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ChatClientThread extends Thread
{
	private BufferedReader in = null;
	public ChatClientThread(BufferedReader in) {
		this.in = in;
	}
	public void run() {
		try {
			while (true) {
				String line = in.readLine();
				System.out.println(line);
			}
		} catch (SocketException e) {
			System.out.println("Ignore exception");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}


