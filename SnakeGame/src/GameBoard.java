import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.Timer;

public class GameBoard extends JPanel implements ActionListener {

    int WIDTH = 600;
    int HEIGHT = 600;
    int UNIT_SIZE = 25;
    int GAME_UNIT = (WIDTH*HEIGHT)/UNIT_SIZE;
   static int DELAY = 125;
    int[] x = new int[GAME_UNIT];
    int[] y = new int[GAME_UNIT];
    int BODY_PARTS = 6;
    int AppleEats;
    int AppleX;
    int AppleY;
    char Direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    GameBoard(){
              random = new Random();
             this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
             this.setBackground(Color.black);
             this.setFocusable(true);
        //     this.requestFocusInWindow();
             this.addKeyListener(new MyKeyAdapter());
             startGame();
    }
        public void startGame(){
             newApple();
             running = true;
             x[0] = 200;
             y[0] = 200;
             timer = new Timer(DELAY,this);
             timer.start();
        }
        public void paintComponent(Graphics g){
             super.paintComponent(g);
             Draw(g);
        }
    public void Draw(Graphics g){
       if (running){
            g.setColor(Color.red);
            g.fillOval(AppleX, AppleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < BODY_PARTS; i++) {
                if (i == 0) {
                    g.setColor(Color.yellow);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        }
       else {
           GameOver(g);
           timer.stop();
       }
    }
        public void newApple(){
         AppleX = random.nextInt((int) (WIDTH/UNIT_SIZE))*UNIT_SIZE;
         AppleY = random.nextInt((int) (HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        }

       public void Move() {
            for (int j = BODY_PARTS-1; j > 0; j--) {
                     x[j] = x[j - 1];
                     y[j] = y[j - 1];
            }

            switch (Direction) {
                case 'U':
                y[0] -= UNIT_SIZE;
                 break;
                 case 'D':
                y[0] += UNIT_SIZE;
                break;
               case 'L':
                x[0] -= UNIT_SIZE;
                break;
               case 'R':
                x[0] += UNIT_SIZE;
                break;
            }
        }

        public void CheckApple(){
            if((x[0]==AppleX) && y[0] == AppleY){
                 BODY_PARTS++;
                 AppleEats++;
                 newApple();
            }
        }
    public void CheckCollision(){

       for(int i = BODY_PARTS;i>0;i--){
        //checking for body collison
        if((x[0] == x[i]) && (y[0]== y[i])){
         running = false;
        }
       }
       //checking border collision
        if(x[0]<0){
            running = false;
        }
        if(x[0]>WIDTH){
            running = false;
        }
        if(y[0]<0){
            running = false;
        }
        if(y[0] > HEIGHT){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void GameOver(Graphics g){
    String msg = "GAME OVER";
    int Score = AppleEats*100;
    String scoreMsg = "SCORE : "+ Integer.toString(Score);
    Font small = new Font("Helvetica",Font.BOLD,20);
    FontMetrics fontMetrics = getFontMetrics(small);
    g.setColor(Color.white);
    g.setFont(small);
    g.drawString(msg,(WIDTH-fontMetrics.stringWidth(msg))/2,HEIGHT/4);
        g.drawString(scoreMsg,(WIDTH-fontMetrics.stringWidth(scoreMsg))/2,3*(HEIGHT/4));

    }

    @Override
    public void actionPerformed(ActionEvent e){

       if(running) {
            Move();
            CheckApple();
            CheckCollision();
        }
       repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent event){
          switch (event.getKeyCode()){
           case KeyEvent.VK_LEFT:
           if(Direction != 'R'){
            Direction = 'L';
           }
           break;

           case KeyEvent.VK_RIGHT:
            if(Direction != 'L'){
             Direction = 'R';
            }
            break;

           case KeyEvent.VK_UP:
            if(Direction != 'D'){
             Direction = 'U';
            }
            break;

           case KeyEvent.VK_DOWN:
            if(Direction != 'U'){
             Direction = 'D';
            }
            break;
          }
        }
    }
}
