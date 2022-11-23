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
		dialog[0] = "Lass deine Haende von \nmeiner Kiste!!";
		dialog[1] = "ach manno...das war \nmeine Schaufel.";
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
