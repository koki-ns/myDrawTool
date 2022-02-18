import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;

public class MyDrawing implements Cloneable, Serializable {
	int x, y, w, h; //X 座標,Y 座標, 幅, 高さ
	Color lineColor, fillColor; //線の色, 塗り色
	int alpha; //透明度
	int shadowAlpha;
	int lineWidth; //線の太さ
	boolean isDashed; //メンバ変数への追加
	boolean hasShadow;

	boolean isClear;
	
	double rad; //回転角(表示用)
	double rad_temp; //回転角(保存用)
	
	boolean isSelected;
	transient protected Shape region;
	final int SIZE = 7; //選択表示矩形に付く四角形の大きさ
	
	//アニメーションを行うフラグ
	//boolean isAnimated;
	AnimationSettings animationSettings;
	
	static Color selectedfillColor = Color.white;
	static Color selectedLineColor = Color.black;
	static int selectedAlpha = 100;
	static int selectedShadowAlpha = 100;
	static int selectedLineWidth = 1;
	
	public MyDrawing() {
		x = y = 0;
		w = h = 40;
		lineColor = Color.black;
		fillColor = Color.white;
		lineWidth = 1;
		isDashed = false;
		rad = 0;
		setRegion();
	}
	
	public MyDrawing(int xpt, int ypt, int wpt, int hpt, Color LColor, Color fColor, int LWidth) {
		x = xpt;
		y = ypt;
		w = wpt;
		h = hpt;
		lineColor = LColor;
		fillColor = fColor;
		lineWidth = LWidth;
		isDashed = false;
		hasShadow = false;
		rad = 0.0;
		rad_temp = 0.0;
		alpha = getSelectedAlpha();
		shadowAlpha = getSelectedShadowAlpha();
		animationSettings = null;
	}
	
	//---------staticメソッド-------------------------
	
	public static void setSelectedLineColor(Color c) {
		//オブジェクトの線の色を取得する処理を書く
		selectedLineColor = c;
	}
	
	public static void setSelectedfillColor(Color c) {
		//オブジェクトの内部の色を設定する処理を書く
		selectedfillColor = c;
	}
	
	public static void setSelectedLineWidth(int selectedLineWidth) {
		MyDrawing.selectedLineWidth = selectedLineWidth;
	}
	
	public static void setSelectedAlpha(int selectedAlpha) {
		MyDrawing.selectedAlpha = selectedAlpha;
	}
	
	public static void setSelectedShadowAlpha(int shadowAlpha) {
		MyDrawing.selectedShadowAlpha = shadowAlpha;
	}
	
	public static Color getSelectedLineColor() {
		//オブジェクトの線の色を取得する処理を書く
		return selectedLineColor;
	}
	
	public static Color getSelectedfillColor() {
		//オブジェクトの内部の色を設定する処理を書く
		return selectedfillColor;
	}
	
	public static int getSelectedLineWidth() {
		return selectedLineWidth;
	}
	
	public static int getSelectedAlpha() {
		return selectedAlpha;
	}
	
	public static int getSelectedShadowAlpha() {
		return selectedShadowAlpha;
	}
	
	//------------------------------------------------
	
	public void draw(Graphics g) {
		//選択状態を表す四角形を描く
		if (isSelected) {
			g.setColor(Color.black);
			g.fillRect(x+w/2-SIZE/2,y-SIZE/2,SIZE,SIZE);
			g.fillRect(x-SIZE/2,y+h/2-SIZE/2,SIZE,SIZE);
			g.fillRect(x+w/2-SIZE/2,y+h-SIZE/2,SIZE,SIZE);
			g.fillRect(x+w-SIZE/2,y+h/2-SIZE/2,SIZE,SIZE);
			g.fillRect(x-SIZE/2,y-SIZE/2,SIZE,SIZE);
			g.fillRect(x+w-SIZE/2,y-SIZE/2,SIZE,SIZE);
			g.fillRect(x-SIZE/2,y+h-SIZE/2,SIZE,SIZE);
			g.fillRect(x+w-SIZE/2,y+h-SIZE/2,SIZE,SIZE);
			
		}
	}
	
	public MyDrawing clone() {
		MyDrawing d;
		try {
			d = (MyDrawing)super.clone();
		} catch (CloneNotSupportedException ce) {
			// TODO: handle exception
			throw new RuntimeException();
		}
		return d;
	}
	
	//アクセッサ
	public void move(int dx, int dy) {
		//オブジェクトを移動する処理を書く
		x = x + dx;
		y = y + dy;
	}
	
	public void setLocation(int x, int y) {
		//オブジェクトの位置を変更する処理を書く
		setCoordinate(x, y);
		setRegion();
	}
	
	//座標だけ移動
	public void setCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setSize(int w, int h) {
		//オブジェクトのサイズを変更する処理を書く
		setScale(w, h);
		setRegion();
	}
	
	public void setScale(int w, int h) {
		this.w = w;
		this.h = h;
	}
	
	public int getX() {
		//オブジェクトのX位置を取得する処理を書く
		return x;
	}

	public int getY() {
		//オブジェクトのX位置を取得する処理を書く
		return y;
	}

	public int getW() {
		//オブジェクトのX位置を取得する処理を書く
		return w;
	}
	
	public int getH() {
		//オブジェクトのX位置を取得する処理を書く
		return h;
	}
	
	public void setLineColor(Color c) {
		//オブジェクトの線の色を取得する処理を書く
		lineColor = c;
	}
	
	public void setfillColor(Color c) {
		//オブジェクトの内部の色を設定する処理を書く
		fillColor = c;
	}
	
	public void setLineWidth(int Lwid) {
		//オブジェクトの線の太さを設定する処理を書く
		lineWidth = Lwid;
	}
	
	public int getLineWidth() {
		//オブジェクトのX位置を取得する処理を書く
		return lineWidth;
	}
	
	public Color getLineColor() {
		//オブジェクトの線の色を取得する処理を書く
		return lineColor;
	}
	
	public Color getfillColor() {
		//オブジェクトの内部の色を設定する処理を書く
		return fillColor;
	}
	
	public void setDashed(boolean b) {
		//オブジェクトの線が破線かどうかを決定する値を設定する処理を書く
		this.isDashed = b;
	}
	
	public boolean getDashed() {
		//オブジェクトの線が破線かどうかを決定する値を取得する処理を書く
		return isDashed;
	}
	
	public boolean getSelected() {
		return isSelected;
	}
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public boolean intersects(double x, double y, double w, double h) {
		return false;
	}
	
	public boolean contains(int x, int y) {
		//MyDrawingを継承する子クラス内でそれぞれ定義する。
		return false;
	}
	
	public void setRegion() {
		//MyDrawingを継承する子クラス内でそれぞれ定義する。
	}
	
	public boolean hasShadow() {
		return hasShadow;
	}
	
	public void setShadow(boolean b) {
		this.hasShadow = b;
	}
	
	public void setRad(double rad) {
		this.rad = rad;
	}
	
	public double getRad() {
		return rad;
	}
	
	public double getRad_temp() {
		return rad_temp;
	}
	
	public void setRad_temp(double rad_temp) {
		this.rad_temp = rad_temp;
	}
	
	public void rotateRegion() {
		AffineTransform af = new AffineTransform();
		double centerX = (double)x + (double)w / 2.0d;
		double centerY = (double)y + (double)h / 2.0d;
		af.rotate(rad, centerX, centerY);
		
		region = af.createTransformedShape(region);
	}
	
	public float calculateAlpha(int alpha) {
		if (alpha >=100) {
			return 1.0f;
		} else if (alpha <= 0) {
			return 0.0f;
		} else {
		return ((float)alpha)/100f;
		}
	}
	
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
	
	public int getAlpha() {
		return alpha;
	}
	
	public void setShadowAlpha(int shadowAlpha) {
		this.shadowAlpha = shadowAlpha;
	}
	
	public int getShadowAlpha() {
		return shadowAlpha;
	}
	
	public void setAnimationSettings(AnimationSettings animationSettings) {
		this.animationSettings = animationSettings;
	}
	
	public AnimationSettings getAnimationSettings() {
		return animationSettings;
	}
	
	//アニメーション開始前に使う
	public void resetAnimationOrigins() {
		if (animationSettings != null) {
			animationSettings.resetOrigins();
		}
	}
	
	public void doAnimation() {
		if (animationSettings != null) {
			animationSettings.doAnimation();
		}
	}
	
	public void stopAnimation() {
		if (animationSettings != null) {
			animationSettings.stopAnimation();
		}
	}

	public void setClear(boolean isClear) {
		this.isClear = isClear;
	}
}
























