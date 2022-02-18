import java.awt.event.*;
import javax.swing.*;

public class MoveButton extends JButton implements ActionListener {
	OperateModeManager oMM;
	Mediator mediator;
	
	MoveButton(OperateModeManager oMM) {
		super("図形の移動");
		setIcon(new ImageIcon("move.png"));
		
		this.oMM = oMM;
		
		mediator = oMM.getMediator();
		
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		((SelectMode)oMM.getOperateMode()).setKeepedMethods(new MoveMethods(mediator));
	}
}

//クリックした位置に図形があった場合のメソッドのセット,移動用
class MoveMethods implements SelectMethods {
	Mediator mediator;
	int currentX, currentY;
	
	public MoveMethods(Mediator mediator) {
		this.mediator = mediator;
	}
	
	public void mouseDown(int x, int y) {
		currentX = x;
		currentY = y;
	}
	
	public void mouseDrag(int x, int y) {
		mediator.move(x-currentX,y-currentY);
		currentX = x;
		currentY = y;
	}
	
	public void mouseUp(int x, int y) {
		mediator.setRegion();
	}
}