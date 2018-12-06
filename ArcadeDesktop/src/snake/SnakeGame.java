package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class SnakeGame extends JPanel implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 640, HEIGHT = 360, TILE_SIZE = 20;

	private Thread thread;
	private boolean running;

	private Random random;
	private Direction direction = Direction.DOWN;

	private SnakeBodyPart head;
	private ArrayList<SnakeBodyPart> snake;

	private SnakeFruit fruit;
	private ArrayList<SnakeFruit> fruits;

	private int ticks = 0;
	private int posX = (WIDTH / TILE_SIZE) / 2, posY = 0, length = 3;

	public SnakeGame() {
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		this.addKeyListener(this);

		this.random = new Random();
		this.snake = new ArrayList<SnakeBodyPart>();
		this.fruits = new ArrayList<SnakeFruit>();

		start();
	}

	public void start() {
		this.running = true;
		this.thread = new Thread(this);
		this.thread.start();
	}

	public void stop() {
		this.running = false;
		try {
			this.thread.join();
		} catch (InterruptedException error) {
			error.printStackTrace();
		}
	}

	public void tick() {
		if (this.snake.size() == 0)
			this.snake.add(this.head = new SnakeBodyPart(posX, posY));

		this.ticks++;

		// Move snake
		if (this.ticks > 2500000 / 2) {
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
				System.out.println("Game Over");
				stop();
			}
		}

		// Snake outside game area
		if (posX < 0 || posX >= WIDTH / TILE_SIZE || posY < 0 || posY >= HEIGHT / TILE_SIZE) {
			System.out.println("Game Over");
			stop();
		}
	}

	@Override
	public void paint(Graphics graphics) {
		// Draw the background
		graphics.clearRect(0, 0, WIDTH, HEIGHT);
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);

		// Draw the snake
		for (int i = 0; i < this.snake.size(); i++)
			this.snake.get(i).draw(graphics);

		// Draw the fruits
		for (int i = 0; i < this.fruits.size(); i++)
			this.fruits.get(i).draw(graphics);

		// Draw lines between the tiles
		graphics.setColor(Color.BLACK);
		for (int i = 0; i < WIDTH / TILE_SIZE; i++) {
			graphics.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, HEIGHT);
		}

		for (int i = 0; i < HEIGHT / TILE_SIZE; i++) {
			graphics.drawLine(0, i * TILE_SIZE, WIDTH, i * TILE_SIZE);
		}

		// Draw score/length
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Impact", Font.PLAIN, TILE_SIZE));
		graphics.drawString("Length: " + this.length, TILE_SIZE / 2,
				TILE_SIZE + graphics.getFontMetrics().getDescent());

		if (!running)
			graphics.drawString("Game Over", WIDTH / 2, HEIGHT / 2);
	}

	@Override
	public void run() {
		while (this.running) {
			tick();
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();

		if (key == KeyEvent.VK_W && this.direction != Direction.DOWN)
			this.direction = Direction.UP;
		else if (key == KeyEvent.VK_A && this.direction != Direction.RIGHT)
			this.direction = Direction.LEFT;
		else if (key == KeyEvent.VK_S && this.direction != Direction.UP)
			this.direction = Direction.DOWN;
		else if (key == KeyEvent.VK_D && this.direction != Direction.LEFT)
			this.direction = Direction.RIGHT;
	}

	@Override
	public void keyReleased(KeyEvent event) {

	}

	@Override
	public void keyTyped(KeyEvent event) {

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
}
