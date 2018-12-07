package SuperMarioBros;
import java.awt.image.BufferedImage;

public enum Material {	
	
	AIR(30, true),
	BROWN_BROKEN_GROUND(0, false),
	BROWN_BRICKS(1,false),
	ITEM_BLOCK(24, false),
	TUBE_TOP_LEFT(264, false),
	TUBE_TOP_RIGHT(265, false),
	TUBE_DOWN_LEFT(297, false),
	TUBE_DOWN_RIGHT(298, false);
	
	private int id;
	private boolean walkable;
	private BufferedImage image;
	
	
	private Material(int id, boolean walkable) {
		this.id = id;
		this.walkable = walkable;
		this.image = MenuState.sprite.getTexture(id);
	}
	
	public int getID() {
		return id;
	}
	
	public boolean isWalkable() {
		return walkable;
	}
	
	public BufferedImage getTexture() {
		return image;
	}
	
}
