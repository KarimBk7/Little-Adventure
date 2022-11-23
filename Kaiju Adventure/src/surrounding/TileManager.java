package surrounding;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GameLoop;

public class TileManager {

	GameLoop gl;
	public Tile[] tile;
	public int mapTile[][];
	int spriteCounter = 0;
	int spriteNum = 1;
	int animationspeed = 30;
	
	//Kosntruktor
	public TileManager(GameLoop gl) {
		this.gl = gl;
		tile = new Tile[12];
		mapTile = new int [gl.maxWeltCol][gl.maxWeltRow];
		ladeWeltkarte("/weltkarte/map2.txt");
		getTilepng();
		
	}
	
	public void getTilepng() {
		//Speichert alle images in Tile-Array
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/transparent.png"));
			tile[0].collision = true;
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/gras.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/wasser1.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/steinboden.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/wand.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/baum.png"));
			tile[5].collision = true;
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/gehweg.png"));
			
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/sand.png"));
			
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/lava1.png"));
			tile[8].collision = true;
			
			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/felsen.png"));
			tile[9].collision = true;
			
			tile[10] = new Tile();
			tile[10].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/wasser2.png"));
			tile[10].collision = true;
			
			tile[11] = new Tile();
			tile[11].image = ImageIO.read(getClass().getResourceAsStream("/surrounding/lava2.png"));
			tile[11].collision = true;
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//speichert die welt von der .txt in ein 2D-Array
	public void ladeWeltkarte(String file) {
		try {
			InputStream karte = getClass().getResourceAsStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(karte));
			
			int col = 0;
			int row = 0;
			
			while (col < gl.maxWeltCol && row < gl.maxWeltRow) {
				
				String line = br.readLine();	
				
				while(col < gl.maxWeltCol) {
				
					String number[] = line.split(" ");
					int num = Integer.parseInt(number[col]);
				
					mapTile[col][row] = num;
					col++;
				}
				
				if (col == gl.maxWeltRow) {
					col = 0;
					row++;
				}
			
			}
			
			
		} catch (Exception e) {
		
		}
	}
	
	//Lädt die Welt
	public void draw(Graphics2D g2) {
		
		
		spriteCounter++;
		if (spriteCounter > animationspeed && gl.gameState == gl.playState) {				//geschwindigkeit der animation. je höher dest langsamer
			if (spriteNum == 1) {
				spriteNum = 2;
			}
			else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
		
		
		int col = 0;
		int row = 0;
		
		
		while (col < gl.maxWeltCol && row < gl.maxWeltRow) {
			
			int tileNum = mapTile[col][row];
			
			int x = col * gl.unitsize;
			int y = row * gl.unitsize;
			
			
			int scX = x - gl.player.posX + gl.player.camX;
			int scY = y - gl.player.posY + gl.player.camY;
			
			if (x + (gl.unitsize * 2) > gl.player.posX - gl.player.camX && x - (gl.unitsize * 2) < gl.player.posX + gl.player.camX &&
				y + (gl.unitsize * 2) > gl.player.posY - gl.player.camY && x - (gl.unitsize * 2) < gl.player.posX + gl.player.camX) {
				
				//Wenn Tile baum
				if (tileNum == 5) {
					g2.drawImage(tile[5].image, scX, scY, gl.baumsize, gl.baumsize , null);
				}
				
				//Wenn Wasser tile ist dann animation
				else if (tileNum == 2) {
					if(spriteNum == 1) {
						g2.drawImage(tile[tileNum].image, scX, scY,gl.unitsize, gl.unitsize, null);
					}
					if(spriteNum == 2) {
						g2.drawImage(tile[10].image, scX, scY,gl.unitsize, gl.unitsize, null);
					}
				} 	
				
				//Wenn Lava Tile ist dann Lava animation
				else if (tileNum == 8) {
					if(spriteNum == 1) {
						g2.drawImage(tile[tileNum].image, scX, scY,gl.unitsize, gl.unitsize, null);
					}
					if(spriteNum == 2) {
						g2.drawImage(tile[11].image, scX, scY,gl.unitsize, gl.unitsize, null);
					}
				} 	
			
				//Restliche Tiles
				else {
					g2.drawImage(tile[tileNum].image, scX, scY,gl.unitsize, gl.unitsize, null);
				}
			}
			
			
			col++;
			
			if (col == gl.maxWeltCol) {
				col = 0;
				row++;	
			}	
		}
	}
}
