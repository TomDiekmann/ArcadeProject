package SuperMarioBros;

import java.awt.Graphics2D;
import java.awt.Image;

import Engine.Spritesheet;
import Engine.Game;

public class RunningMonster extends Entity{
	
	private boolean lastLeft;
	private Type type;
	private int fadeTick = 0;
	
	public enum Type{
		GOOMBA(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/GoombaWalking.png"), 2, 16, 16), 16, 16, 1f),
		KOOPA_TROOPER(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/KoopaWalking.png"), 2, 16, 24), 16, 24, 1f),
		KOOOPA_SHELL(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/KoopaShell.png"), 2, 16, 14), 16, 14, 2f);
		
		public Spritesheet sprite;
		public int[] states;
		public int[] frames;
		public int width;
		public int height;
		public float speed;
		
		private Type(Spritesheet spritesheet, int width, int height,float speed) {
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
			this.speed = speed;
		}
	}
	
	
	public RunningMonster(Type type, float x, float y) {
		super(type.sprite, x, y, type.width, type.height, type.speed, type.states, type.frames);
		lastLeft = true;
		left = true;
		this.type = type;
		if(type == Type.KOOOPA_SHELL) {
			stopMoving = true;
		}
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
		if(fadeTick == 0 ) {
			g.drawImage(animation.getImage(),(int) x - startX,(int) y - startY, null);
		}
		else {
			g.drawImage(Game.imageLoader.load("images/SuperMarioBros/GoombaSmall.png"),(int) x - startX,(int) y - startY, null);
			fadeTick++;
			if(fadeTick > 120)
				MarioWorldState.world.enemies.remove(this);
		}
	}
	
	public void headHit() {
		if(type.equals(Type.GOOMBA)) {
			fadeTick++;
			stopMoving = true;
		}
		else if(type.equals(Type.KOOPA_TROOPER)) {
			MarioWorldState.world.enemies.remove(this);
			MarioWorldState.world.enemies.add(new RunningMonster(Type.KOOOPA_SHELL, x, y + 10));
		}
	}
	
	public boolean isDead() {
		return fadeTick != 0;
	}
	
	public boolean isStaticShell() {
		return type == Type.KOOOPA_SHELL && stopMoving == true;
	}
	
	public void startMoving(boolean left) {
		stopMoving = false;
		this.left = left;
		right = !left;
	}
	
}
