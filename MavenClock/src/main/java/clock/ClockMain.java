
package clock;

import java.awt.EventQueue;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ClockMain {
	
	
	public static void main(String[] args) throws InterruptedException {

//		Calendar cal = new GregorianCalendar(2019,9,12,15,50,00);
//		
//		System.out.println(cal.getTime());
//		cal.add(Calendar.SECOND, 60);
//		System.out.println(cal.getTime());
		
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClockWindow frame = new ClockWindow();
					frame.setResizable(false);

					} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

