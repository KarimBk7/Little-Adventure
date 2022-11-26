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
		
		if (obj[i].name == "house" && i != 9) {
			g2.drawImage(image, scX, scY,gl.buildingsize, gl.buildingsize, null);
		}
		else if(obj[i].name == "closeddoor") {
			g2.drawImage(image, scX, scY,gl.unitsize * 2, gl.unitsize * 2, null);
		}
		else if(obj[i].name == "dach") {
			g2.drawImage(image, scX, scY,gl.unitsize * 20, gl.unitsize * 20, null);
		}
		else if(obj[i].name == "pier") {
			g2.drawImage(image, scX, scY,gl.unitsize * 2, gl.unitsize * 3, null);
		}
		else if(obj[i].name == "Grabstein") {
			g2.drawImage(image, scX, scY,gl.unitsize, gl.unitsize * 2, null);
		}
		else {
			g2.drawImage(image, scX, scY,gl.unitsize, gl.unitsize, null);
		}
		
	}
	
	
}
