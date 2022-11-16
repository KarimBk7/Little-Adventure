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
		
		posX = 100;
		posY = 100; 
		speed = 4;
		diagonalspeed = speed / 2; 
		richtung = "down";
		animationspeed = 16;
	}
	
	public void getPlayerpng() {
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
		
		if(keyI.upPressed == true || keyI.downPressed == true || 
				keyI.leftPressed == true || keyI.rightPressed == true) {
			
			if (keyI.upPressed == true) {							//player bewegt sich nach oben
				if (keyI.downPressed == true) {						//stoppt
					
				}
				else if (keyI.leftPressed == keyI.rightPressed) {	//nach oben
					richtung = "up";
					posY -= speed;
				}
				else if (keyI.leftPressed == true) {				//diagonal nach oben-links
					richtung = "up";
					posY -= diagonalspeed;
					posX -= diagonalspeed;
				}
				else if (keyI.rightPressed == true) {				//diagonal nach oben-rechts
					richtung = "up";
					posY -= diagonalspeed;
					posX += diagonalspeed;
				}
				
			}
			else if (keyI.downPressed == true) {					//player bewegt sich nach unten
				if (keyI.upPressed == true) {						//stoppt
					
				}
				else if (keyI.leftPressed == keyI.rightPressed) {	//nach unten
					richtung = "down";
					posY += speed;
				}
				else if (keyI.leftPressed == true) {				//diagonal nach unten-links
					richtung = "down";
					posY += diagonalspeed;
					posX -= diagonalspeed;
				}
				else if (keyI.rightPressed == true) {				//diagonal nach unten-rechts
					richtung = "down";
					posY += diagonalspeed;
					posX += diagonalspeed;
				}
			}
			else if (keyI.leftPressed == true) {					//player bewegt sich nach links
				if (keyI.rightPressed == true) {					//stoppt
					
				}
				else {												//nach links
					richtung = "left";
					posX -= speed;	
				}
				
			}
			else if (keyI.rightPressed == true) {					//player bewegt sich nach rechts
				if (keyI.leftPressed == true) {						//stoppt
					
				}
				else {												//nach rechts
					richtung = "right";
					posX += speed;
				}

			}
			
			iscollision = false;
			gl.cc.checkTile(this);
			
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
