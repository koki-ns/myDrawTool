import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class AlphaPanel extends JPanel {
	Mediator mediator;
	JSlider slider;
	JSpinner spinner;
	int v;
	
	public AlphaPanel(Mediator mediator) {
		super();
		this.mediator = mediator;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		JLabel label = new JLabel("不透明度:          　　　　　　　     ");
		label.setAlignmentX(CENTER_ALIGNMENT);
		add(label);
		
		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		slider = new JSlider(0, 100, 100);
		slider.addChangeListener(new SliderListener());
		slider.setPreferredSize(new Dimension(150, 20));
		spinner = new JSpinner(new SpinnerNumberModel(100, 0, 100, 1));
		spinner.addChangeListener(new SpinnerListener());
		jp.add(slider);
		jp.add(spinner);
		jp.add(Box.createHorizontalGlue());
		jp.add(Box.createRigidArea(new Dimension(8,20)));
		
		add(jp);
	}
	
	public void setAlphaEX(int alpha) {
		slider.setValue(alpha);
		spinner.setValue(alpha);
		MyDrawing.setSelectedAlpha(alpha);
	}
	
	class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			v = ((JSlider)e.getSource()).getValue();
			mediator.setAlpha(v);
			spinner.setValue(v);
			
		}
	}
	
	class SpinnerListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			v = (int)((JSpinner)e.getSource()).getValue();
			mediator.setAlpha(v);
			slider.setValue(v);
		}
	}
	
	public JSlider getSlider() {
		return slider;
	}
	
	public JSpinner getSpinner() {
		return spinner;
	}
}