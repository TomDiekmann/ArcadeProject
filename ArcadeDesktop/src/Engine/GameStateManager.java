package Engine;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Frogger.FroggerState;
import Menu.MainState;
import snake.SnakeGameState;
import SuperMarioBros.MenuState;
import SuperMarioBros.MarioWorldState;
import pong.PongState;
import Tron.TronState;

public class GameStateManager {

	public static final int MAINSTATE = 0;
	public static final int MARIOMENU = 1;
	public static final int MARIOWORLD = 2;
	public static final int SNAKEGAMESTATE = 3;
	public static final int PONGSTATE = 4;
	public static final int FROGGERSTATE = 5;
	public static final int TRONSTATE = 6;

	private State[] states;
	private int state;

	public GameStateManager(int state) {
		this.state = state;
		states = new State[7];
		states[0] = new MainState(this);
		states[1] = new MenuState(this);
		states[2] = new MarioWorldState(this, "files/SuperMarioBros/world1.txt");
		states[3] = new SnakeGameState(this);
		states[4] = new PongState(this);
		states[5] = new FroggerState(this);
		states[6] = new TronState(this);
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
		if (state == MARIOMENU || state == MARIOWORLD) {
			GamePanel.SCALE = 2;
			Game.gamepanel.scaleChanged();
			states[MARIOWORLD] = new MarioWorldState(this, "files/SuperMarioBros/world1.txt");
		}
		if (state == SNAKEGAMESTATE) {
			states[SNAKEGAMESTATE] = new SnakeGameState(this);
		}
		this.state = state;
	}
	
	public State getState(int state) {
		return states[state];
	}
	
	public int getActiveState() {
		return state;
	}

}
