package SuperMarioBros;

import java.awt.Graphics2D;

import Engine.Game;
import Engine.Spritesheet;

public class FireBall extends Entity{
	private static final float SPEED = 2f;
	
	private boolean exploding;
	private Spritesheet fireBallExploding;
	
	private int timeToLive;
	
	public FireBall(float x, float y, boolean right, boolean left) {
		super(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/FireBall.png"), 4, 16, 16), x, y, 16, 16, SPEED, new int[] { 0, 0, 0, 0 },  new int[] { 3, 3, 3, 3 });
		fireBallExploding = new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/FireBallExploding.png"), 3, 16, 16);
		this.right = right;
		this.left = left;
		exploding = false;
		timeToLive = 3;
	}
	
	@Override
	public void update() {
		if(bottomLeft || bottomRight) dy = -2f;
		if(midRight || midLeft) this.explode();
		RunningMonster nextEnemy = MarioWorldState.world.enemyAt((int) x + width / 2, (int) y + height);
		if(nextEnemy != null) {
			nextEnemy.headHit();
			explode();
		}
		if(exploding) {
			timeToLive--;
			dy = 0;
			x += dx;
			if(timeToLive == 0) MarioWorldState.world.fireBalls.remove(this);
		}
		super.update();
		
	}
	
	public void render(Graphics2D g, int startX, int startY) {
		g.drawImage(animation.getImage(), (int) x - startX, (int) y - startY, null);
	}
	
	public void explode() {
		exploding = true;
		this.changeSprite(fireBallExploding);
	}
}
