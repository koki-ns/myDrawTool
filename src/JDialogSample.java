import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class JDialogSample extends JFrame {
   JDialogSample() {
	  
      getContentPane().setLayout(new FlowLayout());
      getContentPane().add(new JButton("hoge"));

      DialogWindow dlg = new DialogWindow(this);
      setVisible(true);

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("ダイアログウィンドウ呼び出し元");
      setSize(530, 200);
      setVisible(true);
   }
   public static void main(String[] args) {
      new JDialogSample();
   }
}

class DialogWindow extends JDialog implements ActionListener {
   DialogWindow(JFrame owner) {
      super(owner);
      getContentPane().setLayout(new FlowLayout());

      JButton btn = new JButton("ボタン表示");
      btn.addActionListener(this);
      getContentPane().add(btn);

      setTitle("ダイアログウィンドウ");
      setSize(200, 150);
      setVisible(true);
   }
   public void actionPerformed(ActionEvent e) {
      setVisible(true);
   }
}