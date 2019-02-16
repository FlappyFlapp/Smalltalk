package net.tfobz.smalltalk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JTextPane;

public class ChatClient {
	public static final int PORT = 65535;

	public ChatClient(String name) {
		GUI gui = new GUI();
		gui.setVisible(true);
		Socket client = null;
		try {
			client = new Socket("localhost", PORT);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintStream out = new PrintStream(client.getOutputStream());
			BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
			out.println(name);
			new ChatClientThread(in, gui, gui.getArea()).start();
			while (true) {
				String line = consoleIn.readLine();
				if (line == null)
					break;
				out.println(line);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				client.close();
			} catch (Exception e1) {
				;
			}
		}
	}
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.setVisible(true);
		Socket client = null;
		try {
			client = new Socket(args[1], PORT);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintStream out = new PrintStream(client.getOutputStream());
			BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
			out.println(args[0]);
			new ChatClientThread(in, gui, gui.getArea()).start();

			while (true) {
				String line = consoleIn.readLine();
				if (line == null)
					break;
				out.println(line);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				client.close();
			} catch (Exception e1) {
				;
			}
		}
	}
}