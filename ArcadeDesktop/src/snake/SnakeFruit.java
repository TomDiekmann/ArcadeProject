package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class SnakeFruit {
	private int posX, posY;
	private FruitType type = FruitType.SMALL;

	public SnakeFruit(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;

		Random rand = new Random();
		int randInt = rand.nextInt(100);
		if (randInt < 70)
			this.type = FruitType.SMALL;
		else if (randInt < 90)
			this.type = FruitType.MEDIUM;
		else
			this.type = FruitType.LARGE;
	}

	public void draw(Graphics graphics) {
		graphics.setColor(new Color(48, 98, 48));

		switch (this.type) {
		case SMALL:
			graphics.fillRect(this.posX * SnakeGameState.TILE_SIZE + SnakeGameState.TILE_SIZE / 2 - 3, this.posY * SnakeGameState.TILE_SIZE, 6, SnakeGameState.TILE_SIZE);
			graphics.fillRect(this.posX * SnakeGameState.TILE_SIZE, this.posY * SnakeGameState.TILE_SIZE + SnakeGameState.TILE_SIZE / 2 - 3, SnakeGameState.TILE_SIZE, 6);
			break;

		case MEDIUM:
			graphics.fillRoundRect(this.posX * SnakeGameState.TILE_SIZE, this.posY * SnakeGameState.TILE_SIZE, SnakeGameState.TILE_SIZE, SnakeGameState.TILE_SIZE, 10, 10);
			graphics.setColor(new Color(139, 172, 15));
			graphics.fillRoundRect(this.posX * SnakeGameState.TILE_SIZE + 4, this.posY * SnakeGameState.TILE_SIZE + 4, SnakeGameState.TILE_SIZE - 8, SnakeGameState.TILE_SIZE - 8, 10, 10);
			break;

		case LARGE:
			graphics.fillRoundRect(this.posX * SnakeGameState.TILE_SIZE, this.posY * SnakeGameState.TILE_SIZE, SnakeGameState.TILE_SIZE, SnakeGameState.TILE_SIZE, 10, 10);
			break;
		}
	}

	public int getScore() {
		switch (this.type) {
		case SMALL:
			return 1;

		case MEDIUM:
			return 10;

		case LARGE:
			return 50;

		default:
			return 1;
		}
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

	public enum FruitType {
		SMALL, MEDIUM, LARGE
	}
}
