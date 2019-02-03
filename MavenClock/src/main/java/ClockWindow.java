
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextPane;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class ClockWindow extends JFrame {
	String dateFormatEdit;
	GregorianCalendar calendarObjekt;
	int count = 0;
	private Alarm alarm = new Alarm(2019, 01, 01, 9, 54);
	private JPanel timeDisplayPanel;
	private JPanel alarmDisplayPanel;
	
	@Override
	public Font getFont() {
		return super.getFont();
	}
	
	private JTextPane timeDisplayText;
	private JTextPane alarmDisplayText;
	
	//Konstruera klockfönstret
	public ClockWindow() {
		initComponents();
		displayTime();
	}
	
	/* Startar tråd som läser in ny tid, adderar sedan tiden till fönstret */
	private void displayTime() {
		new Thread() {
			public void run() {
				
				while (true) {
					calendarObjekt = new GregorianCalendar();
					
					if (alarm.isEqualTo(calendarObjekt)) {
						alarm.triggerAlarm();
					}
					
					if (count == 0) {
						dateFormatEdit = formatFMAM(calendarObjekt);
					}
					count++;
					
					if (dateFormatEdit.length() > 15) {
						dateFormatEdit = formatDate(calendarObjekt);
					} else if (dateFormatEdit.contains("M")) {
						dateFormatEdit = formatFMAM(calendarObjekt);
					} else {
						dateFormatEdit = format24h(calendarObjekt);
					}
					
					//String dateFormatEdit = dateString.substring(11, 19);
					System.out.println(dateFormatEdit);
					try {
						this.sleep(500);//pausa  uppdatering av tid 0.5 sek
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					showTime(dateFormatEdit); //Uppdatera textfältet som visar tiden
				}
			}
		}.start();
	}
	
	//skapar och sätter inställningar för grafiska objekt till fönstret
	private void initComponents() {
		setSize(1300, 600);
		timeDisplayPanel = new JPanel();
		timeDisplayPanel.setBackground(new Color(245, 245, 220));
		timeDisplayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(timeDisplayPanel);
		
		JButton btnNewButton = new JButton("24h");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateFormatEdit = format24h(calendarObjekt);
			}
		});
		
		//Knappar som ändrar tidsformatet som visas.
		JButton btnNewButton_1 = new JButton("AM/FM");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateFormatEdit = formatFMAM(calendarObjekt);
			}
		});
		//		
		timeDisplayPanel.add(btnNewButton_1, "2, 2, left, center");
		
		JButton btnNewButton_2 = new JButton("Date");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateFormatEdit = formatDate(calendarObjekt);
			}
		});
		timeDisplayPanel.add(btnNewButton_2, "3, 2, left, center");
		timeDisplayPanel.add(btnNewButton, "4, 2, left, center");
		
		timeDisplayText = new JTextPane();
		timeDisplayText.setFont(new Font("Tahoma", Font.PLAIN, 99));
		timeDisplayText.setBackground(new Color(255, 255, 255));
		timeDisplayText.setBorder(null);
		timeDisplayText.setOpaque(false);
		timeDisplayPanel.add(timeDisplayText, "8, 2, center, center");
		
		//Panel för larmvisning+funktioner
		alarmDisplayPanel = new JPanel();
		alarmDisplayPanel.setBackground(new Color(245, 245, 220));
		alarmDisplayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//vektorer med valbara minuter och timmar för larm
		String[] hours = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20", "21", "22", "23", "24" };
		String[] minutes = { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" };
		//dropdowns visar timmar och minuter
		JComboBox dropDownHours = new JComboBox(hours);
		JComboBox dropDownMinutes = new JComboBox(minutes);
		dropDownHours.setMaximumRowCount(12);
		dropDownMinutes.setMaximumRowCount(12);
		alarmDisplayPanel.add(dropDownHours);
		alarmDisplayPanel.add(dropDownMinutes);
		
		JButton btnAlarmOn = new JButton("Alarm ON");
		btnAlarmOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//vid knapptryck hämtas aktuell år, månad,datum
				int year = calendarObjekt.get(Calendar.YEAR);
				int month = calendarObjekt.get(Calendar.MONTH);
				int day = calendarObjekt.get(Calendar.DATE);
				//tid + minute läses in från användarens val
				int hour = Integer.parseInt(dropDownHours.getSelectedItem().toString());
				int minute = Integer.parseInt(dropDownMinutes.getSelectedItem().toString());
				
				alarm.setAlarmTime(year, month, day, hour, minute);
				alarmDisplayText.setText(alarm.getAlarmTime());
			}
		});
		
		alarmDisplayPanel.add(btnAlarmOn);
		
		JButton btnAlarmOff = new JButton("Alarm OFF");
		
		alarmDisplayPanel.add(btnAlarmOff);
		alarmDisplayText = new JTextPane();
		alarmDisplayText.setFont(new Font("Tahoma", Font.PLAIN, 99));
		alarmDisplayText.setBackground(new Color(255, 255, 255));
		alarmDisplayText.setBorder(null);
		alarmDisplayText.setOpaque(false);
		alarmDisplayText.setText("no alarm is set");
		alarmDisplayPanel.add(alarmDisplayText);
		timeDisplayPanel.add(alarmDisplayPanel, "8, 2, center, center");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	//Lägger till tiden till fönstret
	public void showTime(String time) {
		timeDisplayText.setText(time);
	}
	
	//visar alarmtiden i fönstret
	public void showAlarmTime(String alarmTime) {
		alarmDisplayText.setText(alarmTime);
	}
	
	// Formaterar tiden - formatmall kan hittas här:
	// https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
	
	public static String formatFMAM(GregorianCalendar calendar) {
		SimpleDateFormat fmt = new SimpleDateFormat("hh:mm:ss a");
		fmt.setCalendar(calendar);
		String dateFormatted = fmt.format(calendar.getTime());
		
		// EM/AM till stora bokstäver
		return dateFormatted.toUpperCase();
	}
	
	public static String format24h(GregorianCalendar calendar) {
		SimpleDateFormat fmt = new SimpleDateFormat("hh:mm:ss");
		fmt.setCalendar(calendar);
		String dateFormatted = fmt.format(calendar.getTime());
		
		// EM/AM till stora bokstäver
		return dateFormatted;
	}
	
	public static String formatDate(GregorianCalendar calendar) {
		SimpleDateFormat fmt = new SimpleDateFormat("hh:mm:ss" + " EEE MMM d yyyy");
		fmt.setCalendar(calendar);
		String dateFormatted = fmt.format(calendar.getTime());
		
		// Bokstäver till stora bokstäver
		return dateFormatted.toUpperCase();
	}
}

