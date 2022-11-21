package main;

import java.io.IOException;

import javax.imageio.ImageIO;

import objekt.ClosedDoor;
import objekt.Grabstein;
import objekt.Haufen;
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
		
		//Key unterm Haufen
		gl.obj[4] = new Key();					
		gl.obj[4].posX = 53 * gl.unitsize;		
		gl.obj[4].posY = 41 * gl.unitsize;	
		gl.obj[4].hitbox.x = 4;
		gl.obj[4].hitbox.y = 4;
		gl.obj[4].hitbox.width = 30;
		gl.obj[4].hitbox.x = 30;
		
		//Haufen am See
		gl.obj[5] = new Haufen();					
		gl.obj[5].posX = 53 * gl.unitsize;		
		gl.obj[5].posY = 41 * gl.unitsize;		
		
		//Grabstein am see
		gl.obj[6] = new Grabstein();
		gl.obj[6].posX = 45 * gl.unitsize;
		gl.obj[6].posY = 37 * gl.unitsize;
		
		//Tür für Bossfight geschlossen
		gl.obj[7] = new ClosedDoor(gl);
		gl.obj[7].posX = 39 * gl.unitsize;
		gl.obj[7].posY = 28 * gl.unitsize;
		
		
		gl.obj[9] = new Key();
		
		//Dach übers der Burg von Tenbusch
		gl.obj[10] = new House(gl);
		gl.obj[10].posX = 30 * gl.unitsize;
		gl.obj[10].posY = 8 * gl.unitsize;
		try {
			gl.obj[10].image = ImageIO.read(getClass().getResourceAsStream("/objekt/dach.png"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//Schaufel
		gl.obj[11] = new Schaufel();
		try {
			gl.obj[11].image = ImageIO.read(getClass().getResourceAsStream("/objekt/closedchest.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gl.obj[11].posX = 63 * gl.unitsize + 20;
		gl.obj[11].posY = 59 * gl.unitsize - 35;
	}
	
	//setzt positionen von npc
	public void setNPC() {
		
		//Brandon
		gl.npc[0] = new Npc(gl);
		gl.npc[0].posX = 61 * gl.unitsize;
		gl.npc[0].posY = 59 * gl.unitsize;
		
		
	}
}
