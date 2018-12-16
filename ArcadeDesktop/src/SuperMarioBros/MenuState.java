package SuperMarioBros;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Engine.Game;
import Engine.GamePanel;
import Engine.GameStateManager;
import Engine.Spritesheet;
import Engine.State;

public class MenuState extends State {

	public static Spritesheet sprite = new Spritesheet(Engine.Game.imageLoader.load("images/SuperMarioBros/blocksColored.png"), 33, 16, 16);
	private BufferedImage titleImage;

	public MenuState(GameStateManager gsm) {
		super(gsm);
		titleImage = Game.imageLoader.load("images/SuperMarioBros/titleLogo.png");
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		Game.gamepanel.gsm.getState(GameStateManager.MARIOWORLD).render(g);
		g.drawImage(titleImage, (GamePanel.width / 2 - titleImage.getWidth()) / 2, 10, null);
		try {
			g.setFont(Font.createFont(Font.TRUETYPE_FONT, new File("files/SuperMarioBros/SMB.ttf")).deriveFont(15f));
			g.setColor(Color.WHITE);
			g.drawString("PRESS ENTER TO PLAY", (GamePanel.width / 2 - g.getFontMetrics().stringWidth("PRESS ENTER TO PLAY")) / 2, 130);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void keyPressed(KeyEvent e, int k) {
		if(k == KeyEvent.VK_ENTER) {
			Game.gamepanel.gsm.setState(GameStateManager.MARIOWORLD);
		}

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

	@Override
	public void stateEnd() {
		// TODO Auto-generated method stub
		
	}

}
