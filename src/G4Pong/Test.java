/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package G4Pong;

import Engine.Geometry.Circle;
import Engine.Geometry.Rectangle;

/**
 *
 * @author muhammed.anwar
 */
public class Test {
    public static void main(String [] args)
    {
        Rectangle r1 = new Rectangle(20,20,20,20);
        Rectangle r2 = new Rectangle(40,20,10,20);
        
        r1.collides(r2);
    }
}
