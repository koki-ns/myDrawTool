public interface OperateMode {
	public abstract void mouseDown(int x, int y);
	public abstract void mouseDrag(int x, int y);
	public abstract void mouseUp(int x, int y);
	public abstract void ShadowCheckBoxOn();
	public abstract void ShadowCheckBoxOff();
	public abstract void modeChange();
}