package de.ddkfm.micro.ui;

import de.ddkfm.micro.models.LogicValue;
import de.ddkfm.micro.util.ThemeUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;

/**
 * a special Button which can be connected to a LogicValue or a LogicNode to display 0 or 1
 * */
public class LogicButton extends Button {
	private final String CAPTION_OFF = ThemeUtils.getThemeProperty("micro2.logicnode.caption.off");
	private final String CAPTION_ON = ThemeUtils.getThemeProperty("micro2.logicnode.caption.on");
	private final String STYLECLASS_OFF = ThemeUtils.getThemeProperty("micro2.logicnode.style.off");
	private final String STYLECLASS_ON = ThemeUtils.getThemeProperty("micro2.logicnode.style.on");
	private BooleanProperty value;
	private String name;
	private LogicValue logic;
	private int index;
	public LogicButton(LogicValue logic, int index) {
		this.logic = logic;
		this.name = logic.getName();
		this.index = index;
		value = new SimpleBooleanProperty(logic.getValue(index));
		getStyleClass().add(this.STYLECLASS_OFF);
		setText(this.CAPTION_OFF);
		value.bind(logic.getValueProperty(index));
		value.addListener((observable, oldValue, newValue)->{
			if(newValue){
				if(getStyleClass().contains(this.STYLECLASS_OFF))
					getStyleClass().remove(this.STYLECLASS_OFF);
				getStyleClass().add(STYLECLASS_ON);
			}else{
				if(getStyleClass().contains(this.STYLECLASS_ON))
					getStyleClass().remove(this.STYLECLASS_ON);
				getStyleClass().add(STYLECLASS_OFF);
			}	
			setText(newValue ? this.CAPTION_ON : this.CAPTION_OFF);
		});
		this.setOnMouseClicked(event->{
			this.logic.changeByExtern(this, this.index, !this.value.get());
		});
	}
	/**
	 * return the name of the logic button
	 * */
	public String getName() {
		return name;
	}

	/**
	 * set the name of the logic button
	 * */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * return the underlying logic
	 * */
	public LogicValue getLogic() {
		return logic;
	}

	/**
	 * set the underlying logic
	 * */
	public void setLogic(LogicValue logic) {
		this.logic = logic;
	}

	/**
	 * return the index to which the LogicButton is referenced
	 * */
	public int getIndex() {
		return index;
	}

	/**
	 * set the index to which the LogicButton is referenced
	 * */
	public void setIndex(int index) {
		this.index = index;
	}
	
}
