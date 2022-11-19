package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;

import javax.swing.JPanel;

import objekt.Objekt;
import surrounding.TileManager;
import unit.Player;

public class GameLoop extends JPanel implements Runnable {

	//BILDSCHIRM SCALIERUNG
	private int standartunitsize = 16;
	private int scale = 3;
	public int unitsize = standartunitsize * scale;  //48x48
	public int baumsize = unitsize * 2;
	public int buildingsize = unitsize * 4;
	
	public int maxScreenCol = 16;
	public int maxScreenrow = 12;
	
	public int screenweite = unitsize * maxScreenCol;
	public int screenhoehe = unitsize * maxScreenrow;
	
	//Weltkarten Scalierung
	public int maxWeltCol = 80;
	public int maxWeltRow = 80;
	public int weltweite = maxWeltCol + unitsize;
	public int welthoehe = maxWeltRow + unitsize;
	
	
	//FPS
	int fps = 60;

	//Objekte erstellen
	TileManager tileM = new TileManager(this);
	KeyInput keyI = new KeyInput();
	Thread gameThread;
	public CollisionC cc = new CollisionC(this);
	public Player player = new Player(this,keyI);
	public Objekt obj [] = new Objekt[10];
	public ObjektSetter oSetter = new ObjektSetter(this);
	
	
	//Konstruktor
	public GameLoop() {
		this.setPreferredSize(new Dimension(screenweite, screenhoehe));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyI);
		this.setFocusable(true);
	}

	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double drawInt = 1000000000 / fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		long drawCounter = 0;
		
		while (gameThread != null) {	
			
			//FPS Rechner
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInt;
			timer += (currentTime -lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCounter++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCounter);
				drawCounter = 0;
				timer = 0;
			}
		}	
	}
	
	public void update() {
		
		player.update();
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		//draw Weltkarte
		tileM.draw(g2);
		
		//Draw Alle Objekte
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null) {
				if (i < 3) {
					obj[i].drawBuilding(g2, this);
				}
				else {
					obj[i].draw(g2, this);
				}
				
			}
		}
		
		//Draw Spieler
		player.draw(g2);
	
		g2.dispose();
		
		
	}
	
	public void setupObjekt() {
		
		oSetter.setObjekt();
		
	}
}
