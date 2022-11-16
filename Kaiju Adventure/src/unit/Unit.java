package unit;

import java.awt.image.BufferedImage;

public class Unit {

	//Attribute
	public int posX, posY;
	public int speed, diagonalspeed, animationspeed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String richtung;
	public int spriteCounter = 0;
	public int spriteNum = 1;
}
