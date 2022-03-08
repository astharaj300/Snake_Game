package snake.game;

import java.awt.*;
import javax.swing.*;
import javax.tools.Tool;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {
    private Image apple;
    private Image dot;
    private Image head;

    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RANDOM_POSITION = 29;


    private int apple_x;
    private int apple_y;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private int dots;

    private Timer timer;

    Board() {
        addKeyListener(new TAdapter());
        setPreferredSize(new Dimension(300,300));
        setBackground(Color.BLACK);
        setFocusable(true);

        loadImages();
        initGame();
    }
    public void loadImages(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snake/game/icons/apple.png"));
        apple = i1.getImage();

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snake/game/icons/dot.png"));
        dot = i2.getImage();

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snake/game/icons/head.png"));
        head = i3.getImage();
    }
    public void initGame(){
        dots = 3;
        for(int i = 0; i < dots; i++){
            x[i] = 50 - i * DOT_SIZE; 
            y[i] = 50;
        }
        locateApple();
        timer = new Timer(140,this);
        timer.start();
    }

    public void locateApple(){
        int r = (int)(Math.random() * RANDOM_POSITION);
        apple_x = (r * DOT_SIZE);

        r = (int)(Math.random() * RANDOM_POSITION);
        apple_y = (r * DOT_SIZE);

    }
    public void checkApple(){

        if((x[0] == apple_x) && (y[0] == apple_y)){
            dots++;
            locateApple();
        }
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        draw(g);
    }

    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this);

        for(int i = 0 ; i < dots; i++){
            if(i == 0){
                g.drawImage(head,x[i],y[i],this);
            }else{
                g.drawImage(dot,x[i],y[i],this);
            }
            Toolkit.getDefaultToolkit().sync();
        }
        }
        else{
            gameOver(g);
        }
    }

    public void gameOver(Graphics g){

        String msg = "Game Over";

        Font font = new Font("SAN_SERIF",Font.BOLD,14);
        FontMetrics metrics = getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (300 - metrics.stringWidth(msg))/ 2, 300/2);
    }

    public void checkCollision(){

        for(int z = dots; z > 0; z--){
            if((z > 4) && (x[0] == x[z]) && (y[0] == y[z])){
                inGame = false;
            }
        }
        if(y[0] >= 300){
            inGame = false;
        }
        if(x[0] >= 300){
            inGame = false;
        }
        if(x[0] < 0){
            inGame = false;
        }
        if(y[0] < 0){
            inGame = false;
        }

        if(!inGame){
            timer.stop();
        }
    }
    public void move(){

        for(int z = dots; z > 0; z--){
            x[z] = x[z - 1];
            y[z] = y[z - 1];
        }
        if(leftDirection){
            x[0] -= DOT_SIZE;
        }
        if(rightDirection){
            x[0] += DOT_SIZE;
        }
        if(upDirection){
            y[0] -= DOT_SIZE;
        }
        if(downDirection){
            y[0] += DOT_SIZE;
        }
    }
    public void actionPerformed(ActionEvent ae){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();      //component look change
    }
    private class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_UP && (!downDirection)){
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            if(key == KeyEvent.VK_DOWN && (!upDirection)){
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }
        }

    }

}
