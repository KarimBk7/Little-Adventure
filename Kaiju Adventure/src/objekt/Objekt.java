package objekt;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GameLoop;

public class Objekt {

	public BufferedImage image, image2;
	public String name;
	
	public boolean isCollision = false;
	public int posX, posY;
	
	public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
	public int hitboxX = 0;
	public int hitboxY = 0;
	
	public Objekt() {}
	
	public Objekt(String name, int posX, int posY) {
		this.name = name;
		this.posX = posX;
		this.posY = posY;
	}
	//Draw Objekte
	public void draw(Graphics g2, GameLoop gl, Objekt[] obj, int i) {
		
		int scX = posX - gl.player.posX + gl.player.camX;
		int scY = posY - gl.player.posY + gl.player.camY;
		
		
			g2.drawImage(image, scX, scY, null);
		
		
	}
	
	
}
