

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JTextPane;
import java.awt.Component;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ClockWindow extends JFrame {
	String dateFormatEdit;
	GregorianCalendar calendarObjekt;
	int count = 0;
	
	private JPanel timeDisplayPanel;@Override
	public Font getFont() {
		// TODO Auto-generated method stub
		return super.getFont();
	}
	
	private JTextPane timeDisplayText; 
	
	//Konstruera klockfönstret
	public ClockWindow() {
	initComponents();
	displayTime(); 
	}
	
	/*Startar tråd som läser in ny tid varje sekund
	 * Adderar sedan tiden till fönstret */
	private void displayTime() {
		new Thread () {
			public void run() 
			{
			
			while (true) 
			{
				calendarObjekt = new GregorianCalendar();
				//String dateString = calendarObjekt.getTime().toString();
				
				if (count == 0) 
					{
					dateFormatEdit = formatFMAM(calendarObjekt);
					}
				count++;
				
				
				if (dateFormatEdit.length()>15) 
				{
				dateFormatEdit = formatDate(calendarObjekt);
				}
				else if (dateFormatEdit.contains("M")) 
				{
				dateFormatEdit = formatFMAM(calendarObjekt);
				}
				else {
					dateFormatEdit = format24h(calendarObjekt);
					}
			
			
			//String dateFormatEdit = dateString.substring(11, 19);
			System.out.println(dateFormatEdit);
			try {
					this.sleep(500);//pausa 1 sek
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
		setSize(900,600);
		timeDisplayPanel = new JPanel();
		timeDisplayPanel.setBackground(new Color(245, 245, 220));
		timeDisplayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(timeDisplayPanel);
		timeDisplayPanel.setLayout(new BoxLayout(timeDisplayPanel, BoxLayout.X_AXIS));
		
		//Knappar som ändrar tidsformatet som visas
		
		JButton btnNewButton_1 = new JButton("AM/FM");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateFormatEdit = formatFMAM(calendarObjekt);
			}
		});
		
		
		timeDisplayPanel.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("24h");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateFormatEdit = format24h(calendarObjekt);
			}
		});
		
		JButton btnNewButton_2 = new JButton("Date");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateFormatEdit = formatDate(calendarObjekt);
			}
		});
		
		
		timeDisplayPanel.add(btnNewButton_2);
		timeDisplayPanel.add(btnNewButton);
		timeDisplayText = new JTextPane();
		timeDisplayText.setFont(new Font("Tahoma", Font.PLAIN, 99));
		timeDisplayText.setBackground(new Color(255, 255, 255));
		timeDisplayText.setBorder(null);
		timeDisplayText.setOpaque(false); //transparent bakgrund 
		timeDisplayPanel.add(timeDisplayText);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	//Lägger till tiden till visningsfönstret
	public void showTime(String time) {
		timeDisplayText.setText(time);
		
		
		}
	
	// Formaterar tiden - formatmall kan hittas här:
	// https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
	
	public static String formatFMAM(GregorianCalendar calendar){
	    SimpleDateFormat fmt = new SimpleDateFormat("hh:mm:ss a");
	    fmt.setCalendar(calendar);
	    String dateFormatted = fmt.format(calendar.getTime());
	    
	   // EM/AM till stora bokstäver
	    return dateFormatted.toUpperCase();
	}
	public static String format24h(GregorianCalendar calendar){
	    SimpleDateFormat fmt = new SimpleDateFormat("hh:mm:ss");
	    fmt.setCalendar(calendar);
	    String dateFormatted = fmt.format(calendar.getTime());
	    
	   // EM/AM till stora bokstäver
	    return dateFormatted;
	}
	
	public static String formatDate(GregorianCalendar calendar){
	    SimpleDateFormat fmt = new SimpleDateFormat("hh:mm:ss" + " EEE MMM d yyyy");
	    fmt.setCalendar(calendar);
	    String dateFormatted = fmt.format(calendar.getTime());
	    
	   // Bokstäver till stora bokstäver
	    return dateFormatted.toUpperCase();
	}
}
