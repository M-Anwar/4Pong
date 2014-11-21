/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Engine.Keys;
import Entity.Paddle.PaddlePosition;
import GameStates.SinglePlayerGame;

/**
 *
 * @author muhammed.anwar
 */
public class PaddleAI implements InputGenerator {

    SinglePlayerGame state;
    int pos;
    public PaddleAI(SinglePlayerGame state, int pos)
    {
        this.state = state;        
        this.pos = pos;
    }
    @Override
    public int getInput() {        
        Paddle player = state.players.get(pos);
        Ball ball = state.ball.get(0);
        
        if(player.pos == PaddlePosition.TOP)
        {
            if(ball.getPosition().subtract(player.getPosition()).x <10 &&
                   ball.getPosition().subtract(player.getPosition()).x >-10  ) {return -1;}
            else if(ball.getPosition().subtract(player.getPosition()).x >10){return Keys.D;}
            else {return Keys.A;}
        }
        else
        {
            if(ball.getPosition().subtract(player.getPosition()).y <10 &&
                   ball.getPosition().subtract(player.getPosition()).y >-10  ) {return -1;}
            else if(ball.getPosition().subtract(player.getPosition()).y >10){return Keys.S;}
            else {return Keys.W;}
//            if(player.getPosition().y > ball.getPosition().y) return Keys.W;
//            else return Keys.S;
        }
    }
    
}
