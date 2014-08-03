package map;

import java.io.BufferedReader;
import java.io.FileReader;

import engine.Error;

public class MapLoader {
	//private char[][] map = new char[45][78];
	private char[][] map = new char[20][78];
	private String dir = "maps/";
	
	public MapLoader(String file) {
		for(int i = 0; i < map.length; i++)
			for(int j = 0; j < map[i].length; j++)
				map[i][j] = ' ';
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(dir + file + ".map"));
			
			String line = null;
			for(int i = 0; (line = reader.readLine()) != null && i < map.length; i++) {
				for(int j = 0; j < line.length() && j < map[i].length; j++) {
					try {
						map[i][j] = line.charAt(j);
					}
					catch (Exception e) {
						Error.fail(e, this.getClass().toString());
						map = null;
					}
				}
			}
		}
		catch (Exception e) {
			Error.fail(e, this.getClass().toString());
		}
	}
	
	public char[][] getMap() {
		return map;
	}
	
	public String toString() {
		String mapString = "";
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++)
				mapString += map[i][j];
			mapString += "\n";
		}
		
		return mapString;
	}
}