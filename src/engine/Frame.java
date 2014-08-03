package engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Frame extends JFrame {	
	private int width = 800, height = 400;
	private Logic logic = new Logic();
	private Component component = new Component();
	
	private Graphics bufferGraphics;
	private Image bufferImage;
	
	public Frame() {
		initWindow();
		initGraphics();
		
		while(true) {			
			while(!Action.hasKey()) {
				try {
					Thread.sleep(50);
				}
				catch(Exception e) {}
			}
			
			logic.run(logic.getAction(), Action.getKeyCode());
			repaint();
		}
	}
	
	public void initWindow() {
		this.setTitle("rogue");
		
		this.setSize(width, height);
		this.setLocation(100, 100);
		this.setResizable(false);
		
		this.addKeyListener(new Action());
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void initGraphics() {
		bufferImage = createImage(width, height);
	    bufferGraphics = bufferImage.getGraphics();
	}
	
	public void paint(Graphics g) {
		if(bufferImage == null)
			initGraphics();
		
		bufferGraphics.drawImage(component.getBackgroundRectangle(width, height), 0, 0, null);
		
		if(logic.console != "") {
			bufferGraphics.drawImage(component.getConsole(logic.console), 5, 25, null);
			logic.console = "";
		}
		
		if(logic.state == State.GAME) {
			bufferGraphics.drawImage(component.getMap(logic.testi.getArray(), logic.hero, 5, 12), 7, 60, null);
			bufferGraphics.drawImage(component.getCreature(logic.hero, logic.hero, logic.testi.getArray()), logic.hero.getX() * component.getCs() + 12, logic.hero.getY() * component.getLs() + 63, null);
			
			for(int i = 0; i < logic.testi.getCreatures().size(); i++)
				if(logic.hero.hasVisual(logic.testi.getCreatures().get(i).getX(), logic.testi.getCreatures().get(i).getY(), logic.testi.getArray()))
					bufferGraphics.drawImage(component.getCreature(logic.testi.getCreatures().get(i), logic.hero, logic.testi.getArray()), logic.testi.getCreatures().get(i).getX() * component.getCs() + 12, logic.testi.getCreatures().get(i).getY() * component.getLs() + 63, null);
			
			if(logic.action == "attack") {
				bufferGraphics.drawImage(component.getTarget(), logic.cursorX * component.getCs() + 12, logic.cursorY * component.getLs() + 63, null);
			}
			
			bufferGraphics.drawImage(component.getHealth(logic.hero.getHealth(), logic.hero.getTotalHealth()), 5, 350, null);
			bufferGraphics.drawImage(component.getStamina(logic.hero.getStamina(), logic.hero.getTotalStamina()), 5, 370, null);
		}
		
		if(logic.state == State.INVENTORY) {
			bufferGraphics.drawImage(component.getInventory(logic.hero.getInventory()), 7, 60, null);
		}
		
		g.drawImage(bufferImage, 0, 0, null);
	}
	
	public static void main(String[] args) {
		Frame frame = new Frame();
	}
}