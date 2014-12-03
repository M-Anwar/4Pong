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
import Entity.PaddleAI;
import Entity.PlayerInputGenerator;
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
    public ArrayList<Paddle> players;
    public ArrayList<Ball> ball;    
    
    public Paddle lastHit;
    public int p1score;
    public int p2score;
    public int p3score;
    public int p4score;
    
    private float rotation = 0;
    public SinglePlayerGame(GameStateManager gsm) {
        super(gsm);       
    }

    @Override
    public void init() 
    {      
        player = new Paddle(Paddle.PaddlePosition.BOTTOM, new PlayerInputGenerator());
        player2 = new Paddle(Paddle.PaddlePosition.RIGHT, new PaddleAI(this,1));
        player3 = new Paddle(Paddle.PaddlePosition.TOP, new PaddleAI(this,2));
        player4= new Paddle(Paddle.PaddlePosition.LEFT, new PaddleAI(this,3));
        players = new ArrayList<>();
        players.add(player);
        players.add(player2);
        players.add(player3);
        players.add(player4);        
        
        ball = new ArrayList<>();
        for(int i =0 ; i <1; i ++)
            ball.add(new Ball());
        for(Paddle p: players)p.update(0);
        
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
        for(Paddle p: players)p.update(delta);        
        for(Ball b: ball) {
            b.update(delta);
            if(b.hitWall==true){
                b.hitWall=false;                
                b.setPosition(new Vector2D(GamePanel.GAMEWIDTH/2, GamePanel.GAMEHEIGHT/2));
                b.setVelocity(new Vector2D((float)Math.random()*(25+15)+15,(float)Math.toRadians(Math.random()*360), 1));             
                if(lastHit == players.get(0) && ball.get(0).wallHit!=1)p1score++;
                if(lastHit == players.get(1) && ball.get(0).wallHit!=2)p2score++;
                if(lastHit == players.get(2) && ball.get(0).wallHit!=3)p3score++;
                if(lastHit == players.get(3) && ball.get(0).wallHit!=4)p4score++;
                lastHit =null;
            }
        }
       
      
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
        g.drawString("Player 1:"+p1score,GamePanel.GAMEWIDTH+40,90);
        g.setColor(Color.WHITE.getRGB());        
        g.drawString("CPU 1:"+p2score,GamePanel.GAMEWIDTH+40,110);
        g.drawString("CPU 2:"+p3score,GamePanel.GAMEWIDTH+40,130);
        g.drawString("CPU 3:"+p4score,GamePanel.GAMEWIDTH+40,150);
                         
        //Sets the game area
        g.drawRect(5, 5, GamePanel.GAMEWIDTH,GamePanel.GAMEHEIGHT);
        g.setClip(5, 5, GamePanel.GAMEWIDTH, GamePanel.GAMEHEIGHT);
        
        g.translate(5, 5);      
        //g.rotate(Math.toRadians(90), GamePanel.GAMEWIDTH/2+5, GamePanel.GAMEHEIGHT/2+5);
        for(Paddle p: players) p.draw(g);
        for(Ball b: ball) b.draw(g);
        g.setColor(Color.WHITE.getRGB());
        CollisionResult s;        
        for(Paddle p: players){
            for(Ball b: ball){
                if((s=p.getShape().collides(b.getShape()))!=null)
                {              
                    b.getPosition().thisAdd(s.mts);                                 
                    lastHit = p;                   
                    b.getVelocity().thisBounceNormal(s.normal);
                    b.getVelocity().thisAdd(p.getVelocity());
                    Vector2D norm = s.normal.getPerpendicular();
                    float rel1 = b.getVelocity().dot(norm);
                    float rel2 = p.getVelocity().dot(norm);
                    float dir = b.getVelocity().dot(p.getVelocity());
                    if(dir!=0)
                        dir = dir/Math.abs(dir);
                    float amount = rel1-rel2;
                    b.setAngularVelocity(Math.abs(amount)*dir*4);
                    
                }
            }
        }
        //g.rotate(Math.toRadians(-90), GamePanel.GAMEWIDTH/2+5, GamePanel.GAMEHEIGHT/2+5);
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
