package clock;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import javax.swing.JTextPane;
import java.awt.Font;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JSpinner;

public class CompactMode extends JFrame {
	
	String presentTime;
	String presentDate;
	private String dateFormatEdit;
	private GregorianCalendar currentTimeObject;
	private GregorianCalendar currentDateObject;
	
	private int count = 0;
	private Alarm alarm = new Alarm();
	private JPanel timeDisplayPanel;
	private JPanel alarmDisplayPanel;
	
	private JTextPane timeDisplayText;
	private JTextPane alarmDisplayText;
	private JTextPane dateDisplayText;
	
	private JComboBox<String> dropDownHours;
	private JComboBox<String> dropDownMinutes;
	private JComboBox<String> dropDownYears;
	private JComboBox<String> dropDownMonths;
	private JComboBox<String> dropDownDays;
	private boolean alarmMenuDisplaysCurrentDate; 
	private JButton btnSnooze;
	private static JLabel lblAlarmOn;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	
	
	static JLabel BackgroundAlarmOn; 
	private static JLabel BackgroundImage;
	private static JLabel BackgroundAlarmSet;
	ImageIcon BackgroundMain;
	ImageIcon BackgroundAlarmSetIcon;
	ImageIcon BackgroundAlarmOnIcon;
	/**
	 * @wbp.nonvisual location=284,231
	 */
	
	
	//Konstruera klockfönstret
	public CompactMode() {
		initComponents();
		setInitialAlarmMenus(currentTimeObject);
		displayTime();
		updateAlarmDisplayText();
		alarmMenuDisplaysCurrentDate = true;
		}
			
	//Sätter alarmmenyn till aktuellt datum 
	private void setInitialAlarmMenus(GregorianCalendar currentTimeObject) {
		setInitialDropDownMinutes();
		setInitialDropDownHours();
		setInitialDropDownDays(currentTimeObject);
		setInitialDropDownMonths(currentTimeObject);
		setInitialDropDownYears(currentTimeObject);
		
	}
	
	private void setInitialDropDownHours() {//vektorer med valbara minuter och timmar för larm
		String[] availibleHours = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13",
				"14", "15", "16", "17", "18", "19", "20", "21", "22", "23" };
		for (String hour : availibleHours) {
			dropDownHours.addItem(hour);
		}
	}
	
	private void setInitialDropDownMinutes() {
		String[] minutes = { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" };
		for (String minute : minutes) {
			dropDownMinutes.addItem(minute);
		}
	}
	
	private void setInitialDropDownDays(Calendar currentTimeObject) {
		
		//	Calendar.DAY_OF_MONTH returnerar värde 1 för första dagen osv*/
			Integer totalDaysInCurrentMonth = currentTimeObject.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			//Räkna upp antalet dagar samt addera till menyn 
			for (int i = 0 ; i != totalDaysInCurrentMonth; i++) { 
				Integer dayNumber = i+1;  
				dropDownDays.addItem(dayNumber.toString());
			}
			//aktuell dag visas i menyn
				Integer currentDayOfMonth = currentTimeObject.get(Calendar.DAY_OF_MONTH);
				//dropDownDays index börjar på 0 currentDay börjar på 1 därav -1
				dropDownDays.setSelectedIndex(currentDayOfMonth-1);
		
		}
		
	private void setInitialDropDownMonths(Calendar currentTimeObject) {
			//alla månader i ett år i kortform jan,feb,mar etc
			String[] monthsAsString = new DateFormatSymbols().getShortMonths();
			
			//addera alla månader till menyn - monthAsString = 1-13 månader därav -1
			for (int i = 0; i != monthsAsString.length-1 ; i++) {
				dropDownMonths.addItem(monthsAsString[i]);
				}
			
			//alarmmånad sätts till aktuell månad vid start av klockan
			int currentMonth = currentTimeObject.get(Calendar.MONTH);//jan = 0 osv	
			dropDownMonths.setSelectedIndex(currentMonth);	
		}

	/*Året för objektet currentTimeObject adderas till dropdown menyn
	 * samt de kommande 11 åren*/
	private void setInitialDropDownYears(Calendar currentTimeObject) {
		if(!alarmMenuDisplaysCurrentDate) {
		Integer currentYear = currentTimeObject.get(Calendar.YEAR);
		
		for (int i = 0; i < 12; i++) {
		dropDownYears.addItem(currentYear.toString());
		currentYear += 1;  	
				}
			} 
	}
	
	
	//updateringsmetoden anropas när år / månad ändras i menyn
	 private void updateDropDownDays(Calendar updateCalendar) {
		 int currentNumOfdaysInMenu = dropDownDays.getItemCount();
		 //adderar aktuella dagar till menyn 
			Integer totalDaysInCurrentMonth = updateCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			for (int i = 0; i < totalDaysInCurrentMonth; i++) {
				Integer dayNum = i+1; 
				dropDownDays.addItem(dayNum.toString());
			}
		
		//tar sedan bort de inaktuella dagarna från menyn
		int count = 0; 
		while(count < currentNumOfdaysInMenu) {
			dropDownDays.removeItemAt(0);
			count++; 
			}	
		 }
		
	 //Adderar 5 år till menyn mär användare byter år 
private void increaseAvailibleYearsInMenu() {
		
		int lastYearInMenu = Integer.parseInt(dropDownYears.getItemAt(dropDownYears.getItemCount()-1));
		int numOfYearsToAdd = 6;
		//Om nytt år väljs av användaren adderas 5 nya år till menyn
		for(int i = lastYearInMenu+1; i != lastYearInMenu+numOfYearsToAdd; i++ ) {
		Integer yearToAdd = i; 
			dropDownYears.addItem(yearToAdd.toString());	
		}
	}

		//skapar och sätter inställningar för grafiska objekt till fönstret
		private void initComponents() {
			currentTimeObject = new GregorianCalendar();
			setSize(300, 172);
			
			timeDisplayPanel = new JPanel();
			timeDisplayPanel.setBackground(Color.WHITE);
			timeDisplayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(timeDisplayPanel);
			timeDisplayPanel.setLayout(null);
			timeDisplayText = new JTextPane();
			timeDisplayText.setBounds(12, 11, 97, 18);
			timeDisplayText.setFont(new Font("Tahoma", Font.PLAIN, 18));
			timeDisplayText.setBackground(new Color(255, 255, 255));
			timeDisplayText.setBorder(null);
			timeDisplayText.setOpaque(false);
			//timeDisplayText.setEditable(false);
			timeDisplayPanel.add(timeDisplayText);
			
			dateDisplayText = new JTextPane();
			dateDisplayText.setBounds(15, 32, 66, 13);
			dateDisplayText.setText("");
			dateDisplayText.setFont(new Font("Tahoma", Font.PLAIN, 8));
			dateDisplayText.setBackground(new Color(255, 255, 255));
			dateDisplayText.setBorder(null);
			dateDisplayText.setOpaque(false);
			
			timeDisplayPanel.add(dateDisplayText);
			
			
			//Panel för larmvisning+funktioner
			alarmDisplayPanel = new JPanel();
			alarmDisplayPanel.setBounds(18, 57, 91, 41);
			alarmDisplayPanel.setBackground(Color.WHITE);
			alarmDisplayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			dropDownYears = new JComboBox<String>();
			dropDownYears.setFont(new Font("Lucida Grande", Font.PLAIN, 6));
			
			dropDownYears.setEditable(true);
			dropDownYears.getEditor().getEditorComponent().setBackground(Color.WHITE);
	        
			
			dropDownYears.setBounds(8, 125, 70, 17);
			timeDisplayPanel.add(dropDownYears);
			dropDownYears.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//Om användaren byter år i menyn ska dagarna för den valda månaden och året uppdateras till det valda året 
					if (alarmMenuDisplaysCurrentDate) {
						
						int year = Integer.parseInt(dropDownYears.getSelectedItem().toString());
			        	int month = getMonthNumber(dropDownMonths.getSelectedItem().toString());
			        	int date = Integer.parseInt(dropDownDays.getSelectedItem().toString());
						int hour = Integer.parseInt(dropDownHours.getSelectedItem().toString());
						int minute = Integer.parseInt(dropDownMinutes.getSelectedItem().toString());
						updateDropDownDays( new GregorianCalendar(year, month, date, hour, minute, 0));
						
						// menyn avancerar 5 år när användaren väljer ett annat år än nuvarande
						increaseAvailibleYearsInMenu();
						}
					}
			});
			dropDownYears.setToolTipText("Select year");
			dropDownYears.setMaximumRowCount(12);
			alarmDisplayText = new JTextPane();
			alarmDisplayText.setFont(new Font("Tahoma", Font.PLAIN, 12));
			alarmDisplayText.setBackground(new Color(255, 255, 255));
			alarmDisplayText.setBorder(null);
			alarmDisplayText.setOpaque(false);
			alarmDisplayPanel.add(alarmDisplayText);
			timeDisplayPanel.add(alarmDisplayPanel);
			dropDownMonths = new JComboBox<String>();
			dropDownMonths.setFont(new Font("Lucida Grande", Font.PLAIN, 6));
			dropDownMonths.setEditable(true);
			dropDownMonths.getEditor().getEditorComponent().setBackground(Color.WHITE);
			dropDownMonths.setBounds(73, 125, 60, 17);
			timeDisplayPanel.add(dropDownMonths);
			
			
			
			dropDownMonths.addActionListener(new ActionListener() {
				//Om användaren byter månad i menyn ska dagar uppdateras för den valda månaden och året 
				@Override
				public void actionPerformed(ActionEvent e) {
					if (alarmMenuDisplaysCurrentDate) {
						int year = Integer.parseInt(dropDownYears.getSelectedItem().toString());
			        	int month = getMonthNumber(dropDownMonths.getSelectedItem().toString());
			        	int date = Integer.parseInt(dropDownDays.getSelectedItem().toString());
						int hour = Integer.parseInt(dropDownHours.getSelectedItem().toString());
						int minute = Integer.parseInt(dropDownMinutes.getSelectedItem().toString());
						updateDropDownDays( new GregorianCalendar(year, month, date, hour, minute, 0));
					}
				}
				
			});
			dropDownMonths.setMaximumRowCount(12);
			dropDownMonths.setToolTipText("Select month");
			dropDownDays = new JComboBox<String>();
			dropDownDays.setFont(new Font("Lucida Grande", Font.PLAIN, 6));
			dropDownDays.setEditable(true);
			dropDownDays.getEditor().getEditorComponent().setBackground(Color.WHITE);
			dropDownDays.setBounds(128, 125, 60, 17);
			timeDisplayPanel.add(dropDownDays);
			dropDownDays.setToolTipText("Select day");
			dropDownDays.setMaximumRowCount(12);
			
			//dropdowns visar timmar och minuter
			dropDownHours = new JComboBox<String>();
			dropDownHours.setFont(new Font("Lucida Grande", Font.PLAIN, 6));
			dropDownHours.setEditable(true);
			dropDownHours.getEditor().getEditorComponent().setBackground(Color.WHITE);
			dropDownHours.setBounds(128, 125, 60, 17);
			dropDownHours.setBounds(184, 125, 60, 17);
			timeDisplayPanel.add(dropDownHours);
			
			// Rubrik
			
			JTextPane txtpnThealarmclock = new JTextPane();
			txtpnThealarmclock.setBounds(204, 180, 41, 48);
			txtpnThealarmclock.setFont(new Font("Lucida Grande", Font.BOLD, 24));
			timeDisplayPanel.add(txtpnThealarmclock);
			
			// Rubrik hh/mm
						JLabel lblDate = new JLabel("Year     Month.    Day");
						lblDate.setBounds(208, 475, 191, 16);
						lblDate.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
						timeDisplayPanel.add(lblDate);
			
			
				
				dropDownHours.setToolTipText("Select hour");
				dropDownHours.setMaximumRowCount(12);
				dropDownMinutes = new JComboBox<String>();
				dropDownMinutes.setFont(new Font("Lucida Grande", Font.PLAIN, 6));
				
				dropDownMinutes.setEditable(true);
				dropDownMinutes.getEditor().getEditorComponent().setBackground(Color.WHITE);
				dropDownMinutes.setBounds(237, 125, 60, 17);
				timeDisplayPanel.add(dropDownMinutes);
				dropDownMinutes.setToolTipText("Select Minute");
				dropDownMinutes.setMaximumRowCount(12);
				
				
				JCheckBox alarmTickBox = new JCheckBox("On/Off");
				alarmTickBox.setBounds(194, 73, 28, 28);
				alarmTickBox.setVisible(false);
				alarmTickBox.setFont(new Font("Lucida Grande", Font.BOLD, 16));
				timeDisplayPanel.add(alarmTickBox);
				
				JLabel lblHourMinute = new JLabel("Hour.    Minute");
				lblHourMinute.setBounds(399, 475, 139, 16);
				lblHourMinute.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
				timeDisplayPanel.add(lblHourMinute);
				
				btnSnooze = new JButton("Snooze");
				btnSnooze.setBounds(211, 5, 86, 24);
				btnSnooze.setBorder(new LineBorder(Color.BLACK));
				btnSnooze.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
				btnSnooze.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						alarm.snoozeAlarm();
				}
					
				});
				timeDisplayPanel.add(btnSnooze);
				
				
				JButton btnOnOff = new JButton("Off");
				btnOnOff.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (!alarmTickBox.isSelected()) {
				        	int alarmYear = Integer.parseInt(dropDownYears.getSelectedItem().toString());
				        	int alarmMonth = getMonthNumber(dropDownMonths.getSelectedItem().toString());
				        	int alarmDay = Integer.parseInt(dropDownDays.getSelectedItem().toString());
							int alarmHour = Integer.parseInt(dropDownHours.getSelectedItem().toString());
							int alarmMinute = Integer.parseInt(dropDownMinutes.getSelectedItem().toString());
							alarm.setAlarmTime(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute);
							alarm.setAlarmIsSetOn(true);
							updateAlarmDisplayText();
							alarmTickBox.setSelected(true);
							btnOnOff.setText("On");
							BackgroundImage.setVisible(false);
							
							
							
							
				        } 
				        else if (alarmTickBox.isSelected()){
				        	alarm.setAlarmIsSetOn(false);
							updateAlarmDisplayText(); 
							alarm.turnOffAlarm();
							alarmTickBox.setSelected(false);
							btnOnOff.setText("Off");
							btnOnOff.setBackground(Color.WHITE);
							BackgroundImage.setVisible(true);
					}}
				});
				btnOnOff.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
				btnOnOff.setBounds(114, 13, 79, 91);
				btnOnOff.setBorder(new LineBorder(Color.BLACK));
				timeDisplayPanel.add(btnOnOff);
				
				
				
				JComboBox<String> dropDownSnoozeMinutes = new JComboBox<String>();
				dropDownSnoozeMinutes.setFont(new Font("Lucida Grande", Font.PLAIN, 7));
				dropDownSnoozeMinutes.setEditable(true);
				dropDownSnoozeMinutes.getEditor().getEditorComponent().setBackground(Color.WHITE);
				dropDownSnoozeMinutes.setBounds(245, 33, 50, 18);
				//addera stringobjekt till dropdownmenyn
				String[] snoozeMinutes = {"1","2", "5", "10", "15", "20","30"}; 
				for(int i = 0; i<snoozeMinutes.length; i++) {
				dropDownSnoozeMinutes.addItem(snoozeMinutes[i]);	
				} 
				
				dropDownSnoozeMinutes.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						//String to int 
					
						int selectedSnoozeMinutes = Integer.parseInt(dropDownSnoozeMinutes.getSelectedItem().toString());
						alarm.setSnoozeTimeInMinutes(selectedSnoozeMinutes);
						System.out.println("SnoozeAction performed");
					}
				});
				
				timeDisplayPanel.add(dropDownSnoozeMinutes);
				
				lblNewLabel = new JLabel("15 min");
				lblNewLabel.setBounds(174, 703, 61, 16);
				timeDisplayPanel.add(lblNewLabel);
				
				
				
				JLabel lblSetMin = new JLabel("Set min");
				lblSetMin.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
				lblSetMin.setBounds(210, 33, 36, 16);
				timeDisplayPanel.add(lblSetMin);
				
				// Bakgrundsbilder
				
				// Lägger till bakgrundsbilderna

				BackgroundImage = new JLabel("");
				BackgroundMain = new ImageIcon("src/main/resources/img/CompactMode_5.jpg","BackgroundMain");
				BackgroundImage.setBounds(0, 0, 300, 150);
				BackgroundImage.setIcon(BackgroundMain);
				timeDisplayPanel.add(BackgroundImage);
				
				BackgroundAlarmOn = new JLabel("");
				BackgroundAlarmOnIcon = new ImageIcon("src/main/resources/img/CompactMode_On.jpg","BackgroundAlarmOnIcon" );
				BackgroundAlarmOn.setBounds(0, 0, 300, 150);
				BackgroundAlarmOn.setVisible(false);
				BackgroundAlarmOn.setIcon(BackgroundAlarmOnIcon);
				timeDisplayPanel.add(BackgroundAlarmOn);
				
				BackgroundAlarmSet = new JLabel("");
				BackgroundAlarmSetIcon= new ImageIcon("src/main/resources/img/CompactMode_AlarmSet.jpg","BackgroundAlarmSetIcon" );
				BackgroundAlarmSet.setBounds(0, 0, 300, 150);
				BackgroundAlarmSet.setIcon(BackgroundAlarmSetIcon);
				timeDisplayPanel.add(BackgroundAlarmSet);
				
			
				
				
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
							alarm.setAlarmIsSetOn(true);
							updateAlarmDisplayText();
							
				        } 
				        else if (!alarmTickBox.isSelected()){
				        	alarm.setAlarmIsSetOn(false);
							updateAlarmDisplayText(); 
							alarm.turnOffAlarm();
							
				            
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
		
		
		//Lägger till alarmtiden i fönstret
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

	/* Startar tråd som läser in ny tid, adderar sedan tiden till fönstret */
	private void displayTime() {
		new Thread() {
			public void run() {
				
				while (true) {
					currentTimeObject = new GregorianCalendar();
					currentDateObject = new GregorianCalendar();
					
					//kickar igång alarmet om alarmet är på och tiden är lika med aktuell tidpunkt
					if (alarm.getAlarmIsSetOn() && alarm.alarmTimeIsEqual(currentTimeObject)) {
						alarm.triggerAlarm();
					}
					//dateFormatEdit = formatFMAM(currentTimeObject);
					
					presentTime = format24h(currentTimeObject);
					presentDate = formatDate(currentDateObject);
					
					try {
						this.sleep(500);//pausa  uppdatering av tid 0.5 sek
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					showTime(presentTime); //Uppdatera textfältet som visar tiden
					showDate(presentDate); // Uppdatera textfältet som visar datum
				}
			}
		}.start();
	}
	
	public void showDate(String time) {
		dateDisplayText.setText(time);
	}
	
	
	// Metod som visar tiden 
	public static String format24h(GregorianCalendar calendar) {
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
		fmt.setCalendar(calendar);
		String dateFormatted = fmt.format(calendar.getTime());
		
		
		return dateFormatted;
	}
	// Metod som visar Datum
	public static String formatDate(GregorianCalendar calendar) {
		SimpleDateFormat fmt = new SimpleDateFormat("EEE MMM d yyyy");
		fmt.setCalendar(calendar);
		String dateFormatted = fmt.format(calendar.getTime());
		
		// Stor första bokstav i dag och mån
		String dateFormatted_1 = dateFormatted.substring(0, 1).toUpperCase()+dateFormatted.substring(1,3) + dateFormatted.substring(3, 4).toUpperCase()+dateFormatted.substring(4);
		
		return dateFormatted_1;
	}
	
	
	
		//visar alarmtid om alarmet är på annars "alarm off"
		private void updateAlarmDisplayText() {
		if (alarm.getAlarmIsSetOn()==true) {
			alarmDisplayText.setText(alarm.getAlarmTime());
		}	else {
			alarmDisplayText.setText("Off"); 
		}
		}
		
		/*tar emot förkortad månad(jan, feb etc) som string  och returernerar månadens 
		motsvarande parameter till Calendarclassens konstruktor för att bestämma månad */  
		 private int getMonthNumber(String monthString) {
			Integer monthNum = null;
			String[] monthsAsString = new DateFormatSymbols().getShortMonths();
			//jan = 0, feb = 1 osv
			for (int i = 0; i < monthsAsString.length; i++) {
				if (monthString.equals(monthsAsString[i])){
					monthNum = i;
					} 
			} 
			return monthNum;
		
		}
}