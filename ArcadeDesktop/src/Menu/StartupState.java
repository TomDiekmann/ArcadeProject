package Menu;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Engine.Game;
import Engine.GamePanel;
import Engine.GameStateManager;
import Engine.State;

public class StartupState extends State{
	BufferedImage logo = Game.imageLoader.load("images/logo.png");
	private float alpha = 0;
	private float diff = 0.005f;

	public StartupState(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, GamePanel.width, GamePanel.height);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.drawImage(logo, 20, 20, null);
		
		alpha += diff;
		if (alpha >= 1) {
			alpha = 1;
		    diff = -0.005f; 
		}
		else if(alpha <= 0) {
			alpha = 0;
			Game.gamepanel.gsm.setState(GameStateManager.MAINSTATE);
		}
	}

	@Override
	public void stateEnd() {
		// TODO Auto-generated method stub
		
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
