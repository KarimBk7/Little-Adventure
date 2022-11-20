package unit;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GameLoop;
import main.KeyInput;

public class Player extends Unit{

	GameLoop gl;
	KeyInput keyI;
	
	public int camX, camY;
	
	public Player(GameLoop gl, KeyInput keyI) {
		this.gl = gl;
		this.keyI = keyI;
		
		camX = (gl.screenweite / 2) - (gl.unitsize / 2);
		camY = (gl.screenhoehe / 2) - (gl.unitsize / 2);	
		
		hitbox = new Rectangle(8, 8, 32, 32);
		
		setTest();
		getPlayerpng();
		
	}
	
	public void setTest() {
		
		//Spieler-Position bei Start
		posX = 16 * gl.unitsize;			
		posY = 22 * gl.unitsize; 
		
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
			iscollision = false;
			gl.cc.checkTile(this);
			
			//wenn nicht collision dann laufen
			if (iscollision == false) {
				switch (laufen) {
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
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		//Wählt Image zur Rischtung
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
		
		//zeichnet Player
		g2.drawImage(image, camX, camY, gl.unitsize, gl.unitsize, null);
		
	}
	
}
