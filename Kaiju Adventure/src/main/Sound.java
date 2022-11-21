package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[10];
	
	public Sound() {
		//soundURL[0] = getClass().getResource("/sound/maintheme.au");
		soundURL[1] = getClass().getResource("/sound/key.au");
		soundURL[2] = getClass().getResource("/sound/door.au");
	}
	
	public void setFile(int i) {
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			
		}
	}
	
	//start
	public void play() {
		clip.start();
	}
	
	//loop
	public void loop() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	//stop
	public void stop() {
		clip.stop();
	}
}
