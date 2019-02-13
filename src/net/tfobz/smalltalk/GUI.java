package net.tfobz.smalltalk;

import dframe.DFrame;

public class GUI extends DFrame {
	
	public GUI () {
		setSize(1600,900);
		centerFrame();
	}
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.setVisible(true);
	}

}
