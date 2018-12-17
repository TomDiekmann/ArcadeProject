package Menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Engine.GamePanel;

public class GameView {
	private String name;
	private int state;
	private boolean selected;
	private BufferedImage gameCover;
	private BufferedImage gameScreen;
	private ArrayList<String> tutorial;

	public GameView(String name, int state, BufferedImage gameCover, BufferedImage gameScreen, ArrayList<String> tutorial) {
		this.name = name;
		this.state = state;
		selected = false;
		this.gameCover = gameCover;
		this.gameScreen = gameScreen;
		this.tutorial = tutorial;
	}

	public void render(Graphics2D g, int x) {
		int y = GamePanel.height -100;
		g.drawImage(gameCover, x, 230,null);
		if(selected) {
			g.setColor(Color.red);
			g.setStroke(new BasicStroke(2));
			g.drawRect(x-2, 228, gameCover.getWidth()+4, gameCover.getHeight()+4);
			g.drawImage(gameScreen, 10, 10, null);
			g.setColor(Color.white);
			g.drawRect(10, 10, gameScreen.getWidth(), gameScreen.getHeight());
			g.setFont(new Font("Arial Black",1,25));
			g.drawString(name, 20 + gameScreen.getWidth(), 30);
			g.setFont(new Font("Arial Black",1,15));
			int textY = 50;
			for(int i = 0; i < tutorial.size(); i++) {
				g.drawString(tutorial.get(i), 20+gameScreen.getWidth(), textY);
				textY += 15;
			}
			
			if (state == GameStateManager.SNAKEGAMESTATE)
				g.drawString("Highscore: " + SnakeGameState.HIGHSCORE, 20+gameScreen.getWidth(), textY + 15);
		}
		
	}

	public String getName() {
		return name;
	}

	public int getState() {
		return state;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public BufferedImage getCover() {
		return gameCover;
	}
}
