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
import Engine.GUI.ButtonListener;
import Engine.GUI.GameButton;
import java.awt.Color;

/**
 *
 * @author muhammed.anwar
 */
public class Options extends GameState{

    private GameButton btnSlow;
    private GameButton btnNormal;
    private GameButton btnFast;
    private GameButton btnReturn;
    private GameButton btnTestBench;
    private GameButton btnMTestBench;
    private GameButton btnMusicMute;
    
    public Options(GameStateManager gsm) {
        super(gsm);
        init();
    }

    @Override
    public void init() 
    {
        String mute;
        if(GamePanel.getAudio().isMute()) mute ="Un-Mute Music";
        else mute = "Mute Music";
        btnSlow = new GameButton("Slow-Motion",50,100);
        btnNormal = new GameButton("Normal-Motion",60 + btnSlow.getWidth(), 100);
        btnFast = new GameButton("Fast-Motion",70 + btnSlow.getWidth()+ btnNormal.getWidth(), 100);
        btnReturn = new GameButton("Return to Menu",50,GamePanel.HEIGHT-50);
        btnTestBench = new GameButton("Jason's Test Bench",50,340);    
        btnMTestBench = new GameButton("Muhammed's Test Bench", 70+btnTestBench.getWidth(),340);
        btnMusicMute = new GameButton(mute,50,220);
        
        addComponent(btnSlow);
        addComponent(btnNormal);
        addComponent(btnFast);
        addComponent(btnReturn);
        addComponent(btnTestBench);
        addComponent(btnMTestBench);
        addComponent(btnMusicMute);
        
        btnSlow.addButtonListener(new ButtonListener(){            
            public void buttonClicked() {
                GamePanel.setPlaySpeed(GamePanel.SLOMO);
            }
        
        });
        btnNormal.addButtonListener(new ButtonListener(){            
            public void buttonClicked() {
                GamePanel.setPlaySpeed(GamePanel.NORMAL);
            }
        
        });
        btnFast.addButtonListener(new ButtonListener(){            
            public void buttonClicked() {
                GamePanel.setPlaySpeed(GamePanel.FAST);
            }
        
        });
        btnReturn.addButtonListener(new ButtonListener(){            
            public void buttonClicked() {
                gsm.setState(GameStateManager.INTRO_STATE);
            }
        
        });
        
        btnTestBench.addButtonListener(new ButtonListener(){            
            public void buttonClicked() {
                gsm.setState(GameStateManager.TEST_STATE);
            }
        
        });
        btnMTestBench.addButtonListener(new ButtonListener(){            
            public void buttonClicked() {
                gsm.setState(GameStateManager.MTEST_STATE);
            }
        
        });
        btnMusicMute.addButtonListener(new ButtonListener(){           
            public void buttonClicked() {
               GamePanel.getAudio().setMute(!GamePanel.getAudio().isMute());
               if(GamePanel.getAudio().isMute())
                   btnMusicMute.setText("Un-Mute Music");
                else{
                   btnMusicMute.setText("Mute Music");
                   GamePanel.getAudio().loop("MENU");
                   GamePanel.getAudio().play("MENU");
               }
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
        g.drawString("Audio Options:",30,160);
        g.drawString("Test Benches: ",30,280);
        super.draw(g);
    }

    @Override
    public void handleInput() {
    }
    
}
