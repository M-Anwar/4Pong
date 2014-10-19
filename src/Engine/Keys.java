/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

import java.awt.event.KeyEvent;

/**
 *
 * @author muhammed.anwar
 */
public class Keys
{
    public static final int NUM_KEYS = 11;
	
    public static boolean keyState[] = new boolean[NUM_KEYS];
    public static boolean prevKeyState[] = new boolean[NUM_KEYS];

    public static int ENTER = 0;
    public static int ESCAPE = 1;
    public static int R = 2;
    public static int W = 3;
    public static int A = 4;
    public static int S = 5;
    public static int D = 6;
    public static int RIGHT = 7;
    public static int LEFT = 8;
    public static int UP = 9;
    public static int DOWN = 10;
            

    public static void keySet(int i, boolean b) {
            if(i == KeyEvent.VK_ENTER) keyState[ENTER] = b;
            if(i == KeyEvent.VK_ESCAPE) keyState[ESCAPE] = b;
            if(i == KeyEvent.VK_R) keyState[R] = b;
            if(i == KeyEvent.VK_W) keyState[W] = b;
            if(i == KeyEvent.VK_A) keyState[A] = b;
            if(i == KeyEvent.VK_S) keyState[S] = b;
            if(i == KeyEvent.VK_D) keyState[D] = b;
            if(i == KeyEvent.VK_RIGHT) keyState[RIGHT] = b;
            if(i == KeyEvent.VK_LEFT) keyState[LEFT] = b;
            if(i == KeyEvent.VK_UP) keyState[UP] = b;
            if(i == KeyEvent.VK_DOWN) keyState[DOWN] = b;
    }

    public static void update() {
            for(int i = 0; i < NUM_KEYS; i++) {
                    prevKeyState[i] = keyState[i];
            }
    }

    public static boolean isPressed(int i) {
            return keyState[i] && !prevKeyState[i];
    }
    public static boolean isDown(int i){
        return keyState[i];
    }
}
