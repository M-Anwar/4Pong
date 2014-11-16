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
    
    //A temp list to store the components that need to be removed/added in the next update
    //this measure prevents concurrent access issues when trying to remove an object
    //from a list while it is still being iterated over in the update/draw loop
    private ArrayList<Component> removeComponents; 
    private ArrayList<Component> addComponents; 

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
        components = new ArrayList<>();
        removeComponents = new ArrayList<>();
        addComponents = new ArrayList<>();

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
        for(Component c: removeComponents){
            components.remove(c);
        }
        for(Component c: addComponents){
            components.add(c);
        }
        addComponents.clear();
        removeComponents.clear();
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
     * GameStateManager. (POSSIBLE CHANGE IN FUTURE)
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
    public void addComponent(Component p){
        //Add the component to the add list, to be added at the next update.
        addComponents.add(p);}
    
    /**
     * Remove a specific component from the game state
     * @param p the component to remove
     */
    public void removeComponent(Component p){
        //Add the component to the remove list, to be removed at the next update.
        removeComponents.add(p);}
    /**
     * Returns an arraylist of current components in the gameState
     * @return an ArrayList of components
     */
    public ArrayList<Component> getComponents(){return this.components;}
}
