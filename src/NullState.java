import javax.swing.*;

public class NullState implements State {
	
	public MyDrawing getcurrentDrawing() {
		return null;
	}
	
	public State makeself() {return new NullState();}

	@Override
	public void mouseDown(int x, int y) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void mouseUp(int x, int y) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void mouseDrag(int x, int y) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
