/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Engine.Geometry.Shape;
<<<<<<< HEAD
import Engine.Graphics;
=======
>>>>>>> 54618b32292977d659f5f0f7895e4e022116112a
import Engine.Vector2D;

/**
 * Base class for all other game objects
 * The game object encapsulates entity information about any object that can 
 * be drawn on screen.  
 * @author muhammed.anwar
 */
<<<<<<< HEAD
public abstract class GameObject
{   
   protected Vector2D velocity;        
=======
public class GameObject
{   
    protected Vector2D velocity;        
>>>>>>> 54618b32292977d659f5f0f7895e4e022116112a
    protected Shape bounds;
    protected float angularVelocity;       
  
    public void setAngularVelocity(float ang){
        this.angularVelocity = ang;
    }
    public void setVelocity(Vector2D v) {
        this.velocity = v;
    }    
<<<<<<< HEAD
    public void setPosition(Vector2D v){
        this.bounds.setPosition(v);
    }
    public void setRotation(float angle){
        this.bounds.setRotation(angle);        
    }
    
    public abstract void update(float delta);
    public abstract void draw(Graphics g);
    
    
    public float getAngularVelocity(){return angularVelocity;}
    public Vector2D getVelocity(){return velocity;}    
    public Vector2D getPosition(){return this.bounds.getPosition();}
    public Shape getShape(){return this.bounds;}
    public float getRotation(){return this.bounds.getRotation();}   
=======
    
    public float getAngularVelocity(){return angularVelocity;}
    public Vector2D getVelocity(){return velocity;}    
>>>>>>> 54618b32292977d659f5f0f7895e4e022116112a
   
}
