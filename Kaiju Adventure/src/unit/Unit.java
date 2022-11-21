package unit;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GameLoop;

public class Unit {

	//Attribute
	GameLoop gl;
	public int posX, posY;
	public int speed, diagonalspeed, animationspeed;
	String dialog[] = new String[20];
	public int dialogIndex = 0;
	
	//Attribute für Image
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String richtung, laufen;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	//Attribute für Collision
	public Rectangle hitbox = new Rectangle(8, 16, 32, 32);
	public int hitboxX, hitboxY;
	public boolean isCollision = false;
	
	public Unit(GameLoop gl) {
		this.gl = gl;
	}

	public void speak() {}
	public void draw(Graphics2D g2, GameLoop gameLoop, Unit[] npc, int i) {
		
		int scX = posX - gl.player.posX + gl.player.camX;
		int scY = posY - gl.player.posY + gl.player.camY;
		
		
		g2.drawImage(down1, scX, scY,gl.unitsize, gl.unitsize, null);
		
	}
}
