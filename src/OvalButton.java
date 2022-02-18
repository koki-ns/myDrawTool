import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class OvalButton extends JButton {
	OperateModeManager OMM;
	
	public OvalButton(OperateModeManager operateManager) {
		super("Oval");
		setIcon(new Icon() {
			
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				Graphics2D g2 = (Graphics2D) g;
				
				g2.setColor(Color.black);
				g2.fillOval(x, y, 12, 12);
				
			}
			
			@Override
			public int getIconWidth() {
				// TODO 自動生成されたメソッド・スタブ
				return 12;
			}
			
			@Override
			public int getIconHeight() {
				// TODO 自動生成されたメソッド・スタブ
				return 12;
			}
		});
		
		addActionListener(new OvalListener());
		
		this.OMM = operateManager;
	}
	
	class OvalListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			((StateManager)OMM.getOperateMode()).setState(new OvalState(((StateManager)OMM.getOperateMode())));
		}
	}
}


class OvalState implements State {
	StateManager stateManager;
	MyOval currentOval; //最後に追加した図形を参照する
	int currentX, currentY; //最後に追加した図形の始点の座標
	
	
	public OvalState(StateManager stateManager) {
		this.stateManager = stateManager;
	}
	
	public void mouseDown(int x, int y) {
		currentX = x; currentY = y;
		currentOval = new MyOval(x, y, 0, 0, MyDrawing.getSelectedLineColor(), MyDrawing.getSelectedfillColor(), MyDrawing.getSelectedLineWidth());
		currentOval.setDashed(stateManager.getisDashState());
		currentOval.setShadow(stateManager.giveShadow());
		stateManager.addDrawing(currentOval);
	}
	
	public void mouseUp(int x, int y) {
		if (Math.abs(x - currentX) <= 10 || Math.abs(y - currentY) <= 10) {
			stateManager.removeDrawing(currentOval);
		}
	}
	
	public void mouseDrag(int x, int y) {
		if (currentX > x && currentY > y) {
			currentOval.setLocation(x, y);
		} else if (currentX > x) {
			currentOval.setLocation(x, currentY);
		} else if (currentY > y) {
			currentOval.setLocation(currentX, y);
		}
		currentOval.setSize(Math.abs(x - currentX), Math.abs(y - currentY));
	}
	
	public MyDrawing getcurrentDrawing() {
		return currentOval;
	}
	
	public State makeself() {
		return new OvalState(stateManager);
	}
}
