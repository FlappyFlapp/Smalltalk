package net.tfobz.smalltalk;

import java.awt.Color;
import java.awt.geom.Line2D;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import dframe.DButton;
import dframe.DFrame;
import dframe.DFrameConstants;
import dframe.DTextField;
import java.awt.*;

public class GUI extends DFrame {

	private DTextField text;
	private DButton button;

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
		button.setBounds(1555, 820, 40, 40);
		button.setBackground(Color.CYAN);

		try {
			Image img = ImageIO.read(getClass().getResource("senden.png"));
			button.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(button);
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
