package map;

import java.util.ArrayList;

import anatomy.*;
import item.*;

public class Map {
	private String name, description;
	private char[][] map;
	private boolean active = false;
	
	private ArrayList<AI> creatures = new ArrayList<AI>();
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public Map(String name, String description, String filename) {
		this.name = name;
		this.description = description;
		
		map = new MapLoader(filename).getMap();
		
		
		/*creatures.add(new AI("Raider", Race.human, Faction.military_patrol, 22, 13, 3, 0, 0));
		creatures.get(0).addItem(Weapon.ak47);
		creatures.get(0).getInventory().getContents().get(0).equip();
		creatures.add(new AI("Raider", Race.human, Faction.military_patrol, 25, 12, 2, 1, 0));
		creatures.get(1).addItem(Weapon.ak47);
		creatures.get(1).getInventory().getContents().get(0).equip();
		creatures.add(new AI("Raider", Race.human, Faction.military_patrol, 20, 16, 0, 3, 0));
		creatures.get(2).addItem(Weapon.ak47);		
		creatures.get(2).addItem(Ammo.nato762);
		creatures.get(2).getInventory().getContents().get(0).equip();
		
		creatures.add(new AI("Resistance", Race.human, Faction.resistance, 30, 13, 1, 0, 2));
		creatures.get(3).addItem(Weapon.ak47);
		creatures.get(3).getInventory().getContents().get(0).equip();
		creatures.add(new AI("Resistance", Race.human, Faction.resistance, 32, 16, 1, 0, 2));
		creatures.get(4).addItem(Weapon.ak47);
		creatures.get(4).getInventory().getContents().get(0).equip();
		creatures.add(new AI("Resistance", Race.human, Faction.resistance, 34, 12, 1, 0, 2));
		creatures.get(5).addItem(Weapon.ak47);
		creatures.get(5).getInventory().getContents().get(0).equip();*/
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public char[][] getArray() {
		return map;
	}
	
	public ArrayList<AI> getCreatures() {
		return creatures;
	}
	
	public void setActicity() {
		active = active?false:true;
	}
	
	public boolean isActive() {
		return active;
	}
}
