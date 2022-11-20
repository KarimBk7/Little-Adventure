package objekt;

import javax.imageio.ImageIO;

import main.GameLoop;

public class ClosedDoor extends Objekt{

		
		public ClosedDoor(GameLoop gl) {
			name = "closeddoor";
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/Objekt/closeddoor.png"));
			} catch (Exception e) {
				
			}
			isCollision = true;
			hitbox.width = gl.unitsize * 2;
			hitbox.height = gl.unitsize * 2;
		}
	
}
