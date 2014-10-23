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
    private GameButton btnTTestBench;
    private GameButton btnMusicMute;
    
    public Options(GameStateManager gsm) {
        super(gsm);        
    }

    @Override
    public void init() 
    {
        String mute;
        if(GamePanel.getAudio().isMute()) mute ="Un-Mute Music";
        else mute = "Mute Music";
        btnSlow = new GameButton("Slow-Motion",50,60);
        btnNormal = new GameButton("Normal-Motion",60 + btnSlow.getWidth(), 60);
        btnFast = new GameButton("Fast-Motion",70 + btnSlow.getWidth()+ btnNormal.getWidth(), 60);
        btnReturn = new GameButton("Return to Menu",50,GamePanel.HEIGHT-80);
        btnTestBench = new GameButton("Jason's Test Bench",50,300);    
        btnMTestBench = new GameButton("Muhammed's Test Bench", 70+btnTestBench.getWidth(),300);
        btnTTestBench = new GameButton("TT Bench", 90+btnTestBench.getWidth()+btnMTestBench.getWidth(),300);
        btnMusicMute = new GameButton(mute,50,180);
        
        addComponent(btnSlow);
        addComponent(btnNormal);
        addComponent(btnFast);
        addComponent(btnReturn);
        addComponent(btnTestBench);
        addComponent(btnMTestBench);
        addComponent(btnTTestBench);
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
                setState(GameStateManager.INTRO_STATE);
            }
        
        });
        
        btnTestBench.addButtonListener(new ButtonListener(){            
            public void buttonClicked() {
                setState(GameStateManager.TEST_STATE);
            }
        
        });
        btnMTestBench.addButtonListener(new ButtonListener(){            
            public void buttonClicked() {
                setState(GameStateManager.MTEST_STATE);
            }
        
        });
        btnTTestBench.addButtonListener(new ButtonListener(){            
            public void buttonClicked() {
                setState(GameStateManager.TTEST_STATE);
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
    }

    @Override
    public void draw(Graphics g) {        
        g.setColor(Color.WHITE.getRGB());
        g.setFont("Arial", Graphics.BOLD, 30);
        g.drawString("Play Speed Options:", 30, 50);     
        g.drawString("Audio Options:",30,160);
        g.drawString("Test Benches: ",30,280);
    }

    @Override
    public void handleInput() {
    }
    
}
