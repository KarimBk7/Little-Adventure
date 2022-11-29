package objekt;

import main.GameLoop;
import main.ScaleTool;

public class Key extends Objekt{

	ScaleTool sTool = new ScaleTool();
	public Key (GameLoop gl) {
		name = "Key";
		image = sTool.setupImage("objekt", "key", gl.unitsize, gl.unitsize);
	}
}
