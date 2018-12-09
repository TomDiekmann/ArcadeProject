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
	public static ArrayList<MovingObjects> cars;
	
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
	
	public FroggerState(GameStateManager gsm) {
		super(gsm);
		setBackground(Game.imageLoader.load("images/Frogger/background.png"));
		cars = new ArrayList<MovingObjects>();
		truckCounter = 0;
		carCounter = 0;
		carCounter2 = 0;
		baggerCounter = 0;
		baggerCounter2 = 0;
		raceCounter = 0;
		raceCounter2 = 0;
		pinkCounter = 0;
		pinkCounter2 = 0;
		
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
		
		for(int i = 0; i < cars.size(); i++) {
			cars.get(i).update();
		}
		
		//Truck spawning
		if(truckCounter == 0) {
			cars.add(new MovingObjects(Type.Truck6));
			truckCounter = 225;
		}
		truckCounter--;
		
		//Yellow Car spawning
		if(carCounter == 0) {
			cars.add(new MovingObjects(Type.Car10));
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
		
		//Bagger spawning
		if(baggerCounter == 0) {
			cars.add(new MovingObjects(Type.Bagger9));
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
		
		//Race Car spawning
		if(raceCounter == 0) {
			cars.add(new MovingObjects(Type.RaceCar7));
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
			cars.add(new MovingObjects(Type.Pink8));
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
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GamePanel.width, GamePanel.height);
		g.drawImage(getBackground(), GamePanel.width/2-getBackground().getWidth()/2, 0, null);
		for(int i = 0; i < cars.size(); i++) {
			cars.get(i).render(g);
		}
		g.fillRect(0, 0, GamePanel.width/2-getBackground().getWidth()/2, GamePanel.height);
		g.fillRect(GamePanel.width/2+getBackground().getWidth()/2, 0, GamePanel.width/2-getBackground().getWidth()/2, GamePanel.height);
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

}
