import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;

public class LineColorComboBox extends JComboBox<SelectColor> {
	Mediator mediator;
	ColorIcon icon;
	JPanel parentPanel;
	
	public LineColorComboBox(Vector<SelectColor> items, Mediator mediator, ColorIcon icon, JPanel parentPanel) {
		super(items);
		
		this.mediator = mediator;
		this.icon = icon;
		this.parentPanel = parentPanel;
		
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		Color c = ((SelectColor)getSelectedItem()).getColor();
		mediator.setLineColor(c);
		icon.setColor(c);
		parentPanel.repaint();
	}
}