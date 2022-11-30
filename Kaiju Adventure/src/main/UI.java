package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import objekt.Apfel;
import objekt.Heart;
import objekt.Key;
import objekt.Schaufel;

public class UI {

	//Attribute
	Graphics2D g2;
	GameLoop gl;
	Font arial40,arial30, arial20;
	BufferedImage key, apfel, schaufel, spitzhacke, herz, herzleer, herzleer1, prologimg, wasd, enter, e, poison, slowness, itdollar,
				  healing_potion, strenght_potion, speed_potion, tilebg;
	
	public boolean prologabgespielt = false;
	public boolean spielbeendet = false;
	public boolean messageOn = false;
	public String message = "";
	public String currentDialog = "";
	public int counter, countermax;
	public int befehl = 0;
	
	//shop
	public boolean ausverkauft, keinGeld = false;
	
	//Konstruktor
	public UI(GameLoop gl) {
		this.gl = gl;
		arial40 = new Font("Arial", Font.PLAIN, 40);
		arial30 = new Font("Arial", Font.PLAIN, 30);
		arial20 = new Font("Arial", Font.PLAIN, 20);
		Key key = new Key(gl);
		this.key = key.image;
		
		Apfel apfel = new Apfel(gl);
		this.apfel = apfel.image;
		
		try {
			herz = ImageIO.read(getClass().getResourceAsStream("/objekt/herz.png"));
			herzleer = ImageIO.read(getClass().getResourceAsStream("/objekt/herzleer.png")); 
			herzleer1 = ImageIO.read(getClass().getResourceAsStream("/objekt/herzleer1.png")); 
			prologimg = ImageIO.read(getClass().getResourceAsStream("/objekt/prolog_bg.png"));
			tilebg = ImageIO.read(getClass().getResourceAsStream("/objekt/tilebg.png"));
			wasd = ImageIO.read(getClass().getResourceAsStream("/steuerung/wasd.png"));
			e = ImageIO.read(getClass().getResourceAsStream("/steuerung/e.png"));
			enter = ImageIO.read(getClass().getResourceAsStream("/steuerung/enter.png"));
			poison = ImageIO.read(getClass().getResourceAsStream("/effekt/poison.png"));
			itdollar = ImageIO.read(getClass().getResourceAsStream("/objekt/itdollar.png"));
			healing_potion = ImageIO.read(getClass().getResourceAsStream("/effekt/healing_potion.png"));
			strenght_potion = ImageIO.read(getClass().getResourceAsStream("/effekt/strenght_potion.png"));
			speed_potion = ImageIO.read(getClass().getResourceAsStream("/effekt/speed_potion.png"));
			schaufel = ImageIO.read(getClass().getResourceAsStream("/objekt/schaufel.png"));
			spitzhacke = ImageIO.read(getClass().getResourceAsStream("/objekt/spitzhacke.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Steuerung einfuegen
	}
	
	public void showMessage(String text, int countermax) {
		message = text;
		messageOn = true;
		this.countermax = countermax;
	}
	
	public void draw(Graphics2D g2) {
		
		//wenn nicht titel- oder prologbildschirm
		if(gl.gameState != gl.titlestate && gl.gameState != gl.prologstate && gl.gameState != gl.ladespielstate){
			//Spielt
			if (gl.gameState == gl.playState) {
				
				//pause hinweis
				g2.setFont(arial20);
				g2.setColor(Color.black);
				g2.drawString("pause/Steuerung (p)", 18, 23);
				g2.setColor(Color.white);
				g2.drawString("pause/Steuerung (p)", 16, 21);
				
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
				if (gl.player.hatSpitzhacke == true) {
					g2.drawImage(spitzhacke, 25, 135, gl.unitsize, gl.unitsize, null);
				}
				
				//it-dollar anzeigen
				g2.setColor(Color.black);
				g2.drawString(gl.player.itDollar + "$", gl.unitsize * 14 -10+ 3, 68);
				g2.setColor(Color.white);
				g2.drawImage(itdollar, gl.unitsize * 13 - 20, gl.unitsize - 20, gl.unitsize, gl.unitsize, null);
				g2.drawString(gl.player.itDollar + "$", gl.unitsize * 14 - 10, 65);
				
				lostHealth(g2);
				getHealth(g2);
				drawPlayerHealth(g2);
			}
		
			//Interaktions Messages
			if(messageOn == true) {
				currentDialog = message;
				drawDialogFenster(g2);
				counter++;
				//wie lang die message bleibt
				if (counter >= countermax) {
					messageOn = false;
					counter = 0;
				}
			}
			
			//wenn Pause gedrückt wird
			else if (gl.gameState == gl.pauseState) {
				drawPauseScreen(g2);
			}
			//wenn dialog
			else if (gl.gameState == gl.dialogState) {
				drawDialogFenster(g2);
			}
			
			else if(gl.gameState == gl.losestate) {
				drawLosingscreen(g2);
			}
			
			else if(gl.gameState == gl.winstate) {
				drawWinscreen(g2);
			}
			
			else if(gl.gameState == gl.shopState) {
				drawShop(g2);
			}
			else if (gl.gameState == gl.speicherspiel) {
				drawSpielspeichern(g2);
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
		else if (gl.gameState == gl.ladespielstate) {
			drawLadeSpiel(g2);
		}
	}
	
	private void drawLadeSpiel(Graphics2D g2) {
		//Hintergrund
		g2.setColor(new Color(30,30,30));
		g2.fillRect(0, 0, gl.screenweite, gl.screenhoehe);
		
		int x = gl.unitsize * 4 + 25;
		int y = gl.unitsize * 1;
		int weite = gl.unitsize * 7;
		int hoehe = gl.unitsize * 3;
		g2.setStroke(new BasicStroke(5));
		
		//1te Spieldatei
		g2.setColor(Color.black);
		g2.fillRoundRect(x, y, weite, hoehe, 35, 35);
		if (befehl == 0) {g2.setColor(Color.cyan);}
		else {g2.setColor(Color.white);}
		g2.drawRoundRect(x+10, y+10, weite-20, hoehe-20, 25, 25);
		
		
		y = gl.unitsize * 4 + 20;
		//2te Spieldatei
		g2.setColor(Color.black);
		g2.fillRoundRect(x, y, weite, hoehe, 35, 35);
		if (befehl == 1) {g2.setColor(Color.cyan);}
		else {g2.setColor(Color.white);}
		g2.drawRoundRect(x+10, y+10, weite-20, hoehe-20, 25, 25);
		
		
		y = gl.unitsize * 8 - 8;
		//3te Spieldatei
		g2.setColor(Color.black);
		g2.fillRoundRect(x, y, weite, hoehe, 35, 35);
		if (befehl == 2) {g2.setColor(Color.cyan);}
		else {g2.setColor(Color.white);}
		g2.drawRoundRect(x+10, y+10, weite-20, hoehe-20, 25, 25);
		
		x = gl.unitsize * 12;
		y = gl.unitsize * 11 + 20;
		g2.setFont(arial30);
		if (befehl == 3) {
			g2.setColor(Color.gray);
			g2.drawString(">", x - 18, y + 2);
			g2.setColor(Color.white);
			g2.drawString(">", x - 20, y);
			g2.setColor(Color.cyan);
		}
		else {g2.setColor(Color.gray);}
		g2.drawString("Hauptmenue", x + 2, y + 2);
		g2.setColor(Color.white);
		g2.drawString("Hauptmenue", x, y);
	}

	private void drawSpielspeichern(Graphics2D g2) {
		//Hintergrund
				g2.setColor(new Color(0,0,0, 220));
				g2.fillRect(0, 0, gl.screenweite, gl.screenhoehe);
				
				int x = gl.unitsize * 4 + 25;
				int y = gl.unitsize * 1;
				int weite = gl.unitsize * 7;
				int hoehe = gl.unitsize * 3;
				g2.setStroke(new BasicStroke(5));
				
				//1te Spieldatei
				g2.setColor(Color.black);
				g2.fillRoundRect(x, y, weite, hoehe, 35, 35);
				if (befehl == 0) {g2.setColor(Color.cyan);}
				else {g2.setColor(Color.white);}
				g2.drawRoundRect(x+10, y+10, weite-20, hoehe-20, 25, 25);
				
				
				y = gl.unitsize * 4 + 20;
				//2te Spieldatei
				g2.setColor(Color.black);
				g2.fillRoundRect(x, y, weite, hoehe, 35, 35);
				if (befehl == 1) {g2.setColor(Color.cyan);}
				else {g2.setColor(Color.white);}
				g2.drawRoundRect(x+10, y+10, weite-20, hoehe-20, 25, 25);
				
				
				y = gl.unitsize * 8 - 8;
				//3te Spieldatei
				g2.setColor(Color.black);
				g2.fillRoundRect(x, y, weite, hoehe, 35, 35);
				if (befehl == 2) {g2.setColor(Color.cyan);}
				else {g2.setColor(Color.white);}
				g2.drawRoundRect(x+10, y+10, weite-20, hoehe-20, 25, 25);
				
				x = gl.unitsize * 12;
				y = gl.unitsize * 11 + 20;
				g2.setFont(arial30);
				if (befehl == 3) {
					g2.setColor(Color.gray);
					g2.drawString(">", x - 18, y + 2);
					g2.setColor(Color.white);
					g2.drawString(">", x - 20, y);
					g2.setColor(Color.cyan);
				}
				else {g2.setColor(Color.gray);}
				g2.drawString("Zurueck", x + 2, y + 2);
				g2.setColor(Color.white);
				g2.drawString("Zurueck", x, y);
			}
	
	private void drawShop(Graphics2D g2) {
		g2.setColor(new Color(0,0,0,200));
		g2.fillRect(0, 0, gl.screenweite, gl.screenhoehe);
		g2.setFont(arial40);
		
		//it-dollar
		g2.setColor(Color.black);
		g2.drawString(gl.player.itDollar + "$", gl.unitsize * 14 -10+ 3, 68);
		g2.setColor(Color.white);
		g2.drawImage(itdollar, gl.unitsize * 12 - 25, 3, gl.unitsize * 2, gl.unitsize * 2, null);
		g2.drawString(gl.player.itDollar + "$", gl.unitsize * 14 - 10, 65);
		
		//heil_potion
		int x = gl.unitsize * 3;
		int y = gl.unitsize * 4;
		if (befehl == 0) {
			y -= 20;
			g2.setColor(Color.cyan);
		}
		else {
			g2.setColor(Color.gray);
		}
		g2.drawImage(healing_potion, x, y,gl.unitsize * 2, gl.unitsize * 2, null);
		g2.drawString("100$", x + 3, y + gl.unitsize * 3 + 3);
		g2.setColor(Color.white);
		g2.drawString("100$", x, y + gl.unitsize * 3);
		
		//strenght_potion
		x = gl.unitsize * 6;
		y = gl.unitsize * 4;
		if (befehl == 1) {
			y -= 20;
			g2.setColor(Color.cyan);
		}
		else {
			g2.setColor(Color.gray);
		}
		if (gl.keyI.strenght < 1) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}
		else {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		g2.drawImage(strenght_potion, x, y, gl.unitsize * 2, gl.unitsize * 2, null);
		g2.drawString("100$", x + 3, y + gl.unitsize * 3 + 3);
		g2.setColor(Color.white);
		g2.drawString("100$", x, y + gl.unitsize * 3);
		
		//speed_potion
		x = gl.unitsize * 9;
		y = gl.unitsize * 4;
		if (befehl == 2) {
			y -= 20;
			g2.setColor(Color.cyan);
		}
		else {
			g2.setColor(Color.gray);
		}
		if (gl.keyI.speed < 1) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}
		else {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		g2.drawImage(speed_potion, x, y, gl.unitsize * 2, gl.unitsize * 2, null);
		g2.drawString("100$", x + 3, y + gl.unitsize * 3 + 3);
		g2.setColor(Color.white);
		g2.drawString("100$", x, y + gl.unitsize * 3);
		
		//spitzhacke
		x = gl.unitsize * 12;
		y = gl.unitsize * 4;
		if (befehl == 3) {
			y -= 20;
			g2.setColor(Color.cyan);
		}
		else {
			g2.setColor(Color.gray);
		}
		if (gl.keyI.spitzhacke < 1) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}
		else {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		g2.drawImage(spitzhacke, x, y, gl.unitsize * 2, gl.unitsize * 2, null);
		g2.drawString("Gratis", x + 3, y + gl.unitsize * 3 + 3);
		g2.setColor(Color.white);
		g2.drawString("Gratis", x, y + gl.unitsize * 3);
		
		//verlassen-button
		x = gl.unitsize * 6;
		y = gl.unitsize * 11;
		
		if (befehl == 4) {
			g2.setColor(Color.cyan);
		}
		else {
			g2.setColor(Color.gray);
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		g2.drawString("Verlassen", x + 3, y + 3);
		g2.setColor(Color.white);
		g2.drawString("Verlassen", x, y);
		if (befehl == 4) {
			x -= 40; 
			g2.setColor(Color.gray);
			g2.drawString(">", x + 3, y + 3);
			g2.setColor(Color.white);
			g2.drawString(">", x, y);
		}
		
		//ausverkauft & nicht genug it-dollar anzeige
		g2.setFont(arial20);
		
		x = gl.unitsize * 7 - 10;
		y = gl.unitsize * 9;
		if (ausverkauft) {
			g2.setColor(Color.white);
			g2.drawString("Ausverkauft", x + 1, y + 1);
			g2.setColor(new Color(200,50,50));
			g2.drawString("Ausverkauft", x, y);
		}
		
		if (ausverkauft) {
			gl.counter[5].count();
		}
		if (gl.counter[5].isDone()) {
			gl.counter[5].resetCount();
			ausverkauft = false;
			
		}
		
		x = gl.unitsize * 6;
		y = gl.unitsize * 8;
		if (keinGeld) {
			g2.setColor(Color.white);
			g2.drawString("Nicht genug IT-Dollar", x + 1, y + 1);
			g2.setColor(new Color(200,50,50));
			g2.drawString("Nicht genug IT-Dollar", x, y);
		}
		
		if (keinGeld) {
			gl.counter[6].count();
		}
		if (gl.counter[6].isDone()) {
			gl.counter[6].resetCount();
			keinGeld = false;
			
		}
	}

	private void getHealth(Graphics2D g2) {
		if (gl.player.gethealth == true) {
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN,25F));
			g2.setColor(Color.white);
			g2.drawString("+1", gl.screenweite / 2 - 10, gl.screenhoehe / 2 - 38);
			g2.setColor(Color.cyan);
			g2.drawString("+1", gl.screenweite / 2 - 10, gl.screenhoehe / 2 - 40);
			gl.counter[0].count();
		}
		if (gl.counter[0].isDone()) {
			gl.player.gethealth = false;
			gl.counter[0].resetCount();
		}
	}

	private void lostHealth(Graphics2D g2) {
		if (gl.player.losthealth == true) {
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN,25F));
			g2.setColor(Color.white);
			g2.drawString("-1", gl.screenweite / 2 - 10, gl.screenhoehe / 2 - 28);
			g2.setColor(Color.red);
			g2.drawString("-1", gl.screenweite / 2 - 10, gl.screenhoehe / 2 - 30);
			gl.counter[1].count();
		}
		if (gl.counter[1].isDone()) {
			gl.player.losthealth = false;
			gl.counter[1].resetCount();
		}
	}

	private void drawWinscreen(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}

	private void drawLosingscreen(Graphics2D g2) {

		g2.setColor(new Color(0,0,0,220));
		g2.fillRect(0, 0, gl.screenweite, gl.screenhoehe);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,100F));
		g2.setColor(Color.white);
		g2.drawString("Loser", gl.unitsize * 5 + 4, gl.unitsize * 2 + 4);	
		g2.setColor(new Color(200,60,60));
		g2.drawString("Loser", gl.unitsize * 5, gl.unitsize * 2);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
		String text = "Neu Starten";
		int x = gl.unitsize * 5 + 25;
		int y = gl.unitsize * 6;
		if (befehl == 0) {
			g2.setColor(Color.cyan);
		}
		else {
			g2.setColor(Color.gray);
		}
		g2.drawString(text, x + 3, y + 3);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if (befehl == 0) {
			text = ">";
			x -= 40; 
			g2.setColor(Color.gray);
			g2.drawString(text, x + 3, y + 3);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
		}
		
		//Lade Spiel
		text = "Hauptmenu";
		x = gl.unitsize * 5 + 30;
		y = gl.unitsize * 7;
		if (befehl == 1) {
			g2.setColor(Color.cyan);
		}
		else {
			g2.setColor(Color.gray);
		}
		g2.drawString(text, x + 3, y + 3);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if (befehl == 1) {
			text = ">";
			x -= 40; 
			g2.setColor(Color.gray);
			g2.drawString(text, x + 3, y + 3);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
		}
		
		//TODO punkte anzeigen
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
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
		g2.drawString("press ENTER", 550, 500);	
	}

	private void drawPlayerHealth(Graphics2D g2) {
		//leere lebensbalken
		int x = 20;
		int y = gl.unitsize * 9;
		int i = 0;
		
			g2.drawImage(herzleer, x, y + 20, gl.unitsize * 3, gl.unitsize * 3, null);
			g2.drawImage(herzleer1, x + 147, y + 20, gl.unitsize * 3, gl.unitsize * 3, null);
			i++; 
		
		//draw lebenseinheiten
		y += gl.unitsize + 20;
		i = 0;
		
		while (i < gl.player.health) {
			g2.drawImage(herz, x, y, gl.unitsize - 2, gl.unitsize, null);
			i++; 
			x+=gl.unitsize + 1;
			
		}
		
		//draw healing-potion anzahl
		g2.setFont(arial30);
		g2.drawImage(healing_potion, gl.unitsize - 25, gl.unitsize * 8 + 20, 32, 32, null);
		g2.setColor(Color.black);
		g2.drawString("x " + gl.player.healing_potion, gl.unitsize + 17, gl.unitsize * 9 + 2);
		g2.setColor(Color.white);
		g2.drawString("x " + gl.player.healing_potion, gl.unitsize + 15, gl.unitsize * 9);
		
		//draw poison status
		if (gl.player.poison) {
			g2.drawImage(poison, gl.unitsize * 3 + 180, gl.unitsize * 10 + 30, gl.unitsize / 2, gl.unitsize / 2, null);
			gl.counter[3].count();
		}
		if (gl.counter[3].isDone()) {
			gl.counter[3].resetCount();
			gl.player.poison = false;
		}
	}

	private void drawTitleScreen(Graphics2D g2) {
		
		//Hintergrund
		g2.drawImage(tilebg, 0, 0, gl.screenweite, gl.screenhoehe, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g2.fillRect(0, 0, gl.screenweite, gl.screenhoehe);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
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
			g2.setColor(Color.gray);
			g2.drawString(line, posX + 1, posY + 1);
			g2.setColor(Color.white);
			g2.drawString(line, posX, posY);
			posY+=30;
		}
		if (gl.gameState == gl.dialogState) {
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN,16F));
			g2.setColor(Color.gray);
			g2.drawString("press ENTER", 551, 511);
			g2.setColor(Color.white);
			g2.drawString("press ENTER", 550, 510);
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
		int x = gl.screenweite / 2 - gl.unitsize * 2;
		int y = gl.screenhoehe / 4 - 50;
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,70F));
		g2.setColor(Color.white);
		g2.drawString("Pause", x + 4, y + 4);
		g2.setColor(new Color(255,100,100));
		g2.drawString("Pause", x, y);
		
		//Steuerung anzeigen
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
		g2.setColor(Color.gray);
		g2.drawString("Bewegen:", gl.unitsize + 2, gl.unitsize * 9 + 22);
		g2.setColor(Color.white);
		g2.drawString("Bewegen:", gl.unitsize, gl.unitsize * 9 + 20);
		g2.drawImage(wasd, gl.unitsize, gl.unitsize * 9, gl.unitsize * 3, gl.unitsize * 3, null);
		
		g2.setColor(Color.gray);
		g2.drawString("Heilen:", gl.unitsize * 7 + 2, gl.unitsize * 9 + 22);
		g2.setColor(Color.white);
		g2.drawString("Heilen:", gl.unitsize * 7, gl.unitsize * 9 + 20);
		g2.drawImage(e, gl.unitsize * 6 + 30, gl.unitsize * 9 + 15, gl.unitsize * 3, gl.unitsize * 3, null);

		g2.setColor(Color.gray);
		g2.drawString("Interargieren:", gl.unitsize * 11 + 27, gl.unitsize * 9 + 22);
		g2.drawString("Kaempfen /", gl.unitsize * 11 + 27, gl.unitsize * 8 + 37);
		g2.setColor(Color.white);
		g2.drawString("Interargieren:", gl.unitsize * 11 + 25, gl.unitsize * 9 + 20);
		g2.drawString("Kaempfen /", gl.unitsize * 11 + 25, gl.unitsize * 8 + 35);
		g2.drawImage(enter, gl.unitsize * 12, gl.unitsize * 8 + 35, gl.unitsize * 4, gl.unitsize * 4, null);
		
		//Weiter Spielen
		String text = "Weiter Spielen";
		x = gl.unitsize * 5 + 25;
		y = gl.unitsize * 4 + 20;
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
		
		text = "Spiel Speichern";
		x = gl.unitsize * 5 + 10;
		y = gl.screenhoehe / 2 - 15;
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
		
		//Zurück zum Hauptmenu
		text = "Hauptmenu";
		x = gl.unitsize * 6;
		y = gl.screenhoehe / 2 + gl.unitsize;
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,40F));
		if (befehl == 2) {
			g2.setColor(Color.cyan);
		}
		else {
			g2.setColor(Color.gray);
		}
		g2.drawString(text, x + 2, y + 2);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if (befehl == 2) {
			text = ">";
			x -= 40; 
			g2.setColor(Color.black);
			g2.drawString(text, x + 2, y + 2);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);			
		}
	}
}
