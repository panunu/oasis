package anatomy;

import global.CustomColor;

import java.awt.Color;

public enum Race {
	human("Human", "A human.", '@', Color.yellow);
	
	protected String name, description;
	protected char avatar;
	protected Color skin;
	
	Race(String name, String description, char avatar, Color skin) {
		this.name = name;
		this.description = description;
		this.avatar = avatar;
		this.skin = skin;
	}
	
	public String getName() {
		return name;
	}
	
	public char getAvatar() {
		return avatar;
	}
	
	public Color getSkin() {
		return skin;
	}
	
	public String getDescription() {
		return description;
	}
}