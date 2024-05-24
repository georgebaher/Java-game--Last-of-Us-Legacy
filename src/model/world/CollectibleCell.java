package model.world;

import model.collectibles.Collectible;

import java.io.Serializable;

public class CollectibleCell extends Cell implements Serializable {

	private Collectible collectible;

	public CollectibleCell(Collectible collectible) {
		this.collectible = collectible;
	}

	public Collectible getCollectible() {
		return collectible;
	}
	

}
