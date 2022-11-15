package surrounding;

import java.awt.Graphics2D;

import javax.imageio.ImageIO;

import main.GameLoop;

public class TileManager {

	GameLoop gl;
	Tile[] tile;
	
	//Kosntruktor
	public TileManager(GameLoop gl) {
		this.gl = gl;
		tile = new Tile[10];
		getTilepng();
	}
	
	public void getTilepng() {
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/gras.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/wand.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/wasser.png"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		
		//g2.drawImage(tile[0].image, 0, 0, gl.unitsize, gl.unitsize, null); //Test
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while (col < gl.maxScreenCol && row < gl.maxScreenrow) {
			g2.drawImage(tile[0].image, x, y,gl.unitsize, gl.unitsize, null);
			col++;
			x += gl.unitsize;
			
			if (col == gl.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gl.unitsize;
			}
		}
	}
}
