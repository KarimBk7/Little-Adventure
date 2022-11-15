package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import unit.Player;

public class GameLoop extends JPanel implements Runnable {

	//BILDSCHIRM SCALIERUNG
	private int standartunitsize = 16;
	private int scale = 3;
	public int unitsize = standartunitsize * scale;  //48x48
	
	private int maxScreenCol = 16;
	private int maxScreenrow = 12;
	
	private int screenweite = unitsize * maxScreenCol;
	private int screenhoehe = unitsize * maxScreenrow;
	
	//FPS
	int fps = 60;

	KeyInput keyI = new KeyInput();
	Thread gameThread;
	Player player = new Player(this,keyI);
	
	//Default-Position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	
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
		player.draw(g2);
		g2.dispose();
		
		
	}
}
