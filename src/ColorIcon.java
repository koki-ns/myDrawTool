import java.awt.*;
import javax.swing.*;

public class ColorIcon implements Icon {
	int w;
	int h;
	Color color;
	
	public ColorIcon(Color c) {
		w = 15;
		h = 15;
		color = c;
	}

	@Override
	public int getIconWidth() {
		return w;
	}

	@Override
	public int getIconHeight() {
		return h;
	}
	
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(color);
		g.fillRect(x, y, w, h);
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}