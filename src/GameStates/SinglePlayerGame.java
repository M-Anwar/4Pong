/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameStates;

import Engine.GameState;
import Engine.GameStateManager;
import Engine.Graphics;
import Engine.Java2DGraphics;
import Engine.Java2DImage;
import G4Pong.GamePanel;
import static G4Pong.GamePanel.HEIGHT;
import static G4Pong.GamePanel.WIDTH;
import GUI.ButtonListener;
import GUI.Component;
import GUI.GameButton;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author muhammed.anwar
 */
public class SinglePlayerGame extends GameState{

    Java2DImage i;
    Graphics g2;
    public SinglePlayerGame(GameStateManager gsm) {
        super(gsm);
        init();
    }

    @Override
    public void init() {
        i = new Java2DImage(new BufferedImage(HEIGHT, HEIGHT, BufferedImage.TYPE_INT_ARGB));
        g2 = new Java2DGraphics((Graphics2D)i.image.getGraphics());
        buttons = new ArrayList<GameButton>();
        buttons.add(new GameButton("X",GamePanel.WIDTH-40,40));
        buttons.get(0).addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
               gsm.setState(GameStateManager.INTRO_STATE);
            }                
        });
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        handleInput();
         
    }

    @Override
    public void draw(Graphics g) {             
        g2.drawString("Hello World", 10, 10);
        g.setFont("Arial", Graphics.BOLD, 40);
        g.setColor(Color.WHITE.getRGB());
        g.drawString("WELCOME TO SINGLE PLAYER PONG", 50, GamePanel.HEIGHT/2);
        g.drawImage(0, 0, i);
        super.draw(g);
    }    

    @Override
    public void handleInput() {
        
    }
    
}
