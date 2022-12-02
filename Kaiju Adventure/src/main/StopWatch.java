package main;

public class StopWatch {

	 private long startTime = 0;
	 private long minute;
	 private long second;
	 public long zeitGesamt = 0;
	 
	 
	 public StopWatch() {}
	 
	 public void start() {
		 this.startTime = System.currentTimeMillis();
	 }
	 
	 public void stackTime() {
		 zeitGesamt += (System.currentTimeMillis() - startTime);
	 }
	 
	 public long getMinutes() {
		
		minute = (long) Math.floor((zeitGesamt / 1000 / 60) << 0); 
		return minute;
	 }
	 
	 public long getSeconds() {
		 
		 second = (long) Math.floor((zeitGesamt / 1000) % 60);
		 return second;
	 }
}
