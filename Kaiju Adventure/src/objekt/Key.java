package objekt;

import javax.imageio.ImageIO;

public class Key extends Objekt{

	public Key () {
		name = "Key";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objekt/key.png"));
		} catch (Exception e) {
			
		}
	}
}
