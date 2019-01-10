package snake;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import Engine.AudioFilePlayer;
import Engine.GamePanel;
import Engine.GameStateManager;
import Engine.State;

public class SnakeGameState extends State {
	// WINDOW VARIABLES
	public final int WINDOW_WIDTH = 640, WINDOW_HEIGHT = 360;

	public final int TILE_SIZE = 20;
	public final int GAMEAREA_WIDTH = this.WINDOW_WIDTH - (this.TILE_SIZE * 2), GAMEAREA_HEIGHT = this.WINDOW_HEIGHT - (this.TILE_SIZE * 4);
	public final int GAMEAREA_LEFT = this.TILE_SIZE, GAMEAREA_RIGHT = this.WINDOW_WIDTH - this.TILE_SIZE;
	public final int GAMEAREA_TOP = this.TILE_SIZE * 2, GAMEAREA_BOTTOM = GAMEAREA_TOP + GAMEAREA_HEIGHT;

	// GAME VARIABLE
	private boolean RUNNING;
	private boolean PAUSED = false;
	private int gameTicks = 0;
	private int gameoverTicks = 0;
	private Random random;
	private int score = 0;
	public static int HIGHSCORE = loadHighscore();
	private boolean newHighscore = false;
	private int highscoreTicks = 0;
	private boolean redraw = false;
	private AudioFilePlayer soundPlayer = new AudioFilePlayer();

	// SNAKE VARIABLES
	private SnakeBodyPart snakeHead;
	private ArrayList<SnakeBodyPart> snakeBody;

	private int snakeX = this.GAMEAREA_LEFT + ((this.GAMEAREA_WIDTH / this.TILE_SIZE) / 2) * this.TILE_SIZE, snakeY = this.GAMEAREA_TOP;

	private int snakeLength = 3;
	private final int snakeMaxLength = (this.GAMEAREA_WIDTH / this.TILE_SIZE) * (this.GAMEAREA_HEIGHT / this.TILE_SIZE);

	private Direction snakeDirection = Direction.DOWN;
	private Direction snakeDirectionNext = snakeDirection;

	// FRUIT VARIABLES
	private SnakeFruit fruit;
	private ArrayList<SnakeFruit> fruits;

	public SnakeGameState(GameStateManager gameStateManager) {
		super(gameStateManager);
		this.random = new Random();

		this.snakeBody = new ArrayList<SnakeBodyPart>();
		this.fruits = new ArrayList<SnakeFruit>();

		this.RUNNING = true;
	}

	// UPDATE THE GAME
	public void tick() {
		// Add initial Body Part/Head
		if (this.snakeBody.size() == 0) {
			this.snakeHead = new SnakeBodyPart(this.snakeX, this.snakeY, this.TILE_SIZE);
			this.snakeBody.add(this.snakeHead);
		}

		// Increase Ticks
		this.gameTicks++;

		// Update snake
		if (this.gameTicks > 5) {
			// Reset Ticks
			this.gameTicks = 0;

			// Update snake Direction
			if (this.snakeDirection != Direction.getOpposite(this.snakeDirectionNext))
				this.snakeDirection = this.snakeDirectionNext;

			switch (this.snakeDirection) {
			case RIGHT:
				this.snakeX += this.TILE_SIZE;
				break;

			case LEFT:
				this.snakeX -= this.TILE_SIZE;
				break;

			case UP:
				this.snakeY -= this.TILE_SIZE;
				break;

			case DOWN:
				this.snakeY += this.TILE_SIZE;
				break;
			}

			// Add new Head
			this.snakeHead = new SnakeBodyPart(this.snakeX, this.snakeY, this.TILE_SIZE);
			this.snakeBody.add(this.snakeHead);

			// Remove Tail
			if (this.snakeBody.size() > this.snakeLength)
				this.snakeBody.remove(0);

			// Add new Fruit after a Fruit has been collected
			if (this.fruits.size() == 0) {
				int fruitPosX, fruitPosY;

				// Search for available Fruit Position
				do {
					fruitPosX = this.GAMEAREA_LEFT + this.random.nextInt(this.GAMEAREA_WIDTH / this.TILE_SIZE) * this.TILE_SIZE;
					fruitPosY = this.GAMEAREA_TOP + this.random.nextInt(this.GAMEAREA_HEIGHT / this.TILE_SIZE) * this.TILE_SIZE;
				} while (checkCollisionFruit(fruitPosX, fruitPosY) || fruitPosX < this.GAMEAREA_LEFT || fruitPosX >= this.GAMEAREA_RIGHT || fruitPosY < this.GAMEAREA_TOP || fruitPosY >= this.GAMEAREA_BOTTOM);

				this.fruit = new SnakeFruit(fruitPosX, fruitPosY, this.TILE_SIZE);
				this.fruits.add(this.fruit);
			}

			// Snake-Fruit Collision
			for (int i = 0; i < this.fruits.size(); i++) {
				SnakeFruit fruitCurrent = this.fruits.get(i);

				if (this.snakeX == fruitCurrent.getPosX() && this.snakeY == fruitCurrent.getPosY()) {
					if (this.snakeLength < this.snakeMaxLength)
						this.snakeLength++;

					this.score += this.fruits.get(0).getScore();
					if (this.score > this.HIGHSCORE)
						newHighscore = true;
					this.fruits.remove(0);
					new Thread() {
						public void run() {
							soundPlayer.play("sounds/Snake/snakeCollect.wav");
						}
					}.start();
					break;
				}
			}

			// Snake-Body Party Collision
			for (int i = 0; i < this.snakeBody.size(); i++) {
				SnakeBodyPart bodyPart = this.snakeBody.get(i);

				if (i != this.snakeBody.size() - 1 && this.snakeX == bodyPart.getPosX() && this.snakeY == bodyPart.getPosY())
					this.RUNNING = false;
			}

			// Snake outside Game Area
			if (this.snakeX < this.GAMEAREA_LEFT || this.snakeX >= this.GAMEAREA_RIGHT || this.snakeY < this.GAMEAREA_TOP || this.snakeY >= this.GAMEAREA_BOTTOM)
				this.RUNNING = false;
		}

		if (!this.RUNNING)
			this.redraw = true;
	}

	// Fruit Collision
	private boolean checkCollisionFruit(int x, int y) {
		boolean collides = false;

		for (int i = 0; i < this.snakeBody.size(); i++) {
			SnakeBodyPart bodyPart = this.snakeBody.get(i);

			if (bodyPart.getPosX() == x && bodyPart.getPosY() == y)
				collides = true;
		}
		return collides;
	}

	@Override
	public void update() {
		if (this.RUNNING && !this.PAUSED)
			this.tick();
	}

	@Override
	public void render(Graphics2D graphics) {
		if (this.RUNNING || this.redraw) {
			// Clear Window
			graphics.clearRect(0, 0, this.WINDOW_WIDTH, this.WINDOW_HEIGHT);

			// Draw Window Background
			graphics.setColor(new Color(155, 188, 15));
			graphics.fillRect(0, 0, this.WINDOW_WIDTH, this.WINDOW_HEIGHT);

			// Draw Game Area Background
			graphics.setColor(new Color(139, 172, 15));
			graphics.fillRect(this.GAMEAREA_LEFT, this.GAMEAREA_TOP, this.GAMEAREA_WIDTH, this.GAMEAREA_HEIGHT);

			// Draw Game Area Edges
			int outerEdgeWidth = 5;
			int innerEdgeWidth = 3;
			int edgeGap = 1;
			int edgeWidthTotal = outerEdgeWidth + innerEdgeWidth + edgeGap;

			// Outer Edge
			graphics.setColor(new Color(15, 56, 15));
			graphics.fillRect(this.GAMEAREA_LEFT - edgeWidthTotal, this.GAMEAREA_TOP - edgeWidthTotal, outerEdgeWidth, this.GAMEAREA_HEIGHT + edgeWidthTotal * 2);
			graphics.fillRect(this.GAMEAREA_RIGHT + outerEdgeWidth - 1, this.GAMEAREA_TOP - edgeWidthTotal, outerEdgeWidth, this.GAMEAREA_HEIGHT + edgeWidthTotal * 2);
			graphics.fillRect(this.GAMEAREA_LEFT - edgeWidthTotal, this.GAMEAREA_TOP - edgeWidthTotal, this.GAMEAREA_WIDTH + edgeWidthTotal * 2, outerEdgeWidth);
			graphics.fillRect(this.GAMEAREA_LEFT - edgeWidthTotal, this.GAMEAREA_BOTTOM + outerEdgeWidth - 1, this.GAMEAREA_WIDTH + edgeWidthTotal * 2, outerEdgeWidth);

			// Inner Edge
			graphics.setColor(new Color(48, 98, 48));
			graphics.fillRect(this.GAMEAREA_LEFT - innerEdgeWidth, this.GAMEAREA_TOP - innerEdgeWidth, innerEdgeWidth, this.GAMEAREA_HEIGHT + innerEdgeWidth * 2);
			graphics.fillRect(this.GAMEAREA_RIGHT, this.GAMEAREA_TOP - innerEdgeWidth, innerEdgeWidth, this.GAMEAREA_HEIGHT + innerEdgeWidth * 2);
			graphics.fillRect(this.GAMEAREA_LEFT - innerEdgeWidth, this.GAMEAREA_TOP - innerEdgeWidth, this.GAMEAREA_WIDTH + innerEdgeWidth * 2, innerEdgeWidth);
			graphics.fillRect(this.GAMEAREA_LEFT - innerEdgeWidth, this.GAMEAREA_BOTTOM, this.GAMEAREA_WIDTH + innerEdgeWidth * 2, innerEdgeWidth);

			// Draw the Snake
			for (int i = 0; i < this.snakeBody.size(); i++)
				this.snakeBody.get(i).draw(graphics);

			// Draw the Fruits
			for (int i = 0; i < this.fruits.size(); i++)
				this.fruits.get(i).draw(graphics);

			// Draw Score
			graphics.setColor(new Color(15, 56, 15));
			graphics.setFont(new Font("Impact", Font.PLAIN, this.TILE_SIZE));
			graphics.drawString("Score: " + this.score + "   Highscore: " + (HIGHSCORE > this.score ? this.HIGHSCORE : this.score), this.TILE_SIZE / 2, this.TILE_SIZE + graphics.getFontMetrics().getDescent());

			// Draw Points
			// 1 Point
			graphics.fillRect(this.WINDOW_WIDTH / 2 + this.TILE_SIZE * 2 + this.TILE_SIZE / 2 - 3, this.TILE_SIZE / 2 - 3, 6, this.TILE_SIZE);
			graphics.fillRect(this.WINDOW_WIDTH / 2 + this.TILE_SIZE * 2, this.TILE_SIZE / 2 + this.TILE_SIZE / 2 - 6, this.TILE_SIZE, 6);
			graphics.drawString(" : 1p", this.WINDOW_WIDTH / 2 + this.TILE_SIZE * 3, this.TILE_SIZE + graphics.getFontMetrics().getDescent());

			// 10 Points
			graphics.fillRoundRect(this.WINDOW_WIDTH / 2 + this.TILE_SIZE * 7, this.TILE_SIZE / 2 - 3, this.TILE_SIZE, this.TILE_SIZE, 10, 10);
			graphics.setColor(new Color(155, 188, 15));
			graphics.fillRoundRect(this.WINDOW_WIDTH / 2 + this.TILE_SIZE * 7 + 4, this.TILE_SIZE / 2 - 3 + 4, this.TILE_SIZE - 8, this.TILE_SIZE - 8, 10, 10);
			graphics.setColor(new Color(15, 56, 15));
			graphics.drawString(" : 10p", this.WINDOW_WIDTH / 2 + this.TILE_SIZE * 8, this.TILE_SIZE + graphics.getFontMetrics().getDescent());

			// 50 Points
			graphics.fillRoundRect(this.WINDOW_WIDTH / 2 + this.TILE_SIZE * 12, this.TILE_SIZE / 2 - 3, this.TILE_SIZE, this.TILE_SIZE, 10, 10);
			graphics.drawString(" : 50p", this.WINDOW_WIDTH / 2 + this.TILE_SIZE * 13, this.TILE_SIZE + graphics.getFontMetrics().getDescent());

			// Controls
			graphics.setColor(new Color(15, 56, 15));
			graphics.drawString("WASD:   Movement                E:   Pause", this.TILE_SIZE / 2, this.GAMEAREA_BOTTOM + (int) (this.TILE_SIZE * 1.5) + graphics.getFontMetrics().getDescent());
			
			// Draw Pause Screen
			if (this.PAUSED) {
				graphics.setColor(new Color(15, 56, 15));
				graphics.setFont(new Font("Impact", Font.PLAIN, TILE_SIZE * 3));
				graphics.drawString("Paused", (GamePanel.width - graphics.getFontMetrics().stringWidth("Paused")) / 2, TILE_SIZE * 2 + (GamePanel.height - TILE_SIZE * 3) / 2 + graphics.getFontMetrics().getDescent());
			} else if (this.RUNNING && newHighscore && highscoreTicks < 150) {
				Composite originalComposite = graphics.getComposite();
				graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F));
				graphics.setColor(new Color(15, 56, 15));
				graphics.setFont(new Font("Impact", Font.PLAIN, TILE_SIZE * 3));
				graphics.drawString("New Highscore!", (GamePanel.width - graphics.getFontMetrics().stringWidth("New Highscore!")) / 2, TILE_SIZE * 2 + (GamePanel.height - TILE_SIZE * 3) / 2 + graphics.getFontMetrics().getDescent());
				graphics.setComposite(originalComposite);
				this.highscoreTicks++;
			}

			if (this.redraw)
				this.redraw = false;
		} else {
			// Draw Game Over Screen
			graphics.setColor(new Color(15, 56, 15));
			graphics.setFont(new Font("Impact", Font.PLAIN, TILE_SIZE * 3));
			graphics.drawString("Game Over", (GamePanel.width - graphics.getFontMetrics().stringWidth("Game Over")) / 2, TILE_SIZE * 2 + (GamePanel.height - TILE_SIZE * 3) / 2 + graphics.getFontMetrics().getDescent());
			this.gameoverTicks++;

			// Return to main menu
			if (this.gameoverTicks > 250) {
				if (this.score > HIGHSCORE)
					this.updateHighscore(this.score);
				this.gsm.setState(GameStateManager.MAINSTATE);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e, int k) {
		int key = e.getKeyCode();

		// Pause Game
		if (key == KeyEvent.VK_E)
			this.PAUSED = !this.PAUSED;

		// Keyboard Input
		if (!this.PAUSED) {
			if (key == KeyEvent.VK_W && this.snakeDirectionNext != Direction.DOWN)
				this.snakeDirectionNext = Direction.UP;
			else if (key == KeyEvent.VK_A && this.snakeDirectionNext != Direction.RIGHT)
				this.snakeDirectionNext = Direction.LEFT;
			else if (key == KeyEvent.VK_S && this.snakeDirectionNext != Direction.UP)
				this.snakeDirectionNext = Direction.DOWN;
			else if (key == KeyEvent.VK_D && this.snakeDirectionNext != Direction.LEFT)
				this.snakeDirectionNext = Direction.RIGHT;
		}
	}

	@Override
	public void keyReleased(KeyEvent e, int k) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void stateEnd() {
	}

	private enum Direction {
		LEFT, RIGHT, UP, DOWN;

		public static Direction getOpposite(Direction dir) {
			switch (dir) {
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;
			case UP:
				return DOWN;
			case DOWN:
				return UP;
			default:
				return UP;
			}
		}
	}

	public static int loadHighscore() {
		BufferedReader reader;
		int highscore = 0;
		try {
			reader = new BufferedReader(new FileReader(new File("files/Snake/Highscore.txt")));
			highscore = Integer.parseInt(reader.readLine());
			reader.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

		return highscore;
	}

	public void updateHighscore(int newScore) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(new File("files/Snake/Highscore.txt")));
			writer.write("" + newScore);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		HIGHSCORE = newScore;
	}
}
