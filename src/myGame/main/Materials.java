package myGame.main;

import java.awt.Graphics;

public class Materials extends GameObject {

	public Materials(int x, int y, String fileName, ID id) {
		super(x, y, fileName, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics g, int X, int Y) {
		if(id == ID.player || id == ID.enemy) {
			g.drawImage(objectImage, X, Y, 35, 15, null);
		}
		else {
			g.drawImage(objectImage, X, Y, 30, 30, null);
		}

	}

}
