package Tron;

import java.awt.Color;
import java.awt.Graphics;

public class BodyPart {
	private int posX, posY;
	private Color color;

	public BodyPart(int posX, int posY, Color color) {
		this.posX = posX;
		this.posY = posY;
		this.color = color;
	}

	public void draw(Graphics graphics) {
		graphics.setColor(color);
		graphics.fillRect(this.posX * TronState.TILE_SIZE, this.posY * TronState.TILE_SIZE, TronState.TILE_SIZE,
				TronState.TILE_SIZE);
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
