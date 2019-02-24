
package clock;

import java.awt.EventQueue;

public class ClockMain {
	
	
	static String OS;
	static int h;
	static int w;
	public static void main(String[] args) throws InterruptedException {


		
		// Undersöker vilket operativsystem som körs
		OS = System.getProperty("os.name");
		// Dimensionsvariabler till den lilla vyn för att matcha skillnader i OS
		if (OS.equalsIgnoreCase("Mac OS X")) {h=300; w=172;}
		else if (OS.equalsIgnoreCase("Windows 10")) {h=305; w=179;}
		else {h=305; w=179;}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					ClockWindow frame = new ClockWindow(h, w);
					frame.setResizable(false);

					} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}