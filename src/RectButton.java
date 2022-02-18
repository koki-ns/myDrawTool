import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RectButton extends JButton {
	OperateModeManager OMM;
	
	public RectButton(OperateModeManager operateModeManager) {
		super("Rect");
		setIcon(new Icon() {
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				Graphics2D g2 = (Graphics2D)g;
				
				g2.setColor(Color.black);
				g2.fillRect(x, y, 12, 12);
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
		
		addActionListener(new RectListener());
		
		this.OMM = operateModeManager;
	}
	
	class RectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			((StateManager)OMM.getOperateMode()).setState(new RectState(((StateManager)OMM.getOperateMode())));
		}
	}
}

class RectState implements State {
	StateManager stateManager;
	MyRectangle currentRect; //最後に追加した図形を参照する
	int currentX, currentY; //最後に追加した図形の始点の座標
	
	public RectState(StateManager stateManager) {
		this.stateManager = stateManager;
	}
	
	public void mouseDown(int x, int y) {
		currentX = x; currentY = y;
		currentRect = new MyRectangle(x, y, 0, 0, MyDrawing.getSelectedLineColor(), MyDrawing.getSelectedfillColor(), MyDrawing.getSelectedLineWidth());
		currentRect.setDashed(stateManager.getisDashState());
		currentRect.setShadow(stateManager.giveShadow());
		stateManager.addDrawing(currentRect);
	}
	
	public void mouseUp(int x, int y) {
		if (Math.abs(x - currentX) <= 10 || Math.abs(y - currentY) <= 10) {
			stateManager.removeDrawing(currentRect);
		}
	}
	
	public void mouseDrag(int x, int y) {
		if (currentX > x && currentY > y) {
			currentRect.setLocation(x, y);
		} else if (currentX > x) {
			currentRect.setLocation(x, currentY);
		} else if (currentY > y) {
			currentRect.setLocation(currentX, y);
		}
		currentRect.setSize(Math.abs(x - currentX), Math.abs(y - currentY));
	}
	
	public MyDrawing getcurrentDrawing() {
		return currentRect;
	}
	
	public State makeself() {
		
		return new RectState(stateManager);
	}
}