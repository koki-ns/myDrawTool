import java.awt.*;
import java.awt.geom.Line2D;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MyPointFigure extends MyDrawing {
	public MyPointFigure(int xpt, int ypt, int wpt, int hpt, Color LColor, Color fColor, int LWidth) {
		super(xpt, ypt, wpt, hpt, LColor, fColor, LWidth);
	}
	
	public void draw(Graphics g) {
		
		int x = getX();
		int y = getY();
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setStroke(new BasicStroke(getLineWidth()));

		g2.setColor(getLineColor());
		g2.drawLine(x, y, x, y);
	
		super.draw(g);
	}
	
	public MyPointFigure clone() {
		MyPointFigure myPointFigure;
		myPointFigure = (MyPointFigure)super.clone();
		return myPointFigure;
	}
	
	public boolean contains(int x, int y) {
		return region.contains(x, y);
	}
	
	//MyRectangleが図形dを(厳密には図形が構成する矩形領域を)包含しているかの判定
	public boolean contains(MyDrawing d) {
		return region.contains((double)d.getX(), (double)d.getY(), (double)d.getW(), (double)d.getH());
	}
	
	public boolean intersects(double x, double y, double w, double h) {
		return region.intersects(x, y, w, h);
	}
	
	public void setRegion() {
		region = new Line2D.Double((double)x, (double)y, (double)x, (double)y);
	}
	
	private void writeObject(ObjectOutputStream out) {
		
	}
	
	private void readObject(ObjectInputStream in) {
		setRegion();
	}
}