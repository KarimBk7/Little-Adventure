package objekt;

import javax.imageio.ImageIO;

import main.GameLoop;

public class OpenDoor extends Objekt{

	
	public OpenDoor(GameLoop gl) {
		name = "opendoor";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Objekt/opendoor.png"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
