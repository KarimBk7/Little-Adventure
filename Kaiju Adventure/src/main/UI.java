package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

import objekt.Key;

public class UI {

	GameLoop gl;
	Font arial40;
	BufferedImage key;
	public boolean messageOn = false;
	public String message = "";
	int counter;
	public boolean spielbeendet = false;
	
	public UI(GameLoop gl) {
		this.gl = gl;
		arial40 = new Font("Arial", Font.PLAIN, 40);
		Key key = new Key();
		this.key = key.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	public void draw(Graphics2D g2) {
		
		if (spielbeendet == true) {
			
		}
		else {
		
			g2.setFont(arial40);
			g2.setColor(Color.white);
			g2.drawImage(key, 25, 25, gl.unitsize, gl.unitsize, null);
			g2.drawString("x " + gl.player.amountKey, 75, 65);
		
			if(messageOn == true) {
				g2.setFont(g2.getFont().deriveFont(30F));
				g2.drawString(message, 25, 115);
				counter++;
			
				if (counter == 90) {
					messageOn = false;
					counter = 0;
				}
			}
			if (gl.gameState == gl.pauseState) {
				drawPauseScreen(g2);
			}
		}
	}
	public void drawPauseScreen(Graphics2D g2) {
		
		String pausetext = "Pause";
		int x = gl.screenweite / 2 - gl.unitsize * 2;
		int y = gl.screenhoehe / 4;
		g2.setColor(Color.red);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,70F));
		g2.drawString(pausetext, x, y);
	}
}
