package SuperMarioBros;

import java.awt.Graphics2D;
import java.awt.Point;

import Engine.Animation;
import Engine.AudioFilePlayer;
import Engine.GamePanel;

public class Block extends GameObject {


	private Material material;
	private AudioFilePlayer soundPlayer = new AudioFilePlayer();

	// BLOCK DESTROYING

	private boolean marker;
	
	private Item itemContent;
	private boolean containsItem;
	private boolean touched;
	private int animPosition = 0;
	private int animTime = 0;

	public Block(Material material, float x, float y, int width, int height) {
		super(x, y, width, height);
		this.material = material;
		marker = false;
		containsItem = false;
		itemContent = null;
		touched = false;
	}

	public void update() {
		/*if(touched) {
			this.y++;
			touched = false;
			System.out.println(y);
		}*/
	}

	public void render(Graphics2D g) {
		if (!touched)
			g.drawImage(material.getTexture(), (int) x - MarioWorldState.camera.getCamX(),
				(int) y - MarioWorldState.camera.getCamY(), null);
		else {
			animTime++;
			if(animTime % 10 == 0) {				
				animPosition = (int) (Math.sin(animTime) * 2);				
				
			}g.drawImage(material.getTexture(), (int) x - MarioWorldState.camera.getCamX(),
					(int) y - MarioWorldState.camera.getCamY() + animPosition, null);
		}

	}


	public void destroyBlock() {
		switch(material.getID()) {
		case 24:
		case 25:
		case 26:
			if(containsItem) {
				if(itemContent == null) {
					if(MarioWorldState.player.playerIsSmall()) {
						MarioWorldState.world.items.add(new Item(Item.Type.Mushroom, this.x, this.y));
					} else {
						MarioWorldState.world.items.add(new Item(Item.Type.FireFlower, this.x, this.y));
					}
				} else {
					MarioWorldState.world.items.add(itemContent);
				}
				new Thread() {
					public void run() {
						soundPlayer.play("sounds/SuperMarioBros/ItemBlockItemOut.wav");
					}
				}.start();
			}
			this.material = Material.BROWN_ITEM_OUT;
			break;
		case 1:
			if(!MarioWorldState.player.playerIsSmall())
				this.material = Material.AIR;
			else {
				touched = true;
			}
				
			break;

		}
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public void setMark(boolean mark) {
		marker = mark;
	}

	public boolean isMarked() {
		return marker;
	}
	
	public void enableItemContent() {
		containsItem = true;
	}
	
	public void setItemContent(Item.Type type) {
		containsItem = true;
		itemContent = new Item(type, this.x, this.y);
	}
}


