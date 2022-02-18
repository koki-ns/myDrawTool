public class UnCheckedShadowState extends ShadowState {
	State state;
	State parentState;
	
	public UnCheckedShadowState() {
		
	}
	
	public void setParentState(State state) {
		this.parentState = state;
	}
	
	public void mouseDown(int x, int y) {}
	
	public void mouseUp(int x, int y) {}
	
	public void mouseDrag(int x, int y) {}
}