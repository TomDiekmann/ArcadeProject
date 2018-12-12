package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import Engine.GamePanel;
import Engine.GameStateManager;
import Engine.State;

public class SnakeGameState extends State {
	public static final int WIDTH = 640, HEIGHT = 360, TILE_SIZE = 20;

	private boolean running;

	private int score = 0;

	private Random random;
	private Direction direction = Direction.DOWN;
	private Direction nextDirection = direction;

	private SnakeBodyPart head;
	private ArrayList<SnakeBodyPart> snake;

	private SnakeFruit fruit;
	private ArrayList<SnakeFruit> fruits;

	private int ticks = 0;
	private int posX = (WIDTH / TILE_SIZE) / 2, posY = 3, length = 3;
	private final int maxLength = (WIDTH / TILE_SIZE - 2) * (HEIGHT / TILE_SIZE - 4);

	private int gameOverTicks = 0;

	public SnakeGameState(GameStateManager gsm) {
		super(gsm);
		this.random = new Random();
		this.snake = new ArrayList<SnakeBodyPart>();
		this.fruits = new ArrayList<SnakeFruit>();
		running = true;
	}

	public void tick() {
		if (this.snake.size() == 0)
			this.snake.add(this.head = new SnakeBodyPart(posX, posY));

		this.ticks++;

		// Move snake
		if (this.ticks > 10 / 2) {
			if (this.direction != Direction.getOpposite(this.nextDirection)) {
				direction = nextDirection;
			}

			switch (this.direction) {
			case RIGHT:
				this.posX++;
				break;

			case LEFT:
				this.posX--;
				break;

			case UP:
				this.posY--;
				break;

			case DOWN:
				this.posY++;
				break;
			}

			this.ticks = 0;
			this.head = new SnakeBodyPart(this.posX, this.posY);
			this.snake.add(this.head);

			if (this.snake.size() > this.length)
				this.snake.remove(0);

			// No fruit in game area
			if (this.fruits.size() == 0) {
				int fruitPosX, fruitPosY;

				// Search for fruit position
				do {
					fruitPosX = random.nextInt(WIDTH / TILE_SIZE);
					fruitPosY = random.nextInt(HEIGHT / TILE_SIZE);
				} while (checkCollisionFruit(fruitPosX, fruitPosY) || fruitPosX < 1 || fruitPosX >= WIDTH / TILE_SIZE - 1 || fruitPosY < 2 || fruitPosY >= HEIGHT / TILE_SIZE - 1);

				this.fruit = new SnakeFruit(fruitPosX, fruitPosY);
				fruits.add(this.fruit);
			}

			// Snake colliding with fruit
			for (int i = 0; i < this.fruits.size(); i++) {
				SnakeFruit appleCurrent = this.fruits.get(i);

				if (posX == appleCurrent.getPosX() && posY == appleCurrent.getPosY()) {
					if (this.length < this.maxLength)
						this.length++;
					this.score += this.fruits.get(0).getScore();
					this.fruits.remove(0);
					break;
				}
			}

			// Snake colliding with itself
			for (int i = 0; i < this.snake.size(); i++) {
				SnakeBodyPart bodyPart = this.snake.get(i);

				if (i != this.snake.size() - 1 && posX == bodyPart.getPosX() && posY == bodyPart.getPosY()) {
					running = false;
				}
			}

			// Snake outside game area
			if (posX < 1 || posX >= WIDTH / TILE_SIZE - 1 || posY < 2 || posY >= HEIGHT / TILE_SIZE - 1) {
				System.out.println("Game Over");
				running = false;
			}
		}
	}

	private boolean checkCollisionFruit(int x, int y) {
		boolean collides = false;

		for (int i = 0; i < this.snake.size(); i++) {
			SnakeBodyPart bodyPart = this.snake.get(i);

			if (bodyPart.getPosX() == x && bodyPart.getPosY() == y)
				collides = true;
		}
		return collides;
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

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics2D g) {
		if (running) {
			tick();
			g.clearRect(0, 0, WIDTH, HEIGHT);

			g.setColor(new Color(155, 188, 15));
			g.fillRect(0, 0, WIDTH, HEIGHT);

			// Game Area
			g.setColor(new Color(139, 172, 15));
			g.fillRect(TILE_SIZE, TILE_SIZE * 2, WIDTH - TILE_SIZE * 2, HEIGHT - TILE_SIZE * 3);

			// Edges
			int edgeWidth = 5;
			g.setColor(new Color(15, 56, 15));
			// Left Edge
			g.fillRect(TILE_SIZE - edgeWidth - 4, TILE_SIZE * 2 - edgeWidth - 4, edgeWidth, HEIGHT - TILE_SIZE * 3 + edgeWidth * 2 + 8);

			// Right Edge
			g.fillRect(WIDTH - TILE_SIZE + 4, TILE_SIZE * 2 - edgeWidth - 4, edgeWidth, HEIGHT - TILE_SIZE * 3 + edgeWidth * 2 + 8);

			// Top Edge
			g.fillRect(TILE_SIZE - 4, TILE_SIZE * 2 - edgeWidth - 4, WIDTH - TILE_SIZE - edgeWidth * 4 + 8, edgeWidth);

			// Bottom Edge
			g.fillRect(TILE_SIZE - 4, HEIGHT - TILE_SIZE + 4, WIDTH - TILE_SIZE - edgeWidth * 4 + 8, edgeWidth);

			int innerEdgeWidth = 3;
			g.setColor(new Color(48, 98, 48));
			g.fillRect(TILE_SIZE - 3, TILE_SIZE * 2 - 3, innerEdgeWidth, HEIGHT - TILE_SIZE * innerEdgeWidth + 6);

			g.fillRect(WIDTH - TILE_SIZE - innerEdgeWidth + 3, TILE_SIZE * 2 - 3, innerEdgeWidth, HEIGHT - TILE_SIZE * innerEdgeWidth + 6);

			g.fillRect(TILE_SIZE - 3, TILE_SIZE * 2 - 3, WIDTH - TILE_SIZE * 2 + 6, innerEdgeWidth);

			g.fillRect(TILE_SIZE - 3, HEIGHT - TILE_SIZE - innerEdgeWidth + 3, WIDTH - TILE_SIZE * 2 + 6, innerEdgeWidth);

			// Draw the snake
			for (int i = 0; i < this.snake.size(); i++)
				this.snake.get(i).draw(g);

			// Draw the fruits
			for (int i = 0; i < this.fruits.size(); i++)
				this.fruits.get(i).draw(g);

			// Draw score/length
			g.setColor(new Color(15, 56, 15));
			g.setFont(new Font("Impact", Font.PLAIN, TILE_SIZE));
			g.drawString("Score: " + this.score, TILE_SIZE / 2, TILE_SIZE + g.getFontMetrics().getDescent());
		} else {
			g.setColor(new Color(15, 56, 15));
			g.setFont(new Font("Impact", Font.PLAIN, TILE_SIZE * 3));
			g.drawString("Game Over", (GamePanel.width - g.getFontMetrics().stringWidth("Game Over")) / 2, GamePanel.height / 2);
			gameOverTicks++;

			if (gameOverTicks > 250) {
				gsm.setState(GameStateManager.MAINSTATE);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e, int k) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W && this.nextDirection != Direction.DOWN)
			this.nextDirection = Direction.UP;
		else if (key == KeyEvent.VK_A && this.nextDirection != Direction.RIGHT)
			this.nextDirection = Direction.LEFT;
		else if (key == KeyEvent.VK_S && this.nextDirection != Direction.UP)
			this.nextDirection = Direction.DOWN;
		else if (key == KeyEvent.VK_D && this.nextDirection != Direction.LEFT)
			this.nextDirection = Direction.RIGHT;
		else if (key == KeyEvent.VK_ENTER)
			running = true;
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
}
