package SuperMarioBros;

import Engine.Game;
import Engine.Spritesheet;
import java.awt.Graphics2D;


public class Coin extends Entity{

	private int timeToLive;
	private boolean collected;
	
	
	public Coin(float x, float y) {
		super(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/Coin.png"), 4, 16, 16), x, y, 16, 16, 0, new int[] { 0, 0, 0, 0 }, new int[] { 3, 3, 3, 3 });
		collected = false;
		timeToLive = 64;
	}

	public void update() {
		if(collected) {
			dy += 0.15f;
			timeToLive--;
			if(timeToLive == 0) {
				MarioWorldState.world.coins.remove(this);
			}
		}
		super.calculateAnimations();
		super.move();
	}
	
	public void render(Graphics2D g, int startX, int startY) {
		g.drawImage(animation.getImage(), (int) x - startX, (int) y - startY, null);
	}      
	
	public boolean getCollected() {
		return collected;
	}
	
	public void collect() {
		if(!collected) {
			collected = true;
			this.changeSprite(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/CollectedCoin.png"), 4, 16, 16));
			dy = -5f;
		}
	}
}
