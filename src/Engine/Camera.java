/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

import Engine.Geometry.Rectangle;

/**
 * A basic camera which keeps track of world transformations required to 
 * simulate a 2D camera. The camera applies the stored transformations to 
 * a given graphics context, which supports translations and scaling.
 * 
 * The camera also handles transformations from screen space to world space. * 
 * @author muhammed.anwar
 */
public class Camera
{
    private Vector2D position;
    private Vector2D velocity;
    private Rectangle viewPort;    
    private Vector2D finalPosition;
    private float maxVel;
    private float zoom;    
    private float finalZoom;
    
    public Camera(Vector2D pos, Rectangle viewPort)
    {
        this.finalZoom = 1;
        this.zoom =1;//0.1f;
        this.position = pos;
        this.viewPort = viewPort;
        velocity = new Vector2D(0,0);
        finalPosition = new Vector2D(position);
        maxVel =100;
    }
    public void update(float delta)
    {        
        velocity.x = this.finalPosition.x - this.position.x;
        velocity.y = this.finalPosition.y - this.position.y;
        if(velocity.length()>maxVel){
            velocity = velocity.normalize().scale(maxVel);
        }
        
        if(finalZoom <=0.1) finalZoom = 0.1f;
        float zoomVel = (finalZoom - zoom)/5;
        if(Math.abs(zoomVel)>0.001){zoom += zoomVel*delta;}
        else {zoom = finalZoom;}
        
        if(velocity.length2()>4){            
            velocity.thisScale(delta,delta);       
            position.thisAdd(velocity);
        }else
        {
            position.x = finalPosition.x;
            position.y = finalPosition.y;
        }               
    }
    
    /**
     * Applies the camera transformations to the given graphics context
     * A call to applyCamera should be followed subsequently by a call to 
     * unApplyCamera. Furthermore the camera should be updated before applying
     * the camera either in an update loop or before drawing.
     * @param g a reference to the graphics context
     */
    public void applyCamera(Graphics g){
        g.translate(-position.x*zoom+viewPort.getWidth()/2,-position.y*zoom+viewPort.getHeight()/2); 
        g.scale(zoom, zoom);

    }
    /**
     * UnApplies the camera transformations from the given graphics context.
     * @param g a reference to the graphics context
     */
    public void unApplyCamera(Graphics g)
    {
        g.scale(1/zoom,1/zoom);
        g.translate(position.x*zoom-viewPort.getWidth()/2,position.y*zoom-viewPort.getHeight()/2); 
    }
    
    /**
     * Apply an offset to the current camera location
     * @param offset the vector to offset the camera position with
     */
    public void moveCamera(Vector2D offset)
    {
        finalPosition.thisAdd(offset);
    }
    /**
     * The world location to move the camera too. The camera will center the 
     * location in the given viewport.
     * @param pos the world location to move the camera to
     */
    public void moveTo(Vector2D pos)
    {
        finalPosition = new Vector2D(pos);
    }   
    /**
     * Sets the zoom level of the camera
     * @param finalZoomLevel the final zoom level to set
     */
    public void zoomTo(float finalZoomLevel){this.finalZoom = finalZoomLevel;}
    /**
     * Increment the zoom level by a specified about. Positive to zoom in, 
     * negative to zoom out.
     * @param zoomInc the amount to change the zoom level
     */
    public void zoomInc(float zoomInc) {this.finalZoom +=zoomInc;}
    
    
    /**
    /** Returns the position of the camera     
     * @return the position of the camera */
    public Vector2D getPosition(){return this.position;}
    /** Returns the present zoom level of the camera 
     * @return */
    public float getZoom(){return this.zoom;}
    /**
     * Directly set the position of the camera, avoiding any animation
     * @param pos 
     */
    public void setPosition(Vector2D pos){
        this.finalPosition.x = pos.x; this.finalPosition.y = pos.y;
        this.position.x =pos.x; this.position.y = pos.y;
    }
    /**
     * Directly set the zoom of the camera, avoiding any animation
     * @param zoomLevel the zoom level of the camera >0
     */
    public void setZoom(float zoomLevel){
        this.finalZoom = zoomLevel;
        this.zoom = zoomLevel;
    }
    /**
     * Takes a screen space coordinate and projects it to the world space
     * @param screen the coordinates in screen space 
     * @return the projected world coordinates.
     */
    public Vector2D projectPoint(Vector2D screen)
    {
        Vector2D i = screen.add(new Vector2D(-viewPort.getWidth()/2,-viewPort.getHeight()/2));
        i.thisAdd(new Vector2D(position.x*zoom, position.y*zoom));
        i.thisScale(1/zoom);
        return i;
    }
}
