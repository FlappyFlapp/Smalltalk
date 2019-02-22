package Test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.Position;

public class MyTextPane extends JTextPane {
	int textHeight = 0;
	int counter = 1;
	int position = 0;
	public static ArrayList<Line2D> lines = new ArrayList<Line2D>();

	public MyTextPane () {
		super();
		Font f = new Font("Segoe UI Symbol", Font.PLAIN, 15);
		textHeight = f.getSize();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(new Color(75, 75, 75));

		textHeight = g2.getFontMetrics().getHeight();

		for (Line2D lin : lines) {
			g2.draw(lin);
		}

		// for (int i = textHeight; i < getHeight(); i += (6 * textHeight)) {
		// g2.drawLine(0, i + 1, getWidth(), i + 1);
		// }

		g2.dispose();
	}

	public void setLine(int p) {
		position+= (p * textHeight);
		lines.add(new Line2D.Float(15, position - 5, this.getWidth() - 15, position - 5));
		counter++;
	}
}
