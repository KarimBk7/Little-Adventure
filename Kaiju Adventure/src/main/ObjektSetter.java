package main;

import objekt.House;
import objekt.Key;

public class ObjektSetter {

	GameLoop gl;
	
	public ObjektSetter(GameLoop gl) {
		this.gl = gl;
	}
	
	public void setObjekt() {
		
		//Haupthaus
		gl.obj[0] = new House();
		gl.obj[0].posX = 44 * gl.unitsize;
		gl.obj[0].posY = 64 * gl.unitsize;
		
		//Key am See
		gl.obj[3] = new Key();					//Belibieges Objekt in array speichern
		gl.obj[3].posX = 52 * gl.unitsize;		//position angeben
		gl.obj[3].posY = 41 * gl.unitsize;
	}
}
