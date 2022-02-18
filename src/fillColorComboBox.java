import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;

public class fillColorComboBox extends JComboBox<SelectColor> {
	Mediator mediator;
	ColorIcon icon;
	JPanel parentPanel;
	
	public fillColorComboBox(Vector<SelectColor> items, Mediator mediator, ColorIcon icon, JPanel parentPanel) {
		super(items);
		
		this.mediator = mediator;
		this.icon = icon;
		this.parentPanel = parentPanel;
		
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		Color c = ((SelectColor)getSelectedItem()).getColor();
		mediator.setfillColor(c);
		icon.setColor(c);
		parentPanel.repaint();
	}
}