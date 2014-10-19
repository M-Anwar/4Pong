/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

import G4Pong.GamePanel;
import GameStates.IntroState;
import GameStates.JasonTestBench;
import GameStates.MTestBench;
import GameStates.Options;
import GameStates.SinglePlayerGame;
import java.awt.Color;

/**
 *
 * @author muhammed.anwar
 */
public class GameStateManager 
{
    private GameState gameState;
    private int currentState;

    public static final int INTRO_STATE = -1;
    public static final int SINGLE_PLAYER_STATE = 0;
    public static final int OPTION_STATE = 1;
    public static final int TEST_STATE = 3;
    public static final int MTEST_STATE = 4;
    

    public GameStateManager() {
        currentState = INTRO_STATE;
        loadState(currentState);
    }

    private void loadState(int state) {
            if(state == INTRO_STATE)
                    gameState = new IntroState(this);   
            else if(state == SINGLE_PLAYER_STATE)
                    gameState = new SinglePlayerGame(this);
            else if(state == OPTION_STATE)
                    gameState = new Options(this);
            else if(state == TEST_STATE)
                    gameState =new JasonTestBench(this);
            else if(state == MTEST_STATE)
                    gameState =new MTestBench(this);
                    
    }

    public void setState(int state) {
            currentState = state;
            loadState(currentState);
    }

    public void update(float delta) {
            if(gameState != null) gameState.update(delta);
    }

    public void draw(Graphics g) {
            if(gameState != null) gameState.draw(g);
            else {
                    g.setColor(Color.BLACK.getRGB());
                    g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
            }
    }
}
