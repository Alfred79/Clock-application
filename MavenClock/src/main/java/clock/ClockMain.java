
package clock;

import java.awt.EventQueue;

public class ClockMain {
	
	
	static String OS;
	static int h;
	static int w;
	public static void main(String[] args) throws InterruptedException {


		
		
		OS = System.getProperty("os.name");
		
		if (OS.equalsIgnoreCase("Mac OS X")) {h=300; w=172;}
		else if (OS.equalsIgnoreCase("Windows 10")) {h=305; w=179;}
		else {h=305; w=179;}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//System.out.println(OS + h + " " + w);
					ClockWindow frame = new ClockWindow(h, w);
					frame.setResizable(false);

					} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

