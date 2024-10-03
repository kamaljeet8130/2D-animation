package main;

import entity.Player;
import object.SuperObject;
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
    Sound music= new Sound();
    Sound soundEffect = new Sound();

    //

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    public Player player = new Player(this,keyH);
    public SuperObject[] obj = new SuperObject[10];



    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void setupGame(){
        aSetter.setObject();
        playMusic(0);
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
        //tile
        tileM.draw(g2);
        //object
        for (int i = 0;i<obj.length;i++){
            if(obj[i]!=null){
                obj[i].draw(g2,this);
            }
        }
        //player
        player.draw(g2);
        ui.draw(g2);
        g2.dispose();

    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        soundEffect.setFile(i);
        soundEffect.play();


    }
}
