/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Engine.Keys;
import Engine.Vector2D;

/**
 *
 * @author muhammed.anwar
 */
public class PlayerInputGenerator implements InputGenerator {

    @Override
    public int getInput() {
        if(Keys.isDown(Keys.W)){return Keys.W;}
        if(Keys.isDown(Keys.S)){return Keys.S;}
        if(Keys.isDown(Keys.A)){return Keys.A;}
        if(Keys.isDown(Keys.D)){return Keys.D;}
        return -1;
    }
    
}
