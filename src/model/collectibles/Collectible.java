package model.collectibles;

import model.characters.Hero;
import model.characters.Character;

import java.io.Serializable;

public interface Collectible extends Serializable {
	
	void pickUp(Hero h);
	
	void use(Hero h);

}
