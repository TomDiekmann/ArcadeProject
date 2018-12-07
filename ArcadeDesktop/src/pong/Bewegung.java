package pong;

import java.util.Timer;
import java.util.TimerTask;

public class Bewegung {
Timer bew;
Timer bewb;
Timer bw;
public Bewegung()
{
	bew = new Timer();
	bew.scheduleAtFixedRate(new TimerTask()
			{
		public void run()
		{
			if(Variablen.moveup == true)
			{
				if(Variablen.y >=10)
				{
					Variablen.y -= 2;
				}else if(Variablen.movedown == true)
				{
					if(Variablen.y <= Variablen.bsh - 200)
					{
						Variablen.y += 2;
				}
			}
		}
			}
			}, 0, 10);
	bewb = new Timer();
	bewb.scheduleAtFixedRate(new TimerTask()
			{
		
		public void run()
		{
			Variablen.bx += Variablen.ballx;
			Variablen.by += Variablen.bally;
			
			}
			}, 1500, 8);
	bw = new Timer();
	bw.scheduleAtFixedRate(new TimerTask()
			{
		
		public void run()
		{
			if(Variablen.by+20 >= Variablen.bsh)
			{
				Variablen.bally = -1;
			}
			if(Variablen.by <= 0)
			{
				Variablen.bally = 1;
			}
			if(Variablen.bx +10 >= Variablen.bsb)
			{
				Variablen.bx = Variablen.bsb/2 -10;
				Variablen.by = Variablen.bsh/2 -10;
				Variablen.ballx = -1;
			}
			if(Variablen.bx  <= 0)
			{
				Variablen.bx = Variablen.bsb/2 -10;
				Variablen.by = Variablen.bsh/2 -10;
				Variablen.ballx = 1;
			}
			}
			}, 1500, 4);
	
}
}

