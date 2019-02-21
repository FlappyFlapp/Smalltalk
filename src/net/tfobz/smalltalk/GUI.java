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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicScrollBarUI;

import dframe.DButton;
import dframe.DFrame;
import dframe.DFrameConstants;
import dframe.DLabel;
import dframe.DTextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends DFrame {
	private DTextField text;
	private DButton button;
	private MyTextPane area;
	private BufferedReader in;
	private PrintStream out;
	private Socket client = null;
	private JScrollPane jsc;
	private String name = "Unknown";
	private JPanel login_pnl;
	private JPanel all_pnl;
	private DTextField name_txt;
	private DButton start;
	private DLabel start_lbl;
	private JLabel bild;
	private DButton vote_btn;
	private JPanel write_bar;

	public GUI() {
		setSize(1600, 900);
		centerFrame();
		setToolbar(DFrameConstants.SHOW_MINIMIZE);
		this.getContentPane().setBackground(new Color(60, 60, 60));

		all_pnl = new JPanel();
		all_pnl.setBounds(0, 0, getWidth(), getHeight());
		all_pnl.setBackground(new Color(60, 60, 60));
		all_pnl.setLayout(null);
		add(all_pnl);
		
		write_bar = new JPanel();
		write_bar.setBounds(0, 810, getWidth(), 90);
		write_bar.setBackground(new Color(65, 65, 65));
		write_bar.setLayout(null);
		all_pnl.add(write_bar);
		
		text = new DTextField();
		text.setBounds(50, 10, 1500, 40);
		text.setBackground(new Color(65, 65, 65));
		write_bar.add(text);

		button = new DButton();
		button.setBounds(1555, 15, 30, 30);
		button.setContentAreaFilled(false);
		
		try {
			Image img = ImageIO.read(getClass().getResource("senden.png"));
			Image newimg = img.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
			button.setIcon(new ImageIcon(newimg));
		} catch (Exception ex) {
		}

		write_bar.add(button);

		vote_btn = new DButton();
		vote_btn.setBounds(10, 15, 30, 30);
		vote_btn.setBackground(new Color(60, 60, 60));
		vote_btn.setContentAreaFilled(false);

		try {
			Image img = ImageIO.read(getClass().getResource("vote.png"));
			Image newimg = img.getScaledInstance(vote_btn.getWidth(), vote_btn.getHeight(), Image.SCALE_SMOOTH);
			vote_btn.setIcon(new ImageIcon(newimg));
		} catch (Exception ex) {
		}

		write_bar.add(vote_btn);

		vote_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VoteDialog v = new VoteDialog(GUI.this, GUI.this.out);
				v.setLocation((int) GUI.this.getLocation().getX() + 10, (int) GUI.this.getLocation().getY() + 365);
				v.setModal(true);
				v.setVisible(true);
			}
		});

		area = new MyTextPane();
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
		jsc.setBounds(0, 0, getWidth(), 810);
		jsc.setBorder(null);
		all_pnl.add(jsc);

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

		all_pnl.setVisible(false);

		login_pnl = new JPanel();
		login_pnl.setBounds(0, 0, getWidth(), getHeight());
		login_pnl.setBackground(new Color(60, 60, 60));
		login_pnl.setLayout(null);
		add(login_pnl);

		start_lbl = new DLabel("What's your name, buddy?");
		start_lbl.setBounds(500, 150, 600, 50);
		start_lbl.setHorizontalAlignment(SwingUtilities.HORIZONTAL);
		start_lbl.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 35));
		login_pnl.add(start_lbl);

		bild = new JLabel();
		bild.setSize(300, 300);
		bild.setLocation(getWidth() / 2 - bild.getWidth() / 2, 250);
		try {
			Image img = ImageIO.read(getClass().getResource("Avatar.png"));
			Image newimg = img.getScaledInstance(bild.getWidth(), bild.getHeight(), Image.SCALE_SMOOTH);
			bild.setIcon(new ImageIcon(newimg));
		} catch (Exception ex) {
		}

		login_pnl.add(bild);
		name_txt = new DTextField();
		name_txt.setBounds(600, 600, 400, 60);
		name_txt.setBackground(new Color(60, 60, 60));
		name_txt.setHorizontalAlignment(SwingConstants.HORIZONTAL);
		name_txt.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 30));
		login_pnl.add(name_txt);

		start = new DButton("Smalltalk now.");
		start.setBounds(700, 700, 200, 50);
		start.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		start.setContentAreaFilled(false);
		login_pnl.add(start);

		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (name_txt != null && name_txt.getText().length() > 0) {
					name = name_txt.getText();
					try {
						client = new Socket("localhost", 65535);
						in = new BufferedReader(new InputStreamReader(client.getInputStream()));
						out = new PrintStream(client.getOutputStream());
						out.println(name);
						new ChatClientThread(in, GUI.this, area).start();
					} catch (IOException ex) {
					}
					login_pnl.setVisible(false);
					all_pnl.setVisible(true);

					GUI.this.repaint();
				}
			}
		});
	}

	public JTextPane getArea() {
		return area;
	}

	public void setArea(MyTextPane area) {
		this.area = area;
	}

	public DTextField getText() {
		return text;
	}

	public void setText(DTextField text) {
		this.text = text;
	}

	public PrintStream getOut() {
		return out;
	}

	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.setVisible(true);
	}

}

class MyScrollBarUI extends BasicScrollBarUI {
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
			color = new Color(40, 40, 40);
		} else if (isThumbRollover()) {
			color = new Color(42, 42, 42);
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
