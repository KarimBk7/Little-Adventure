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
		hitboxX = 20;
		hitbox.height = gl.buildingsize;
		hitbox.width = gl.buildingsize - 40;
		
	}
	
}
