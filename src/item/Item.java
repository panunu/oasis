package item;

import global.TimeSpace;

import java.awt.Color;


public class Item extends TimeSpace {
	//DUMMY_ITEM("Dummyitem", "This does nothing.", 'Q', Color.pink, 0.0, 1.0);
	
	private String name, description;
	private char avatar;
	private Color color;
	private double weight, value;
	private boolean equipped = false;
	
	public Item(String name, String description, char avatar, Color color, double weight, double value) {
		this.name = name;
		this.description = description;
		
		this.avatar = avatar;
		this.color = color;
		
		this.weight = weight;
		this.value = value;
	}

	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public char getAvatar() {
		return avatar;
	}
	
	public Color getColor() {
		return color;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public double getValue() {
		return value;
	}
	
	public void equip() {
		if(isEquipable())
			equipped = true;
		else
			equipped = false;		
	}
	
	public boolean isEquipped() {
		return equipped;
	}
	
	public boolean isEquipable() {
		return this instanceof Equipable;
	}
	
	public boolean isWeapon() {
		return this instanceof Weapon;
	}
	
	public boolean isArmor() {
		return this instanceof Armor;
	}
	
	public String getType() {
		if(isWeapon())
			return "weapon";
		else if(isArmor())
			return "armor";
		
		return "";
	}
}
