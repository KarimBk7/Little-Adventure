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
	
	public int checkObjekt(Unit unit, boolean player) {
		
		int index = 99;
		
		for (int i = 0; i < gl.obj.length; i++) {
			
			if (gl.obj[i] != null) {
				
				//Unit's hitbox
				unit.hitbox.x = unit.posX + unit.hitbox.x;
				unit.hitbox.y = unit.posY + unit.hitbox.y;
				
				//objekt's hitbox
				gl.obj[i].hitbox.x = gl.obj[i].posX	+ gl.obj[i].hitbox.x;
				gl.obj[i].hitbox.y = gl.obj[i].posY + gl.obj[i].hitbox.y;
				
				switch (unit.richtung) {
				case "up":	
					unit.hitbox.y -= unit.speed;
					if(unit.hitbox.intersects(gl.obj[i].hitbox)) {
						if(gl.obj[i].isCollision == true) {
							unit.iscollision = true;
						}
						if (player == true) {
							index = i;
						}
					}
					break;
				case "down":
					unit.hitbox.y += unit.speed;
					if(unit.hitbox.intersects(gl.obj[i].hitbox)) {
						if(gl.obj[i].isCollision == true) {
							unit.iscollision = true;
						}
						if (player == true) {
							index = i;
						}
					}
					break;
				case "left":
					unit.hitbox.x -= unit.speed;
					if(unit.hitbox.intersects(gl.obj[i].hitbox)) {
						if(gl.obj[i].isCollision == true) {
							unit.iscollision = true;
						}
						if (player == true) {
							index = i;
						}
					}
					break;
				case "right":
					unit.hitbox.x += unit.speed;
					if(unit.hitbox.intersects(gl.obj[i].hitbox)) {
						if(gl.obj[i].isCollision == true) {
							unit.iscollision = true;
						}
						if (player == true) {
							index = i;
						}
					}
					break;
				}
				unit.hitbox.x = unit.hitboxX;
				unit.hitbox.y = unit.hitboxY;
				gl.obj[i].hitbox.x = gl.obj[i].hitboxX;
				gl.obj[i].hitbox.y = gl.obj[i].hitboxY;
			}	
		}
		return index;
	}
}
