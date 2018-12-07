package SuperMarioBros;

import java.awt.Graphics2D;
import java.awt.Image;

import Engine.Spritesheet;
import Engine.Game;

public class RunningMonster extends Entity{
	
	private boolean lastLeft;
	
	public enum Type{
		GOOMBA(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/GoombaWalking.png"), 2, 16, 16), 16, 16),
		KOOPA_TROOPER(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/KooperWalking.png"), 2, 16, 24), 16, 24);
		
		
		public Spritesheet sprite;
		public int[] states;
		public int[] frames;
		public int width;
		public int height;
		public float speed;
		
		private Type(Spritesheet spritesheet, int width, int height) {
			sprite = spritesheet;
			if(sprite.getSprite().getHeight()>height) {
				int tmp[] = {0,1,0,1};
				states = tmp;
			}
			else { 
				int tmp[] = {0,0,0,0};
				states = tmp;
			}
			int tmp[] = {2,2};
			frames = tmp;
			this.width = width;
			this.height = height;
			speed = 1f;
		}
	}
	
	
	public RunningMonster(Type type, float x, float y) {
		super(type.sprite, x, y, type.width, type.height, type.speed, type.states, type.frames);
		lastLeft = true;
		left = true;
	}
	
	public void update() {
		super.update();
		if(saveCollision) {
			saveCollision = false;
			left = false;
			right = false;
		}
		if(lastLeft == true && left == false && right == false) {
			right = true;
			lastLeft = false;
		}
		else if(lastLeft == false && left == false && right == false) {
			left = true;
			lastLeft = true;
		}
	}
	
	public void render(Graphics2D g, int startX, int startY) {
		g.drawImage(animation.getImage(),(int) x - startX,(int) y - startY, null);
	}
	
}
