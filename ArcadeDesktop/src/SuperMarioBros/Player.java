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
	private boolean playerIsFireMario;
	private boolean invincible;
	private int invicibilityTime;
	private Spritesheet bigPlayer;
	private Spritesheet smallPlayer;
	private Spritesheet firePlayer;
	private AudioFilePlayer soundPlayer = new AudioFilePlayer();

	private Thread jumpSound;

	// Die animation
	public boolean died;
	private float diffY = 0;
	private boolean down;
	private int dieAnimationCounter = 0;
	private Thread dieSound;

	public Player(float x, float y, int width, int height, float speed) {
		super(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/BigWalking.png"), 2, 16, 32), x, y, width,
				height, speed, states, frames);
		bigPlayer = new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/BigWalking.png"), 2, 16, 32);
		smallPlayer = new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/SmallWalking.png"), 2, 16, 16);
		firePlayer = new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/FireWalking.png"), 2, 16, 32);
		playerIsSmall = false;
		invincible = false;
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
		
		//System.out.println(y);
		
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
				if(invincible) {
					//TO-DO implement mehtod defated in claas Running Monster
					tmp.defeated();
				}
				if (!tmp.isStaticShell()) {
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
				}
				else {
					if(x > tmp.getCenterX()) {
						tmp.startMoving(true);
					}
					else
						tmp.startMoving(false);
				}
			}
		}

			
		Item nextItem = MarioWorldState.world.itemAt((int) x + width / 2, (int) y + height);
		if(nextItem != null) {
			//System.out.println("item gefunden");
			switch(nextItem.getType()) {
			case Mushroom:
				if(playerIsSmall) {					
					height = 32;
					changeSprite(bigPlayer);
					if(playerIsSmall) {
						y -= 16;
					}
					new Thread() {
						public void run() {
							soundPlayer.play("sounds/SuperMarioBros/MarioGetsStronger.wav");
						}
					}.start();
					playerIsSmall = false;
				}
				break;
			case FireFlower:
				if(!playerIsFireMario) {
					playerIsFireMario = true;
					changeSprite(firePlayer);
					height = 32;
					if(playerIsSmall) {
						y -= 16;
					}
					new Thread() {
						public void run() {
							soundPlayer.play("sounds/SuperMarioBros/MarioGetsStronger.wav");
						}
					}.start();
				}
				break;
			case Up_Mushroom:
				//TO-DO implement live-Counter
				new Thread() {
					public void run() {
						soundPlayer.play("sounds/SuperMarioBros/1-UpSoundtrack.wav");
					}
				}.start();
				break;
			case Star:
				invincible = true;
				invicibilityTime = 1000;		
				MarioWorldState.world.playStarSoundtrack();
			}
			MarioWorldState.world.items.remove(nextItem);

		}
		
		if(invincible) {
			invicibilityTime--;
			//System.out.println(invicibilityTime);
			if(invicibilityTime == 0) {
				invincible = false;
				invicibilityTime = 0;
				MarioWorldState.world.stopStarSoundtrack();
			}
		}
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
			g.drawImage(animation.getImage(), GamePanel.width / 2 / GamePanel.SCALE - width / 2 - 7,
					GamePanel.height / 2 / GamePanel.SCALE - height / 2, null);
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
			System.out.println(drawY);
			g.drawImage(Game.imageLoader.load("images/SuperMarioBros/died.png"),
					(int) GamePanel.width / 2 / GamePanel.SCALE - width / 2 - 7,
					(int) (GamePanel.height / 2 / GamePanel.SCALE - height / 2 + diffY), null);
		}
	}

	// BLOCK DESTROYING
	private void destroyBlock() {
		if (isMouseOnScreen()) {
			if (isBlockInRadius(new Point(GamePanel.mouse.mouseConvertedX, GamePanel.mouse.mouseConvertedY), 2)) {
				System.out.println("dada");
				MarioWorldState.world.getBlock(GamePanel.mouse.mouseConvertedX, GamePanel.mouse.mouseConvertedY).destroyBlock();
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
