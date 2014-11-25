/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

import G4Pong.GamePanel;
import GameStates.IntroState;
import GameStates.MultiPlayerChat;
import GameStates.MultiPlayerGame;
import GameStates.MultiPlayerLogin;
import GameStates.Options;
import GameStates.SinglePlayerGame;
import GameStates.TestBench.JasonTestBench;
import GameStates.TestBench.MTestBench;
import GameStates.TestBench.TrevorTestBench;
import java.awt.Color;

/**
 * A manager class which delegates the act of drawing and updating the screen.
 * The manager controls the current state of the program and can switch between
 * different states.
 * 
 * TODO: Refactoring to allow users to register their own GameState classes
 * instead of having to change it manually in the actual Engine package.
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
    public static final int MULTI_PLAYER_STATE = 5;
    public static final int MULTIPLAYER_CHAT_STATE = 6;
    public static final int TTEST_STATE = 7;
    public static final int MULTI_PLAYER_GAME_STATE=8;
    

    public GameStateManager() {
        currentState = INTRO_STATE;//MULTI_PLAYER_GAME_STATE;//MULTI_PLAYER_STATE;//MTEST_STATE;
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
            else if(state == MULTI_PLAYER_STATE)
                    gameState = new MultiPlayerLogin(this);
            else if(state == MULTIPLAYER_CHAT_STATE)
                    gameState = new MultiPlayerChat(this);
            else if(state == TTEST_STATE)
                    gameState =new TrevorTestBench(this);
            else if(state == MULTI_PLAYER_GAME_STATE)
                    gameState = new MultiPlayerGame(this);

    }

    public void setState(int state) {
            currentState = state;
            loadState(currentState);
    }

    public void update(float delta) {
            if(gameState != null) gameState.internalUpdate(delta);
    }

    public void draw(Graphics g) {
            if(gameState != null) gameState.render(g);
            else {
                    g.setColor(Color.BLACK.getRGB());
                    g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
            }
    }
}
