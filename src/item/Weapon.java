package item;

import java.awt.Color;

public class Weapon extends Equipable {
	// Additional equipment
	public static Weapon climbing_hook =
		new Weapon("Climbing hook", "Equipment to scale mountains, weak as a weapon.", '/', Color.red, 0.5, 1, null, 5.0, 75.0);
	
	// Assault rifles
	public static Weapon ak47 = 
		new Weapon("AK-47", "Avtomat Kalashnikova, the mother of all assault rifles.", '/', Color.red, 2.0, 8, Ammo.nato762, 4.3, 50.0);
	
	private int range;
	private double damage, condition = 0.0;
	private Ammo ammo;
	
	public Weapon(String name, String description, char avatar, Color color, double damage, int range, Ammo ammo, double weight, double value) {
		super(name, description, avatar, color, weight, value);
		this.damage = damage;
		this.range = range;
		this.ammo = ammo;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public double getCondition() {
		return condition;
	}
	
	public void setCondition(double condition) {
		this.condition = condition;
	}
		
	public int getRange() {
		return range;
	}
	
	public Ammo getAmmo() {
		return ammo;
	}
}