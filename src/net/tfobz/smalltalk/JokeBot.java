package net.tfobz.smalltalk;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import ChatConsole.ChatClientThread;

public class JokeBot extends Thread {

	private BufferedReader in = null;
	private PrintStream out;
	private String name = "JokeBot";
	public static final int PORT = 65535;
	private ArrayList<String> list = new ArrayList<String>();
	private Random r = new Random();

	public JokeBot() {
		list.add("A common programming solution is to use threads. But then, two ‘llyou hav erpoblesm.");
		list.add("I’ve got a really good UDP joke to tell you, but I don’t know if you’ll get it.");
		list.add("There are 10 kinds of people in this world: Those who understand binary, those who don’t.");
		list.add("In order to understand recursion you must first understand recursion.");
		list.add("Why do Java programmers wear glasses? Because they don’t C#!");
		Socket client = null;
		try {
			client = new Socket("localhost", PORT);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintStream(client.getOutputStream());
			out.println(name);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				String line = in.readLine();
				if (line.contains("-joke")) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							out.println(tellJoke());
						}
					}).start();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	public String tellJoke() {
		return list.get(r.nextInt(list.size()));
	}
}
