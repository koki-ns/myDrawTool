import java.io.Serializable;

public class AnimationSettings implements Cloneable,Serializable {
	MyDrawing drawing;

	boolean isClear;
	
	int originX, originY;
	int originW, originH;
	double originRad;
	int originAlpha;
	int originShadowAlpha;
	int count; //ループに使う変数
	int offsetCount;
	
	int addX, addY;
	double addRad;
	int addAlpha;
	int addShadowAlpha;
	int scale;
	int addCount; //ループの上限、フレーム数を表す
	int offset;
	
	//アニメーションの際の増分
	double dx, dy;
	double dw, dh;
	double dRad;
	double dAlpha;
	double dShadowAlpha;
	double expansion;
	
	//計算に使う変数
	double tempX,tempY;
	double tempW,tempH;
	double tempRad;
	double tempAlpha;
	double tempShadowAlpha;
	
	public AnimationSettings(MyDrawing drawing) {
		this.drawing = drawing;
		setOrigin(drawing.getX(), drawing.getY(), drawing.getW(), drawing.getH(), drawing.getRad(), drawing.getAlpha(), drawing.getShadowAlpha());
		setGoal(0, 0, 100, 0d, 0, 0, 25, 0);
	}
	
	public AnimationSettings clone() {
		AnimationSettings as;
		try {
			as = (AnimationSettings)super.clone();
		} catch (CloneNotSupportedException ce) {
			// TODO: handle exception
			throw new RuntimeException();
		}
		return as;
	}
	
	public void setOrigin(int x, int y, int w, int h, double rad, int Alpha, int shadowAlpha) {
		this.originX = x;
		this.originY = y;
		this.originW = w;
		this.originH = h;
		this.originRad = rad;
		this.originAlpha = Alpha;
		this.originShadowAlpha = shadowAlpha;
		setTemptoOrigin();
	}
	
	public void setGoal(int addX, int addY, int scale, double addRad, int addAlpha, int addShadowAlpha, int goalCount, int offset) {
		this.addX = addX;
		this.addY = addY;
		this.addRad = addRad;
		this.addAlpha = addAlpha;
		this.addShadowAlpha = addShadowAlpha;
		this.scale = scale;
		this.addCount = goalCount;
		this.offset = offset;
		
		if (goalCount == 0) {
			this.dx = 0d;
			this.dy = 0d;
			this.dw = 0d;
			this.dh = 0d;
			this.dRad = 0d;
			this.dAlpha = 0d;
			this.dShadowAlpha = 0d;
		} else {
			//ここのゼロ除算を考えて対策が必要
			this.dx = (double)addX / (double)goalCount;
			this.dy = (double)addY / (double)goalCount;
			this.dw = (((double)originW * ((double)scale / 100d)) - (double)originW) / (double)goalCount;
			this.dh = (((double)originH * ((double)scale / 100d)) - (double)originH) / (double)goalCount;
			this.dRad = addRad / (double)goalCount;
			this.dAlpha = (double)addAlpha / (double)goalCount;
			this.dShadowAlpha = (double)addShadowAlpha / (double)goalCount;
		}
	}
	
	public void setTemptoOrigin() {
		this.tempX = (double)this.originX;
		this.tempY = (double)this.originY;
		this.tempW = (double)this.originW;
		this.tempH = (double)this.originH;
		this.tempRad = this.originRad;
		this.tempAlpha = (double)this.originAlpha;
		this.tempShadowAlpha = (double)this.originShadowAlpha;
	}
	
	public void doAnimation() {
		if (count < addCount) {
			tempX = tempX + dx - dw/2;
			tempY = tempY + dy - dh/2;
			tempW = tempW + dw;
			tempH = tempH + dh;
			tempRad = tempRad + dRad;
			tempAlpha = tempAlpha + dAlpha;
			tempShadowAlpha = tempShadowAlpha + dShadowAlpha;
			count = count + 1;
		} else {
			setTemptoOrigin();
			count = 0;
		}
		
		drawing.setCoordinate((int)tempX, (int)tempY);
		drawing.setScale((int)tempW, (int)tempH);
		drawing.setRad(tempRad);
		drawing.setAlpha((int)tempAlpha);
		drawing.setShadowAlpha((int)tempShadowAlpha);
	}
	
	public void stopAnimation() {
		drawing.setCoordinate((int)originX, (int)originY);
		drawing.setScale(originW, originH);
		drawing.setRad(originRad);
		drawing.setAlpha((int)originAlpha);
		drawing.setShadowAlpha((int)originShadowAlpha);
		count = 0;
		offsetCount = 0;
		
		setTemptoOrigin();
	}
	
	public void setDrawing(MyDrawing drawing) {
		this.drawing = drawing;
	}
	
	public int getOriginX() {
		return originX;
	}
	
	public int getOriginY() {
		return originY;
	}
	
	public int getOriginW() {
		return originW;
	}
	
	public int getOriginH() {
		return originH;
	}
	
	public double getOriginRad() {
		return originRad;
	}
	
	public double getOriginRadtoDegree() {
		return (Math.floor((Math.toDegrees(originRad)) * 10d)) / 10d;
	}
	
	public int getOriginAlpha() {
		return originAlpha;
	}
	
	public int getOriginShadowAlpha() {
		return originShadowAlpha;
	}
	
	public int getAddX() {
		return addX;
	}
	
	public int getAddY() {
		return addY;
	}
	
	public double getAddRad() {
		return addRad;
	}
	
	public double getAddRadtoDegree() {
		return (Math.floor((Math.toDegrees(addRad)) * 10d)) / 10d;
	}
	
	public int getScale() {
		return scale;
	}
	
	public int getAddAlpha() {
		return addAlpha;
	}
	
	public int getAddShadowAlpha() {
		return addShadowAlpha;
	}
	
	public int getAddCount() {
		return addCount;
	}
	
	public int getOffset() {
		return offset;
	}
	
	//設定を適用する際に使う
	public void setOrigintoDrawing() {
		stopAnimation();
		drawing.setRegion();
		drawing.rotateRegion();
	}
	
	//アニメーション開始前に使う
	public void resetOrigins() {
		this.originX = drawing.getX();
		this.originY = drawing.getY();
		this.originW = drawing.getW();
		this.originH = drawing.getH();
		this.originRad = drawing.getRad();
		this.originAlpha = drawing.getAlpha();
		this.originShadowAlpha = drawing.getShadowAlpha();
		setTemptoOrigin();
		adoptOffset();

	}

	public void setClear(boolean isClear) {
		this.isClear = isClear;
	}

	public void adoptOffset() {
		tempX = tempX + (dx - dw/2) * offset;
		tempY = tempY + (dy - dh/2) * offset;
		tempW = tempW + dw * offset;
		tempH = tempH + dh * offset;
		tempRad = tempRad + dRad * offset;
		tempAlpha = tempAlpha + dAlpha * offset;
		tempShadowAlpha = tempShadowAlpha + dShadowAlpha * offset;
		count = count + offset;
	}
}