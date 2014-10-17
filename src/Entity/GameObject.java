/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Engine.Vector2D;

/**
 * Base class for all other game objects
 * @author muhammed.anwar
 */
public class GameObject
{
    protected Vector2D position;
    protected Vector2D velocity;
    
    protected float width;
    protected float height;
    
    protected int color;
    protected int colorBorder;
    
    protected GameObject(){}
    
    public void setPosition(Vector2D pos) {
        this.position = pos;
    }

    public void setPosition(GameObject o) {
        this.position = o.getPosition();
    }

    public void setVelocity(Vector2D v) {
        this.velocity = v;
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Vector2D getPosition(){return position;}
    public Vector2D getVelocity(){return velocity;}    
    public float getWidth() { return width; }
    public float getHeight() { return height; }
}
