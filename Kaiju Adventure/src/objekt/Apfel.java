package objekt;

import main.GameLoop;
import main.ScaleTool;

public class Apfel extends Objekt{
	
	ScaleTool sTool = new ScaleTool();
	public Apfel(GameLoop gl) {
		name = "apfel";
		image = sTool.setupImage("objekt", "apfel", gl.unitsize, gl.unitsize);
	
	}
}
