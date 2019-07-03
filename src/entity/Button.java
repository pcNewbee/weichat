package entity;

import java.util.ArrayList;
import java.util.List;

public class Button {

	private List<AbstractButon> button = new ArrayList<>();

	public List<AbstractButon> getButton() {
		return button;
	}

	public void setButton(List<AbstractButon> button) {
		this.button = button;
	}

}
