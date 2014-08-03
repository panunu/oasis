package anatomy;

import java.util.ArrayList;
import java.awt.Color;

import map.Terrain;
import skill.Skill;
import global.TimeSpace;
import item.*;

public class Creature extends TimeSpace {	
	protected String name;
	protected Race race;
	protected Faction faction;
	
	protected int strength = 4, coordination = 4, intelligency = 4;
	protected int health, stamina, nerves;
	protected int vision, hearing, communication;
	protected double accuracy, critical, gossip; // 0-100% may overflow max value
	protected double capacity;
	protected int damage = 0, panic = 0, fatigue = 0;
	protected int xp = 0;
	
	protected ArrayList<Skill> skills = new ArrayList<Skill>();
	protected Inventory inventory;
	
	protected int[][] perceives = new int[20][78];
	
	protected int swimdistance = 0, climbdistance = 0, walkdistance = 0;
			
	public Creature(String name, Race race, Faction faction, int x, int y,
					int strength, int coordination, int intelligency) {
		this.name = name;
		this.race = race;
		this.faction = faction;
		
		this.strength += strength;
		this.coordination += coordination;
		this.intelligency += intelligency;
		
		this.health = 20 + this.strength * 2;
		this.stamina = (int) Math.round(this.strength * 1.5) + this.coordination;
		this.nerves = 2 + this.intelligency * 2;
		
		this.vision = 2 + this.coordination;
		this.hearing = 4 + this.coordination;
		this.communication = 2 + this.intelligency;
		
		this.accuracy = (this.coordination + this.intelligency/2.0)/10.0;
		this.critical = (this.coordination/8 + this.intelligency/6)/10;
		this.gossip = this.intelligency * (this.intelligency/2)/10;
		this.capacity = Math.pow(this.strength, 2);
		
		inventory = new Inventory("Backpack", "", capacity);
		
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Info
	 */
	
	public String getName() {
		return name;
	}
	
	public Race getRace() {
		return race;
	}
	
	public Color getColor() {
		if(faction.getUniform() == null)
			return race.getSkin();
		else
			return faction.getUniform();
	}
	
	/*
	 * Stats
	 */
	
	public int getHealth() {
		return health - damage;
	}
	
	public int getTotalHealth() {
		return health;
	}
	
	public int getStamina() {
		return stamina - fatigue;
	}
	
	public int getTotalStamina() {
		return stamina;
	}
	
	public void addFatigue(int fatigue) {
		this.fatigue += fatigue;
		if(this.fatigue >= stamina)
			this.fatigue = stamina;
	}
	
	public void healFatigue(int heal) {
		fatigue -= heal;
		
		if(fatigue < 0)
			fatigue = 0;
	}
	
	public void addDamage(int damage) {
		this.damage += damage;
	}
	
	public void healDamage(int heal) {
		damage -= heal;
		
		if(damage < 0)
			damage = 0;
	}
	
	public double getAccuracy() {
		return accuracy;
	}
	
	/*
	 * Skills
	 */
	
	public void addSkill(Skill skill) {
		skills.add(skill);
	}
	
	public boolean isAbleToSwim() {
		if(skills.contains(Skill.swimming))
			return true;
		return false;
	}
	
	public boolean isAbleToClimb() {
		if(inventory.hasItem(Weapon.climbing_hook))
			return true;
		return false;
	}
	
	public boolean isAbleToUseDoors() {
		if(skills.contains(Skill.use_door))
			return true;
		return false;
	}
	
	/*
	 * Items
	 */
	
	public void addItem(Item item) {
		inventory.addItem(item);
	}
	
	public void dropItem(int index) {
		inventory.removeItem(index);
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	/*
	 * Movement
	 */
	
	public String move(int x, int y, char[][] map, ArrayList<AI> creatures, Hero hero) {
		if((hasCreature(getX() + x, getY() + y, creatures, hero)) == null) {	
			if(walkable(x, y, map)) {					
				setX(getX() + x);
				setY(getY() + y);
				walkdistance++;
				swimdistance = 0;
				climbdistance = 0;
					
				if(walkdistance % 3 == 0)
					healFatigue(1);
					
				return null;
			}						
			else if(swimmable(x, y, map)) {
				setX(getX() + x);
				setY(getY() + y);
				swimdistance++;
				walkdistance = 0;
				climbdistance = 0;
				
				if(swimdistance % 2 == 0)
					addFatigue(1);
				
				if(!isAbleToSwim()) {				
					if(swimdistance > 1) {
						addDamage(1);
						
						return "You don't know how to swim. You are drowning! ";
					}
					return "You don't know how to swim. ";
				}			
				
				if(getStamina() > 0)
					return "You're swimming easily. ";
				else {
					addDamage(1);
					return "You are too tired to swim. You are drowning!";
				}
			}
			else if(scalable(x, y, map)) {			
				if(isAbleToClimb()) {
					setX(getX() + x);
					setY(getY() + y);
					walkdistance = 0;
					swimdistance = 0;				
					climbdistance++;
					
					if(climbdistance % 2 == 0)
						addFatigue(1);
					
					return "You're climbing the mountains. ";
				}
				return "You need scaling equipment to climb those mountains. ";
			}
		}
		else
			return "Something blocks your way. ";
		
		addDamage(1);
		return "Ouch! You ran into an obstacle. ";
	}
	
	/*
	 * Combat
	 */
	
	public String attack(int x, int y, char[][] map, ArrayList<AI> creatures, Hero hero) {
		addFatigue(1);
		boolean collides = false;
		boolean collidesWithCreature = false;

		int x0 = getX(), y0 = getY(), x1 = x, y1 = y;
		int Dx = x1 - x0; 
		int Dy = y1 - y0;
		boolean steep = (Math.abs(Dy) >= Math.abs(Dx));
		
		// Based on Bresenham's algorithm
		if (steep) {
			int temp = x0;
			x0 = y0;
			y0 = temp;

			temp = x1;
			x1 = y1;
			y1 = temp;
			// recompute Dx, Dy after swap
			Dx = x1 - x0;
			Dy = y1 - y0;
		}
		
		int xstep = 1;
		if (Dx < 0) {
			xstep = -1;
			Dx = -Dx;
		}
		
		int ystep = 1;
		if (Dy < 0) {
			ystep = -1;		
			Dy = -Dy; 
		}
		
		int TwoDy = 2*Dy; 
		int TwoDyTwoDx = TwoDy - 2*Dx; // 2*Dy - 2*Dx
		int E = TwoDy - Dx; //2*Dy - Dx
		y = y0;
		int xDraw = 0, yDraw = 0;
		
		boolean hit = getAccuracy() > Math.random();
		
		if(!hit) {
			int miss = Math.random() > 0.5?1:-1;
				
			x1 += miss;			
		}
		
		boolean first = true;
		for (x = x0; x != x1 && !collides && !collidesWithCreature; x += xstep) {		
			if (steep) {			
				xDraw = y;
				yDraw = x;
			}
			else {			
				xDraw = x;
				yDraw = y;
			}
	
			if(Terrain.getTerrainByKey(map[yDraw][xDraw]).blocksView() && getDistance(x, y) > 0 && !first)
				collides = true;
			else if(hasCreature(xDraw, yDraw, creatures, hero) != null)
				collidesWithCreature = true;

			if (E > 0) {
				E += TwoDyTwoDx; //E += 2*Dy - 2*Dx;
				y += ystep;
			}
			else
				E += TwoDy; //E += 2*Dy;
			
			if(first)
				first = false;
		}
		
		/*if(yDraw != y1)
			yDraw += ystep;
		
		if(xDraw != x1)
			xDraw += xstep;*/

		System.out.println(getAccuracy() + " " + hit);
		
		if(steep) {
			int temp = y1;
			y1 = x1;
			x1 = temp;
		}
			
		
		if(!collides && !collidesWithCreature) {	
			int damage = (int) Math.round((getInventory().getEquippedWeapon().getCondition() * getInventory().getEquippedWeapon().getDamage()) * getInventory().getEquippedWeapon().getAmmo().getDamage());	
			Creature creature;
			
			if((creature = hasCreature(x1, y1, creatures, hero)) != null) {
				creature.addDamage(damage);
				return getName() + " hits " + creature.getName() + "! ";
			}
			return getName() + " hits " + Terrain.getTerrainByKey(map[y1][x1]).getName().toLowerCase() + ". ";
		}
		
		return getName() + " hits " + Terrain.getTerrainByKey(map[yDraw][xDraw]).getName().toLowerCase() + ". ";
	}

	/*
	 * Death
	 */
	
	public String isDead() {
		if((health - damage) <= 0)
			return getName() + " dies!";
		
		return null;
	}
	
	/*
	 * Factions
	 */
	
	public Faction getFaction() {
		return faction;
	}
	
	public void setFaction(Faction faction) {
		this.faction = faction;
	}
	
	/*
	 * Environment actions
	 */
	
	public String openDoor(int x, int y, char[][] map) {
		if(isAbleToUseDoors()) {
			if(checkBounds(x, y, map)) {
				if(Terrain.getTerrainByKey(map[getY() + y][getX() + x]).isDoor() && Terrain.getTerrainByKey(map[getY() + y][getX() + x]).equals(Terrain.door_closed)) {
					map[getY() + y][getX() + x] = Terrain.door_open.getKey();
					return "Door opened. ";
				}
			}
		}
		return "No closed door in that direction. ";
	}
	
	public String closeDoor(int x, int y, char[][] map) {
		if(isAbleToUseDoors()) {
			if(checkBounds(x, y, map)) {
				if(Terrain.getTerrainByKey(map[getY() + y][getX() + x]).isDoor() && Terrain.getTerrainByKey(map[getY() + y][getX() + x]).equals(Terrain.door_open)) {
					map[getY() + y][getX() + x] = Terrain.door_closed.getKey();
					return "Door closed. ";
				}
			}
		}
		return "No open door in that direction. ";
	}
	
	/*
	 * Vision
	 */
	
	public int getVision() {
		return vision;
	}
	
	public void addRevealed(int x, int y) {
		perceives[y][x] = 1;
	}
	
	public boolean hasPerception(int x, int y) {
		return perceives[y][x] == 1;	
	}
	
	public boolean hasClearView(int x, int y, char[][] map) {
		boolean isClear = true;
		
		int x0 = getX(), y0 = getY(), x1 = x, y1 = y;
		int Dx = x1 - x0; 
		int Dy = y1 - y0;
		boolean steep = (Math.abs(Dy) >= Math.abs(Dx));
		
		// Based on Bresenham's algorithm
		
		if (steep) {
			int temp = x0;
			x0 = y0;
			y0 = temp;

			temp = x1;
			x1 = y1;
			y1 = temp;
			// recompute Dx, Dy after swap
			Dx = x1 - x0;
			Dy = y1 - y0;
		}
		
		int xstep = 1;
		if (Dx < 0) {
			xstep = -1;
			Dx = -Dx;
		}
		
		int ystep = 1;
		if (Dy < 0) {
			ystep = -1;		
			Dy = -Dy; 
		}
		
		int TwoDy = 2*Dy; 
		int TwoDyTwoDx = TwoDy - 2*Dx; // 2*Dy - 2*Dx
		int E = TwoDy - Dx; //2*Dy - Dx
		y = y0;
		int xDraw, yDraw;	
		
		boolean first = true;
		for (x = x0; x != x1; x += xstep) {		
			if (steep) {			
				xDraw = y;
				yDraw = x;
			} else {			
				xDraw = x;
				yDraw = y;
			}
	
			if(Terrain.getTerrainByKey(map[yDraw][xDraw]).blocksView() && getDistance(x, y) > 0 && !first)
				isClear = false;

			if (E > 0) {
				E += TwoDyTwoDx; //E += 2*Dy - 2*Dx;
				y = y + ystep;
			}
			else
				E += TwoDy; //E += 2*Dy;
			
			if(first)
				first = false;
		}
		
		// own algorithm, doesn't process vision extending based on distance
		/*	
		while(getY() > y || getY() < y || getX() > x || getX() < x) {	
			if(getX() > x) {
				x++;
			}
			else if(getX() < x) {
				x--;
			}
			if(getY() > y) {
				y++;
			}
			else if(getY() < y) {
				y--;
			}
			
			if(Terrain.getTerrainByKey(map[y][x]).blocksView() && getDistance(x, y) > 0)
				isClear = false;
			
			if(getDistance(x, getY()) > getDistance(getX(), y)) {
				if(getX() >= x)
					x++;
				else if(getX() <= x)
					x--;
			}			
			else if(getDistance(x, getY()) < getDistance(getX(), y)) {
				if(getY() >= y)
					y++;
				else if(getY() <= y)
					y--;
			}
						
			if(Terrain.getTerrainByKey(map[y][x]).blocksView() && getDistance(x, y) > 0)
				isClear = false;
		}
		*/
		
		return isClear;
	}
	
	public boolean hasVisual(int x, int y, char[][] map) {
		if(getDistance(x, y) < vision)
			if(hasClearView(x, y, map))
				return true;
		return false;
	}
	
	/*
	 * Surroundings
	 */	
	
	public boolean swimmable(int x, int y, char[][] map) {
		if(checkBounds(x, y, map)) {
			if((Terrain.getTerrainByKey(map[getY() + y][getX() + x])).isSwimmable())
				return true;
		}
		return false;
	}
	
	public boolean walkable(int x, int y, char[][] map) {
		if(checkBounds(x, y, map))
			return Terrain.getTerrainByKey(map[getY() + y][getX() + x]).isWalkable();
		return false;
	}
	
	public boolean scalable(int x, int y, char[][] map) {
		if(checkBounds(x, y, map))
			return Terrain.getTerrainByKey(map[getY() + y][getX() + x]).isScalable();
		return false;
	}
	
	public Creature hasCreature(int x, int y, ArrayList<AI> creatures, Hero hero) {
		for(int i = 0; i < creatures.size(); i++) {			
			if(x == creatures.get(i).getX()) {
				if(y == creatures.get(i).getY())
					return creatures.get(i);
			}
		}
		
		if(hero != null)
			if(x == hero.getX())
				if(y == hero.getY())
					return hero;					
		
		return null;
	}

	public String getSurroundings(char[][] map) {
		return Terrain.getTerrainByKey(map[getY()][getX()]).getDescription();
	}
}