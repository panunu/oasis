package engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import anatomy.Creature;
import creature.Player;
import map.Terrain;
import item.Inventory;

public class Component extends JPanel {
	private int cs = 10, ls = 14; // character spacing, line spacing
	private int fontSize = 14;
	private String fontFace = "Courier";
	
	private final Font FONT_PLAIN = new Font("Courier", Font.PLAIN, 14);
	private final Font FONT_BOLD = new Font("Courier", Font.BOLD, 14);
	private final Font FONT_CONSOLE = new Font("Courier", Font.BOLD, 13);

	public Component() {}
	
	public Image getBackgroundRectangle(int width, int height) {
		BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		buffer.createGraphics();
		Graphics2D gdd = (Graphics2D) buffer.getGraphics();
		
		gdd.setColor(Color.black);
		gdd.drawRect(0, 0, width, height);		
		
		return buffer;
	}
	
	public Image getConsole(String console) {
		BufferedImage buffer = new BufferedImage(780, 55, BufferedImage.TYPE_INT_RGB);
		buffer.createGraphics();
		Graphics2D gdd = (Graphics2D) buffer.getGraphics();
		
		gdd.setColor(Color.white);
		gdd.setFont(FONT_CONSOLE);		
		
		if(console.length() > 95) {
			String firstline = console.substring(0, 95) + "\n";
			String secondline = console.substring(95);
			
			gdd.drawString(firstline, cs, ls);
			gdd.drawString(secondline, cs, ls * 2);
		}
		else
			gdd.drawString(console, cs, ls);
		
		return buffer;
	}
	
	public Image getHealth(int health, int total) {
		BufferedImage buffer = new BufferedImage(780, 55, BufferedImage.TYPE_INT_RGB);
		buffer.createGraphics();
		Graphics2D gdd = (Graphics2D) buffer.getGraphics();
		
		gdd.setFont(FONT_CONSOLE);
		gdd.setColor(Color.lightGray);
		gdd.drawString("HP: " + health + "(" + total + ")", cs, ls);
		
		return buffer;
	}
	
	public Image getStamina(int stamina, int total) {
		BufferedImage buffer = new BufferedImage(780, 55, BufferedImage.TYPE_INT_RGB);
		buffer.createGraphics();
		Graphics2D gdd = (Graphics2D) buffer.getGraphics();
		
		gdd.setFont(FONT_CONSOLE);
		gdd.setColor(Color.lightGray);
		gdd.drawString("STA: " + stamina + "(" + total + ")", cs, ls);
		
		return buffer;
	}
	
	public Image getMap(char[][] map, Player hero, int mapX, int mapY) {		
		BufferedImage buffer = new BufferedImage(785, 457, BufferedImage.TYPE_INT_RGB);
		buffer.createGraphics();
		Graphics2D gdd = (Graphics2D) buffer.getGraphics();
			
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				if(hero.hasVisual(j, i, map) || hero.hasPerception(j, i)) {
					hero.addRevealed(j, i);
					
					int a = !hero.hasVisual(j, i, map)?125:255; // 150
					int r = Terrain.getTerrainByKey(map[i][j]).getColor().getRed();					
					int g = Terrain.getTerrainByKey(map[i][j]).getColor().getGreen();
					int b = Terrain.getTerrainByKey(map[i][j]).getColor().getBlue();
					gdd.setColor(new Color(r, g, b, a));
					
					if(Terrain.getTerrainByKey(map[i][j]).isBold())
						gdd.setFont(FONT_CONSOLE);
					else
						gdd.setFont(FONT_PLAIN);
					gdd.drawString(Character.toString(Terrain.getTerrainByKey(map[i][j]).getAvatar()), j * cs + mapX, i * ls + mapY);
				}
			}
		}
				
		return buffer;
	}
	
	public Image getCreature(Creature creature, Creature player, char[][] map) {
		BufferedImage buffer = new BufferedImage(cs, ls, BufferedImage.TYPE_INT_RGB);
		buffer.createGraphics();
		Graphics2D gdd = (Graphics2D) buffer.getGraphics();
		
		gdd.setFont(FONT_BOLD);
		gdd.setColor(creature.getColor());
		gdd.drawString(Character.toString(creature.getRace().getAvatar()), 0, 9);
		
		return buffer;
	}
	
	public Image getTarget() {
		BufferedImage buffer = new BufferedImage(cs, ls, BufferedImage.TYPE_INT_RGB);
		buffer.createGraphics();
		Graphics2D gdd = (Graphics2D) buffer.getGraphics();
		
		gdd.setFont(FONT_BOLD);
		gdd.setColor(Color.red);
		gdd.drawString("X", 0, 9);
		
		return buffer;
	}
	
	public Image getInventory(Inventory inventory) {		
		BufferedImage buffer = new BufferedImage(785, 457, BufferedImage.TYPE_INT_RGB);
		buffer.createGraphics();
		Graphics2D gdd = (Graphics2D) buffer.getGraphics();
		
		gdd.setColor(Color.lightGray);
		gdd.setFont(FONT_BOLD);		
		for(int i = 0; i < inventory.getContents().size(); i++) {
			gdd.drawString(inventory.getContents().get(i).getName() + ", " +
							inventory.getContents().get(i).getType() + " " +
							inventory.getContents().get(i).getValue() + " gp " + 
							(inventory.getEquippedWeapon()), cs, ls * (i + 1));
		}
		
		return buffer;
	}

	public int getCs() {
		return cs;
	}
	
	public int getLs() {
		return ls;
	}
	
	private void setAliasing(Graphics2D gdd) {
		gdd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
}