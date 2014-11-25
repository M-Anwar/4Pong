/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Engine.Keys;
import Entity.Paddle.PaddlePosition;

/**
 *
 * @author muhammed.anwar
 */
public class ClientInputGenerator implements InputGenerator {

    private PaddlePosition pos;
    int input;
    public ClientInputGenerator(PaddlePosition p)
    {
        pos = p;
    }
    @Override
    public int getInput() {
        if(pos == PaddlePosition.BOTTOM){
            if(input == Keys.W)return Keys.W;
            if(input == Keys.S)return Keys.S;
            if(input == Keys.A)return Keys.A;            
            if(input == Keys.D)return Keys.D;
        }
        if(pos == PaddlePosition.RIGHT){
            if(input == Keys.W)return Keys.A;
            if(input == Keys.S)return Keys.D;
            if(input == Keys.A)return Keys.S;            
            if(input == Keys.D)return Keys.W;
        }
        if(pos == PaddlePosition.TOP){
            if(input == Keys.W)return Keys.S;
            if(input == Keys.S)return Keys.W;
            if(input == Keys.A)return Keys.D;            
            if(input == Keys.D)return Keys.A;            
        }if(pos == PaddlePosition.LEFT){
             if(input == Keys.W)return Keys.D;
            if(input == Keys.S)return Keys.A;
            if(input == Keys.A)return Keys.W;            
            if(input == Keys.D)return Keys.S;
        }        
        
        return -1;
    }
    public void setInput(int input)
    {
        this.input = input;
    }   
    
}
