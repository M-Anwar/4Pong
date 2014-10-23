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
public class MultiPlayerGame extends GameState{
    
    private TextBox name;
    private GameButton btnExit;
    private GameButton btnConnect;
    public MultiPlayerGame(GameStateManager gsm) {
        super(gsm);       
    }
    
    @Override
    public void init(){
        name = new TextBox("Enter Username", 300, 300);
        addComponent(name);
        btnExit = new GameButton("X",GamePanel.WIDTH-60,10);
        addComponent(btnExit);
        btnExit.addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
               setState(GameStateManager.INTRO_STATE);
            }                
        });
        
        btnConnect = new GameButton("Connect",300,360);
        addComponent(btnConnect);
        btnConnect.addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
                System.out.println(name.getText());
            }                
        }); 
    }
    
    @Override
    public void update(float delta){
       
    }
    
    @Override
    public void draw(Graphics g){
        
    }
    
    @Override
    public void handleInput() {
        
    }
    
    
}
