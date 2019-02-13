package net.tfobz.smalltalk;

import java.awt.geom.Line2D;

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

public class GUI extends DFrame {

	private DTextField text;
	private DButton button;
	private JTextPane area;
	private Style style;

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
