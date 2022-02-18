import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ItemEvent;

public class StateManager implements OperateMode {
	OperateModeManager operateModeManager;
	State state;
	DropShadowCheckBox dropShadowCheckBox;
	boolean isDashstate;
	MyCanvas canvas;
	Mediator mediator;
	
	
	public StateManager(OperateModeManager operateModeManager) {
		this.operateModeManager = operateModeManager;
		this.canvas = operateModeManager.getCanvas();
		mediator = canvas.getMediator();
		state = new RectState(this);
		this.dropShadowCheckBox = operateModeManager.getDropShadowCheckBox();
		isDashstate = false;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public void mouseDown(int x, int y) {
		state.mouseDown(x, y);
	}
	
	public void mouseDrag(int x, int y) {
		state.mouseDrag(x, y);
	}
	
	public void mouseUp(int x, int y) {
		state.mouseUp(x, y);
	}
	
	public void modeChange() {
		
	}
	
	public void addDrawing(MyDrawing d) {
		mediator.addDrawing(d);
	}
	
	public void removeDrawing(MyDrawing d) {
		mediator.removeDrawing(d);
	}
	
	public State getState() {
		return state;
	}
	
	public void setisDashedState(boolean b) {
		isDashstate = b;
	}
	
	public boolean getisDashState() {
		return isDashstate;
	}
	
	public boolean giveShadow() {
		return dropShadowCheckBox.isSelected();
	}
	
	public Mediator getMediator() {
		return mediator;
	}
	
	public void ShadowCheckBoxOn() {
	}
	
	public void ShadowCheckBoxOff() {
	}
}
