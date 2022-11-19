package main;

import unit.Unit;

public class CollisionC {

	GameLoop gl;
	
	public CollisionC(GameLoop gl) {
		this.gl = gl;
	}
	
	public void checkTile(Unit unit) {
		
		int unitLeftX = unit.posX + unit.hitbox.x;
		int unitRightX = unit.posX + unit.hitbox.x + unit.hitbox.width;
		int unitTopY = unit.posY + unit.hitbox.y;
		int unitBottomY = unit.posY + unit.hitbox.y + unit.hitbox.height;
		
		int unitLeftCol = unitLeftX / gl.unitsize;
		int unitRightCol = unitRightX / gl.unitsize;
		int unitTopRow = unitTopY / gl.unitsize;
		int unitBottomRow = unitBottomY / gl.unitsize;
		
		int tileNum1, tileNum2;
		
		//Setzt isCollision auf true wenn Spieler auf festes Tiles trifft
		switch (unit.richtung) {
		case "up": 
			unitTopRow = (unitTopY - unit.speed) / gl.unitsize;
			tileNum1 = gl.tileM.mapTile[unitLeftCol][unitTopRow];
			tileNum2 = gl.tileM.mapTile[unitRightCol][unitTopRow];
			if (gl.tileM.tile[tileNum1].collision == true || gl.tileM.tile[tileNum2].collision == true) {
				unit.iscollision = true;
			}
			break;
		case "down":
			unitBottomRow = (unitBottomY + unit.speed) / gl.unitsize;
			tileNum1 = gl.tileM.mapTile[unitLeftCol][unitBottomRow];
			tileNum2 = gl.tileM.mapTile[unitRightCol][unitBottomRow];
			if (gl.tileM.tile[tileNum1].collision == true || gl.tileM.tile[tileNum2].collision == true) {
				unit.iscollision = true;
			}
			break;
		case "left":
			unitLeftCol = (unitLeftX - unit.speed) / gl.unitsize;
			tileNum1 = gl.tileM.mapTile[unitLeftCol][unitTopRow];
			tileNum2 = gl.tileM.mapTile[unitLeftCol][unitBottomRow];
			if (gl.tileM.tile[tileNum1].collision == true || gl.tileM.tile[tileNum2].collision == true) {
				unit.iscollision = true;
			}
			break;
		case "right":
			unitRightCol = (unitRightX + unit.speed) / gl.unitsize;
			tileNum1 = gl.tileM.mapTile[unitRightCol][unitTopRow];
			tileNum2 = gl.tileM.mapTile[unitRightCol][unitBottomRow];
			if (gl.tileM.tile[tileNum1].collision == true || gl.tileM.tile[tileNum2].collision == true) {
				unit.iscollision = true;
			}
			break;
		}
		
	}
}
