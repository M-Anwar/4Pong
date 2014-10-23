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
import Engine.KeyListener;
import Engine.Keys;
import G4Pong.GamePanel;
import static G4Pong.GamePanel.WIDTH;
import java.awt.Color;

/**
 *
 * @author Jason Xu
 */
public class MultiPlayerGame extends GameState{
    
    private TextBox name;
    private GameButton btnExit;
    private GameButton btnConnect;
    private TextBox status;
    public MultiPlayerGame(GameStateManager gsm) {
        super(gsm);       
    }
    
    @Override
    public void init(){
        name = new TextBox("Enter Username", 300, 360);
        name.setFocus(true);
        name.addKeyListener(new KeyListener(){
            public void KeyTyped(int keyCode, char keyChar) {
                if(keyCode == Keys.VK_ENTER){
                    System.out.println(name.getText());
                }
            }        
        });
        addComponent(name);
        btnExit = new GameButton("X",GamePanel.WIDTH-60,10);
        addComponent(btnExit);
        btnExit.addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
               setState(GameStateManager.INTRO_STATE);
            }                
        });
        
        btnConnect = new GameButton("Connect",300,420);
        addComponent(btnConnect);
        btnConnect.addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
                removeComponent(btnConnect);   
                status = new TextBox("Connecting...",300,420);
                addComponent(status);
            }                
        }); 
    }
    
    @Override
    public void update(float delta){
       
    }
    
    @Override
    public void draw(Graphics g){
        g.setColor(Color.WHITE.getRGB());
         g.setFont("Arial", Graphics.BOLD, 180);       
        g.drawString("4 P O N G", WIDTH/2 - g.getFontDimension("4 P O N G")[0]/2, 250);
        g.setFont("Arial", Graphics.PLAIN, 50);
        g.drawString("MULTIPLAYER",WIDTH/2,300);
    }
    
    @Override
    public void handleInput() {
        
    }
    
    
}
