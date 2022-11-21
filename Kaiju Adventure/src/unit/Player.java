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
		speed = 15;
		diagonalspeed = 15; 
		richtung = "down";
		animationspeed = 16;
	}
	
	public void getPlayerpng() {
		
		//speichert Spieler Bilder in BufferdImage
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/pl_up1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/pl_up2.png"));
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
				/**case "upleft": posY -= diagonalspeed; posX -= diagonalspeed;
					break;
				case "upright": posY -= diagonalspeed; posX += diagonalspeed;
					break;
				case "downleft": posY += diagonalspeed; posX -= diagonalspeed;
					break;
				case "downright": posY += diagonalspeed; posX += diagonalspeed;
					break;
				case "stop":
					break;**/
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
					gl.ui.showMessage("Ich schaffte es nicht Tenbusch zu besiegen. \nIch vergrub einen Schlüssel zu seiner Burg."
							+ " \nDoch nur die Vögel die hoch fliegen können, \nkönnen den Hinweis sichten, um ihn zu finden. ", 480);
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
						gl.ui.showMessage("Schlüssel ausgegraben!", 90);
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
					gl.soundEffekt(1);
					amountKey++;
					gl.ui.showMessage("Schlüssel erhalten!", 90);
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
						gl.ui.showMessage("Tür geöffnet! \nMöge der Kampf gegen Tenbusch\nbeginnen!!!", 120);
					}
					else {
						gl.ui.showMessage("Ich benötige 2 Schlüssel.", 90);
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
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			break;
		
		}
		
		g2.drawImage(image, camX, camY, gl.unitsize, gl.unitsize, null);
		
	}
	
}
