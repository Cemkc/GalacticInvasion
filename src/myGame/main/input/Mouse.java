package myGame.main.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import myGame.main.Game;
import myGame.main.STATES;
import myGame.main.levelDesigner;

public class Mouse implements MouseListener{
	private int rectangleXvalue;

	public Mouse() {
		rectangleXvalue = Game.WIDTH * Game.scale / 2;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
//		private Rectangle playButton = new Rectangle(Game.WIDTH * Game.scale / 2 - 62, 125, 100, 50);
//		private Rectangle endlessButton = new Rectangle(Game.WIDTH * Game.scale / 2 - 97, 225, 172, 50);
//		private Rectangle quitButton = new Rectangle(Game.WIDTH * Game.scale / 2 - 62, 325, 100, 50);
//		private Rectangle menuButton = new Rectangle(Game.WIDTH * Game.scale / 2 - 82, 225, 132, 50);
//		private Rectangle contButton = new Rectangle(Game.WIDTH * Game.scale / 2 - 105, 150, 185, 50);
//		private Rectangle pauseMenuButton = new Rectangle(Game.WIDTH * Game.scale / 2 - 82, 250, 140, 50);
//		private Rectangle changeBGButton = new Rectangle(Game.WIDTH * Game.scale / 2 + 270, 415, 150, 20);
		
		if(Game.State == STATES.MENU) {
			int msX = e.getX();
			int msY = e.getY();
			if(msX > (rectangleXvalue - 62) && msX < rectangleXvalue -62 + 100) {//play
				if(msY > 125 && msY < 175) { 
					Game.setState(STATES.LEVEL1);
				}
			}
			if(msX > (rectangleXvalue - 97) && msX < rectangleXvalue -97 + 172) {//endless
				if(msY > 225 && msY < 275) { 
					Game.setState(STATES.ENDLESS);
				}
			}
			if(msX > (rectangleXvalue - 62) && msX < rectangleXvalue -62 + 100) {//quit
				if(msY > 325 && msY < 375) { 
					System.exit(0);
				}
			}
			if(msX > (rectangleXvalue + 270) && msX < rectangleXvalue + 270 + 150) {//change background
				if(msY > 415 && msY < 435) {
					if(Game.backgroundSelector == 1) {
						Game.backgroundSelector = 2;
					}
					else {
						Game.backgroundSelector = 1;
					}
				}
			}
		}
		else if(Game.State == STATES.END) {
			int msX = e.getX();
			int msY = e.getY();
			if(msX > (rectangleXvalue - 82) && msX < rectangleXvalue -82 + 132) {//back to menu
				if(msY > 225 && msY < 275) { 
					Game.setState(STATES.MENU);
				}
			}
		}
		else if(Game.State == STATES.PAUSE) {
			int msX = e.getX();
			int msY = e.getY();
			if(msX > (rectangleXvalue - 105) && msX < rectangleXvalue -105 + 182) {//continue
				if(msY > 150 && msY < 200) { 
					if(Game.currentLevel == 1) Game.setState(STATES.LEVEL1);
					if(Game.currentLevel == 2) Game.setState(STATES.LEVEL2);
					if(Game.currentLevel == 3) Game.setState(STATES.LEVEL3);
					if(Game.currentLevel == 4) Game.setState(STATES.ENDLESS);
				}
			}
			if(msX > (rectangleXvalue - 82) && msX < rectangleXvalue -82 + 140) {//back to menu
				if(msY > 250 && msY < 300) { 
					Game.setState(STATES.MENU);
					Game.mainCharacter.setX(0);
					Game.mainCharacter.setY(0);
					Game.Bullets.clear();
					Game.EnemyList.clear();
					levelDesigner.spawnedEnemyNum = 0;
					levelDesigner.highScore = 0;
					Game.HP.clear();
					Game.BarrierHP.clear();
					Game.initBars();
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
