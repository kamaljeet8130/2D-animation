package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16;
    final int scale = 3;
    final public int tileSize = originalTileSize*scale;
    final public int maxScreenCol =16;
    final public int maxScreenRow= 12;
    final public int screenWidth = tileSize*maxScreenCol;
    final public int screenHeight = tileSize*maxScreenRow;

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize*maxWorldCol;
    public final int worldHeight = tileSize*maxWorldRow;
    //FPS:
    int FPS = 60;
    //keyHandler :
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    //
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this,keyH);


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

//    @Override
//    public void run() {
//        double drawInterval = 1_00_00_00_00_0/FPS; // 0.016666 per second
//        double nextDrawTime = System.nanoTime()+drawInterval;
//
//
//        while(gameThread!=null){
//
//            update();
//
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime-System.nanoTime();
//                remainingTime = remainingTime/1_00_00_00;
//
//                if(remainingTime<0)
//                    remainingTime=0;
//                Thread.sleep((long) remainingTime);
//                nextDrawTime+=drawInterval;
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    // ALTERNATE WAY TO CREATE GAME LOOP USING ACCUMULATOR:
    @Override
    public void run(){
        double drawInterval = 1_00_00_00_00_0/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (gameThread!=null){
            currentTime = System.nanoTime();
            delta+= (currentTime-lastTime)/drawInterval;
            timer+=(currentTime-lastTime);
            lastTime = currentTime;
            if(delta>=1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer>=1_000_000_000){
                System.out.println("FPS : " + drawCount);
                drawCount =0;
                timer = 0;
            }
        }
    }
    public void update(){
        player.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tileM.draw(g2);
        player.draw(g2);
        g2.dispose();

    }
}
