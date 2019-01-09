package pong;

import java.util.Timer;
import java.util.TimerTask;

public class Bewegung {
  Timer bew;
  Timer bewb;
  Timer bw;
  Timer bws;

  public Bewegung() {

    // spielerbewegung
    bew = new Timer();
    bew.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        if (Variablen.moveup1 == true) {
          if (Variablen.y1 >= 10) {
            Variablen.y1 -= 2;
          }
        } else if (Variablen.movedown1 == true) {
          if (Variablen.y1 <= Variablen.bsh - 120) {
            Variablen.y1 += 2;
          }
        }
        if (Variablen.moveup2 == true) {
          if (Variablen.y2 >= 10) {
            Variablen.y2 -= 2;
          }
        } else if (Variablen.movedown2 == true) {
          if (Variablen.y2 <= Variablen.bsh - 120) {
            Variablen.y2 += 2;
          }
        }

      }
    }, 0, 10);

    // ballgeschwindigkeit
    bewb = new Timer();
    bewb.scheduleAtFixedRate(new TimerTask() {

      public void run() {
        Variablen.bx += Variablen.ballx;
        Variablen.by += Variablen.bally;

      }
    }, 1500, 8);

    // ballkollision
    bw = new Timer();
    bw.scheduleAtFixedRate(new TimerTask() {

      public void run() {
        if (Variablen.by + 20 >= Variablen.bsh) {
          Variablen.bally = -1;
        }
        if (Variablen.by <= 0) {
          Variablen.bally = 1;
        }
        if (Variablen.bx + 10 >= Variablen.bsb) {
          Variablen.bx = Variablen.bsb / 2 - 10;
          Variablen.by = Variablen.bsh / 2 - 10;
          Variablen.ballx = -1;
                    Variablen.punkte1++;
        }
        if (Variablen.bx <= 0) {
          Variablen.bx = Variablen.bsb / 2 - 10;
          Variablen.by = Variablen.bsh / 2 - 10;
          Variablen.ballx = 1;
                    Variablen.punkte2++;
        }
        if(Variablen.bx <= 35 && Variablen.y1 <= Variablen.by && Variablen.y1 +90 >= Variablen.by ){
          Variablen.ballx = 1;
        }
        if(Variablen.bx >= 585 && Variablen.y2 <= Variablen.by && Variablen.y2 +90 >= Variablen.by ){
          Variablen.ballx = -1;
        }
      }
    }, 1500, 4);
    bws = new Timer();
    bws.scheduleAtFixedRate(new TimerTask() {
      public void run(){
       if(Variablen.y1 >= Variablen.by && Variablen.y1 + 90 <= Variablen.by && Variablen.x1 == Variablen.bx + 35){
        
        Variablen.bx = Variablen.bsb / 2 - 10;
        Variablen.by = Variablen.bsh / 2 - 10;
        Variablen.ballx = 1;
       }
            if(Variablen.y2 >= Variablen.by && Variablen.y2 + 90 <= Variablen.by && Variablen.x2 == Variablen.bx +20){
              Variablen.bx = Variablen.bsb / 2 - 10;
        Variablen.by = Variablen.bsh / 2 - 10;
        Variablen.ballx = -1;
        }
      }
    }, 0,10);

  }
}
