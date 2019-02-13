package dframe;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class DLabel extends JLabel {
	public DLabel() {
		super();
		initialize();
	}

	public DLabel(String text) {
		super(text);
		initialize();
	}

	private void initialize() {
		setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15));
		setForeground(Color.WHITE);
	}

	public void setIdealFontSize() {
		Font labelFont = getFont();
		String labelText = getText();

		int stringWidth = getFontMetrics(labelFont).stringWidth(labelText);
		int componentWidth = getWidth();

		// Find out how much the font can grow in width.
		double widthRatio = (double) componentWidth / (double) stringWidth;

		int newFontSize = (int) (labelFont.getSize() * widthRatio);
		int componentHeight = getHeight();

		// Pick a new font size so it will not be larger than the height of label.
		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		// Set the label's font size to the newly determined size.
		setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
	}
}
