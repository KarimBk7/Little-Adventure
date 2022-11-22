package objekt;

import javax.imageio.ImageIO;

import main.GameLoop;

public class Heart extends Objekt{
	
	GameLoop gl;

	
	public Heart(GameLoop gl) {
		this.gl = gl;
		name = "Herz";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objekt/herz.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/objekt/herzleer.png")); 
			
		} catch (Exception e) {
		
		}
	}
}
