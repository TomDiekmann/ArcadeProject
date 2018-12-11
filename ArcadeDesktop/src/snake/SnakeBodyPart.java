package snake;

import java.awt.Color;
import java.awt.Graphics;

public class SnakeBodyPart {
	private int posX, posY;

	public SnakeBodyPart(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public void draw(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(this.posX * SnakeGameState.TILE_SIZE, this.posY * SnakeGameState.TILE_SIZE,
				SnakeGameState.TILE_SIZE, SnakeGameState.TILE_SIZE);
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
}
