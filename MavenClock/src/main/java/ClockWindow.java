
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

public class ClockWindow extends JFrame {
	
	private String dateFormatEdit;
	private GregorianCalendar currentTimeObject;
	private int count = 0;
	private Alarm alarm = new Alarm();
	private JPanel timeDisplayPanel;
	private JPanel alarmDisplayPanel;
	
	private JTextPane timeDisplayText;
	private JTextPane alarmDisplayText;
	private JComboBox<String> dropDownHours;
	private JComboBox<String> dropDownMinutes;
	private JComboBox<String> dropDownYears;
	private JComboBox<String> dropDownMonths;
	private JComboBox<String> dropDownDays;
	private boolean alarmMenuDisplaysCurrentDate; 
	
	//Konstruera klockfönstret
	public ClockWindow() {
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
			setSize(1300, 600);
			timeDisplayPanel = new JPanel();
			timeDisplayPanel.setBackground(new Color(245, 245, 220));
			timeDisplayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(timeDisplayPanel);
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
			    //ett nytt alarm sätts med de valda inställningarna i menyn om användaren fyller i tickbox
			        if (alarmTickBox.isSelected()) {
			        	int alarmYear = Integer.parseInt(dropDownYears.getSelectedItem().toString());
			        	int alarmMonth = getMonthNumber(dropDownMonths.getSelectedItem().toString());
			        	int alarmDay = Integer.parseInt(dropDownDays.getSelectedItem().toString());
						int alarmHour = Integer.parseInt(dropDownHours.getSelectedItem().toString());
						int alarmMinute = Integer.parseInt(dropDownMinutes.getSelectedItem().toString());
						alarm.setAlarmTime(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute);
						alarm.setAlarmOn(true);
						updateAlarmDisplayText();
			        } 
			        else if (!alarmTickBox.isSelected()){
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
					
					//kickar igång alarmet om alarmet är på och tiden är lika med aktuell tidpunkt
					if (alarm.alarmIsOn() && alarm.isEqualTo(currentTimeObject)) {
						alarm.triggerAlarm();
					}
					dateFormatEdit = formatFMAM(currentTimeObject);
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
	
		//visar alarmtid om alarmet är på annars "alarm off"
		private void updateAlarmDisplayText() {
		if (alarm.alarmIsOn()==true) {
			alarmDisplayText.setText(alarm.getAlarmTime());
		}	else {
			alarmDisplayText.setText("alarm off"); 
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

