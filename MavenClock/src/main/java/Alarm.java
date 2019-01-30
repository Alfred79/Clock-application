
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
			
public class Alarm {
	private boolean isOn;
	//denna alarmtid ska sättas av användaren
	private Calendar alarmTime;
	//formaterar alarmtiden till enhetlig output som string
	private DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//Konstruerar alarmet och sätter alarmtiden
	public Alarm(int year, int month, int date, int hour, int minute) {
		this.setAlarmTime(year, month, date, hour, minute);
	}
	
	//Objekt för att läsa in och spela upp alarmljudet
	private InputStream soundFileInputStream;
	@SuppressWarnings("restriction")
	private AudioStream audioStream;
	@SuppressWarnings("restriction")
	private AudioPlayer alarmSoundPlayer = null;
	File soundFile = new File("src/main/resources/Soundfiles/Alien_AlarmDrum-KevanGC-893953959.wav");
	
	
	
	public boolean isEqualTo(Calendar compare) {
		
		String inputString = dateFormater.format(compare.getTime()); 
		String alarmString = dateFormater.format(alarmTime.getTime());
		
		if(inputString.equals(alarmString)) {
			return true; 
		} else {
			return false; 
		}
		
			
			
	}
	
	public void triggerAlarm() {
		
		System.out.println("triiiiiiigeredddd");
		new Thread() {
			public void run() {
				try {
					soundFileInputStream = new FileInputStream(soundFile);
					audioStream = new AudioStream(soundFileInputStream);
					alarmSoundPlayer.player.start(audioStream);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {
					
				}
			}
		}.start();
		
	}
	
	//getters och setters
	public boolean isOn() {
		return isOn;
	}
	
	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}
	
	public String getAlarmTime() {
		String alarmTimeString = dateFormater.format(alarmTime.getTime());
		return alarmTimeString;
	}
	
	public void setAlarmTime(int year, int month, int date, int hour, int minute) {
		alarmTime = new GregorianCalendar(year, month, date, hour, minute, 0);
		//0=jan, 1=feb , osv
	}
}