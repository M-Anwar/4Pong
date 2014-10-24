/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameStates;

import Engine.GUI.ButtonListener;
import Engine.GUI.GameButton;
import Engine.GameState;
import Engine.GameStateManager;
import Engine.Graphics;
import Entity.ImageLoader;
import G4Pong.GamePanel;

/**
 *
 * @author trevor.witjes
 */
public class TrevorTestBench extends GameState{

    private GameButton btnClose;
    
    public TrevorTestBench(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
         btnClose = new GameButton("X", GamePanel.WIDTH - 100, 100);
         addComponent(btnClose);
         
         btnClose.addButtonListener(new ButtonListener(){//X button to go back to options
            @Override
            public void buttonClicked() {
                setState(GameStateManager.OPTION_STATE);
            }
        });
    }

    @Override
    public void update(float delta) {
        
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(0, 0, ImageLoader.TREVOR, GamePanel.WIDTH, GamePanel.HEIGHT);
    }

    @Override
    public void handleInput() {
        
    }
    
}
