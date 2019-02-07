
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextPane;
import java.awt.Font;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;

public class ClockWindow extends JFrame {
	
	private String dateFormatEdit;
	private GregorianCalendar currentTimeObject;
	private int count = 0;
	private Alarm alarm = new Alarm();
	private JPanel timeDisplayPanel;
	private JPanel alarmDisplayPanel;
	
	
	@Override
	public Font getFont() {
		return super.getFont();
	}
	
	private JTextPane timeDisplayText;
	private JTextPane alarmDisplayText;
	private JComboBox<String> dropDownHours;
	private JComboBox<String> dropDownMinutes;
	private JComboBox<String> dropDownYears;
	private JComboBox<String> dropDownMonths;
	private JComboBox<String> dropDownDays;
	private boolean clockIsRunning; 
	
	
	private class DropDownAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
		
			
			if (clockIsRunning) {
			if (e.getSource()==(dropDownYears)) {
				System.out.println("Year");
			}
			
			if (e.getSource()==(dropDownMonths)) {
				System.out.println("Month");
			}
			
			if (e.getSource()==(dropDownDays)) {
				System.out.println("Day");
			}
			
			int year = Integer.parseInt(dropDownYears.getSelectedItem().toString());
        	int month = getMonthNumber(dropDownMonths.getSelectedItem().toString());
        	int date = Integer.parseInt(dropDownDays.getSelectedItem().toString());
			int hour = Integer.parseInt(dropDownHours.getSelectedItem().toString());
			int minute = Integer.parseInt(dropDownMinutes.getSelectedItem().toString());
			
			Calendar cal = new GregorianCalendar(year, month, date, hour, minute, 0);  
			//Calendar cal = new GregorianCalendar(1980, 3, 19, 04, 7, 0);  
			
//			Hämta isntällningar från dropdowns 
//			Skapa ett calendar-objekt utifrån det
//			 passera objektet som argument till metoden
				updateDropDownDisplays(cal); 
			}
		}
		
		
	}
	
	//Konstruera klockfönstret
	public ClockWindow() {
		initComponents();
		updateDropDownDisplays(currentTimeObject);
		displayTime();
		updateAlarmDisplayText(); 
		clockIsRunning = true; 
		
	}
	
	/* Startar tråd som läser in ny tid, adderar sedan tiden till fönstret */
	private void displayTime() {
		new Thread() {
			public void run() {
				
				while (true) {
					currentTimeObject = new GregorianCalendar();
					
					//kickar igång alarmet om alarmet är på och tiden är lika med aktuell tidpunkt
					if (alarm.alarmIsOn() && alarm.isEqualTo(currentTimeObject)) {
						alarm.triggerAlarm();
					}
					
					
					if (count == 0) {
						dateFormatEdit = formatFMAM(currentTimeObject);
					}
					count++;
					
					if (dateFormatEdit.length() > 15) {
						dateFormatEdit = formatDate(currentTimeObject);
					} else if (dateFormatEdit.contains("M")) {
						dateFormatEdit = formatFMAM(currentTimeObject);
					} else {
						dateFormatEdit = format24h(currentTimeObject);
					}
					
					//String dateFormatEdit = dateString.substring(11, 19);
					//System.out.println(dateFormatEdit);
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
		currentTimeObject = new GregorianCalendar();
		setSize(1300, 600);
		timeDisplayPanel = new JPanel();
		timeDisplayPanel.setBackground(new Color(245, 245, 220));
		timeDisplayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(timeDisplayPanel);
		
		JButton btnNewButton = new JButton("24h");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateFormatEdit = format24h(currentTimeObject);
			}
		});
		
		//Knappar som ändrar tidsformatet som visas.
		JButton btnNewButton_1 = new JButton("AM/FM");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateFormatEdit = formatFMAM(currentTimeObject);
			}
		});
		//		
		timeDisplayPanel.add(btnNewButton_1, "2, 2, left, center");
		
		JButton btnNewButton_2 = new JButton("Date");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateFormatEdit = formatDate(currentTimeObject);
			}
		});
		timeDisplayPanel.add(btnNewButton_2, "3, 2, left, center");
		timeDisplayPanel.add(btnNewButton, "4, 2, left, center");
		
		timeDisplayText = new JTextPane();
		timeDisplayText.setFont(new Font("Tahoma", Font.PLAIN, 99));
		timeDisplayText.setBackground(new Color(255, 255, 255));
		timeDisplayText.setBorder(null);
		timeDisplayText.setOpaque(false);
		timeDisplayText.setEditable(false);
		timeDisplayPanel.add(timeDisplayText, "8, 2, center, center");
		
		//Panel för larmvisning+funktioner
		alarmDisplayPanel = new JPanel();
		alarmDisplayPanel.setBackground(new Color(245, 245, 220));
		alarmDisplayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		
		
		//dropdowns visar timmar och minuter
		dropDownHours = new JComboBox<String>();
		dropDownMinutes = new JComboBox<String>();
		dropDownYears = new JComboBox<String>();
		dropDownMonths = new JComboBox<String>();
		dropDownDays = new JComboBox<String>();
		DropDownAction changedStateAction = new DropDownAction();
		dropDownMonths.addActionListener(changedStateAction);
		dropDownYears.addActionListener(changedStateAction);
		dropDownDays.addActionListener(changedStateAction);
		dropDownHours.setToolTipText("Select hour");
		dropDownMinutes.setToolTipText("Select Minute");
		dropDownHours.setMaximumRowCount(12);
		dropDownMinutes.setMaximumRowCount(12);
		dropDownYears.setToolTipText("Select year");
		dropDownYears.setMaximumRowCount(12);
		dropDownMonths.setMaximumRowCount(12);
		dropDownMonths.setToolTipText("Select month");
		dropDownDays.setToolTipText("Select day");
		dropDownDays.setMaximumRowCount(12);
	
		
		
		
		alarmDisplayPanel.add(dropDownYears);
		alarmDisplayPanel.add(dropDownMonths); 
		alarmDisplayPanel.add(dropDownDays); 
		alarmDisplayPanel.add(dropDownHours);
		alarmDisplayPanel.add(dropDownMinutes);
		
		
		JCheckBox alarmTickBox = new JCheckBox("Alarm on");
		
		alarmTickBox.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		    
		        if (alarmTickBox.isSelected()) {
		        	int alarmYear = Integer.parseInt(dropDownYears.getSelectedItem().toString());
		        	int alarmMonth = getMonthNumber(dropDownMonths.getSelectedItem().toString());
		        	int alarmDay = Integer.parseInt(dropDownDays.getSelectedItem().toString());
					int alarmHour = Integer.parseInt(dropDownHours.getSelectedItem().toString());
					int alarmMinute = Integer.parseInt(dropDownMinutes.getSelectedItem().toString());
					alarm.setAlarmTime(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute);
					alarm.setAlarmOn(true);
					updateAlarmDisplayText();
		        } else if (!alarmTickBox.isSelected()){
		        	alarm.setAlarmOn(false);
					updateAlarmDisplayText(); 
		            
		        }
		    }
		});
		
		
		alarmDisplayPanel.add(alarmTickBox);
		alarmDisplayText = new JTextPane();
		alarmDisplayText.setFont(new Font("Tahoma", Font.PLAIN, 99));
		alarmDisplayText.setBackground(new Color(255, 255, 255));
		alarmDisplayText.setBorder(null);
		alarmDisplayText.setOpaque(false);
		alarmDisplayText.setEditable(false);
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
	//visar alarmtid om alarmet är på annars "alarm off"
	private void updateAlarmDisplayText() {
	if (alarm.alarmIsOn()==true) {
		alarmDisplayText.setText(alarm.getAlarmTime());
	}	else {
		alarmDisplayText.setText("alarm off"); 
	}
	}
	
	private void updateDropDownHours(){//vektorer med valbara minuter och timmar för larm
		String[] availibleHours = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
		for (String hour : availibleHours) {
			dropDownHours.addItem(hour);
		}
			}
	private void updateDropDownMinutes(){
		String[] minutes = { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" };
		for (String minute : minutes) {
				dropDownMinutes.addItem(minute);
			}
	}
	
	private void updateDropDownDays(Calendar updateDropDownsObject){
		
		//addera antalet dagar till dropdown för månaden
		if(!clockIsRunning) {
		//dropDownDays.addItem("6");
		Integer currentDayOfMonth = updateDropDownsObject.get(Calendar.DAY_OF_MONTH);
		Integer totalDaysInCurrentMonth = updateDropDownsObject.getActualMaximum(Calendar.DAY_OF_MONTH);
		ArrayList<String> daysToAdd = new ArrayList<String>(); 
		
		for (int i = currentDayOfMonth-1; i < totalDaysInCurrentMonth; i++) {
			daysToAdd.add(currentDayOfMonth.toString());
			currentDayOfMonth++;
		}
		for (String day : daysToAdd) {
			dropDownDays.addItem(day);
		}
		}
		
		else if (clockIsRunning) {
//			System.out.println("running");
		//	dropDownDays.addItem("2");
			Integer totalDaysInCurrentMonth = updateDropDownsObject.getActualMaximum(Calendar.DAY_OF_MONTH);
			ArrayList<String> daysToAdd = new ArrayList<String>(); 
			
			for (int i = 0; i < totalDaysInCurrentMonth; i++) {
				Integer day = i; 
				daysToAdd.add(day.toString());
				
			}
			for (String day : daysToAdd) {
				dropDownDays.addItem(day);
			}
			
		}
		
	}
	
	private void updateDropDownMonths(Calendar updateDropDownsObject){
				//Adderar aktuell månad + resterande av året till menyn 
				//månader som stringobjekt 
				//alla månader som Stringobjekt 
				String[] monthsAsString = new DateFormatSymbols().getShortMonths();
				//aktuell månad som int
				int currentMonth = updateDropDownsObject.get(Calendar.MONTH); 
				int nextMonth = 0;
				//adderar alla månader efter aktuell månad till meny 
				//-1 för att sista index är tom position?
				for(int i = currentMonth; i<monthsAsString.length-1; i++) {
				dropDownMonths.addItem(monthsAsString[currentMonth+nextMonth]);
				nextMonth++; 	
				}
	}
	private void updateDropDownDisplays(Calendar updateDropDownsObject) {
		dropDownDays.removeAllItems();
		dropDownHours.removeAllItems();
		dropDownYears.removeAllItems();
		updateDropDownHours(); 
		updateDropDownMinutes(); 
		updateDropDownDays(updateDropDownsObject); 
		updateDropDownMonths(updateDropDownsObject);
		updateDropDownYears(updateDropDownsObject);
		
		
		
		
				
		
		}
	
	
	private void updateDropDownYears(Calendar updateDropDownsObject) {
		
		//adderar innehållet till dropdown för året
				Integer currentYear = updateDropDownsObject.get(Calendar.YEAR);
				ArrayList<String> yearsToAdd = new ArrayList<String>(); //= {currentYear.toString()};
				for (int i = 0; i < 12; i++) {
					yearsToAdd.add(currentYear.toString());
					currentYear++;
				}
				for (String yearToAdd : yearsToAdd) {
					dropDownYears.addItem(yearToAdd);
				}
	}

	//returernerar månad som int till Calendarobjekt 
	//jan = 0, feb = 2 osv 
	private int getMonthNumber(String monthString) {
		Integer monthNum = null;
		String[] monthsAsString = new DateFormatSymbols().getShortMonths();
		
		for (int i = 0; i < monthsAsString.length; i++) {
			if (monthString.equals(monthsAsString[i])){
				monthNum = i;
				} 
		} 
		return monthNum;
	
	}

}

