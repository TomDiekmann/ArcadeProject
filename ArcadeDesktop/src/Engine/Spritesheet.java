package Engine;

import java.awt.image.BufferedImage;

public class Spritesheet {

	private int width;
	private int height;
	private int col;
	private BufferedImage sprite;
	
	public Spritesheet(BufferedImage sprite, int col, int width, int height) {
		this.width = width;
		this.height = height;
		this.col = col;
		this.sprite = sprite;
	}
	
	
	public BufferedImage getTexture(int id) {
		int y = (id/col);
		int x = (id%col);
		return sprite.getSubimage(x * width, y * height, width, height);
	}
	
	public BufferedImage getTexture(int col, int row) {
		return sprite.getSubimage(col * width, row * height, width, height);
	}
	
	public int getCols() {
		return col;
	}
}

