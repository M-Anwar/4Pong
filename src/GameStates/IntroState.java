/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameStates;

import Engine.GameState;
import Engine.GameStateManager;
import Engine.Geometry.CollisionResult;
import Engine.Graphics;
import Engine.Mouse;
import Engine.Vector2D;
import Entity.ImageLoader;
import Entity.MenuBall;
import G4Pong.GamePanel;
import static G4Pong.GamePanel.HEIGHT;
import static G4Pong.GamePanel.WIDTH;
import Engine.GUI.ButtonListener;
import Engine.GUI.Component;
import Engine.GUI.GameButton;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author muhammed.anwar
 */
public class IntroState extends GameState
{    
    ArrayList<MenuBall> b;
    float angle;
    
    GameButton btnPlay;
    GameButton btnMulti;
    GameButton btnOptions;
    GameButton btnExit;
    
    public IntroState(GameStateManager gsm) {
        super(gsm);
        init();        
    }

    @Override
    public void init() {       
        b = new ArrayList<MenuBall>();    
        if(GamePanel.getAudio().isPlaying("MENU")==false){
            GamePanel.getAudio().loop("MENU");
            GamePanel.getAudio().play("MENU");
        }
        int startY = 380;
        
        btnPlay=new GameButton("Play Game",90,startY);
        btnMulti=new GameButton("Multi-Player",90,startY+60);
        btnOptions=new GameButton("Options",90,startY+120);
        btnExit=new GameButton("Exit",90,startY+175);
                
        addComponent(btnPlay);
        addComponent(btnMulti);
        addComponent(btnOptions);
        addComponent(btnExit);
        for (int i =0; i <10; i ++){
            b.add(new MenuBall(GamePanel.WIDTH/2+i,GamePanel.HEIGHT/2));       
        }
        
        btnExit.addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
               System.exit(0);
            }                
        });
         btnOptions.addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
               gsm.setState(GameStateManager.OPTION_STATE);
            }                
        });
        btnMulti.addButtonListener(new ButtonListener(){          
            public void buttonClicked() {                
               Random r = new Random();              
               btnMulti.moveTo(r.nextInt(WIDTH), r.nextInt(HEIGHT));
            }                
        });
        btnPlay.addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
                GamePanel.getAudio().stop("MENU");
               gsm.setState(GameStateManager.SINGLE_PLAYER_STATE);
            }                
        });
    }

    @Override
    public void update(float delta) {
       super.update(delta);
        handleInput();        
        boolean collides;

        for (int i =0; i <b.size(); i ++)
        {            
            collides = false;
            MenuBall ball = b.get(i);            
            
            for (int j = i+1; j< b.size(); j ++)
            {
                CollisionResult s = ball.circleShape.collides(b.get(j).circleShape);
                if (s!=null){                                       
                        ball.circleShape.setPosition(ball.circleShape.getPosition().add(s.mts));
                        Vector2D temp = new Vector2D(ball.velocity);
                        ball.velocity = b.get(j).velocity;
                        b.get(j).velocity= temp;
                }
                
            }           
            ball.update(delta);
        }
        
    }

    @Override
    public void draw(Graphics g) {   
        
        boolean collides = false;
         for(MenuBall ball: b)
            ball.draw(g);
        //Drawing code goes here
        g.setColor(Color.WHITE.getRGB());
        
        //Draw the Title and credits        
        g.setFont("Arial", Graphics.BOLD, 180);       
        g.drawString("4 P O N G", WIDTH/2 - g.getFontDimension("4 P O N G")[0]/2, 250);
        g.setFont("Arial", Graphics.PLAIN, 20);
        g.drawString("Muhammed A. , Jason X. , Trevor W.", WIDTH-g.getFontDimension("Muhammed A. , Jason X., Trevor W.")[0]-100, 300);
        
        //Draw Instructions and welcome details
        g.setFont("Arial", Graphics.BOLD, 45);
        g.drawString("Welcome",WIDTH/2-100,420);
        g.setFont("Arial",Graphics.PLAIN, 20);
        g.drawString("4 Player pong, pick up your paddle and have fun",WIDTH/2-100, 450);
        g.drawString("Arrow keys to move your paddle",WIDTH/2-100, 480);        
       
        //For giggles :D
//        for (int i =0; i <b.size(); i ++)
//        {             
//            MenuBall ball = b.get(i);            
//            for (int j = i+1; j< b.size(); j ++)
//            {
//                 g.drawLine(ball.circleShape.getPosition().x, ball.circleShape.getPosition().y,
//                        b.get(j).circleShape.getPosition().x, b.get(j).circleShape.getPosition().y);                        
//                }                      
//        }              
        
        
        Vector2D p1 = new Vector2D(Mouse.x, Mouse.y);
        Vector2D p2 = new Vector2D(GamePanel.WIDTH/2, GamePanel.HEIGHT/2);        
        p1.thisSubtract(p2);
        angle = p1.angle();    
        
        super.draw(g);
    }

    @Override
    public void handleInput() {
        if (Mouse.isPressed() ==true)
        {            
            b.add(new MenuBall(GamePanel.WIDTH/2,GamePanel.HEIGHT/2,angle,10));
        }
    }
    
}