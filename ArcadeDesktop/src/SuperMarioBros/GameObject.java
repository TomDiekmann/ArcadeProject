package SuperMarioBros;

import java.awt.Rectangle;


public abstract class GameObject {

	protected float x;
	protected float y;
	protected int width;
	protected int height;
	
	public GameObject (float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Rectangle getBox() {
		Rectangle rectangle = new Rectangle();
		rectangle.setBounds((int)x, (int)y, width, height);
		return rectangle;
	}
	
	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setBounds(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public float getCenterX() {
		return x + width / 2;
	}
	
	public float getCenterY() {
		return y + height / 2;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
