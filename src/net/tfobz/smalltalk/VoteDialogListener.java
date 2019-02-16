package net.tfobz.smalltalk;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JFrame;

import dframe.ColorPalette;
import dframe.DButton;
import dframe.DTextField;

public class VoteDialogListener extends JDialog {
	
	private BufferedReader in = null;
	public static final int PORT = 65535;
	private DTextField title;
	private DButton[] votes = new DButton[4];
	private DButton cancel;
	private PrintStream out;
	private int numvote;

	public VoteDialogListener(JFrame owner) {	
		super(owner);
		Socket client = null;
		try {
			client = new Socket("localhost", PORT);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintStream(client.getOutputStream());
			out.println("#+/(!)!(?)()=?!?");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		setLayout(null);
		setUndecorated(true);
		getContentPane().setBackground(new Color(65, 65, 65));
		setBounds(50, 100, 400, 460);
		cancel = new DButton("Cancel");
		cancel.setContentAreaFilled(false);
		cancel.setBounds(320, 10, 70, 20);
		cancel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		title = new DTextField();
		title.setBounds(20, 60, 360, 40);
		title.setBackground(new Color(65, 65, 65));
		title.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 25));
		title.setForeground(new Color(140, 140, 140));
		title.setEditable(false);
		add(title);
		add(cancel);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						String line = in.readLine();
						if (line.contains("!§$%&/()=")) { // 10 long
							String lh[]= new String[5];
							line=line.substring(27);
							for (int i = 0; i< 5; i++) {
								int j= line.indexOf("///");
								lh[i]=line.substring(0, j);
								line=line.substring(j+3);
							}
							title.setText(lh[0]);
							for (int i = 0; i < 4; i++) {
								votes[i] = new DButton(lh[i+1]);
								votes[i].setBounds(40, 150 + i * 70, 300, 40);
								votes[i].setFont(new Font("Segoe UI Symbol", Font.PLAIN, 22));
								votes[i].setBorder(null);
								votes[i].setBackground(new Color(65, 65, 65));
								votes[i].setForeground(new Color(140, 140, 140));
								votes[i].addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent arg0) {
										if ( arg0.getSource().equals(votes[1])) {
											numvote=1;
										} else if ( arg0.getSource().equals(votes[2])) {
											numvote=2;
										}else if ( arg0.getSource().equals(votes[3])) {
											numvote=3;
										}else if ( arg0.getSource().equals(votes[4])) {
											numvote=4;
										}
									}
								});
								add(votes[i]);
							}
							setVisible(true);
						}
					}
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
			
		}).start();
		
	}
	
	public String getVotingString() {
		return "!§$%&/()=" + title.getText()+ "///" + votes[0].getText()+"///" + votes[1].getText()+"///" + votes[2].getText()+"///"
				+ votes[3].getText() +"///"+"vote="+numvote;
	}
}
