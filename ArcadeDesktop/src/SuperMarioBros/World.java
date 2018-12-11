package SuperMarioBros;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Engine.AudioFilePlayer;
import Engine.Game;
import Engine.GamePanel;

public class World {

	private int blocksX;
	private int blocksY;
	public Block[][] blocks;
	public List<RunningMonster> enemies = new ArrayList<RunningMonster>();
	private int[][] blockIDs;
	public static final int BLOCKSIZE = 16;
	public boolean soundPlayed;

	private AudioFilePlayer musicPlayer = new AudioFilePlayer();
	private Thread musicThread;

	public World(String filepath) {
		loadWorld2();
		loadWorld("files/SuperMarioBros/world.txt");
		enemies.add(new RunningMonster(RunningMonster.Type.KOOPA_TROOPER, 150, 168));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 352, 176));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 640, 176));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 816, 176));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 848, 176));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 1280, 48));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 1312, 48));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 1552, 176));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 1572, 176));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 1822, 176));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 1848, 176));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 1967, 176));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 1993, 176));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 2046, 176));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 2074, 176));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 2775, 176));
		enemies.add(new RunningMonster(RunningMonster.Type.GOOMBA, 2810, 176));
		enemies.add(new RunningMonster(RunningMonster.Type.KOOPA_TROOPER, 1714, 168));
		soundPlayed = false;
		musicThread = new Thread() {
			public void run() {
				musicPlayer.play("sounds/SuperMarioBros/01-main-theme-overworld.wav");
			}
		};
	}

	public void render(Graphics2D g) {
		if (!musicThread.isAlive() && !MarioWorldState.player.died) {
			musicThread.start();
		}

		g.setColor(new Color(112, 140, 255));
		g.fillRect(0, 0, Game.gamepanel.width, Game.gamepanel.height);

		Player player = MarioWorldState.player;
		int startX = MarioWorldState.camera.getCamX();
		int startY = MarioWorldState.camera.getCamY();
		int endX = MarioWorldState.camera.getCamX() + GamePanel.width / GamePanel.SCALE + 16;
		int endY = MarioWorldState.camera.getCamY() + GamePanel.height / GamePanel.SCALE + 16;

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

		for (int i = 0; i < enemies.size(); i++) {
			RunningMonster enemy = enemies.get(i);
			enemy.update();
			if (enemy.getX() > startX && enemy.getX() < endX) {
				enemy.render(g, startX, startY);
				enemy.triggerMoving();
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
					blocks[row][col] = new Block(material, col * BLOCKSIZE, row * BLOCKSIZE, BLOCKSIZE, BLOCKSIZE);
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

	private void loadWorld2() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("files/SuperMarioBros/world.txt")));
			BufferedImage world = Game.imageLoader.load("images/SuperMarioBros/World1Test.png");
			writer.write((world.getWidth() - 16) / 16 + "\n");
			writer.write(((int) world.getHeight() / 16 + 1) + "\n");
			blocks = new Block[world.getHeight() / 16][world.getWidth() / 16];
			for (int blockY = 0; blockY < world.getHeight(); blockY += 16) {
				for (int blockX = 0; blockX < world.getWidth() - 16; blockX += 16) {
					BufferedImage blockImage;
					if (blockY == world.getHeight() - 8) {
						blockImage = world.getSubimage(blockX, blockY - 16, 16, 16);
					} else {
						blockImage = world.getSubimage(blockX, blockY, 16, 16);
					}
					Material[] materials = Material.values();
					for (int i = 0; i < materials.length; i++) {
						if (compareImages(materials[i].getTexture(), blockImage)) {
							writer.write(i + " ");
							break;
						}
						if (i == materials.length - 1)
							writer.write(0 + " ");
					}
				}
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).getX() <= x && enemies.get(i).getX() + enemies.get(i).getWidth() >= x) {
				if (enemies.get(i).getY() <= y && enemies.get(i).getY() + enemies.get(i).getHeight() >= y) {
					return enemies.get(i);
				}
			}
		}
		return null;
	}

	public void stopMusic() {
		musicPlayer.stop();
	}

	public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
		// The images must be the same size.
		if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
			return false;
		}

		int width = imgA.getWidth();
		int height = imgA.getHeight();

		// Loop over every pixel.
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// Compare the pixels for equality.
				if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
					return false;
				}
			}
		}

		return true;
	}
}
