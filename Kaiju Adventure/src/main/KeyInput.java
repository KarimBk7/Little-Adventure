package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
	
	GameLoop gl;
	public boolean upPressed, downPressed, leftPressed, rightPressed,
	jPressed, kPressed, iPressed, pausePressed, enterPressed;
	
	//shop
	public int speed = 1;
	public int strenght = 1;
	public int spitzhacke = 1;

	public KeyInput(GameLoop gl) {
		this.gl = gl;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int eingabe = e.getKeyCode();
			
		//Wenn Titelbildschirm
		if (gl.gameState == gl.titlestate) {
			if (eingabe == KeyEvent.VK_W) {
				gl.ui.befehl--;
				if (gl.ui.befehl < 0) {
					gl.ui.befehl = 2;
				}
				gl.soundEffekt(1);
			}
			if (eingabe == KeyEvent.VK_S) {
				gl.ui.befehl++;
				if (gl.ui.befehl > 2) {
					gl.ui.befehl = 0;
				}
				gl.soundEffekt(1);
			}
			if (eingabe == KeyEvent.VK_ENTER) {
				enterPressed = true;
				//Spiel Starte
				if (gl.ui.befehl == 0) {
					if (gl.ui.prologabgespielt == false) {
						gl.gameState = gl.prologstate;
						gl.ui.prologabgespielt = true;
					}
					else {
						enterPressed = false;
						gl.gameState = gl.playState;
						//gl.playMusik(0);
					}
				}
				//Lade Spiel
				if (gl.ui.befehl == 1) {
					
				}
				//Beende Spiel
				if (gl.ui.befehl == 2) {
					System.exit(0);
				}
			}
		}
		
		//losebildschirm
		else if(gl.gameState == gl.losestate) {
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
				enterPressed = true;
				//Spiel Starte
				if (gl.ui.befehl == 0) {
					
					//TODO	
				}
				else if (gl.ui.befehl == 1){
					gl.ui.befehl = 0;
					for (int i = 0; i < gl.counter.length && gl.counter[i] != null; i++) {
							gl.counter[i].removeCount();
					}
					gl.player.health = gl.player.maxHealth;
					gl.player.setTest();
					gl.gameState = gl.titlestate;
				}
			}
		}

		//Wenn Prologbildschirm
		else if (gl.gameState == gl.prologstate) {
			if (eingabe == KeyEvent.VK_ENTER) {
				gl.gameState = gl.playState;
				//gl.playMusik(0);
			}
		}
		
		//Wenn Spielbildschirm
		else if (gl.gameState == gl.playState) {
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
					gl.stopMusik();
			}
			if (eingabe == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}
		}
		
		//Shopbildschirm
		else if(gl.gameState == gl.shopState) {
			if (eingabe == KeyEvent.VK_A) {
				gl.ui.befehl--;
				if (gl.ui.befehl < 0) {
					gl.ui.befehl = 3;
				}
				gl.soundEffekt(1);
			}
			if (eingabe == KeyEvent.VK_D) {
				gl.ui.befehl++;
				if (gl.ui.befehl > 3) {
					gl.ui.befehl = 0;
				}
				gl.soundEffekt(1);
			}
			if (eingabe == KeyEvent.VK_W) {
				gl.ui.befehl = 0;
				
				gl.soundEffekt(1);
			}
			if (eingabe == KeyEvent.VK_S) {
				gl.ui.befehl = 4;
				gl.soundEffekt(1);
			}
			if (eingabe == KeyEvent.VK_ENTER) {
				if (gl.ui.befehl == 0) {
					if (gl.player.itDollar > 99) {
					gl.player.healing_potion++;
					gl.soundEffekt(7);
					gl.player.itDollar -= 100;
					}
					else {
						gl.ui.keinGeld = true;
						gl.soundEffekt(8);
					}
				}
				if (gl.ui.befehl == 1) {
					if (gl.player.itDollar > 99 && strenght > 0) {
						gl.player.strenght++;
						gl.soundEffekt(7);
						strenght--;
						gl.player.itDollar -= 100;
					}
					else if(strenght < 1){
						gl.ui.ausverkauft = true;
						gl.soundEffekt(8);
					}
					else if (gl.player.itDollar < 100) {
						gl.ui.keinGeld = true;
						gl.soundEffekt(8);
					}
					
				}
				if (gl.ui.befehl == 2) {
					if (gl.player.itDollar > 99 && speed > 0) {
						gl.player.speed++;
						gl.soundEffekt(7);
						speed--;
						gl.player.itDollar -= 100;
					}
					else if (speed < 1){
						gl.ui.ausverkauft = true;
						gl.soundEffekt(8);
					}
					else if (gl.player.itDollar < 100) {
						gl.ui.keinGeld = true;
						gl.soundEffekt(8);
					}
				}
				if (gl.ui.befehl == 3) {
					if (spitzhacke > 0) {
						gl.player.hatSchaufel = true;
						gl.soundEffekt(7);
						spitzhacke--;
					}
					else if (spitzhacke < 1){
						gl.ui.ausverkauft = true;
						gl.soundEffekt(8);
					}
				}
				if (gl.ui.befehl == 4) {
					gl.gameState = gl.playState;
				}
			}
		}
		
		//Wenn Pausebildschirm
		else if (gl.gameState == gl.pauseState) {
			if (eingabe == KeyEvent.VK_W) {
				gl.ui.befehl--;
				if (gl.ui.befehl < 0) {
					gl.ui.befehl = 1;
				}
				gl.soundEffekt(1);
			}
			if (eingabe == KeyEvent.VK_S) {
				gl.ui.befehl++;
				if (gl.ui.befehl > 1) {
					gl.ui.befehl = 0;
				}
				gl.soundEffekt(1);
			}
			if (eingabe == KeyEvent.VK_ENTER) { 
				if (gl.ui.befehl == 0) {
					gl.gameState = gl.playState;
					gl.resumeMusik(0);
				}
				else if(gl.ui.befehl == 1) {
					gl.gameState = gl.titlestate;
					gl.ui.befehl = 0;
				}
			}	
		}
		
		//Wenn Dialog-Fenster
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
