package unit;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GameLoop;
import main.KeyInput;

public class Player extends Unit{

	GameLoop gl;
	KeyInput keyI;
	
	public Player(GameLoop gl, KeyInput keyI) {
		this.gl = gl;
		this.keyI = keyI;
		
		setTest();
		getPlayerpng();
	}
	
	public void setTest() {
		
		posX = 100;
		posY = 100; 
		speed = 5;
		richtung = "down";
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
			
			if (keyI.upPressed == true) {		//player bewegt sich nach oben
				richtung = "up";
				posY -= speed;
			}
			else if (keyI.downPressed == true) {//player bewegt sich nach unten
				richtung = "down";
				posY += speed;
			}
			else if (keyI.leftPressed == true) {//player bewegt sich nach links
				richtung = "left";
				posX -= speed;
			}
			else if (keyI.rightPressed == true) {//player bewegt sich nach rechts
				richtung = "right";
				posX += speed;
			}
			
			spriteCounter++;
			if (spriteCounter > 14) {			//geschwindigkeit der animation. je höher dest langsamer
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
		
		g2.drawImage(image, posX, posY, gl.unitsize, gl.unitsize, null);
		
	}
	
}
