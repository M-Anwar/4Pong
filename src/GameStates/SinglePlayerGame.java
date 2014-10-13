/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameStates;

import Engine.GameState;
import Engine.GameStateManager;
import Engine.Geometry.Rectangle;
import Engine.Graphics;
import Engine.Java2DGraphics;
import Engine.Java2DImage;
import Engine.Vector2D;
import Entity.ImageLoader;
import G4Pong.GamePanel;
import static G4Pong.GamePanel.HEIGHT;
import static G4Pong.GamePanel.WIDTH;
import GUI.ButtonListener;
import GUI.Component;
import GUI.GameButton;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author muhammed.anwar
 */
public class SinglePlayerGame extends GameState{
    
    Rectangle r;
    private GameButton btnExit;
    public SinglePlayerGame(GameStateManager gsm) {
        super(gsm);
        init();
    }

    @Override
    public void init() 
    {      
        btnExit = new GameButton("X",GamePanel.WIDTH-40,40);
        addComponent(btnExit);
        btnExit.addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
               gsm.setState(GameStateManager.INTRO_STATE);
            }                
        });
        r = new Rectangle(GamePanel.GAMEWIDTH/2-50, GamePanel.GAMEHEIGHT/2-50,100,100);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        handleInput();         
    }

    @Override
    public void draw(Graphics g) {             
        
        //Draw Score Card
        g.setFont("Arial", Graphics.BOLD, 25);
        g.setColor(Color.WHITE.getRGB());        
        g.drawString("Score:",GamePanel.GAMEWIDTH+20,70);
        g.setFont("Arial",Graphics.PLAIN,15);
        g.setColor(Color.GREEN.getRGB());
        g.drawString("Player 1:",GamePanel.GAMEWIDTH+40,90);
        g.setColor(Color.WHITE.getRGB());        
        g.drawString("CPU 1:",GamePanel.GAMEWIDTH+40,110);
        g.drawString("CPU 2:",GamePanel.GAMEWIDTH+40,130);
        g.drawString("CPU 3:",GamePanel.GAMEWIDTH+40,150);
                         
        //Sets the game area
        g.drawRect(5, 5, GamePanel.GAMEWIDTH,GamePanel.GAMEHEIGHT);
        g.setClip(5, 5, GamePanel.GAMEWIDTH, GamePanel.GAMEHEIGHT);
        g.translate(5, 5);
        
        g.drawRect(r.x, r.y, r.width, r.height);
        Vector2D n = r.getCorners().get(Rectangle.TOPRIGHT).subtract(r.getCorners().get(Rectangle.TOPLEFT));
        Vector2D n1 = n.getPerpendicular();
        n1.normalize();
        n1.thisScale(10);
        g.drawLine(r.x+r.width/2, r.y, r.x+r.width/2 +n1.x, r.y + n1.y);
        
        n = r.getCorners().get(Rectangle.BOTTOMRIGHT).subtract(r.getCorners().get(Rectangle.TOPRIGHT));
        n1 = n.getPerpendicular();
        n1.normalize();
        n1.thisScale(10);
        g.drawLine(r.x+r.width, r.y+r.height/2, r.x+r.width +n1.x, r.y +r.height/2+ n1.y);
        
        n = r.getCorners().get(Rectangle.BOTTOMLEFT).subtract(r.getCorners().get(Rectangle.BOTTOMRIGHT));
        n1 = n.getPerpendicular();
        n1.normalize();
        n1.thisScale(10);
        g.drawLine(r.x+r.width/2, r.y+r.height, r.x+r.width/2 +n1.x, r.y +r.height+ n1.y);
        
        n = r.getCorners().get(Rectangle.TOPLEFT).subtract(r.getCorners().get(Rectangle.BOTTOMLEFT));
        n1 = n.getPerpendicular();
        n1.normalize();
        n1.thisScale(10);
        g.drawLine(r.x, r.y+r.height/2, r.x+n1.x, r.y +r.height/2+ n1.y);
        
        g.translate(-5, -5);
        g.setClip(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);
        float x = WIDTH-ImageLoader.LOGO.getWidth();
        g.drawImage(x, HEIGHT-ImageLoader.LOGO.getHeight(), ImageLoader.LOGO);
        x-=ImageLoader.EXPADDLE.getWidth();
        g.drawImage(x, HEIGHT-ImageLoader.EXPADDLE.getHeight(), ImageLoader.EXPADDLE);
         x-=ImageLoader.SPEED.getWidth();
        g.drawImage(x, HEIGHT-ImageLoader.SPEED.getHeight(), ImageLoader.SPEED);
         x-=ImageLoader.SHIELD.getWidth();
        g.drawImage(x, HEIGHT-ImageLoader.SHIELD.getHeight(), ImageLoader.SHIELD);
         x-=ImageLoader.CURVE.getWidth();
        g.drawImage(x, HEIGHT-ImageLoader.CURVE.getHeight(), ImageLoader.CURVE);
        super.draw(g);
    }    

    @Override
    public void handleInput() {
        
    }
    
}
