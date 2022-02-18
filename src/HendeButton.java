import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HendeButton extends JButton {
	OperateModeManager OMM;
	
	public HendeButton(OperateModeManager operateModeManager) {
		super("Hende");
		setIcon(new Icon() {
			
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				int xPoints[] = new int[11];
				int yPoints[] = new int[11];
				
				MyHendecagonal.culculatePoints(x-12, y-7, 14, 14, xPoints, yPoints);
				Graphics2D g2 = (Graphics2D)g;
				g2.setColor(Color.black);
				g2.fillPolygon(xPoints, yPoints, 11);
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
		
		addActionListener(new HendeListener());
		
		this.OMM = operateModeManager;
	}
	
	class HendeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			((StateManager)OMM.getOperateMode()).setState(new HendeState(((StateManager)OMM.getOperateMode())));
		}
	}
}

 class HendeState implements State {
	StateManager stateManager;
	MyHendecagonal currentHende; //最後に追加した図形を参照する
	int currentX, currentY; //最後に追加した図形の始点の座標
	
	public HendeState(StateManager stateManager) {
		this.stateManager = stateManager;
	}
	
	public void mouseDown(int x, int y) {
		currentX = x; currentY = y;
		currentHende = new MyHendecagonal(x, y, 0, 0, MyDrawing.getSelectedLineColor(), MyDrawing.getSelectedfillColor(), MyDrawing.getSelectedLineWidth());
		currentHende.setDashed(stateManager.getisDashState());
		currentHende.setShadow(stateManager.giveShadow());
		stateManager.addDrawing(currentHende);
	}
	
	public void mouseUp(int x, int y) {
		if (Math.abs(x - currentX) <= 10 || Math.abs(y - currentY) <= 10) {
			stateManager.removeDrawing(currentHende);
		}
	}
	
	public void mouseDrag(int x, int y) {
		if (currentX > x && currentY > y) {
			if (Math.abs(x - currentX) > Math.abs(y - currentY)) {
				currentHende.setLocation(currentX - Math.abs(y - currentY), y);
			} else {
				currentHende.setLocation(x, currentY - Math.abs(x - currentX));
			}
		} else if (currentX > x) {
			if (Math.abs(x - currentX) > Math.abs(y - currentY)) {
				currentHende.setLocation(currentX - Math.abs(y - currentY), currentY);
			} else {
				currentHende.setLocation(x, currentY);
			}
		} else if (currentY > y) { // y座標が基準点より小さい、すなわち上にあるとき
			if (Math.abs(y - currentY) > Math.abs(x - currentX)) {
				currentHende.setLocation(currentX, currentY - Math.abs(x - currentX));
			} else {
				currentHende.setLocation(currentX, y);
			}
		}
		currentHende.setSize(Math.abs(x - currentX), Math.abs(y - currentY));
	}
	
	public MyDrawing getcurrentDrawing() {
		return currentHende;
	}
	
	public State makeself() {
		return new HendeState(stateManager);
	}
}
