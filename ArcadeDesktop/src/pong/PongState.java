package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import Engine.AudioFilePlayer;
import Engine.GamePanel;
import Engine.GameStateManager;
import Engine.State;

public class PongState extends State {
	
	private Engine.AudioFilePlayer soundPlayer = new AudioFilePlayer();
	
	JFrame jframe1;
	private int width = 640;
	private int height = 360;

	private int x1 = 20;
	private int y1 = 125;

	private int x2 = 605;
	private  int y2 = 125;

	private int bx = 315;
	private int by = 180;
	private int counter = 0;
	
	private int punkte1 = 0;
	private int punkte2 = 0;

	private boolean moveup1 = false;
	private boolean movedown1 = false;

	private boolean moveup2 = false;
	private boolean movedown2 = false;

	private float balldirx = 1;
	private float balldiry = -1;
	 
	private Timer spielerb;
	private Timer ballb;
	private Timer wandcol;
	private Timer spielercol;
	
	
	  static Font pongpixel;
	  
	public PongState(GameStateManager gsm) {
		super(gsm);
		try {
			pongpixel = Font.createFont(Font.TRUETYPE_FONT, new File("files/Pong/Cc.ttf")).deriveFont(80f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("files/Pong/Cc.ttf")));
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 // spielerspielerbegung
	    spielerb = new Timer();
	    spielerb.scheduleAtFixedRate(new TimerTask() {
	      public void run() {
	        if (moveup1 == true) {
	          if (y1 >= 10) {
	            y1 -= 2;
	          }
	        } else if (movedown1 == true) {
	          if (y1 <= height - 120) {
	            y1 += 2;
	          }
	        }
	        if (moveup2 == true) {
	          if (y2 >= 10) {
	            y2 -= 2;
	          }
	        } else if (movedown2 == true) {
	          if (y2 <= height - 120) {
	           y2 += 2;
	          }
	        }

	      }
	    }, 0, 10);

	    // ballgeschwindigkeit
	    ballb = new Timer();
	    ballb.scheduleAtFixedRate(new TimerTask() {

	      public void run() {
	        bx += balldirx;
	        by += balldiry;

	      }
	    }, 1500, 8);

	    // ballkollision
	    wandcol = new Timer();
	    wandcol.scheduleAtFixedRate(new TimerTask() {

	      public void run() {
	        if (by + 20 >= height) {
	        	if(balldiry > 0)
	        		balldiry = balldiry * -1;
	        	  new Thread() {
				public void run() {
					soundPlayer.play("sounds/Pong/pong1.wav");
				}
	          }.start();
	        }
	        if (by <= 0) {
	        	if(balldiry < 0) 
	        		balldiry = balldiry * -1;
	          new Thread() {
					public void run() {
						soundPlayer.play("sounds/Pong/pong1.wav");
					}
				}.start();
	        }
	        if (bx + 10 >= width) {
	          bx = width / 2 - 10;
	          by = height / 2 - 10;
	          if(balldirx > 0)
	           balldirx = balldirx * -1;
	          balldirx = 1;
	          balldiry = 1;
	          counter = 0;
	          punkte1++;
	          new Thread() {
					public void run() {
						soundPlayer.play("sounds/Pong/score.wav");
					}
				}.start();

	        }
	        if (bx <= 0) {
	          bx = width / 2 - 10;
	          by = height / 2 - 10;
	          if(balldirx < 0)
	        	  balldirx = balldirx * -1;
	          balldirx = -1;
	          balldiry = 1;
	          counter = 0;
	          punkte2++;
	          new Thread() {
					public void run() {
						soundPlayer.play("sounds/Pong/score.wav");
					}
				}.start();       
	        }
	        
	        if(bx <= 35 && y1 <= by && y1 +90 >= by ){
	        	if(balldirx < 0)
	        		balldirx = balldirx * -1;
	          new Thread() {
					public void run() {
						soundPlayer.play("sounds/Pong/pong2.wav");
					}
				}.start();
	        }
	        if(bx >= 585 && y2 <= by && y2 +90 >= by ){
	        	if(balldirx > 0)
	        		balldirx = balldirx * -1;
	          new Thread() {
					public void run() {
						soundPlayer.play("sounds/Pong/pong2.wav");
					}
				}.start();
	        }
	      }
	    }, 1500, 4);
	    spielercol = new Timer();
	    spielercol.scheduleAtFixedRate(new TimerTask() {
	      public void run(){
	       if(y1 >= by && y1 + 90 <= by && x1 == bx + 35){
	        
	        bx = width / 2 - 10;
	        by = height / 2 - 10;
	        balldirx = balldirx * -1;
	       }
	            if(y2 >= by && y2 + 90 <= by && x2 == bx +20){
	              bx = width / 2 - 10;
	        by = height / 2 - 10;
	        balldirx = balldirx * -1;
	        }
	      }
	    }, 0,10);

	  }
	

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.clearRect(0, 0, 640, 360);
		g.fillRect(0, 0, 640, 360);
		g.setColor(Color.WHITE);

		for (int z = 0; z <= 30; z++) {
			g.fillRect(width / 2 - 5, z * 20, 10, 10);
		}

		g.fillRect(x1, y1, 15, 110);
		g.fillRect(x2, y2, 15, 110);
		g.setFont(pongpixel);
		g.drawString("" + punkte1, width / 2 - 15 - g.getFontMetrics().stringWidth(String.valueOf(punkte1)) , 55);
		g.drawString("" + punkte2, width / 2 + 20, 55);
		g.fillRect(bx, by, 20, 20);

		counter++;
		if(counter == 700){
			counter = 0;
			if(balldirx <= 2 && balldirx > 0) {
				balldirx++;
			}
			else if (balldirx >= -2 && balldirx < 0) {
				balldirx--;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e, int k) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			moveup1 = true;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			movedown1 = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			moveup2 = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			movedown2 = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e, int k) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			moveup1 = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			movedown1 = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			moveup2 = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			movedown2 = false;
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateEnd() {
		ballb.cancel();
		spielerb.cancel();
		spielercol.cancel();
		wandcol.cancel();
	}

}
