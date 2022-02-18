import java.awt.*;
import java.awt.Color;
import javax.swing.*;

public interface SelectColor {
	public abstract Color getColor();
}

class SelectRedColor implements SelectColor {
	static final Color selectedColor = Color.red;
	
	public SelectRedColor() {}
	
	public String toString() {
		return "Red";
	}
	
	public Color getColor() {
		return selectedColor;
	}
}

class SelectWhiteColor implements SelectColor {
	static final Color selectedColor = Color.white;
	
	public SelectWhiteColor() {}
	
	public String toString() {
		return "White";
	}
	
	public Color getColor() {
		return selectedColor;
	}
}

class SelectBlackColor implements SelectColor {
	static final Color selectedColor = Color.black;
	
	public SelectBlackColor() {}
	
	public String toString() {
		return "Black";
	}
	
	public Color getColor() {
		return selectedColor;
	}
}

class SelectBlueColor implements SelectColor {
	static final Color selectedColor = Color.blue;
	
	public SelectBlueColor() {}
	
	public String toString() {
		return "Blue";
	}
	
	public Color getColor() {
		return selectedColor;
	}
}

class SelectGreenColor implements SelectColor {
	static final Color selectedColor = Color.green;
	
	public SelectGreenColor() {}
	
	public String toString() {
		return "Green";
	}
	
	public Color getColor() {
		return selectedColor;
	}
}

class SelectOtherColor implements SelectColor {
	Color selectedColor = Color.white;
	Component parent;
	
	public SelectOtherColor(Component parent) {
		this.parent = parent;
	}
	
	public String toString() {
		return "OtherColors..";
	}
	
	public Color getColor() {
		selectedColor = JColorChooser.showDialog(parent, "色の選択", Color.white);
		if (selectedColor == null) {
			selectedColor = Color.white;
		}
		/*
	    colorChooser.getSelectionModel().addChangeListener(this);
	    */
		return selectedColor;
	}
}