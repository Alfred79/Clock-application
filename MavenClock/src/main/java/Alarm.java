
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JOptionPane;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class Alarm {
	
	//denna alarmtid ska sättas av användaren
	private Calendar alarmTime;
	private boolean alarmOn; 
	//formaterar alarmtiden till enhetlig output som string
	private DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//Objekt för att läsa in och spela upp alarmljudet
		private InputStream soundFileInputStream;
		@SuppressWarnings("restriction")
		private AudioStream audioStream;
		@SuppressWarnings("restriction")
		private AudioPlayer alarmSoundPlayer = null;
		
	private ArrayList<File> soundFiles = new ArrayList<File>();
	private File defaultAlarmSoundFile; 
	
	
	//Konstruerar alarmet
	public Alarm() {
		compileSoundFiles();	
	}
	
	//Konstruerar alarmet och sätter alarmtiden
	public Alarm(int year, int month, int date, int hour, int minute) {
		compileSoundFiles();
		this.setAlarmTime(year, month, date, hour, minute);
	}
	
	//lägger till valbara ljud till listan
		private void compileSoundFiles() {
			File alarmDrum = new File("src/main/resources/Soundfiles/Alien_AlarmDrum-KevanGC-893953959.wav");
			File alert = new File("src/main/resources/Soundfiles/sms-alert-5-daniel_simon.wav.wav");
			File buzzer = new File("src/main/resources/Soundfiles/Loud_Alarm_Clock_Buzzer-Muk1984-493547174.wav");
			soundFiles.add(alarmDrum); 
			soundFiles.add(alert);
			soundFiles.add(buzzer);
			//Default ljudinställning
			defaultAlarmSoundFile = soundFiles.get(0);	
			}
		
		
		
	//jämför om alarmtiden är samma tidpunkt som ett annnat kalenderobjekt
	public boolean isEqualTo(Calendar compare) {
		
		String inputString = dateFormater.format(compare.getTime());
		String alarmString = dateFormater.format(alarmTime.getTime());
		
		if (inputString.equals(alarmString)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	
	public void triggerAlarm() {
		new Thread() {
			public void run() {
				try {
					soundFileInputStream = new FileInputStream(defaultAlarmSoundFile);
					audioStream = new AudioStream(soundFileInputStream);
					alarmSoundPlayer.player.start(audioStream);
					System.out.println("alarm triggered");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {
				}
			}
		}.start();
		
	}
		//Ändra vald ljudfil. 
			private void changeDefaultSoundFile(int soundFileIndex) {
			defaultAlarmSoundFile = soundFiles.get(soundFileIndex);
			}
			
			
	public String getAlarmTime() {
		String alarmTimeString = dateFormater.format(alarmTime.getTime());
		return alarmTimeString;
	}
	
	public void setAlarmTime(int year, int month, int date, int hour, int minute) {
		alarmTime = new GregorianCalendar(year, month, date, hour, minute, 0);
		//0=jan, 1=feb , osv
	}

	public boolean alarmIsOn() {
		return alarmOn;
	}

	public void setAlarmOn(boolean alarmOn) {
		this.alarmOn = alarmOn;
	}
	
	public ArrayList<File> getSoundFiles() {
		return soundFiles;
	}
}