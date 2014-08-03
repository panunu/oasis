package map;

import global.CustomColor;

import java.awt.Color;

public enum Terrain {
	grass('.', "Grass", "You feel soft, cool grass beneath your feet.", '.', Color.green, true, true, false, false, false, false),
	water('=', "Water", "Brrr, cold water!", '=', Color.blue, true, false, true, false, false, false),
	road(',', "A road", "You're walking on a sand road.", '.', CustomColor.brown, true, true, false, false, false, false),
	mountain('^', "A mountain", "A rough path through some mountains.", '^', Color.white, true, false, false, true, true, false),
	tree('T', "A tree", "You see a magnificent tree.", 'T', Color.green, true, true, false, false, true, false),
	pillar('I', "A pillar", "You see a tall stone pilar.", 'I', Color.lightGray, true, true, false, false, true, false),
	flower(';', "A flower", "A beautiful flower.", ';', Color.red, false, false, false, false, false, false),
	
	wall('#', "A wall", "A hard wall.", '#', Color.gray, false, false, false, false, true, false),
	floor(':', "Floor", "Stone floor.", '.', Color.gray, true, true, false, false, false, false),
	door_closed('+', "A closed door", "A closed door.", '+', CustomColor.brown, false, false, false, false, true, true),
	door_open('/', "An open door", "An open door.", '/', CustomColor.brown, false, true, false, false, false, true),
	
	blank(' ', "Empty", "You feel empty inside", ' ', Color.black, false, false, false, false, false, false);
	
	private String name, description;
	private char key, avatar;
	private Color color;
	private boolean bold;
	private boolean walkable, swimmable, scalable,
					covers, door;
	
	Terrain(char key, String name, String description,
			char avatar, Color color, boolean bold,
			boolean walkable, boolean swimmable, boolean scalable,
			boolean covers, boolean door) {
		this.name = name;
		this.description = description;
		
		this.avatar = avatar;
		this.bold = bold;
		this.color = color;
		this.key = key;
		
		this.walkable = walkable;
		this.swimmable = swimmable;
		this.scalable = scalable;
		
		this.covers = covers;
		this.door = door;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Color getColor() {
		return color;
	}
	
	public char getAvatar() {
		return avatar;
	}
	
	public boolean isBold() {
		return bold;
	}
	
	public char getKey() {
		return key;
	}
	
	public boolean isWalkable() {
		return walkable;
	}
	
	public boolean isSwimmable() {
		return swimmable;
	}
	
	public boolean isScalable() {
		return scalable;
	}
	
	public boolean blocksView() {
		return covers;
	}
	
	public boolean isDoor() {
		return door;
	}

	public static Terrain getTerrainByKey(char mapChar) {
		for(Terrain terrain : Terrain.values()) {
			char mapKey = terrain.getKey();
			if(mapKey == mapChar)
				return terrain;
		}
		return null;
	}
}
