/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

import GUI.Component;
import GUI.GameButton;
import java.util.ArrayList;

/**
 * Every unique game screen should extend this class. Game states should be 
 * switched using only the GameStateManager instance.
 * @author muhammed.anwar
 */
public abstract class GameState 
{
    protected GameStateManager gsm;
    protected ArrayList<GameButton> buttons;
    public GameState(GameStateManager gsm) {
            this.gsm = gsm;
            buttons = new ArrayList<GameButton>();
            
    }

    public abstract void init();
    public void update(float delta){
        for(Component c: buttons)
            c.update(delta);
    }
    public void draw(Graphics g){
        for(Component c: buttons)
            c.draw(g);
    }
    public abstract void handleInput();
}
