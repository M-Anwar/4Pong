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
    public enum PaddlePosition{
        
    }
    
    public Paddle()
    {
        this.bounds = new Rectangle(GamePanel.GAMEWIDTH/2, GamePanel.GAMEHEIGHT/2, 100,20);
        this.velocity = new Vector2D(0,0);      
    }
    @Override
    public void update(float delta) {
        handleInput();
        this.setPosition(this.getPosition().add(velocity.scale(delta)));       
        this.velocity.thisScale((float)Math.pow(0.7f, delta));
        if(velocity.length()>maxSpeed){
            velocity = velocity.normalize().scale(maxSpeed);
        }
    }

    @Override
    public void draw(Graphics g) {
        Rectangle r = (Rectangle)this.bounds;
        g.setColor(Color.GREEN.getRGB());                
        g.fillRect(this.getPosition().x-r.getWidth()/2, this.getPosition().y-r.getHeight()/2, r.getWidth(), r.getHeight());
        g.setColor(Color.WHITE.getRGB());
        Vector2D position = this.getPosition();
        g.drawString("Velocity: " + velocity.length(),position.x, position.y+20);
    }
    
    public void handleInput()
    {
        
        if(Keys.isDown(Keys.W)){this.velocity.thisAdd(new Vector2D(0,-5));}
        if(Keys.isDown(Keys.S)){this.velocity.thisAdd(new Vector2D(0,5));}
        if(Keys.isDown(Keys.A)){this.velocity.thisAdd(new Vector2D(-5,0));}
        if(Keys.isDown(Keys.D)){this.velocity.thisAdd(new Vector2D(5,0));}
        
    }
            
}
