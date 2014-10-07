/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Engine.Graphics;
import Engine.Vector2D;
import G4Pong.GamePanel;
import java.awt.Color;
import java.util.Random;

/**
 *
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
        this.velocity = new Vector2D(r.nextFloat()*50+40,r.nextFloat()*50+40); 
        
    }
    public void update(float delta){        
        this.position.add(velocity.scale(delta));        
        if((position.x -radius/2)<0){ velocity.thisScale(-1,1);}
        if((position.x +radius/2)>GamePanel.WIDTH){ velocity.thisScale(-1,1);}
        if((position.y -radius/2)<0){ velocity.thisScale(1,-1);}
        if((position.y +radius/2)>GamePanel.HEIGHT){ velocity.thisScale(1,-1);}
        
    }
    public void draw(Graphics g)
    {   
        g.setColor(new Color(146,208,80).getRGB());
        g.fillOval(position.x-radius/2, position.y-radius/2,radius, radius);
    }
    
}
