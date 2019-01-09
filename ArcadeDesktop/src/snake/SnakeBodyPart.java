package snake;

import java.awt.Color;
import java.awt.Graphics;

public class SnakeBodyPart {
	private int posX, posY, tileSize;

	public SnakeBodyPart(int posX, int posY, int tileSize) {
		this.posX = posX;
		this.posY = posY;
		this.tileSize = tileSize;
	}

	public void draw(Graphics graphics) {
		graphics.setColor(new Color(48, 98, 48));
		graphics.fillRoundRect(this.posX, this.posY, this.tileSize, this.tileSize, 10, 10);
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
