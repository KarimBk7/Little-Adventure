package unit;

import main.GameLoop;
import main.KeyInput;

public class Player extends Unit{

	GameLoop gl;
	KeyInput keyI;
	
	public Player(GameLoop gl, KeyInput keyI) {
		this.gl = gl;
		this.keyI = keyI;
	}
}
