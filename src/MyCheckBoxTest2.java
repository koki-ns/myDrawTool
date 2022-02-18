import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyCheckBoxTest2 extends JFrame {
	public MyCheckBoxTest2() {
		super("MyCheckBoxTest");
		
		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout());
		getContentPane().add(jp);
		
		JCheckBox dashCheck = new JCheckBox("dash line");
		dashCheck.addItemListener(new DashCheckListener());
		jp.add(dashCheck);
		
		JCheckBox boldCheck = new JCheckBox("bold line");
		boldCheck.addItemListener(new BoldCheckListener());
		jp.add(boldCheck);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(jp,BorderLayout.NORTH);
		
		setSize(300, 300);
	}
	
	public static void main(String[] args) {
		MyCheckBoxTest2 myapp = new MyCheckBoxTest2();
		myapp.setVisible(true);
	}
}

class DashCheckListener implements ItemListener {
	public void itemStateChanged(ItemEvent e) {
		int state = e.getStateChange();
		if (state == ItemEvent.SELECTED) {
			System.out.println("dash line was Checked!");
		} else {
			System.out.println("dash line was unchecked!");
		}
	}
}

class BoldCheckListener implements ItemListener {
	public void itemStateChanged(ItemEvent e) {
		int state = e.getStateChange();
		if (state == ItemEvent.SELECTED) {
			System.out.println("bold line was Checked!");
		} else {
			System.out.println("bold line was unchecked!");
		}
	}
}