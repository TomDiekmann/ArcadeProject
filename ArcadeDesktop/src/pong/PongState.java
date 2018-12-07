package pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Engine.GamePanel;
import Engine.GameStateManager;
import Engine.State;

public class PongState extends State {

	public PongState(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.clearRect(0, 0, 640, 360);
		g.fillRect(0, 0, 640, 360);
		g.setColor(Color.WHITE);
		
		for(int z = 0; z <= 30; z++)
		{
			g.fillRect(GamePanel.width/2-5, z*20, 10, 10);
		}
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
