package Frogger;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
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
	
	private int titleTicks;
	
	public FroggerState(GameStateManager gsm) {
		super(gsm);
		setBackground(Game.imageLoader.load("images/Frogger/background.png"));
		setTitle(Game.imageLoader.load("images/Frogger/title.png"));
		setControls(Game.imageLoader.load("images/Frogger/frogger_controls.png"));
		setPointtable(Game.imageLoader.load("images/Frogger/frogger_pointtable.png"));
		
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
		
		musicThread = new Thread(){
			 public void run(){
				 musicPlayer.play("sounds/Frogger/StageTheme.wav");
			 }
		};
	}

	@Override
	public void update() {
		if(!musicThread.isAlive()) {
			musicThread = new Thread(){
				 public void run(){
					 musicPlayer.play("sounds/Frogger/StageTheme.wav");
				 }
			};
			musicThread.start();
		}
		
		for(int i = moving.size()-1; i >= 0; i--) {
			moving.get(i).update();
		}
		
		//Truck spawning
		if(truckCounter == 0) {
			moving.add(new MovingObjects(Type.Truck6));
			truckCounter = 225;
		}
		truckCounter--;
		
		//Race Car spawning
		if(raceCounter == 0) {
			moving.add(new MovingObjects(Type.RaceCar7));
			if(raceCounter2 == 0) {
				raceCounter = 100;
				raceCounter2 = 1;
			}
			else {
				raceCounter = 25;
				raceCounter2--;
			}
		}
		raceCounter--;
		
		//Pink Car spawning
		if(pinkCounter == 0) {
			moving.add(new MovingObjects(Type.Pink8));
			if(pinkCounter2 == 0) {
				pinkCounter = 100;
				pinkCounter2 = 2;
			}
			else {
				pinkCounter = 75;
				pinkCounter2--;
			}
		}
		pinkCounter--;
		
		//Bagger spawning
		if(baggerCounter == 0) {
			moving.add(new MovingObjects(Type.Bagger9));
			if(baggerCounter2 == 0) {
				baggerCounter = 150;
				baggerCounter2 = 1;
			}
			else {
				baggerCounter = 100;
				baggerCounter2--;
			}
		}
		baggerCounter--;
		
		//Yellow Car spawning
		if(carCounter == 0) {
			moving.add(new MovingObjects(Type.Car10));
			if(carCounter2 == 0) {
				carCounter = 200;
				carCounter2 = 2;
			}
			else {
				carCounter = 100;
				carCounter2--;
			}
		}
		carCounter--;
		
		//Log spawning
		if(log1Counter == 0) {
			moving.add(new MovingObjects(Type.Log1));
			if(log1Counter2 == 0) {
				log1Counter = 100;
				log1Counter2 = 2;
			}
			else {
				log1Counter = 28;
				log1Counter2--;
			}
		}
		log1Counter--;
				
		//Turtle spawning
		if(turtle2Counter == 0) {
			moving.add(new MovingObjects(Type.Turtle2));
			if(turtle2Counter2 == 0) {
				turtle2Counter = 100;
				turtle2Counter2 = 2;
			}
			else {
				turtle2Counter = 30;
				turtle2Counter2--;
			}
		}
		turtle2Counter--;
		
		//Log spawning
		if(log3Counter == 0) {
			moving.add(new MovingObjects(Type.Log3));
			log3Counter = 225;
		}
		log3Counter--;
		
		//Log spawning
		if(log4Counter == 0) {
			moving.add(new MovingObjects(Type.Log4));
			if(log4Counter2 == 0) {
				log4Counter = 200;
				log4Counter2 = 1;
			}
			else {
				log4Counter = 28;
				log4Counter2--;
			}
		}
		log4Counter--;
		
		//Turtle spawning
		if(turtle5Counter == 0) {
			moving.add(new MovingObjects(Type.Turtle5));
			if(turtle5Counter2 == 0) {
				turtle5Counter = 60;
				turtle5Counter2 = 1;
			}
			else {
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
		if(titleTicks != 0) {
			g.drawImage(getTitle(), GamePanel.width/2-getBackground().getWidth()/2, 0, null);
			titleTicks--;
		}
		else {
			g.drawImage(getBackground(), GamePanel.width/2-getBackground().getWidth()/2, 0, null);
			for(int i = moving.size()-1; i >= 0; i--) {
				moving.get(i).render(g);
			}
			g.drawImage(getControls(), 0, 0, null);
			g.drawImage(getPointtable(), GamePanel.width/2+getBackground().getWidth()/2, 0, null);
		}
	}

	@Override
	public void keyPressed(KeyEvent e, int k) {

	}

	@Override
	public void keyReleased(KeyEvent e, int k) {
	
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
}
