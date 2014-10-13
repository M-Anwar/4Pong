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
import Engine.JavaSoundEffects;
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
    String force = "C:/Users/Jason Xu/Documents/GitHub/4Pong/src/res/forcebewith.wav";
    String blip = "C:/Users/Jason Xu/Documents/GitHub/4Pong/src/res/blip.wav";
    String feel = "C:/Users/Jason Xu/Documents/GitHub/4Pong/src/res/feelgood.wav";

    JavaSoundEffects effects;
    JavaBackgroundMusic music;
    
    public TestBench(GameStateManager gsm) {
        super(gsm);
        init();
        music = new JavaBackgroundMusic();
        effects = new JavaSoundEffects();
        
    }

    @Override
    public void init() {
        buttons.add(new GameButton("X", GamePanel.WIDTH - 100, 100));
        buttons.add(new GameButton("Backgroud Music", 100, 100));
        buttons.add(new GameButton("Sound Effects", 400, 100));
        buttons.get(0).addButtonListener(new ButtonListener(){//X button to go back to options

            @Override
            public void buttonClicked() {
                gsm.setState(GameStateManager.OPTION_STATE);
            }
        });
        buttons.get(1).addButtonListener(new ButtonListener(){//BackgroundMusic button

            @Override
            public void buttonClicked() {
               music.init();
                try {
                    music.load(force, "Background");
                    music.loop("Background");
                    music.play("Background");
                } catch (Exception ex) {
                    Logger.getLogger(TestBench.class.getName()).log(Level.SEVERE, null, ex);
                }
                
               
            }
        });
        
         buttons.get(2).addButtonListener(new ButtonListener(){ //For SoundEffect button

            @Override
            public void buttonClicked() {
                effects.init();
                try {
                    effects.load(blip, "Blip");
                    effects.play("Blip");
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
