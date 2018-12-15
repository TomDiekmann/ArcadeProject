package Frogger;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Engine.Game;
import Engine.GamePanel;

public class MovingObjects {

  private float currentX;
  public Type type;

  public MovingObjects(Type type) {
    this.type = type;
    if (type.fromleft)
      currentX = GamePanel.width / 2 - FroggerState.getBackground().getWidth() / 2 - type.image.getWidth();
    else
      currentX = GamePanel.width / 2 + FroggerState.getBackground().getWidth() / 2;
  }

  public void render(Graphics2D g) {
    g.drawImage(type.image, (int) currentX, type.startY, null);

  }

  public void update() {
    if (type.fromleft && currentX > GamePanel.width / 2 + FroggerState.getBackground().getWidth() / 2) {
      FroggerState.moving.remove(this);
    } else if (!type.fromleft && currentX + type.image.getWidth() < GamePanel.width / 2
        - FroggerState.getBackground().getWidth() / 2) {
      FroggerState.moving.remove(this);
    }
    if (type.fromleft) {
      currentX += type.speed;
    } else {
      currentX -= type.speed;
    }
  }
  
  public boolean hasCollision(int collisionX, int collisionY) {
	  if(collisionX > currentX && collisionX < currentX + type.image.getWidth() && collisionY > type.startY && collisionY < type.startY + type.image.getWidth()) {
		  return true;
	  }
	  else {
		  return false;
	  }
  }

  public enum Type {
    Log1(Game.imageLoader.load("images/Frogger/log_0.png"), true, 73, 1.25f, false),
    Turtle2(Game.imageLoader.load("images/Frogger/turtle_1.png"), false, 93, 0.7f, false),
    Log3(Game.imageLoader.load("images/Frogger/log_0.png"), true, 118, 0.8f, false),
    Log4(Game.imageLoader.load("images/Frogger/log_0.png"), true, 138, 0.6f, false),
    Turtle5(Game.imageLoader.load("images/Frogger/turtle_1.png"), false, 158, 1f, false),

    Truck6(Game.imageLoader.load("images/Frogger/car_c_0.png"), false, 206, 0.5f, true),
    RaceCar7(Game.imageLoader.load("images/Frogger/car_a_0.png"), true, 226, 2f, true),
    Pink8(Game.imageLoader.load("images/Frogger/car_b_0.png"), false, 250, 1f, true),
    Bagger9(Game.imageLoader.load("images/Frogger/car_d_0.png"), true, 269, 0.7f, true),
    Car10(Game.imageLoader.load("images/Frogger/car_e_0.png"), false, 290, 0.9f, true);

    public BufferedImage image;

    public boolean fromleft;
    public int startY;
    public float speed;
    public boolean car;

    private Type(BufferedImage image, boolean left, int startY, float speed, boolean car) {
      this.image = image;
      this.fromleft = left;
      this.startY = startY;
      this.speed = speed;
      this.car = car;
    }
  }
}