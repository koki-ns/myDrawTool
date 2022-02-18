import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.*;

public class SelectModeItem extends JMenuItem {
	OperateModeManager operateModeManager;
	JTabbedPane tab;
	
	SelectModeItem(OperateModeManager operateModeManager, JTabbedPane tab) {
		super("選択");
		
		addActionListener(new SelectListener());
		
		this.operateModeManager = operateModeManager;
		this.tab = tab;
	}
	
	class SelectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			operateModeManager.setOperateMode((new SelectMode(operateModeManager)));
			tab.setSelectedIndex(0);
		}
	}
}
