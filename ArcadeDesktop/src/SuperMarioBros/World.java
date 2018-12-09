package SuperMarioBros;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Engine.AudioFilePlayer;
import Engine.Game;
import Engine.GamePanel;

public class World {

	private int blocksX;
	private int blocksY;
	private Block[][] blocks;
	public List<RunningMonster> enemies = new ArrayList<RunningMonster>();
	private int[][] blockIDs;
	public static final int BLOCKSIZE = 16;
	public boolean soundPlayed;
	
	private AudioFilePlayer musicPlayer = new AudioFilePlayer();
	private Thread musicThread;
	
	public World(String filepath) {
		loadWorld(filepath);
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 160, 224));
		enemies.add(new RunningMonster(RunningMonster.Type.KOOPA_TROOPER, 400, 224));
		soundPlayed = false;
		musicThread = new Thread(){
			 public void run(){
				 musicPlayer.play("sounds/SuperMarioBros/01-main-theme-overworld.wav");
			 }
		};
	}


	public void render(Graphics2D g) {
		if(!musicThread.isAlive() && !MarioWorldState.player.died) {
			musicThread.start();
		}
		Player player = MarioWorldState.player;
		int startX = (int) player.getCenterX() - GamePanel.width / GamePanel.SCALE / 2;
		int startY = (int) player.getCenterY() - GamePanel.height / GamePanel.SCALE / 2;
		int endX = (int) player.getCenterX() + GamePanel.width / GamePanel.SCALE / 2 + BLOCKSIZE;
		int endY = (int) player.getCenterY() + GamePanel.height / GamePanel.SCALE / 2 +BLOCKSIZE ;

		for (int row = startY; row <= endY; row += BLOCKSIZE) {
			for (int col = startX; col < endX; col += BLOCKSIZE) {
				int blockX = getColTile(col);
				int blockY = getRowTile(row);
				if (blockX >= 0 && blockY >= 0 && blockX < this.blocksX && blockY < this.blocksY) {
					blocks[blockY][blockX].update();
					blocks[blockY][blockX].render(g);
				}
			}
		}
		
		for(int i = 0; i < enemies.size();i++) {
			RunningMonster enemy = enemies.get(i);
			enemy.update();
			if(enemy.getX() > startX && enemy.getX() < endX) {
				enemy.render(g, startX, startY);
			}
		}
	}

	public boolean placeBlock(Material material, int x, int y) {
		Block block = getBlock(x, y);
		block.setMaterial(material);
		return true;
	}


	public Block getBlock(int x, int y) {
		int blockx = x / BLOCKSIZE;
		int blocky = y / BLOCKSIZE;
		return blocks[blocky][blockx];
	}

	private void loadWorld(String filepath) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));
			blocksX = Integer.parseInt(reader.readLine());
			blocksY = Integer.parseInt(reader.readLine());
			blocks = new Block[blocksY][blocksX];

			for (int row = 0; row < blocksY; row++) {
				String line = reader.readLine();
				String tokens[] = line.split(" ");
				for (int col = 0; col < blocksX; col++) {
					int id = Integer.parseInt(tokens[col]);
					Material material = Material.values()[id];
					blocks[row][col] = new Block(material, col * BLOCKSIZE, row * BLOCKSIZE, BLOCKSIZE,
							BLOCKSIZE);
				}
			}

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getColTile(int x) {
		return x / BLOCKSIZE;
	}

	public int[][] getBlockIDs() {
		return blockIDs;
	}

	public int getRowTile(int y) {
		return y / BLOCKSIZE;
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public int getBlocksX() {
		return blocksX;
	}

	public int getBlocksY() {
		return blocksY;
	}

	public void setBlock(int x, int y, Block block) {
		blocks[getRowTile(y)][getColTile(x)] = block;
	}
	
	public RunningMonster enemyAt(int x, int y) {
		for(int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).getX() <= x && enemies.get(i).getX() + enemies.get(i).getWidth()  >= x) {
				if(enemies.get(i).getY() <= y && enemies.get(i).getY() + enemies.get(i).getHeight()  >= y) {
					return enemies.get(i);
				}
			}
		}
		return null;
	}
	
	public void stopMusic(){
		musicPlayer.stop();
	}
}
