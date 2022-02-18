public class DrawModePanel extends ModePanel {
OperateModeManager operateModeManager;
	
	DrawModePanel(OperateModeManager operateModeManager) {
		super();
		
		this.operateModeManager = operateModeManager;
	}
	
	public void selected() {
		operateModeManager.setOperateMode((new StateManager(operateModeManager)));
	}
}