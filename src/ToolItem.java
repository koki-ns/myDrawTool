import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ToolItem extends JMenuItem {
	OperateModeManager operateModeManager;
	
	public ToolItem(String str, OperateModeManager operateModeManager) {
		super(str);
		this.operateModeManager = operateModeManager;
	}
}

class ScaleItem extends ToolItem {
	public ScaleItem(OperateModeManager operateModeManager) {
		super("拡大/縮小", operateModeManager);
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String str = JOptionPane.showInputDialog(null, "倍率を入力してください(1~1000)", "100");
					int scale = (Integer.valueOf(str)).intValue();
					if (scale > 1000 || scale < 1) {
						throw new NumberFormatException();
					}
					operateModeManager.getMediator().scale(scale);
				} catch(NumberFormatException ex1) {
					JOptionPane.showMessageDialog(null, "入力された値が不正です。");
				}
			}
		});
	}
}

class BackGroundColorItem extends ToolItem {
	Color selectedColor;
	
	public BackGroundColorItem(OperateModeManager operateModeManager) {
		super("背景色の設定", operateModeManager);
		selectedColor = Color.white;
		
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color c;
				c = JColorChooser.showDialog(null, "色の選択", selectedColor);
				if (c != null) {
					selectedColor = c;
				}
				operateModeManager.getCanvas().setBackground(selectedColor);
			}
		});
	}
}

class ToumeiItem extends ToolItem {
	public ToumeiItem(OperateModeManager operateModeManager) {
		super("透明化", operateModeManager);
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				operateModeManager.getMediator().setClear(true);
			}
		});
	}
}

class ToFrontItem extends ToolItem {
	public ToFrontItem(OperateModeManager operateModeManager) {
		super("アイテムを前面へ", operateModeManager);
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				operateModeManager.getMediator().toFront();
			}
		});
	}
}

