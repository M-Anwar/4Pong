/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Engine.Graphics;
import Engine.Mouse;
import Engine.Vector2D;
import G4Pong.GamePanel;
import java.awt.Color;
import java.util.Random;

/**
 * First test object EVER :D
 * @author muhammed.anwar
 */
public class MenuBall 
{   
    Vector2D position;
    Vector2D velocity;
    int radius = 50;
    public MenuBall(float x, float y)
    {
        Random r = new Random();
        this.position = new Vector2D(x,y);
        this.velocity = new Vector2D(r.nextFloat()*10+10,r.nextFloat()*10,1f); 
        
    }
     public MenuBall(float x, float y, float angle, float speed)
    {
        Random r = new Random();
        this.position = new Vector2D(x,y);
        this.velocity = new Vector2D(speed,angle,1f);   
    }
    public void update(float delta){        
               
        if((position.x -radius/2)<0){ position.x = radius/2; velocity.thisBounceNormal(new Vector2D(1,0));}
        if((position.x +radius/2)>GamePanel.WIDTH){ position.x = GamePanel.WIDTH-radius/2;velocity.thisBounceNormal(new Vector2D(-1,0));}
        if((position.y -radius/2)<0){position.y = radius/2;velocity.thisBounceNormal(new Vector2D(0,1));}
        if((position.y +radius/2)>GamePanel.HEIGHT){ position.y = GamePanel.HEIGHT-radius/2;velocity.thisBounceNormal(new Vector2D(0,-1));}
        Vector2D c = new Vector2D(position.x - Mouse.x, position.y -Mouse.y);
        if (c.length() < 100){
            c.normalize();
            c.thisScale(1);
            velocity.thisAdd(c);
        }
        
        if (velocity.length() > 20)
        {
            velocity.normalize();
            velocity.thisScale(10);
        }
        this.position.thisAdd(velocity.scale(delta)); 
    }
    private void move(float delta)
    {
        this.position.thisAdd(velocity.scale(delta));
    }
    public void draw(Graphics g)
    {   
        g.setColor(new Color(146,208,80).getRGB());
        g.fillOval(position.x-radius/2, position.y-radius/2,radius, radius);
        g.setColor(Color.WHITE.getRGB());         
    }
    
}
