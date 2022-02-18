import java.awt.*;
import java.io.*;

public class MyRangeRectangle extends MyRectangle {
	
	public MyRangeRectangle(int xpt, int ypt, int wpt, int hpt, Color LColor, Color fColor, int LWidth) {
		super(xpt, ypt, wpt, hpt, LColor, fColor, LWidth);
		setAlpha(100);
	}
	
	public MyRangeRectangle clone() {
		MyRangeRectangle myRangeRectangle;
		myRangeRectangle = (MyRangeRectangle)super.clone();
		return myRangeRectangle;
	}
	
	//MyRangeRectangleが図形dを(厳密には図形が構成する矩形領域を)包含しているかの判定
	public boolean contains(MyDrawing d) {
		return region.contains((double)d.getX(), (double)d.getY(), (double)d.getW(), (double)d.getH());
	}
}