package persistenz;

/**
 * Reiner Datencontainer fuer einen Spielstand. Enthaelt keine Spiellogik, damit
 * die Speicher-Backends (Datei bzw. MySQL) unabhaengig vom Spiel bleiben.
 */
public class Spielstand {

	//Zeit
	public long zeitGesamt;

	//Spieler
	public int health;
	public int posX;
	public int posY;
	public int strenght;
	public int speed;

	//Inventar
	public int keys;
	public int apfel;
	public int heiltrank;
	public int itDollar;
	public int exp;
	public boolean schaufel;
	public boolean spitzhacke;

	//Shop
	public int shopStaerke;
	public int shopSchnelligkeit;
	public int shopSpitzhacke;

	//Welt
	public int[] npcDialogIndex = new int[0];
	public int[] objStatus = new int[0];
}
