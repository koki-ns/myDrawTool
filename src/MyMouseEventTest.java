import javax.swing.*;
import java.awt.Event;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseEventTest extends JFrame {
	public MyMouseEventTest() {
		super("Mouse Event Test");
		addMouseListener(new MyMouseLisner());
		setSize(100, 100);
	}
	
	public static void main(String[] args) {
		MyMouseEventTest myapp = new MyMouseEventTest();
		myapp.setVisible(true);
	}
	
	class MyMouseLisner extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			System.out.println("Clicked!");
		}
		
		public void mouseEntered(MouseEvent e) {
			System.out.println("Entered!");
		}
	}
}