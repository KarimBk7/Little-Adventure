package unit;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Unit {

	//Attribute
	public int posX, posY;
	public int speed, diagonalspeed, animationspeed;
	
	//Attribute für Image
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String richtung, laufen;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	//Attribute für Collision
	public Rectangle hitbox;
	public int hitboxX, hitboxY;
	public boolean iscollision = false;
}
