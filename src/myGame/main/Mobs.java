package myGame.main;

import java.awt.Graphics;


public class Mobs extends GameObject {
	public Mobs(int x, int y, String fileName, ID id) {
		super(x, y, fileName, id);
		
	}

	public void render(Graphics g, int X, int Y) {
		g.drawImage(objectImage, X, Y, 65, 65, null);

	}


}
