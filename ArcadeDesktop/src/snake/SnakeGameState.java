package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import Engine.GamePanel;
import Engine.GameStateManager;
import Engine.State;

public class SnakeGameState extends State {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 640, HEIGHT = 360, TILE_SIZE = 20;

	private boolean running;

	private Random random;
	private Direction direction = Direction.DOWN;

	private SnakeBodyPart head;
	private ArrayList<SnakeBodyPart> snake;

	private SnakeFruit fruit;
	private ArrayList<SnakeFruit> fruits;

	private int ticks = 0;
	private int posX = (WIDTH / TILE_SIZE) / 2, posY = 0, length = 3;

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
		}

		// No fruit in game area
		if (this.fruits.size() == 0) {
			int fruitPosX, fruitPosY;

			// Search for fruit position
			do {
				fruitPosX = random.nextInt(WIDTH / TILE_SIZE);
				fruitPosY = random.nextInt(HEIGHT / TILE_SIZE);
			} while (checkCollisionFruit(fruitPosX, fruitPosY));

			this.fruit = new SnakeFruit(fruitPosX, fruitPosY);
			fruits.add(this.fruit);
		}

		// Snake colliding with fruit
		for (int i = 0; i < this.fruits.size(); i++) {
			SnakeFruit appleCurrent = this.fruits.get(i);

			if (posX == appleCurrent.getPosX() && posY == appleCurrent.getPosY()) {
				this.length++;
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
		if (posX < 0 || posX >= WIDTH / TILE_SIZE || posY < 0 || posY >= HEIGHT / TILE_SIZE) {
			System.out.println("Game Over");
			running = false;
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
		LEFT, RIGHT, UP, DOWN
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g) {
		if (running) {
			tick();
			g.clearRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			// Draw the snake
			for (int i = 0; i < this.snake.size(); i++)
				this.snake.get(i).draw(g);

			// Draw the fruits
			for (int i = 0; i < this.fruits.size(); i++)
				this.fruits.get(i).draw(g);

			// Draw lines between the tiles
			g.setColor(Color.BLACK);
			for (int i = 0; i < WIDTH / TILE_SIZE; i++) {
				g.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, HEIGHT);
			}

			for (int i = 0; i < HEIGHT / TILE_SIZE; i++) {
				g.drawLine(0, i * TILE_SIZE, WIDTH, i * TILE_SIZE);
			}

			// Draw score/length
			g.setColor(Color.WHITE);
			g.setFont(new Font("Impact", Font.PLAIN, TILE_SIZE));
			g.drawString("Length: " + this.length, TILE_SIZE / 2, TILE_SIZE + g.getFontMetrics().getDescent());
		} else {
			g.setColor(Color.RED);
			g.setFont(new Font("Arial Black", 1, 25));
			g.drawString("Game Over", (GamePanel.width - g.getFontMetrics().stringWidth("Game Over")) / 2,
					(GamePanel.height - g.getFontMetrics().getHeight()) / 2);
			gameOverTicks++;

			if (gameOverTicks > 100) {

				gsm.setState(GameStateManager.MAINSTATE);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e, int k) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W && this.direction != Direction.DOWN)
			this.direction = Direction.UP;
		else if (key == KeyEvent.VK_A && this.direction != Direction.RIGHT)
			this.direction = Direction.LEFT;
		else if (key == KeyEvent.VK_S && this.direction != Direction.UP)
			this.direction = Direction.DOWN;
		else if (key == KeyEvent.VK_D && this.direction != Direction.LEFT)
			this.direction = Direction.RIGHT;
		else if (key == KeyEvent.VK_ENTER)
			running = true;
	}

	@Override
	public void keyReleased(KeyEvent e, int k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
