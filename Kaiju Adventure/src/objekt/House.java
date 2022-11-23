package objekt;

import javax.imageio.ImageIO;

import main.CollisionC;
import main.GameLoop;

public class House extends Objekt{

	
	public House (GameLoop gl) {
		name = "house";
		isCollision = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objekt/house.png"));
		} catch (Exception e) {
			
		}
		hitbox.height = gl.buildingsize;
		hitbox.width = gl.buildingsize;
		
	}
	
}
