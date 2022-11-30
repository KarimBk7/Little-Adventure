package sql;

import main.GameLoop;
import objekt.Objekt;
import unit.Unit;

public class Datenabfrage {

	GameLoop gl;
	
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
	
	//npc & objekte
	Unit npc[];
	Objekt obj[];
	
	public Datenabfrage(GameLoop gl){
		this.gl = gl;
	}
	
	public void speichern(int id) throws ClassNotFoundException {
		getAllInformation();
		
		Interface.connect();
		Interface.update("INSERT INTO `t_spieler`(`Leben`, `Position X`, `Position Y`, `Staerke`, `Schnelligkeit`,"
		+ " `Spiele_ID`) VALUES ("+ health +","+ posX +","+ posY +","+ strenght +","+ speed +","+ id +")");
		Interface.disconnect();
	}
	
	public void laden() {
		Interface.update("");
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
		schaufel = gl.player.hatSpitzhacke;
		
		obj = gl.obj;
		npc = gl.npc;
	
	}
}
