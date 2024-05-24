package model.world;

import java.io.Serializable;

public abstract class Cell implements Serializable {

	private boolean isVisible;

	public Cell() {

	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

}
