import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MyDiamond extends MyDrawing {
	public MyDiamond(int xpt, int ypt, int wpt, int hpt, Color LColor, Color fColor, int LWidth) {
		super(xpt, ypt, wpt, hpt, LColor, fColor, LWidth);
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
		int xPoints[] = new int[4];
		int yPoints[] = new int[4];
		
		//高さや横幅が負の時の処理
		if (w < 0) {
			x += w;
			w *= -1;
		}
		if (h < 0) {
			y += h;
			h += -1;
		}
		
		xPoints[0] = x + w / 2;
		yPoints[0] = y;
		xPoints[1] = x;
		yPoints[1] = y + h / 2;
		xPoints[2] = x + w / 2;
		yPoints[2] = y + h;
		xPoints[3] = x + w;
		yPoints[3] = y + h / 2;
		
		Graphics2D g2 = (Graphics2D) g;
		if (getDashed()) {
			//破線の時の処理
			g2.setStroke(new MyDashStroke(getLineWidth()));
		} else {
			g2.setStroke(new BasicStroke(getLineWidth()));
		}
		
		g2.rotate(this.rad, x+w/2, y+h/2);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, calculateAlpha(alpha)));
		g2.setColor(fillColor);
		g2.fillPolygon(xPoints, yPoints, 4);
		g2.setColor(lineColor);
		g2.drawPolygon(xPoints, yPoints, 4);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		
		if (canSelected) {
			super.draw(g);
		}
		
		g2.rotate(-(this.rad),x+w/2, y+h/2);
	}
	
	public static void calcuratePoints(int x, int y, int w, int h, int xPoints[], int yPoints[]) {
		xPoints[0] = x + w / 2;
		yPoints[0] = y;
		xPoints[1] = x;
		yPoints[1] = y + h / 2;
		xPoints[2] = x + w / 2;
		yPoints[2] = y + h;
		xPoints[3] = x + w;
		yPoints[3] = y + h / 2;
	}
	
	public MyDiamond clone() {
		MyDiamond myDiamond;
		myDiamond = (MyDiamond)super.clone();
		return myDiamond;
	}
	
	public boolean contains(int x, int y) {
		return region.contains(x, y);
	}
	
	public boolean intersects(double x, double y, double w, double h) {
		return region.intersects(x, y, w, h);
	}
	
	public void setRegion() {
		region = new Polygon(new int[] {x+w/2,x,x+w/2,x+w}, new int[] {y,y+h/2,y+h,y+h/2}, 4);
		rotateRegion();
	}
	
	private void writeObject(ObjectOutputStream out) {
		
	}
	
	private void readObject(ObjectInputStream in) {
		setSelected(false);
		setRegion();
	}
}