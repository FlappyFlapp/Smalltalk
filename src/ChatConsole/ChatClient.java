package ChatConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JTextPane;

public class ChatClient
{
	public static final int PORT = 65535;
	private String name;
	public ChatClient(String name) {
		this.name = name;
		Socket client = null;
		try {
			client = new Socket("localhost", PORT);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(client.getInputStream()));
			PrintStream out = new PrintStream(client.getOutputStream());
			BufferedReader consoleIn =
					new BufferedReader(new InputStreamReader(System.in));
			// sending the name of the client to the server
			out.println(name);
			new ChatClientThread(in).start();
			int i = 0;
			while (i < 10) {
				String line = "Ich bin "+name;
				out.println(line);
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				i++;
			}
		
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try { client.close(); } catch (Exception e1) { ; }
		}
	}
}
