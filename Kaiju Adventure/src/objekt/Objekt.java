package objekt;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameLoop;

public class Objekt {

	public BufferedImage image;
	public String name;
	public boolean isCollision = false;
	public int posX, posY;
	
	//Draw Objekte
	public void draw(Graphics g2, GameLoop gl) {
		
		int scX = posX - gl.player.posX + gl.player.camX;
		int scY = posY - gl.player.posY + gl.player.camY;
		

		g2.drawImage(image, scX, scY,gl.unitsize, gl.unitsize, null);
	}
	
	//Draw Objekt wenn Building
	public void drawBuilding(Graphics2D g2, GameLoop gl) {
	
		int scX = posX - gl.player.posX + gl.player.camX;
		int scY = posY - gl.player.posY + gl.player.camY;
		

		g2.drawImage(image, scX, scY,gl.buildingsize, gl.buildingsize, null);
	}
	
}
