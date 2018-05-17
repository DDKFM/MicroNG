package de.ddkfm.micro.ui;

import de.ddkfm.micro.models.LogicValue;

public class FlagFX extends LogicNode {
	public FlagFX() {}
	@Override
	public void setLogic(LogicValue logic) {
		super.setLogic(logic);
		double baseX = this.points.get(0).getX();
		double baseY = this.points.get(0).getY();
		FlagButton button = new FlagButton(this.logic,0);
		button.setLayoutX(baseX);
		button.setLayoutY(baseY);
		this.getChildren().add(button);
	}
	
}
