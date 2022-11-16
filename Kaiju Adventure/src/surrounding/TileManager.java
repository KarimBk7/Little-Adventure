package surrounding;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GameLoop;

public class TileManager {

	GameLoop gl;
	Tile[] tile;
	int mapTile[][];
	
	//Kosntruktor
	public TileManager(GameLoop gl) {
		this.gl = gl;
		tile = new Tile[10];
		mapTile = new int [gl.maxScreenCol][gl.maxScreenrow];
		ladeWeltkarte("/weltkarte/map1.txt");
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
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/gehweg.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/baum.png"));
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/grashalme.png"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ladeWeltkarte(String file) {
		try {
			InputStream karte = getClass().getResourceAsStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(karte));
			
			int col = 0;
			int row = 0;
			
			while (col < gl.maxScreenCol && row < gl.maxScreenrow) {
				
				String line = br.readLine();	
				
				while(col < gl.maxScreenCol) {
				
					String number[] = line.split(" ");
					int num = Integer.parseInt(number[col]);
				
					mapTile[col][row] = num;
					col++;
				}
				
				if (col == gl.maxScreenCol) {
					col = 0;
					row++;
				}
			
			}
			
			
		} catch (Exception e) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		
		//g2.drawImage(tile[0].image, 0, 0, gl.unitsize, gl.unitsize, null); //Test
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while (col < gl.maxScreenCol && row < gl.maxScreenrow) {
			
			int tileNum = mapTile[col][row];
			
			g2.drawImage(tile[tileNum].image, x, y,gl.unitsize, gl.unitsize, null);
			col++;
			x += gl.unitsize;
			
			if (col == gl.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gl.unitsize;
			}
			
		}
		g2.drawImage(tile[4].image, 100, 100, gl.baumsize, gl.baumsize , null);
	}
}
