package Menu;

import java.awt.Color;
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

	private ArrayList<GameView> games = new ArrayList<GameView>();
	private GameView selectedGame;

	public MainState(GameStateManager gsm) {
		super(gsm);
		selectedGame = new GameView("Super Mario Bros", GameStateManager.MARIOMENU);
		selectedGame.setSelected(true);
		games.add(selectedGame);
		games.add(new GameView("Snake", GameStateManager.SNAKEGAMESTATE));
		games.add(new GameView("Pong", GameStateManager.PONGSTATE));
		games.add(new GameView("Frogger", GameStateManager.FROGGERSTATE));
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, GamePanel.width, GamePanel.height);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", 1, 15));
		String instructions = "ArrowKeys to switch Games, Enter to play";
		g.drawString(instructions, (GamePanel.width - g.getFontMetrics().stringWidth(instructions)) / 2,
				GamePanel.height - 20);

		int amount = games.size();
		int midY = GamePanel.height / (amount + 1);
		for (int i = 0; i < games.size(); i++) {
			games.get(i).render(g, midY);
			midY += GamePanel.height / (amount + 1);
		}
	}

	@Override
	public void keyPressed(KeyEvent e, int k) {
		switch (k) {
		case KeyEvent.VK_ENTER:
			Game.gamepanel.gsm.setState(selectedGame.getState());
			break;
		case KeyEvent.VK_UP:
			selectedGame.setSelected(false);
			if (games.indexOf(selectedGame) - 1 < 0)
				selectedGame = games.get(games.size() - 1);
			else
				selectedGame = games.get(games.indexOf(selectedGame) - 1);
			selectedGame.setSelected(true);
			break;
		case KeyEvent.VK_DOWN:
			selectedGame.setSelected(false);
			if (games.indexOf(selectedGame) + 1 > games.size() - 1)
				selectedGame = games.get(0);
			else
				selectedGame = games.get(games.indexOf(selectedGame) + 1);
			selectedGame.setSelected(true);
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
