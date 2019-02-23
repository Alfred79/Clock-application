package clock;

import java.awt.EventQueue;

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
