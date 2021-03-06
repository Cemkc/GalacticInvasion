package myGame.main.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	private static boolean[] keys = new boolean[120];
	public boolean up, down, left, right, shift, space, ctrl, esc;
	
	public void update(){
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		shift = keys[KeyEvent.VK_SHIFT];
		space = keys[KeyEvent.VK_SPACE];
		esc = keys[KeyEvent.VK_ESCAPE];
		ctrl = keys[KeyEvent.VK_CONTROL];
		
//		System.out.println("up: " + up + ", down: " + down + ", space: " + space);
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true; 		 
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

}
