import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyButtonTest2 extends JFrame {
	public MyButtonTest2() {
		super("MyButtonTest");
		
		// FlowLayout を使って部品を左から配置するパネルを作成
		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout());
		getContentPane().add(jp);
		
		Button rectButton = new Button("Rectangle");
		rectButton.addActionListener(new RectButtonListener());
		jp.add(rectButton);
		
		Button circleButton = new Button("Circle");
		circleButton.addActionListener(new CircleButtonListener());
		jp.add(circleButton);
		
		Button triangleButton = new Button("Triangle");
		triangleButton.addActionListener(new TriangleButtonListener());
		jp.add(triangleButton);
		
		setSize(300, 250);
		}
	
	public static void main(String[] args) {
		MyButtonTest2 myapp = new MyButtonTest2();
		myapp.setVisible(true);
	}
}

//Rect ボタンのイベントリスナ
class RectButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "Rect is pressed.");
	}
}

//Circle ボタンのイベントリスナ
class CircleButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "Circle is pressed.");
	}
}

//Triangle ボタンのイベントリスナ
class TriangleButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "Triangle is pressed.");
	}
}



