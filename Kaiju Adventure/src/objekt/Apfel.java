package objekt;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Apfel extends Objekt{

	public Apfel() {
		name = "apfel";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objekt/apfel.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
