import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.Vector;
import javax.swing.*;

public class FileInputItem extends JMenuItem {
	JFrame parentFrame;
	Mediator mediator;
	
	public FileInputItem(JFrame parentFrame, Mediator mediator) {
		super("ファイルの読み込み");
		
		this.mediator = mediator;
		this.parentFrame = parentFrame;
		
		addActionListener(new FileInputActionListener());
	}
	
	class FileInputActionListener implements ActionListener {
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent e) {
			try {
				Path p1 = Paths.get("");
				Path p2 = p1.toAbsolutePath();
				JFileChooser fc = new JFileChooser(p2.toString());
				int returnVal = fc.showOpenDialog(parentFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					FileInputStream fin = new FileInputStream(file);
					ObjectInputStream in = new ObjectInputStream(fin);
					
					mediator.setDrawings((Vector<MyDrawing>)in.readObject());
					fin.close();
				}
				
				
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(parentFrame, "Error:File Load");
			} catch (IOException e2) {
				JOptionPane.showMessageDialog(parentFrame, "Error:File Load");
			} catch (ClassNotFoundException e3) {
				JOptionPane.showMessageDialog(parentFrame, "Error:ClassNotFoundException");
			} catch (ClassCastException e4) {
				JOptionPane.showMessageDialog(parentFrame, "Error:ClassCastException");
			}
		}
	}
}