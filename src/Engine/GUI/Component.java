/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.GUI;

import Engine.Graphics;
import Engine.Keys;
import Engine.Mouse;
import Engine.StringBuilder;
import Engine.Vector2D;
import G4Pong.GamePanel;
import java.awt.Color;
/**
 * The basic component for all GUI elements.
 * Manages the basic animation for all the elements in terms of positioning.
 * Contains basic bounding box information, including AABB positioning, width and
 * height.
 * 
 * Position defines the top left corner of the component regardless of any border.
 * All extended class must abide by this, or else undefined positioning will occur.
 * 
 * @author muhammed.anwar
 */
public abstract class Component 
{
    protected Vector2D velocity;
    protected Vector2D position;
    protected Vector2D finalPosition;
       
    protected float width;
    protected float height;    
    
    private String font = "Arial";   
    protected int fontSize = 30;
    private boolean isEnabled;  
    private boolean isFocused;
    
    public Component(float x, float y, float width, float height)
    {        
        this.finalPosition = new Vector2D(x,y);
        init();        
    }
    public Component(Vector2D v, float width, float height){this(v.x,v.y, width, height);}
    private void init(){
        velocity = new Vector2D();
        if (finalPosition.x < GamePanel.WIDTH/2){
            position = new Vector2D(0,finalPosition.y);
        }
        else{
            position = new Vector2D(GamePanel.WIDTH,finalPosition.y);
        }        
    }   
            
    public abstract void dispose();        
    
    public void setFont(String font, int size){
        this.font = font; 
        this.fontSize = size;              
    }    
    public void setFocus(boolean foc){this.isFocused = foc;}
    
    public String getFont(){return this.font;}
    public float getFontSize(){return this.fontSize;}
    public float getWidth(){return this.width;}
    public float getHeight(){return this.height;}     
    public boolean isEnabled(){return this.isEnabled;}
    public boolean isFocused(){return this.isFocused;}
    public boolean isHovering(int x, int y) {       
        return x > this.position.x && x < this.position.x+ width && 
                y > this.position.y  && y < this.position.y + height;        
    }
    
    public void update(float delta)
    {
        velocity.x = this.finalPosition.x - this.position.x;
        velocity.y = this.finalPosition.y - this.position.y;
        
        if(velocity.length2()>9){            
            velocity.thisScale(0.5f);
            velocity.thisScale(delta,delta);       
            position.thisAdd(velocity);
            this.isEnabled = false;
        }else
        {
            position.x = finalPosition.x;
            position.y = finalPosition.y;
            this.isEnabled = true;
        }               
        if(isHovering(Mouse.x,Mouse.y)){
            if(Mouse.isPressed()){
               setFocus(true);
            }
        }else{
            if(Mouse.isPressed()){
               setFocus(false);
            }
        }
    }
    public void draw(Graphics g)
    {
        if (isEnabled)
            g.setColor(Color.WHITE.getRGB());   
        else
            g.setColor(Color.RED.getRGB());
        g.setFont(font, Graphics.BOLD, fontSize);       
        g.drawString("Component", position.x, position.y);
        g.drawRect(position.x, position.y, width, height);
        
    }
    public void moveTo(float x, float y)
    {
        this.finalPosition.x = x;
        this.finalPosition.y = y;
    }    
}
