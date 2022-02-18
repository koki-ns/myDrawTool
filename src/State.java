public interface State {
	public abstract void mouseDown(int x, int y);
	public abstract void mouseUp(int x, int y);
	public abstract void mouseDrag(int x, int y);
	public abstract MyDrawing getcurrentDrawing();
	public abstract State makeself();
}
