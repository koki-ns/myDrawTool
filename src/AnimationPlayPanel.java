import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AnimationPlayPanel extends JPanel {
	AnimationPlayButton apb;
	AnimationStopButton asb;
	
	public AnimationPlayPanel(MyApplication ma, Mediator mediator) {
		setLayout(new GridLayout(1, 2));
		apb = new AnimationPlayButton(ma, mediator);
		asb = new AnimationStopButton(ma, mediator);
		apb.setAsb(asb);
		asb.setApb(apb);
		add(apb);
		add(asb);
		asb.setEnabled(false);
	}
	
	public AnimationPlayButton getApb() {
		return apb;
	}
	
	public AnimationStopButton getAsb() {
		return asb;
	}
}

class AnimationPlayButton extends JButton {
	MyApplication ma;
	Mediator mediator;
	AnimationStopButton asb;
	
	public AnimationPlayButton(MyApplication ma, Mediator mediator) {
		super(new ImageIcon("play.png"));
		this.ma = ma;
		this.mediator = mediator;
		addActionListener(new PlayListener());
	}
	
	class PlayListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			mediator.releaseAllSelected();
			ma.removeListeners();
			ma.setPartsEnabled(false, null);
			asb.setEnabled(true);
			mediator.doAnimation();
		}
	}
	
	public void setAsb(AnimationStopButton asb) {
		this.asb = asb;
	}
}

class AnimationStopButton extends JButton {
	MyApplication ma;
	Mediator mediator;
	AnimationPlayButton apb;
	
	public AnimationStopButton(MyApplication ma, Mediator mediator) {
		super(new ImageIcon("stop.png"));
		this.ma = ma;
		this.mediator = mediator;
		addActionListener(new StopListener());
	}
	
	class StopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ma.addListeners();
			ma.setPartsEnabled(true, null);
			mediator.stopAnimation();
			AnimationStopButton.this.setEnabled(false);
		}
	}
	
	public void setApb(AnimationPlayButton apb) {
		this.apb = apb;
	}
}