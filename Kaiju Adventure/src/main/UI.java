package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.util.Iterator;

import objekt.Key;
import objekt.Schaufel;

public class UI {

	GameLoop gl;
	Font arial40;
	BufferedImage key, schaufel;
	public boolean messageOn = false;
	public String message = "";
	int counter, countermax;
	public boolean spielbeendet = false;
	public String currentDialog = "";
	
	public UI(GameLoop gl) {
		this.gl = gl;
		arial40 = new Font("Arial", Font.PLAIN, 40);
		Key key = new Key();
		this.key = key.image;
		
		Schaufel schaufel = new Schaufel();
		this.schaufel = schaufel.image;
	}
	
	public void showMessage(String text, int countermax) {
		message = text;
		messageOn = true;
		this.countermax = countermax;
	}
	public void draw(Graphics2D g2) {
		
		if (spielbeendet == true) {
			
		}
		else {
			//zeigt Schlüssel-anzahl
			g2.setFont(arial40);
			g2.setColor(Color.white);
			g2.drawImage(key, 25, 25, gl.unitsize, gl.unitsize, null);
			g2.drawString("x " + gl.player.amountKey, 75, 65);
			
			//zeigt Schaufel an wenn in besitz
			if (gl.player.hatSchaufel == true) {
				g2.drawImage(schaufel, 25, 85, gl.unitsize, gl.unitsize, null);
			}
		
			//Interaktions Messages
			if(messageOn == true) {
				currentDialog = message;
				drawDialogFenster(g2);
				counter++;
				//wie lang die message bleibt
				if (counter > countermax) {
					messageOn = false;
					counter = 0;
				}
			}
			
			if (gl.gameState == gl.pauseState) {
				drawPauseScreen(g2);
			}
			if (gl.gameState == gl.dialogState) {
				drawDialogFenster(g2);
			}
		}
	}
	
	private void drawDialogFenster(Graphics2D g2) {
			
		//Dialog-Fenster
		int posX = gl.unitsize * 2; 
		int posY = gl.unitsize * 8; 
		int hoehe = gl.screenhoehe / 2 - gl.unitsize * 3; 
		int weite = gl.screenweite - gl.unitsize * 4;
		
		//zeichnet dialog-fenster
		drawSubWindow(posX, posY, weite, hoehe, g2);
		
		//zeichnet text
		posX += gl.unitsize - 25;
		posY += gl.unitsize - 15;
		
		//setzt Dialog in Fenster
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,25F));
		for (String line : currentDialog.split("\n")) {
			g2.drawString(line, posX, posY);
			posY+=30;
		}
		if (gl.gameState == gl.dialogState) {
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
			g2.drawString("press ENTER", 520, 500);
		}
		
	}
	
	public void drawSubWindow(int x, int y, int weite, int hoehe, Graphics2D g2) {
		
		//Schwarzer Kaste
		Color c = new Color(10,10,10,220);
		g2.setColor(c);
		g2.fillRoundRect(x, y, weite, hoehe, 35, 35);
		
		//weißer Rand
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, weite-10, hoehe-10, 25, 25);
		
	}

	public void drawPauseScreen(Graphics2D g2) {
		//Hintergrund
		Color c = new Color(0,0,0,220);
		g2.setColor(c);
		g2.fillRect(0, 0, gl.screenweite + gl.unitsize, gl.screenhoehe * gl.unitsize);
		
		//Pause Text
		String pausetext = "Pause";
		int x = gl.screenweite / 2 - gl.unitsize * 2;
		int y = gl.screenhoehe / 4;
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,70F));
		g2.drawString(pausetext, x, y);
	}
}
