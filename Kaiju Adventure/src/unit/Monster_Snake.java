package unit;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GameLoop;

public class Monster_Snake extends Unit{
	
	public int actioncounter = 0;

	public Monster_Snake(GameLoop gl) {
		super(gl);
		
		name = "snake";
		richtung = "down";
		speed = 1;
		maxHealth = 4;
		health = maxHealth;
		isCollision = true;
		
		hitbox.x = 3;
		hitbox.y = 8;
		hitbox.width = 42;
		hitbox.height = 40;
		hitboxX = hitbox.x;
		hitboxY = hitbox.y;
		
		getImage();
	}
	
	public void getImage() {
		
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/monster/snake_left.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/monster/snake_left.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/monster/snake_right.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/monster/snake_right.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/monster/snake_left.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/monster/snake_left.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/monster/snake_right.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/monster/snake_right.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setAction() {
		
		actioncounter ++;
		
		if (actioncounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1;
			
			if (i <= 25) {
				richtung = "up";
			}
			else if (i <= 50) {
				richtung = "down";
			}
			else if (i < 75) {
				richtung = "left";
			}
			else if (i <= 100) {
				richtung = "right";
			}
			actioncounter = 0;
			
		}
		
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
		
		switch(richtung) {
		case "up":
			if(spriteNum == 1) {image = up1;}
			else if(spriteNum == 2) {image = up2;}
			break;
			
		case "down":
			if(spriteNum == 1) {image = down1;}
			else if(spriteNum == 2) {image = down2;}
			break;
			
		case "left":
			if(spriteNum == 1) {image = left1;}
			else if(spriteNum == 2) {image = left2;}
			break;
			
		case "right":
			if(spriteNum == 1) {image = right1;}
			else if(spriteNum == 2) {image = right2;}
			break;
		}
		
		if (this.immunity) {
			gl.counter[4].count();
		}
		if (gl.counter[4].isDone()) {
			gl.counter[4].resetCount();
		this.immunity = false;
		}
	}
}
