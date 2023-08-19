package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    private Image apple;
    private Image dot;
    private Image head;
    private final int All_dots = 2500;
    private final int Dot_size=10;

    private final int x[]=new int[All_dots];
    private final int y[]=new int[All_dots];
    private final int RANDOM_POSITION = 29;
    private int apple_x;
    private int apple_y;
    private Boolean LeftDirection = false;
    private Boolean rightDirection = true;
    private Boolean upDirection = false;
    private Boolean downDirection = false;
    private int dots;
    private Timer timer;
    private boolean inGame = true;
    private boolean ispaused = false;
    private boolean gameEnded =false;
    Board(){
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(500, 500));
        loadImages();
        initGAme();
        restartGame();
    }
    public void restartGame() {
        gameEnded = false;
        inGame = true;
        dots = 3;
        // Reset snake and apple positions
        for (int i = 0; i < dots; i++) {
            y[i] = 50;
            x[i] = 50 - i * Dot_size;
        }
        locateApple();
        timer.start();
        repaint();
    }
    
    public void loadImages(){
        ImageIcon I1 = new ImageIcon(ClassLoader.getSystemResource("Snake/icons/apple.png"));
        apple= I1.getImage();
        ImageIcon I2 = new ImageIcon(ClassLoader.getSystemResource("Snake/icons/dot.png"));
        dot= I2.getImage();
        ImageIcon I3 = new ImageIcon(ClassLoader.getSystemResource("Snake/icons/head.png"));
         head=I3.getImage();
    }
    public void initGAme(){
        dots = 3;
        for (int i =0;i<3;i++){
            y[i]=50;
            x[i]=50-i*Dot_size;
        }
        locateApple();

       timer = new Timer(140,this);
       timer.start();
       
    }
    public void locateApple(){
    int r = (int)(Math.random()* RANDOM_POSITION);
    apple_x =r*Dot_size;
     r = (int)(Math.random()* RANDOM_POSITION);
     apple_y =r*Dot_size;


    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if(inGame){
        g.drawImage(apple, apple_x, apple_y, this);
        for (int i=0;i<dots;i++){
            if(i==0){
                g.drawImage(head,x[i],y[i],this);
            }else {
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }else{
        Gameover(g);
    }
    if(ispaused){
        String pauseMsg = "Paused    Press SPACE to Resume";
        Font pauseFont= new Font("sans-serif",Font.BOLD,20);
        FontMetrics pauseMetrics = getFontMetrics(pauseFont);
        g.setColor(Color.white);
        g.setFont(pauseFont);
        g.drawString(pauseMsg,(500 -pauseMetrics.stringWidth(pauseMsg))/2,200);
            

    }
}
public void Gameover(Graphics g){
    String msg = "Game Over - press 'R' to Restart";
    Font font = new Font("sans-serif",Font.BOLD,23);
    FontMetrics metrices =getFontMetrics(font);
    g.setColor(Color.white);
    g.setFont(font);

    g.drawString(msg,(500 -metrices.stringWidth(msg))/2,200);

}
    public void move(){
        for(int i =dots;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

    if(LeftDirection){
        x[0] =x[0]-Dot_size;
    }
    if(rightDirection){
        x[0] =x[0]+Dot_size;
    }
    if(upDirection){
        y[0] =y[0]-Dot_size;
    }
    if(downDirection){
        y[0] =y[0]+Dot_size;
    }
       
    }
    public void checkapple(){
     if(x[0]==apple_x && (y[0]==apple_y)){
       dots++;
       locateApple(); 
     }
    }
    public void checkcollision(){
    for(int i = dots;i>0;i--){
        if((i>4) &&(x[0] ==x[i]) &&(y[0]==y[i])){
            inGame=false;
        }
    }
     if (y[0] >= 500) {
            inGame = false;
        }
        if (x[0] >= 500) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        
        if (!inGame) {
            gameEnded=true;
            timer.stop();
        }
    }

    public void actionPerformed(ActionEvent ae){
        if(inGame && !ispaused){
        checkcollision();
        checkapple();
        move();
        }
        repaint();
      
    }
    public class TAdapter extends  KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
         int key =e.getKeyCode();
         if(key ==KeyEvent.VK_LEFT && (!rightDirection)){
            LeftDirection = true;
            upDirection= false;
            downDirection= false;
         }  
         if(key ==KeyEvent.VK_RIGHT && (!LeftDirection)){
            rightDirection= true;
            upDirection= false;
            downDirection= false;
         }  
         if(key ==KeyEvent.VK_UP && (!downDirection)){
             upDirection= true;
            LeftDirection = false;
            rightDirection= false;
         }  
         if(key ==KeyEvent.VK_DOWN && (!upDirection)){
            LeftDirection = false;
            rightDirection= false;
            downDirection= true;
         }
         if(key==KeyEvent.VK_SPACE) {
            ispaused=!ispaused;
         }
         if (key == KeyEvent.VK_R && gameEnded) {
            restartGame();
        }
        }
        
    }
}