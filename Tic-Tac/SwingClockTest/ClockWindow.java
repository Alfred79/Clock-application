

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
import java.util.Calendar;
import java.util.GregorianCalendar;


public class ClockWindow extends JFrame {
	
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
			public void run() {
			
			while (true) {
			Calendar calendarObjekt = new GregorianCalendar();
			String dateString = calendarObjekt.getTime().toString(); 
			try {
					this.sleep(1000);//pausa 1 sek
				} catch (InterruptedException e) {
				e.printStackTrace();
				}
				showTime(dateString); //Uppdatera textfältet som visar tiden
				}
			}	
			}.start();
		
		
	}
	
	//skapar och sätter inställningar för grafiska objekt till fönstret
	private void initComponents() {
		setSize(900,600);
		timeDisplayPanel = new JPanel();
		timeDisplayPanel.setBackground(Color.ORANGE);
		timeDisplayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(timeDisplayPanel);
		timeDisplayPanel.setLayout(new BoxLayout(timeDisplayPanel, BoxLayout.X_AXIS));
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
}
