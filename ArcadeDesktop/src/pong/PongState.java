package pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Engine.GamePanel;
import Engine.GameStateManager;
import Engine.State;

public class PongState extends State {
	
	Variablen variablen;
	Bewegung bewegung;
	
	public PongState(GameStateManager gsm) {
		super(gsm);
		variablen = new Variablen();
		bewegung = new Bewegung();
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
		
		g.fillRect(Variablen.x1, Variablen.y1, 15, 110);
		g.fillRect(Variablen.x2, Variablen.y2, 15, 110);
		g.setFont(Variablen.pongpixel);
		g.drawString(""+Variablen.punkte1, Variablen.bsb/2-40, 55);
		g.drawString(""+Variablen.punkte2, Variablen.bsb/2+25, 55);
		g.fillRect(Variablen.bx, Variablen.by, 20, 20);
		
	}

	@Override
	public void keyPressed(KeyEvent e, int k) {
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
		Variablen.moveup1 = true;
		}else if(e.getKeyCode()==KeyEvent.VK_S)
		{
			Variablen.movedown1 = true;
		}
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
		Variablen.moveup2 = true;
		}else if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			Variablen.movedown2 = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e, int k) {
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
		Variablen.moveup1 = false;
		}else if(e.getKeyCode()==KeyEvent.VK_S)
		{
			Variablen.movedown1 = false;
		}
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
		Variablen.moveup2 = false;
		}else if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			Variablen.movedown2 = false;
		}
		
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
