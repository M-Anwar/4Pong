/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameStates;

import Engine.GameState;
import Engine.GameStateManager;
import Engine.Geometry.CollisionResult;
import Engine.Geometry.Rectangle;
import Engine.Graphics;
import Engine.Java2DGraphics;
import Engine.Java2DImage;
import Engine.Keys;
import Engine.Vector2D;
import Entity.ImageLoader;
import G4Pong.GamePanel;
import static G4Pong.GamePanel.HEIGHT;
import static G4Pong.GamePanel.WIDTH;
import Engine.GUI.ButtonListener;
import Engine.GUI.Component;
import Engine.GUI.GameButton;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author muhammed.anwar
 */
public class SinglePlayerGame extends GameState{    
   
    private GameButton btnExit;
    private ArrayList<Rectangle> rects;
    
    private float rotation = 0;
    public SinglePlayerGame(GameStateManager gsm) {
        super(gsm);
        init();
    }

    @Override
    public void init() 
    {      
        rects = new ArrayList<>();
        for(int i =0; i <10; i ++){
            rects.add(new Rectangle(GamePanel.GAMEWIDTH/2, GamePanel.GAMEHEIGHT/2-100+i*40 , 80,20));
        }       
        
        btnExit = new GameButton("X",GamePanel.WIDTH-40,40);
        addComponent(btnExit);
        btnExit.addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
               gsm.setState(GameStateManager.INTRO_STATE);
            }                
        });        
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
     
      
        CollisionResult s;
        for(int i =0; i <rects.size(); i++){
            Rectangle r1 = rects.get(i);
            for(int j = 0; j<rects.size(); j++){
                if(j!=i){
                    Rectangle r2 = rects.get(j);
                    if ((s=r1.collides(r2))!=null){                               
                       r2.setPosition(r2.getPosition().add(s.mts));
                    }                
                }
            }
        }
                
        Rectangle rect2= rects.get(0);
        rect2.setRotation((float)Math.toRadians(rotation));        
         g.rotate(rect2.getRotation(), rect2.getPosition().x, rect2.getPosition().y);
         g.drawRect(rect2.getPosition().x-rect2.getWidth()/2, rect2.getPosition().y-rect2.getHeight()/2,
                rect2.getWidth(), rect2.getHeight());
         g.rotate(-rect2.getRotation(), rect2.getPosition().x, rect2.getPosition().y);
        
         int i =0;
         for(Rectangle rect1: rects){
            if(i!=0){ 

            g.drawRect(rect1.getPosition().x-rect1.getWidth()/2, rect1.getPosition().y-rect1.getHeight()/2,
                   rect1.getWidth(), rect1.getHeight());
            }
            i++;
         }
        
        
        
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
        if (Keys.isDown(Keys.UP)) this.rotation++;
        if (Keys.isDown(Keys.DOWN)) this.rotation--;
        if (Keys.isDown(Keys.W)) this.rects.get(0).getPosition().thisAdd(0, -2);
        if (Keys.isDown(Keys.S)) this.rects.get(0).getPosition().thisAdd(0, 2);
        if (Keys.isDown(Keys.A)) this.rects.get(0).getPosition().thisAdd(-2, 0);
        if (Keys.isDown(Keys.D)) this.rects.get(0).getPosition().thisAdd(2, 0);
    }
    
}
