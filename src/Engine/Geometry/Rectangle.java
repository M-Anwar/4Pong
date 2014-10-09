/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Geometry;

import Engine.Vector2D;
import java.util.HashMap;

/**
 * Represents an axis aligned bounding box. 
 * @author muhammed.anwar
 */
public class Rectangle 
{
    public static final String TOPLEFT = "TOPLEFT";
    public static final String TOPRIGHT = "TOPRIGHT";
    public static final String BOTTOMLEFT = "BOTTOMLEFT";
    public static final String BOTTOMRIGHT = "BOTTOMRIGHT";
    
    public float x;
    public float y; 
    public float width;
    public float height;
    
    public Rectangle(float x, float y, float width, float height)
    {
        this.x = x; 
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public Rectangle(Vector2D pos, float width, float height)
    {
        this.x = pos.x;
        this.y = pos.y;
        this.width = width;
        this.height = height;
    }
    public Rectangle(float xmin, float ymin, float xmax, float ymax, float d)
    {
        this.x = xmin;
        this.y = ymin;
        this.width = xmax - xmin;
        this.height = ymax - ymin;
    }
    
    public void setPosition(Vector2D v){
        this.x = v.x;
        this.y = v.y;
    }
    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }
    
    public HashMap<String, Vector2D> getCorners()
    {
        HashMap<String,Vector2D> corners = new HashMap<>();
        corners.put(TOPLEFT, new Vector2D(x,y));
        corners.put(TOPRIGHT, new Vector2D(x+width,y));
        corners.put(BOTTOMLEFT, new Vector2D(x,y+height));
        corners.put(BOTTOMRIGHT, new Vector2D(x+width,y+height));        
        return corners;      
    }
}
