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
 * The basic component for all GUI elements
 * @author muhammed.anwar
 */
public class Component 
{
    protected Vector2D velocity;
    protected Vector2D position;
    protected Vector2D finalPosition;
    
    protected float txtWidth;
    protected float txtHeight; 
    
    protected float width;
    protected float height;
    
    protected float border = 25;
    
    private String font = "Arial";
    private String text; 
    protected int fontSize = 30;
    private boolean isEnabled;  
    
    public Component(String text, float x, float y)
    {
        setText(text);
        this.finalPosition = new Vector2D(x,y);
        init();        
    }
    public Component(float x, float y)
    {
        setText(" ");
        this.finalPosition = new Vector2D(x,y);
        init();
    }
    private void init(){
        velocity = new Vector2D();
        if (finalPosition.x < GamePanel.WIDTH/2){
            position = new Vector2D(0,finalPosition.y);
        }
        else{
            position = new Vector2D(GamePanel.WIDTH,finalPosition.y);
        }        
    }
    public Component(String text, Vector2D v){ this(text,v.x,v.y);}
    public Component(Vector2D v){this(v.x,v.y);}
    
    public void setText(String text){
        this.text = text;
        this.txtWidth = StringBuilder.getWidth(text,font,fontSize);
        this.txtHeight = StringBuilder.getHeight(text, font, fontSize);
    }
    public String getText(){return text;}
    public void setFont(String font, int size){
        this.font = font; 
        this.fontSize = size;
        setText(this.getText());
        
    }
    public String getFont(){return this.font;}
    public boolean isEnabled(){return this.isEnabled;}
    
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
    }
    public void draw(Graphics g)
    {
        if (isEnabled)
            g.setColor(Color.WHITE.getRGB());   
        else
            g.setColor(Color.RED.getRGB());
        g.setFont(font, Graphics.BOLD, fontSize);
        g.drawString(text, position.x, position.y);
        g.drawRect(position.x-5, position.y-txtHeight-5, txtWidth+10, txtHeight+10);
        
    }
    public void moveTo(float x, float y)
    {
        this.finalPosition.x = x;
        this.finalPosition.y = y;
    }    
    
     public boolean isHovering(int x, int y) {       
        return x > this.position.x-border/2 && x < this.position.x-border/2+ width && 
                y > this.position.y+border/2-this.height  && y < this.position.y +border/2;        
    }
    public float getWidth(){return this.width;}
    public float getHeight(){return this.height;}
    
    
            
    
}
