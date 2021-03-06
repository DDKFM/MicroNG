package de.ddkfm.micro.ui;

import de.ddkfm.micro.models.LogicValue;
import de.ddkfm.micro.util.ThemeUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DataSwitchFX extends LogicNode {
	private ImageView imgView = new ImageView();
	public DataSwitchFX() {}
	@Override
	public void setLogic(LogicValue logic) {
		super.setLogic(logic);
		double baseX = this.points.get(0).getX();
		double baseY = this.points.get(0).getY();
		imgView.setLayoutX(baseX);
		imgView.setLayoutY(baseY);
		imgView.setImage(new Image(ThemeUtils.getResourcePart("pass_off")));
		LogicButton button = new LogicButton(this.logic,0);
		button.setLayoutX(baseX + 5);
		button.setLayoutY(baseY + 3);
		this.getChildren().add(imgView);
		this.getChildren().add(button);
	}
	@Override
	protected void change(int index, boolean value) {
		if(value){
			imgView.setImage(new Image(ThemeUtils.getResourcePart("pass_down")));
		}else{
			imgView.setImage(new Image(ThemeUtils.getResourcePart("pass_off")));
		}
	}
	
}
