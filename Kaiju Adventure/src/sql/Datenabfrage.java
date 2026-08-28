package sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import main.GameLoop;
import main.ScaleTool;
import objekt.Key;
import persistenz.DateiSpeicher;
import persistenz.Spielstand;
import persistenz.SpielstandSpeicher;
import unit.Monster_Snake;

public class Datenabfrage {

	GameLoop gl;
	ResultSet rs;
	ScaleTool sTool;
	
	private int totalcount;
	
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
	
	//Fallback, falls keine MySQL-Datenbank vorhanden ist
	private SpielstandSpeicher dateispeicher;
	private Boolean mysqlGeprueft;
	
	
	
	public Datenabfrage(GameLoop gl){
		this.gl = gl;
		sTool = new ScaleTool();
	}
	
	public void speichern(int id) throws ClassNotFoundException {
		getAllInformation();
		
		if (!mysqlAktiv()) {
			dateien().speichern(id, alsSpielstand());
			return;
		}
		
		Interface.connect();
		loeschSpielstand(id);
		Interface.update("INSERT INTO `t_zeit`(`zeit_in_ms`, `Spiele_id`) VALUES ("+ gl.stopw.zeitGesamt +","+ id +")");
		
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
		
		if (!mysqlAktiv()) {
			uebernehmen(dateien().laden(id));
			return;
		}
		
		try {
			Interface.connect();
			
			//ladet zeit in ms
			rs = Interface.select("SELECT * FROM `t_zeit` WHERE Spiele_ID = " + id);
			while (rs.next()) { 
				gl.stopw.zeitGesamt = rs.getLong(1);
				
			}
			
			//lade Spieler attribute
			rs = Interface.select("SELECT * FROM `t_spieler` WHERE Spiele_ID = " + id);
			while (rs.next()) { 
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
	
	public void loeschSpielstand(int id) {
		
		if (!mysqlAktiv()) {
			dateien().loeschen(id);
			return;
		}
		Interface.update("DELETE FROM `t_zeit` WHERE Spiele_id = " + id);
		Interface.update("DELETE FROM `t_inventar` WHERE Spieler_id = " + id);
		Interface.update("DELETE FROM `t_npc` WHERE Spiele_id = " + id);
		Interface.update("DELETE FROM `t_objekt` WHERE Spiele_id = " + id);
		Interface.update("DELETE FROM `t_shop` WHERE Shop_id = " + id);
		Interface.update("DELETE FROM `t_spieler` WHERE Spiele_id = " + id);
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
	
	public void chechSpielstand() throws SQLException, ClassNotFoundException {
		
		if (!mysqlAktiv()) {
			checkDateien();
			return;
		}
		Interface.connect();
		for(int i = 1; i < 4; i++) {
			rs = Interface.select("SELECT Count(*) AS total FROM `t_spieler` WHERE Spiele_ID = " + i);
			while (rs.next()) {
				totalcount = rs.getInt(1);
			}
			if (totalcount == 0 && i == 1) {
				gl.ui.dbvorhanden1 = false;
			}
			else if (totalcount == 0 && i == 2) {
				gl.ui.dbvorhanden2 = false;
			}
			else if (totalcount == 0 && i == 3) {
				gl.ui.dbvorhanden3 = false;
			}
		
			if (totalcount == 1 && i == 1) {
				gl.ui.dbvorhanden1 = true;
				rs = Interface.select("SELECT `Schluessel` FROM `t_inventar` WHERE Spieler_id = " + i);
				while (rs.next()) { 
				gl.ui.dbKey1 = rs.getInt(1);	
			}
				rs = Interface.select("SELECT `Leben` FROM `t_spieler` WHERE Spiele_id = " + i);
				while (rs.next()) { 
					gl.ui.dbhealth1 = rs.getInt(1);	
				}
			}
		
			if (totalcount == 1 && i == 2) {
				gl.ui.dbvorhanden2 = true;
				rs = Interface.select("SELECT `Schluessel` FROM `t_inventar` WHERE Spieler_id = " + i);
				while (rs.next()) { 
					gl.ui.dbKey2 = rs.getInt(1);	
				}
				rs = Interface.select("SELECT `Leben` FROM `t_spieler` WHERE Spiele_id = " + i);
				while (rs.next()) { 
					gl.ui.dbhealth2 = rs.getInt(1);	
				}
			}
		
			if (totalcount == 1 && i == 3) {
				gl.ui.dbvorhanden3 = true;
				rs = Interface.select("SELECT `Schluessel` FROM `t_inventar` WHERE Spieler_id = " + i);
				while (rs.next()) { 
					gl.ui.dbKey3 = rs.getInt(1);	
				}
				rs = Interface.select("SELECT `Leben` FROM `t_spieler` WHERE Spiele_id = " + i);
				while (rs.next()) { 
					gl.ui.dbhealth3 = rs.getInt(1);	
				}
			}
		}
	}
	
	public void updateObjekte() {
		
		int i = 0;
		while(gl.obj[i] != null) {
			//wenn mit item interargiert wurde
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
			
			//wenn mit objekt noch nicht inerargiert wurde
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
		
		if (gl.npc[3].dialogIndex > 7) {
			gl.monster[3] = null;
			gl.monster[4] = null;
		}
		else {
			//monster am schmied
			gl.monster[3] = new Monster_Snake(gl);
			gl.monster[3].posX = 13 * gl.unitsize;
			gl.monster[3].posY = 20 * gl.unitsize;
			
			gl.monster[4] = new Monster_Snake(gl);
			gl.monster[4].posX = 18 * gl.unitsize;
			gl.monster[4].posY = 20 * gl.unitsize;
		}
	}
	
	//---- Weiche MySQL / lokale Dateien ---------------------------------
	
	//einmalig pruefen, ob eine MySQL-Datenbank nutzbar ist
	private boolean mysqlAktiv() {
		
		if (mysqlGeprueft == null) {
			mysqlGeprueft = Interface.verfuegbar();
			System.out.println("[Spielstand] " + (mysqlGeprueft ? "MySQL" : "lokale Dateien"));
		}
		return mysqlGeprueft;
	}
	
	private SpielstandSpeicher dateien() {
		
		if (dateispeicher == null) {
			dateispeicher = new DateiSpeicher();
		}
		return dateispeicher;
	}
	
	//baut aus den von getAllInformation() eingesammelten Werten einen Spielstand
	private Spielstand alsSpielstand() {
		
		Spielstand st = new Spielstand();
		
		st.zeitGesamt = gl.stopw.zeitGesamt;
		
		st.health = health;
		st.posX = posX;
		st.posY = posY;
		st.strenght = strenght;
		st.speed = speed;
		
		st.keys = keys;
		st.apfel = apfel;
		st.heiltrank = heiltrank;
		st.itDollar = itDollar;
		st.exp = exp;
		st.schaufel = schaufel;
		st.spitzhacke = spitzhacke;
		
		st.shopStaerke = shopStaerke;
		st.shopSchnelligkeit = shopSchnelligkeit;
		st.shopSpitzhacke = shopSpitzhacke;
		
		int anzahlNpc = 0;
		while (gl.npc[anzahlNpc] != null) {
			anzahlNpc++;
		}
		st.npcDialogIndex = new int[anzahlNpc];
		for (int i = 0; i < anzahlNpc; i++) {
			st.npcDialogIndex[i] = gl.npc[i].dialogIndex;
		}
		
		int anzahlObj = 0;
		while (gl.obj[anzahlObj] != null) {
			anzahlObj++;
		}
		st.objStatus = new int[anzahlObj];
		for (int i = 0; i < anzahlObj; i++) {
			st.objStatus[i] = gl.obj[i].status;
		}
		
		return st;
	}
	
	//spielt einen aus der Datei gelesenen Spielstand ein
	private void uebernehmen(Spielstand st) {
		
		if (st != null) {
			
			gl.stopw.zeitGesamt = st.zeitGesamt;
			
			gl.player.health = st.health;
			gl.player.posX = st.posX;
			gl.player.posY = st.posY;
			gl.player.strenght = st.strenght;
			gl.player.speed = st.speed;
			
			gl.player.itDollar = st.itDollar;
			gl.player.amountKey = st.keys;
			gl.player.amountApfel = st.apfel;
			gl.player.healing_potion = st.heiltrank;
			gl.player.exp = st.exp;
			gl.player.hatSchaufel = st.schaufel;
			gl.player.hatSpitzhacke = st.spitzhacke;
			
			gl.keyI.strenght = st.shopStaerke;
			gl.keyI.speed = st.shopSchnelligkeit;
			gl.keyI.spitzhacke = st.shopSpitzhacke;
			
			int i = 0;
			while (gl.npc[i] != null) {
				if (i < st.npcDialogIndex.length) {
					gl.npc[i].dialogIndex = st.npcDialogIndex[i];
				}
				i++;
			}
			
			i = 0;
			while (gl.obj[i] != null) {
				if (i < st.objStatus.length) {
					gl.obj[i].status = st.objStatus[i];
				}
				i++;
			}
		}
		
		updateObjekte();
	}
	
	//Gegenstueck zu chechSpielstand() fuer den Dateispeicher
	private void checkDateien() {
		
		for(int i = 1; i < 4; i++) {
			
			Spielstand st = dateien().laden(i);
			
			if (st == null && i == 1) {
				gl.ui.dbvorhanden1 = false;
			}
			else if (st == null && i == 2) {
				gl.ui.dbvorhanden2 = false;
			}
			else if (st == null && i == 3) {
				gl.ui.dbvorhanden3 = false;
			}
			
			if (st != null && i == 1) {
				gl.ui.dbvorhanden1 = true;
				gl.ui.dbKey1 = st.keys;
				gl.ui.dbhealth1 = st.health;
			}
			
			if (st != null && i == 2) {
				gl.ui.dbvorhanden2 = true;
				gl.ui.dbKey2 = st.keys;
				gl.ui.dbhealth2 = st.health;
			}
			
			if (st != null && i == 3) {
				gl.ui.dbvorhanden3 = true;
				gl.ui.dbKey3 = st.keys;
				gl.ui.dbhealth3 = st.health;
			}
		}
	}
}
