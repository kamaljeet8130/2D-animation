package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Font roboto_40,roboto_80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter=0;
    public boolean gameFinished = false;
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");



    public UI(GamePanel gp){
        this.gp = gp;
        roboto_40 = new Font("Roboto",Font.ITALIC,40);
        roboto_80B = new Font("Roboto",Font.BOLD,80);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }
    public void showMessage(String text){
        message = text;
        messageOn=true;

    }
    public void draw(Graphics2D g2){
        if(gameFinished){
            g2.setFont(roboto_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;
            text = "You Found The Treasure";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
             x = gp.screenWidth/2-textLength/2;
             y = gp.screenHeight/2-(gp.tileSize*3);
             g2.drawString(text,x,y);

            text = "Your Time is:" + dFormat.format(playTime) + " seconds!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2-textLength/2;
            y = gp.screenHeight/2+ (gp.tileSize*4);
            g2.drawString(text,x,y);

            g2.setFont(roboto_80B);
            g2.setColor(Color.YELLOW);
            text = "Congratulations!!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2-textLength/2;
            y = gp.screenHeight/2+(gp.tileSize*2);
            g2.drawString(text,x,y);
            //STOP THREAD
            gp.gameThread= null;
        }
        else {
            g2.setFont(roboto_40);
            g2.setColor(Color.WHITE);
            g2.drawImage(keyImage,gp.tileSize/2,gp.tileSize/2,gp.tileSize,gp.tileSize,null);
            g2.drawString(" X " + gp.player.hasKey,72,65);
            playTime+= (double)1/60;
            g2.drawString("Time: "+ dFormat.format(playTime),gp.tileSize*11,65);
            if(messageOn){
                g2.drawString(message,gp.tileSize/2,gp.tileSize*8);
                g2.setFont(g2.getFont().deriveFont(50F));
                messageCounter++;
                if(messageCounter>120){
                    messageCounter=0;
                    messageOn=false;
                }
        }
        }
    }

}
