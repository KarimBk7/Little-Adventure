package objekt;

import javax.imageio.ImageIO;

public class House extends Objekt{

	public House () {
		name = "House";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objekt/house.png"));
		} catch (Exception e) {
			
		}
	}
	
}
