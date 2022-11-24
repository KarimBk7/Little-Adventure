package main;

import java.io.IOException;

import javax.imageio.ImageIO;

import objekt.Apfel;
import objekt.ClosedDoor;
import objekt.Grabstein;
import objekt.Objekt;
import objekt.House;
import objekt.Key;
import objekt.Schaufel;
import unit.Npc;

public class ObjektSetter {

	GameLoop gl;
	
	public ObjektSetter(GameLoop gl) {
		this.gl = gl;
	}
	
	//setzt Objekte
	public void setObjekt() {
		
		//Haupthaus
		gl.obj[0] = new House(gl);
		gl.obj[0].posX = 44 * gl.unitsize;
		gl.obj[0].posY = 64 * gl.unitsize;
		
		//Haus Dorf 1
		gl.obj[1] = new House(gl);
		gl.obj[1].posX = 52 * gl.unitsize;
		gl.obj[1].posY = 58 * gl.unitsize;
		
		//Haus Dorf 2
		gl.obj[2] = new House(gl);
		gl.obj[2].posX = 61 * gl.unitsize;
		gl.obj[2].posY = 55 * gl.unitsize;
		
		//Haus Schmiede
		gl.obj[3] = new House(gl);
		gl.obj[3].posX = 16 * gl.unitsize;
		gl.obj[3].posY = 14 * gl.unitsize;
		
		//Key unter der erde
		gl.obj[4] = new Key();						
		
		//loch am See
		gl.obj[5] = new Objekt("loch",53 * gl.unitsize,41 * gl.unitsize);			
		
		//Grabstein am see
		gl.obj[6] = new Grabstein();
		gl.obj[6].posX = 45 * gl.unitsize;
		gl.obj[6].posY = 37 * gl.unitsize;
		
		//Tür für Bossfight geschlossen
		gl.obj[7] = new ClosedDoor(gl);
		gl.obj[7].posX = 39 * gl.unitsize;
		gl.obj[7].posY = 28 * gl.unitsize;
		
		
		gl.obj[8] = new Key();
		
		//Dach übers der Burg von Tenbusch
		gl.obj[9] = new House(gl);
		gl.obj[9].posX = 30 * gl.unitsize;
		gl.obj[9].posY = 8 * gl.unitsize;
		try {
			gl.obj[9].image = ImageIO.read(getClass().getResourceAsStream("/objekt/dach.png"));
		} catch (Exception e) {
		}
		
		//Schaufel
		gl.obj[10] = new Schaufel();
		try {
			gl.obj[10].image = ImageIO.read(getClass().getResourceAsStream("/objekt/closedchest.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		gl.obj[10].posX = 63 * gl.unitsize + 20;
		gl.obj[10].posY = 59 * gl.unitsize - 35;
		
		//Apfel
		gl.obj[11] = new Apfel();
		gl.obj[11].posX = 58 * gl.unitsize;
		gl.obj[11].posY = 13 * gl.unitsize;
		
		gl.obj[12] = new Apfel();
		gl.obj[12].posX = 59 * gl.unitsize;
		gl.obj[12].posY = 17 * gl.unitsize;
		
		gl.obj[13] = new Apfel();
		gl.obj[13].posX = 61 * gl.unitsize;
		gl.obj[13].posY = 15 * gl.unitsize;
		
		//pier am see
		gl.obj[14] = new Objekt("pier",55 * gl.unitsize, 31 * gl.unitsize);
		try {
			gl.obj[14].image = ImageIO.read(getClass().getResourceAsStream("/objekt/pier.png"));
		} catch (Exception e) {
		}
	}
	
	//setzt positionen von npc
	public void setNPC() {
		
		//Brandon Apfelkuchen
		gl.npc[0] = new Npc(gl);
		gl.npc[0].posX = 61 * gl.unitsize;
		gl.npc[0].posY = 59 * gl.unitsize;
		gl.npc[0].name = "brandon";
		
		//NPC am pier
		gl.npc[1] =  new Npc(gl);	
		gl.npc[1].posX = 55 * gl.unitsize;
		gl.npc[1].posY = 31 * gl.unitsize;
		gl.npc[1].dialogIndex = 2;
		gl.npc[1].name = "angler";
		try {
			gl.npc[1].down1 = ImageIO.read(getClass().getResourceAsStream("/npc/angler.png"));
		} catch (Exception e) {
		
		}
		
		
		//NPC momo
		gl.npc[2] =  new Npc(gl);	
		gl.npc[2].posX = 55 * gl.unitsize;
		gl.npc[2].posY = 61 * gl.unitsize + 20;
		gl.npc[2].dialogIndex = 4;
		gl.npc[2].name = "momo";
		try {
			gl.npc[2].down1 = ImageIO.read(getClass().getResourceAsStream("/npc/momo.png"));
		} catch (Exception e) {
				
		}
	}
}
