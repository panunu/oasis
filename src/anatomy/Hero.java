package anatomy;

import java.util.ArrayList;

public class Hero extends Creature {	
	public Hero(String name, Race race, Faction faction, int x, int y,
				int strength, int coordination, int intelligency) {
		super(name, race, faction, x, y, strength, coordination, intelligency);
	}
	
	public String move(int x, int y, char[][] map, ArrayList<AI> creatures) {		
		return super.move(x, y, map, creatures, null);
	}
	
	public String attack(int x, int y, char[][] map, ArrayList<AI> creatures) {		
		return super.attack(x, y, map, creatures, null);
	}
}
