package myGame.main;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends GameObject {

	public Player(int x, int y, String fileName, ID id) {
		super(x, y, fileName, id);
		// TODO Auto-generated constructor stub
	}

	public void render(Graphics g, int X, int Y) {
		g.setColor(Color.PINK);
		g.drawImage(objectImage, X, Y, 65, 65, null);
		//g.fillRect(X, Y, 30, 30);
		
	}


}
