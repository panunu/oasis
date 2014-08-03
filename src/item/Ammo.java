package item;

import java.awt.Color;

import global.CustomColor;

public class Ammo extends Item {	
	
	public final static Ammo nato762 = 
		new Ammo("7.62 mm NATO", "Basic ammo for assault rifles.", '/', Color.yellow, 1.0);
	
	private double damage;

	public Ammo(String name, String description, char avatar, Color color, double damage) {
		super(name, description, avatar, color, 0.0, 1.0);
		this.damage = damage;
	}
	
	public double getDamage() {
		return damage;
	}
}
