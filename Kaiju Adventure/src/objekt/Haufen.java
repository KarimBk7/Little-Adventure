package objekt;

import javax.imageio.ImageIO;

public class Haufen extends Objekt{
	public Haufen () {
		name = "Haufen";
		isCollision = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objekt/haufen.png"));
		} catch (Exception e) {
			
		}
	}
}
