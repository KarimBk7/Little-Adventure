package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
	

		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);				//Vom nutzer verstellbar (nein)
		window.setTitle("Kaiju Adventure");	
		
		GameLoop gameloop = new GameLoop();
		window.add(gameloop);
		window.pack();
		window.setLocationRelativeTo(null);//fenster taicht in der mitte des Bildschrims auf
		window.setVisible(true);
		
		gameloop.startGameThread();
		
	}
}
