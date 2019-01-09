package Frogger;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Engine.AudioFilePlayer;
import Engine.Game;
import Engine.GamePanel;
import Engine.GameStateManager;
import Frogger.MovingObjects.Type;

public class FroggerState extends Engine.State {

  public static BufferedImage background;
  public static BufferedImage title;
  public static BufferedImage controls;
  public static BufferedImage pointtable;
  public static ArrayList<MovingObjects> moving;
  
  public Frog frog;

  private AudioFilePlayer musicPlayer = new AudioFilePlayer();
  private Thread musicThread;

  private int truckCounter;
  private int carCounter;
  private int carCounter2;
  private int baggerCounter;
  private int baggerCounter2;
  private int raceCounter;
  private int raceCounter2;
  private int pinkCounter;
  private int pinkCounter2;
  private int log1Counter;
  private int log1Counter2;
  private int turtle2Counter;
  private int turtle2Counter2;
  private int log3Counter;
  private int log4Counter;
  private int log4Counter2;
  private int turtle5Counter;
  private int turtle5Counter2;
  
  private boolean w = false;
  private boolean a = false;
  private boolean s = false;
  private boolean d = false;

  private int titleTicks;
  private int timeTicks;
  
  private boolean[] zielteiche = new boolean[5];
  private int belegteTeiche = 0;
  
  private int farthestLane = 0;
  private int actualLane = 0;
  
  private int score = 0;
  private int highscore = 0;
  private int timeRectWidth = 0;

  public FroggerState(GameStateManager gsm) {
    super(gsm);
    setBackground(Game.imageLoader.load("images/Frogger/background.png"));
    setTitle(Game.imageLoader.load("images/Frogger/title.png"));
    setControls(Game.imageLoader.load("images/Frogger/controlsWASD.png"));
    setPointtable(Game.imageLoader.load("images/Frogger/frogger_pointtable.png"));
    frog = new Frog(GamePanel.width/2-10, GamePanel.height-55);

    moving = new ArrayList<MovingObjects>();
    truckCounter = 0;
    carCounter = 0;
    carCounter2 = 0;
    baggerCounter = 0;
    baggerCounter2 = 0;
    raceCounter = 0;
    raceCounter2 = 0;
    pinkCounter = 0;
    pinkCounter2 = 0;
    log1Counter = 0;
    log1Counter2 = 0;
    turtle2Counter = 0;
    turtle2Counter2 = 0;
    log3Counter = 0;
    log4Counter = 0;
    log4Counter2 = 0;
    turtle5Counter = 0;
    turtle5Counter2 = 0;

    titleTicks = 150;
    timeTicks = 50;
    
    highscore = loadHighscore();

    musicThread = new Thread() {
      public void run() {
        musicPlayer.play("sounds/Frogger/StageTheme.wav");
      }
    };
  }

  @Override
  public void update() {
    if (!musicThread.isAlive()) {
      musicThread = new Thread() {
        public void run() {
          musicPlayer.play("sounds/Frogger/StageTheme.wav");
        }
      };
      musicThread.start();
    }

    for (int i = moving.size() - 1; i >= 0; i--) {
      moving.get(i).update();
    }

    // Truck spawning
    if (truckCounter == 0) {
      moving.add(new MovingObjects(Type.Truck6));
      truckCounter = 225;
    }
    truckCounter--;

    // Race Car spawning
    if (raceCounter == 0) {
      moving.add(new MovingObjects(Type.RaceCar7));
      if (raceCounter2 == 0) {
        raceCounter = 100;
        raceCounter2 = 1;
      } else {
        raceCounter = 25;
        raceCounter2--;
      }
    }
    raceCounter--;

    // Pink Car spawning
    if (pinkCounter == 0) {
      moving.add(new MovingObjects(Type.Pink8));
      if (pinkCounter2 == 0) {
        pinkCounter = 100;
        pinkCounter2 = 2;
      } else {
        pinkCounter = 75;
        pinkCounter2--;
      }
    }
    pinkCounter--;

    // Bagger spawning
    if (baggerCounter == 0) {
      moving.add(new MovingObjects(Type.Bagger9));
      if (baggerCounter2 == 0) {
        baggerCounter = 150;
        baggerCounter2 = 1;
      } else {
        baggerCounter = 100;
        baggerCounter2--;
      }
    }
    baggerCounter--;

    // Yellow Car spawning
    if (carCounter == 0) {
      moving.add(new MovingObjects(Type.Car10));
      if (carCounter2 == 0) {
        carCounter = 200;
        carCounter2 = 2;
      } else {
        carCounter = 100;
        carCounter2--;
      }
    }
    carCounter--;

    // Log spawning
    if (log1Counter == 0) {
      moving.add(new MovingObjects(Type.Log1));
      if (log1Counter2 == 0) {
        log1Counter = 210;
        log1Counter2 = 2;
      } else {
        log1Counter = 28;
        log1Counter2--;
      }
    }
    log1Counter--;

    // Turtle spawning
    if (turtle2Counter == 0) {
      moving.add(new MovingObjects(Type.Turtle2));
      if (turtle2Counter2 == 0) {
        turtle2Counter = 100;
        turtle2Counter2 = 2;
      } else {
        turtle2Counter = 30;
        turtle2Counter2--;
      }
    }
    turtle2Counter--;

    // Log spawning
    if (log3Counter == 0) {
      moving.add(new MovingObjects(Type.Log3));
      log3Counter = 225;
    }
    log3Counter--;

    // Log spawning
    if (log4Counter == 0) {
      moving.add(new MovingObjects(Type.Log4));
      if (log4Counter2 == 0) {
        log4Counter = 200;
        log4Counter2 = 1;
      } else {
        log4Counter = 28;
        log4Counter2--;
      }
    }
    log4Counter--;

    // Turtle spawning
    if (turtle5Counter == 0) {
      moving.add(new MovingObjects(Type.Turtle5));
      if (turtle5Counter2 == 0) {
        turtle5Counter = 60;
        turtle5Counter2 = 1;
      } else {
        turtle5Counter = 21;
        turtle5Counter2--;
      }
    }
    turtle5Counter--;
  }

  @Override
  public void render(Graphics2D g) {
    g.setColor(Color.black);
    g.fillRect(0, 0, GamePanel.width, GamePanel.height);
    if (titleTicks != 0) {
      g.drawImage(getTitle(), GamePanel.width / 2 - getBackground().getWidth() / 2, 0, null);
      titleTicks--;
    } else {
      g.drawImage(getBackground(), GamePanel.width / 2 - getBackground().getWidth() / 2, 0, null);
      
      boolean hasContact = false;
      float movingLogTurtleSpeed = 0f;
      for (int i = moving.size() - 1; i >= 0; i--) {
        moving.get(i).render(g);
        if(moving.get(i).hasCollision((int)frog.getFrogX()+10, (int)frog.getFrogY()+10) && moving.get(i).type.car) {
        	Game.gamepanel.gsm.setState(GameStateManager.FROGGERSTATE);
        	stateEnd();
        	frog.setDirection(Direction.UP);
          	frog.setFrogX(GamePanel.width/2-10);
          	frog.setFrogY(GamePanel.height-55);
          	if(score > loadHighscore()) {
          		updateHighscore(score);
          	}
          	score = 0;
          	actualLane = 0;
          	farthestLane = 0;
        	break;
        }
        if(frog.getFrogY() <= 175 && moving.get(i).hasCollision((int)frog.getFrogX()+10, (int)frog.getFrogY() +10) && !moving.get(i).type.car && moving.get(i).hasCollision((int)frog.getFrogX()+10, (int)frog.getFrogY() +10)) {
      	  	hasContact = true;
      	  	movingLogTurtleSpeed = moving.get(i).type.speed;
      	  	if(!moving.get(i).type.fromleft) {
      	  		movingLogTurtleSpeed *= -1;
      	  	}
        }
      }
      if(frog.getFrogY() > 175) {
    	  hasContact = true;
      }
      if(hasContact == false) {
    	Game.gamepanel.gsm.setState(GameStateManager.FROGGERSTATE);
      	stateEnd();
      	frog.setDirection(Direction.UP);
      	frog.setFrogX(GamePanel.width/2-10);
      	frog.setFrogY(GamePanel.height-55);
      	if(score > loadHighscore()) {
      		updateHighscore(score);
      	}
      	score = 0;
      	actualLane = 0;
      	farthestLane = 0;
      }
      frog.setFrogX(frog.getFrogX()+movingLogTurtleSpeed);
      frog.render(g);
      g.drawImage(getControls(), 0, 0, null);
      g.drawImage(getPointtable(), GamePanel.width / 2 + getBackground().getWidth() / 2, 0, null);
      g.setColor(Color.yellow);
      Font font = new Font("ArcadeClassic", Font.PLAIN, 20);
      g.setFont(font);
      g.drawString("Score   " + this.score, GamePanel.width / 2 + 70, 13);
      g.setColor(Color.red);
      g.drawString("" + loadHighscore(), GamePanel.width / 2 - getBackground().getWidth() / 2 + 114, 24);
      g.setColor(Color.black);
      if(timeTicks != 0) {
    	  timeTicks--;
      }
      else {
    	  timeTicks = 50;
    	  if(timeRectWidth != 170) {
    		  timeRectWidth++;
    	  }
    	  else {
    		  	timeRectWidth = 0;
    		  	Game.gamepanel.gsm.setState(GameStateManager.FROGGERSTATE);
          		stateEnd();
          		frog.setDirection(Direction.UP);
            	frog.setFrogX(GamePanel.width/2-10);
            	frog.setFrogY(GamePanel.height-55);
            	if(score > loadHighscore()) {
              		updateHighscore(score);
              	}
            	score = 0;  
            	actualLane = 0;
              	farthestLane = 0;
    	  }
      }
      g.fillRect(GamePanel.width / 2 - getBackground().getWidth() / 2 + 100, 347, timeRectWidth, 13);
    }
    for(int i = 0; i < zielteiche.length; i++) {
    	if(zielteiche[i] == true) {
    		g.drawImage(Game.imageLoader.load("images/Frogger/win_0.png"), GamePanel.width / 2 - getBackground().getWidth() / 2 + (i*67) + 11, 45, null);
    	}
    }
  }

  @Override
  public void keyPressed(KeyEvent e, int k) {
    int key = e.getKeyCode();
    
    if(titleTicks == 0 && w == false && a == false && s == false && d == false) {
    	if(key == KeyEvent.VK_W) {
    	      frog.setDirection(Direction.UP);
    	      w = true;
    	      if(!(frog.getFrogY() - 22 < 62)) {
    	    	  frog.setFrogY(frog.getFrogY() - 22);
    	    	  if(actualLane+1 > farthestLane) {
    	    		  score += 10;
    	    		  actualLane++;
    	    		  farthestLane++;
    	    	  }
    	    	  else {
    	    		  actualLane++;
    	    	  }
    	      }
    	      else if(frog.getFrogX() >= GamePanel.width / 2 - getBackground().getWidth() / 2 + 5 && frog.getFrogX() <= GamePanel.width / 2 - getBackground().getWidth() / 2 + 20 && zielteiche[0] == false) {
    	    	  zielteiche[0] = true;
    	    	  belegteTeiche++;
    	    	  score += 60;
    	    	  frog.setDirection(Direction.UP);
    	    	  frog.setFrogX(GamePanel.width/2-10);
    	    	  frog.setFrogY(GamePanel.height-55);
    	      }
    	      else if(frog.getFrogX() >= GamePanel.width / 2 - getBackground().getWidth() / 2 + 73 && frog.getFrogX() <= GamePanel.width / 2 - getBackground().getWidth() / 2 + 88 && zielteiche[1] == false) {
    	    	  zielteiche[1] = true;
    	    	  belegteTeiche++;
    	    	  score += 60;
    	    	  frog.setDirection(Direction.UP);
    	    	  frog.setFrogX(GamePanel.width/2-10);
    	    	  frog.setFrogY(GamePanel.height-55);
    	      }
    	      else if(frog.getFrogX() >= GamePanel.width / 2 - getBackground().getWidth() / 2 + 140 && frog.getFrogX() <= GamePanel.width / 2 - getBackground().getWidth() / 2 + 155 && zielteiche[2] == false) {
    	    	  zielteiche[2] = true;
    	    	  belegteTeiche++;
    	    	  score += 60;
    	    	  frog.setDirection(Direction.UP);
    	    	  frog.setFrogX(GamePanel.width/2-10);
    	    	  frog.setFrogY(GamePanel.height-55);
    	      }
    	      else if(frog.getFrogX() >= GamePanel.width / 2 - getBackground().getWidth() / 2 + 208 && frog.getFrogX() <= GamePanel.width / 2 - getBackground().getWidth() / 2 + 223 && zielteiche[3] == false) {
    	    	  zielteiche[3] = true;
    	    	  belegteTeiche++;
    	    	  score += 60;
    	    	  frog.setDirection(Direction.UP);
    	    	  frog.setFrogX(GamePanel.width/2-10);
    	    	  frog.setFrogY(GamePanel.height-55);
    	      }
    	      else if(frog.getFrogX() >= GamePanel.width / 2 - getBackground().getWidth() / 2 + 275 && frog.getFrogX() <= GamePanel.width / 2 - getBackground().getWidth() / 2 + 290 && zielteiche[4] == false) {
    	    	  zielteiche[4] = true;
    	    	  belegteTeiche++;
    	    	  score += 60;
    	    	  frog.setDirection(Direction.UP);
    	    	  frog.setFrogX(GamePanel.width/2-10);
    	    	  frog.setFrogY(GamePanel.height-55);
    	      }
    	      if(belegteTeiche == 5) {
    	    	  belegteTeiche = 0;
    	    	  score += 1000;
    	    	  for(int i = 0; i < 5; i++) {
    	    		  zielteiche[i] = false;
    	    	  }
    	      }
    	    }
    	    else if(key == KeyEvent.VK_S) {
    	      frog.setDirection(Direction.DOWN);
    	      s = true;
    	      if(!(frog.getFrogY() + 22 > 330)) {
    	    	  frog.setFrogY(frog.getFrogY() + 22);
    	    	  actualLane--;
    	      }	  
    	    }
    	    else if(key == KeyEvent.VK_D) {
    	      frog.setDirection(Direction.RIGHT);
    	      d = true;
    	      if(!(frog.getFrogX() + 22 >= GamePanel.width / 2 + getBackground().getWidth() / 2 - 20)) {
    	    	  frog.setFrogX(frog.getFrogX() + 22);
    	      }	  
    	    }
    	    else if(key == KeyEvent.VK_A) {
    	      frog.setDirection(Direction.LEFT);
    	      a = true;
    	      if(!(frog.getFrogX() - 22 <= GamePanel.width / 2 - getBackground().getWidth() / 2)) {
    	    	  frog.setFrogX(frog.getFrogX() - 22);
    	      }
    	    }
    }
  }

  @Override
  public void keyReleased(KeyEvent e, int k) {
	  int key = e.getKeyCode();
	  if(key == KeyEvent.VK_W) {w = false;}
	  else if(key == KeyEvent.VK_S) {s = false;}
	  else if(key == KeyEvent.VK_D) {d = false;}
	  else if(key == KeyEvent.VK_A) {a = false;}
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseMoved(MouseEvent e) {

  }

  public static BufferedImage getBackground() {
    return background;
  }

  public static void setBackground(BufferedImage background) {
    FroggerState.background = background;
  }

  public static BufferedImage getTitle() {
    return title;
  }

  public static void setTitle(BufferedImage title) {
    FroggerState.title = title;
  }

  public static BufferedImage getControls() {
    return controls;
  }

  public static void setControls(BufferedImage controls) {
    FroggerState.controls = controls;
  }

  public static BufferedImage getPointtable() {
    return pointtable;
  }

  public static void setPointtable(BufferedImage pointtable) {
    FroggerState.pointtable = pointtable;
  }

  @Override
  public void stateEnd() {
    musicPlayer.stop();
  }
  
  public static int loadHighscore() {
		BufferedReader reader;
		int highscore = 0;
		try {
			reader = new BufferedReader(new FileReader(new File("files/Frogger/highscore.txt")));
			highscore = Integer.parseInt(reader.readLine());
			reader.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

		return highscore;
	}

	public void updateHighscore(int newScore) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(new File("files/Frogger/highscore.txt")));
			writer.write("" + newScore);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		highscore = newScore;
	}
}
