package main;

public class CountSetter {
	
	GameLoop gl;
	
	public CountSetter(GameLoop gl) {
		this.gl = gl;
	}
	
	public void setCounter() {
		
		
		gl.counter[0] = new Counter();
		
		gl.counter[1] = new Counter();
		
		gl.counter[2] = new Counter();
	}

}
