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

import javax.swing.JDialog;
import javax.swing.JFrame;

import dframe.ColorPalette;
import dframe.DButton;
import dframe.DTextField;

public class VoteDialog extends JDialog {

	public static final int PORT = 65535;
	private PrintStream out;
	private DButton button;
	private DButton send;
	private DTextField title;
	private DTextField[] votes = new DTextField[4];

	public VoteDialog(JFrame owner, PrintStream out) {
		super(owner);
		this.out = out;
		initialize();
	}

	public VoteDialog() {
		initialize();
	}

	private void initialize() {
		setLayout(null);
		setUndecorated(true);
		getContentPane().setBackground(new Color(65, 65, 65));
		setBounds(50, 100, 400, 460);
		button = new DButton("Cancel");
		button.setContentAreaFilled(false);
		button.setBounds(320, 10, 70, 20);
		button.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		add(button);

		send = new DButton("Send");
		send.setContentAreaFilled(false);
		send.setBounds(320, 420, 70, 20);
		send.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 25));
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				out.println(getVotingString());
				VoteDialog.this.setVisible(false);
			}
		});
		add(send);
		title = new DTextField("Poll title");
		title.setBounds(20, 60, 360, 40);
		title.setBackground(new Color(65, 65, 65));
		title.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 25));
		title.setForeground(new Color(140, 140, 140));

		title.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (title.getText() == null || title.getText().length() <= 0 || title.getText() == "") {
					title.setForeground(new Color(140, 140, 140));
					title.setText("Poll title");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				title.setForeground(Color.WHITE);
				if (title.getText().contains("Poll title"))
					title.setText("");
			}
		});
		add(title);

		for (int i = 0; i < 4; i++) {
			final int j = i;
			votes[i] = new DTextField("poll option...");
			votes[i].setBounds(40, 150 + i * 70, 300, 40);
			votes[i].setFont(new Font("Segoe UI Symbol", Font.PLAIN, 22));
			votes[i].setBorder(null);
			votes[i].setBackground(new Color(65, 65, 65));
			votes[i].removeFocusListener(votes[i].getFocusListener());
			votes[i].setForeground(new Color(140, 140, 140));
			votes[i].addFocusListener(new FocusListener() {

				@Override
				public void focusLost(FocusEvent e) {
					votes[j].setForeground(Color.WHITE);
					if (votes[j].getText() == null || votes[j].getText().length() <= 0 || votes[j].getText() == "") {
						votes[j].setForeground(new Color(140, 140, 140));
						votes[j].setText("poll option...");
					}
				}

				@Override
				public void focusGained(FocusEvent e) {
					votes[j].setForeground(ColorPalette.BLAU);
					if (votes[j].getText().contains("poll option..."))
						votes[j].setText("");
				}
			});
			add(votes[i]);
		}
	}

	public String getVotingString() {
		return "!§$%&/()=" + title.getText() + "///" + votes[0].getText() + "///" + votes[1].getText() + "///"
				+ votes[2].getText() + "///" + votes[3].getText() + "///";
	}
}
