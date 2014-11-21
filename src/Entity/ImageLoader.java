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
    public static Image BACKGROUND = Utils.loadJava2DImage("/res/background.jpg");
    public static Image TREVOR = Utils.loadJava2DImage("/res/trevor.jpg");
    public static Image WOODFLOOR1 = Utils.loadJava2DImage("/res/wood_floor_1.png");
    public static Image STONEFLOOR1 = Utils.loadJava2DImage("/res/stone_floor_1.png");
    public static Image GRASS1 = Utils.loadJava2DImage("/res/Grass1.png");
    public static Image WATER1 = Utils.loadJava2DImage("/res/Water1.png");

}
