package Frogger;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Engine.Game;
import Engine.GamePanel;

public class MovingObjects {
	
	private float currentX;
	private Type type;
	
	public MovingObjects(Type type) {
		this.type = type;
		if(type.fromleft)
			currentX = GamePanel.width/2-FroggerState.getBackground().getWidth()/2;
		else
			currentX = GamePanel.width/2+FroggerState.getBackground().getWidth()/2;
	}
	
	public void render(Graphics2D g) {
		g.drawImage(type.image, (int) currentX, type.startY, null);
		
	}
	
	public void update() {
		if(type.fromleft && currentX > GamePanel.width/2+FroggerState.getBackground().getWidth()/2) {
			FroggerState.cars.remove(this);
		}
		else if(!type.fromleft && currentX+type.image.getWidth() < GamePanel.width/2-FroggerState.getBackground().getWidth()/2) {
			FroggerState.cars.remove(this);
		}
		if(type.fromleft) {
			currentX += type.speed;
		}
		else {
			currentX -= type.speed;
		}
	}
	
	public enum Type {
		Log1(Game.imageLoader.load("images/Frogger/log_0.png"), true, 345, 1.25f),
		Turtle2(Game.imageLoader.load("images/Frogger/turtle_1.png"), false, 234, 0.7f),
		Log3(Game.imageLoader.load("images/Frogger/log_0.png"), true, 234, 0.8f),
		Log4(Game.imageLoader.load("images/Frogger/log_0.png"), true, 345, 0.6f),
		Turtle5(Game.imageLoader.load("images/Frogger/turtle_1.png"), false, 345, 1f),
		
		Truck6(Game.imageLoader.load("images/Frogger/car_c_0.png"), false, 208, 0.5f),
		RaceCar7(Game.imageLoader.load("images/Frogger/car_a_0.png"), true, 227, 2f), 
		Pink8(Game.imageLoader.load("images/Frogger/car_b_0.png"), false, 250, 1f),
		Bagger9(Game.imageLoader.load("images/Frogger/car_d_0.png"), true, 269, 0.7f),
		Car10(Game.imageLoader.load("images/Frogger/car_e_0.png"), false, 290, 0.9f);
		
		public BufferedImage image;
		
		public boolean fromleft;
		public int startY;
		public float speed;
		
		private Type(BufferedImage image, boolean left, int startY, float speed) {
			this.image = image;
			this.fromleft = left;
			this.startY = startY;
			this.speed = speed;
		}
	}
}
