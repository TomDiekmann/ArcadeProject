package SuperMarioBros;

import java.awt.Graphics2D;
import java.awt.Point;

import Engine.Animation;
import Engine.GamePanel;

public class Block extends GameObject {

	// public static Spritesheet destroy = new
	// Spritesheet(Game.imageLoader.load("img/destroy.png"), 10, 16, 16);

	private Material material;
	private Animation animation;

	// BLOCK DESTROYING
	private long destroyStartTime;
	private boolean destroying;

	private boolean marker;

	public Block(Material material, float x, float y, int width, int height) {
		super(x, y, width, height);
		this.material = material;
//		animation = new Animation(destroy, dt);
		marker = false;
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

	// INIT BLOCK DESTROYING
	public void destroyBlock() {
//		if(!destroying && material != Material.AIR) {
//			if(material.getBreakLevel() != null && material.getBreakLevel().getLevel() > Playstate.inventory.getSelectedItem().getType().getBreakLevel().getLevel()) destroyDuration = (long) (material.getDestroyDuration() * 3.33);
//			else destroyDuration = material.getDestroyDuration();
//			if(material.getBreakType() == Playstate.inventory.getSelectedItem().getType().getBreakType())destroyDuration *= Playstate.inventory.getSelectedItem().getType().getBreakLevel().getBreakMultiplicator();
//			destroying = true;
//			destroyStartTime = System.currentTimeMillis();
//			animation.setDelay((long) (destroyDuration / destroy.getCols()));
//			animation.start(0, destroy.getCols());
//		}
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

}
