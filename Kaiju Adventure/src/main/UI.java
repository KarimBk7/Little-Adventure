package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.plaf.ColorUIResource;

import objekt.Apfel;
import objekt.Heart;
import objekt.Key;
import objekt.Schaufel;

public class UI {

	//Attribute
	Graphics2D g2;
	GameLoop gl;
	Font arial40;
	BufferedImage key, apfel, schaufel, herz, herzleer, prologimg;
	public boolean prologabgespielt = false;
	public boolean spielbeendet = false;
	public boolean messageOn = false;
	public String message = "";
	public String currentDialog = "";
	int counter, countermax;
	public int befehl = 0;
	
	//Konstruktor
	public UI(GameLoop gl) {
		this.gl = gl;
		arial40 = new Font("Arial", Font.PLAIN, 40);
		Key key = new Key();
		this.key = key.image;
		
		Apfel apfel = new Apfel();
		this.apfel = apfel.image;
		
		Schaufel schaufel = new Schaufel();
		this.schaufel = schaufel.image;
		
		Heart heart = new Heart(gl);
		herz = heart.image;
		herzleer = heart.image2;
		
		try {
			prologimg = ImageIO.read(getClass().getResourceAsStream("/objekt/prolog_bg.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showMessage(String text, int countermax) {
		message = text;
		messageOn = true;
		this.countermax = countermax;
	}
	
	public void draw(Graphics2D g2) {
		
		//wenn nicht titel- oder prologbildschirm
		if(gl.gameState != gl.titlestate && gl.gameState != gl.prologstate){
			//Spielt
			if (gl.gameState == gl.playState) {
				
				//zeigt Schlüssel-anzahl
				g2.setFont(arial40);
				g2.setColor(Color.black);
				g2.drawString("x " + gl.player.amountKey, 78, 68);
				g2.setColor(Color.white);
				g2.drawImage(key, 25, 25, gl.unitsize, gl.unitsize, null);
				g2.drawString("x " + gl.player.amountKey, 75, 65);
				if (gl.player.amountApfel > 0) {
					g2.setFont(arial40);
					g2.setColor(Color.black);
					g2.drawString("x " + gl.player.amountApfel, 78, 75 + gl.unitsize);
					g2.setColor(Color.white);
					g2.drawImage(apfel, 25, gl.unitsize + 35, gl.unitsize, gl.unitsize, null);
					g2.drawString("x " + gl.player.amountApfel, 75, 72 + gl.unitsize);
				}
				
				//zeigt Schaufel an wenn in besitz
				if (gl.player.hatSchaufel == true) {
					g2.drawImage(schaufel, 25, 85, gl.unitsize, gl.unitsize, null);
				}
				
				drawPlayerHealth(g2);
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
			
			//wenn Pause gedrückt wird
			if (gl.gameState == gl.pauseState) {
				drawPauseScreen(g2);
			}
			//wenn dialog
			if (gl.gameState == gl.dialogState) {
				drawDialogFenster(g2);
			}
		}
		
		//wenn Titelscreen
		else if(gl.gameState == gl.titlestate){
			drawTitleScreen(g2);
		}
		//prolog
		else if(gl.gameState == gl.prologstate) {
			drawProlog(g2);
		}
	
	}
	
	private void drawProlog(Graphics2D g2) {
		
		g2.drawImage(prologimg, 0, 0, gl.screenweite, gl.screenhoehe, null);
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gl.screenweite, gl.screenhoehe);
		String text = "IT-Dollar.... Sie regieren diese Welt. \n"
					+ "Doch wer regiert die Dollar? \nEs ist kein anderer "
					+ "als Herr Tenbusch \ndie Wurzel allen uebels. \n"
					+ "Mit Hilfe der IT-Dollar hinterzog Herr Tenbusch \n"
					+ "das Finanzamt in Mecklenburg-Vorpommern \n"
					+ "und kam so an die Macht. \n"
					+ "Doch Kaiju hatt es satt und macht sich auf den Weg \n"
					+ "Herr Tenbusch ein fuer alle mal das Gar aus zu machen...";
		
		g2.setColor(Color.white);
		int x = gl.unitsize;
		int y = gl.unitsize * 3 + 30;
		int weite = gl.unitsize * 14;
		int hoehe = gl.unitsize * 6 + 15;
		
		g2.setColor(new Color(0,0,0,150));
		g2.fillRoundRect(x - 5, y - 5, weite + 10, hoehe + 10, 35, 35);
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(4));
		g2.drawRoundRect(x, y, weite, hoehe, 25, 25);
		
		x = gl.unitsize * 2 - 30;
		y = gl.unitsize * 4 + 20;
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,25F));
		for (String line : text.split("\n")) {
			g2.drawString(line, x, y);
			y+=30;
		}
		
	}

	private void drawPlayerHealth(Graphics2D g2) {
		//draw Leere herzen
		int x = 20;
		int y = gl.unitsize * 9;
		int i = 0;
		
			g2.drawImage(herzleer, x, y + 20, gl.unitsize * 3, gl.unitsize * 3, null);
			i++; 
		
		//draw Volle Herzen
		y += gl.unitsize + 20;
		i = 0;
		
		while (i < gl.player.health) {
			g2.drawImage(herz, x, y, gl.unitsize, gl.unitsize, null);
			i++; 
			x+=gl.unitsize + 1;
			
		}
	}

	private void drawTitleScreen(Graphics2D g2) {
		
		//Hintergrund
		g2.fillRect(0, 0, gl.screenweite, gl.screenhoehe);
		
		//titel text
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,60F));
		String text = "Kaiju Adventure";
		int x = gl.unitsize * 3;
		int y = gl.unitsize * 2;
		g2.setColor(Color.black);
		g2.drawString(text, x + 4, y + 4);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		//Spieler
		g2.drawImage(gl.player.down1, gl.unitsize * 6 + 10, gl.unitsize * 4, gl.unitsize * 3, gl.unitsize * 3, null);
		
		
		//Menu
		//Starte Spiel
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
		text = "Starte Spiel";
		x = gl.unitsize * 5 + 20;
		y = gl.unitsize * 8;
		if (befehl == 0) {
			g2.setColor(Color.cyan);
		}
		else {
			g2.setColor(Color.black);
		}
		g2.drawString(text, x + 4, y + 4);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if (befehl == 0) {
			text = ">";
			x -= 40; 
			g2.setColor(Color.black);
			g2.drawString(text, x + 4, y + 4);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
		}
		
		//Lade Spiel
		text = "Lade Spiel";
		x = gl.unitsize * 5 + 30;
		y = gl.unitsize * 9;
		if (befehl == 1) {
			g2.setColor(Color.cyan);
		}
		else {
			g2.setColor(Color.black);
		}
		g2.drawString(text, x + 4, y + 4);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if (befehl == 1) {
			text = ">";
			x -= 40; 
			g2.setColor(Color.black);
			g2.drawString(text, x + 4, y + 4);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
		}
		
		//Verlasse Speil
		text = "Beenden";
		x = gl.unitsize * 6;
		y = gl.unitsize * 10;
		if (befehl == 2) {
			g2.setColor(Color.cyan);
		}
		else {
			g2.setColor(Color.black);
		}
		g2.drawString(text, x + 4, y + 4);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if (befehl == 2) {
			text = ">";
			x -= 40; 
			g2.setColor(Color.black);
			g2.drawString(text, x + 4, y + 4);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
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
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
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
		g2.setColor(new Color(0,0,0,220));
		g2.fillRoundRect(x, y, weite, hoehe, 35, 35);
		
		//weißer Rand
		g2.setColor(new Color(255,255,255));
		g2.setStroke(new BasicStroke(4));
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
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,70F));
		g2.setColor(Color.white);
		g2.drawString(pausetext, x + 4, y + 4);
		g2.setColor(new Color(255,100,100));
		g2.drawString(pausetext, x, y);
		
		//Weiter Spielen
		String text = "Weiter Spielen";
		x = gl.unitsize * 5 + 25;
		y = gl.screenhoehe / 2;
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,40F));
		if (befehl == 0) {
			g2.setColor(Color.cyan);
		}
		else {
			g2.setColor(Color.gray);
		}
		g2.drawString(text, x + 2, y + 2);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if (befehl == 0) {
			text = ">";
			x -= 40; 
			g2.setColor(Color.black);
			g2.drawString(text, x + 2, y + 2);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);			
		}
		
		//Zurück zum Hauptmenu
		text = "Hauptmenu";
		x = gl.unitsize * 6;
		y = gl.screenhoehe / 2 + gl.unitsize;
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,40F));
		if (befehl == 1) {
			g2.setColor(Color.cyan);
		}
		else {
			g2.setColor(Color.gray);
		}
		g2.drawString(text, x + 2, y + 2);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if (befehl == 1) {
			text = ">";
			x -= 40; 
			g2.setColor(Color.black);
			g2.drawString(text, x + 2, y + 2);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);			
		}
		
		
		
		
	}
}
