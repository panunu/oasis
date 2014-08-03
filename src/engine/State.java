package engine;

public enum State {
	GAME(1),
	INVENTORY(2);
	
	private int id;
	
	State(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return toString();
	}
}
