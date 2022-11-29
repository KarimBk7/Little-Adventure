package objekt;

import main.GameLoop;
import main.ScaleTool;

public class Grabstein extends Objekt{

	ScaleTool sTool = new ScaleTool();
	public Grabstein(GameLoop gl) {
		name = "Grabstein";
		image = sTool.setupImage("objekt", "grabstein", gl.unitsize, gl.unitsize * 2);
		hitbox.height = 96;
		isCollision = true;
	}
}
