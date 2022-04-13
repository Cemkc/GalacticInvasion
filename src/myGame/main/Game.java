package myGame.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.Timer;

import myGame.main.input.Keyboard;
import myGame.main.input.Mouse;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = -4299026826047166818L;

	public static final int WIDTH = 300, HEIGHT = WIDTH / 16 * 9;
	public static int scale = 3;
	protected static int frames;
	
	private Thread thread;
	Timer enemyHandler;
	public static Keyboard key;
	private Mouse mouse;
	private boolean running = false;
	
	public static Player mainCharacter;
	public static ArrayList<Mobs> EnemyList;
	public static ArrayList<Materials> Bullets;
	public static ArrayList<Materials> HP;
	public static ArrayList<Materials> BarrierHP;
	public static ArrayList<levelDesigner> Levels;
	private Menu menu;
	public static int trueHighScore = 0;
	
	public static int passedEnemyCounter;
	
//	private Screen screen;
	
	private Graphics graphs;
	
	ActionListener al;
	
	public static STATES State;
	
//	private String BulletImage = "resource/BulletImage.png";
//	private String EnemyShipImage = "resource/EnemyShip.png";
	private static String HPimage = "resource/HPimage.png";
	private static String BarrierImage = "resource/BarrierLogo.png";
	private String BackgroundImage = "resource/Background.jpg";
	private String BackgroundImage2 = "resource/Background2.jpg";
	private String SpaceShipImage = "resource/SpaceShip.png";
	public static int backgroundSelector = 0;
	
	
	private BufferedImage backgroundImage;
	private BufferedImage backgroundImage2;
//	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game() {
		new Window(WIDTH, HEIGHT, scale, "Galactic Invasion", this);
//		screen = new Screen(WIDTH, HEIGHT);
		
		key = new Keyboard();
		mouse = new Mouse();
		
		addKeyListener(key);
		addMouseListener(mouse);
		
		menu = new Menu();
		
		State = STATES.MENU;
		
		mainCharacter = new Player(1,2,SpaceShipImage,ID.player);
		Bullets = new ArrayList<Materials>();
		HP = new ArrayList<Materials>();
		BarrierHP = new ArrayList<Materials>();
		EnemyList = new ArrayList<Mobs>();
		
		initBars();
		
		Levels = new ArrayList<levelDesigner>();
		Levels.add(new levelDesigner("LEVEL1", 5));
		Levels.add(new levelDesigner("LEVEL2", 10));
		Levels.add(new levelDesigner("LEVEL3", 15));
		Levels.add(new levelDesigner("ENDLESS", 0));
		
		try {
			backgroundImage = ImageIO.read(new FileImageInputStream(new File(BackgroundImage)));
			backgroundImage2 = ImageIO.read(new FileImageInputStream(new File(BackgroundImage2)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
	}
	

	public synchronized void start() {
		thread = new Thread(this, "Game Loop");
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				update();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				new Window();
				frames = 0;
			}
		}
		stop();
	}
	
	long levelCompScreenTime = 0; 
	public static int currentLevel = 0;
	boolean loopFirstTime = true;
	private void update() {
		if(State == STATES.MENU) {
			File highScoresFile = new File("HighScores/highScores.txt");
			Scanner myReader;
			try {
				myReader = new Scanner(highScoresFile);
				String highScoreOnFile = myReader.nextLine();
				try {
					trueHighScore = Integer.valueOf(highScoreOnFile);
				}catch(Exception e) {
					trueHighScore = 0;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else if(State == STATES.LEVEL1) {
			currentLevel = 1;
			Levels.get(0).update(5000, 1500);
			if(Levels.get(0).isLevelComplete() || key.ctrl) {
				Levels.get(0).resetElements();
				setState(STATES.LEVELCOMP);
			}
			loopFirstTime = true;
		}
		else if(State == STATES.LEVEL2) {
			currentLevel = 2;
			Levels.get(1).update(2500, 1000);
			if(Levels.get(1).isLevelComplete() || key.ctrl) {
				Levels.get(1).resetElements();
				setState(STATES.LEVELCOMP);
			}
			loopFirstTime = true;
		}
		else if(State == STATES.LEVEL3) {
			currentLevel = 3;
			Levels.get(2).update(1000, 750);
			if(Levels.get(2).isLevelComplete() || key.ctrl) {
				Levels.get(2).resetElements();
				setState(STATES.END);
			}
			loopFirstTime = true;
		}
		else if(State == STATES.ENDLESS) {
			currentLevel = 4;
			Levels.get(3).update(4000, 1500);
		}
		else if(State == STATES.LEVELCOMP) {
			long currentTime = System.currentTimeMillis();
			if(loopFirstTime) {
				levelCompScreenTime = currentTime;
			}
			if(currentTime - levelCompScreenTime > 2000) {
				if(currentLevel == 1) setState(STATES.LEVEL2);
				else if(currentLevel == 2) setState(STATES.LEVEL3);
				levelCompScreenTime = currentTime;
			}
			loopFirstTime = false;
		}
		
		
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		graphs = bs.getDrawGraphics();
		
		if(backgroundSelector == 1) {
			graphs.drawImage(backgroundImage2, 0, 0, getWidth(), getHeight(), null);
		}
		else {
			graphs.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		}
			
		if(State == STATES.MENU) {
			menu.renderMainMenu(graphs);
		}
		else if(State == STATES.LEVELCOMP) {
			menu.renderLevelComp(graphs);
		}
		else if(State == STATES.END) {
			menu.renderEndMenu(graphs);
		}
		else if(State == STATES.PAUSE) {
			menu.renderPauseMenu(graphs);
		}
		else{						
			mainCharacter.render(this.graphs, mainCharacter.getX(), mainCharacter.getY());
			
			for(Materials bullet: Bullets) {
				bullet.render(this.graphs, bullet.getX(), bullet.getY());
			}
			
			for(Mobs enemy: EnemyList) {
				enemy.render(this.graphs, enemy.getX(), enemy.getY());
			}
			
			for (Materials hp : HP) {
				hp.render(this.graphs, hp.getX(), hp.getY());
			}
			
			for (Materials barrier : BarrierHP) {
				barrier.render(this.graphs, barrier.getX(), barrier.getY());
			}
			graphs.setColor(Color.GREEN);
			graphs.fillRect(getWidth() - 90, 75, levelDesigner.boost / 2, 10);
			if(State == STATES.ENDLESS) {
				Font fnt0 = new Font("arial", Font.BOLD, 15);
				graphs.setFont(fnt0);
				graphs.setColor(Color.WHITE);
				String scoreMessage = "Score: " + String.valueOf(levelDesigner.highScore);
				graphs.drawString(scoreMessage, 10, 20);
			}
		}
		
		
		graphs.dispose();
		bs.show();
	}
	
	public static void initBars() {
		for (int i = 0; i < 3; i++) {
			HP.add(new Materials((WIDTH - 6) * scale - ((i+1) * 30), 5, HPimage, null));
		}
		
		for (int i = 0; i < 3; i++) {
			BarrierHP.add(new Materials((WIDTH - 6) * scale - ((i+1) * 30), 40, BarrierImage, null));
		}
	}
	
	public static void setState(STATES s) {
		State = s;
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.requestFocus();
	}
	
	
}
