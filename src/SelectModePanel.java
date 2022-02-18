public class SelectModePanel extends ModePanel {
OperateModeManager operateModeManager;
	
	SelectModePanel(OperateModeManager operateModeManager) {
		super();
		
		this.operateModeManager = operateModeManager;
	}
	
	public void selected() {
		operateModeManager.setOperateMode((new SelectMode(operateModeManager)));
	}
}