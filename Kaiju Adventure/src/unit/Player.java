package unit;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GameLoop;
import main.KeyInput;
import main.ScaleTool;

public class Player extends Unit{

	KeyInput keyI;

	public int camX, camY;
	public int defaultspeed;
	public int strenght;
	boolean playersteht = true;
	
	//attack
	public Rectangle attackhitbox = new Rectangle(0, 8, 48, 32);
	boolean attacking = false;
	
	//inventar
	public int amountKey = 0;
	public int amountApfel = 0;
	public int schmiedquest = 0;
	public boolean hatSchaufel, hatSpitzhacke = false;
	public int itDollar = 0; 
	public int itDollargesamt = 0;
	public int healing_potion = 0;
	
	//statuseffekte
	public boolean slowness, poison = false;
	int immunityCounter = 0;

	
	
	public Player(GameLoop gl, KeyInput keyI) {
		super(gl);
	
		this.keyI = keyI;
		
		camX = (gl.screenweite / 2) - (gl.unitsize / 2);
		camY = (gl.screenhoehe / 2) - (gl.unitsize / 2);	
		
		hitboxX = hitbox.x;
		hitboxY = hitbox.y;
		
		setTest();
		getPlayerpng();
		
	}
	
	public void setTest() {
		
		//Spieler-Position bei Start
		posX = 11 * gl.unitsize;			
		posY = 62 * gl.unitsize; 
		
		//Spieler- & Animationsgeschwindigkeit
		defaultspeed = 10;
		speed = defaultspeed; 
		strenght = 1;
		richtung = "down";
		animationspeed = 16;
		
		//Spieler werte
		maxHealth = 3;
		health = maxHealth;
	}
	
	public void getPlayerpng() {
		ScaleTool sTool = new ScaleTool();
			//movement bilder
			up = sTool.setupImage("pl_up", gl.unitsize, gl.unitsize);
			up1 = sTool.setupImage("pl_up1", gl.unitsize, gl.unitsize);
			up2 = sTool.setupImage("pl_up2", gl.unitsize, gl.unitsize);
			down = sTool.setupImage("pl_down", gl.unitsize, gl.unitsize);
			down1 = sTool.setupImage("pl_down1", gl.unitsize, gl.unitsize);
			down2 = sTool.setupImage("pl_down2", gl.unitsize, gl.unitsize);
			left = sTool.setupImage("pl_left", gl.unitsize, gl.unitsize);
			left1 = sTool.setupImage("pl_left1", gl.unitsize, gl.unitsize);
			left2 = sTool.setupImage("pl_left2", gl.unitsize, gl.unitsize);
			right = sTool.setupImage("pl_right", gl.unitsize, gl.unitsize);
			right1 = sTool.setupImage("pl_right1", gl.unitsize, gl.unitsize);
			right2 = sTool.setupImage("pl_right2", gl.unitsize, gl.unitsize);
			
			//angriffsbilder
			attackup1 = sTool.setupImage("attackup1", gl.unitsize, gl.unitsize * 2);
			attackup2 = sTool.setupImage("attackup2", gl.unitsize, gl.unitsize * 2);
			attackdown1 = sTool.setupImage("attackdown1", gl.unitsize, gl.unitsize * 2);
			attackdown2 = sTool.setupImage("attackdown2", gl.unitsize, gl.unitsize * 2);
			attackleft1 = sTool.setupImage("attackleft1", gl.unitsize * 2, gl.unitsize);
			attackleft2 = sTool.setupImage("attackleft2", gl.unitsize * 2, gl.unitsize);
			attackright1 = sTool.setupImage("attackright1", gl.unitsize * 2, gl.unitsize);
			attackright2 = sTool.setupImage("attackright2", gl.unitsize * 2, gl.unitsize);
			
	}
	
	public void update ()  {
		
		if (health < 1) {
			//gl.stopMusik();
			gl.soundEffekt(5);
			gl.gameState = gl.losestate;
		}
		
		int npcindex;
		int objindex;
		int monsterindex;
		
		if (immunity) {
			 gl.counter[2].count();
		}
		if (gl.counter[2].isDone()) {
			gl.counter[2].resetCount();
			immunity = false;
		}
		
		if (attacking) {
			attacking();
		}
		else if (keyI.ePressed) {
			if (health < maxHealth ) {
				health++;
				healing_potion--;
				gl.soundEffekt(7);
				gethealth = true;
			}
			else {
				gl.soundEffekt(10);
			}
			keyI.ePressed = false;
		}
		else if(keyI.upPressed == true || keyI.downPressed == true || 
				keyI.leftPressed == true || keyI.rightPressed == true) {
			playersteht = false;
			
			if (keyI.upPressed == true) {							
				if (keyI.downPressed == true) {	
					laufen= "stop";									
				}
				else if (keyI.leftPressed == keyI.rightPressed) {	
					richtung = "up";
					laufen = "up";
				}
				else if (keyI.leftPressed == true) {				
					richtung = "up";
					laufen = "upleft";
				}
				else if (keyI.rightPressed == true) {				
					richtung = "up";
					laufen = "upright";
				}
				
			}
			else if (keyI.downPressed == true) {					
				if (keyI.upPressed == true) {						
				}
				else if (keyI.leftPressed == keyI.rightPressed) {	
					richtung = "down";
					laufen = "down";
				}
				else if (keyI.leftPressed == true) {				
					richtung = "down";
					laufen = "downleft";
				}
				else if (keyI.rightPressed == true) {				
					richtung = "down";
					laufen = "downright";
				}
			}
			else if (keyI.leftPressed == true && keyI.rightPressed == true) {	
				laufen = "stop";		
			}
			else if (keyI.rightPressed == true) {																			
					richtung = "right";
					laufen = "right";
			}
			else if (keyI.leftPressed == true) {
				richtung = "left";
				laufen = "left";
			}
			
			
			//check if collision
			isCollision = false;
			gl.cc.checkTile(this);
			npcindex = gl.cc.checkUnit(this, gl.npc);
			objindex = gl.cc.checkObjekt(this, true);
			monsterindex = gl.cc.checkUnit(this, gl.monster);
			
			interact(objindex);
			interactNpc(npcindex);
			hitbyMonster(monsterindex);
			keyI.enterPressed = false;
			
			//wenn nicht collision dann laufen
			if (isCollision == false) {
				switch (richtung) {
				case "up": posY -= speed;
					break;
				case "left": posX -= speed;
					break;
				case "right": posX += speed;
					break;
				case "down": posY += speed;
					break;
				}
			}
			
			//Animations-Loop-Counter
			spriteCounter++;
			if (spriteCounter > animationspeed) {								//geschwindigkeit der animation. je höher dest langsamer
				if (spriteNum == 1) {
					spriteNum = 2;
				}
				else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		else {
		playersteht = true;
		npcindex = gl.cc.checkUnit(this,gl.npc);
		objindex = gl.cc.checkObjekt(this, true);
		monsterindex = gl.cc.checkUnit(this, gl.monster);
		interact(objindex);
		interactNpc(npcindex);
		hitbyMonster(monsterindex);
		}
	}

	private void attacking() {
		
		spriteCounter++;
		
		if (spriteCounter <= 5) {
			spriteNum = 1;
		}
		if (spriteCounter  > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			
			//sichert spielers position & hitbox
			int currentPosX = posX;
			int currentPosY = posY;
			int hitboxWith = hitbox.width;
			int hitboxHeigh = hitbox.height;
			
			//setzt schlag-hitbox
			switch (richtung) {
			case "up": posY -= attackhitbox.height;break;
			case "down": posY += attackhitbox.height;break;
			case "left": posX -= attackhitbox.width;break;
			case "right": posX += attackhitbox.width;break;		
			}
			
			//ersetz hitbox mit attackhitbox
			hitbox.width = attackhitbox.width;
			hitbox.height = attackhitbox.height;
			
			//checkt ob mosnter getroffen wird
			int monsterindex = gl.cc.checkUnit(this, gl.monster);
			damageMonster(monsterindex);
			
			//nach dem checken wird player information wiederhergestellt
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
						schmiedquest++;
					}
					gl.monster[i] = null;
					itDollar += 100;
					itDollargesamt += 100;;
					gl.soundEffekt(2);
					mosterKilled = true;
				}
			}	
		}
	}

	//interargieren mit objekten
	public void interact(int i) {
		
		if (i != 99) {
		
			switch (gl.obj[i].name) {
			
			//Kiste oeffnen 
			case "Schaufel":
				if (keyI.enterPressed == true && gl.npc[0].dialogIndex == 1) {
					try {
						gl.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/objekt/openchest.png"));
					} catch (IOException e) {
						e.printStackTrace();
					};
					gl.ui.showMessage("Schaufel erhalten!", 90);
					hatSchaufel = true;
				}
				else if(keyI.enterPressed == true) {
					gl.ui.showMessage("*verschlossen*", 60);
				}
				break;
				
			//Grabstein
			case "Grabstein":
				if (keyI.enterPressed == true) {		
					gl.ui.showMessage("Ich schaffte es nicht Tenbusch zu besiegen. \nIch vergrub einen Schluessel zu seiner Burg."
							+ " \nDoch nur die Voegel die hoch fliegen koennen, \nkoennen den Hinweis sichten, um ihn zu finden. ", 480);
				}
				break;
				
			//Haufen ausgraben
			case "loch":
				if (keyI.enterPressed == true) {
					if (hatSchaufel == true) {
						gl.ui.showMessage("Schluessel ausgegraben!", 60);
						gl.obj[4].posX = gl.obj[i].posX - gl.unitsize;
						gl.obj[4].posY = gl.obj[i].posY;
						gl.obj[i] = null;
					}
				}
				
				break;
				
			//Schlüssel einsammeln	
			case "Key":
				if (keyI.enterPressed == true) {
					gl.obj[i] = null;
					//gl.soundEffekt(1);
					amountKey++;
					gl.ui.showMessage("Schluessel erhalten!", 60);
				}
				break;
				
				//Apfel einsammeln
			case "apfel":
				if(keyI.enterPressed == true) {
					gl.obj[i] = null;
					amountApfel++;
				}
				break;
				
			//Tür öffnen	
			case "closeddoor":
				if (keyI.enterPressed == true) {
					if(amountKey > 1) {
						//gl.soundEffekt(2);
						try {
							gl.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/Objekt/opendoor.png"));
							gl.obj[i].isCollision = false;
							gl.obj[9] = null;
						} catch (Exception e) {
							
						} 
						gl.ui.showMessage("Tuer geoeffnet! \nMoege der Kampf gegen Tenbusch\nbeginnen!!!", 120);
					}
					else {
						gl.ui.showMessage("*verschlossen* \nSieht so aus als ob ich 2 Schluessel benoetige", 120);
					}
				}
				break;
			}
		}
	}
	
	//interagieren mit npc
	public void interactNpc(int i) {
		
		if (i != 99) {
			if (keyI.enterPressed == true) {
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
					}
					else {
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
					}
					else if (gl.npc[i].dialogIndex == 5){
						gl.gameState = gl.dialogState;
						gl.npc[i].speak();
						gl.npc[i].dialogIndex = 6;
					}
					else {
						gl.gameState = gl.dialogState;
						gl.npc[i].speak();
						gl.npc[i].dialogIndex = 4;
					}
					break;
				case "haendler":
					if (schmiedquest < 1) {
						gl.gameState = gl.dialogState;
						gl.npc[i].speak();
					}
					if (schmiedquest > 1 && gl.npc[i].dialogIndex == 7) {
						gl.npc[i].dialogIndex++;
						gl.gameState = gl.dialogState;
						gl.npc[i].speak();
						gl.npc[i].dialogIndex++;
					}
					else if (schmiedquest > 1 && gl.npc[i].dialogIndex == 9) {
						gl.gameState = gl.dialogState;
						gl.npc[i].speak();
						gl.npc[i].dialogIndex++;
					}
					else if (schmiedquest > 1) {
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
			if (gl.keyI.enterPressed == true) {
				attacking = true;
			}
		}
	}
	
	//von monster gehittet
	public void hitbyMonster(int i) {
		
		if (i!=99 && immunity == false) {
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
	
	//zeichnet spieler
	public void draw(Graphics2D g2) {
		
		int tempCamX = camX;
		int tempCamY = camY;
		
		//Wählt Image zur Richtung
		switch(richtung) {
		case "up":
			if (!attacking) {
				if (playersteht) {image = up;}
				else if(spriteNum == 1) {image = up1;}
				else if(spriteNum == 2) {image = up2;}	
			}
			else {
				tempCamY = camY - gl.unitsize;
				if(spriteNum == 1) {image = attackup1;}
				if(spriteNum == 2) {image = attackup2;}	
			}
			break;
			
		case "down":
			if (!attacking) {
				if (playersteht) {image = down;}
				else if(spriteNum == 1) {image = down1;}
				else if(spriteNum == 2) {image = down2;}
			}
			else {
				if(spriteNum == 1) {image = attackdown1;}
				if(spriteNum == 2) {image = attackdown2;}
			}
			break;
			
		case "left":
			if (!attacking) {
				if (playersteht) {image = left;}
				else if(spriteNum == 1) {image = left1;}
				else if(spriteNum == 2) {image = left2;}
			}
			else {
				tempCamX = camX - gl.unitsize;
				if(spriteNum == 1) {image = attackleft1;}
				if(spriteNum == 2) {image = attackleft2;}
			}
			break;
			
		case "right":
			if (!attacking) {
				if (playersteht) {image = right;}
				else if(spriteNum == 1) {image = right1;}
				else if(spriteNum == 2) {image = right2;}
			}
			else {
				if(spriteNum == 1) {image = attackright1;}
				if(spriteNum == 2) {image = attackright2;}
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
