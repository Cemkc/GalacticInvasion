package myGame.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

public abstract class GameObject {
	protected int x, y;
	protected ID id;
	protected BufferedImage objectImage;
	protected int velX, VelY;
	
	public GameObject(int x, int y, String fileName, ID id) {
		super();
		this.x = x;
		this.y = y;
		try {
			objectImage = ImageIO.read(new FileImageInputStream(new File(fileName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.id = id;
	}
	public abstract void render(Graphics g, int X, int Y);

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return VelY;
	}

	public void setVelY(int velY) {
		VelY = velY;
	}
	
	
}
