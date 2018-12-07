package SuperMarioBros;

import java.awt.Color;
import java.awt.Graphics2D;

import Engine.Animation;
import Engine.Spritesheet;


public abstract class Entity extends GameObject {
	
	private float health = 10;
	
		//CONSTANTS
		private final float GRAVITY = 0.2F;
		private final float MAX_FALLING_SPEED = 2.5F;
		private final float JUMP_START = -5.0F;
		
		
		
		//MOVEMENT
		protected float dx;
		protected float dy;
		protected float speed;
		
		protected boolean right;
		protected boolean left;
		protected boolean falling;
		protected boolean hasFallen;
		protected boolean jumping;
		
		protected boolean inWater;
		
		//COLLISION
		protected boolean topLeft;
		protected boolean topRight;
		protected boolean bottomLeft;
		protected boolean bottomRight;
		protected boolean midLeft;
		protected boolean midRight;
		protected boolean hasfallen;
		
		protected boolean saveCollision;
		
		//ANIMATION
		private int idle;
		private int IDLE_LEFT;
		private int IDLE_RIGHT;
		private int LEFT;
		private int RIGHT;
		private int[] frames;
		protected Spritesheet sprite;
		protected Animation animation;
		
		public Entity(Spritesheet spritesheet, float x, float y, int width, int height, float speed, int[] states, int[] frames) {
			super(x, y, width, height);
			IDLE_LEFT = states[0];
			IDLE_RIGHT = states[1];
			LEFT = states[2];
			RIGHT = states[3];
			this.frames = frames;
			this.speed = speed;
			this.sprite = spritesheet;
			this.idle = IDLE_LEFT;
			this.animation = new Animation(spritesheet, 150L, LEFT, frames[LEFT]);
		}
		
		public void update() {
			calculateMovement(); //1
			calculateCollisions(); //2
			calculateAnimations();
			move(); //3
		}
		
		public void render(Graphics2D g) {
			g.setColor(Color.RED);
			g.fillRect((int)x, (int)y, width, height);
			g.drawImage(animation.getImage(), (int)x, (int)y, null);
		}
		
		private void calculateCollisions() {
			World world = Playstate.world;
			float tox = x + dx;
			float toy = y + dy;
			
			calculateCorners(tox, y - 1);
			if(dx < 0) {
				if(topLeft || bottomLeft || midLeft) {
					dx = 0;
					saveCollision = true;
				}
			}
			
			if(dx > 0) {
				if(topRight || bottomRight || midRight) {
					dx = 0;
					saveCollision = true;
				}
			}
			
			calculateCorners(x, toy);
			if(topLeft || topRight) {
				dy = 0;
				falling = true;
				int playerrow = Playstate.world.getRowTile((int)toy);
				y = (playerrow + 1) * World.BLOCKSIZE;
			}
			
			if(bottomLeft || bottomRight && falling) {
				falling = false;
				int playerRow = world.getRowTile((int)toy + height);
				y = (playerRow * World.BLOCKSIZE - height);
				dy = 0;
			} 
			
			if(!bottomLeft && !bottomRight) {
					if(!jumping) {
						hasFallen = true;
						falling = true;
					}
					else falling = false;
			}
		}
		
		
		private void calculateCorners(float x, float y) {
			World world = Playstate.world;
			int leftTile = world.getColTile((int)x);
			int rightTile = world.getColTile((int)x + width - 1);
			int topTile = world.getRowTile((int)y);
			int bottomTile = world.getRowTile((int)y + height);
			int midTile = world.getRowTile((int)y + height / 2);
			topLeft = !world.getBlocks()[topTile][leftTile].getMaterial().isWalkable();
			bottomLeft = !world.getBlocks()[bottomTile][leftTile].getMaterial().isWalkable();
			topRight = !world.getBlocks()[topTile][rightTile].getMaterial().isWalkable();
			bottomRight = !world.getBlocks()[bottomTile][rightTile].getMaterial().isWalkable();
			midLeft = !world.getBlocks()[midTile][leftTile].getMaterial().isWalkable();
			midRight = !world.getBlocks()[midTile][rightTile].getMaterial().isWalkable();
		}
		
		private void calculateMovement() {
			if(left) dx = -speed;
			if(right) dx = speed;
			
			if(falling && !jumping) {
				dy += GRAVITY;
				if(dy > MAX_FALLING_SPEED) dy = MAX_FALLING_SPEED;
			}
			
			if(jumping && !falling) {
				dy = JUMP_START;
				if(!inWater) {
					jumping = false;
					System.out.println("Test2");
					falling = true;
				}
				else jumping = true;
			}
		}
		
		private void calculateAnimations() {
			animation.update();
			if(left && animation.getState() != LEFT) {
				animation.setImages(LEFT, frames[LEFT]);
				idle = IDLE_LEFT;
			} else if(right && animation.getState() != RIGHT) {
				animation.setImages(RIGHT, frames[RIGHT]);
				idle = IDLE_RIGHT;
			}  
			
			if(!left && !right) {
				animation.setImages(idle, frames[idle]);
			}
		}
		
		private void move() {
			x += dx;
			y += dy;
			dx = 0;
		}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}
}
