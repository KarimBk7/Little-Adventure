package persistenz;

/**
 * Backend zum Ablegen von Spielstaenden. Aktuell gibt es nur den
 * {@link DateiSpeicher}; liegt eine MySQL-Datenbank vor, speichert
 * sql.Datenabfrage weiterhin direkt ueber sql.Interface.
 */
public interface SpielstandSpeicher {

	/** Legt den Spielstand im Slot ab und ueberschreibt einen vorhandenen. */
	boolean speichern(int slot, Spielstand stand);

	/** Liefert den Spielstand des Slots oder {@code null}, wenn keiner da ist. */
	Spielstand laden(int slot);

	void loeschen(int slot);

	/** Kurzname fuer die Konsolenausgabe beim Start. */
	String name();
}
