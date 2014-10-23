/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Engine.Geometry.Circle;
import Engine.Graphics;
import Engine.Vector2D;
import G4Pong.GamePanel;
import java.awt.Color;

/**
 *
 * @author muhammed.anwar
 */
public class Ball extends GameObject
{
    private float maxSpeed = 50;
    public Ball()
    {
        this.bounds = new Circle(GamePanel.GAMEWIDTH/2, GamePanel.GAMEHEIGHT/2,10);
        this.setPosition(new Vector2D(GamePanel.GAMEWIDTH/2,GamePanel.GAMEHEIGHT/2));
        this.setVelocity(new Vector2D(20,20));        
        this.setAngularVelocity(5);
    }
    @Override
    public void update(float delta) {
        
        //this.getVelocity().thisAdd(new Vector2D(0,9.8f*delta));
        this.setRotation(this.angularVelocity*delta+this.getRotation());
        this.angularVelocity*=Math.pow(0.95f, delta);
        
        this.setPosition(this.getPosition().add(velocity.scale(delta)));   
        Vector2D position = bounds.getPosition();
        float radius = bounds.getRadius();
        if((position.x -radius/2)<0){ bounds.setXPosition(radius/2); velocity.thisBounceNormal(new Vector2D(1,0));}
        if((position.x +radius/2)>GamePanel.GAMEWIDTH){ bounds.setXPosition(GamePanel.GAMEWIDTH-radius/2);velocity.thisBounceNormal(new Vector2D(-1,0));}
        if((position.y -radius/2)<0){bounds.setYPosition(radius/2);velocity.thisBounceNormal(new Vector2D(0,1));}
        if((position.y +radius/2)>GamePanel.GAMEHEIGHT){ bounds.setYPosition(GamePanel.GAMEHEIGHT-radius/2);velocity.thisBounceNormal(new Vector2D(0,-1));}
         if(velocity.length()>maxSpeed){
            velocity = velocity.normalize().scale(maxSpeed);
        }
         velocity.thisRotate(this.angularVelocity*delta/100);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE.getRGB());
        g.fillOval(bounds.getPosition().x-bounds.getRadius(), bounds.getPosition().y-bounds.getRadius(), bounds.getRadius()*2, bounds.getRadius()*2);
        g.setColor(Color.BLUE.getRGB());
        g.drawLine(bounds.getPosition().x, bounds.getPosition().y,bounds.getPosition().x+(float)Math.cos(this.getRotation())*bounds.getRadius(),bounds.getPosition().y+(float)Math.sin(this.getRotation())*bounds.getRadius());
        
    }
    
}
