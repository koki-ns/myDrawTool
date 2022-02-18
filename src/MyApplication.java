import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MyApplication extends JFrame {
	OperateModeManager operateModeManager;//モード変更を扱うクラス
	MyCanvas canvas;
	Mediator med;
	
	List<Container> partsList;
	
	public MyApplication() {
		super("Kusozako DrawTool");
		
		canvas = new MyCanvas();
		canvas.setBackground(Color.white);
		
		this.med = canvas.getMediator();
		operateModeManager = new OperateModeManager(canvas);
		
		canvas.setOperateModeManager(operateModeManager);
		
		partsList = new ArrayList<Container>();

		
		/*　　　上側に配置されるオブジェクトの要素変更に関する部品　　　*/
		JPanel basePanel = new JPanel();
		basePanel.setLayout(new GridLayout(2,1));
		
		JTabbedPane modeCommandPane = new JTabbedPane();
		
		partsList.add(modeCommandPane);
		
		/*    左側に配置される操作に関する部品    */
		
		SelectModePanel selectModePanel = new SelectModePanel(operateModeManager);
		selectModePanel.setLayout(new BoxLayout(selectModePanel, BoxLayout.Y_AXIS));
		
		JPanel methodsPanel = new JPanel();
		methodsPanel.setLayout(new GridLayout(0, 2));
		
		MoveButton moveButton = new MoveButton(operateModeManager);
		RotateButton rotateButton = new RotateButton(operateModeManager);
		
		methodsPanel.add(moveButton);
		methodsPanel.add(rotateButton);
		
		
		partsList.add(moveButton);
		partsList.add(rotateButton);
		
		selectModePanel.add(methodsPanel);
		
		JPanel cutPastePanel = new JPanel();
		cutPastePanel.setLayout(new GridLayout(2, 2, 0, 0));
		cutPastePanel.setPreferredSize(new Dimension(100, 14));
		CutButton cutButton = new CutButton(med);
		CopyButton copyButton = new CopyButton(med);
		PasteButton pasteButton = new PasteButton(med);
		DeleteButton deleteButton = new DeleteButton(med);
		cutPastePanel.add(cutButton);
		cutPastePanel.add(copyButton);
		cutPastePanel.add(pasteButton);
		cutPastePanel.add(deleteButton);
		
		partsList.add(cutButton);
		partsList.add(copyButton);
		partsList.add(pasteButton);
		partsList.add(deleteButton);
		
		selectModePanel.add(cutPastePanel);
		
		JPanel animationPanel = new JPanel();
		animationPanel.setLayout(new BoxLayout(animationPanel, BoxLayout.Y_AXIS));
		
		JPanel asbPanel = new JPanel();
		asbPanel.setLayout(new GridLayout(1, 1));
		AnimationSettingButton asb = new AnimationSettingButton(med);
		asb.setPreferredSize(new Dimension(100, 15));
		asbPanel.add(asb);
		selectModePanel.add(asbPanel);
		partsList.add(asb);
		
		
		AnimationPlayPanel animationPlayPanel = new AnimationPlayPanel(this, med);
		partsList.add(animationPlayPanel.getApb());
		partsList.add(animationPlayPanel.getAsb());
		
		animationPanel.add(animationPlayPanel);
		selectModePanel.add(animationPanel);
		
		modeCommandPane.addTab("選択/操作",selectModePanel);
		
		DrawModePanel drawModePanel = new DrawModePanel(operateModeManager);
		drawModePanel.setLayout(new GridLayout(4,2));
		
		RectButton rectButton = new RectButton(operateModeManager);
		drawModePanel.add(rectButton);
		OvalButton ovalButton = new OvalButton(operateModeManager);
		drawModePanel.add(ovalButton);
		DiaButton diaButton = new DiaButton(operateModeManager);
		drawModePanel.add(diaButton);
		StarButton starButton = new StarButton(operateModeManager);
		drawModePanel.add(starButton);
		HendeButton hendeButton = new HendeButton(operateModeManager);
		drawModePanel.add(hendeButton);
		
		modeCommandPane.addTab("図形描画",drawModePanel);
		
		modeCommandPane.setForegroundAt(0, Color.BLACK);
		basePanel.add(modeCommandPane);
		
		
		
		
		/* 固定の下側 */
		
		JPanel drawOptionPanel = new JPanel();
		drawOptionPanel.setLayout(new BoxLayout(drawOptionPanel, BoxLayout.Y_AXIS));
		
		JPanel fillColorPanel = new JPanel();
		fillColorPanel.setLayout(new FlowLayout());
		
		ColorIcon fillColorIcon = new ColorIcon(Color.white);
		JLabel fillColorLabel = new JLabel("塗り色:",fillColorIcon, JLabel.CENTER);
		fillColorLabel.setHorizontalTextPosition(JLabel.LEFT);
		
		fillColorComboBox fcom = new fillColorComboBox(new Vector<SelectColor>(), med, fillColorIcon, drawOptionPanel);
		fcom.addItem(new SelectBlackColor());
		fcom.addItem(new SelectWhiteColor());
		fcom.addItem(new SelectRedColor());
		fcom.addItem(new SelectBlueColor());
		fcom.addItem(new SelectGreenColor());
		fcom.addItem(new SelectOtherColor(this));
		fcom.setSelectedIndex(1);
		
		partsList.add(fcom);
		
		fillColorPanel.add(fillColorLabel);
		fillColorPanel.add(fcom);
		drawOptionPanel.add(fillColorPanel);
		
		JPanel lineColorPanel = new JPanel();
		lineColorPanel.setLayout(new FlowLayout());
		
		ColorIcon lineColorIcon = new ColorIcon(Color.black);
		JLabel lineColorLabel = new JLabel("線の色:",lineColorIcon, JLabel.CENTER);
		lineColorLabel.setHorizontalTextPosition(JLabel.LEFT);
		
		LineColorComboBox lcom = new LineColorComboBox(new Vector<SelectColor>(), med, lineColorIcon, drawOptionPanel);
		lcom.addItem(new SelectBlackColor());
		lcom.addItem(new SelectWhiteColor());
		lcom.addItem(new SelectRedColor());
		lcom.addItem(new SelectBlueColor());
		lcom.addItem(new SelectGreenColor());
		lcom.addItem(new SelectOtherColor(this));
		
		partsList.add(lcom);
		
		lineColorPanel.add(lineColorLabel);
		lineColorPanel.add(lcom);
		drawOptionPanel.add(lineColorPanel);
		
		JPanel lwidPanel = new JPanel();
		lwidPanel.setLayout(new FlowLayout());
		
		JLabel lwid = new JLabel("線の太さ:                     ");
		LwidthComboBox lwidcom = new LwidthComboBox(new Vector<Integer>(), med);
		lwidcom.addItem(1);
		lwidcom.addItem(3);
		lwidcom.addItem(5);
		
		lwidPanel.add(lwid);
		lwidPanel.add(lwidcom);
		partsList.add(lwidcom);
		drawOptionPanel.add(lwidPanel);
		
		AlphaPanel alphaPanel = new AlphaPanel(med);
		drawOptionPanel.add(alphaPanel);
		partsList.add(alphaPanel.getSlider());
		partsList.add(alphaPanel.getSpinner());
		
		JPanel shadowPanel = new JPanel();
		shadowPanel.setLayout(new FlowLayout());
		
		DropShadowCheckBox dropShadow = new DropShadowCheckBox(operateModeManager);
		//dropShadow.setAlignmentX(CENTER_ALIGNMENT);
		shadowPanel.add(dropShadow);
		partsList.add(dropShadow);
		shadowPanel.add(Box.createHorizontalGlue());
		shadowPanel.add(Box.createRigidArea(new Dimension(80,30)));
		
		drawOptionPanel.add(shadowPanel);
		
		ShadowAlphaPanel shadowAlphaPanel = new ShadowAlphaPanel(med);
		drawOptionPanel.add(shadowAlphaPanel);
		partsList.add(shadowAlphaPanel.getSlider());
		partsList.add(shadowAlphaPanel.getSpinner());
		
		
		JPanel mirrorPanel = new JPanel();
		shadowPanel.setLayout(new FlowLayout());
		
		MirrorCheckBox mirrorCheckBox = new MirrorCheckBox(med);
		//dropShadow.setAlignmentX(CENTER_ALIGNMENT);
		mirrorPanel.add(mirrorCheckBox);
		partsList.add(mirrorCheckBox);
		mirrorPanel.add(Box.createHorizontalGlue());
		mirrorPanel.add(Box.createRigidArea(new Dimension(120,30)));
		
		drawOptionPanel.add(mirrorPanel);
		
		
		drawOptionPanel.add(Box.createVerticalGlue());
		drawOptionPanel.add(Box.createRigidArea(new Dimension(80,450)));
		
		operateModeManager.setAlphaPanel(alphaPanel);
		operateModeManager.setDropShadowCheckBox(dropShadow);
		operateModeManager.setShadowAlphaPanel(shadowAlphaPanel);
		
		operateModeManager.setMirrorCheckBox(mirrorCheckBox);
		
		basePanel.add(drawOptionPanel);
		
		
		/* メニューバー */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("ファイル");
		JMenu editMenu = new JMenu("編集");
		JMenu toolMenu = new JMenu("ツール");
		
		FileInputItem  fileInputItem  = new FileInputItem(this, med);
		FileOutputItem fileOutputItem = new FileOutputItem(this, med);
		ImageOpenItem  imageOpenItem  = new ImageOpenItem(this, med);
		fileMenu.add(fileInputItem);
		fileMenu.add(fileOutputItem);
		fileMenu.add(imageOpenItem);
		partsList.add(fileInputItem);
		partsList.add(fileOutputItem);
		partsList.add(imageOpenItem);
		
		SelectModeItem selectModeItem = new SelectModeItem(operateModeManager, modeCommandPane);
		DrawModeItem     drawModeItem = new DrawModeItem(operateModeManager, modeCommandPane);
		editMenu.add(selectModeItem);
		editMenu.add(drawModeItem);
		partsList.add(selectModeItem);
		partsList.add(drawModeItem);
		
		ScaleItem scaleItem = new ScaleItem(operateModeManager);
		BackGroundColorItem backGroundColorItem = new BackGroundColorItem(operateModeManager);
		ToumeiItem toumeiItem = new ToumeiItem(operateModeManager);
		ToFrontItem toFrontItem = new ToFrontItem(operateModeManager);
		toolMenu.add(scaleItem);
		toolMenu.add(backGroundColorItem);
		toolMenu.add(toumeiItem);
		toolMenu.add(toFrontItem);
		partsList.add(scaleItem);
		partsList.add(backGroundColorItem);
		partsList.add(toumeiItem);
		partsList.add(toFrontItem);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(toolMenu);
		
		
		/* レイアウト */
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(basePanel, BorderLayout.EAST);
		getContentPane().add(canvas, BorderLayout.CENTER);
		
		
		
		modeCommandPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JTabbedPane jtab = (JTabbedPane) e.getSource();
				int sindex = jtab.getSelectedIndex();
				jtab.setForegroundAt(sindex, Color.BLACK);
				
				((ModePanel)jtab.getSelectedComponent()).selected();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			//ウィンドウ閉じて終了
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
	
	public void setPartsEnabled(boolean b, ArrayList<Container> exlist) {
		if (exlist == null) {
			for (Container c : partsList) {
				c.setEnabled(b);
			}
		} else {
			for (Container c : partsList) {
				if (!exlist.contains(c)) {
					c.setEnabled(b);
				}
			}
		}
	}
	
	public void removeListeners() {
		canvas.removeListeners();
	}
	
	public void addListeners() {
		canvas.addListeners();
	}
	
	/*
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

	    layout.show(modeCommandPanel, cmd);
	}
	*/
	
	public Dimension getPreferredSize() {
		return new Dimension(1200, 800);
	}
	
	public static void main(String[] args) {
		MyApplication app = new MyApplication();
		app.pack();
		app.setVisible(true);
	}
}

