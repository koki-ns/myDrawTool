import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.*;

public class SelectModeButton extends JButton {
	OperateModeManager operateModeManager;
	
	SelectModeButton(OperateModeManager operateModeManager) {
		super("SelectMode");
		
		addActionListener(new SelectListener());
		
		this.operateModeManager = operateModeManager;
	}
	
	class SelectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			operateModeManager.setOperateMode((new SelectMode(operateModeManager)));
		}
	}
}



class SelectMode implements OperateMode {
	OperateModeManager operateModeManager;
	MyCanvas canvas;
	Mediator mediator;
	ShadowState shadowState;
	MyDrawing currentDrawing;
	
	SelectMethods keepedMethods; //保持しておく変数
	SelectMethods executedMethods; //実行される変数
	
	
	SelectMode(OperateModeManager operateModeManager) {
		this.operateModeManager = operateModeManager;
		canvas = operateModeManager.getCanvas();
		this.mediator = operateModeManager.getMediator();
		
		keepedMethods = new MoveMethods(mediator);
		
		currentDrawing = null;
	}
	
	public void mouseDown(int x, int y) {
		MyDrawing temp = mediator.selectMouseDown(x, y);
		if (temp != null) {
			executedMethods = keepedMethods;
			if (mediator.howManySelected() == 1) {
				notifySelected(temp);
			}
		} else {
			executedMethods = new RangeMethods(mediator);
		}
		executedMethods.mouseDown(x, y);
	}
	
	public void mouseUp(int x, int y) {
		executedMethods.mouseUp(x, y);
	}
	
	public void mouseDrag(int x, int y) {
		executedMethods.mouseDrag(x, y);
	}
	
	public MyDrawing getcurrentDrawing() {
		return currentDrawing;
	}

	@Override
	public void ShadowCheckBoxOn() {
		mediator.generateShadowForSelected();
		canvas.repaint();
	}

	@Override
	public void ShadowCheckBoxOff() {
		mediator.clearShadowForSelected();
		canvas.repaint();
	}
	
	public void setKeepedMethods(SelectMethods keepedMethods) {
		this.keepedMethods = keepedMethods;
	}
	
	//選択された図形の状態をGUI部品に通知
	public void notifySelected(MyDrawing d) {
		operateModeManager.notifySelected(d);
	}
	
	public void modeChange() {
		mediator.releaseAllSelected();
		mediator.clearBuffer();
		mediator.repaint();
	}
}


class CutButton extends JButton implements ActionListener{
	Mediator mediator;
	
	public CutButton(Mediator mediator) {
		super("カット");
		setIcon(new ImageIcon("cut.png"));
		
		addActionListener(this);
		
		this.mediator = mediator;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		mediator.cut();
	}
}

class CopyButton extends JButton implements ActionListener{
	Mediator mediator;
	
	public CopyButton(Mediator mediator) {
		super("コピー");
		setIcon(new ImageIcon("copy.png"));
		
		addActionListener(this);
		
		this.mediator = mediator;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		mediator.copy();
	}
}

class PasteButton extends JButton implements ActionListener{
	Mediator mediator;
	
	public PasteButton(Mediator mediator) {
		super("貼り付け");
		setIcon(new ImageIcon("paste.png"));
		
		addActionListener(this);
		
		this.mediator = mediator;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		mediator.paste();
	}
}

class DeleteButton extends JButton implements ActionListener{
	Mediator mediator;
	
	public DeleteButton(Mediator mediator) {
		super("削除");
		setIcon(new ImageIcon("delete.png"));
		
		addActionListener(this);
		
		this.mediator = mediator;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		mediator.delete();
	}
}