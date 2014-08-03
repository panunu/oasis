package global;

public class TimeSpace {
	protected int x, y;	
	public static final int LEFT = -1, RIGHT = 1, DOWN = 1, UP = -1;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getDistance(int x, int y) {
		int distance = (int) Math.round(Math.sqrt(Math.pow(Math.abs(getX() - x), 2) + Math.pow(Math.abs(getY() - y), 2)));
		
		return distance;
	}
	
	public boolean checkBounds(int x, int y, char[][] map) {
		int bX = getX() + x, bY = getY() + y;
		boolean isInsideBounds = true;
		
		if(y == DOWN) {
			if(bY >= map.length)
				isInsideBounds = false;
		}
		else if(y == UP) {
			if(bY < 0)
				isInsideBounds = false;
		}
		
		if(isInsideBounds) {
			if(x == LEFT) {
				if(bX < 0)
					isInsideBounds = false;
			}
			else if(x == RIGHT) {
				if(bX >= map[bY].length)
					isInsideBounds = false;
			}
		}
		
		return isInsideBounds;
	}
}
