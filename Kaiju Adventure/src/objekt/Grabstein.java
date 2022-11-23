package objekt;

import javax.imageio.ImageIO;

import main.GameLoop;

public class Grabstein extends Objekt{

	public Grabstein() {
		name = "Grabstein";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Objekt/grabstein.png"));
		} catch (Exception e) {
			
		}
		hitbox.height = 96;
		isCollision = true;
	}
}
