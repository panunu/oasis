package anatomy;

import java.util.ArrayList;

public class AI extends Creature {
	
	private int hostileX, hostileY, hostileIndex = -1;
	private int search = 0;
	private boolean hasSeenHostile = false;
	
	public AI(String name, Race race, Faction faction, int x, int y,
			int strength, int coordination, int intelligency) {
		super(name, race, faction, x, y, strength, coordination, intelligency);
	}
	
	public void engage(char[][] map, ArrayList<AI> creatures, Hero hero) {
		int index = -1, distance = 100, moveX = 0, moveY = 0;
		
		for(int i = 0; i < creatures.size(); i++) {			
			for(int j = 0; j < getFaction().getEnemies().size(); j++) {
				if(creatures.get(i).getFaction().equals(getFaction().getEnemies().get(j))) {
					if(hasVisual(creatures.get(i).getX(), creatures.get(i).getY(), map)) {
						if(creatures.get(i).getDistance(getX(), getY()) < distance) {
							index = i;
							distance = creatures.get(i).getDistance(getX(), getY());
							hasSeenHostile = true;
							search = 10;
						}			
					}
				}					
			}
		}
		
		for(int i = 0; i < getFaction().getEnemies().size(); i++) {
			if(hero.getFaction().equals(getFaction().getEnemies().get(i))) {
				if(hasVisual(hero.getX(), hero.getY(), map)) {
					if(hero.getDistance(getX(), getY()) < distance) {
						index = -2;
						hasSeenHostile = true;
						search = 10;
					}
				}
			}
		}
		
		if(index == -1 && !hasSeenHostile) {
			hostileX = -1;
			hostileY = -1;
		}
		if(index >= 0) {
			hostileX = creatures.get(index).getX();
			hostileY = creatures.get(index).getY();
		}
		else if(index == -2) {
			hostileX = hero.getX();
			hostileY = hero.getY();
		}
		else if(hasSeenHostile && search > 0) {
			if(search > 0)
				search--;
		}
		else if(hasSeenHostile && search == 0) {
			hasSeenHostile = false;
			index = -1;
		}
		
		if(hostileX != -1) {
			if(hostileX > getX())
				moveX = RIGHT;
			else if(hostileX < getX())
				moveX = LEFT;
			if(hostileY > getY())
				moveY = DOWN;
			else if(hostileY < getY())
				moveY = UP;
		}
		
		if(!hasSeenHostile) {
			double direction = Math.random();
			
			if(direction <= 0.3)
				moveX = RIGHT;
			else if(direction <= 0.6)
				moveX = LEFT;
			else
				moveX = 0;
			
			direction = Math.random();
			
			if(direction <= 0.3)
				moveY = UP;
			else if(direction <= 0.6)
				moveY = DOWN;
			else
				moveY = 0;				
		}
		
		if(hasSeenHostile) {
			if(index == -2) {
				if(getInventory().getEquippedWeapon().getRange() < getDistance(hero.getX(), hero.getY())
					|| getVision() <= getDistance(hero.getX(), hero.getY()))
					move(moveX, moveY, map, creatures, hero);
				else if(getVision() >= getDistance(hero.getX(), hero.getY()))
					attack(hostileX, hostileY, map, creatures, hero);
			}
			else if(index >= 0) {
				if(getInventory().getEquippedWeapon().getRange() < getDistance(creatures.get(index).getX(), creatures.get(index).getY())
						|| getVision() <= getDistance(creatures.get(index).getX(), creatures.get(index).getY()))
						move(moveX, moveY, map, creatures, hero);
					else if(getVision() >= getDistance(creatures.get(index).getX(), creatures.get(index).getY()))
						attack(hostileX, hostileY, map, creatures, hero);
			}
		}
		else
			move(moveX, moveY, map, creatures, hero);
	}
}