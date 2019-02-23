package clock;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetSound implements ActionListener{

	int sound;
	
	public SetSound(int sound) {
		this.sound = sound;
	}
	public void actionPerformed(ActionEvent e) {
		ClockWindow.setAlarmSound(sound);
	}
}