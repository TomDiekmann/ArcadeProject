package SuperMarioBros;

import java.awt.Graphics2D;

import Engine.Game;
import Engine.Spritesheet;

public class FireBall extends Entity{
	private static final float SPEED = 0;
	
	private boolean exploding;
	private Spritesheet fireBall;
	private Spritesheet fireBallExploding;
	
	public FireBall(float x, float y, boolean right, boolean left) {
		super(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/FireBall.png"), 4, 16, 16), x, y, 16, 16, SPEED, new int[] { 0, 0, 0, 0 },  new int[] { 3, 3, 3, 3 });
		fireBall = new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/FireBall.png"), 4, 16, 16);
		fireBallExploding = new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/FireBallExploding.png"), 3, 16, 16);
		this.right = right;
		this.left = left;
		exploding = false;
	}
	
	@Override
	public void update() {
		//System.out.println(x + "; " +  y);
		super.update();
	}

	public void render(Graphics2D g, int startX, int startY) {
		g.drawImage(animation.getImage(), (int) x - startX, (int) y - startY, null);
	}
}
