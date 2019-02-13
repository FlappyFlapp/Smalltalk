package dframe;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class DFrame extends JFrame {
	private JMenuBar menuBar;
	private JButton exit;
	private JButton full;
	private JButton min;

	public DFrame() {
		super();
		initialize();
	}

	public DFrame(String title) {
		super(title);
		initialize();
	}

	public void setToolbar(DFrameConstants settings) {
		switch (settings) {
		case SHOW_ALL:
			min.setVisible(true);
			full.setVisible(true);
			exit.setVisible(true);
			break;
		case SHOW_EXIT_ONLY:
			min.setVisible(false);
			full.setVisible(false);
			exit.setVisible(true);
			break;
		case SHOW_MINIMIZE:
			min.setVisible(true);
			full.setVisible(false);
			exit.setVisible(true);
			break;
		case SHOW_FULLSCREEN:
			min.setVisible(false);
			full.setVisible(true);
			exit.setVisible(true);
			break;
		}
	}

	private void initialize() {
		setLayout(null);
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(75, 75, 75));
		idealFrameSize();
		centerFrame();
		addMenuBar();
		moveableFrame();
	}

	private void addMenuBar() {
		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(52, 52, 52));
		menuBar.setBorderPainted(false);
		setJMenuBar(menuBar);
		addNavigationTools();

	}

	private void addNavigationTools() {
		Image exit_icon = null;
		Image full_icon = null;
		Image min_icon = null;

		try {
			min_icon = ImageIO.read(getClass().getResource("min.png"));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		try {
			full_icon = ImageIO.read(getClass().getResource("full.png"));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		try {
			exit_icon = ImageIO.read(getClass().getResource("exit.png"));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		min = new JButton();
		full = new JButton();
		exit = new JButton();

		min.setIcon(new ImageIcon(min_icon));
		min.setBackground(new Color(65, 65, 65));
		min.setBorderPainted(false);
		min.setFocusPainted(false);
		min.setOpaque(false);
		min.setContentAreaFilled(false);
		min.setMargin(new Insets(5, 5, 5, 5));
		min.setForeground(Color.WHITE);
		min.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		min.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DFrame.this.setState(Frame.ICONIFIED);
			}
		});
		min.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				min.setContentAreaFilled(true);
			}

			public void mouseExited(MouseEvent e) {
				min.setContentAreaFilled(false);
			}
		});

		full.setIcon(new ImageIcon(full_icon));
		full.setBackground(new Color(65, 65, 65));
		full.setBorderPainted(false);
		full.setFocusPainted(false);
		full.setOpaque(false);
		full.setContentAreaFilled(false);
		full.setMargin(new Insets(5, 5, 5, 5));
		full.setForeground(Color.WHITE);
		full.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		full.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (DFrame.this.getWidth() != DFrame.this.getScreenDimensions().getWidth())
					DFrame.this.setExtendedState(JFrame.MAXIMIZED_BOTH);
				else
					DFrame.this.idealFrameSize();
				DFrame.this.centerFrame();
			}
		});
		full.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				full.setContentAreaFilled(true);
			}

			public void mouseExited(MouseEvent e) {
				full.setContentAreaFilled(false);
			}
		});

		exit.setIcon(new ImageIcon(exit_icon));
		exit.setBackground(Color.red);
		exit.setBorderPainted(false);
		exit.setFocusPainted(false);
		exit.setOpaque(false);
		exit.setContentAreaFilled(false);
		exit.setMargin(new Insets(5, 5, 5, 5));
		exit.setForeground(Color.WHITE);
		exit.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DFrame.this.dispose();
				System.exit(0);
			}
		});
		exit.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				exit.setContentAreaFilled(true);
			}

			public void mouseExited(MouseEvent e) {
				exit.setContentAreaFilled(false);
			}
		});
		menuBar.add(Box.createHorizontalGlue());

		menuBar.add(min);
		menuBar.add(full);
		menuBar.add(exit);
	}

	private DisplayMode getScreenDimensions() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		return gd.getDisplayMode();
	}

	private void idealFrameSize() {
		setSize((int) getScreenDimensions().getWidth() / 2, (int) getScreenDimensions().getHeight() / 2);
	}

	private void moveableFrame() {
		Point point = new Point();
		menuBar.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!e.isMetaDown()) {
					point.x = e.getX();
					point.y = e.getY();
				}
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!e.isMetaDown()) {
					Point p = getLocation();
					setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
				}
			}
		});
	}

	public void centerFrame() {
		setLocation(getScreenDimensions().getWidth() / 2 - getWidth() / 2,
				getScreenDimensions().getHeight() / 2 - getHeight() / 2);
	}

}
