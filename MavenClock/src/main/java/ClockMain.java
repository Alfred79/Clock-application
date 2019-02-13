
import java.awt.EventQueue;

public class ClockMain {
	public static void main(String[] args) {
			

		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClockWindowMerge frame = new ClockWindowMerge();
					} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
