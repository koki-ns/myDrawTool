import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

import javax.swing.*;

public class FileOutputItem extends JMenuItem {
	JFrame parentFrame;
	Mediator mediator;
	
	public FileOutputItem(JFrame parentFrame, Mediator mediator) {
		super("名前をつけて保存");
		
		this.mediator = mediator;
		this.parentFrame = parentFrame;
		
		addActionListener(new FileOutputActionListener());
	}
	
	class FileOutputActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				Path p1 = Paths.get("");
				Path p2 = p1.toAbsolutePath();
				JFileChooser fc = new JFileChooser(p2.toString());
				int returnVal = fc.showSaveDialog(parentFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					FileOutputStream fout = new FileOutputStream(file);
					ObjectOutputStream out = new ObjectOutputStream(fout);
					
					out.writeObject(mediator.getDrawings());
					out.flush();
					
					fout.close();
				}
				
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(parentFrame, "Error:File Not Found");
			} catch (IOException e2) {
				JOptionPane.showMessageDialog(parentFrame, "Error:File Save");
			}
		}
	}
}