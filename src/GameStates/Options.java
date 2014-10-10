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
 * @author muhammed.anwar
 */
public class Options extends GameState{

    public Options(GameStateManager gsm) {
        super(gsm);
        init();
    }

    @Override
    public void init() 
    {
        buttons.add(new GameButton("Slow-Motion",50,100));
        buttons.add(new GameButton("Normal-Motion",60 + buttons.get(0).getWidth(), 100));
        buttons.add(new GameButton("Fast-Motion",70 + buttons.get(0).getWidth()+ buttons.get(1).getWidth(), 100));
        buttons.add(new GameButton("Return to Menu",50,GamePanel.HEIGHT-50));
       
        buttons.get(0).addButtonListener(new ButtonListener(){            
            public void buttonClicked() {
                GamePanel.setPlaySpeed(GamePanel.SLOMO);
            }
        
        });
        buttons.get(1).addButtonListener(new ButtonListener(){            
            public void buttonClicked() {
                GamePanel.setPlaySpeed(GamePanel.NORMAL);
            }
        
        });
        buttons.get(2).addButtonListener(new ButtonListener(){            
            public void buttonClicked() {
                GamePanel.setPlaySpeed(GamePanel.FAST);
            }
        
        });
        buttons.get(3).addButtonListener(new ButtonListener(){            
            public void buttonClicked() {
                gsm.setState(GameStateManager.INTRO_STATE);
            }
        
        });
        
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void draw(Graphics g) {        
        g.setColor(Color.WHITE.getRGB());
        g.setFont("Arial", Graphics.BOLD, 30);
        g.drawString("Play Speed Options:", 30, 50);       
        super.draw(g);
    }

    @Override
    public void handleInput() {
    }
    
}
