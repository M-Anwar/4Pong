/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import Engine.GameState;
import Engine.GameStateManager;
import Engine.Graphics;
import Engine.JavaBackgroundMusic;
import G4Pong.GamePanel;
import GUI.ButtonListener;
import GUI.GameButton;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason Xu
 */
public class TestBench extends GameState
{
    String pacmanMusic = "C:/Users/Jason Xu/Documents/GitHub/4Pong/src/res/forcebewith.wav";
    JavaBackgroundMusic music;
    public TestBench(GameStateManager gsm) {
        super(gsm);
        init();
        music = new JavaBackgroundMusic();
        
    }

    @Override
    public void init() {
        buttons.add(new GameButton("X", GamePanel.WIDTH - 100, 100));
        buttons.add(new GameButton("Backgroud Music", 100, 100));
        buttons.get(0).addButtonListener(new ButtonListener(){

            @Override
            public void buttonClicked() {
                gsm.setState(GameStateManager.OPTION_STATE);
            }
        });
        buttons.get(1).addButtonListener(new ButtonListener(){

            @Override
            public void buttonClicked() {
               music.init();
                try {
                    music.load(pacmanMusic, "Background");
                    music.play("Background");
                } catch (Exception ex) {
                    Logger.getLogger(TestBench.class.getName()).log(Level.SEVERE, null, ex);
                }
                
               
            }
        });
    }
    
    public void update(float delta)
    {
        super.update(delta);
    }
    public void draw(Graphics g){
        
        
        super.draw(g);
    }
    @Override
    public void handleInput() {
    }
    
}
