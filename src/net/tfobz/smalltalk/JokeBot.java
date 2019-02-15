package net.tfobz.smalltalk;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import ChatConsole.ChatClientThread;

public class JokeBot extends Thread {

	private BufferedReader in = null;
	private JTextPane pane;
	private String name="JokeBot";
	public static final int PORT = 65535;
	String l=null;
	private PrintStream out;

	public JokeBot() {
		Socket client = null;
		try {
			client = new Socket("localhost", PORT);
			in = new BufferedReader(
					new InputStreamReader(client.getInputStream()));
			out = new PrintStream(client.getOutputStream());
			// sending the name of the client to the server
			out.println(name);
			this.start();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try { client.close(); } catch (Exception e1) { ; }
		}
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				String line = in.readLine();
				if (line.startsWith("-")) {
					switch (line) {
						case "-joke": {
							line=tellJoke();
							break;
						}
						default: {
							line="Kein Befehl";
							break;
						}
					}
					new Thread(new Runnable() {
						@Override
						public void run() {
							out.println(l);
						}
					});
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		
	}

	public String tellJoke() {
		return "Das ist ein Witz";
	}
}
