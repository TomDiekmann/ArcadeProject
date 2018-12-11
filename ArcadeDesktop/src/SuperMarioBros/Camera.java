package SuperMarioBros;

import Engine.GamePanel;

public class Camera {

	private int camX;
	private int camY;
	private Player player;
	
	public Camera(Player player){
		this.player = player;
		camX = 0;
		camY = 40;
	}
	
	public void update(){
		
		if(!((int)player.getX() + player.getWidth() / 2 - GamePanel.width / 2 / GamePanel.SCALE < camX))
			camX = (int)player.getX() + player.getWidth() / 2 - GamePanel.width / 2 / GamePanel.SCALE;
		
	}
	
	public int getCamX(){
		return this.camX;
	}
	
	public int getCamY(){
		return this.camY;
	}
	
	
}
