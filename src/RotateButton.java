import java.awt.event.*;
import javax.swing.*;

public class RotateButton extends JButton implements ActionListener {
	OperateModeManager oMM;
	Mediator mediator;
	
	RotateButton(OperateModeManager oMM) {
		super("図形の回転");
		setIcon(new ImageIcon("rotate.png"));
		
		this.oMM = oMM;
		
		mediator = oMM.getMediator();
		
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		((SelectMode)oMM.getOperateMode()).setKeepedMethods(new RotateMethods(mediator));
	}
}

//クリックした位置に図形があった場合のメソッドのセット,移動用
class RotateMethods implements SelectMethods {
	Mediator mediator;
	double currentX, currentY;
	double centerX, centerY;
	double ChangedcurrentX, ChangedcurrentY;
	
	double startRadian;
	
	public RotateMethods(Mediator mediator) {
		this.mediator = mediator;
	}
	
	public void mouseDown(int x, int y) {
		MyDrawing temp;
		
		currentX = (double)x;
		currentY = (double)y;
		temp = mediator.contains(x, y);
		centerX = (double)temp.getX() + (double)temp.getW() / 2.0;
		centerY = (double)temp.getY() + (double)temp.getH() / 2.0;
		/*
		ChangedcurrentX = currentX-centerX;
		ChangedcurrentY = currentY-centerY;
		*/
		startRadian = -Math.atan2(currentY-centerY, currentX-centerX);
		
	}
	
	public void mouseDrag(int x, int y) {
		/*
		mediator.setRad(Math.acos((ChangedcurrentX * ((double)x-centerX) + ChangedcurrentY * ((double)y-centerY)) 
				/ (getDistance(currentX, currentY, centerX, centerY) * getDistance((double)x, (double)y, centerX, centerY))));
				 */
		mediator.setRad(startRadian + Math.atan2(y-centerY, x-centerX));
	}
	
	public void mouseUp(int x, int y) {
		mediator.setRad_end();
	}
	
	public double getDistance (double x1, double y1, double x2, double y2) {
		double d = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
		return d;
	}
}