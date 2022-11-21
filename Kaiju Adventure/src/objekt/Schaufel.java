package objekt;

import javax.imageio.ImageIO;

public class Schaufel extends Objekt{

	public Schaufel () {
		name = "Schaufel";
		isCollision = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objekt/schaufel.png"));
		} catch (Exception e) {
			
		}
	}
}
