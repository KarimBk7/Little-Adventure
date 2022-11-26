package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import objekt.Objekt;
import surrounding.TileManager;
import unit.Player;
import unit.Unit;

public class GameLoop extends JPanel implements Runnable {

	//BILDSCHIRM SCALIERUNG
	private int standartunitsize = 16;
	private int scale = 3;
	public int unitsize = standartunitsize * scale;  //48x48
	public int baumsize = unitsize * 2;
	public int buildingsize = unitsize * 4;
	
	//Fenster groesse
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

	//Keyinput
	public KeyInput keyI = new KeyInput(this);
	
	//Units
	public CollisionC cc = new CollisionC(this);
	public Player player = new Player(this,keyI);
	public Unit npc[] = new Unit[10];
	public Unit monster[] = new Unit[10];
	
	//Weltkarte
	TileManager tileM = new TileManager(this);
	public Objekt obj [] = new Objekt[30];
	public ObjektSetter oSetter = new ObjektSetter(this);
	
	//sound & ui
	Sound sound = new Sound();
	public UI ui = new UI(this);
	public Counter counter []= new Counter[20]; 	
		
	//Gamestate
	public int gameState;
	public int titlestate = 0;
	public int prologstate = 1;
	public int playState = 2;
	public int pauseState = 3;
	public int dialogState = 4; 
	public int shopState = 5;
	public int winstate = 6;
	public int losestate = 7;
	
	
	public Thread gameThread;
	
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
			long starttim = System.nanoTime();	
			
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
				long endtime = System.nanoTime();
				endtime -= starttim;
				System.out.println("Nanosec: " + endtime);
				drawCounter = 0;
				timer = 0;
			}
			
		}	
	}
	
	public void update() {//
		
		if (gameState == playState) {
			player.update();
			
			
			//update Monster
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					monster[i].update();
				}
			}
		}
	}
	
	public void setupObjekt() {
		
		//settet alle Objekte
		oSetter.setObjekt();
		oSetter.setNPC();
		oSetter.setMonster();
		oSetter.setCounter();
		
		
		gameState = titlestate;
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		//Titel-Screen
		if (gameState == titlestate || gameState == prologstate) {
			ui.draw(g2);
		}
		else {
			//draw Weltkarte
			tileM.draw(g2);
			
			//Draw Alle Objekte
			for (int i = 0; i < obj.length; i++) {
				if (obj[i] != null) {
					obj[i].draw(g2, this,obj,i);
				}
			}
			
			//Draw alle NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					npc[i].draw(g2, this,npc,i);
				}
			}
			
			//Draw alle Monster
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					monster[i].draw(g2, this,monster,i);
				}
			}
			
			//Draw Spieler
			player.draw(g2);
			
			//Draw HUD
			ui.draw(g2);
			
			g2.dispose();
		}
	}
	
	//startet audio in loop
	public void playMusik(int i) {
		
		sound.setFile(i);
		sound.play();
		sound.loop();
	}
	
	//stoppt audio
	public void stopMusik() {
		
		sound.stop();
	}
	
	//startet audio
	public void soundEffekt(int i) {
		
		sound.setFile(i);
		sound.play();
	}
}
