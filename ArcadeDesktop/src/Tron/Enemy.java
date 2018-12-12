package Tron;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {
	private BodyPart head;
	private ArrayList<BodyPart> body;
	private int posX, posY;
	private Direction direction;
	private Color color;
	private boolean active = true;
	private Random rand;

	public Enemy(int posX, int posY, Direction dir, Color color) {
		this.posX = posX;
		this.posY = posY;
		this.direction = dir;
		this.color = color;
		this.rand = new Random();

		this.body = new ArrayList<BodyPart>();
		TronState.gameMap.get(this.posX).replace(this.posY, true);
	}

	public void tick() {
		if (active) {
			if (this.body.size() == 0)
				this.body.add(this.head = new BodyPart(posX, posY, color));

			if (this.rand.nextInt(20) == 0)
				this.moveLeftOrRight();

			int[] dirFront = Direction.getValueFromDirection(this.direction);
			boolean collisionFront = TronState.gameMap.get(this.posX + dirFront[0]) != null ? TronState.gameMap.get(this.posX + dirFront[0]).get(this.posY + dirFront[1]) != null ? TronState.gameMap.get(this.posX + dirFront[0]).get(this.posY + dirFront[1]) : true : true;

			if (collisionFront)
				this.moveLeftOrRight();

			switch (this.direction) {
			case RIGHT:
				this.posX++;
				break;

			case LEFT:
				this.posX--;
				break;

			case UP:
				this.posY--;
				break;

			case DOWN:
				this.posY++;
				break;
			}

			this.head = new BodyPart(this.posX, this.posY, color);
			this.body.add(this.head);

			// Snake outside game area
			if (posX < 0 || posX >= TronState.WIDTH / TronState.TILE_SIZE || posY < 0 || posY >= TronState.HEIGHT / TronState.TILE_SIZE) {
				active = false;
				return;
			}

			if (TronState.gameMap.get(this.posX) != null && TronState.gameMap.get(this.posX).get(this.posY) != null && TronState.gameMap.get(this.posX).get(this.posY)) {
				active = false;
				return;
			}
			TronState.gameMap.get(this.posX).replace(this.posY, true);
		}
	}

	public void draw(Graphics graphics) {
		for (int i = 0; i < this.body.size(); i++)
			this.body.get(i).draw(graphics);
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public ArrayList<BodyPart> getBody() {
		return body;
	}

	public void setBody(ArrayList<BodyPart> body) {
		this.body = body;
	}

	public BodyPart getHead() {
		return head;
	}

	public void setHead(BodyPart head) {
		this.head = head;
	}
	
	private void moveLeftOrRight() {
		int[] dirLeft = Direction.getValueFromDirection(Direction.rotate270(this.direction));
		int[] dirRight = Direction.getValueFromDirection(Direction.rotate90(this.direction));

		boolean collisionLeft = TronState.gameMap.get(this.posX + dirLeft[0]) != null ? TronState.gameMap.get(this.posX + dirLeft[0]).get(this.posY + dirLeft[1]) != null ? TronState.gameMap.get(this.posX + dirLeft[0]).get(this.posY + dirLeft[1]) : true : true;
		boolean collisionRight = TronState.gameMap.get(this.posX + dirRight[0]) != null ? TronState.gameMap.get(this.posX + dirRight[0]).get(this.posY + dirRight[1]) != null ? TronState.gameMap.get(this.posX + dirRight[0]).get(this.posY + dirRight[1]) : true : true;

		if (collisionLeft && !collisionRight)
			this.direction = Direction.rotate90(this.direction);
		else if (!collisionLeft && collisionRight)
			this.direction = Direction.rotate270(this.direction);
		else if (!collisionLeft && !collisionRight) {
			if (this.rand.nextInt(2) == 0)
				this.direction = Direction.rotate90(this.direction);
			else
				this.direction = Direction.rotate270(this.direction);
		}
	}
}
