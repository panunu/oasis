package creature;

import java.util.ArrayList;

import anatomy.*;

public class Player extends Hero {
	
	public Player(String name, Race race, Faction faction, int x, int y) {
		super(name, race, faction, x, y,
				1, 1, 1);		
	}
}
