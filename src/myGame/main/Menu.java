package myGame.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu{
	private Rectangle playButton = new Rectangle(Game.WIDTH * Game.scale / 2 - 62, 125, 100, 50);
	private Rectangle endlessButton = new Rectangle(Game.WIDTH * Game.scale / 2 - 97, 225, 172, 50);
	private Rectangle quitButton = new Rectangle(Game.WIDTH * Game.scale / 2 - 62, 325, 100, 50);
	private Rectangle menuButton = new Rectangle(Game.WIDTH * Game.scale / 2 - 82, 225, 140, 50);
	private Rectangle contButton = new Rectangle(Game.WIDTH * Game.scale / 2 - 105, 150, 185, 50);
	private Rectangle pauseMenuButton = new Rectangle(Game.WIDTH * Game.scale / 2 - 82, 250, 140, 50);
	private Rectangle changeBGButton = new Rectangle(Game.WIDTH * Game.scale / 2 + 270, 415, 150, 20);

	public Menu() {
	}
	
	public void renderMainMenu(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Font fnt0 = new Font("arial", Font.BOLD, 45);
		Font fnt1 = new Font("arial", Font.BOLD, 35);
		Font fnt2 = new Font("arial", Font.BOLD, 15);
		
		g.setFont(fnt0);
		g.setColor(Color.WHITE);
		g.drawString("Galactic Invasion", Game.WIDTH * Game.scale / 2 - 180, 75);
		
		g.setFont(fnt1);
		g.drawString("PLAY", playButton.x + 5, playButton.y + 37);
		g.drawString("ENDLESS", endlessButton.x + 5, endlessButton.y + 37);
		g.drawString("QUIT", quitButton.x + 10, quitButton.y + 37);
		
		g.setFont(fnt2);
		g.drawString("Change Background", changeBGButton.x + 3, changeBGButton.y + 15);
		g.drawString("High Score: " + String.valueOf(Game.trueHighScore), 20, 425);
		g2d.draw(playButton);
		g2d.draw(endlessButton);
		g2d.draw(quitButton);
		g2d.draw(changeBGButton);
	}
	
	public void renderEndMenu(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Font fnt0 = new Font("arial", Font.BOLD, 45);
		Font fnt1 = new Font("arial", Font.BOLD, 35);
		
		g.setFont(fnt0);
		g.setColor(Color.WHITE);
		g.drawString("GAME ENDED",  Game.WIDTH * Game.scale / 2 - 170, 100);
		
		g.setFont(fnt1);
		g.drawString("<-MENU", menuButton.x + 5, menuButton.y + 37);
		g2d.draw(menuButton);
	}
	
	public  void renderLevelComp(Graphics g) {
		Font fnt0 = new Font("arial", Font.BOLD, 45);
		
		g.setFont(fnt0);
		g.setColor(Color.WHITE);
		g.drawString("LEVEL COMPLETED!",  Game.WIDTH * Game.scale / 2 - 235, 220);
		
	}
	public void renderPauseMenu(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Font fnt1 = new Font("arial", Font.BOLD, 35);
		
		g.setFont(fnt1);
		g.setColor(Color.WHITE);
		g.drawString("CONTINUE",  contButton.x + 5, contButton.y + 37);
		g.drawString("<-MENU",  pauseMenuButton.x + 5 , pauseMenuButton.y + 37);
		
		g2d.draw(contButton);
		g2d.draw(pauseMenuButton);
	}

}
