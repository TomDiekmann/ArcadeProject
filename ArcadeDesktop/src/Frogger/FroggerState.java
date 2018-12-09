package Frogger;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Engine.Game;
import Engine.GamePanel;
import Engine.GameStateManager;

public class FroggerState extends Engine.State {

	private BufferedImage background;
	
	public FroggerState(GameStateManager gsm) {
		super(gsm);
		background = Game.imageLoader.load("images/Frogger/background.png");
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GamePanel.width, GamePanel.height);
		g.drawImage(background, GamePanel.width/2-background.getWidth()/2, 0, null);
	}

	@Override
	public void keyPressed(KeyEvent e, int k) {
		// TODO Auto-generated method stub
		
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
