package Tron;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import Engine.GamePanel;
import Engine.GameStateManager;

public class TronState extends Engine.State {
	public static final int WIDTH = 640, HEIGHT = 360, TILE_SIZE = 10;

	private Thread thread;
	private boolean running;

	public static HashMap<Integer, HashMap<Integer, Boolean>> gameMap = new HashMap<Integer, HashMap<Integer, Boolean>>();

	private Direction direction = Direction.RIGHT;
	private Direction nextDirection = direction;

	private BodyPart head;
	private ArrayList<BodyPart> snake;

	private ArrayList<Enemy> enemies;

	public static int ticks = 0;
	private int posX = 3, posY = 3;

	private int gameOverTicks = 0;

	public TronState(GameStateManager gsm) {
		super(gsm);

		for (int i = 0; i < WIDTH / TILE_SIZE; i++) {
			gameMap.put(i, new HashMap<Integer, Boolean>());

			for (int j = 0; j < HEIGHT / TILE_SIZE; j++) {
				gameMap.get(i).put(j, false);
			}
		}

		this.snake = new ArrayList<BodyPart>();
		this.enemies = new ArrayList<Enemy>();
		this.enemies.add(new Enemy(WIDTH / TILE_SIZE - 3, 3, Direction.DOWN, Color.ORANGE));
		this.enemies.add(new Enemy(3, HEIGHT / TILE_SIZE - 3, Direction.UP, Color.CYAN));
		this.enemies.add(new Enemy(WIDTH / TILE_SIZE - 3, HEIGHT / TILE_SIZE - 3, Direction.LEFT, Color.MAGENTA));

		TronState.gameMap.get(this.posX).replace(this.posY, true);
		start();
	}

	public void start() {
		this.running = true;
		this.thread = new Thread();
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

	@Override
	public void update() {
		if (running) {
			if (this.snake.size() == 0)
				this.snake.add(this.head = new BodyPart(posX, posY, Color.RED));

			ticks++;

			if (ticks > 3) {
				for (Enemy enemy : this.enemies)
					enemy.tick();

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

				ticks = 0;
				this.head = new BodyPart(this.posX, this.posY, Color.RED);
				this.snake.add(this.head);

				if (TronState.gameMap.get(this.posX) != null && TronState.gameMap.get(this.posX).get(this.posY) != null
						&& TronState.gameMap.get(this.posX).get(this.posY)) {
					stop();
					return;
				}

				// Snake outside game area
				if (posX < 0 || posX >= WIDTH / TILE_SIZE || posY < 0 || posY >= HEIGHT / TILE_SIZE) {
					stop();
					return;
				}

				TronState.gameMap.get(this.posX).replace(this.posY, true);
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(new Color(30, 30, 30));
		for (int i = 0; i < WIDTH / (TILE_SIZE * 4); i++) {
			g.drawRect(i * (TILE_SIZE * 4), 0, 1, HEIGHT);
		}
		
		for (int i = 0; i < HEIGHT / (TILE_SIZE * 4); i++) {
			g.drawRect(0, i * (TILE_SIZE * 4), WIDTH, 1);
		}

		// Draw the snake
		for (int i = 0; i < this.snake.size(); i++)
			this.snake.get(i).draw(g);

		for (Enemy enemy : this.enemies)
			enemy.draw(g);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Impact", Font.PLAIN, TILE_SIZE));

		if (!running) {
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
			this.nextDirection = Direction.UP;
		else if (key == KeyEvent.VK_A && this.direction != Direction.RIGHT)
			this.nextDirection = Direction.LEFT;
		else if (key == KeyEvent.VK_S && this.direction != Direction.UP)
			this.nextDirection = Direction.DOWN;
		else if (key == KeyEvent.VK_D && this.direction != Direction.LEFT)
			this.nextDirection = Direction.RIGHT;
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
