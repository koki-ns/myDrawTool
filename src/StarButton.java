import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StarButton extends JButton {
	OperateModeManager OMM;
	
	public StarButton(OperateModeManager operateModeManager) {
		super("Star");
		setIcon(new Icon() {
			
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				int xPoints[] = new int[10];
				int yPoints[] = new int[10];
				
				MyStar.culculatePoints(x-12, y-7, 14, 14, xPoints, yPoints);
				Graphics2D g2 = (Graphics2D)g;
				g2.setColor(Color.black);
				g2.fillPolygon(xPoints, yPoints, 10);
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
		
		addActionListener(new StarListener());
		
		this.OMM = operateModeManager;
	}
	
	class StarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			((StateManager)OMM.getOperateMode()).setState(new StarState(((StateManager)OMM.getOperateMode())));
		}
	}
}

 class StarState implements State {
	StateManager stateManager;
	MyStar currentStar; //最後に追加した図形を参照する
	int currentX, currentY; //最後に追加した図形の始点の座標
	
	public StarState(StateManager stateManager) {
		this.stateManager = stateManager;
	}
	
	public void mouseDown(int x, int y) {
		currentX = x; currentY = y;
		currentStar = new MyStar(x, y, 0, 0, MyDrawing.getSelectedLineColor(), MyDrawing.getSelectedfillColor(), MyDrawing.getSelectedLineWidth());
		currentStar.setDashed(stateManager.getisDashState());
		currentStar.setShadow(stateManager.giveShadow());
		stateManager.addDrawing(currentStar);
	}
	
	public void mouseUp(int x, int y) {
		if (Math.abs(x - currentX) <= 10 || Math.abs(y - currentY) <= 10) {
			stateManager.removeDrawing(currentStar);
		}
	}
	
	public void mouseDrag(int x, int y) {
		if (currentX > x && currentY > y) {
			if (Math.abs(x - currentX) > Math.abs(y - currentY)) {
				currentStar.setLocation(currentX - Math.abs(y - currentY), y);
			} else {
				currentStar.setLocation(x, currentY - Math.abs(x - currentX));
			}
		} else if (currentX > x) {
			if (Math.abs(x - currentX) > Math.abs(y - currentY)) {
				currentStar.setLocation(currentX - Math.abs(y - currentY), currentY);
			} else {
				currentStar.setLocation(x, currentY);
			}
		} else if (currentY > y) { // y座標が基準点より小さい、すなわち上にあるとき
			if (Math.abs(y - currentY) > Math.abs(x - currentX)) {
				currentStar.setLocation(currentX, currentY - Math.abs(x - currentX));
			} else {
				currentStar.setLocation(currentX, y);
			}
		}
		currentStar.setSize(Math.abs(x - currentX), Math.abs(y - currentY));
	}
	
	public MyDrawing getcurrentDrawing() {
		return currentStar;
	}
	
	public State makeself() {
		return new StarState(stateManager);
	}
}
