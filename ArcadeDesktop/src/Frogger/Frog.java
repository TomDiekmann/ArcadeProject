package Frogger;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Engine.Game;

public class Frog {
  
  private float frogX;
  private float frogY;
  private Direction direction = Direction.UP;
  
  public static BufferedImage up;
  public static BufferedImage down;
  public static BufferedImage right;
  public static BufferedImage left;
  
  public Frog(float pFrogX, float pFrogY) {
    setFrogX(pFrogX);
    setFrogY(pFrogY);
    up = Game.imageLoader.load("images/Frogger/frog_up_0.png");
    down = Game.imageLoader.load("images/Frogger/frog_down_0.png");
    right = Game.imageLoader.load("images/Frogger/frog_right_0.png");
   	left = Game.imageLoader.load("images/Frogger/frog_left_0.png");
  }
  
  public void render(Graphics2D g) {
	  switch (direction) {
		  case UP: g.drawImage(up, (int)frogX, (int)frogY, null);
		  break;
		  case DOWN: g.drawImage(down, (int)frogX, (int)frogY, null);
		  break;
		  case RIGHT: g.drawImage(right, (int)frogX, (int)frogY, null);
		  break;
		  case LEFT: g.drawImage(left, (int)frogX, (int)frogY, null);
		  break;
	  }
  }

  public float getFrogX() {
    return frogX;
  }

  public void setFrogX(float frogX) {
    this.frogX = frogX;
  }

  public float getFrogY() {
    return frogY;
  }

  public void setFrogY(float frogY) {
    this.frogY = frogY;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }
}
