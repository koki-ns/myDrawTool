import java.util.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.*;

import javax.swing.JPanel;

public class MyCanvas extends JPanel {
	//MyCanvasは描画を行う
	OperateModeManager operateModeManager;
	Mediator mediator;
	CanvasAdapter canvasAdapter;
	CanvasMotionListener canvasMotionListener;
	
	public MyCanvas() {
		this.mediator = new Mediator(this);
		
		setBackground(Color.white);
		
		canvasAdapter = new CanvasAdapter();
		canvasMotionListener = new CanvasMotionListener();
		
		addListeners();
	}
	
	public Mediator getMediator() {
		return mediator;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Enumeration<MyDrawing> e = mediator.drawingElements();
		while (e.hasMoreElements()) {
			MyDrawing d = e.nextElement();
			d.draw(g);
		}
	}
	
	public void setOperateModeManager(OperateModeManager operateModeManager) {
		this.operateModeManager = operateModeManager;
	}
	
	public void addListeners() {
		addMouseListener(canvasAdapter);
		addMouseMotionListener(canvasMotionListener);
	}
	
	public void removeListeners() {
		removeMouseListener(canvasAdapter);
		removeMouseMotionListener(canvasMotionListener);
	}
	
	class CanvasAdapter extends MouseAdapter {
		//現在の状態のmouseDown 処理の呼び出し
		public void mousePressed(MouseEvent e) {
			operateModeManager.mouseDown(e.getX(), e.getY());
			repaint();
		}
		
		public void mouseReleased(MouseEvent e) {
			operateModeManager.mouseUp(e.getX(), e.getY());
			repaint();
		}
	}
	
	class CanvasMotionListener extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent e) {
			operateModeManager.mouseDrag(e.getX(), e.getY());;
			repaint();
		}
	}
	
}