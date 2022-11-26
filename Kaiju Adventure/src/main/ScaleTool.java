package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ScaleTool {

	public BufferedImage scaleImage(BufferedImage image, int width, int height) {
		
		BufferedImage scaleImage = new BufferedImage (width, height, image.getType());
		Graphics g2 = scaleImage.createGraphics();
		g2.drawImage(image, 0, 0, width, height, null);
		g2.dispose();
		
		return scaleImage;
	}
	
	public BufferedImage setupImage(String namepng, int width, int heigh) {
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/" + namepng + ".png"));
			image = scaleImage(image, width, heigh);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return image;
	}
}
