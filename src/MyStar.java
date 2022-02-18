import java.awt.*;
import java.io.*;

public class MyStar extends MyDrawing {
	
	public MyStar(int xpt, int ypt, int wpt, int hpt, Color LColor, Color fColor, int LWidth) {
		super(xpt, ypt, wpt, hpt, LColor, fColor, LWidth);
		setSize(w, h);
		setRegion();
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
		int xPoints[] = new int[10];
		int yPoints[] = new int[10];
		
		culculatePoints(x, y, w, h, xPoints, yPoints);
		
		
		Graphics2D g2 = (Graphics2D) g;
		if (getDashed()) {
			//破線の時の処理
			g2.setStroke(new MyDashStroke(getLineWidth()));
		} else {
			g2.setStroke(new BasicStroke(getLineWidth()));
		}
		
		g2.rotate(this.rad, x+w/2, y+h/2); //世界をねじ曲げる
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, calculateAlpha(alpha)));
		g2.setColor(fillColor);
		g2.fillPolygon(xPoints, yPoints, 10);
		g2.setColor(lineColor);
		g2.drawPolygon(xPoints, yPoints, 10);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		
		if (canSelected) {
			super.draw(g);
		}
		
		g2.rotate(-(this.rad),x+w/2, y+h/2);
	}
	
	public static void culculatePoints(int x, int y, int w, int h, int xPoints[], int yPoints[]) {
		double basis;
		double d_x;
		double d_y;
		
		
		if (w < 0) {
			x += w;
			w *= -1;
		}
		if (h < 0) {
			y += h;
			h += -1;
		}
		if (w < h) {
			basis = (double)w;
		} else {
			basis = (double)h;
		}
		
		d_x = (double)x + basis / 2;
		d_y = (double)y + basis / 2;
		basis = basis / 2;
		for (int i = 0; i < 5; i++) {
			xPoints[i*2] = (int)(d_x - (basis * Math.sin((double)i / (double)5 * 2 * Math.PI)));
			yPoints[i*2] = (int)(d_y - (basis * Math.cos((double)i / (double)5 * 2 * Math.PI)));
			xPoints[i*2+1] = (int)(d_x - ((basis / 2) * Math.sin(((double)1 / (double)10 + (double)i / (double)5) * 2 * Math.PI)));
			yPoints[i*2+1] = (int)(d_y - ((basis / 2) * Math.cos(((double)1 / (double)10 + (double)i / (double)5) * 2 * Math.PI)));
		}
	}
	
	public MyStar clone() {
		MyStar myStar;
		myStar = (MyStar)super.clone();
		return myStar;
	}
	
	public void setSize(int w, int h) {
		if (w > h) {
			super.setSize(h, h);
		} else {
			super.setSize(w, w);
		}
	}
	
	public boolean contains(int x, int y) {
		return region.contains(x, y);
	}
	
	public boolean intersects(double x, double y, double w, double h) {
		return region.intersects(x, y, w, h);
	}
	
	public void setRegion() {
		int xPoints[] = new int[10];
		int yPoints[] = new int[10];
		
		culculatePoints(x, y, w, h, xPoints, yPoints);
		region = new Polygon(xPoints, yPoints, 10);
		rotateRegion();
	}
	
	private void writeObject(ObjectOutputStream out) {
		
	}
	
	private void readObject(ObjectInputStream in) {
		setSelected(false);
		setRegion();
	}
}