package dframe;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyFrameTest {

	public static void main(String[] args) {
		DFrame m = new DFrame();
		m.setToolbar(DFrameConstants.SHOW_MINIMIZE);
		m.setSize(960,540);
		m.centerFrame();
		BufferedImage image = null;
		try {
			image = ImageIO.read(m.getClass().getResource("Moutains.png"));
		} catch (IOException ex) {
			System.out.println("no u");
		}
		JLabel pic = new JLabel(new ImageIcon(image));

		JPanel p = new JPanel();
		p.setBackground(ColorPalette.BLAU);
		p.setBounds(0, 0, m.getWidth() / 2, m.getHeight());
		p.setLayout(null);
		pic.setBounds(0, 0, p.getWidth(), p.getHeight());
		System.out.println(m.getWidth() / 2 + " " + m.getHeight());
		p.add(pic);
		m.add(p);

		DLabel title = new DLabel("darkframe.");
		title.setBounds(550, 25, 300, 30);
		title.setForeground(ColorPalette.BLAU);
		title.setIdealFontSize();
		m.add(title);

		DLabel name = new DLabel("name");
		name.setBounds(550, 100, 340, 25);
		name.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		name.setForeground(new Color(150, 150, 150));
		m.add(name);

		DTextField name_txt = new DTextField();
		name_txt.setBounds(550, 140, 340, 35);
		name_txt.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		m.add(name_txt);

		name_txt.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				name.setForeground(new Color(150, 150, 150));
			}

			@Override
			public void focusGained(FocusEvent e) {
				name.setForeground(ColorPalette.BLAU);
			}
		});

		DLabel surname = new DLabel("surname");
		surname.setBounds(550, 200, 340, 25);
		surname.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		surname.setForeground(new Color(150, 150, 150));
		m.add(surname);

		DTextField surname_txt = new DTextField();
		surname_txt.setBounds(550, 240, 340, 35);
		surname_txt.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		m.add(surname_txt);
		
		surname_txt.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				surname.setForeground(new Color(150, 150, 150));
			}

			@Override
			public void focusGained(FocusEvent e) {
				surname.setForeground(ColorPalette.BLAU);
			}
		});
		DLabel password = new DLabel("password");
		password.setBounds(550, 300, 340, 25);
		password.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		password.setForeground(new Color(150, 150, 150));
		m.add(password);

		DTextField password_txt = new DTextField();
		password_txt.setBounds(550, 340, 340, 35);
		password_txt.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		m.add(password_txt);
		
		password_txt.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				password.setForeground(new Color(150, 150, 150));
			}

			@Override
			public void focusGained(FocusEvent e) {
				password.setForeground(ColorPalette.BLAU);
			}
		});
		DButton button = new DButton("Sign up");
		button.setBounds(550, 400, 340, 50);
		button.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		m.add(button);

		DLabel member = new DLabel("If you are already a member please");
		member.setBounds(550, 470, 200, 15);
		member.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 12));
		member.setForeground(new Color(150, 150, 150));
		m.add(member);

		DLabel sign = new DLabel("Sign in");
		sign.setBounds(750, 470, 340, 15);
		sign.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 12));
		sign.setForeground(ColorPalette.BLAU);
		m.add(sign);
		m.setVisible(true);
	}

}
