package SuperMarioBros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class PointsText {
	
	private int startY;
	private String points;
	private float x;
	private float y;
	
	public PointsText(String points, int startX, int startY) {
		this.points = points;
		this.startY = startY;
		this.x = startX;
		this.y = startY;
	}
	
	public void render(Graphics2D g) {
		g.setFont(new Font("Arial Black", 1 ,7));
		g.setColor(Color.WHITE);
		g.drawString(points, x, y);
		y -= 0.5;
	}
	
	public boolean isDone() {
		return y <= startY - 20;
	}
	
	
}
