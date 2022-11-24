package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
	

		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);					//Vom nutzer verstellbar (nein)
		window.setTitle("Kaiju Adventure");	
		
		//fügt gameloop zum fenster hinzu
		GameLoop gameloop = new GameLoop();
		window.add(gameloop);
		window.pack();
		window.setLocationRelativeTo(null);			//fenster taucht in mitte auf
		window.setVisible(true);
		
		gameloop.setupObjekt();
		gameloop.startGameThread();
		
	}
}
