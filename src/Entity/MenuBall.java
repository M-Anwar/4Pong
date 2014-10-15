/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Engine.Geometry.Circle;
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
    public Vector2D velocity;
    public Circle circleShape;
    public int color;
    
    int radius = 50;
    public MenuBall(float x, float y)
    {
        Random r = new Random();
        setColor(1);
        circleShape = new Circle(x,y,radius/2);        
        this.velocity = new Vector2D(r.nextFloat()*10+10,r.nextFloat()*10,1f); 
        
    }
     public MenuBall(float x, float y, float angle, float speed)
    {
        Random r = new Random();
        setColor(1);
        circleShape = new Circle(x,y,radius/2);   
        this.velocity = new Vector2D(speed,angle,1f);   
    }
    public void setColor (int i)
    {
        if (i ==1 )
        {
            color = new Color(146,208,80).getRGB();
        }
        else
        {
            color = Color.RED.getRGB();
        }
    }
    public void update(float delta){        
        Vector2D position = circleShape.getPosition();
        if((position.x -radius/2)<0){ circleShape.setXPosition(radius/2); velocity.thisBounceNormal(new Vector2D(1,0));}
        if((position.x +radius/2)>GamePanel.WIDTH){ circleShape.setXPosition(GamePanel.WIDTH-radius/2);velocity.thisBounceNormal(new Vector2D(-1,0));}
        if((position.y -radius/2)<0){circleShape.setYPosition(radius/2);velocity.thisBounceNormal(new Vector2D(0,1));}
        if((position.y +radius/2)>GamePanel.HEIGHT){ circleShape.setYPosition(GamePanel.HEIGHT-radius/2);velocity.thisBounceNormal(new Vector2D(0,-1));}
        Vector2D c = new Vector2D(position.x - Mouse.x, position.y -Mouse.y);
        if (c.length() < 100){
            c.thisNormalize();
            c.thisScale(1);
            velocity.thisAdd(c);
        }
        
        if (velocity.length() > 20)
        {
            velocity.thisNormalize();
            velocity.thisScale(10);
        }
        position.thisAdd(velocity.scale(delta)); 
    }
    
    public void draw(Graphics g)
    {   
        g.setColor(color);
        g.fillOval(circleShape.getPosition().x-radius/2, circleShape.getPosition().y-radius/2,radius, radius);
        g.setColor(Color.WHITE.getRGB());         
    }
    
}
