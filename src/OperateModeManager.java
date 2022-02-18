public class OperateModeManager {
	OperateMode operateMode;
	MyCanvas canvas;
	Mediator mediator;
	
	AlphaPanel alphaPanel;
	ShadowAlphaPanel shadowAlphaPanel;
	DropShadowCheckBox dropShadowCheckBox;
	
	MirrorCheckBox mirrorCheckBox;
	
	
	
	public OperateModeManager(MyCanvas canvas) {
		this.canvas = canvas;
		this.mediator = canvas.getMediator();
		this.operateMode = new SelectMode(this);
	}
	
	public void mouseDown(int x, int y) { operateMode.mouseDown(x, y); }
	
	public void mouseDrag(int x, int y) { operateMode.mouseDrag(x, y); }
	
	public void mouseUp(int x, int y) { operateMode.mouseUp(x, y); }
	

	
	public void setOperateMode(OperateMode operateMode) {
		
		this.operateMode.modeChange();
		
		this.operateMode = operateMode;
	}
	
	public OperateMode getOperateMode() {
		return operateMode;
	}
	
	public MyCanvas getCanvas() {
		return canvas;
	}
	
	public Mediator getMediator() {
		return mediator;
	}
	
	public void ShadowCheckBoxOn() {
		operateMode.ShadowCheckBoxOn();
	}
	
	public void ShadowCheckBoxOff() {
		operateMode.ShadowCheckBoxOff();
	}
	
	public void setAlphaPanel(AlphaPanel alphaPanel) {
		this.alphaPanel = alphaPanel;
	}
	
	public void setShadowAlphaPanel(ShadowAlphaPanel shadowAlphaPanel) {
		this.shadowAlphaPanel = shadowAlphaPanel;
	}
	
	public void setDropShadowCheckBox(DropShadowCheckBox dropShadowCheckBox) {
		this.dropShadowCheckBox = dropShadowCheckBox;
	}
	
	public DropShadowCheckBox getDropShadowCheckBox() {
		return dropShadowCheckBox;
	}
	
	
	public void setMirrorCheckBox(MirrorCheckBox mirrorCheckBox) {
		this.mirrorCheckBox = mirrorCheckBox;
	}
	
	
	public void notifySelected(MyDrawing d) {
		dropShadowCheckBox.setSelectedEX(d.hasShadow());
		alphaPanel.setAlphaEX(d.getAlpha());
		if (d.hasShadow()) {
			shadowAlphaPanel.setAlphaEX(d.getShadowAlpha());
		}
		
		if (d instanceof MyImage) {
			mirrorCheckBox.setSelectedEX(((MyImage) d).isMirrored());
		}
		
	}
}