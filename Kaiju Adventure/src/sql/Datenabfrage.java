package sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import main.GameLoop;
import main.ScaleTool;
import objekt.Key;

public class Datenabfrage {

	GameLoop gl;
	ResultSet rs;
	ScaleTool sTool;
	
	//Spieler
	private int posX;
	private int posY;
	private int health;
	private int strenght;
	private int speed;
	
	//inventar
	private int keys;
	private int apfel;
	private int heiltrank;
	private boolean schaufel;
	private boolean spitzhacke;
	private int itDollar;
	private int exp;
	
	//shop
	private int shopStaerke;
	private int shopSchnelligkeit;
	private int shopSpitzhacke;
	
	
	public Datenabfrage(GameLoop gl){
		this.gl = gl;
		sTool = new ScaleTool();
	}
	
	public void speichern(int id) throws ClassNotFoundException {
		getAllInformation();
		
		Interface.connect();
		Interface.update("INSERT INTO `t_spieler`(`Leben`, `Position_X`, `Position_Y`, `Staerke`, `Schnelligkeit`,"
		+ " `Spiele_ID`) VALUES ("+ health +","+ posX +","+ posY +","+ strenght +","+ speed +","+ id +")");
		
		Interface.update("INSERT INTO `t_inventar`(`IT_Dollar`, `Schluessel`, `Apfel`, `Heiltrank`, `Erfahrung`,"
				+ " `Schaufel`, `Spitzhacke`, `Spieler_id`) VALUES ("+ itDollar +","+ keys +","+ apfel +","
				+ ""+ heiltrank +","+ exp +","+ schaufel +","+ spitzhacke +","+ id +")");
		
		Interface.update("INSERT INTO `t_shop`(`Shop_id`, `Staerketrank`, `Schnelligkeitstrank`, `Spitzhacke_shop`) "
				+ "VALUES ("+ id +","+ shopStaerke +","+ shopSchnelligkeit +","+ shopSpitzhacke +")");
		
		int i = 0;
		int primarkey = 0;
		while(gl.npc[i] != null) {
			if (id == 2) {
				primarkey = i + 4;
			}
			if (id == 3) {
				primarkey = i + 8;
			}
			Interface.update("INSERT INTO `t_npc`(`Npc_id`, `Dialog_index`, `Spiele_id`) "
					+ "VALUES ("+ primarkey +","+ gl.npc[i].dialogIndex +","+ id +")");
			i++;
			primarkey++;
		}
		
		i = 0;
		primarkey = 0;
		while(gl.obj[i] != null) {
			if (id == 2) {
				primarkey = i + 18;
			}
			if (id == 3) {
				primarkey = i + 36;
			}
			Interface.update("INSERT INTO `t_objekt`(`Objekt_id`, `Ob_Index`, `Status`, `Spiele_id`)"
					+ " VALUES ("+ primarkey +","+ i +","+ gl.obj[i].status +","+ id +")");
			i++;
			primarkey++;
		}
		
		Interface.disconnect();
	}
	
	public void laden(int id) throws ClassNotFoundException {
		
		try {
			Interface.connect();
			
			//lade Spieler attribute
			rs = Interface.select("SELECT * FROM `t_spieler` WHERE Spiele_ID = " + id);
			while (rs.next()) { 
				System.out.println(rs.getInt(1) + " a " + rs.getInt(2) + " b " + rs.getInt(3) + " c " + rs.getInt(4) + " d " + rs.getInt(5));
				gl.player.health = rs.getInt(1);
				gl.player.posX = rs.getInt(2);
				gl.player.posY = rs.getInt(3);
				gl.player.strenght = rs.getInt(4);
				gl.player.speed = rs.getInt(5);
			}
			
			//ladet Inventar
			rs = Interface.select("SELECT * FROM `t_inventar` WHERE Spieler_id = " + id);
			while (rs.next()) { 
				gl.player.itDollar = rs.getInt(1);
				gl.player.amountKey = rs.getInt(2);
				gl.player.amountApfel = rs.getInt(3);
				gl.player.healing_potion = rs.getInt(4);
				gl.player.exp = rs.getInt(5);
				gl.player.hatSchaufel = rs.getBoolean(6);
				gl.player.hatSpitzhacke = rs.getBoolean(7);
			}
			
			//lade shop-kapazität
			rs = Interface.select("SELECT * FROM `t_shop` WHERE Shop_id = " + id);
			while (rs.next()) { 
				gl.keyI.strenght = rs.getInt(2);
				gl.keyI.speed = rs.getInt(3);
				gl.keyI.spitzhacke = rs.getInt(4);
				
			}
			
			//lade npc-dialogindex
			int i = 0;
			int primarkey = 0;
			while (gl.npc[i] != null) {
				if (id == 2) {
					primarkey = i + 4;
				}
				if (id == 3) {
					primarkey = i + 8;
				}
				rs = Interface.select("SELECT * FROM `t_npc` WHERE Npc_id = " + primarkey + " AND Spiele_id = " + id);
				while (rs.next()) { 
					gl.npc[i].dialogIndex = rs.getInt(2);
				}
				i++;
				primarkey++;
			}
			
			//lade objekt-statuse
			i = 0;
			primarkey = 0;
			while (gl.obj[i] != null) {
				if (id == 2) {
					primarkey = i + 18;
				}
				if (id == 3) {
					primarkey = i + 36;
				}
				rs = Interface.select("SELECT * FROM `t_objekt` WHERE Objekt_id = " + primarkey + " AND Spiele_id = " + id);
				while (rs.next()) { 
					gl.obj[i].status = rs.getInt(3);
				}
				i++;
				primarkey++;
			}
			updateObjekte();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Interface.disconnect();
	}
	
	
	
	public void getAllInformation() {

		health = gl.player.health;
		posX = gl.player.posX;
		posY = gl.player.posY;
		strenght = gl.player.strenght;
		speed = gl.player.speed;
		
		keys = gl.player.amountKey;
		apfel = gl.player.amountApfel;
		heiltrank = gl.player.healing_potion;
		itDollar = gl.player.itDollar;
		exp = gl.player.exp;
		spitzhacke = gl.player.hatSpitzhacke;
		schaufel = gl.player.hatSchaufel;
		
		shopStaerke = gl.keyI.strenght;
		shopSchnelligkeit = gl.keyI.speed;
		shopSpitzhacke = gl.keyI.spitzhacke;
	
	}
	
	public void updateObjekte() {
		
		int i = 0;
		while(gl.obj[i] != null) {
			if (gl.obj[i].status == 2) {
				
				if (gl.obj[i].name == "closeddoor") {
					gl.obj[i].image = sTool.setupImage("objekt", "opendoor", gl.unitsize * 2, gl.unitsize * 2);
					gl.obj[i].isCollision = false;
					gl.obj[9].image = null;
					gl.monster[5].posX = 38 * gl.unitsize;
					gl.monster[5].posY = 11 * gl.unitsize;
				}
				else if (gl.obj[i].name == "Schaufel") {
					gl.obj[i].image = sTool.setupImage("objekt", "openchest", gl.unitsize, gl.unitsize);
				}
				else if (gl.obj[i].name == "loch") {
					if (gl.obj[4].status == 1) {
						gl.obj[4].posX = gl.obj[i].posX - gl.unitsize;
						gl.obj[4].posY = gl.obj[i].posY;
					}
				}
				else if (gl.obj[i].name == "apfel") {
					gl.obj[i].image = null;
				}
				else if (gl.obj[i].name == "zaun") {
					gl.obj[i].image = sTool.setupImage("objekt", "zaunopen", gl.unitsize * 2, gl.unitsize * 2);
					gl.obj[i].isCollision = false;
				}
				else if (gl.obj[i].name == "closeddoor1") {
					gl.obj[i].image = sTool.setupImage("objekt", "opendoor1", gl.unitsize * 2, gl.unitsize * 2);
					gl.obj[i].isCollision = false;
				}
				else if (gl.obj[i].name == "fels") {
					gl.obj[i].image = null;
					gl.obj[i].isCollision = false;
					if (gl.obj[8].status == 1) {
						gl.obj[8].posX = gl.obj[i].posX - gl.unitsize;
						gl.obj[8].posY = gl.obj[i].posY;
					}
				}
			}
			if (gl.obj[i].status == 1) {
				
				if (gl.obj[i].name == "closeddoor") {
					gl.obj[i].image = sTool.setupImage("objekt", "closeddoor", gl.unitsize * 2, gl.unitsize * 2);
					gl.obj[i].isCollision = true;
					gl.obj[9].image = sTool.setupImage("objekt", "dach", gl.unitsize * 20, gl.unitsize * 20);
				}
				else if (gl.obj[i].name == "loch") {
					gl.obj[4] = new Key(gl);
				}
				else if (gl.obj[i].name == "Schaufel") {
					gl.obj[i].image = sTool.setupImage("objekt", "closedchest", gl.unitsize, gl.unitsize);
				}
				else if (gl.obj[i].name == "apfel") {
					gl.obj[i].image = sTool.setupImage("objekt", "apfel", gl.unitsize, gl.unitsize);
				}
				else if (gl.obj[i].name == "zaun") {
					gl.obj[i].image = sTool.setupImage("objekt", "zauntuer", gl.unitsize * 2, gl.unitsize * 2);
					gl.obj[i].isCollision = true;
				}
				else if (gl.obj[i].name == "closeddoor1") {
					gl.obj[i].image = sTool.setupImage("objekt", "closeddoor1", gl.unitsize * 2, gl.unitsize * 2);
					gl.obj[i].isCollision = true;
				}
				else if (gl.obj[i].name == "fels") {
					gl.obj[i].image = sTool.setupImage("objekt", "felsen2", gl.unitsize, gl.unitsize);
					gl.obj[i].isCollision = true;
					gl.obj[8] = new Key(gl);
				}
			}
			i++;
		}
	}
}
