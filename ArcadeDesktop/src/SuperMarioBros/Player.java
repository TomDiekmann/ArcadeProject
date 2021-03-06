package SuperMarioBros;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Engine.Animation;
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
	private int points;
	private int lives;
	private int coins;
	private Thread jumpSound;
	private boolean stopMovingLeft;

	// Die animation
	public boolean died;
	private float diffY = 0;
	private boolean down;
	private int dieAnimationCounter = 0;
	private Thread dieSound;

	private Thread poleSound;
	private Animation climpAnimation;
	public boolean climpAnimationStarted;
  
	private BufferedImage flag = Game.imageLoader.load("images/SuperMarioBros/marioFlag.png");
	private boolean flagMoving = false;
	private int flagEndX;
	private int flagEndY;
	public boolean endWorld;

	private int shellImuneTime = 0;

	public Player(float x, float y, int width, int height, float speed) {
		super(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/BigWalking.png"), 2, 16, 32), x, y, width, height, speed, states, frames);
		bigPlayer = new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/BigWalking.png"), 2, 16, 32);
		smallPlayer = new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/SmallWalking.png"), 2, 16, 16);
		firePlayer = new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/FireWalking.png"), 2, 16, 32);
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
		poleSound = new Thread() {
			public void run() {
				soundPlayer.play("sounds/SuperMarioBros/flagpole.wav");
			}
		};
		points = 0;
		lives = 3;
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
			if (falling && (int) (y + height) - 5 <= (int) tmp.getY() && (int) (y + height) + 5 >= (int) tmp.getY()) {
				tmp.headHit();
				points += 100;
				MarioWorldState.world.pointsTexts.add(new PointsText("100", (int) tmp.getX() - MarioWorldState.camera.getCamX(), (int) tmp.getY() - MarioWorldState.camera.getCamY()));
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
						if (!dieSound.isAlive()) {
							dieSound.start();
						}
					}
				} else {
					if (x < tmp.getX() + 16 && x > tmp.getX()) {
						tmp.startMoving(true);
						MarioWorldState.world.enemies.remove(tmp);
						MarioWorldState.world.shells.add(tmp);
						shellImuneTime = 2;
					} else if (x + 16 > tmp.getX()) {
						tmp.startMoving(false);
						MarioWorldState.world.enemies.remove(tmp);
						MarioWorldState.world.shells.add(tmp);
						shellImuneTime = 2;
					}
				}
			}
			if (shellImuneTime > 0)
				shellImuneTime--;

		}

		if (y > 185 && !died) {
			MarioWorldState.world.stopMusic();
			MarioWorldState.deadScreen = true;
		}

		Item nextItem = MarioWorldState.world.itemAt((int) x + width / 2, (int) y + height);
		if (nextItem != null) {
			switch (nextItem.getType()) {
			case Mushroom:
				if (playerIsSmall) {
					height = 32;
					changeSprite(bigPlayer);
					if (playerIsSmall) {
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
				if (!playerIsFireMario) {
					playerIsFireMario = true;
					changeSprite(firePlayer);
					height = 32;
					if (playerIsSmall) {
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
				increaseLives();
				break;
			case Star:
				invincible = true;
				invicibilityTime = 1000;
				MarioWorldState.world.playStarSoundtrack();
			}
			MarioWorldState.world.items.remove(nextItem);

		}
		
		Coin nextCoin = MarioWorldState.world.coinAt((int) x + width / 2, (int) y + height);
		if(nextCoin != null && !nextCoin.getCollected()) {
			nextCoin.collect();
			coinCollected();
		}

		if (invincible) {
			invicibilityTime--;
			if (invicibilityTime == 0) {
				invincible = false;
				invicibilityTime = 0;
				MarioWorldState.world.stopStarSoundtrack();
			}
		}
		Block blockOverMario = null;
		if (topLeft) {
			blockOverMario = MarioWorldState.world.getBlock((int) this.x, (int) this.y - width);

		} else if (topRight) {
			blockOverMario = MarioWorldState.world.getBlock((int) this.x + 16, (int) this.y - width);
		}
		if (blockOverMario != null) {

			blockOverMario.destroyBlock();
		}
	}

	@Override
	public void render(Graphics2D g) {
		if (!died && !climpAnimationStarted) {
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
			g.drawImage(animation.getImage(), (int) (x - MarioWorldState.camera.getCamX()), (int) (y - MarioWorldState.camera.getCamY()), null);
		} else if (died) {
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
					MarioWorldState.deadScreen = true;
				}
			}
			g.drawImage(Game.imageLoader.load("images/SuperMarioBros/died.png"), (int) GamePanel.width / 2 / GamePanel.SCALE - width / 2 - 7, (int) (GamePanel.height / 2 / GamePanel.SCALE - height / 2 + diffY), null);
		}

		if (x <= MarioWorldState.camera.getCamX()) {
			left = false;
			stopMovingLeft = true;
		} else
			stopMovingLeft = false;

		if (MarioWorldState.world.getBlock((int) x, (int) y).getMaterial() == Material.POLE && !climpAnimationStarted) {
			if (!playerIsSmall)
				climpAnimation = new Animation(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/climpBig.png"), 2, 16, 32), 150L, 0, 2);
			else
				climpAnimation = new Animation(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/climpSmall.png"),2,16,16), 150L, 0, 2);
			
			climpAnimationStarted = true; 

			stopMoving = true;
		}

		if (climpAnimationStarted && !endWorld) {
			MarioWorldState.stopTimeCounting();
			if ((MarioWorldState.world.getBlock((int) x, (int) y + 17).getMaterial() == Material.POLE && playerIsSmall) || (MarioWorldState.world.getBlock((int) x, (int) y + 33).getMaterial() == Material.POLE && !playerIsSmall)) {
				climpAnimation.update();
				y++;
				MarioWorldState.world.stopMusic();
				if (!poleSound.isAlive()) {
					poleSound.start();
				}
				g.drawImage(climpAnimation.getImage(),(int) x - MarioWorldState.camera.getCamX(),(int) y - MarioWorldState.camera.getCamY(), null);
				g.drawImage(flag, (int) (MarioWorldState.world.getBlock((int)x,(int) y).getX() - 8 - MarioWorldState.camera.getCamX()), (int) (y - MarioWorldState.camera.getCamY()), null);
				flagEndX =  (int) MarioWorldState.world.getBlock((int)x,(int) y).getX() - 8;
				flagEndY = (int) y;
			}
			else {
				g.drawImage(animation.getImage(), (int) (x - MarioWorldState.camera.getCamX()),
						(int) (y - MarioWorldState.camera.getCamY()), null);
				g.drawImage(flag, flagEndX - MarioWorldState.camera.getCamX() , flagEndY - MarioWorldState.camera.getCamY(), null);

				soundPlayer = new AudioFilePlayer();
				if (!right) {
					new Thread() {
						public void run() {
							soundPlayer.play("sounds/SuperMarioBros/stageClear.wav");
						}
					}.start();
				}
				stopMoving = false;
				right = true;
				if (MarioWorldState.world.getBlock((int) x, (int) y).getMaterial() == Material.BLACK || MarioWorldState.world.getBlock((int) x, (int) y).getMaterial() == Material.CASTLE_LEFT_BRICKS || MarioWorldState.world.getBlock((int) x, (int) y).getMaterial() == Material.CASTLE_BRICKS) {
					right = false;
					endWorld = true;
				}
			}
		}

		if (endWorld) {
			if (MarioWorldState.time > 0) {
				MarioWorldState.time--;
				points += 50;
				if (points % 500 == 0) {
					new Thread() {
						public void run() {
							soundPlayer.play("sounds/SuperMarioBros/Beep.wav");
						}
					}.start();
				}
				g.drawImage(flag, flagEndX - MarioWorldState.camera.getCamX() , flagEndY - MarioWorldState.camera.getCamY(), null);
			}
			else {
				Game.gamepanel.gsm.setState(GameStateManager.MAINSTATE);
			}
		}

	}

	// BLOCK DESTROYING
	private void destroyBlock() {
		if (isMouseOnScreen()) {
			if (isBlockInRadius(new Point(GamePanel.mouse.mouseConvertedX, GamePanel.mouse.mouseConvertedY), 2)) {
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
				if (blockX >= 0 && blockY >= 0 && blockX < MarioWorldState.world.getBlocksX() && blockY < MarioWorldState.world.getBlocksY()) {
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
		if (mx >= 0 && my >= 0 && mx <= world.getBlocksX() * World.BLOCKSIZE && my <= world.getBlocksY() * World.BLOCKSIZE) {
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
		case KeyEvent.VK_SPACE:
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
		case KeyEvent.VK_E:
			if(playerIsFireMario && MarioWorldState.world.fireBalls.size() <= 1) {
				boolean directionRight = this.right;
				boolean directionLeft = this.left;
				if(!(left && right)) {
					if(idle == 0) directionLeft = true;
					else directionRight = true;
				}
				MarioWorldState.world.fireBalls.add(new FireBall(this.x, this.y, directionRight, directionLeft));
				new Thread() {
					public void run() {
						soundPlayer.play("sounds/SuperMarioBros/FireBallSoundEffect.wav");
					}
				}.start();
			}
		}
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

	public void mousePressed(MouseEvent e) {

	}

	public int getLives() {
		return lives;
	}

	public void reduceLives() {
		lives--;
	}

	public void increaseLives() {
		lives++;
		new Thread() {
			public void run() {
				soundPlayer.play("sounds/SuperMarioBros/1-UpSoundtrack.wav");
			}
		}.start();
	}

	public int getPoints() {
		return points;
	}

	public int getCoins() {
		return coins;
	}

	public void coinCollected() {
		coins++;
		if (coins >= 100) {
			increaseLives();
			coins -= 100;
		}
		points += 100;
		new Thread() {
			public void run() {
				soundPlayer.play("sounds/SuperMarioBros/CoinSoundEffect.wav");
			}
		}.start();
	}

	public boolean playerIsSmall() {
		return playerIsSmall;
	}
}
