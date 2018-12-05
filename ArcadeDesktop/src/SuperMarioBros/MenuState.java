package SuperMarioBros;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Engine.GameStateManager;
import Engine.Spritesheet;
import Engine.State;


public class MenuState extends State{
	 
	
	public static Spritesheet sprite = new Spritesheet(Engine.Game.imageLoader.load("images/blocks.png"), 33, 16, 16);
	private Material debug;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
		debug = Material.BROWN_BROKEN_GROUND;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(debug.getTexture(), 50, 50, null);
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
