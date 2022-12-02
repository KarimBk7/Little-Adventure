package unit;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import main.GameLoop;
import main.KeyInput;
import main.ScaleTool;

public class Player extends Unit {

	KeyInput keyI;
	ScaleTool sTool = new ScaleTool();

	public int camX, camY;
	public int defaultspeed;
	public int strenght;
	boolean playersteht = true;

	// attack
	public Rectangle attackhitbox = new Rectangle(0, 8, 48, 32);
	boolean attacking = false;

	// inventar
	public int amountKey = 0;
	public int amountApfel = 0;
	public int haendlerquest = 0;
	public boolean hatSchaufel = false;
	public boolean hatSpitzhacke = false;
	public int itDollar = 0;
	public int exp = 0;
	public int healing_potion = 0;

	// statuseffekte
	public boolean slowness, poison = false;
	int immunityCounter = 0;

	public Player(GameLoop gl, KeyInput keyI) {
		super(gl);

		this.keyI = keyI;

		camX = (gl.screenweite / 2) - (gl.unitsize / 2);
		camY = (gl.screenhoehe / 2) - (gl.unitsize / 2);

		hitboxX = hitbox.x;
		hitboxY = hitbox.y;

		setDefault();
		getPlayerpng();

	}

	public void setDefault() {

		// Spieler-Position bei Start
		posX = 35 * gl.unitsize; //standard spawnpunkt x = 35
		posY = 67 * gl.unitsize; //standard spawnpunkt y = 67

		// Spieler- & Animationsgeschwindigkeit
		defaultspeed = 4;
		speed = defaultspeed;
		strenght = 1;
		richtung = "down";
		animationspeed = 16;

		// Spieler werte
		maxHealth = 6;
		health = maxHealth;
	}
	
	public void neuStart() {
		posX = 35 * gl.unitsize;
		posY = 67 * gl.unitsize;
		health = 4;
		richtung = "right";
		gl.oSetter.resetBoss();
	}
	
	public void fullNeuStart() {
		posX = 35 * gl.unitsize;
		posY = 67 * gl.unitsize;
		health = 4;
		richtung = "right";
		speed = defaultspeed;
		strenght = 1;
		richtung = "down";
		
		health = maxHealth;
		amountApfel = 0;
		amountKey = 0;
		hatSchaufel = false;
		hatSpitzhacke = false;
		itDollar = 0;
		exp = 0;
		healing_potion = 0;
	}

	public void getPlayerpng() {
		// movement bilder
		up = sTool.setupImage("player", "pl_up", gl.unitsize, gl.unitsize);
		up1 = sTool.setupImage("player", "pl_up1", gl.unitsize, gl.unitsize);
		up2 = sTool.setupImage("player", "pl_up2", gl.unitsize, gl.unitsize);
		down = sTool.setupImage("player", "pl_down", gl.unitsize, gl.unitsize);
		down1 = sTool.setupImage("player", "pl_down1", gl.unitsize, gl.unitsize);
		down2 = sTool.setupImage("player", "pl_down2", gl.unitsize, gl.unitsize);
		left = sTool.setupImage("player", "pl_left", gl.unitsize, gl.unitsize);
		left1 = sTool.setupImage("player", "pl_left1", gl.unitsize, gl.unitsize);
		left2 = sTool.setupImage("player", "pl_left2", gl.unitsize, gl.unitsize);
		right = sTool.setupImage("player", "pl_right", gl.unitsize, gl.unitsize);
		right1 = sTool.setupImage("player", "pl_right1", gl.unitsize, gl.unitsize);
		right2 = sTool.setupImage("player", "pl_right2", gl.unitsize, gl.unitsize);

		// angriffsbilder
		attackup1 = sTool.setupImage("player", "attackup1", gl.unitsize, gl.unitsize * 2);
		attackup2 = sTool.setupImage("player", "attackup2", gl.unitsize, gl.unitsize * 2);
		attackdown1 = sTool.setupImage("player", "attackdown1", gl.unitsize, gl.unitsize * 2);
		attackdown2 = sTool.setupImage("player", "attackdown2", gl.unitsize, gl.unitsize * 2);
		attackleft1 = sTool.setupImage("player", "attackleft1", gl.unitsize * 2, gl.unitsize);
		attackleft2 = sTool.setupImage("player", "attackleft2", gl.unitsize * 2, gl.unitsize);
		attackright1 = sTool.setupImage("player", "attackright1", gl.unitsize * 2, gl.unitsize);
		attackright2 = sTool.setupImage("player", "attackright2", gl.unitsize * 2, gl.unitsize);

	}

	public void update() {

		// wenn spiler kein leben mehr hat
		if (health < 1) {
			// gl.stopMusik();
			gl.soundEffekt(5);
			gl.ui.befehl = 0;
			gl.gameState = gl.losestate;
		}

		int npcindex;
		int objindex;
		int monsterindex;

		// immunity
		if (immunity) {
			gl.counter[2].count();
		}
		if (gl.counter[2].isDone()) {
			gl.counter[2].resetCount();
			immunity = false;
		}

		if (attacking) {
			attacking();
		} else if (keyI.ePressed) {
			if (health < maxHealth && poison == false && healing_potion > 0) {
				health++;
				healing_potion--;
				gl.soundEffekt(7);
				gethealth = true;
			} else {
				gl.soundEffekt(10);
			}
			keyI.ePressed = false;
		} else if (keyI.upPressed == true || keyI.downPressed == true || keyI.leftPressed == true
				|| keyI.rightPressed == true) {
			playersteht = false;

			if (keyI.upPressed == true) {
				if (keyI.downPressed == true) {
					laufen = "stop";
				} else if (keyI.leftPressed == keyI.rightPressed) {
					richtung = "up";
					laufen = "up";
				} else if (keyI.leftPressed == true) {
					richtung = "up";
					laufen = "upleft";
				} else if (keyI.rightPressed == true) {
					richtung = "up";
					laufen = "upright";
				}

			} else if (keyI.downPressed == true) {
				if (keyI.upPressed == true) {
				} else if (keyI.leftPressed == keyI.rightPressed) {
					richtung = "down";
					laufen = "down";
				} else if (keyI.leftPressed == true) {
					richtung = "down";
					laufen = "downleft";
				} else if (keyI.rightPressed == true) {
					richtung = "down";
					laufen = "downright";
				}
			} else if (keyI.leftPressed == true && keyI.rightPressed == true) {
				laufen = "stop";
			} else if (keyI.rightPressed == true) {
				richtung = "right";
				laufen = "right";
			} else if (keyI.leftPressed == true) {
				richtung = "left";
				laufen = "left";
			}

			// check if collision
			isCollision = false;
			gl.cc.checkTile(this);
			npcindex = gl.cc.checkUnit(this, gl.npc);
			objindex = gl.cc.checkObjekt(this, true);
			monsterindex = gl.cc.checkUnit(this, gl.monster);

			interact(objindex);
			interactNpc(npcindex);
			hitbyMonster(monsterindex);
			keyI.enterPressed = false;

			// wenn nicht collision dann laufen
			if (isCollision == false) {
				switch (richtung) {
				case "up":
					posY -= speed;
					break;
				case "left":
					posX -= speed;
					break;
				case "right":
					posX += speed;
					break;
				case "down":
					posY += speed;
					break;
				}
			}

			// Animations-Loop-Counter
			spriteCounter++;
			if (spriteCounter > animationspeed) { // geschwindigkeit der animation. je höher dest langsamer
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		} else {
			playersteht = true;
			npcindex = gl.cc.checkUnit(this, gl.npc);
			objindex = gl.cc.checkObjekt(this, true);
			monsterindex = gl.cc.checkUnit(this, gl.monster);
			interact(objindex);
			interactNpc(npcindex);
			hitbyMonster(monsterindex);
		}
	}

	private void attacking() {

		spriteCounter++;

		if (spriteCounter <= 15) {
			spriteNum = 1;
		}
		if (spriteCounter > 15 && spriteCounter <= 40) {
			spriteNum = 2;

			// sichert spielers position & hitbox
			int currentPosX = posX;
			int currentPosY = posY;
			int hitboxWith = hitbox.width;
			int hitboxHeigh = hitbox.height;

			// setzt schlag-hitbox
			switch (richtung) {
			case "up":posY -= attackhitbox.height;break;
			case "down":posY += attackhitbox.height;break;
			case "left":posX -= attackhitbox.width;break;
			case "right":posX += attackhitbox.width;break;
			}

			// ersetz hitbox mit attackhitbox
			hitbox.width = attackhitbox.width;
			hitbox.height = attackhitbox.height;

			// checkt ob mosnter getroffen wird
			int monsterindex = gl.cc.checkUnit(this, gl.monster);
			damageMonster(monsterindex);

			// nach dem checken wird player information wiederhergestellt
			posX = currentPosX;
			posY = currentPosY;
			hitbox.width = hitboxWith;
			hitbox.height = hitboxHeigh;
		}
		if (spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}

	}

	private void damageMonster(int i) {

		if (i != 99) {
			if (gl.monster[i].immunity == false) {
				gl.monster[i].health -= strenght;
				gl.monster[i].immunity = true;
				gl.soundEffekt(3);
				if (gl.monster[i].health < 1) {
					if (i == 3 || i == 4) {
						haendlerquest++;
					}
					else if (i == 5) {
						exp += 1000;
						gl.stopw.stackTime();
						gl.gameState = gl.winstate;
						gl.soundEffekt(6);
					}
					gl.monster[i] = null;
					itDollar += 100;
					exp += 100;
					gl.soundEffekt(2);
					monsterKilled = true;
				}
			}
		}
	}

	// interargieren mit objekten
	public void interact(int i) {

		if (i != 99) {

			if (keyI.enterPressed == true) {
				switch (gl.obj[i].name) {

				// Kiste oeffnen
				case "Schaufel":
					if (gl.npc[0].dialogIndex == 1 && !hatSchaufel && gl.obj[i].status == 1) {
						gl.soundEffekt(9);
						gl.obj[i].image = sTool.setupImage("objekt", "openchest", gl.unitsize, gl.unitsize);
						gl.ui.showMessage("Schaufel erhalten!", 90);
						hatSchaufel = true;
						gl.obj[i].status = 2;
					} else if (!hatSchaufel) {
						gl.soundEffekt(10);
						gl.ui.showMessage("*verschlossen*", 60);
					}
					break;

				// Grabstein
				case "Grabstein":
					gl.ui.showMessage(
							"Ich schaffte es nicht Tenbusch zu besiegen. \nIch vergrub einen Schluessel zu seiner Burg."
							+ " \nDoch nur die Voegel die hoch fliegen koennen, \nkoennen den Hinweis sichten, um ihn zu finden. ",
							480);
					break;

				// Haufen ausgraben
				case "loch":
					if (hatSchaufel == true && gl.obj[i].status == 1) {
						gl.ui.showMessage("Schluessel ausgegraben!", 60);
						gl.obj[4].posX = gl.obj[i].posX - gl.unitsize;
						gl.obj[4].posY = gl.obj[i].posY;
						gl.obj[i].status = 2;
					}
					break;
					
				case "fels":
					if (hatSpitzhacke == true && gl.obj[i].status == 1) {
						gl.ui.showMessage("Felsen zerstoert", 60);
						gl.soundEffekt(3);
						gl.obj[8].posX = 8 * gl.unitsize;
						gl.obj[8].posY = 71 * gl.unitsize;
						gl.obj[i].image = null;
						gl.obj[i].isCollision = false;
						gl.obj[i].status = 2;;
					}
					break;

				// Schlüssel einsammeln
				case "Key":
					if (gl.obj[i].status == 1) {
						gl.obj[i].image = null;
						gl.soundEffekt(2);
						amountKey++;
						gl.ui.showMessage("Schluessel erhalten!", 60);
						gl.obj[i].status = 2;
					}
					break;

				// Apfel einsammeln
				case "apfel":
					if (gl.obj[i].status == 1) {
						amountApfel++;
						gl.soundEffekt(2);
						gl.obj[i].status = 2;
						gl.obj[i].image = null;
					}
					break;

				// zaun beim haendler
				case "zaun":
					if (gl.obj[i].isCollision == true && gl.obj[i].status == 1) {
						gl.soundEffekt(9);
						gl.obj[i].status = 2;
					}
					gl.obj[i].image = sTool.setupImage("objekt", "zaunopen", gl.unitsize * 2, gl.unitsize * 2);
					gl.obj[i].isCollision = false;
					break;

				// tuer zum westen
				case "closeddoor1":
					if (amountKey > 0) {
						if (gl.obj[i].isCollision == true && gl.obj[i].status == 1) {
							gl.soundEffekt(9);
							gl.obj[i].status = 2;
						}
						gl.obj[i].image = sTool.setupImage("objekt", "opendoor1", gl.unitsize * 2, gl.unitsize * 2);
						gl.obj[i].isCollision = false;
						gl.ui.showMessage("*Tuer geoeffnet!!* \n Ab in den gefaehrlichen westen.", 240);
					} else {
						gl.ui.showMessage("*verschlossen* \nSieht so aus als ob ich 1 Schluessel benoetige", 120);
						gl.soundEffekt(10);
					}
					break;

				// Tür öffnen
				case "closeddoor":
					if (amountKey > 1) {
						if (gl.obj[i].isCollision == true && gl.obj[i].status == 1) {
							gl.soundEffekt(9);
							gl.obj[i].status = 2;
							gl.obj[i].image = sTool.setupImage("objekt", "opendoor", gl.unitsize * 2, gl.unitsize * 2);
							gl.obj[i].isCollision = false;
							gl.obj[9].image = null;
							gl.monster[5].posX = 38 * gl.unitsize;
							gl.monster[5].posY = 11 * gl.unitsize;
							gl.ui.showMessage("*Tuer geoeffnet!!* \nMoege der Kampf gegen Tenbusch beginnen!!!", 240);
						}
					} else {
						gl.ui.showMessage("*verschlossen* \nSieht so aus als ob ich 2 Schluessel benoetige", 120);
						gl.soundEffekt(10);
					}
					break;
				}
				keyI.enterPressed = false;
			} 
		}
	}

	// interagieren mit npc
	public void interactNpc(int i) {

		if (i != 99) {
			if (keyI.enterPressed == true) {
				gl.ui.messageOn = false;
				switch (gl.npc[i].name) {
				case "brandon":
					if (amountApfel > 2) {
						gl.npc[i].dialogIndex++;
						amountApfel -= 3;
					}
					gl.gameState = gl.dialogState;
					gl.npc[i].speak();
					break;

				case "angler":
					if (gl.npc[i].dialogIndex == 2) {
						gl.gameState = gl.dialogState;
						gl.npc[i].speak();
						gl.npc[i].dialogIndex = 3;
					} else {
						gl.gameState = gl.dialogState;
						gl.npc[i].speak();
						gl.npc[i].dialogIndex = 2;
					}
					break;
					
				case "momo":
					if (gl.npc[i].dialogIndex == 4) {
						gl.gameState = gl.dialogState;
						gl.npc[i].speak();
						gl.npc[i].dialogIndex = 5;
					} else if (gl.npc[i].dialogIndex == 5) {
						gl.gameState = gl.dialogState;
						gl.npc[i].speak();
						gl.npc[i].dialogIndex = 6;
					} else {
						gl.gameState = gl.dialogState;
						gl.npc[i].speak();
						gl.npc[i].dialogIndex = 4;
					}
					break;
					
				case "haendler":
					if (haendlerquest < 2 && gl.npc[i].dialogIndex == 7) {
						gl.gameState = gl.dialogState;
						gl.npc[i].speak();
					}
					else if (haendlerquest > 1 && gl.npc[i].dialogIndex == 7) {
						gl.npc[i].dialogIndex++;
						gl.gameState = gl.dialogState;
						gl.npc[i].speak();
						gl.npc[i].dialogIndex++;
					} else if (haendlerquest > 1 && gl.npc[i].dialogIndex == 9) {
						gl.gameState = gl.dialogState;
						gl.npc[i].speak();
						gl.npc[i].dialogIndex++;
					} else {
						gl.gameState = gl.dialogState;
						gl.npc[i].speak();
						gl.ui.befehl = 0;
						gl.gameState = gl.shopState;
					}
					break;
				}
			}
			keyI.enterPressed = false;
		}

		else {
			if (gl.keyI.enterPressed == true && gl.cc.checkObjekt(this, true) == 99) {
				attacking = true;
				gl.soundEffekt(12);
			}
		}
	}

	// von monster gehittet
	public void hitbyMonster(int i) {

		if (i != 99 && immunity == false) {
			switch (gl.monster[i].name) {
			case "snake":
				health--;
				poison = true;
				immunity = true;
				losthealth = true;
				break;
			}
			gl.soundEffekt(4);
		}
	}

	// zeichnet spieler
	public void draw(Graphics2D g2) {

		int tempCamX = camX;
		int tempCamY = camY;

		// Wählt Image zur Richtung
		switch (richtung) {
		case "up":
			if (!attacking) {
				if (playersteht) {
					image = up;
				} else if (spriteNum == 1) {
					image = up1;
				} else if (spriteNum == 2) {
					image = up2;
				}
			} else {
				tempCamY = camY - gl.unitsize;
				if (spriteNum == 1) {
					image = attackup1;
				}
				if (spriteNum == 2) {
					image = attackup2;
				}
			}
			break;

		case "down":
			if (!attacking) {
				if (playersteht) {
					image = down;
				} else if (spriteNum == 1) {
					image = down1;
				} else if (spriteNum == 2) {
					image = down2;
				}
			} else {
				if (spriteNum == 1) {
					image = attackdown1;
				}
				if (spriteNum == 2) {
					image = attackdown2;
				}
			}
			break;

		case "left":
			if (!attacking) {
				if (playersteht) {
					image = left;
				} else if (spriteNum == 1) {
					image = left1;
				} else if (spriteNum == 2) {
					image = left2;
				}
			} else {
				tempCamX = camX - gl.unitsize;
				if (spriteNum == 1) {
					image = attackleft1;
				}
				if (spriteNum == 2) {
					image = attackleft2;
				}
			}
			break;

		case "right":
			if (!attacking) {
				if (playersteht) {
					image = right;
				} else if (spriteNum == 1) {
					image = right1;
				} else if (spriteNum == 2) {
					image = right2;
				}
			} else {
				if (spriteNum == 1) {
					image = attackright1;
				}
				if (spriteNum == 2) {
					image = attackright2;
				}
			}
			break;
		}

		if (immunity) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		}
		g2.drawImage(image, tempCamX, tempCamY, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
}
