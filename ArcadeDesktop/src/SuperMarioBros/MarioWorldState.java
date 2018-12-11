package SuperMarioBros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Engine.Game;
import Engine.GamePanel;
import Engine.GameStateManager;
import Engine.Mouse;
import Engine.State;

public class MarioWorldState extends State {

	public static final int SCALE = 3;
	public static int mouseX;
	public static int mouseY;
	public static World world;
	public static Player player;
	public static Camera camera;
	public static boolean worldGenerated = false;

	private final int menuButtonHeight = 25;
	private final int menuButtonWidth = 100;
	private Mouse mouse;
	private boolean menuOpen = false;

	private int lanButtonX;
	private int lanButtonY;
	private boolean lanOpen;

	private int endButtonX;
	private int endButtonY;

	public MarioWorldState(GameStateManager gsm, String filepath) {
		super(gsm);

		lanButtonX = GamePanel.width / GamePanel.SCALE / 2 - 50;
		lanButtonY = GamePanel.height / GamePanel.SCALE / 2 - 30;

		endButtonX = GamePanel.width / GamePanel.SCALE / 2 - 50;
		endButtonY = GamePanel.height / GamePanel.SCALE / 2 + 5;

		world = new World(filepath);
		player = new Player(64, 160, 8, 31, 2f);
		camera = new Camera(player);
		mouse = GamePanel.mouse;
	}

	@Override
	public void update() {
		camera.update();
		player.update();
	}

	@Override
	public void render(Graphics2D g) {
		g.clearRect(0, 0, GamePanel.width / GamePanel.SCALE, GamePanel.height / GamePanel.SCALE);

		lanButtonX = GamePanel.width / GamePanel.SCALE / 2 - 50;
		lanButtonY = GamePanel.height / GamePanel.SCALE / 2 - 30;

		endButtonX = GamePanel.width / GamePanel.SCALE / 2 - 50;
		endButtonY = GamePanel.height / GamePanel.SCALE / 2 + 5;

		if (!menuOpen) {
			world.render(g);
			player.render(g);
		}

		if (menuOpen) {
			g.setColor(Color.GRAY);
			if (mouse.scaledMouseX > lanButtonX && mouse.scaledMouseX < lanButtonX + menuButtonWidth
					&& mouse.scaledMouseY > lanButtonY && mouse.scaledMouseY < lanButtonY + menuButtonHeight)
				g.setColor(Color.LIGHT_GRAY);
			if (lanOpen)
				g.setColor(Color.DARK_GRAY);
			g.fill3DRect(lanButtonX, lanButtonY, menuButtonWidth, menuButtonHeight, true);

			g.setColor(Color.GRAY);
			if (mouse.scaledMouseX > endButtonX && mouse.scaledMouseX < endButtonX + menuButtonWidth
					&& mouse.scaledMouseY > endButtonY && mouse.scaledMouseY < endButtonY + menuButtonHeight)
				g.setColor(Color.LIGHT_GRAY);
			g.fill3DRect(endButtonX, endButtonY, menuButtonWidth, menuButtonHeight, true);

			g.setColor(Color.white);
			g.setFont(new Font("Impact", Font.PLAIN, 15));
			g.drawString("Open to LAN", lanButtonX + 16, lanButtonY + 18);
			g.drawString("End Game", endButtonX + 20, endButtonY + 18);
		}

	}

	@Override
	public void keyPressed(KeyEvent e, int k) {
		if (k == KeyEvent.VK_ESCAPE) {
			if (menuOpen)
				menuOpen = false;
			else
				menuOpen = true;
		}
		player.keyPressed(e, k);
	}

	@Override
	public void keyReleased(KeyEvent e, int k) {
		player.keyReleased(e, k);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mousePressed(e);

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		player.mouseReleased(e);

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
}
