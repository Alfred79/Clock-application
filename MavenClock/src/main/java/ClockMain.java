
import java.awt.EventQueue;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ClockMain {
	public static void main(String[] args) {
	
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClockWindow frame = new ClockWindow();
					} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
