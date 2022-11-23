package unit;

import javax.imageio.ImageIO;

import main.GameLoop;

public class Npc extends Unit{

	public Npc(GameLoop gl) {
		super(gl);
		
		richtung = "down";
		isCollision = true;
		getNpcpng();
		setDialog();
	}
	
	public void setDialog() {
		dialog[0] = "Du willst meine wertvolle Kiste?\nDann bring mir 3 Aepfel, damit ich \neinen Apfelkuchen backen kann."
					+ "\nVielleicht bekommst du auch etwas ab.";
		dialog[1] = "Vielen Dank fuer die Aepfel mein Freund\n"
					+ "Der Inhalt meiner Kiste gehört dir.\n"
					+ "Erwarte aber nicht zu viel.";
		dialog[2] = "Danke das du mir die Mosnter \nverjagt hast!\nWie kann ich mich revanchieren?";
		dialog[3] = "juckt";
	}

	public void getNpcpng() {
		
		//speichert Spieler Bilder in BufferdImage
		try {
			
			down1 = ImageIO.read(getClass().getResourceAsStream("/npc/brandon.png"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void speak() {
		gl.ui.currentDialog = dialog[dialogIndex];
		
	}
}
