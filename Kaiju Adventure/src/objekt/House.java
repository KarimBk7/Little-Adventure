package objekt;

import main.GameLoop;
import main.ScaleTool;

public class House extends Objekt{
	ScaleTool sTool = new ScaleTool();
	
	public House (GameLoop gl) {
		name = "house";
		isCollision = true;
		image = sTool.setupImage("objekt", "house", gl.buildingsize, gl.buildingsize);
		hitboxX = 20;
		hitbox.height = gl.buildingsize;
		hitbox.width = gl.buildingsize - 40;
		
	}
	
}
