package Engine;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Menu.MenuState;



public class GameStateManager {
	
	public static final int MENUSTATE = 0;
	
	private State[] states;
	private int state;
	
	public GameStateManager(int state) {
		this.state = state;
		states = new State[2];
		states[0] = new MenuState(this);	

		
	}
	
	
	
	
	public void update() {
		states[state].update();
	}
	
	public void render(Graphics2D g) {
		states[state].render(g);
	}
	
	public void keyPressed(KeyEvent e, int k) {
		states[state].keyPressed(e, k);
	}

	public void keyReleased(KeyEvent e, int k) {
		states[state].keyReleased(e, k);
	}
	
	public void mousePressed(MouseEvent e) {
		states[state].mousePressed(e);
	}
	
	public void mouseReleased(MouseEvent e) {
		states[state].mouseReleased(e);
	}
	
	public void mouseMoved(MouseEvent e) {
		states[state].mouseMoved(e);
	}
	
	public void setState(int state) {
		this.state = state;
	}
}