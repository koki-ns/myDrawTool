import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DiaButton extends JButton {
	OperateModeManager OMM;
	
	public DiaButton(OperateModeManager operateModeManager) {
		super("Diamond");
		setIcon(new Icon() {
			
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				int xPoints[] = new int[4];
				int yPoints[] = new int[4];
				
				MyDiamond.calcuratePoints(x-12, y-7, 14, 14, xPoints, yPoints);
				
				Graphics2D g2 = (Graphics2D)g;
				g2.setColor(Color.black);
				g2.fillPolygon(xPoints, yPoints, 4);
			}
			
			@Override
			public int getIconWidth() {
				// TODO 自動生成されたメソッド・スタブ
				return 0;
			}
			
			@Override
			public int getIconHeight() {
				// TODO 自動生成されたメソッド・スタブ
				return 0;
			}
		});
		
		addActionListener(new DiaListener());
		
		this.OMM = operateModeManager;
	}
	
	class DiaListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			((StateManager)OMM.getOperateMode()).setState(new DiaState(((StateManager)OMM.getOperateMode())));
		}
	}
}


class DiaState implements State {
	StateManager stateManager;
	MyDiamond currentDia; //最後に追加した図形を参照する
	int currentX, currentY; //最後に追加した図形の始点の座標
	
	public DiaState(StateManager stateManager) {
		this.stateManager = stateManager;
	}
	
	public void mouseDown(int x, int y) {
		currentX = x; currentY = y;
		currentDia = new MyDiamond(x, y, 0, 0, MyDrawing.getSelectedLineColor(), MyDrawing.getSelectedfillColor(), MyDrawing.getSelectedLineWidth());
		currentDia.setDashed(stateManager.getisDashState());
		currentDia.setShadow(stateManager.giveShadow());
		stateManager.addDrawing(currentDia);
	}
	
	public void mouseUp(int x, int y) {
		if (Math.abs(x - currentX) <= 10 || Math.abs(y - currentY) <= 10) {
			stateManager.removeDrawing(currentDia);
		}
	}
	
	public void mouseDrag(int x, int y) {
		if (currentX > x && currentY > y) {
			currentDia.setLocation(x, y);
		} else if (currentX > x) {
			currentDia.setLocation(x, currentY);
		} else if (currentY > y) {
			currentDia.setLocation(currentX, y);
		}
		currentDia.setSize(Math.abs(x - currentX), Math.abs(y - currentY));
	}
	
	public MyDrawing getcurrentDrawing() {
		return currentDia;
	}
	
	public State makeself() {
		return new DiaState(stateManager);
	}
}
