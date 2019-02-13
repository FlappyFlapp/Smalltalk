package net.tfobz.smalltalk;

import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import dframe.DButton;
import dframe.DFrame;
import dframe.DFrameConstants;
import dframe.DTextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends DFrame {

	private DTextField text;
	private DButton button;
	private JTextPane area;
	private Style style;
	private BufferedReader in;
	private PrintStream out;
	private Socket client = null;

	public GUI() {
		setSize(1600, 900);
		centerFrame();
		setToolbar(DFrameConstants.SHOW_MINIMIZE);
		this.getContentPane().setBackground(new Color(60, 60, 60));

		text = new DTextField();
		text.setBounds(50, 820, 1500, 40);
		text.setBackground(new Color(60, 60, 60));
		add(text);

		button = new DButton();
		button.setBounds(1555, 825, 30, 30);
		button.setBackground(new Color(60, 60, 60));

		try {
			Image img = ImageIO.read(getClass().getResource("senden.png"));
			Image newimg = img.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
			button.setIcon(new ImageIcon(newimg));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(button);

		area = new JTextPane();
		StyledDocument doc = area.getStyledDocument();

		style = area.addStyle("I'm a Style", null);
		StyleConstants.setForeground(style, Color.WHITE);

		area.setBounds(0, 0, getWidth(), 810);
		area.setBackground(new Color(60, 60, 60));
		area.setForeground(Color.WHITE);
		area.setEditable(false);
		area.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15));
		add(area);

		// Zin einikritzeln
		// try {
		// StyleConstants.setForeground(style, Color.WHITE);
		// doc.insertString(0, "Scheduler is now running (schedule period = 10)\n",
		// style);
		// StyleConstants.setForeground(style, dframe.ColorPalette.BLAU);
		// doc.insertString(0, "Message: ", style);
		// } catch (BadLocationException e) {
		// }

		try {
			client = new Socket("localhost", 65535);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintStream(client.getOutputStream());
			out.println("Flapp");
			new ChatClientThread(in, area).start();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (text.getText() != null && text.getText() != "") {
					out.println(text.getText());
					text.setText("");
				}
			}
		});
	}

	public JTextPane getArea() {
		return area;
	}

	public void setArea(JTextPane area) {
		this.area = area;
	}

	public DTextField getText() {
		return text;
	}

	public void setText(DTextField text) {
		this.text = text;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		Line2D lin = new Line2D.Float(10, 840, 1590, 840);
		g2.setColor(new Color(90, 90, 90));
		g2.draw(lin);
	}

	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.setVisible(true);


	}

}
