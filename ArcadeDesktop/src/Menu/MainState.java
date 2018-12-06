package Menu;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Engine.Game;
import Engine.GamePanel;
import Engine.GameStateManager;
import Engine.State;

public class MainState extends State {
	
	private ArrayList<GameView> games = new ArrayList();
	private GameView selectedGame; 

	public MainState(GameStateManager gsm) {
		super(gsm);
		selectedGame = new GameView("Super Mario Bros", GameStateManager.MARIOWORLD1, GamePanel.height / 2);
		selectedGame.setSelected(true);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g) {
		g.setFont(new Font("Arial", 1 , 15));
		String instructions = "ArrowKeys to switch Games, Enter to play";
		g.drawString(instructions, (GamePanel.width - g.getFontMetrics().stringWidth(instructions)) / 2, GamePanel.height - 20);
		selectedGame.render(g);
	}

	@Override
	public void keyPressed(KeyEvent e, int k) {
		switch(k){
		case KeyEvent.VK_ENTER: Game.gamepanel.gsm.setState(selectedGame.getState());
		break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e, int k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
