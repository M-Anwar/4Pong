/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Geometry;

import Engine.Graphics;
import Engine.Vector2D;

/**
 * A simple circle class with position and radius. The position defined the
 * center of the circle and the radius is the half-width of the circle.
 * @author muhammed.anwar
 */
public class Circle extends Shape {

    public Circle(Vector2D pos, float radius)
    {
        this.setPosition(pos);
        this.radius = radius;
        this.type = ShapeType.CIRCLE;
    }
    public Circle(float x, float y, float radius)
    {
        this(new Vector2D(x,y),radius);
    }
            
    public void setRadius(float rad){ this.radius = rad; }
    
    @Override
    public void setRotation(float rot) { 
        this.rotation = rot;
    }

    @Override
    public float getRadius() {
        return this.radius;
    }

    @Override
    public Vector2D[] getVertices() {
        return null;
    }

    @Override
    public void debugDraw(Graphics g) {
        g.drawOval(this.getPosition().x-radius, this.getPosition().y-radius, radius*2, radius*2);
    }

   
    
}
