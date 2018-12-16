package SuperMarioBros;

import java.awt.Graphics2D;
import java.awt.Point;

import Engine.Animation;
import Engine.AudioFilePlayer;
import Engine.GamePanel;

public class Block extends GameObject {

	// public static Spritesheet destroy = new
	// Spritesheet(Game.imageLoader.load("img/destroy.png"), 10, 16, 16);

	private Material material;
	private Animation animation;
	private AudioFilePlayer soundPlayer = new AudioFilePlayer();

	// BLOCK DESTROYING
	private long destroyStartTime;
	private boolean destroying;
	private boolean isDestructible;

	private boolean marker;
	
	private Item itemContent;

	public Block(Material material, float x, float y, int width, int height) {
		super(x, y, width, height);
		this.material = material;
//		animation = new Animation(destroy, dt);
		marker = false;
		switch(material.getID()) {
		case 24:
		case 25:
		case 26:
			isDestructible = true;
		default:
			isDestructible = false;
		}
	}

	public void update() {

		// BLOCK DESTROYING
		if (destroying) {
			int mouseX = GamePanel.mouse.mouseConvertedX;
			int mouseY = GamePanel.mouse.mouseConvertedY;

			animation.update();

			// INTERRUPTS IF CONDITIONS ARE NOT FULFILLED
			if (!GamePanel.mouse.pressed || !getBox().contains(new Point(mouseX, mouseY))
					|| !MarioWorldState.player.isBlockInRadius(new Point((int) x, (int) y), 2)) {
				destroying = false;
				animation.stop();
			}

			// DESTROYS THE BLOCK AFTER DESTROYING TIME
			if (System.currentTimeMillis() - destroyStartTime >= 1000) {

//				soundPlayer.play("sounds/dig/grass2.mp3");

				destroying = false;
				animation.stop();
			}

		}

	}

	public void render(Graphics2D g) {
		g.drawImage(material.getTexture(), (int) x - MarioWorldState.camera.getCamX(),
				(int) y - MarioWorldState.camera.getCamY(), null);

		// DESTROYING ANIMATION
		if (destroying) {
			g.drawImage(animation.getImage(), (int) x - MarioWorldState.camera.getCamX(),
					(int) y - MarioWorldState.camera.getCamY(), null);
		}

	}


	public void destroyBlock() {
		switch(material.getID()) {
		case 24:
		case 25:
		case 26:
			this.material = Material.BROWN_ITEM_OUT;
			if(itemContent != null) {
				MarioWorldState.world.items.add(itemContent);
				new Thread() {
					public void run() {
						soundPlayer.play("sounds/SuperMarioBros/ItemBlockItemOut.wav");
					}
				}.start();
			}
		}
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public void setMark(boolean mark) {
		marker = mark;
	}

	public boolean isMarked() {
		return marker;
	}
	public boolean isDestructible() {
		return isDestructible;
	}
	
	public void setItemContent(Item.Type type) {
		itemContent = new Item(type, this.x, this.y);
	}
}


