package objekt;

import main.GameLoop;
import main.ScaleTool;

public class ClosedDoor extends Objekt{

		ScaleTool sTool = new ScaleTool();
		public ClosedDoor(GameLoop gl) {
			name = "closeddoor";
			image = sTool.setupImage("objekt", "closeddoor", gl.unitsize * 2, gl.unitsize * 2);
			isCollision = true;
			hitbox.width = gl.unitsize * 2;
			hitbox.height = gl.unitsize * 2;
		}
	
}
