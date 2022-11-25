package main;

public class Counter {

	public int counter;
	public int counterMax = 120;
	boolean isDone = false;
	
	public Counter() {
		counter = 0;
	}
	
	public void count() {
		counter++;
	}
	
	public boolean isDone() {
		if (counter > counterMax) {
			isDone = true;
		}
		return isDone;
	}
	
	public void resetCount() {
		isDone = false;
		counter = 0;
	}
	
	public void setCountermax(int counterMax) {
		this.counterMax =  counterMax;
	}
}
