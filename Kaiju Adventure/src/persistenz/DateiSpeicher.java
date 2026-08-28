package persistenz;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Standard-Backend: schreibt jeden Slot als kleine Properties-Datei in das
 * Benutzerverzeichnis. Braucht keine Installation und funktioniert auf jedem
 * Rechner, auf dem das Spiel startet.
 */
public class DateiSpeicher implements SpielstandSpeicher {

	private final Path ordner;

	public DateiSpeicher() {
		this.ordner = standardOrdner();
	}

	/** -Dkaiju.save.dir, sonst %APPDATA%/KaijuAdventure bzw. ~/.kaiju-adventure. */
	private static Path standardOrdner() {
		String eigener = System.getProperty("kaiju.save.dir");
		if (eigener != null && !eigener.isBlank()) {
			return Paths.get(eigener);
		}
		String appdata = System.getenv("APPDATA");
		if (appdata != null && !appdata.isBlank()) {
			return Paths.get(appdata, "KaijuAdventure");
		}
		return Paths.get(System.getProperty("user.home"), ".kaiju-adventure");
	}

	private Path datei(int slot) {
		return ordner.resolve("spielstand-" + slot + ".properties");
	}

	@Override
	public boolean speichern(int slot, Spielstand st) {

		Properties p = new Properties();
		p.setProperty("zeitGesamt", Long.toString(st.zeitGesamt));
		p.setProperty("health", Integer.toString(st.health));
		p.setProperty("posX", Integer.toString(st.posX));
		p.setProperty("posY", Integer.toString(st.posY));
		p.setProperty("strenght", Integer.toString(st.strenght));
		p.setProperty("speed", Integer.toString(st.speed));
		p.setProperty("keys", Integer.toString(st.keys));
		p.setProperty("apfel", Integer.toString(st.apfel));
		p.setProperty("heiltrank", Integer.toString(st.heiltrank));
		p.setProperty("itDollar", Integer.toString(st.itDollar));
		p.setProperty("exp", Integer.toString(st.exp));
		p.setProperty("schaufel", Boolean.toString(st.schaufel));
		p.setProperty("spitzhacke", Boolean.toString(st.spitzhacke));
		p.setProperty("shopStaerke", Integer.toString(st.shopStaerke));
		p.setProperty("shopSchnelligkeit", Integer.toString(st.shopSchnelligkeit));
		p.setProperty("shopSpitzhacke", Integer.toString(st.shopSpitzhacke));
		p.setProperty("npcDialogIndex", alsText(st.npcDialogIndex));
		p.setProperty("objStatus", alsText(st.objStatus));

		try {
			Files.createDirectories(ordner);
			try (Writer w = Files.newBufferedWriter(datei(slot), StandardCharsets.UTF_8)) {
				p.store(w, "Kaiju Adventure - Spielstand " + slot);
			}
			return true;
		} catch (IOException e) {
			System.err.println("[Speicher] Spielstand " + slot + " konnte nicht geschrieben werden: " + e.getMessage());
			return false;
		}
	}

	@Override
	public Spielstand laden(int slot) {

		Path f = datei(slot);
		if (!Files.exists(f)) {
			return null;
		}

		Properties p = new Properties();
		try (Reader r = Files.newBufferedReader(f, StandardCharsets.UTF_8)) {
			p.load(r);
		} catch (IOException e) {
			System.err.println("[Speicher] Spielstand " + slot + " konnte nicht gelesen werden: " + e.getMessage());
			return null;
		}

		Spielstand st = new Spielstand();
		st.zeitGesamt = zahlLang(p, "zeitGesamt");
		st.health = zahl(p, "health");
		st.posX = zahl(p, "posX");
		st.posY = zahl(p, "posY");
		st.strenght = zahl(p, "strenght");
		st.speed = zahl(p, "speed");
		st.keys = zahl(p, "keys");
		st.apfel = zahl(p, "apfel");
		st.heiltrank = zahl(p, "heiltrank");
		st.itDollar = zahl(p, "itDollar");
		st.exp = zahl(p, "exp");
		st.schaufel = Boolean.parseBoolean(p.getProperty("schaufel", "false"));
		st.spitzhacke = Boolean.parseBoolean(p.getProperty("spitzhacke", "false"));
		st.shopStaerke = zahl(p, "shopStaerke");
		st.shopSchnelligkeit = zahl(p, "shopSchnelligkeit");
		st.shopSpitzhacke = zahl(p, "shopSpitzhacke");
		st.npcDialogIndex = alsArray(p.getProperty("npcDialogIndex", ""));
		st.objStatus = alsArray(p.getProperty("objStatus", ""));
		return st;
	}

	@Override
	public void loeschen(int slot) {
		try {
			Files.deleteIfExists(datei(slot));
		} catch (IOException e) {
			System.err.println("[Speicher] Spielstand " + slot + " konnte nicht geloescht werden: " + e.getMessage());
		}
	}

	@Override
	public String name() {
		return "Datei (" + ordner + ")";
	}

	private static int zahl(Properties p, String key) {
		try {
			return Integer.parseInt(p.getProperty(key, "0").trim());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	private static long zahlLang(Properties p, String key) {
		try {
			return Long.parseLong(p.getProperty(key, "0").trim());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	private static String alsText(int[] werte) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < werte.length; i++) {
			if (i > 0) {
				sb.append(',');
			}
			sb.append(werte[i]);
		}
		return sb.toString();
	}

	private static int[] alsArray(String text) {
		if (text == null || text.isBlank()) {
			return new int[0];
		}
		String[] teile = text.split(",");
		int[] werte = new int[teile.length];
		for (int i = 0; i < teile.length; i++) {
			try {
				werte[i] = Integer.parseInt(teile[i].trim());
			} catch (NumberFormatException e) {
				werte[i] = 0;
			}
		}
		return werte;
	}
}
