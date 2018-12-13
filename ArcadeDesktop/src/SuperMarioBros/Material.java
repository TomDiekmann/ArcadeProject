package SuperMarioBros;

import java.awt.image.BufferedImage;

public enum Material {

	AIR(30, true), BROWN_BROKEN_GROUND(0, false), BROWN_BRICKS_TOP(1, false), ITEM_BLOCK_LIGHT(24, false),
	TUBE_TOP_LEFT(264, false), TUBE_TOP_RIGHT(265, false), TUBE_DOWN_LEFT(297, false), TUBE_DOWN_RIGHT(298, false),
	ITEM_BLOCK_MIDDLE(25, false), ITEM_BLOCK_DARK(26, false), BROWN_ITEM_OUT(3, false), BROWN_HARD_BLOCK(33, false),
	HILL_UP(272, true), HILL_TOP(273, true), HILL_DOWN(274, true), HILL_BASE1(305, true), HILL_BASE2(306, true),
	HILL_BASE3(307, true), BUSH_UP(308, true), BUSH_TOP(309, true), BUSH_DOWN(310, true), CLOUD_TOP_LEFT(660, true),
	CLOUD_TOP_MID(661, true), CLOUD_TOP_RIGHT(662, true), CLOUD_DOWN_LEFT(693, true), CLOUD_DOWN_MID(694, true),
	CLOUD_DOWN_RIGHT(695, true),  CASTLE_TOP_BLUE(11, true), CASTLE_LEFT_BRICKS(12,true), CASTLE_BRICKS(13,true), CASTLE_RIGHT_BRICKS(14,true),
	CASTLE_TOP_BROWN(44,true),CASTLE_DOOR_TOP(45,true), BLACK(46, true), POLE_TOP(280,true), POLE(313,true);

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
