package SuperMarioBros;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

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
	private static boolean stopTime;
	private boolean menuOpen = false;
	public static int time;
	private int timeTicks;
	
	public static boolean deadScreen;
	private int deadScreenTicks = 0;

	public MarioWorldState(GameStateManager gsm, String filepath) {
		super(gsm);

		world = new World(filepath);
		player = new Player(64, 160, 8, 31, 2f);
		camera = new Camera(player);
		time = 400;
		timeTicks = 0;
	}

	@Override
	public void update() {
		camera.update();
		player.update();
		
		if(timeTicks == 50) {
			timeTicks = 0;
			time--;
		}
		if(!stopTime && !deadScreen)
			timeTicks++;
	}

	@Override
	public void render(Graphics2D g) {
		g.clearRect(0, 0, GamePanel.width / GamePanel.SCALE, GamePanel.height / GamePanel.SCALE);

		if(!deadScreen) {
			world.render(g);
			player.render(g);
		}
		else {
			if(gsm.marioLives == -1) {
				gsm.setState(GameStateManager.MAINSTATE);
			}
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, GamePanel.width/ GamePanel.SCALE, GamePanel.height/GamePanel.SCALE);
			g.drawImage(Game.imageLoader.load("images/SuperMarioBros/SmallWalking.png").getSubimage(0, 16, 16, 16), (GamePanel.width / 2 - 75 - 16) / GamePanel.SCALE, (GamePanel.height / 2 - 16) / GamePanel.SCALE, null);
			g.setColor(Color.white);
			g.drawString(String.valueOf(gsm.marioLives),(GamePanel.width / 2 + 75) / GamePanel.SCALE , GamePanel.height / 2 / GamePanel.SCALE + g.getFontMetrics().getHeight() / 2 / GamePanel.SCALE);
			g.drawLine(GamePanel.width / 2 / GamePanel.SCALE - 5, GamePanel.height / 2 / GamePanel.SCALE - 5, GamePanel.width / 2 / GamePanel.SCALE + 5, GamePanel.height / 2 / GamePanel.SCALE + 5);
			g.drawLine(GamePanel.width / 2 / GamePanel.SCALE - 5, GamePanel.height / 2 / GamePanel.SCALE + 5, GamePanel.width / 2 / GamePanel.SCALE + 5, GamePanel.height / 2 / GamePanel.SCALE - 5);
			deadScreenTicks++;
			if(deadScreenTicks > 150) {
				Game.gamepanel.gsm.setState(GameStateManager.MARIOWORLD);
				deadScreen = false;
				gsm.marioLives--;
			}
		}
		
		g.setFont(new Font("Arial Black", 10 ,10));
		g.setColor(Color.WHITE);
		g.drawString("MARIO", 20,15);
		String points = String.valueOf(player.getPoints());
		int pointsLength = points.length();
		for(int i = 6; i >pointsLength; i--) {
			points = "0"+points;
		}
		g.drawString(points, 20, 27);
		g.drawString("TIME", GamePanel.width / GamePanel.SCALE - g.getFontMetrics().stringWidth("TIME")-20,15 );
		g.drawString(String.valueOf(time), GamePanel.width / GamePanel.SCALE - g.getFontMetrics().stringWidth(String.valueOf(time))-20, 27);
		
		g.drawString("WORLD",GamePanel.width / GamePanel.SCALE / 2 + GamePanel.width / GamePanel.SCALE / 8, 15);
		g.drawString("1-1", (GamePanel.width / GamePanel.SCALE / 2 + GamePanel.width / GamePanel.SCALE / 8) + (g.getFontMetrics().stringWidth("WORLD") - g.getFontMetrics().stringWidth("1-1")) / 2, 27);
		
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

	@Override
	public void stateEnd() {
		// TODO Auto-generated method stub
		world.stopMusic();
	}
	
	public static void stopTimeCounting() {
		stopTime = true;
	}
}
