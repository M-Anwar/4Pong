/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameStates;

import Engine.GameState;
import Engine.GameStateManager;
import Engine.Graphics;
import Entity.MenuBall;
import static G4Pong.GamePanel.HEIGHT;
import static G4Pong.GamePanel.WIDTH;
import GUI.ButtonListener;
import GUI.Component;
import GUI.GameButton;
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
    
    public IntroState(GameStateManager gsm) {
        super(gsm);
        init();        
    }

    @Override
    public void init() {       
        b = new ArrayList<MenuBall>();       
        
        int startY = 380;
        buttons.add(new GameButton("Play Game",90,startY));
        buttons.add(new GameButton("Multi-Player",90,startY+60));
        buttons.add(new GameButton("Options",90,startY+120));
        buttons.add(new GameButton("Exit",90,startY+175));
        for (int i =0; i <1; i ++){
            b.add(new MenuBall(40,40));       
        }
        
        buttons.get(3).addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
               System.exit(0);
            }                
        });
         buttons.get(2).addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
               gsm.setState(GameStateManager.OPTION_STATE);
            }                
        });
        buttons.get(1).addButtonListener(new ButtonListener(){          
            public void buttonClicked() {                
               Random r = new Random();
               buttons.get(1).moveTo(r.nextInt(WIDTH), r.nextInt(HEIGHT));
            }                
        });
        buttons.get(0).addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
               gsm.setState(GameStateManager.SINGLE_PLAYER_STATE);
            }                
        });
    }

    @Override
    public void update(float delta) {
       super.update(delta);
        handleInput();
        
        for(MenuBall ball: b)
            ball.update(delta);
        
    }

    @Override
    public void draw(Graphics g) {        
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
        super.draw(g);
    }

    @Override
    public void handleInput() {
        
    }
    
}
