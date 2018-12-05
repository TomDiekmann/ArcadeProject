package Engine;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Game extends JFrame {
	
	public static ImageLoader imageLoader = new ImageLoader();
	
	public static GamePanel gamepanel;
	public Game() {
		super("Arcade Project");
		
		setLayout(new BorderLayout());
		gamepanel = new GamePanel();
		add(gamepanel, BorderLayout.CENTER);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String args[]) {
		new Game();
	}
}
