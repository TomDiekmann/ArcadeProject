package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Engine.GamePanel;

public class GameView {
	private String name;
	private int state;
	private int midY;
	private boolean selected;
	
	public GameView(String name, int state, int midY) {
		this.name = name;
		this.state = state;
		this.midY = midY;
		selected = false;
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.black);
		if(selected) g.setColor(Color.RED);
		g.setFont(new Font("Arial Black", 1, 20));
		int x = (GamePanel.width - g.getFontMetrics().stringWidth(name)) / 2;
		int y = midY - g.getFontMetrics().getHeight() / 2;
		g.drawString(name, x, y);
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
}
