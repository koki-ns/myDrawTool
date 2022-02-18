import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

public class isDashedCheckBox extends JCheckBox{
	StateManager stateManager;
	
	public isDashedCheckBox(StateManager stateManager) {
		super("Dash line");
		this.stateManager = stateManager;
		addItemListener(new isDashedListener());
		
	}
	
	class isDashedListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				stateManager.setisDashedState(true);
			} else {
				stateManager.setisDashedState(false);
			}
		}
	}
}