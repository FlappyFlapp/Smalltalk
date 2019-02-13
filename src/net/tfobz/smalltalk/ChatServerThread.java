package net.tfobz.smalltalk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ChatServerThread extends Thread
{
	private Socket client = null;
	private BufferedReader in = null;
	private PrintStream out = null;
	public ChatServerThread(Socket client) throws IOException {
	//	(Initialisierung von BufferedReader und PrintStream siehe hinten)
	//	…
	}
	@Override
	public void run() {
		try {
			ChatServer.outputStreams.add(out);
			String name = in.readLine();
			System.out.println(name + " signed in");
			for (PrintStream outs: ChatServer.outputStreams)
				outs.println(name + " signed in");
			while (true) {
				String line = in.readLine();
				if (line == null)
					break;
				for (PrintStream outs: ChatServer.outputStreams)
					outs.println(name + ": " + line);
			}
			ChatServer.outputStreams.remove(out);
			System.out.println(name + " signed out");
			for (PrintStream outs: ChatServer.outputStreams)
				outs.println(name + " signed out");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			if (out != null)
				ChatServer.outputStreams.remove(out);
		} finally {
			try { client.close(); } catch (Exception e1) { ; }
		}
	}
}

