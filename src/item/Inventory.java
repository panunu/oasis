package item;

import java.util.ArrayList;

public class Inventory {
	private String name, description;
	private double capacity;
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public Inventory(String name, String description, double capacity) {
		this.name = name;
		this.description = description;
		this.capacity = capacity;
	}
	
	public boolean addItem(Item item) {
		if(items.size() < capacity) {
			items.add(item);
			return true;
		}
		return false;
	}
	
	public boolean removeItem(int index) {
		items.remove(index);
		return true;
	}
	
	public boolean hasItem(Item item) {
		if(items.contains(item))
			return true;
		else
			return false;
	}
	
	public ArrayList<Item> getContents() {
		return items;
	}
	
	public double getTotalWeight() {
		double weight = 0;
		for(int i = 0; i < items.size(); i++) {
			weight += items.get(i).getWeight();
		}		
		return weight;
	}
	
	public Weapon getEquippedWeapon() {
		for(int i = 0; items.size() > 0 && i < items.size(); i++) {
			if(items.get(i).isWeapon() && items.get(i).isEquipped())
				return (Weapon) items.get(i);
		}
		return null;
	}
}