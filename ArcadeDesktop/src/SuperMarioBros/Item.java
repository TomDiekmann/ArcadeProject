package SuperMarioBros;

import java.awt.Graphics2D;

import Engine.Game;
import Engine.Spritesheet;

public class Item extends Entity {

	private static final Float spawningMovementY = 0.2F;

	private boolean spawning;
	private Type type;
	private int ticksSinceSpawning;

	public enum Type {
		Mushroom(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/Mushroom.png"), 1, 16, 16), 16, 16, 1.2f, new int[] { 1, 1, 1, 1 }), 
		Up_Mushroom(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/1Up-Mushroom.png"), 1, 16, 16), 16, 16, 1.2f, new int[] { 1, 1, 1, 1 }), 
		FireFlower(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/FireFlower.png"), 4, 16, 16), 16, 16, 0, new int[] { 3, 3, 3, 3 }), 
		Star(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/Star.png"), 4, 16, 16), 16, 16, 1.2f, new int[] { 3, 3, 3, 3 });

		public Spritesheet sprite;
		public int[] states;
		public int[] frames;
		public int width;
		public int height;
		public float speed;

		private Type(Spritesheet spritesheet, int width, int height, float speed, int[] frames) {
			sprite = spritesheet;
			int[] tmp = { 0, 0, 0, 0 };
			states = tmp;
			this.frames = frames;

			this.width = width;
			this.height = height;
			this.speed = speed;
		}

	}

	public Item(Type type, float x, float y) {
		super(type.sprite, x, y, type.width, type.height, type.speed, type.states, type.frames);
		this.type = type;
		spawning = true;
		ticksSinceSpawning = 0;
		right = true;
		if (type.equals(Type.FireFlower)) {
			stopMoving = true;
		}
	}

	public void update() {
		if (spawning) {
			y -= spawningMovementY;
			if (ticksSinceSpawning == 64) {
				spawning = false;
			}
			ticksSinceSpawning++;
		} else {
			if(type == Type.Star && (bottomLeft || bottomRight)) {
				dy = -5F;
			}
			super.update();
			if (x <= 1) {
				MarioWorldState.world.items.remove(this);
			}
		}
		if(midRight && !midLeft) {
			left = true;
			right = false;
		} else if(midLeft && !midRight) {
			right = true;
			left = false;
		} else if(midRight && midLeft) {
			stopMoving = true;
		}
			
	}

	public void render(Graphics2D g, int startX, int startY) {
		g.drawImage(animation.getImage(), (int) x - startX, (int) y - startY, null);

	}

	public Type getType() {
		return type;
	}
}
