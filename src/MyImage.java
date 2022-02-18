import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


public class MyImage extends MyDrawing implements Serializable {
	transient protected BufferedImage img;
	String str;
	
	boolean isMirrored;
	
	public MyImage(BufferedImage img, String str) {
		this.img = img;
		this.str = str;
		
		x = 0;
		y = 0;
		w = img.getWidth();
		h = img.getHeight();
		lineColor = MyDrawing.getSelectedLineColor();
		fillColor = MyDrawing.getSelectedfillColor();
		lineWidth = MyDrawing.getSelectedLineWidth();
		isDashed = false;
		isClear = false;
		hasShadow = false;
		rad = 0.0;
		rad_temp = 0.0;
		alpha = MyDrawing.getSelectedAlpha();
		animationSettings = null;
		
		isMirrored = false;
		
		
		setRegion();
	}
	
	public void draw(Graphics g) {
		
		int x = getX();
		int y = getY();
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
		if (getDashed()) {
			//破線の時の処理
			g2.setStroke(new MyDashStroke(getLineWidth()));
		} else {
			g2.setStroke(new BasicStroke(getLineWidth()));
		}
		
		
		g2.rotate(this.rad, x+w/2, y+h/2);
		
		if (isClear) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f));
		} else {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, calculateAlpha(alpha)));
		}
		if (isMirrored) {
			g2.drawImage(img, x+w, y, -w, h, null);
		} else {
			g2.drawImage(img, x, y, w, h, null);
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		super.draw(g);
		
		g2.rotate(-(this.rad),x+w/2, y+h/2);
	}
	
	public MyImage clone() {
		MyImage myImage;
		myImage = (MyImage)super.clone();
		return myImage;
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
		try {
			out.writeBytes(str);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Error:IOException");
		}
	}
	
	private void readObject(ObjectInputStream in) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			str = reader.readLine();
			BufferedImage img = ImageIO.read(new File(str));
			this.img = img;
			setSelected(false);
			setRegion();
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error:IOException");
		}
	}
	
	public void setMirrored(boolean isMirrored) {
		this.isMirrored = isMirrored;
	}
	
	public boolean isMirrored() {
		return isMirrored;
	}
	
}