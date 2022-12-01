package sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import main.GameLoop;
import objekt.Objekt;
import unit.Unit;

public class Datenabfrage {

	GameLoop gl;
	ResultSet rs;
	
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
	
	//npc & objekte
	Unit npc[];
	Objekt obj[];
	
	public Datenabfrage(GameLoop gl){
		this.gl = gl;
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
			
			rs = Interface.select("SELECT * FROM `t_shop` WHERE Shop_id = " + id);
			while (rs.next()) { 
				gl.keyI.strenght = rs.getInt(2);
				gl.keyI.speed = rs.getInt(3);
				gl.keyI.spitzhacke = rs.getInt(4);
				
			}
			
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
		
		obj = gl.obj;
		npc = gl.npc;
	
	}
}
