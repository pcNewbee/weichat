package entity;

public abstract class AbstractButon {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AbstractButon(String name) {
		super();
		this.name = name;
	}

}
