import java.awt.event.*;
import javax.swing.*;

public class MirrorCheckBox extends JCheckBox {
	Mediator mediator;
	
	public MirrorCheckBox(Mediator mediator) {
		super("画像の反転");
		
		addActionListener(new MirrorListener());
		
		this.mediator = mediator;
	}
	
	class MirrorListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (((JCheckBox)(e.getSource())).isSelected() == true) {
				mediator.setMirrored(true);
			} else {
				mediator.setMirrored(false);
			}
		}
	}
	
	public void setSelectedEX(boolean b) {
		setSelected(b);
	}
}
