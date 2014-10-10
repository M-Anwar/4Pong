/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Engine.Image;
import Engine.Utils;

/**
 *
 * @author muhammed.anwar
 */
public class ImageLoader {    
    public static Image LOGO = Utils.loadJava2DImage("/logo.png");
    public static Image EXPADDLE = Utils.loadJava2DImage("/res/paddleextension.png");
    public static Image SPEED = Utils.loadJava2DImage("/res/speed.png");
    public static Image SHIELD = Utils.loadJava2DImage("/res/shield.png");
    public static Image CURVE = Utils.loadJava2DImage("/res/curve.png");
}
