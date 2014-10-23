/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author muhammed.anwar
 */
public class Keys
{
    public static final int NUM_KEYS = 11;
	
    public static boolean keyState[] = new boolean[NUM_KEYS];
    public static boolean prevKeyState[] = new boolean[NUM_KEYS];
    
    /**Listeners which might want asynchronous direct input */
    public static ArrayList<KeyListener> listeners = new ArrayList<>();
    
    //These ints should be used when doing synchronous polling of keys
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
            
    //These ints should only be used in listeners
    public static final int VK_ENTER = 10;
    public static final int VK_BACK_SPACE = 8;
    public static final int VK_LEFT = 37;
    public static final int VK_UP = 38;
    public static final int VK_RIGHT = 39;
    public static final int VK_DOWN = 40;
    public static final int VK_SHIFT = 16;
    
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
    public static void keyTyped(KeyEvent k){
        for(KeyListener listen : listeners){            
            listen.KeyTyped(k.getKeyCode(),k.getKeyChar());
        }
    }   
    
    /**
     * Check whether a key was typed (pressed and then released). 
     * @param i the key code for the test -> the list of available keys are defined
     * statically in Keys, those not suffixed by VK.
     * @return true or false for whether the given key is typed
     */
    public static boolean isPressed(int i) {
            return keyState[i] && !prevKeyState[i];
    }
    
    /**
     * Check whether a key is currently being held down
     * @param i the key code for the test -> the list of available keys are defined
     * statically in Keys, those not suffixed by VK.
     * @return true or false for whether the given key is pressed
     */
    public static boolean isDown(int i){
        return keyState[i];
    }
    
    //Adding and removing listeners - Important cause this holds a static list.
    
    /**Add a listener for asynchronous key event reporting.
     * @param k - the listener to report back to */    
    public static void addKeyListener(KeyListener k){listeners.add(k);}
    
    /**Remove a given listener from being informed of key typed events.
     * @param k - the listener to remove*/
    public static void removeKeyListener(KeyListener k){listeners.remove(k);}
}
