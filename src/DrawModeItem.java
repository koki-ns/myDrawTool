import java.awt.event.*;
import javax.swing.*;

public class DrawModeItem extends JMenuItem {
	//図形描画モードに変更するボタン。図形描画を管理するクラスはStateManagerになる。
	OperateModeManager operateModeManager;
	MyCanvas canvas;
	JTabbedPane tab;
	
	public DrawModeItem(OperateModeManager operateModeManager, JTabbedPane tab) {
		super("図形描画");
		
		addActionListener(new DrawButtonListener());
		
		this.operateModeManager = operateModeManager;
		this.canvas = operateModeManager.getCanvas();
		this.tab = tab;
	}
	
	class DrawButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			operateModeManager.setOperateMode(new StateManager(operateModeManager));
			tab.setSelectedIndex(1);
		}
	}
}