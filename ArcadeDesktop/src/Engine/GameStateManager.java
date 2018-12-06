package Engine;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Menu.MainState;
import snake.SnakeGameState;



public class GameStateManager {
	
	public static final int MAINSTATE = 0;
	public static final int MARIOMENU = 1;
	public static final int MARIOWORLD1 = 2;
	public static final int SNAKEGAMESTATE = 3;
	
	private State[] states;
	private int state;
	
	public GameStateManager(int state) {
		this.state = state;
		states = new State[4];
		states[0] = new MainState(this);	
		states[1] = new SuperMarioBros.MenuState(this);
		states[2] = new SuperMarioBros.Playstate(this, "files/SuperMarioBros/world1.txt");
		states[3] = new SnakeGameState(this);
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
		if(state == MARIOWORLD1) {
			Game.gamepanel.SCALE = 2;
			Game.gamepanel.scaleChanged();
		}
		this.state = state;
	}
	
}