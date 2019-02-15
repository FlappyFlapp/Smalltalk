package net.tfobz.smalltalk;

import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.sun.javafx.scene.control.skin.ColorPalette;

import dframe.DButton;
import dframe.DFrame;
import dframe.DFrameConstants;
import dframe.DTextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI extends DFrame
{
	private DTextField text;
	private DButton button;
	private JTextPane area;
	private Style style;
	private BufferedReader in;
	private PrintStream out;
	private Socket client = null;
	private JScrollPane jsc;
	private String name = "Fabi";

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
		button.setContentAreaFilled(false);

		try {
			Image img = ImageIO.read(getClass().getResource("senden.png"));
			Image newimg = img.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
			button.setIcon(new ImageIcon(newimg));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(button);
		area = new JTextPane();
		area.setEditable(false);
		area.setBackground(new Color(60, 60, 60));
		area.setForeground(Color.WHITE);
		area.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15));

		UIManager.getLookAndFeelDefaults().put("ScrollBar.thumb", Color.GRAY);
		jsc = new JScrollPane(area, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsc.setComponentZOrder(jsc.getVerticalScrollBar(), 0);
		jsc.setComponentZOrder(jsc.getViewport(), 1);
		jsc.getVerticalScrollBar().setOpaque(false);

		jsc.setLayout(new ScrollPaneLayout() {
			@Override
			public void layoutContainer(Container parent) {
				JScrollPane scrollPane = (JScrollPane) parent;

				Rectangle availR = scrollPane.getBounds();
				availR.x = availR.y = 0;

				Insets parentInsets = parent.getInsets();
				availR.x = parentInsets.left;
				availR.y = parentInsets.top;
				availR.width -= parentInsets.left + parentInsets.right;
				availR.height -= parentInsets.top + parentInsets.bottom;

				Rectangle vsbR = new Rectangle();
				vsbR.width = 12;
				vsbR.height = availR.height;
				vsbR.x = availR.x + availR.width - vsbR.width;
				vsbR.y = availR.y;

				if (viewport != null) {
					viewport.setBounds(availR);
				}
				if (vsb != null) {
					vsb.setVisible(true);
					vsb.setBounds(vsbR);
				}
			}
		});
		jsc.getVerticalScrollBar().setUI(new MyScrollBarUI());

		StyledDocument doc = area.getStyledDocument();

		style = area.addStyle("I'm a Style", null);
		StyleConstants.setForeground(style, Color.WHITE);
		jsc.setBounds(0, 0, getWidth(), 810);
		jsc.setBorder(null);
		add(jsc);

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
			out.println(name);
			new ChatClientThread(in, area).start();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		this.getRootPane().setDefaultButton(button);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String t = text.getText();
				if (t != null && t.length() > 0) {
					out.println(t);
					text.setText(null);
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

class MyScrollBarUI extends BasicScrollBarUI
{
	private final Dimension d = new Dimension();

	@Override
	protected JButton createDecreaseButton(int orientation) {
		return new JButton() {
			@Override
			public Dimension getPreferredSize() {
				return d;
			}
		};
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		return new JButton() {
			@Override
			public Dimension getPreferredSize() {
				return d;
			}
		};
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color color = null;
		JScrollBar sb = (JScrollBar) c;
		if (!sb.isEnabled() || r.width > r.height) {
			return;
		} else if (isDragging) {
			color =  new Color(40, 40, 40);
		} else if (isThumbRollover()) {
			color =  new Color(42, 42, 42);
		} else {
			color = new Color(45, 45, 45);
		}
		g2.setPaint(color);
		g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
		
		g2.drawRoundRect(r.x, r.y, r.width, r.height, 10, 10);
		g2.dispose();
	}

	@Override
	protected void setThumbBounds(int x, int y, int width, int height) {
		super.setThumbBounds(x, y, width, height);
		scrollbar.repaint();
	}
}
