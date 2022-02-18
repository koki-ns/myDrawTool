import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DrawModeButton extends JButton {
	//図形描画モードに変更するボタン。図形描画を管理するクラスはStateManagerになる。
	OperateModeManager operateModeManager;
	MyCanvas canvas;
	
	public DrawModeButton(OperateModeManager operateModeManager) {
		super("DrawMode");
		
		addActionListener(new DrawButtonListener());
		
		this.operateModeManager = operateModeManager;
		this.canvas = operateModeManager.getCanvas();
	}
	
	class DrawButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			operateModeManager.setOperateMode(new StateManager(operateModeManager));
		}
	}
}