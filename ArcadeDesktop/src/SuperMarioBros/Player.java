package SuperMarioBros;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Engine.Game;
import Engine.GamePanel;
import Engine.Spritesheet;


public class Player extends Entity {

	
	private boolean destroyingBlock;
	private static int[] states = {0,1,2,3};
	private static int[] frames = {1,1,2,2};
	
	
	public Player(float x, float y, int width, int height, float speed) {
		super(new Spritesheet(Game.imageLoader.load("images/SuperMarioBros/Walking.png"), 2, 16, 32), x, y, width, height, speed,states, frames);
	}
	
	@Override
	public void update() {
		super.update();
		
		if(destroyingBlock) {
			destroyBlock();
		}
		
		if(falling) {
			RunningMonster tmp = MarioWorldState.world.enemyAt((int)x + width / 2, (int)y + height);
			if(tmp != null)
				tmp.headHit();
		}
		
//		//Check if items nearby
//		for(int i = 0; i < Playstate.world.items.size(); i++) {
//			if(Playstate.world.items.get(i).getX() > (x  - Game.ITEM_SIZE - 8) && Playstate.world.items.get(i).getX() < (x +8)) {
//				if(Playstate.world.items.get(i).getY() > (y - 16) && Playstate.world.items.get(i).getY() < (y + height / 2 + 16)) {
//					Playstate.inventory.addItem(Playstate.world.items.get(i));
//					Playstate.world.items.remove(i);
//				}
//			}
//		}
	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(animation.getImage(), GamePanel.width / 2 / GamePanel.SCALE - width / 2 - 7, 
				GamePanel.height / 2 / GamePanel.SCALE - height / 2, null);
		
	}
	
	//BLOCK DESTROYING
	private void destroyBlock() {
		if(isMouseOnScreen()) {
			if(isBlockInRadius(new Point(GamePanel.mouse.mouseConvertedX, GamePanel.mouse.mouseConvertedY), 2)) {
				MarioWorldState.world.getBlock(GamePanel.mouse.mouseConvertedX, GamePanel.mouse.mouseConvertedY).destroyBlock();	
			}
		}
	}
	
	//CHECK IF BLOCK IS IN RADIUS
	public boolean isBlockInRadius(Point p, int radius) {
		int colstart = MarioWorldState.world.getColTile((int)getCenterX()) - radius;
		int rowstart = MarioWorldState.world.getRowTile((int)getCenterY()) - radius;
		for(int row = 0; row < 2*radius+1; row++) {
			for(int col = 0; col < 2*radius+1; col++) {
				int blockX = colstart+col;
				int blockY = rowstart+row;
				if(blockX >= 0 && blockY >= 0 && blockX < MarioWorldState.world.getBlocksX() && blockY < MarioWorldState.world.getBlocksY()) {
					if(MarioWorldState.world.getBlocks()[blockY][blockX].getBox().contains(p)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	//CHECK IF MOUSE IS INSIDE WORLD
	private boolean isMouseOnScreen() {
		World world = MarioWorldState.world;
		int mx = GamePanel.mouse.mouseConvertedX;
		int my = GamePanel.mouse.mouseConvertedY;
		if(mx >= 0 && my >= 0 && mx <= world.getBlocksX() * World.BLOCKSIZE && my <= world.getBlocksY() * World.BLOCKSIZE) {
			return true;
		} else return false;
	}
	
	
	public void keyPressed(KeyEvent e, int k) {
		switch(k) {
		case KeyEvent.VK_A: left = true; break;
		case KeyEvent.VK_D: right = true; break;
		case KeyEvent.VK_SPACE: {
			if(!inWater && !falling && !jumping) jumping = true; 
			else if(inWater) jumping = true;
			break;
		}
		}
	}
	
	
	public void mousePressed(MouseEvent e) {
		

	}
	
	public void keyReleased(KeyEvent e, int k) {
		switch(k) {
		case KeyEvent.VK_A: left = false; break;
		case KeyEvent.VK_D: right = false; break;
		case KeyEvent.VK_SPACE: jumping = false; break;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		destroyingBlock = false;
	}
}
