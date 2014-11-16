/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameStates;

import Engine.GUI.ButtonListener;
import Engine.GUI.Component;
import Engine.GUI.GameButton;
import Engine.GameState;
import Engine.GameStateManager;
import Engine.Geometry.CollisionResult;
import Engine.Geometry.Rectangle;
import Engine.Graphics;
import Engine.Java2DGraphics;
import Engine.Java2DImage;
import Engine.Keys;
import Engine.Vector2D;
import Entity.Ball;
import Entity.ImageLoader;
import Entity.Paddle;
import G4Pong.GamePanel;
import static G4Pong.GamePanel.HEIGHT;
import static G4Pong.GamePanel.WIDTH;
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
    private Paddle player;
    private Paddle player2;
    private Paddle player3;
    private Paddle player4;
    private ArrayList<Paddle> players;
    private ArrayList<Ball> ball;    
    
    private float rotation = 0;
    public SinglePlayerGame(GameStateManager gsm) {
        super(gsm);       
    }

    @Override
    public void init() 
    {      
        player = new Paddle(Paddle.PaddlePosition.BOTTOM);
        player2 = new Paddle(Paddle.PaddlePosition.RIGHT);
        player3 = new Paddle(Paddle.PaddlePosition.TOP);
        player4= new Paddle(Paddle.PaddlePosition.LEFT);
        players = new ArrayList<>();
        players.add(player);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        for(Paddle p: players)p.update(0);
        
        ball = new ArrayList<>();
        for(int i =0 ; i <1000; i ++)
            ball.add(new Ball());
        btnExit = new GameButton("X",GamePanel.WIDTH-60,20);
        addComponent(btnExit);
        btnExit.addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
               setState(GameStateManager.INTRO_STATE);
            }                
        });        
    }

    @Override
    public void update(float delta) {
        player.update(delta);
        player2.update(delta);
        player3.update(delta);
        player4.update(delta);
        for(Ball b: ball) b.update(delta);
        
      
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
     
        player.draw(g);
        player2.draw(g); 
        player3.draw(g);
        player4.draw(g);
        for(Ball b: ball) b.draw(g);
               
        CollisionResult s;
        
        for(Paddle p: players){
            for(Ball b: ball){
                if((s=p.getShape().collides(b.getShape()))!=null)
                {              
                    b.getPosition().thisAdd(s.mts);                    
                    float amount = b.getVelocity().dot(p.getVelocity()); //relative velocity
                    b.setAngularVelocity(amount/50);
                    b.getVelocity().thisBounceNormal(s.normal);
                    b.getVelocity().thisAdd(p.getVelocity());
                }
            }
        }
        
        g.translate(-5, -5);
        g.setClip(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);
        
       /* 
        float x = WIDTH-ImageLoader.LOGO.getWidth();
        g.drawImage(x, HEIGHT-ImageLoader.LOGO.getHeight(), ImageLoader.LOGO);
        x-=ImageLoader.EXPADDLE.getWidth();
        g.drawImage(x, HEIGHT-ImageLoader.EXPADDLE.getHeight(), ImageLoader.EXPADDLE);
         x-=ImageLoader.SPEED.getWidth();
        g.drawImage(x, HEIGHT-ImageLoader.SPEED.getHeight(), ImageLoader.SPEED);
         x-=ImageLoader.SHIELD.getWidth();
        g.drawImage(x, HEIGHT-ImageLoader.SHIELD.getHeight(), ImageLoader.SHIELD);
         x-=ImageLoader.CURVE.getWidth();
        g.drawImage(x, HEIGHT-ImageLoader.CURVE.getHeight(), ImageLoader.CURVE);*/
    }    

    @Override
    public void handleInput() {
        
    }
    
}
