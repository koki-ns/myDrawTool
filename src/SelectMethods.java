import java.awt.Color;

public interface SelectMethods {
	public abstract void mouseDown(int x, int y);
	public abstract void mouseDrag(int x, int y);
	public abstract void mouseUp(int x, int y);
}




//クリックした位置に図形が無かった場合のメソッドのセット
class RangeMethods implements SelectMethods {
	Mediator mediator;
	int currentX, currentY;
	MyRangeRectangle rangeRect; //選択範囲の矩形
	
	public RangeMethods(Mediator mediator) {
		this.mediator = mediator;
	}
	
	public void mouseDown(int x, int y) {
		rangeRect = new MyRangeRectangle(x, y, 0, 0, Color.black, new Color(0, 0, 0, 0), 1);
		rangeRect.setDashed(true);
		currentX = x;
		currentY = y;
		
		mediator.releaseAllSelected();
		mediator.addDrawing(rangeRect);
	}
	
	public void mouseDrag(int x, int y) {
		if (currentX > x && currentY > y) {
			rangeRect.setLocation(x, y);
		} else if (currentX > x) {
			rangeRect.setLocation(x, currentY);
		} else if (currentY > y) {
			rangeRect.setLocation(currentX, y);
		}
		rangeRect.setSize(Math.abs(x - currentX), Math.abs(y - currentY));
	}
	
	public void mouseUp(int x, int y) {
		mediator.rangeSelect((double)rangeRect.getX(),(double)rangeRect.getY(),(double)rangeRect.getW(),(double)rangeRect.getH());
		mediator.releaseSelected(rangeRect);
		mediator.removeDrawing(rangeRect);
	}
}

