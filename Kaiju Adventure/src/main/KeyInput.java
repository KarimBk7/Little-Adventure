package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
	
	public boolean upPressed, downPressed, leftPressed, rightPressed,
	jPressed, kPressed, iPressed, pausePressed;

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int eingabe = e.getKeyCode();
			
		if (eingabe == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (eingabe == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (eingabe == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (eingabe == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (eingabe == KeyEvent.VK_J) {
			jPressed = true;
		}
		if (eingabe == KeyEvent.VK_K) {
			kPressed = true;
		}
		if (eingabe == KeyEvent.VK_P) {
			pausePressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int eingabe = e.getKeyCode();
		
		if (eingabe == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (eingabe == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (eingabe == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (eingabe == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (eingabe == KeyEvent.VK_J) {
			jPressed = false;
		}
		if (eingabe == KeyEvent.VK_K) {
			kPressed = false;
		}
		if (eingabe == KeyEvent.VK_P) {
			pausePressed = false;
		}

	}

}
