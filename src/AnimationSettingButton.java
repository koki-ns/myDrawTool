import java.awt.*;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.*;

public class AnimationSettingButton extends JButton {
	Mediator mediator;
	MyDrawing d;
	AnimationSettings as;
	
	public AnimationSettingButton(Mediator mediator) {
		super("アニメーション設定");
		this.mediator = mediator;
		addActionListener(new ASBListener());
	}
	
	class ASBListener implements ActionListener {
		AnimationSettingsPanel asp;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int selected = mediator.howManySelected();
				if (selected >= 1) {
					Vector<MyDrawing> selecteDrawings = mediator.getSelectedDrawings();
					d = selecteDrawings.elementAt(0);
					as = d.getAnimationSettings();
					if (as == null) {
						as = new AnimationSettings(d);
					} else {
					as.resetOrigins();	
					}
					asp =  new AnimationSettingsPanel(as, selected);
					
					int option = JOptionPane.showConfirmDialog(null, asp, "アニメーション設定", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (option == JOptionPane.OK_OPTION) {
						if (!asp.animationEnable()) {
							d.setAnimationSettings(null);
							return;
						}
						
						int addX = (Integer.valueOf((asp.getAddXField()).getText())).intValue();
						int addY = (Integer.valueOf((asp.getAddYField()).getText())).intValue();
						double addRad =  Math.toRadians((Double.valueOf((asp.getAddRadField()).getText())).doubleValue());
						int addAlpha = (Integer.valueOf((asp.getAddAlphaField()).getText())).intValue();
						int addShadowAlpha = (Integer.valueOf((asp.getAddShadowAlphaField()).getText())).intValue();
						int scale = (Integer.valueOf((asp.getScaleField()).getText())).intValue();
						
						int count = (Integer.valueOf((asp.getCountField()).getText())).intValue();
						if (count < 0) {
							count = 0;
						}
						int offset = (Integer.valueOf((asp.getOffsetField()).getText())).intValue();
						if (offset < 0) {
							offset = 0;
						}
						
						if (selected == 1) {
							int originX = (Integer.valueOf((asp.getOriginXField()).getText())).intValue();
							int originY = (Integer.valueOf((asp.getOriginYField()).getText())).intValue();
							double originRad = Math.toRadians((Double.valueOf((asp.getOriginRadField()).getText())).doubleValue());
							while(originRad >= (Math.PI * 2d)) {
								originRad = originRad - (Math.PI * 2d);
							}
							int originAlpha = (Integer.valueOf((asp.getOriginAlphaField()).getText())).intValue();
							int originShadowAlpha = (Integer.valueOf((asp.getOriginShadowAlphaField()).getText())).intValue();
							
							as.setOrigin(originX, originY, d.getW(), d.getH(), originRad, originAlpha, originShadowAlpha);
							as.setOrigintoDrawing();
							as.setGoal(addX, addY, scale, addRad, addAlpha, addShadowAlpha, count, offset);
							d.setAnimationSettings(as);
						} else if (selected >= 2) {
							for (MyDrawing drawing : selecteDrawings) {
								AnimationSettings animationSettings = new AnimationSettings(drawing);
								animationSettings.setGoal(addX, addY, scale, addRad, addAlpha, addShadowAlpha, count, offset);
								drawing.setAnimationSettings(animationSettings);
							}
						}
					} else {
						//取り消しまたはウィンドウが閉じられた場合の処理
					}
					mediator.repaint();
				} else {
					JOptionPane.showMessageDialog(null, "アイテムを選択してください");
				}
			} catch(NumberFormatException ex1) {
				JOptionPane.showMessageDialog(null, "入力された値が不正です。");
			}
		}
	}
}

class AnimationSettingsPanel extends JPanel {
	JTextField originXField;
	JTextField originYField;
	JTextField originRadField;
	JTextField originAlphaField;
	JTextField originShadowAlphaField;
	JTextField addXField;
	JTextField addYField;
	JTextField addRadField;
	JTextField addAlphaField;
	JTextField addShadowAlphaField;
	JTextField scaleField;
	
	JTextField countField;
	JTextField offsetField;
	
	JCheckBox animationEnableBox;

	JCheckBox offsetCheckBox;
	
	public AnimationSettingsPanel(AnimationSettings as, int i) {
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		animationEnableBox = new JCheckBox("アニメーションを行う");
		animationEnableBox.setSelected(true);
		
		Component c = Box.createRigidArea(new Dimension(10,15));
		JLabel xLabel = new JLabel("X座標");
		JLabel yLabel = new JLabel("Y座標");
		JLabel radLabel = new JLabel("角度");
		JLabel alphaLabel = new JLabel("不透明度");
		JLabel shadowAlphaLabel = new JLabel("影の不透明度");
		JLabel frame = new JLabel("フレーム数");
		JLabel scale = new JLabel("倍率");
		JLabel fps = new JLabel("/25fps");
		JLabel offset = new JLabel("オフセット");
		
		JLabel origin = new JLabel("初期値");
		originXField = new JTextField(String.valueOf(as.getOriginX()),4);
		originYField = new JTextField(String.valueOf(as.getOriginY()),4);
		originRadField = new JTextField(String.valueOf(as.getOriginRadtoDegree()),4);
		originAlphaField = new JTextField(String.valueOf(as.getOriginAlpha()),4);
		originShadowAlphaField = new JTextField(String.valueOf(as.getOriginShadowAlpha()),4);
		
		countField = new JTextField(String.valueOf(as.getAddCount()),4);
		offsetField = new JTextField(String.valueOf(as.getOffset()),4);
		
		JLabel increment = new JLabel("増加分");
		addXField = new JTextField(String.valueOf(as.getAddX()),4);
		addYField = new JTextField(String.valueOf(as.getAddY()),4);
		addRadField = new JTextField(String.valueOf(as.getAddRadtoDegree()),4);
		addAlphaField = new JTextField(String.valueOf(as.getAddAlpha()),4);
		addShadowAlphaField = new JTextField(String.valueOf(as.getAddShadowAlpha()),4);
		scaleField = new JTextField(String.valueOf(as.getScale()),4);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		layout.setConstraints(animationEnableBox, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 4;
		gbc.gridy = 1;
		layout.setConstraints(c, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		layout.setConstraints(xLabel, gbc);
		gbc.gridy = 4;
		layout.setConstraints(yLabel, gbc);
		gbc.gridy = 5;
		layout.setConstraints(radLabel, gbc);
		gbc.gridy = 6;
		layout.setConstraints(alphaLabel, gbc);
		gbc.gridy = 7;
		layout.setConstraints(shadowAlphaLabel, gbc);
		gbc.gridy = 8;
		layout.setConstraints(scale, gbc);
		gbc.gridy = 9;
		layout.setConstraints(frame, gbc);
		gbc.gridy = 10;
		layout.setConstraints(offset, gbc);
		
		gbc.gridx = 1;
		gbc.gridwidth = 1;
		gbc.gridy = 2;
		layout.setConstraints(origin, gbc);
		
		gbc.gridy = 3;
		layout.setConstraints(originXField, gbc);
		
		gbc.gridy = 4;
		layout.setConstraints(originYField, gbc);
		gbc.gridy = 5;
		layout.setConstraints(originRadField, gbc);
		gbc.gridy = 6;
		layout.setConstraints(originAlphaField, gbc);
		gbc.gridy = 7;
		layout.setConstraints(originShadowAlphaField, gbc);
		gbc.gridy = 9;
		layout.setConstraints(countField, gbc);
		gbc.gridy = 10;
		layout.setConstraints(offsetField, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 2;
		layout.setConstraints(increment, gbc);
		gbc.gridy = 3;
		layout.setConstraints(addXField, gbc);
		gbc.gridy = 4;
		layout.setConstraints(addYField, gbc);
		gbc.gridy = 5;
		layout.setConstraints(addRadField, gbc);
		gbc.gridy = 6;
		layout.setConstraints(addAlphaField, gbc);
		gbc.gridy = 7;
		layout.setConstraints(addShadowAlphaField, gbc);
		gbc.gridy = 8;
		layout.setConstraints(scaleField, gbc);
		gbc.gridy = 9;
		layout.setConstraints(fps, gbc);
		
		add(c);
		add(xLabel);
		add(yLabel);
		add(radLabel);
		add(alphaLabel);
		add(shadowAlphaLabel);
		add(scale);
		add(frame);
		add(offset);
		add(animationEnableBox);
		add(origin);
		add(originXField);
		add(originYField);
		add(originRadField);
		add(originAlphaField);
		add(originShadowAlphaField);
		add(countField);
		add(offsetField);
		add(increment);
		add(addXField);
		add(addYField);
		add(addRadField);
		add(addAlphaField);
		add(addShadowAlphaField);
		add(scaleField);
		add(fps);
		
		if (i >= 2) {
			originXField.setEnabled(false);
			originYField.setEnabled(false);
			originRadField.setEnabled(false);
			originAlphaField.setEnabled(false);
			originShadowAlphaField.setEnabled(false);
		}
	}
	
	public JTextField getOriginXField() {
		return originXField;
	}
	
	public JTextField getOriginYField() {
		return originYField;
	}
	
	public JTextField getOriginRadField() {
		return originRadField;
	}
	
	public JTextField getOriginAlphaField() {
		return originAlphaField;
	}
	
	public JTextField getAddXField() {
		return addXField;
	}
	
	public JTextField getAddYField() {
		return addYField;
	}
	
	public JTextField getAddRadField() {
		return addRadField;
	}
	
	public JTextField getAddAlphaField() {
		return addAlphaField;
	}
	
	public JTextField getOriginShadowAlphaField() {
		return originShadowAlphaField;
	}
	
	public JTextField getAddShadowAlphaField() {
		return addShadowAlphaField;
	}
	
	public JTextField getScaleField() {
		return scaleField;
	}
	
	public JTextField getCountField() {
		return countField;
	}
	
	public JTextField getOffsetField() {
		return offsetField;
	}
	
	public boolean animationEnable() {
		return animationEnableBox.isSelected();
	}
	
}