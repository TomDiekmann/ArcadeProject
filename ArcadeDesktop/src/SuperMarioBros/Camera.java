package SuperMarioBros;

import Engine.GamePanel;

public class Camera {

	private int camX;
	private int camY;
	private Player player;
	
	public Camera(Player player){
		this.player = player;
		camX = (int)player.getX() - GamePanel.width / 2 / GamePanel.SCALE;
		camY = (int)player.getY() - GamePanel.height / 2 / GamePanel.SCALE;
	}
	
	public void update(){
		camX = (int)player.getX() + player.getWidth() / 2 - GamePanel.width / 2 / GamePanel.SCALE;
		camY = (int)player.getY() + player.getHeight() / 2 - GamePanel.height / 2 / GamePanel.SCALE;
		
	}
	
	public int getCamX(){
		return this.camX;
	}
	
	public int getCamY(){
		return this.camY;
	}
	
	
}
