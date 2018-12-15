package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Engine.Game;
import Engine.GamePanel;
import Engine.GameStateManager;
import Engine.ImageLoader;
import Engine.State;

public class MainState extends State {

	private ArrayList<GameView> games = new ArrayList<GameView>();
	private int selectedGame;
	private final int COVER_WIDTH = 73;
	

	public MainState(GameStateManager gsm) {
		super(gsm);
		games.add(new GameView("Super Mario Bros", GameStateManager.MARIOMENU, Game.imageLoader.load("images/Menu/marioCover.png"), Game.imageLoader.load("images/Menu/marioGameScreen.png"), getListFromFile("files/Menu/marioTutorial.txt")));
		games.add(new GameView("Snake", GameStateManager.SNAKEGAMESTATE, Game.imageLoader.load("images/Menu/snakeCover.png"), Game.imageLoader.load("images/Menu/snakeGameScreen.png"), getListFromFile("files/Menu/snakeTutorial.txt")));
		games.add(new GameView("Pong", GameStateManager.PONGSTATE, Game.imageLoader.load("images/Menu/pongCover.png"), Game.imageLoader.load("images/Menu/pongGameScreen.png"), getListFromFile("files/Menu/pongTutorial.txt")));
		games.add(new GameView("Frogger", GameStateManager.FROGGERSTATE, Game.imageLoader.load("images/Menu/froggerCover.png"), Game.imageLoader.load("images/Menu/froggerGameScreen.png"), getListFromFile("files/Menu/froggerTutorial.txt")));
		games.add(new GameView("Tron", GameStateManager.TRONSTATE, Game.imageLoader.load("images/Menu/tronCover.png"), Game.imageLoader.load("images/Menu/tronGameScreen.png"), getListFromFile("files/Menu/tronTutorial.txt")));
		games.get(0).setSelected(true);
		selectedGame = 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(120,118,240));
		g.fillRect(0, 0, GamePanel.width, GamePanel.height);
		g.setColor(Color.lightGray);
		g.fillRect(0, 225, GamePanel.width, 110);
		g.setColor(Color.WHITE);
		g.fill3DRect(0, 220, GamePanel.width, 5, true);
		g.fill3DRect(0, 335, GamePanel.width, 5, true);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 340, GamePanel.width, 20);
		
		int x = GamePanel.width / 2 -  COVER_WIDTH / 2;
		int game = selectedGame;
		games.get(selectedGame).render(g, x);
		while(x + COVER_WIDTH > 0) {
			x = (x-COVER_WIDTH) - 22;
			if(game - 1 < 0)
				game = games.size();
			game--;
			games.get(game).render(g, x);
		}
		x = GamePanel.width / 2 -  COVER_WIDTH / 2;
		game = selectedGame;
		while(x < GamePanel.width) {
			x += COVER_WIDTH + 20;
			if(game + 1 == games.size()) 
				game = -1;
			game++;
			games.get(game).render(g, x);
		}
	}

	@Override
	public void keyPressed(KeyEvent e, int k) {
		switch (k) {
		case KeyEvent.VK_ENTER:
			Game.gamepanel.gsm.setState(games.get(selectedGame).getState());
			break;
		case KeyEvent.VK_LEFT:
			games.get(selectedGame).setSelected(false);
			if (selectedGame - 1 < 0)
				selectedGame = games.size() - 1;
			else
				selectedGame--;
			games.get(selectedGame).setSelected(true);
			break;
		case KeyEvent.VK_RIGHT:
			games.get(selectedGame).setSelected(false);
			if (selectedGame + 1 > games.size() - 1)
				selectedGame = 0;
			else
				selectedGame++;
			games.get(selectedGame).setSelected(true);
			break;
		}
	}
	
	public ArrayList<String> getListFromFile(String path){
		File file = new File(path);
		ArrayList<String> ret = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while(true) {
				String line = reader.readLine();
				if(line == null)
					break;
				ret.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
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

	@Override
	public void stateEnd() {
		// TODO Auto-generated method stub
		
	}

}
