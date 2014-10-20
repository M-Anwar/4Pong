/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Engine.Geometry.Rectangle;
import Engine.Graphics;
import Engine.Keys;
import Engine.Vector2D;
import G4Pong.GamePanel;
import java.awt.Color;

/**
 * A basic paddle that can be controlled by a player or an AI.
 * 
 * @author muhammed.anwar
 */
public class Paddle extends GameObject
{
    public float maxSpeed = 30;
    protected Rectangle limits;
    protected PaddlePosition pos;
    protected int col;
    public enum PaddlePosition{
        TOP,BOTTOM,LEFT,RIGHT
    }
    
    public Paddle(PaddlePosition p)
    {
        this.velocity = new Vector2D(0,0);      
        limits = getBounds(p);
        this.pos = p;
    }
    @Override
    public void update(float delta) {
        handleInput();
        Vector2D[] vertices = this.bounds.getVertices();
        Rectangle r = (Rectangle)this.bounds;
        if(vertices[0].x<limits.getPosition().x){
            this.setPosition(new Vector2D(limits.getPosition().x + r.getWidth()/2,this.getPosition().y)); 
            this.velocity.thisBounceNormal(new Vector2D(1,0));
            this.velocity.thisScale(0.1f);
        }
        if(vertices[1].x>limits.getPosition().x+limits.getWidth()){
            this.setPosition(new Vector2D(limits.getPosition().x +limits.getWidth()-r.getWidth()/2,this.getPosition().y));        
            this.velocity.thisBounceNormal(new Vector2D(-1,0));
            this.velocity.thisScale(0.1f);
        }
        if(vertices[0].y <limits.getPosition().y){
            this.setPosition(new Vector2D(this.getPosition().x,limits.getPosition().y + r.getHeight()/2)); 
            this.velocity.thisBounceNormal(new Vector2D(0,1));
            this.velocity.thisScale(0.1f);
        }
        if(vertices[2].y>limits.getPosition().y+limits.getHeight()){
            this.setPosition(new Vector2D(this.getPosition().x,limits.getPosition().y+limits.getHeight() - r.getHeight()/2));        
            this.velocity.thisBounceNormal(new Vector2D(0,-1));
            this.velocity.thisScale(0.1f);
        }
        
        this.setPosition(this.getPosition().add(velocity.scale(delta)));       
        this.velocity.thisScale((float)Math.pow(0.7f, delta));
        if(velocity.length()>maxSpeed){
            velocity = velocity.normalize().scale(maxSpeed);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE.getRGB());
        //g.drawRect(limits.getPosition().x, limits.getPosition().y, limits.getWidth(), limits.getHeight());
        Rectangle r = (Rectangle)this.bounds;
        g.setColor(col);                
        g.fillRect(this.getPosition().x-r.getWidth()/2, this.getPosition().y-r.getHeight()/2, r.getWidth(), r.getHeight());
//        g.setColor(Color.WHITE.getRGB());
//        Vector2D position = this.getPosition();
//        g.drawString("Velocity: " + velocity.length(),position.x, position.y+20);
    }
    
    public void handleInput()
    {
        
        if(Keys.isDown(Keys.W)){this.velocity.thisAdd(new Vector2D(0,-2));}
        if(Keys.isDown(Keys.S)){this.velocity.thisAdd(new Vector2D(0,2));}
        if(Keys.isDown(Keys.A)){this.velocity.thisAdd(new Vector2D(-5,0));}
        if(Keys.isDown(Keys.D)){this.velocity.thisAdd(new Vector2D(5,0));}
        
    }
    private Rectangle getBounds(PaddlePosition pos)
    {
        Rectangle r = null;
        if(pos == PaddlePosition.BOTTOM){
            this.bounds = new Rectangle(GamePanel.GAMEWIDTH/2, GamePanel.GAMEHEIGHT/2, 100,20);
            r = new Rectangle(0,GamePanel.GAMEHEIGHT-50,GamePanel.GAMEWIDTH,50);
            col = new Color(146,208,80).getRGB();
        }else if(pos == PaddlePosition.TOP){       
            this.bounds = new Rectangle(GamePanel.GAMEWIDTH/2, GamePanel.GAMEHEIGHT/2, 100,20);            
            r = new Rectangle(0,0,GamePanel.GAMEWIDTH,50);
            col = Color.RED.getRGB();
        }else if(pos == PaddlePosition.RIGHT){
            this.bounds = new Rectangle(GamePanel.GAMEWIDTH/2, GamePanel.GAMEHEIGHT/2, 20,100); 
            r = new Rectangle(GamePanel.GAMEWIDTH-50,0,50,GamePanel.GAMEHEIGHT);      
            col = Color.CYAN.getRGB();
        }else if(pos == PaddlePosition.LEFT){
            this.bounds = new Rectangle(GamePanel.GAMEWIDTH/2, GamePanel.GAMEHEIGHT/2, 20,100); 
            r = new Rectangle(0,0,50, GamePanel.GAMEHEIGHT);
            col = Color.ORANGE.getRGB();
        }
        return r;
    }
            
}
