package SuperMarioBros;

import java.awt.Graphics2D;

import Engine.Spritesheet;
import Engine.Game;

public class Monsters extends Entity{
	
	public enum type{
		GOOMBA(new Spritesheet(Game.imageLoader.load("images/goomba.png"), 1, 1, 1));
		
		public Spritesheet sprite; 
		
		private type(Spritesheet spritesheet) {
			sprite = spritesheet;
		}
	}
	public Monsters(Spritesheet spritesheet, float x, float y, int width, int height, float speed) {
		super(spritesheet, x, y, width, height, speed);
		
	}
	
	public void update() {
		
	}
	
	public void render(Graphics2D g) {
		
	}
	
}
