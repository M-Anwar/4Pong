/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Geometry;

import Engine.Vector2D;

/**
 * A simple rectangle class with position, height and width. The position
 * is defined as the center of the rectangle.
 * @author muhammed.anwar
 */
public class Rectangle extends Shape
{
    private float width; 
    private float height;
    
    public Rectangle(float x, float y, float width, float height)
    {
        this(new Vector2D(x,y),width,height);
    }
    public Rectangle(Vector2D pos, float width, float height)
    {
        //pos.thisAdd(width/2, height/2);
        this.setPosition(pos);
        this.vertices = new Vector2D[4];
        vertices[0] = new Vector2D(-width/2,-height/2);
        vertices[1] = new Vector2D(width/2, -height/2);
        vertices[2] = new Vector2D(width/2, height/2);
        vertices[3] = new Vector2D(-width/2, height/2);
        this.setRotation(0);
        this.radius = vertices[0].length();   
        this.width = width;
        this.height = height;
        this.type = ShapeType.POLYGON;
    }
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
        Vector2D[] newVerts  = new Vector2D[vertices.length];
        for (int i =0; i <vertices.length; i++)
        {
            newVerts[i] = vertices[i].rotate(rotation);
            newVerts[i].thisAdd(this.getPosition());
        }      
        return newVerts;
    }
    public float getWidth(){return this.width;}
    public float getHeight(){return this.height;}
    
}
