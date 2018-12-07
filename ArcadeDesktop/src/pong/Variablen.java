package pong;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class Variablen {

	static JFrame jframe1;
	static int bsb = 640;
    static int bsh = 360;
    
	static int x = 20;
	static int y = 125;
	
	static int x1 = 605;
	static int y2= 125;
	
	static int bx = 315;
	static int by = 180;
	
	static int punkte1 = 0;
	static int punkte2 = 0;
	
	static boolean moveup = false;
	static boolean movedown = false;
	
	static int ballx = 1;
	static int bally = -1;
	
	static Font pongpixel;
	
	
	public Variablen() {
		try {
			pongpixel = Font.createFont(Font.TRUETYPE_FONT, new File ("files/Pong/Cc.ttf")).deriveFont(80f);
			System.out.println("Test");
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File ("files/Pong/Cc.ttf")));
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
