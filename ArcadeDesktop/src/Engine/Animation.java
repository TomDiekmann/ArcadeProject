package Engine;

import java.awt.image.BufferedImage;


public class Animation {

	private int state;
	private int frames;
	private int frame;
	private long delay;
	private long startTime;
	private boolean started;
	private Spritesheet sprite;
	
	public Animation(Spritesheet sprite, long delay, int state, int frames) {
		this.sprite = sprite;
		this.delay = delay;
		this.state = state;
		this.frames = frames;
		startTime = System.currentTimeMillis();
		frame = 0;
		started = true;
	}
	
	public Animation(Spritesheet sprite, long delay) {
		this.started = false;
		this.sprite = sprite;
		this.delay = delay;
	}
	
	public void update() {
		if(started && System.currentTimeMillis() - startTime >= delay) {
			frame++;
			if(frame == frames) frame = 0;
			startTime = System.currentTimeMillis();
		}
	}
	
	public void start(int state, int frames) {
		this.frames = frames;
		started = true;
		startTime = System.currentTimeMillis();
		frame = 0;
	}
	
	public void stop() {
		started = false;
	}
	
	public void setImages(int state, int frames) {
		this.state = state;
		this.frames = frames;
		frame = 0;
		startTime = System.currentTimeMillis();
	}
	
	public BufferedImage getImage() {
		return sprite.getTexture(frame, state);
	}
	
	public int getState() {
		return state;
	}
	
	public void setDelay(long delay) {
		this.delay = delay;
	}
	
}