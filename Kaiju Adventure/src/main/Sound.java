package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[10];
	long clipTime;
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/maintheme.wav");
		soundURL[1] = getClass().getResource("/sound/menunavigate.wav");
		soundURL[2] = getClass().getResource("/sound/getmoney.wav");
		soundURL[3] = getClass().getResource("/sound/hit.wav");
		soundURL[4] = getClass().getResource("/sound/enemyhit.wav");
		soundURL[5] = getClass().getResource("/sound/lose.wav");
		soundURL[6] = getClass().getResource("/sound/win.wav");
		soundURL[7] = getClass().getResource("/sound/upgrade.wav");
		soundURL[8] = getClass().getResource("/sound/ausverkauft.wav");
		soundURL[9] = getClass().getResource("/sound/open.wav");
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
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-30.0f);
		clip.start();
	}
	
	//loop
	public void loop() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	//stop
	public void pause() {
		clipTime = clip.getMicrosecondPosition();
		clip.stop();
	}
	
	//pause
	public void resume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-35.0f);
		clip.setMicrosecondPosition(clipTime);
		clip.start();
		
	}
	
}
