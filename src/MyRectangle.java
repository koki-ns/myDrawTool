import java.awt.*;
import java.io.*;

public class MyRectangle extends MyDrawing {
	public MyRectangle(int xpt, int ypt, int wpt, int hpt, Color LColor, Color fColor, int LWidth) {
		super(xpt, ypt, wpt, hpt, LColor, fColor, LWidth);
		region = new Rectangle(x, y, w, h);
	}
	
	public void draw(Graphics g) {
		int x = getX();
		int y = getY();
		
		if (hasShadow()) {
			drawFigure(g, x+10, y+10, Color.black, Color.black, shadowAlpha, false);
		}
		
		drawFigure(g, x, y, fillColor, lineColor, alpha, true);
	}
	
	public void drawFigure(Graphics g, int x, int y, Color fillColor, Color lineColor, int alpha, boolean canSelected) {
		
		int w = getW();
		int h = getH();
		
		//高さや横幅が負の時の処理
		if (w < 0) {
			x += w;
			w *= -1;
		}
		if (h < 0) {
			y += h;
			h += -1;
		}
		
		Graphics2D g2 = (Graphics2D) g;
		//g2.setStroke(new BasicStroke(getLineWidth()));
		if (getDashed()) {
			//破線の時の処理
			g2.setStroke(new MyDashStroke(getLineWidth()));
		} else {
			g2.setStroke(new BasicStroke(getLineWidth()));
		}
		
		g2.rotate(this.rad, (double)x+(double)w/2d, (double)y+(double)h/2d); //世界をねじ曲げる
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, calculateAlpha(alpha)));
		g2.setColor(fillColor);
		g2.fillRect(x, y, w, h);
		g2.setColor(lineColor);
		g2.drawRect(x, y, w, h);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		if (canSelected) {
			super.draw(g);
		}
		g2.rotate(-(this.rad),(double)x+(double)w/2, (double)y+(double)h/2); //世界を元に戻す
		//rotateはどうやら常に最初の位置からの角度を参照し続けるらしい？
	}
	
	
	
	public MyRectangle clone() {
		MyRectangle myRectangle;
		myRectangle = (MyRectangle)super.clone();
		return myRectangle;
	}
	
	public boolean contains(int x, int y) {
		return region.contains(x, y);
	}
	
	
	
	public boolean intersects(double x, double y, double w, double h) {
		return region.intersects(x, y, w, h);
	}
	
	public void setRegion() {
		region = new Rectangle(x, y, w, h);
		rotateRegion();
	}
	
	private void writeObject(ObjectOutputStream out) {
		
	}
	
	private void readObject(ObjectInputStream in) {
		setSelected(false);
		setRegion();
	}
	
}