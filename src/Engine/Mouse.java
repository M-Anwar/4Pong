/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

/**
 *
 * @author muhammed.anwar
 */
public class Mouse 
{
    public static int x;
    public static int y;

    public static int action;
    public static int previousAction;

    public static final int RELEASED = 0;
    public static final int PRESSED = 1;

    public static void update() {
            previousAction = action;
    }

    public static void setPosition(int i1, int i2) {
            x = i1;
            y = i2;
    }

    public static void setAction(int i) {
            action = i;
    }

    public static boolean isPressed() {
            return action == PRESSED && previousAction == RELEASED;
    }

    public static boolean isDown() {
            return action == PRESSED;
    }
	
}
