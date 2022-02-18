import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.nio.file.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageOpenItem extends JMenuItem {
	JFrame parentFrame;
	Mediator mediator;
	
	public ImageOpenItem( JFrame parentFrame, Mediator mediator) {
		super("画像を開く");
		
		this.mediator = mediator;
		this.parentFrame = parentFrame;
		
		addActionListener(new ImageOpenListener());
	}
	
	
	class ImageOpenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				Path p1 = Paths.get("");
				Path p2 = p1.toAbsolutePath();
				JFileChooser fc = new JFileChooser(p2.toString());
				int returnVal = fc.showOpenDialog(parentFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					BufferedImage img = ImageIO.read(file);
					mediator.addDrawing(new MyImage(img,file.getPath()));
					mediator.repaint();
				}
			}
			catch(NullPointerException e1) {
				JOptionPane.showMessageDialog(parentFrame, "Error:File Not Found");
			}
			catch(IOException e2) {
				JOptionPane.showMessageDialog(parentFrame, "Error:IOException");
			}
		}
	}
}