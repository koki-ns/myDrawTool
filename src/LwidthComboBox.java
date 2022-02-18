import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;

public class LwidthComboBox extends JComboBox<Integer> {
	Mediator mediator;
	
	public LwidthComboBox(Vector<Integer> items, Mediator mediator) {
		super(items);
		
		this.mediator = mediator;
		
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		mediator.setLineWidth(((Integer)getSelectedItem()).intValue());
	}
}