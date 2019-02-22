
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.net.MalformedURLException;

public class Alarm {
	
	//denna alarmtid ska sättas av användaren
	private Calendar alarmTime;
	private boolean alarmIsSetOn;
	private volatile boolean alarmSoundIsRunning = false;
	//formaterar alarmtiden till enhetlig output som string
	private DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private boolean snoozeIsPressed;
	private int snoozeTimeMinutes = 1;
	
	private ArrayList<File> soundFiles = new ArrayList<File>();
	private File defaultAlarmSoundFile;
	
	//Objekt för at spela upp ljud
	private volatile AudioClip alarmSoundclip;
	
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
		File alert = new File("src/main/resources/Soundfiles/sms-alert-5-daniel_simon.wav");
		File buzzer = new File("src/main/resources/Soundfiles/Loud_Alarm_Clock_Buzzer-Muk1984-493547174.wav");
		soundFiles.add(alarmDrum);
		soundFiles.add(alert);
		soundFiles.add(buzzer);
		//Default ljudinställning
		defaultAlarmSoundFile = soundFiles.get(0);
	}
	
	public void snoozeAlarm() {
		if (alarmSoundIsRunning) {
			alarmSoundclip.stop();
			alarmSoundIsRunning = false;
			CompactMode.BackgroundAlarmOn.setVisible(false);
			}
	//skapar nytt tidsobjekt med aktuell tid när snoozeknappen trycks
	Calendar currentTime = new GregorianCalendar(); 
	//Lägger till det antal minuter som användaren valt att snooza
	currentTime.add(Calendar.MINUTE,snoozeTimeMinutes );
	//sätter nytt alarm med den valda tiden
	alarmTime = currentTime; 
	}
	
	public void turnOfAlarm() {
		if (alarmSoundIsRunning) {
			alarmSoundclip.stop();
			alarmSoundIsRunning = false;
			CompactMode.BackgroundAlarmOn.setVisible(false);
			
			//alarmet är avstängt
			this.setAlarmIsSetOn(false);
			}
	}
	

	public synchronized void loopAlarmSoundFile() {
		if (!alarmSoundIsRunning) {
			try {
				//skapa nytt ljudklipp 
				alarmSoundclip = Applet.newAudioClip(new File(defaultAlarmSoundFile.toString()).toURI().toURL());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			alarmSoundclip.loop();
			alarmSoundIsRunning = true;
		}
	}
	
	//jämför om alarmtiden är samma tidpunkt som ett annnat kalenderobjekt
	public boolean alarmTimeIsEqual(Object obj) {
		
		boolean returnValue = false;
		
		if (obj instanceof GregorianCalendar) {
			GregorianCalendar compare = (GregorianCalendar) obj;
			String inputString = dateFormater.format(compare.getTime());
			String alarmString = dateFormater.format(alarmTime.getTime());
			if (inputString.equals(alarmString)) {
				returnValue = true;
			}
			
		}
		
		return returnValue;
	}
	
	public void triggerAlarm() {
		new Thread() {
			public void run() {
				CompactMode.BackgroundAlarmOn.setVisible(true);
				if (!alarmSoundIsRunning) {
					loopAlarmSoundFile();
				}
			}
		}.start();
	}
	
	//	Ändra vald ljudfil. 
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
	
	public boolean getAlarmIsSetOn() {
		return alarmIsSetOn;
	}
	
	public void setAlarmIsSetOn(boolean alarmSet) {
		this.alarmIsSetOn = alarmSet;
	}
	
	public ArrayList<File> getSoundFiles() {
		return soundFiles;
	}
	
	public void setSnoozeIsPressed(boolean isPressed) {
		snoozeIsPressed = isPressed;
	}
	
	public boolean isAlarmSoundRunning() {
		return alarmSoundIsRunning;
	}
	
	public void setAlarmSoundIsRunning(boolean alarmSoundIsRunning) {
		this.alarmSoundIsRunning = alarmSoundIsRunning;
	}
	
	public void setSnoozeTimeInMinutes(int minutes) {
		snoozeTimeMinutes = minutes; 
	}
}