/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import Engine.GUI.TextBox;
import Engine.GameState;
import Engine.GameStateManager;
import Engine.Graphics;
import G4Pong.GamePanel;

/**
 *
 * @author Jason Xu
 */
public class MultiPlayerGame extends GameState{
    
    TextBox name;
    public MultiPlayerGame(GameStateManager gsm) {
        super(gsm);
        init();
    }
    
    @Override
    public void init(){
        name = new TextBox("", 300, 300);
        System.out.println("Here");
    }
    
    @Override
    public void update(float delta){
        super.update(delta);
        name.update(delta);
    }
    
    @Override
    public void draw(Graphics g){
        name.draw(g);
        super.draw(g);
    }
    
    @Override
    public void handleInput() {
        
    }
    
    
}
