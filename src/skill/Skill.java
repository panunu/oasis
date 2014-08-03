package skill;

public enum Skill {
	swimming("Swimming", "You can swim in water without drowning"),
	use_door("Open door", "You have the ability to use unlocked doors.");
	
	private String name, description;
	
	Skill(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
}
