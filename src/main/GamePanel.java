package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize*scale;
    final int maxScreenCol =16;
    final int maxScreenRow= 12;
    final int screenWidth = tileSize*maxScreenCol;
    final int screenHeight = tileSize*maxScreenRow;

    //Fps
    int FPS = 60;


    //keyHandler :
    KeyHandler keyH = new KeyHandler();
    //
    Thread gameThread;
    //set player's default position;
    int playerX=100;
    int playerY=100;
    int playerSpeed = 4;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {
        double drawInterval = 1_00_00_00_00_0/FPS; // 0.016666 per second
        double nextDrawTime = System.nanoTime()+drawInterval;


        while(gameThread!=null){

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime-System.nanoTime();
                remainingTime = remainingTime/1_00_00_00;

                if(remainingTime<0)
                    remainingTime=0;
                Thread.sleep((long) remainingTime);
                nextDrawTime+=drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void update(){
        if(keyH.upPressed){
            playerY-=playerSpeed;
        }
        else if(keyH.downPressed){
            playerY+=playerSpeed;
        }
        else if(keyH.leftPressed){
            playerX-=playerSpeed;
        }
        else if(keyH.rightPressed){
            playerX+=playerSpeed;
        }


    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        g2.fillRect(playerX,playerY,tileSize,tileSize);
        g2.dispose();

    }
}
