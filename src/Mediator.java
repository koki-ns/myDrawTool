import java.awt.*;
import java.util.*;

public class Mediator  {
	//Mediatorは図形に対する操作を扱う
	Vector<MyDrawing> drawings;
	MyCanvas canvas;
	Vector<MyDrawing> selectedDrawings;
	Vector<MyDrawing> buffer; //Cut,Copyバッファ
	
	Timer animationTimer;
	
	/*
	MyRangeRectangle rangeRect; //選択範囲の矩形
	*/
	
	public Mediator(MyCanvas canvas) {
		this.canvas = canvas;
		drawings = new Vector<MyDrawing>();
		selectedDrawings = new Vector<MyDrawing>();
		buffer = new Vector<MyDrawing>();
	}
	
	public Enumeration<MyDrawing> drawingElements() {
		return drawings.elements();
	}
	
	public void addDrawing(MyDrawing d) {
		drawings.add(d);
	}
	
	public void removeDrawing(MyDrawing d) {//影があったら諸共消す
		drawings.remove(d);
	}
	
	public Vector<MyDrawing> getDrawings() {
		return drawings;
	}
	
	public Vector<MyDrawing> getSelectedDrawings() {
		return selectedDrawings;
	}
	
	public void setDrawings(Vector<MyDrawing> drawings) {
		this.drawings = drawings;
		repaint();
	}
	
	public void move(int dx, int dy) {
		if  (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				d.move(dx, dy);
			}
		}
	}
	
	public void setRegion() {
		if  (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				d.setRegion();
				d.rotateRegion();
			}
		}
	}
	
	public void repaint() {
		canvas.repaint();
	}
	
	/*
	public void setSelected(int x, int y) {
		releaseSelected();
		Enumeration<MyDrawing> e = drawingElements();
		while (e.hasMoreElements()) {
			MyDrawing d = e.nextElement();
			if (d.contains(x, y) && (d.getParentDrawing()==null)) { //影を選択できないように
				selectedDrawing = d;
			}
		}
		if (selectedDrawing!=null) {
			selectedDrawing.setSelected(true);
		}
	}
	*/
	public void setSelected(MyDrawing d) {
		selectedDrawings.add(d);
		d.setSelected(true);
	}
	
	public boolean selectedExists() {
		if (selectedDrawings == null || selectedDrawings.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public int howManySelected() {
		if (selectedDrawings != null && selectedDrawings.size() >= 1) {
			return selectedDrawings.size();
		} else {
			return 0;
		}
	}
	
	
	//位置(x, y)を含む一番手前の図形を返すメソッド
	public MyDrawing contains(int x, int y) {
		MyDrawing temp = null; //クリックした位置にある図形の仮保存用ローカル変数
		
		Enumeration<MyDrawing> e = drawingElements();
		while (e.hasMoreElements()) {
			MyDrawing d = e.nextElement();
			if (d.contains(x, y)) { //影を選択できないように
				temp = d;
			}
		}
		
		return temp;
	}
	
	//SelectModeのMouseDownから呼び出されるメソッド
	public MyDrawing selectMouseDown(int x, int y) {
		MyDrawing temp = null; //クリックした位置にある図形の仮保存用ローカル変数
		
		temp = contains(x, y);
		
		//クリックした位置に図形があったかで分岐
		if (temp != null) {
			//既に選択されている図形があるかで分岐
			if (selectedExists()) {
				//既に選択されている図形の中にtempが入っているかチェック
				//入ってないなら選択をリセットし、tempを選択状態に
				if (selectedDrawings.contains(temp) == false) {
					releaseAllSelected();
					setSelected(temp);
				}
			}
			else {
				//既に選択されている図形がなければ、tempを選択状態に
				setSelected(temp);
			}
			//メソッドのセットのクラスを返す
			return temp;
		}
		else {
			//メソッドのセットのクラスを返す
			return null;
		}
	}
	
	//SelectMethodsのRangeMethodsクラスから呼び出される
	public void rangeSelect(double x, double y, double w, double h) {
		for (MyDrawing d : drawings) {
			if (d.intersects(x, y, w, h)) {
				setSelected(d);
			}
		}
	}
	
	/*
	public void rangeSelect() {
		for (MyDrawing d : drawings) {
			if (rangeRect.contains(d) && d.getParentDrawing() == null) {
				selectedDrawings.add(d);
			}
		}
		rangeRect.setSelected(true);
		//範囲内に図形が無ければrangeRectを消す
		//rangeRectの大きさが0だと選択状態に引っかからない？とりあえずselectedDrawingsが0の時も考慮することで対処
		if (selectedDrawings.size() == 1 || selectedDrawings.size() == 0) {
			removeRangeRect();
		}
	}
	*/
	
	public void releaseSelected(MyDrawing d) {
		if (selectedDrawings.contains(d)) {
			d.setSelected(false);
			selectedDrawings.remove(d);
		}
	}
	
	public void releaseAllSelected() {//全ての選択を解除
		if (selectedExists()) {
			selectedDrawings.removeAllElements();
			Enumeration<MyDrawing> e = drawingElements();
			while (e.hasMoreElements()) {
				MyDrawing d = e.nextElement();
				d.setSelected(false);
			}
		}
	}
	
	/*
	public void releaseAllSelected() {//全ての選択を解除
		if (selectedExists()) {
			selectedDrawings.removeAllElements();
			Enumeration<MyDrawing> e = drawingElements();
			while (e.hasMoreElements()) {
				MyDrawing d = e.nextElement();
				d.setSelected(false);
			}
			if (rangeRect!=null) {
				removeRangeRect();
			}
		}
	}
	
	public void setfillColor(Color color) {
		if (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				if (d != rangeRect) d.setfillColor(color);
			}
			canvas.repaint();
		}
		MyDrawing.setSelectedfillColor(color);
	}
	
	public void setLineColor(Color color) {
		if (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				if (d != rangeRect) d.setLineColor(color);
			}
			canvas.repaint();
		}
		MyDrawing.setSelectedLineColor(color);
	}
	
	public void setLineWidth(int i) {
		if (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				if (d != rangeRect) d.setLineWidth(i);
			}
			canvas.repaint();
		}
		MyDrawing.setSelectedLineWidth(i);
	}
	*/

	public void setfillColor(Color color) {
		if (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				d.setfillColor(color);
			}
			canvas.repaint();
		}
		MyDrawing.setSelectedfillColor(color);
	}
	
	public void setLineColor(Color color) {
		if (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				d.setLineColor(color);
			}
			canvas.repaint();
		}
		MyDrawing.setSelectedLineColor(color);
	}
	
	public void setLineWidth(int i) {
		if (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				d.setLineWidth(i);
			}
			canvas.repaint();
		}
		MyDrawing.setSelectedLineWidth(i);
	}
	 
	public void setRad(double rad) {
		if (selectedExists() && Double.isNaN(rad) == false) {
			for (MyDrawing d : selectedDrawings) {
				d.setRad(d.getRad_temp() + rad);
				//影も回転させる
			}
			canvas.repaint();
		}
	}
	
	public void setRad_end() {
		if (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				double rad = d.getRad();
				if(rad >= Math.PI * 2d) {
					while(rad >= (Math.PI * 2d)) {
						rad = rad - (Math.PI * 2d);
					}
				} else if(rad < -(Math.PI * 2d)) {
					while(rad < -(Math.PI * 2d)) {
						rad = rad + (Math.PI * 2d);
					}
				}
				d.setRad(rad);
				d.setRad_temp(rad);
				d.setRegion();
			}
			canvas.repaint();
		}
	}
	
	public void setAlpha(int alpha) {
		if (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				d.setAlpha(alpha);
			}
			canvas.repaint();
		}
		MyDrawing.setSelectedAlpha(alpha);
	}
	
	public void setShadowAlpha(int alpha) {
		if (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				if (d.hasShadow()) {
					d.setShadowAlpha(alpha);
				}
			}
			canvas.repaint();
		}
		MyDrawing.setSelectedShadowAlpha(alpha);
	}
	
	/*
	public void generateShadow(MyDrawing d) {//引数の図形に影を生み出す
		if (d != null && d.getShadowDrawing() == null && d != rangeRect) {
			MyDrawing Shadow = d.clone();
			Shadow.setLocation(Shadow.getX()+10, Shadow.getY()+10);
			Shadow.setParentDrawing(d);
			Shadow.setDashed(false);
			Shadow.setfillColor(Color.black);
			Shadow.setLineColor(Color.black);
			Shadow.setSelected(false);
			
			drawings.add(drawings.indexOf(d), Shadow);
			d.setShadowDrawing(Shadow);
		}
	}
	*/
	
	//このコードは影が選択できないという前提で書かれている
	public void generateShadow(MyDrawing d) {//引数の図形に影を生み出す
		if (d != null && d.hasShadow() == false) {
			d.setShadow(true);
			d.setShadowAlpha(MyDrawing.getSelectedShadowAlpha());
		}
	}
	
	public void clearShadow(MyDrawing d) {//引数の図形の影を削除
		if (d != null && d.hasShadow()) {
			d.setShadow(false);
		}
	}
	
	public void generateShadowForSelected() {
		for(MyDrawing d : selectedDrawings) {
			generateShadow(d);
		}
	}
	
	public void clearShadowForSelected() {
		for(MyDrawing d : selectedDrawings) {
			clearShadow(d);
		}
	}
	
/*
	public boolean hasShadow() {
		if (selectedDrawing != null) {
			if (selectedDrawing.getShadowDrawing() != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
*/
	public boolean bufferExists() {
		if (buffer == null || buffer.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public void clearBuffer() {
		if (bufferExists()) {
			buffer.removeAllElements();
		}
	}
	
	public void copy() {
		clearBuffer();
		if (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				buffer.add(d.clone());
			}
		}
	}
	
	public void cut() {
		clearBuffer();
		if (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				buffer.add(d.clone());
				removeDrawing(d);
			}
			releaseAllSelected();
		}
		repaint();
	}
	
	public void delete() {
		if (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				removeDrawing(d);
			}
			releaseAllSelected();
		}
		repaint();
	}
	
	public void paste() {
		if (bufferExists()) {
			//選択を生成したものへ移動するための前処理
			releaseAllSelected();
			//
			
			for (MyDrawing d : buffer) {
				//バッファ(元々クローンされてる)のクローンを作って配置
				d.setLocation(d.getX() + 20, d.getY() + 20);
				MyDrawing clone = (MyDrawing)d.clone();
				if (clone.getAnimationSettings() != null) {
					AnimationSettings as = (clone.getAnimationSettings()).clone();
					as.setDrawing(clone);
					clone.setAnimationSettings(as);
				}
				
				addDrawing(clone);
				
				//ペースト元が影を持っていた場合、参照を削除して新しい影を生成
				
				//生成したものを選択
				setSelected(clone);
			}
			repaint();
		}
	}
	
	public void scale(int scale) {
		double magni = scale/100d;
		int scaledW, scaledH;
		if (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				scaledW = (int)((double)d.getW() * magni);
				scaledH = (int)((double)d.getH() * magni);
				d.setCoordinate((d.getX() - ((scaledW - d.getW()) / 2)), (d.getY() - ((scaledH - d.getH()) / 2)));
				d.setScale(scaledW, scaledH);
				d.setRegion();
			}
		}
		repaint();
	}
	
	public void doAnimation() {
		for(MyDrawing d : drawings) {
			d.resetAnimationOrigins();
		}
		allClear(false);
		animationTimer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				for(MyDrawing d : drawings) {
					d.doAnimation();
				}
				canvas.repaint();
			}
		};
		animationTimer.schedule(task, 100L, 40L);
	}
	
	public void stopAnimation() {
		animationTimer.cancel();
		for(MyDrawing d : drawings) {
			d.stopAnimation();
		}
		repaint();
	}
	
	public void setMirrored(boolean b) {
		if (selectedExists()) {
			for (MyDrawing d : selectedDrawings) {
				if (d instanceof MyImage) {
					((MyImage) d).setMirrored(b);
				}
			}
		}
		repaint();
	}

	public void setClear(boolean b) {
		if (selectedExists()) {
			for (MyDrawing d: selectedDrawings) {
				d.setClear(b);
			}
		}
		repaint();
	}

	public void allClear(boolean b) {
		for (MyDrawing d: drawings) {
			d.setClear(b);
		}
	}
	
	/*
	
	public void paste() {
		if (bufferExists()) {
			//選択を生成したものへ移動するための前処理
			releaseAllSelected();
			//
			
			for (MyDrawing d : buffer) {
				//バッファ(元々クローンされてる)のクローンを作って配置
				d.setLocation(d.getX() + 20, d.getY() + 20);
				MyDrawing clone = (MyDrawing)d.clone();
				
				//RangeRectの存在を判別し、あるならRangeRectを切り替える
				if (clone instanceof MyRangeRectangle) {
					setRangeRect((MyRangeRectangle)clone);
				} else {
					addDrawing(clone);
				}
				
				if (clone.getShadowDrawing() != null) {//ペースト元が影を持っていた場合、参照を削除して新しい影を生成
					clone.setShadowDrawing(null);
					generateShadow(clone);
				}
				
				//生成したものを選択
				selectedDrawings.add(clone);
			}
			repaint();
		}
	}
	
	public void removeRangeRect() {
		removeDrawing(rangeRect);
		rangeRect = null;
	}
	
	//RangeRectがあれば除去して追加
	public void setRangeRect(MyRangeRectangle rangeRect) {
		if (this.rangeRect != null) {
			removeDrawing(this.rangeRect);
		}
		addDrawing(rangeRect);
		this.rangeRect = rangeRect;
	}
	*/
	public void toFront() {
		if (selectedExists()) {
			for (MyDrawing d: selectedDrawings) {
				removeDrawing(d);
				addDrawing(d);
			}
			repaint();
		}
	}
}