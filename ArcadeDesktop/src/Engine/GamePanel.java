package Engine;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements ComponentListener, Runnable, KeyListener, MouseListener, MouseMotionListener  {

	private static final long serialVersionUID = 1L;
	public static int SCALE = 1;
	public static int width;
	public static int height;
	public static Mouse mouse;
	
	private static VolatileImage image;
	private GameStateManager gsm;
	private Thread thread;
	private boolean running;
	
	private final int FPS = 60;
	private double averageFPS;
	
	public GamePanel() {
		super();
		setOpaque(false);
		setPreferredSize(new Dimension(1400, 788));
		addComponentListener(this);
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		width = 1400;
		height = 788;
		mouse = new Mouse(this);
		gsm = new GameStateManager(GameStateManager.MAINSTATE);
	}

	@Override
	public void addNotify() {
		super.addNotify();
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		image = createVolatileImage(600 / SCALE, 400 / SCALE);
		
		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = 30;
		long targetTime = 1000 / FPS;
		
		while(running) {
			startTime = System.nanoTime();
			
			update();
			repaint();
			
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - URDTimeMillis;
			
			try {
				Thread.sleep(waitTime);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			totalTime += System.nanoTime() - startTime;
			frameCount++;
			if(frameCount == maxFrameCount) {
				averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = image.createGraphics();
		g2d.setBackground(new Color(146, 189, 221));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gsm.render(g2d);
		g.drawImage(image, 0, 0, width, height, null);
		g.setColor(Color.white);
		g.drawString("FPS: " + averageFPS, 10, 10);
		g2d.dispose();
	}
	
	private void update() {
		gsm.update();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gsm.mousePressed(e);	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		gsm.mouseReleased(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		gsm.keyPressed(e, e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gsm.keyReleased(e, e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		gsm.mouseMoved(e);
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
	
	}

	@Override
	public void componentResized(ComponentEvent e) {
		if(e.getSource().equals(this)) {
			width = getWidth();
			height = getHeight();
			System.out.println(width + " " + height);
			image = createVolatileImage(width / SCALE, height / SCALE);
		}
	}
	
	@Override
	public void componentShown(ComponentEvent arg0) {

	}
	
	public void scaleChanged() {
		image = createVolatileImage(width / SCALE, height / SCALE);
	}
	
	public static BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
	    int imageWidth  = image.getWidth();
	    int imageHeight = image.getHeight();

	    double scaleX = (double)width/imageWidth;
	    double scaleY = (double)height/imageHeight;
	    AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
	    AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

	    return bilinearScaleOp.filter(
	        image,
	        new BufferedImage(width, height, image.getType()));
	}
	
}