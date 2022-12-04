package unit;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GameLoop;

public class Unit {

	//Attribute
	GameLoop gl;
	public int posX, posY;
	public int speed, animationspeed;
	String dialog[] = new String[20];
	public int dialogIndex = 0;
	public String name = "";
	
	//Attribute für Image
	public BufferedImage image, up, up1, up2, down, down1, down2, left, left1, left2, right, right1, right2;
	public BufferedImage attackup1, attackup2, attackdown1, attackdown2, attackleft1, attackleft2, attackright1, attackright2;
	public String richtung, laufen;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public int actioncounter = 0;
	
	//Attribute für Collision
	public Rectangle hitbox = new Rectangle(8, 16, 32, 32);
	public int hitboxX, hitboxY;
	public boolean isCollision = false;
	
	//Attribute für ingame-wert
	public int maxHealth;
	public int health;
	public boolean losthealth, gethealth, immunity, monsterKilled = false;
	
	public Unit(GameLoop gl) {
		this.gl = gl;
	}

	public void speak() {}
	public void draw(Graphics2D g2, GameLoop gameLoop, Unit[] npc, int i) {
		
		int scX = posX - gl.player.posX + gl.player.camX;
		int scY = posY - gl.player.posY + gl.player.camY;
		
		if (immunity) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}
		g2.drawImage(image, scX, scY, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	public void setAction() {}
	public void update() {
		
		setAction();
		
		isCollision = false;
		gl.cc.checkTile(this);
		gl.cc.checkObjekt(this, false);
		gl.cc.checkUnit(this, gl.npc);
		gl.cc.checkUnit(this, gl.monster);
		gl.cc.checkPlayer(this);
		
		if (isCollision == false) {
			
			switch (richtung) {
			case "up": 
				posY -= speed; break;
			case "down":
				posY += speed; break;
			case "left": 
				posX -= speed; break;
			case "right": 
				posX += speed; break;
			}
		}
		
		spriteCounter++;
		if (spriteCounter > animationspeed) {
			if (spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
}
