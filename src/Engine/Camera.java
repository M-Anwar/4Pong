/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

import Engine.Geometry.Rectangle;

/**
 *
 * @author muhammed.anwar
 */
public class Camera
{
    private Vector2D position;
    private Vector2D velocity;
    private float zoom;
    private Rectangle viewPort;
    
    private Vector2D finalPosition;
    
    public Camera(Vector2D pos, Rectangle viewPort)
    {
        this.position = pos;
        this.viewPort = viewPort;
        velocity = new Vector2D(0,0);
        finalPosition = new Vector2D(position);
    }
    public void update(float delta)
    {        
        this.position.thisAdd(velocity.scale(delta)); 
        
        if(velocity.length2()>9){            
            velocity.thisScale(0.5f);
            velocity.thisScale(delta,delta);       
            position.thisAdd(velocity);
        }else
        {
            position.x = finalPosition.x;
            position.y = finalPosition.y;
        }               
    }
    public void applyCamera(Graphics g){
        g.translate(position.x+viewPort.getWidth()/2,position.y+viewPort.getHeight()/2); 
    }
    public void unApplyCamera(Graphics g)
    {
        g.translate(-(position.x+viewPort.getWidth()/2),-(position.y+viewPort.getHeight()/2));
    }
    public void moveCamera(Vector2D offset)
    {
        position.thisAdd(offset);
    }
    public void moveTo(Vector2D pos)
    {
        finalPosition = new Vector2D(pos);
    }   
    public Vector2D getPosition(){return this.position;}
}
