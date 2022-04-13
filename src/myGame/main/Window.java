package myGame.main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas {

	private static final long serialVersionUID = -6912241850947887395L;
	
	protected static JFrame frame;
	
	public Window(int width, int height, int scale, String windowtitle, Game game) {
		frame = new JFrame(windowtitle);
		
		frame.setPreferredSize(new Dimension(width * scale, height * scale));
		frame.setMaximumSize(new Dimension(width * scale, height * scale));
		frame.setMinimumSize(new Dimension(width * scale, height * scale));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.pack();
		frame.setVisible(true);
	}
	public Window() {
		frame.setTitle("Galactic Invasion | " + Game.frames + " fps");
	}

}
