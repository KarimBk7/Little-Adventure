package unit;

import java.awt.Rectangle;
import main.GameLoop;
import main.ScaleTool;

public class Tenbusch extends Unit{

	ScaleTool sTool = new ScaleTool();
	public Rectangle attackhitbox = new Rectangle(24, 24, 96, 120);
	public boolean attacking = false;
	
	public Tenbusch(GameLoop gl) {
		super(gl);
		
		name = "tenbusch";
		richtung = "down";
		speed = 1;
		maxHealth = 15;
		health = maxHealth;
		isCollision = true;
		animationspeed = 120;
		
		
		hitbox.x = 24;
		hitbox.y = 24;
		hitbox.width = (gl.unitsize * 3) - (24 * 2);
		hitbox.height = gl.unitsize * 3 - 24;
		hitboxX = hitbox.x;
		hitboxY = hitbox.y;
		
		getImage();
		}

	
	private void getImage() {
		int scale = 3; 
		
		// movement bilder
		
		up1 = sTool.setupImage("tenbusch", "TenbuschUp1", gl.unitsize* scale, gl.unitsize* scale);
		up2 = sTool.setupImage("tenbusch", "TenbuschUp2", gl.unitsize* scale, gl.unitsize* scale);
		down1 = sTool.setupImage("tenbusch", "TenbuschDown1", gl.unitsize* scale, gl.unitsize* scale);
		down2 = sTool.setupImage("tenbusch", "TenbuschDown2", gl.unitsize* scale, gl.unitsize* scale);
		left1 = sTool.setupImage("player", "pl_left1", gl.unitsize* scale, gl.unitsize* scale);
		left2 = sTool.setupImage("player", "pl_left2", gl.unitsize* scale, gl.unitsize* scale);
		right1 = sTool.setupImage("player", "pl_right1", gl.unitsize* scale, gl.unitsize* scale);
		right2 = sTool.setupImage("player", "pl_right2", gl.unitsize* scale, gl.unitsize* scale);

		// angriffsbilder
		/*attackup1 = sTool.setupImage("player", "attackup1", gl.unitsize* scale, gl.unitsize* (scale + 2));
		attackup2 = sTool.setupImage("player", "attackup2", gl.unitsize* scale, gl.unitsize * (scale + 2));
		attackdown1 = sTool.setupImage("player", "attackdown1", gl.unitsize* scale, gl.unitsize * (scale + 2));
		attackdown2 = sTool.setupImage("player", "attackdown2", gl.unitsize* scale, gl.unitsize * (scale + 2));
		attackleft1 = sTool.setupImage("player", "attackleft1", gl.unitsize * (scale + 2), gl.unitsize* scale);
		attackleft2 = sTool.setupImage("player", "attackleft2", gl.unitsize * (scale + 2), gl.unitsize* scale);
		attackright1 = sTool.setupImage("player", "attackright1", gl.unitsize * (scale + 2), gl.unitsize* scale);
		attackright2 = sTool.setupImage("player", "attackright2", gl.unitsize * (scale + 2), gl.unitsize* scale);
		*/
	}
	
	public void setAction() {
		
		attacking = gl.cc.checkPlayerBoss(this);
		if (attacking) {
			attacking();
		}
		
		
		if (posX + 50 < gl.player.posX) {
			richtung = "right";
		}
		else if (posX - 50 > gl.player.posX) {
			richtung = "left";
		}
		else if (posY + 72 < gl.player.posY) {
			richtung = "down";
		}
		else if (posY - 72 > gl.player.posY) {
			richtung = "up";
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
		
		int tempCamY;
		int tempCamX;
		
		switch(richtung) {
		case "up":
			if (!attacking) {
				if(spriteNum == 1) {image = up1;}
				else if(spriteNum == 2) {image = up2;}
			}
			else {
				//tempCamY = camY - gl.unitsize;
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}
			}
			
			break;
			
		case "down":
			if (!attacking) {
				if(spriteNum == 1) {image = down1;}
				else if(spriteNum == 2) {image = down2;}
			}
			else {
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
			}
			break;
			
		case "left":
			if (!attacking) {
				if(spriteNum == 1) {image = left1;}
				else if(spriteNum == 2) {image = left2;}
			}
			else {
				//tempCamX = camX - gl.unitsize;
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
			}
			break;
			
		case "right":
			if (!attacking) {
				if(spriteNum == 1) {image = right1;}
				else if(spriteNum == 2) {image = right2;}
			}
			else {
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
			}
			break;
		}
		
		
		if (this.immunity) {
			gl.counter[6].count();
		}
		if (gl.counter[6].isDone()) {
			gl.counter[6].resetCount();
		this.immunity = false;
		}
		
	}


	private void attacking() {

		if (spriteCounter <= 15) {
			spriteNum = 1;
		}
		if (spriteCounter > 15 && spriteCounter <= 40) {
			spriteNum = 2;

			// sichert spielers position & hitbox
			int currentPosX = posX;
			int currentPosY = posY;
			int hitboxWith = hitbox.width;
			int hitboxHeigh = hitbox.height;

			// setzt schlag-hitbox
			switch (richtung) {
			case "up":posY -= attackhitbox.height;break;
			case "down":posY += attackhitbox.height;break;
			case "left":posX -= attackhitbox.width;break;
			case "right":posX += attackhitbox.width;break;
			}

			
			
			
			// ersetz hitbox mit attackhitbox
			hitbox.width = attackhitbox.width;
			hitbox.height = attackhitbox.height;

			//checkt wenn spieler getroffen wird
			gl.cc.checkPlayerBoss(this);

			// nach dem checken wird player information wiederhergestellt
			posX = currentPosX;
			posY = currentPosY;
			hitbox.width = hitboxWith;
			hitbox.height = hitboxHeigh;
		}
		/**if (spriteCounter > 40) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}*/
		
		
	}


	private void damageSpieler() {
		// TODO Auto-generated method stub
		
	}
}
