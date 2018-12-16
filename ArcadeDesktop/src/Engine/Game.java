package Engine;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import SuperMarioBros.Material;

public class Game extends JFrame {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

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
		setResizable(false);
		setIconImage(imageLoader.load("images/Menu/AGC_small.png"));
	}

	public static void main(String args[]) {
		new Game();
	}

}
