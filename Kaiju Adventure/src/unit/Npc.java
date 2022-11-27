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
		//Brandon
		dialog[0] = "Du willst meine wertvolle Kiste?\nDann bring mir 3 Aepfel, \ndamit ich einen Apfelkuchen backen kann."
					+ "\nVielleicht bekommst du auch etwas ab.";
		dialog[1] = "Vielen Dank fuer die Aepfel mein Freund\n"
					+ "Der Inhalt meiner Kiste gehoert dir.\n"
					+ "Erwarte aber nicht zu viel.";
		
		//Angler
		dialog[2] = "Nachdem ich einen Fisch geangelt hab hol ich \nmir ein paar Aepfel vom Wald.";
		dialog[3] = "Hab gehoert das es welche etwas noerdlich von hier gibt.";
		
		//momo
		dialog[4] = "Was wenn ich krank bin?";
		dialog[5] = "Was wenn ich alleine machen will?";
		dialog[6] = "*ich glaub er ist paranoid*";
		
		//haendler
		dialog[7] = "Hilf mir diese Monster zu vertreiben!!!";
		dialog[8] = "Ich danke dir reisender.\nIch hab schon befuerchtet das dies mein Ende sei\n"
					+ "Ich wollte eigentlich nur im Wald etwas \nHolz hacken";
		dialog[9] = "Als dank eroeffne ich meinen Laden etwas frueher heute. \n"
					+ "Erwarte aber bloss keinen Rabatt nur weil du mich \ngerettet hast. "
					+ "Immerhin herrscht \nInflation wegen Herr Tenbusch.";
		dialog[10] = "Was kann ich für dich tun?";
	}

	public void getNpcpng() {
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/npc/brandon.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void speak() {
		gl.ui.currentDialog = dialog[dialogIndex];
	}
}
