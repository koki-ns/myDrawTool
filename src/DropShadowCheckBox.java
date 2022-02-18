import java.awt.event.*;
import javax.swing.*;

public class DropShadowCheckBox extends JCheckBox {
	OperateModeManager operateModeManager;
	
	
	public DropShadowCheckBox(OperateModeManager operateModeManager) {
		super("ドロップシャドウ");
		
		addActionListener(new DropShadowListener());
		
		this.operateModeManager = operateModeManager;
	}
	
	class DropShadowListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (((JCheckBox)(e.getSource())).isSelected() == true) {
				operateModeManager.ShadowCheckBoxOn();
			} else {
				operateModeManager.ShadowCheckBoxOff();
			}
		}
	}

	
	public void setSelectedEX(boolean b) {
		setSelected(b);
	}
}