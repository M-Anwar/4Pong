/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import Engine.GUI.ButtonListener;
import Engine.GUI.GameButton;
import Engine.GUI.TextBox;
import Engine.GameState;
import Engine.GameStateManager;
import Engine.Graphics;
import G4Pong.GamePanel;

/**
 *
 * @author Jason Xu
 */
public class MultiPlayerChat extends GameState{
    private TextBox chat;
    private GameButton btnExit;

    public MultiPlayerChat(GameStateManager gsm) {
        super(gsm);       
    }
    
    @Override
    public void init(){
        chat = new TextBox(280, 500);
        addComponent(chat);
        btnExit = new GameButton("X",GamePanel.WIDTH-60,10);
        addComponent(btnExit);
        btnExit.addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
               setState(GameStateManager.MULTI_PLAYER_STATE);
            }                
        });
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void draw(Graphics g) {
    }

    @Override
    public void handleInput() {
    
    }
}
