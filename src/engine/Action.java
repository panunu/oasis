package engine;

import java.awt.event.*;

public class Action implements KeyListener {
	private static String keyString;
	private static char keyChar = '\0';
	
	public static String getKeyString() {
		String key = keyString;
		keyString = null;
		
		return key;
	}
	
	public static char getKeyCode() {
		char key = keyChar;
		keyChar = '\0';
		
		return key;
	}
	
	public static boolean hasKey() {
		if(keyChar != '\0')
			return true;
		else
			return false;
	}
	
	public void keyPressed(KeyEvent e) {
		keyChar = e.getKeyChar();
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}	

}
