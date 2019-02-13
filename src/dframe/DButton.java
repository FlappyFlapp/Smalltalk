package dframe;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

public class DButton extends JButton {
	public DButton() {
		super();
		initialize();
	}

	public DButton(String text) {
		super(text);
		initialize();
	}

	private void initialize() {
		setBackground(new Color(62, 62, 62));
		setFocusPainted(false);
		setBorderPainted(false);
		setBorder(BorderFactory.createLineBorder(Color.white, 1));
		setForeground(new Color(240, 240, 240));
		setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15));
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				// setBorderPainted(true);

				setForeground(ColorPalette.BLAU);
			}

			public void mouseExited(MouseEvent e) {
				// setBorderPainted(false);
				setForeground(Color.WHITE);
			}
		});
	}
}
