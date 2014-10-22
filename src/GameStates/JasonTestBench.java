/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import Engine.GameState;
import Engine.GameStateManager;
import Engine.Graphics;
import G4Pong.GamePanel;
import Engine.GUI.ButtonListener;
import Engine.GUI.GameButton;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason Xu
 */
public class JasonTestBench extends GameState
{  
    private GameButton btnClose;
    private GameButton btnMusic;
    
    public JasonTestBench(GameStateManager gsm) {
        super(gsm);               
    }

    @Override
    public void init() {
        btnClose = new GameButton("X", GamePanel.WIDTH - 100, 100);
        btnMusic = new GameButton("Backgroud Music", 100, 100);
        
        addComponent(btnClose);
        addComponent(btnMusic);
       
        btnClose.addButtonListener(new ButtonListener(){//X button to go back to options
            @Override
            public void buttonClicked() {
                setState(GameStateManager.OPTION_STATE);
            }
        });
        btnMusic.addButtonListener(new ButtonListener(){//BackgroundMusic button
            @Override
            public void buttonClicked() {               
                try {                    
                   GamePanel.getAudio().play("FORCE");
                } catch (Exception ex) {
                    Logger.getLogger(JasonTestBench.class.getName()).log(Level.SEVERE, null, ex);
                }              
            }
        });        
         
    }
    
    public void update(float delta)
    {
    }
    public void draw(Graphics g){
        
        
    }
    @Override
    public void handleInput() {
    }
    
}
