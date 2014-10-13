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
    private ArrayList<Component> components;
    public GameState(GameStateManager gsm) {
            this.gsm = gsm;
            components = new ArrayList<>();
            
    }

    public abstract void init();
    public void update(float delta){
        for(Component c: components)
            c.update(delta);
    }
    public void draw(Graphics g){
        for(Component c: components)
            c.draw(g);
    }
    public abstract void handleInput();
    
    
    /**
     * Add a specific component to the game state
     * @param p the component to add
     */
    public void addComponent(Component p){components.add(p);}
    
    /**
     * Returns an arraylist of current components in the gameState
     * @return an ArrayList of components
     */
    public ArrayList<Component> getComponents(){return this.components;}
}
