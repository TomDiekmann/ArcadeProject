package SuperMarioBros;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Engine.AudioFilePlayer;
import Engine.Game;
import Engine.GamePanel;
import Engine.GameStateManager;
import Engine.Spritesheet;

public class Player extends Entity {

	private boolean destroyingBlock;
	private static int[] states = { 0, 1, 2, 3 };
	private static int[] frames = { 1, 1, 2, 2 };
	private boolean playerIsSmall;
	private Spritesheet bigPlayer;
	private Spritesheet smallPlayer;
	private AudioFilePlayer soundPlayer = new AudioFilePlayer();

	private Thread jumpSound;
	private boolean stopMovingLeft;

	// Die animation
	public boolean died;
	private float diffY = 0;
	private boolean down;
	private int dieAnimationCounter = 0;
	private Thread dieSound;

	private int shellImuneTime = 0;

	public Player(float x, float y, int width, int height, float speed) {
		super(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/BigWalking.png"), 2, 16, 32), x, y, width,
				height, speed, states, frames);
		bigPlayer = new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/BigWalking.png"), 2, 16, 32);
		smallPlayer = new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/SmallWalking.png"), 2, 16, 16);
		playerIsSmall = false;
		dieSound = new Thread() {
			public void run() {
				soundPlayer.play("sounds/SuperMarioBros/MarioDies.wav");
			}
		};
		jumpSound = new Thread() {
			public void run() {
				soundPlayer.play("sounds/SuperMarioBros/highJump.wav");
			}
		};
	}

	@Override
	public void update() {
		super.update();

		if (destroyingBlock) {
			destroyBlock();
		}

		if (falling) {
			jumpSound.interrupt();
		}

		RunningMonster tmp = MarioWorldState.world.enemyAt((int) x + width / 2, (int) y + height);
		if (tmp != null && !tmp.isDead()) {
			if (falling && (int) (y + height) - 4 <= (int) tmp.getY() && (int) (y + height) + 4 >= (int) tmp.getY()) {
				tmp.headHit();
				falling = false;
				jumping = true;
				new Thread() {
					public void run() {
						soundPlayer.play("sounds/SuperMarioBros/stomp.wav");
					}
				}.start();
			} else {
				if (!tmp.isStaticShell() && (!tmp.isShell() || shellImuneTime == 0)) {
					if (!playerIsSmall && dieAnimationCounter == 0) {
						dieAnimationCounter = 110;
						stopMoving = true;
					} else if (playerIsSmall && dieAnimationCounter == 0) {
						died = true;
						stopMoving = true;
						MarioWorldState.world.stopMusic();
						if (!dieSound.isAlive())
							dieSound.start();
					}
				} else {
					if (x < tmp.getX() + 16 && x > tmp.getX()) {
						tmp.startMoving(true);
						shellImuneTime = 2;
					} else if (x + 16 > tmp.getX()) {
						tmp.startMoving(false);
						shellImuneTime = 2;
					}
				}
			}
			if (shellImuneTime > 0)
				shellImuneTime--;

		}

//		//Check if items nearby
//		for(int i = 0; i < Playstate.world.items.size(); i++) {
//			if(Playstate.world.items.get(i).getX() > (x  - Game.ITEM_SIZE - 8) && Playstate.world.items.get(i).getX() < (x +8)) {
//				if(Playstate.world.items.get(i).getY() > (y - 16) && Playstate.world.items.get(i).getY() < (y + height / 2 + 16)) {
//					Playstate.inventory.addItem(Playstate.world.items.get(i));
//					Playstate.world.items.remove(i);
//				}
//			}
//		}
	}

	@Override
	public void render(Graphics2D g) {
		if (!died) {
			if (dieAnimationCounter != 0) {
				if (dieAnimationCounter % 10 == 0) {
					if (playerIsSmall) {
						changeSprite(bigPlayer);
						playerIsSmall = false;
					} else {
						changeSprite(smallPlayer);
						playerIsSmall = true;
					}
					if (dieAnimationCounter == 10) {
						y += 16;
						height = 16;
						stopMoving = false;
					}
				}
				dieAnimationCounter--;
			}
			g.drawImage(animation.getImage(), (int) (x - MarioWorldState.camera.getCamX()),
					(int) (y - MarioWorldState.camera.getCamY()), null);
		} else {
			float drawY;
			if (!down) {
				drawY = y + diffY;
				diffY -= 2;
				if (diffY < -32) {
					down = true;
				}
			} else {
				drawY = y + diffY;
				diffY += 2;
				if (drawY > Game.gamepanel.getHeight()) {
					dieSound.interrupt();
					Game.gamepanel.gsm.setState(GameStateManager.MARIOWORLD);
				}
			}
			g.drawImage(Game.imageLoader.load("images/SuperMarioBros/died.png"),
					(int) GamePanel.width / 2 / GamePanel.SCALE - width / 2 - 7,
					(int) (GamePanel.height / 2 / GamePanel.SCALE - height / 2 + diffY), null);
		}

		if (x <= MarioWorldState.camera.getCamX()) {
			left = false;
			stopMovingLeft = true;
		} else
			stopMovingLeft = false;

	}

	// BLOCK DESTROYING
	private void destroyBlock() {
		if (isMouseOnScreen()) {
			if (isBlockInRadius(new Point(GamePanel.mouse.mouseConvertedX, GamePanel.mouse.mouseConvertedY), 2)) {
				MarioWorldState.world.getBlock(GamePanel.mouse.mouseConvertedX, GamePanel.mouse.mouseConvertedY)
						.destroyBlock();
			}
		}
	}

	// CHECK IF BLOCK IS IN RADIUS
	public boolean isBlockInRadius(Point p, int radius) {
		int colstart = MarioWorldState.world.getColTile((int) getCenterX()) - radius;
		int rowstart = MarioWorldState.world.getRowTile((int) getCenterY()) - radius;
		for (int row = 0; row < 2 * radius + 1; row++) {
			for (int col = 0; col < 2 * radius + 1; col++) {
				int blockX = colstart + col;
				int blockY = rowstart + row;
				if (blockX >= 0 && blockY >= 0 && blockX < MarioWorldState.world.getBlocksX()
						&& blockY < MarioWorldState.world.getBlocksY()) {
					if (MarioWorldState.world.getBlocks()[blockY][blockX].getBox().contains(p)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// CHECK IF MOUSE IS INSIDE WORLD
	private boolean isMouseOnScreen() {
		World world = MarioWorldState.world;
		int mx = GamePanel.mouse.mouseConvertedX;
		int my = GamePanel.mouse.mouseConvertedY;
		if (mx >= 0 && my >= 0 && mx <= world.getBlocksX() * World.BLOCKSIZE
				&& my <= world.getBlocksY() * World.BLOCKSIZE) {
			return true;
		} else
			return false;
	}

	public void keyPressed(KeyEvent e, int k) {
		switch (k) {
		case KeyEvent.VK_A:
			if (!stopMovingLeft)
				left = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			break;
		case KeyEvent.VK_SPACE: {
			if (!inWater && !falling && !jumping) {
				jumping = true;
				new Thread() {
					public void run() {
						soundPlayer.play("sounds/SuperMarioBros/highJump.wav");
					}
				}.start();
			} else if (inWater)
				jumping = true;
			break;
		}
		}
	}

	public void mousePressed(MouseEvent e) {

	}

	public void keyReleased(KeyEvent e, int k) {
		switch (k) {
		case KeyEvent.VK_A:
			left = false;
			break;
		case KeyEvent.VK_D:
			right = false;
			break;
		case KeyEvent.VK_SPACE:
			jumping = false;
			break;
		}
	}

	public void mouseReleased(MouseEvent e) {
		destroyingBlock = false;

	}
}
