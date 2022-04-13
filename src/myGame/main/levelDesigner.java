package myGame.main;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JPanel;

import myGame.main.input.Keyboard;

public class levelDesigner extends JPanel{
	private static final long serialVersionUID = 1L;

	private String lvlName;
	
	private int PanelHeight = Game.HEIGHT * Game.scale;
	private int PanelWidth = Game.WIDTH * Game.scale;
	
	private Keyboard key = Game.key;
	private Player mainCharacter = Game.mainCharacter;
	private ArrayList<Materials> Bullets = Game.Bullets;
	private ArrayList<Mobs> EnemyList = Game.EnemyList;
	private ArrayList<Materials> HP = Game.HP;
	private ArrayList<Materials> BarrierHP = Game.BarrierHP;
	
	public static int boost = 200;
	public static int totalSpentBullet = 0;
	private long boostRefillDelay = 0;
	private int bulletSpeedX = 5;
	private int bulletSpeedY = 0;
	private int characterSpeed = 2;
	private int characterRunningSpeed = 4;
	private int enemySpeedX = -2;
	private int enemySpeedY = 0;
	public static int spawnedEnemyNum = 0;
	public static int highScore = 0;
	private int maximumEnemySpawn;
	
	long lastShotTime = 0;
	long mobSpawnTime = 0;
	long lastEnemyShot = 0;
	long highScoreRefresh = 0;
	boolean isFireReady = true;
	
	private String BulletImage = "resource/BulletImage.png";
	private String EnemyShipImage = "resource/EnemyShip.png";
	
	Random random = new Random();
	
	File highScoreFile = new File("HighScores/highScores.txt");

	public levelDesigner(String lvlName, int maximumEnemySpawn) {
		this.lvlName = lvlName;
		this.maximumEnemySpawn = maximumEnemySpawn;
		
	}
	
	public void update(int MobSpawnFreq, int EnemyFireRate) {
		key.update();
		mainCharacter.setVelY(characterSpeed);
		mainCharacter.setVelX(characterSpeed);
		long currentTime = System.currentTimeMillis();
		if(key.shift) {
			if(boost > 0) {
				mainCharacter.setVelX(characterRunningSpeed);
				mainCharacter.setVelY(characterRunningSpeed);
				boost = boost - 5;
			}
			boostRefillDelay = currentTime;
		}
		else{
			if(currentTime - boostRefillDelay > 500) {
				boost = boost + 4;
			}
			if(boost > 200) {
				boost = 200;
			}
		}
		if(key.up && mainCharacter.getY() > 0) {
			mainCharacter.setY(mainCharacter.getY() - mainCharacter.getVelY());
		}
		if(key.down && mainCharacter.getY() < PanelHeight - 105) {
			mainCharacter.setY(mainCharacter.getY() + mainCharacter.getVelY());
		}
		if(key.left && mainCharacter.getX() > 0) {
			mainCharacter.setX(mainCharacter.getX() - mainCharacter.getVelX());
		}
		if(key.right && mainCharacter.getX() < PanelWidth - 75) {
			mainCharacter.setX(mainCharacter.getX() + mainCharacter.getVelX());
		}
		if(currentTime - lastShotTime > 500) {
			isFireReady = true;
		}
		if(key.space) {
			if(isFireReady) {
				Bullets.add(new Materials(mainCharacter.getX() + 65, mainCharacter.getY() + 32, BulletImage, ID.player));
				lastShotTime = currentTime;
				isFireReady = false;
			}
		}		
		for(Materials bullet: Bullets) {
			bullet.setVelX(bulletSpeedX);
			bullet.setVelY(bulletSpeedY);
			if(bullet.getId() == ID.player) {
				bullet.setX(bullet.getX() + bullet.getVelX());
				bullet.setY(bullet.getY() + bullet.getVelY());
			}
			if(bullet.getId() == ID.enemy) {
				bullet.setX(bullet.getX() - bullet.getVelX());
				bullet.setY(bullet.getY() + bullet.getVelY());
			}
		}
		
		if(currentTime - mobSpawnTime > MobSpawnFreq) {
			if(spawnedEnemyNum < maximumEnemySpawn || this.getName() == "ENDLESS") {
				EnemyList.add(new Mobs((random.nextInt(50) + PanelWidth), random.nextInt(PanelHeight - 105), EnemyShipImage, ID.enemy));
			}
			spawnedEnemyNum++;
			mobSpawnTime = currentTime;
		}
		
		int lastEnemyCheck = 0;
		for (Mobs enemy : EnemyList) {
			enemy.setVelX(enemySpeedX);
			enemy.setVelY(enemySpeedY);
			enemy.setX(enemy.getX() + enemy.getVelX());
			enemy.setY(enemy.getY() + enemy.getVelY());
			if(currentTime - lastEnemyShot > EnemyFireRate) {
				Bullets.add(new Materials(enemy.getX() - 50, enemy.getY() + 20, BulletImage, ID.enemy));
				if(lastEnemyCheck >= EnemyList.size() - 1) {
					lastEnemyShot = currentTime;
				}
			}
			lastEnemyCheck++;
		}
		
		for (int i = 0; i < Bullets.size(); i++) {
			if(Bullets.get(i).getX() > PanelWidth || Bullets.get(i).getX() < -35) {
				Bullets.remove(i);
				break;
			}
		}
		
		for (int i = 0; i < EnemyList.size(); i++) {
			if(EnemyList.get(i).getX() < -65) {
				EnemyList.remove(i);
				int size = BarrierHP.size();
				if(size > 0) {
					BarrierHP.remove(size - 1);
				}
			}
		}
		collisionDetector();
		
		if (HP.size() == 0) {
			if(Game.State == STATES.ENDLESS) {
				try {
					Scanner myReader = new Scanner(highScoreFile);
					int highScoreOnFile;
					try {
						highScoreOnFile = Integer.valueOf(myReader.nextLine());
					}catch(Exception e) {
						highScoreOnFile = 0; 
					}
					myReader.close();
					if(highScore > highScoreOnFile) {
						try {
							FileWriter myWriter = new FileWriter(highScoreFile);
							myWriter.write(String.valueOf(highScore));
							myWriter.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			Game.State = STATES.END;
			resetElements();
		}
		
		if (BarrierHP.size() == 0) {
			Game.State = STATES.END;
			resetElements();
		}
		
		if(this.getName() == "ENDLESS") {
			if(currentTime - highScoreRefresh > 250) {
				highScore += 1;
				highScoreRefresh = currentTime;
			}
		}
		
		if(key.esc) {
			Game.setState(STATES.PAUSE);
		}
	
	}
	
	private void collisionDetector() {
		for (int j = 0; j < Bullets.size(); j++) {
			for (int i = 0; i < EnemyList.size(); i++) {
				if(new Rectangle(EnemyList.get(i).getX(), EnemyList.get(i).getY(), 65, 65).intersects(new Rectangle(Bullets.get(j).getX(), Bullets.get(j).getY(), 35, 15))) {				
					EnemyList.remove(i);
				}
			}
			if(new Rectangle(mainCharacter.getX(), mainCharacter.getY(), 65, 65).intersects(new Rectangle(Bullets.get(j).getX(), Bullets.get(j).getY(), 35, 15))) {
				Bullets.remove(j);
				if(HP.size() > 0) {
					HP.remove(HP.size() - 1);
				}
			}
		}
	}
	
	public void resetElements() {
		mainCharacter.setX(0);
		mainCharacter.setY(0);
		Bullets.clear();
		EnemyList.clear();
		spawnedEnemyNum = 0;
		highScore = 0;
		HP.clear();
		BarrierHP.clear();
		Game.initBars();
	}
	
	public String getName() {
		return lvlName;
	}
	
	public boolean isLevelComplete() {
		if(EnemyList.isEmpty() && spawnedEnemyNum >= maximumEnemySpawn) {
			return true;
		}
		else {
			return false;
		}
	}


}
