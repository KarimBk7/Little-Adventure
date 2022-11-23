package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
	
	GameLoop gl;
	public boolean upPressed, downPressed, leftPressed, rightPressed,
	jPressed, kPressed, iPressed, pausePressed, enterPressed;

	public KeyInput(GameLoop gl) {
		this.gl = gl;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int eingabe = e.getKeyCode();
			
		if (gl.gameState == gl.titlestate) {
			if (eingabe == KeyEvent.VK_W) {
				gl.ui.befehl--;
				if (gl.ui.befehl < 0) {
					gl.ui.befehl = 2;
				}
			}
			if (eingabe == KeyEvent.VK_S) {
				gl.ui.befehl++;
				if (gl.ui.befehl > 2) {
					gl.ui.befehl = 0;
				}
			}
			if (eingabe == KeyEvent.VK_ENTER) {
				enterPressed = true;
				if (gl.ui.befehl == 0) {
					if (gl.ui.prologabgespielt == false) {
						gl.gameState = gl.prologstate;
						gl.ui.prologabgespielt = true;
					}
					else {
						gl.gameState = gl.playState;
					}
					
					//gl.playMusik(0);
				}
				if (gl.ui.befehl == 1) {
					
				}
				if (gl.ui.befehl == 2) {
					System.exit(0);
				}
			}
		}
		
		if (gl.gameState == gl.prologstate) {
			if (eingabe == KeyEvent.VK_SPACE) {
				gl.gameState = gl.playState;
			}
		}
		if (gl.gameState == gl.playState) {
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
					gl.gameState = gl.pauseState;
			}
			if (eingabe == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}
		}
		
		else if (gl.gameState == gl.pauseState) {
			if (eingabe == KeyEvent.VK_W) {
				gl.ui.befehl--;
				if (gl.ui.befehl < 0) {
					gl.ui.befehl = 1;
				}
			}
			if (eingabe == KeyEvent.VK_S) {
				gl.ui.befehl++;
				if (gl.ui.befehl > 1) {
					gl.ui.befehl = 0;
				}
			}
			if (eingabe == KeyEvent.VK_ENTER) { 
				if (gl.ui.befehl == 0) {
					gl.gameState = gl.playState;
				}
				else if(gl.ui.befehl == 1) {
					gl.gameState = gl.titlestate;
				}
			}	
		}
		else if (gl.gameState == gl.dialogState) {
			if (eingabe == KeyEvent.VK_ENTER) {
				gl.gameState = gl.playState;
			}
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
		if (eingabe == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}

	}

}
