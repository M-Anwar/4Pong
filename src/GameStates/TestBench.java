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
import GUI.ButtonListener;
import GUI.GameButton;
import java.awt.Color;

/**
 *
 * @author Jason Xu
 */
public class TestBench extends GameState
{

    public TestBench(GameStateManager gsm) {
        super(gsm);
        init();
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
