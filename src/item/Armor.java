package item;

import global.CustomColor;

import java.awt.Color;

public class Armor extends Equipable {
	public final static Armor leather_armor =
		new Armor("Leather armor", "A light armor made of leather.",'&', CustomColor.brown,
					15, 125);

	private int protection;
	
	public Armor(String name, String description, char avatar, Color color, double weight, double value) {
		super(name, description, avatar, color, weight, value);
	}
}
