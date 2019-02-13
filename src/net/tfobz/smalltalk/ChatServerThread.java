package net.tfobz.smalltalk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ChatServerThread extends Thread {
	private Socket client = null;
	private BufferedReader in = null;
	private PrintStream out = null;
	private String name = null;

	public ChatServerThread(Socket client) throws IOException {
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintStream(client.getOutputStream());
	}

	public void run() {
		try {
			synchronized (ChatServer.outputStreams) {
				ChatServer.outputStreams.add(out);
				name = in.readLine();
				System.out.println(name + " signed in");
				for (PrintStream outs : ChatServer.outputStreams)
					outs.println(name + " signed in");
			}
			while (true) {
				String line = in.readLine();
				if (line == null)
					break;
				synchronized (ChatServer.outputStreams) {
					for (PrintStream outs : ChatServer.outputStreams)
						outs.println(name + ": " + line);
				}
			}

			synchronized (ChatServer.outputStreams) {
				ChatServer.outputStreams.remove(out);
				System.out.println(name + " signed out");
				for (PrintStream outs : ChatServer.outputStreams)
					outs.println(name + " signed out");
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			if (out != null)
				ChatServer.outputStreams.remove(out);
		} finally {
			try {
				client.close();
			} catch (Exception e1) {
				;
			}
		}
	}
}
