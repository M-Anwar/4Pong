/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

import Engine.GUI.Component;
import java.util.ArrayList;

/**
 * Every unique game screen should extend this class. Game states should be 
 * switched using only the GameStateManager instance.
 * @author muhammed.anwar
 */
public abstract class GameState 
{
    private GameStateManager gsm;
    private ArrayList<Component> components;
    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
        components = new ArrayList<>();
        init();            
    }

    public abstract void init();
    public abstract void update(float delta);
    public abstract void draw(Graphics g);
    public abstract void handleInput();
    
    public void internalUpdate(float delta){
        update(delta);
        for(Component c: components)
            c.update(delta);
    }
    public void render(Graphics g){
        draw(g);
        for(Component c: components)
            c.draw(g);
    }
    
    /**
     * Switch the current gameState.
     * Will immediately switch the current game state to another one.
     * The parameter should be taken from the list of static values in 
     * GameStateManager.
     * @param state GameStateManager.INTRO etc, the int representing the state.
     */
    public void setState(int state)
    {
        for(Component c: components)
            c.dispose();
        this.gsm.setState(state);
    }   
    
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
