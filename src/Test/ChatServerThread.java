package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

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
			}
			name = in.readLine();
			System.out.println(name + " signed in");
			synchronized (ChatServer.names) {
				ChatServer.names.add(name);
			}
			synchronized (ChatServer.outputStreams) {
				for (PrintStream outs : ChatServer.outputStreams)
					outs.println(name + " signed in");
			}
			while (true) {
				String line = in.readLine();
				if (line == null)
					break;
				System.out.println(line);
				if (line.contains("!§$%&/()=")) {
					synchronized (ChatServer.votings) {
						ChatServer.votings.add(new Voting(line));
					}
					synchronized (ChatServer.outputStreams) {
						for (PrintStream outs : ChatServer.outputStreams)
							outs.println(name + ": " + line);
					}
				} else if (line.contains("=)(/&%$§!")) {
					synchronized (ChatServer.votings) {
						for (int i = 0; i < ChatServer.votings.size(); i++) {
							int index = ChatServer.votings.get(i).getLine().indexOf("!§$%&/()=") + 9;
							System.out.println("index: " + index);
							System.out.println(" line: " + ChatServer.votings.get(i).getLine());
							if (line.contains(ChatServer.votings.get(i).getLine().substring(index))) {
								int vote = Integer.parseInt(line.substring(line.indexOf("vote=") + 5));
								System.out.println(vote);
								ChatServer.votings.get(i).setVote(vote);
								String voting = ChatServer.votings.get(i).getVotingString();
								synchronized (ChatServer.outputStreams) {
									for (PrintStream outs : ChatServer.outputStreams)
										outs.println(name + ": " + voting);
								}
								break;
							}
						}
					}

				} else {
					synchronized (ChatServer.outputStreams) {
						for (PrintStream outs : ChatServer.outputStreams)
							outs.println(name + ": " + line);
					}
				}

			}
			synchronized (ChatServer.outputStreams) {
				ChatServer.outputStreams.remove(out);
				synchronized (ChatServer.names) {
					ChatServer.names.remove(name);
				}
				System.out.println(name + " signed out");
				for (PrintStream outs : ChatServer.outputStreams)
					outs.println(name + " signed out");
			}
		} catch (IOException e) {
			if (out != null && name != null) {
				synchronized (ChatServer.names) {
					ChatServer.names.remove(name);
				}
				synchronized (ChatServer.outputStreams) {
					ChatServer.outputStreams.remove(out);
					System.out.println(name + " signed out");
					for (PrintStream outs : ChatServer.outputStreams)
						outs.println(name + " signed out");
				}
			}
		} finally {
			try {
				client.close();
			} catch (Exception e1) {
				;
			}
		}
	}
}
