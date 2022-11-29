package unit;

import java.util.Random;

import main.GameLoop;
import main.ScaleTool;

public class Monster_Snake extends Unit{
	
	ScaleTool sTool = new ScaleTool();
	public int actioncounter = 0;

	public Monster_Snake(GameLoop gl) {
		super(gl);
		
		name = "snake";
		richtung = "down";
		speed = 1;
		maxHealth = 4;
		health = maxHealth;
		isCollision = true;
		animationspeed = 120;
		
		hitbox.x = 3;
		hitbox.y = 8;
		hitbox.width = 42;
		hitbox.height = 40;
		hitboxX = hitbox.x;
		hitboxY = hitbox.y;
		
		getImage();
	}
	
	public void getImage() {
			up1 = sTool.setupImage("monster", "snake_up1", gl.unitsize, gl.unitsize);
			up2 = sTool.setupImage("monster", "snake_up2", gl.unitsize, gl.unitsize);
			down1 = sTool.setupImage("monster", "snake_down1", gl.unitsize, gl.unitsize);
			down2 = sTool.setupImage("monster", "snake_down2", gl.unitsize, gl.unitsize);
			left1 = sTool.setupImage("monster", "snake_left1", gl.unitsize, gl.unitsize);
			left2 = sTool.setupImage("monster", "snake_left2", gl.unitsize, gl.unitsize);
			right1 = sTool.setupImage("monster", "snake_right1", gl.unitsize, gl.unitsize);
			right2 = sTool.setupImage("monster", "snake_right2", gl.unitsize, gl.unitsize);
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
