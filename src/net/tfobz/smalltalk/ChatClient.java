package net.tfobz.smalltalk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ChatClient
{
	public static final int PORT = 65535;
	public static void main(String[] args) {
		Socket client = null;
		try {
			client = new Socket(args[1], PORT);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(client.getInputStream()));
			PrintStream out = new PrintStream(client.getOutputStream());
			BufferedReader consoleIn =
					new BufferedReader(new InputStreamReader(System.in));
			// sending the name of the client to the server
			out.println(args[0]);
			new ChatClientThread(in).start();
			while (true) {
				String line = consoleIn.readLine();
				if (line == null)
					// pressed [Ctrl]+Z to sign out
					break;
				out.println(line);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try { client.close(); } catch (Exception e1) { ; }
		}
	}
}