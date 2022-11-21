package main;

import objekt.ClosedDoor;
import objekt.Grabstein;
import objekt.Haufen;
import objekt.House;
import objekt.Key;
import objekt.OpenDoor;

public class ObjektSetter {

	GameLoop gl;
	
	public ObjektSetter(GameLoop gl) {
		this.gl = gl;
	}
	
	public void setObjekt() {
		
		//Haupthaus
		gl.obj[0] = new House(gl);
		gl.obj[0].posX = 44 * gl.unitsize;
		gl.obj[0].posY = 64 * gl.unitsize;
		
		gl.obj[3] = new Haufen();					
		gl.obj[3].posX = 53 * gl.unitsize;		//Belibieges Objekt in array speichern
		gl.obj[3].posY = 41 * gl.unitsize;		//position angeben
		
		gl.obj[4] = new Grabstein();
		gl.obj[4].posX = 45 * gl.unitsize;
		gl.obj[4].posY = 37 * gl.unitsize;
		
		gl.obj[5] = new ClosedDoor(gl);
		gl.obj[5].posX = 39 * gl.unitsize;
		gl.obj[5].posY = 28 * gl.unitsize;
		
		gl.obj[6] = new OpenDoor(gl);
	
		gl.obj[8] = new Key();					
		
		gl.obj[9] = new Key();
	}
}
