package dframe;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.text.Caret;
import javax.swing.text.JTextComponent;

public class DTextField extends JTextField {
	public DTextField() {
		super();
		initialize();
	}

	public DTextField(String text) {
		super(text);
		initialize();
	}

	private void initialize() {
		setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15));
		setForeground(Color.WHITE);
		setBackground(new Color(75, 75, 75));
		setCaretColor(Color.WHITE);
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
			}

			@Override
			public void focusGained(FocusEvent e) {
				setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ColorPalette.BLAU));
			}
		});
		;
	}
}
