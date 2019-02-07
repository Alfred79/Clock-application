
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.javafx.binding.StringFormatter;

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
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class ClockWindow extends JFrame {
	
	String presentTime;
	String presentDate;
	GregorianCalendar calendarObjekt;
	GregorianCalendar calendarObjekt2;
	int count = 0;
	private Alarm alarm = new Alarm();
	private JPanel timeDisplayPanel;
	private JPanel alarmDisplayPanel;
	
	@Override
	public Font getFont() {
		return super.getFont();
	}
	
	private JTextPane dateDisplayText;
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
					calendarObjekt2 = new GregorianCalendar();
					
					//kickar igång alarmet om alarmet är på och tiden är lika med aktuell tidpunkt
					if (alarm.alarmIsOn() && alarm.isEqualTo(calendarObjekt)) {
						alarm.triggerAlarm();
					}
					
					
					
						presentTime = format24h(calendarObjekt);
						presentDate = formatDate(calendarObjekt2);
						
					
					
					//String dateFormatEdit = dateString.substring(11, 19);
					System.out.println(presentTime);
					try {
						this.sleep(500);//pausa  uppdatering av tid 0.5 sek
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					showTime(presentTime);
					showDate(presentDate);
					
					//Uppdatera textfältet som visar tiden
				}
			}
		}.start();
	}
	
	//skapar och sätter inställningar för grafiska objekt till fönstret
	private void initComponents() {
		setSize(700, 1000);
		timeDisplayPanel = new JPanel();
		timeDisplayPanel.setBackground(Color.WHITE);
		timeDisplayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(timeDisplayPanel);
		
		JButton btnNewButton = new JButton("24h");
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(167, 6, 75, 29);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				presentTime = format24h(calendarObjekt);
			}
		});
		
		//Knappar som ändrar tidsformatet som visas.
		JButton btnNewButton_1 = new JButton("AM/FM");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setBounds(6, 6, 89, 29);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				presentTime = formatFMAM(calendarObjekt);
			}
		});
		timeDisplayPanel.setLayout(null);
		//		
		timeDisplayPanel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Date");
		btnNewButton_2.setEnabled(false);
		btnNewButton_2.setBounds(91, 6, 75, 29);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				presentTime = formatDate(calendarObjekt);
			}
		});
		timeDisplayPanel.add(btnNewButton_2);
		timeDisplayPanel.add(btnNewButton);
		
		timeDisplayText = new JTextPane();
		timeDisplayText.setBounds(224, 170, 245, 120);
		timeDisplayText.setFont(new Font("Tahoma", Font.PLAIN, 60));
		timeDisplayText.setBackground(new Color(255, 255, 255));
		timeDisplayText.setBorder(null);
		timeDisplayText.setOpaque(false);
		timeDisplayPanel.add(timeDisplayText);
		
		dateDisplayText = new JTextPane();
		dateDisplayText.setText("");
		dateDisplayText.setBounds(259, 290, 178, 55);
		dateDisplayText.setFont(new Font("Tahoma", Font.PLAIN, 25));
		dateDisplayText.setBackground(new Color(255, 255, 255));
		dateDisplayText.setBorder(null);
		dateDisplayText.setOpaque(false);
		timeDisplayPanel.add(dateDisplayText);
		
		//Panel för larmvisning+funktioner
		alarmDisplayPanel = new JPanel();
		alarmDisplayPanel.setBounds(201, 633, 268, 39);
		alarmDisplayPanel.setBackground(Color.WHITE);
		alarmDisplayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//vektorer med valbara minuter och timmar för larm
		String[] hours = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20", "21", "22", "23", "24" };
		String[] minutes = { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" };
		alarmDisplayText = new JTextPane();
		alarmDisplayText.setFont(new Font("Tahoma", Font.PLAIN, 25));
		alarmDisplayText.setBackground(new Color(255, 255, 255));
		alarmDisplayText.setBorder(null);
		alarmDisplayText.setOpaque(false);
		alarmDisplayPanel.add(alarmDisplayText);
		timeDisplayPanel.add(alarmDisplayPanel);
		//dropdowns visar timmar och minuter
		JComboBox dropDownHours = new JComboBox(hours);
		dropDownHours.setBounds(269, 515, 72, 27);
		timeDisplayPanel.add(dropDownHours);
		dropDownHours.setMaximumRowCount(12);
		JComboBox dropDownMinutes = new JComboBox(minutes);
		dropDownMinutes.setBounds(346, 515, 72, 27);
		timeDisplayPanel.add(dropDownMinutes);
		dropDownMinutes.setMaximumRowCount(12);
		
		JLabel lblTimmar = new JLabel("hh  /  mm");
		lblTimmar.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblTimmar.setBounds(309, 475, 96, 16);
		timeDisplayPanel.add(lblTimmar);
		
		JTextPane txtpnThealarmclock = new JTextPane();
		txtpnThealarmclock.setFont(new Font("Lucida Grande", Font.BOLD, 23));
		txtpnThealarmclock.setText("TheAlarmClock");
		txtpnThealarmclock.setBounds(259, 60, 186, 48);
		timeDisplayPanel.add(txtpnThealarmclock);
		
		JCheckBox alarmTickBox = new JCheckBox("Alarm on");
		alarmTickBox.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		alarmTickBox.setBounds(293, 569, 139, 23);
		timeDisplayPanel.add(alarmTickBox);
		
		JLabel lblAlarm = new JLabel("Alarm");
		lblAlarm.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblAlarm.setBounds(309, 417, 75, 34);
		timeDisplayPanel.add(lblAlarm);
		
		alarmTickBox.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		    
		        if (alarmTickBox.isSelected()) {
		        	//vid ikryssad box hämtas aktuell år, månad,datum
					int year = calendarObjekt.get(Calendar.YEAR);
					int month = calendarObjekt.get(Calendar.MONTH);
					int day = calendarObjekt.get(Calendar.DATE);
					//tid + minute läses in från användarens val
					int hour = Integer.parseInt(dropDownHours.getSelectedItem().toString());
					int minute = Integer.parseInt(dropDownMinutes.getSelectedItem().toString());
					alarm.setAlarmTime(year, month, day, hour, minute);
					alarm.setAlarmOn(true);
					updateAlarmDisplayText();
		        } else if (!alarmTickBox.isSelected()){
		        	alarm.setAlarmOn(false);
					updateAlarmDisplayText(); 
		            
		        }
		    }
		});
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	//Lägger till tiden till fönstret
	public void showTime(String time) {
		timeDisplayText.setText(time);
	}
	
	public void showDate(String time) {
		dateDisplayText.setText(time);
	}
	
	
	//visar alarmtiden i fönstret
	public void showAlarmTime(String alarmTime) {
		alarmDisplayText.setText(alarmTime);
	}
	
	// Formaterar tiden - formatmall kan hittas här:
	// https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
	
	public static String formatFMAM(GregorianCalendar calendar) {
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss a");
		fmt.setCalendar(calendar);
		String dateFormatted = fmt.format(calendar.getTime());
		
		// EM/AM till stora bokstäver
		return dateFormatted.toUpperCase();
	}
	
	public static String format24h(GregorianCalendar calendar) {
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
		fmt.setCalendar(calendar);
		String dateFormatted = fmt.format(calendar.getTime());
		
		// EM/AM till stora bokstäver
		return dateFormatted;
	}
	
	public static String formatDate(GregorianCalendar calendar) {
		SimpleDateFormat fmt = new SimpleDateFormat("EEE MMM d yyyy");
		fmt.setCalendar(calendar);
		String dateFormatted = fmt.format(calendar.getTime());
		
		// Stor första bokstav i dag och mån
		String dateFormatted_1 = dateFormatted.substring(0, 1).toUpperCase()+dateFormatted.substring(1,3) + dateFormatted.substring(3, 4).toUpperCase()+dateFormatted.substring(4);
		
		return dateFormatted_1;
	}
	
	private void updateAlarmDisplayText() {
	if (alarm.alarmIsOn()==true) {
		alarmDisplayText.setText(alarm.getAlarmTime());
	}	else {
		alarmDisplayText.setText("alarm off"); 
	}
	}
}

