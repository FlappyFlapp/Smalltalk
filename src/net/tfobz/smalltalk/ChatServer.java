package net.tfobz.smalltalk;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer
{
	public static final int PORT = 65535;
	public static ArrayList<PrintStream> outputStreams = new ArrayList<PrintStream>();
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(PORT);
			System.out.println("Chat server started");
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					JokeBot bot = new JokeBot();
					bot.start();
				}
				
			}).start();
			while (true) {
				Socket client = server.accept();
				try {
					new ChatServerThread(client).start();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try { server.close(); } catch (Exception e1) { ; }
		}
	}
}