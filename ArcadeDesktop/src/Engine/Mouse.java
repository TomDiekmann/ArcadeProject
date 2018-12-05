package Engine;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	public int mouseX;
	public int mouseY;
	public int mouseConvertedX;
	public int mouseConvertedY;
	public int scaledMouseX;
	public int scaledMouseY;
	public boolean pressed;
	
	public GamePanel panel;
	
	public Mouse(GamePanel panel) {
		this.panel = panel;
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
	}
	
	public void addMouseWheelListener(MouseWheelListener listener) {
		panel.addMouseWheelListener(listener);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		setCoordinates(e.getPoint());
		setScaledCoordinates(e.getPoint());
		pressed = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		setCoordinates(e.getPoint());
		setScaledCoordinates(e.getPoint());
		pressed = false;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		setCoordinates(e.getPoint());
		setScaledCoordinates(e.getPoint());
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		setCoordinates(e.getPoint());
		setScaledCoordinates(e.getPoint());
	}
	
	
	private void setCoordinates(Point p) {
		mouseX = p.x;
		mouseY = p.y;
	}
	
	private void setScaledCoordinates(Point p) {
		scaledMouseX = p.x / GamePanel.SCALE;
		scaledMouseY = p.y / GamePanel.SCALE;
	}
	
	//UNUSED METHODS
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
	}
}

