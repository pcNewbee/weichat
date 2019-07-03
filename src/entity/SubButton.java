package entity;

import java.util.ArrayList;
import java.util.List;

public class SubButton extends AbstractButon {
	
	private List<AbstractButon> sub_button = new ArrayList<>();

	public List<AbstractButon> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<AbstractButon> sub_button) {
		this.sub_button = sub_button;
	}

	public SubButton(String name) {
		super(name);
	}
	
}
