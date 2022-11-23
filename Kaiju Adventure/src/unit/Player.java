package unit;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GameLoop;
import main.KeyInput;

public class Player extends Unit{

	KeyInput keyI;
	
	public int camX, camY;
	public int amountKey = 0;
	public boolean hatSchaufel = false;
	public boolean beendet = false;
	boolean playersteht = true;
	
	public Player(GameLoop gl, KeyInput keyI) {
		super(gl);
	
		this.keyI = keyI;
		
		camX = (gl.screenweite / 2) - (gl.unitsize / 2);
		camY = (gl.screenhoehe / 2) - (gl.unitsize / 2);	
		
		hitboxX = hitbox.x;
		hitboxY = hitbox.y;
		
		setTest();
		getPlayerpng();
		
	}
	
	public void setTest() {
		
		//Spieler-Position bei Start
		posX = 55 * gl.unitsize;			
		posY = 63 * gl.unitsize; 
		
		//Spieler- & Animationsgeschwindigkeit
		speed = 4; 
		richtung = "down";
		animationspeed = 16;
		
		//Spieler werte
		maxHealth = 3;
		health = maxHealth;
	}
	
	public void getPlayerpng() {
		
		//speichert Spieler Bilder in BufferdImage
		try {
			
			up = ImageIO.read(getClass().getResourceAsStream("/player/pl_up.png"));
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/pl_up1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/pl_up2.png"));
			down = ImageIO.read(getClass().getResourceAsStream("/player/pl_down.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/pl_down1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/pl_down2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/pl_left1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/pl_left2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/pl_right1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/pl_right2.png"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update ()  {
		
		//Tastenerkennung
		if(keyI.upPressed == true || keyI.downPressed == true || 
				keyI.leftPressed == true || keyI.rightPressed == true) {
			playersteht = false;
			
			if (keyI.upPressed == true) {							
				if (keyI.downPressed == true) {	
					laufen= "stop";									
				}
				else if (keyI.leftPressed == keyI.rightPressed) {	
					richtung = "up";
					laufen = "up";
				}
				else if (keyI.leftPressed == true) {				
					richtung = "up";
					laufen = "upleft";
				}
				else if (keyI.rightPressed == true) {				
					richtung = "up";
					laufen = "upright";
				}
				
			}
			else if (keyI.downPressed == true) {					
				if (keyI.upPressed == true) {						
				}
				else if (keyI.leftPressed == keyI.rightPressed) {	
					richtung = "down";
					laufen = "down";
				}
				else if (keyI.leftPressed == true) {				
					richtung = "down";
					laufen = "downleft";
				}
				else if (keyI.rightPressed == true) {				
					richtung = "down";
					laufen = "downright";
				}
			}
			else if (keyI.leftPressed == true && keyI.rightPressed == true) {	
				laufen = "stop";		
			}
			else if (keyI.rightPressed == true) {																			
					richtung = "right";
					laufen = "right";
			}
			else if (keyI.leftPressed == true) {
				richtung = "left";
				laufen = "left";
			}
			
			
			//check if collision
			isCollision = false;
			gl.cc.checkTile(this);
			int npcindex = gl.cc.checkNpc(this,true);
			int objindex = gl.cc.checkObjekt(this, true);
			interact(objindex);
			interactNpc(npcindex);
			
			//wenn nicht collision dann laufen
			if (isCollision == false) {
				switch (richtung) {
				case "up": posY -= speed;
					break;
				case "left": posX -= speed;
					break;
				case "right": posX += speed;
					break;
				case "down": posY += speed;
					break;
				}
			}
			
			//Animations-Loop-Counter
			spriteCounter++;
			if (spriteCounter > animationspeed) {								//geschwindigkeit der animation. je höher dest langsamer
				if (spriteNum == 1) {
					spriteNum = 2;
				}
				else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		else {
		playersteht = true;
		}
	}
	
	//interargieren mit objekten
	public void interact(int i) {
		
		if (i != 99) {
		
			switch (gl.obj[i].name) {
			
			//Wenn Man Kiste öffnet 
			case "Schaufel":
				if (keyI.enterPressed == true) {
					try {
						gl.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/objekt/openchest.png"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
					gl.ui.showMessage("Schaufel erhalten!", 90);
					gl.npc[0].dialogIndex = 1;
					hatSchaufel = true;
				}
				break;
				
			case "Grabstein":
				if (keyI.enterPressed == true) {		
					gl.ui.showMessage("Ich schaffte es nicht Tenbusch zu besiegen. \nIch vergrub einen Schluessel zu seiner Burg."
							+ " \nDoch nur die Voegel die hoch fliegen koennen, \nkoennen den Hinweis sichten, um ihn zu finden. ", 480);
				}
				break;
				
			//Haufen ausgraben
			case "Haufen":
				if (keyI.enterPressed == true) {
					if (hatSchaufel == true) {
						try {
							gl.gameThread.sleep(200);
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
						gl.obj[i] = null;
						gl.ui.showMessage("Schluessel ausgegraben!", 60);
					}
					else {
						gl.ui.showMessage("Was ist mit der Erde hier?", 90);
					}
				}
				
				break;
				
			//Schlüssel einsammeln	
			case "Key":
				if (keyI.enterPressed == true) {
					gl.obj[i] = null;
					//gl.soundEffekt(1);
					amountKey++;
					gl.ui.showMessage("Schluessel erhalten!", 60);
				}
				break;
				
			//Tür öffnen	
			case "closeddoor":
				if (keyI.enterPressed == true) {
					if(amountKey > 1) {
						//gl.soundEffekt(2);
						try {
							gl.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/Objekt/opendoor.png"));
							gl.obj[i].isCollision = false;
							gl.obj[10] = null;
						} catch (Exception e) {
							// TODO: handle exception
						} 
						gl.ui.showMessage("Tuer geoeffnet! \nMoege der Kampf gegen Tenbusch\nbeginnen!!!", 120);
					}
					else {
						gl.ui.showMessage("Ich benoetige 2 Schlüssel.", 90);
					}
				}
				break;
			}
		}
	}
	
	//interagieren mit npc
	public void interactNpc(int i) {
		
		if (i != 99) {
			if (keyI.enterPressed == true) {
				gl.gameState = gl.dialogState;
				gl.npc[i].speak();
			}
			keyI.enterPressed = false;
		}
	}
	
	
	//zeichnet spieler
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		//Wählt Image zur Richtung
		switch(richtung) {
		case "up":
			if (playersteht) {
				image = up;
			}
			else if(spriteNum == 1) {
				image = up1;
			}
			else if(spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if (playersteht) {
				image = down;
			}
			else if(spriteNum == 1) {
				image = down1;
			}
			else if(spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if (playersteht) {
				image = left;
			}
			else if(spriteNum == 1) {
				image = left1;
			}
			else if(spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if (playersteht) {
				 image = right;
			}
			else if(spriteNum == 1) {
				image = right1;
			}
			else if(spriteNum == 2) {
				image = right2;
			}
			break;
		
		}
		
		g2.drawImage(image, camX, camY, gl.unitsize, gl.unitsize, null);
		
	}
	
}
