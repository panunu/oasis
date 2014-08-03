package engine;

import java.util.ArrayList;

import map.Map;
import maps.*;
import anatomy.*;
import creature.*;
import skill.*;
import item.*;

public class Logic {
	Map testi = new Map("Test map", "Nothing to be afraid of here.", "test2");
	Player hero = new Player("Tarkovsky", Race.human, Faction.resistance, 32, 12);
		
	String action = null;
	String console = "";
	State state = State.GAME;
	
	int cursorX, cursorY;
	
	public Logic() {
		hero.addSkill(Skill.use_door);
		//hero.addSkill(Skill.swimming);
		
		hero.addItem(Weapon.climbing_hook);
		hero.addItem(Weapon.ak47);
		hero.addItem(Ammo.nato762);
		hero.getInventory().getContents().get(1).equip();
		hero.getInventory().getEquippedWeapon().setCondition(1.0);
		hero.addItem(Armor.leather_armor);	
	}
	
	public void run(String action, char key) {
		boolean turn = true;
		
		if(action == null) {
			if(state == State.GAME) {
				switch(key) {
				case '1': 
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					move(key);
					break;
				case 'a':
					attack(key, 0);
					turn = false;
					break;
				case 'c':
					closeDoor(key, 0);
					turn = false;
					break;
				case 'i':
					checkInventory();
					turn = false;
					break;
				case 'o':
					openDoor(key, 0);
					turn = false;
					break;
				case 's':
					checkSurroundings();
					break;
				}
			}
			else if(state == State.INVENTORY) {
				turn = false;
				switch(key) {
				case '\u001B':
					state = State.GAME;
				}
			}
		}
		else if(action.equals("opendoor")) {
			openDoor(key, 1);
		}
		else if(action.equals("closedoor")) {
			closeDoor(key, 1);
		}
		else if(action.equals("attack")) {
			switch(key) {
			case 'a':
				attack(key, 2);
			break;
			default:
				attack(key, 1);
				turn = false;
			break;
			}
		}
		
		if(turn) {
			int numberOfCreatures = testi.getCreatures().size();
			for(int i = 0; i < numberOfCreatures; i++) {
				addMsgToConsole(testi.getCreatures().get(i).isDead());
				if(testi.getCreatures().get(i).isDead() != null) {
					testi.getCreatures().remove(i);
					numberOfCreatures = testi.getCreatures().size();
				}
			}
			
			runAI();
		}
	}
	
	public String getAction() {
		String act = action;
		action = null;
		
		return act;		
	}
	
	public void addAction(String action) {
		this.action = action;
	}
	
	private void addMsgToConsole(String msg) {
		if(msg != null)
			console += msg + " ";
	}
	
	private int getHorizontal(char key) {
		switch(key) {
		case '1':
			return Creature.LEFT;
		case '2':
			return 0;
		case '3':
			return Creature.RIGHT;
		case '4':
			return Creature.LEFT;
		case '6':
			return Creature.RIGHT;
		case '7':
			return Creature.LEFT;
		case '8':
			return 0;
		case '9':
			return Creature.RIGHT;
		default:
			return 0;
		}
	}
	
	private int getVertical(char key) {
		switch(key) {
		case '1':
			return Creature.DOWN;
		case '2':
			return Creature.DOWN;
		case '3':
			return Creature.DOWN;
		case '4':
			return 0;
		case '6':
			return 0;
		case '7':
			return Creature.UP;
		case '8':
			return Creature.UP;
		case '9':
			return Creature.UP;
		default:
			return 0;
		}
	}
	
	/*
	 * User actions
	 */
	
	private void attack(char key, int phase) {
		if(phase == 0) {
			cursorX = hero.getX();
			cursorY = hero.getY();
			addAction("attack");
			addMsgToConsole("Attack [aim] ");
		}
		else if(phase == 1) {
			cursorX += getHorizontal(key);
			cursorY += getVertical(key);
			
			if(hero.getDistance(cursorX, cursorY) >= (hero.getInventory().getEquippedWeapon().getRange())) {
				cursorX -= getHorizontal(key);
				cursorY -= getVertical(key);
			}
			
			addAction("attack");
			addMsgToConsole("Attack [aim] ");
		}
		else if(phase == 2) {
			addMsgToConsole(hero.attack(cursorX, cursorY, testi.getArray(), testi.getCreatures()));
		}
	}	
	
	private void move(char key) {
		addMsgToConsole(hero.move(getHorizontal(key), getVertical(key), testi.getArray(), testi.getCreatures()));
	}
	
	private void checkInventory() {
		state = State.INVENTORY;
	}
	
	private void openDoor(char key, int phase) {
		if(phase == 0) {
			addAction("opendoor");
			addMsgToConsole("Open a door. (Which direction?) ");
		}
		else if(phase == 1)
			addMsgToConsole(hero.openDoor(getHorizontal(key), getVertical(key), testi.getArray()));
	}
	
	private void closeDoor(char key, int phase) {
		if(phase == 0) {
			addAction("closedoor");
			addMsgToConsole("Close a door. (Which direction?) ");
		}
		else if(phase == 1) 
			addMsgToConsole(hero.closeDoor(getHorizontal(key), getVertical(key), testi.getArray()));
	}
	
	private void checkSurroundings() {
		addMsgToConsole("You check your surroundings. " + hero.getSurroundings(testi.getArray()));
	}
	
	private void runAI() {
		for(int i = 0; i < testi.getCreatures().size(); i++) {
			testi.getCreatures().get(i).engage(testi.getArray(), testi.getCreatures(), hero);
		}
	}
}