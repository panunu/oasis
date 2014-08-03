package anatomy;

import java.awt.Color;
import java.util.ArrayList;

import global.CustomColor;

public enum Faction {
	resistance("Resistance", "", Color.red),
	neutral("Neutral civilians", "", null),
	military_patrol("Military Patrol Units", "", Color.orange),
	military_guard("Military Guards", "", Color.white);
	
	private String name, description;
	private Color color;
	private ArrayList<Faction> enemies = new ArrayList<Faction>();
	
	Faction (String name, String description, Color color) {
		this.name = name;
		this.description = description;
		this.color = color;
	}
	
	public String getName() {
		return name;
	}
	
	public Color getUniform() {
		return color;
	}
	
	public ArrayList<Faction> getEnemies() {
		return getEnemyFactions();
	}	
	
	private ArrayList<Faction> getEnemyFactions() {
		ArrayList<Faction> enemies = new ArrayList<Faction>();
		if(this.equals(resistance)) {
			enemies.add(military_patrol);
			enemies.add(military_guard);
		}
		else if(this.equals(military_patrol)) {
			enemies.add(resistance);
		}
		else if(this.equals(military_guard)) {
			enemies.add(resistance);
		}
		
		return enemies;
	}
}
