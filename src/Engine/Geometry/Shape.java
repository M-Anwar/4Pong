/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Geometry;

import Engine.Graphics;
import Engine.Vector2D;
import java.util.Arrays;

/**
 * A Generic shape definition used for collision detection. 
 * When defining a polygon a local coordinate system is used. Refer to the 
 * geometry documentation for more details on how to define a general polygon.
 * The vertices should be defined when the polygon has 0 rotation. 
 * 
 * All shapes should be assigned a radius, this can be assigned at construction
 * or it can be calculated using a getter method. The radius of the object 
 * should encapsulate the object fully, as this information can be used when
 * doing a particular broad-phase collision check. 
 * 
 * The rotation of the shape is applied on all calculated values (i.e when
 * retrieving vertex information or normal information). The rotation can be
 * applied immediately to the local variables or indirectly through getter methods.
 * @author muhammed.anwar
 */
public abstract class Shape {
    
    public enum ShapeType{
        CIRCLE,POLYGON
    }
    protected Vector2D[] vertices;
    protected Vector2D position;
    protected ShapeType type;
    protected float rotation;
    protected float radius;
    
    //Setter methods
    public abstract void setRotation(float rot);
    public void setPosition(Vector2D pos){this.position = pos;}            
    public void setPosition(float x, float y){ this.position.x = x;this.position.y = y;}
    public void setXPosition(float x){this.position.x = x;}
    public void setYPosition(float y){this.position.y = y;}
    
    //Getter methods
    public ShapeType getType(){return this.type;}
    public Vector2D getPosition(){return this.position;}
    public float getRotation(){return this.rotation;}
    public abstract float getRadius();
    public abstract Vector2D[] getVertices();
    
    
    public Vector2D[] getNormals(){
        Vector2D[] vertices = getVertices();
        if (vertices == null){return null;}
        Vector2D[] norms = new Vector2D[vertices.length];
        for (int i =0; i <vertices.length; i ++)
        {
            Vector2D edge = vertices[(i+1)%vertices.length].subtract(vertices[i]);
            norms[i] = edge.getPerpendicular().normalize();
        }
        
        return norms;
    }
    /**
     * Returns the collision result of two shapes. The collision algorithm uses
     * the SAT (Separating Axis Theorem) to calculate the collision result
     * @param s the other shape to detect collision with
     * @return the collision result of the check
     */
    public CollisionResult collides(Shape s, Graphics g)
    {
        //Circle on Circle Collision
        if (this.getType() == ShapeType.CIRCLE && s.getType() == ShapeType.CIRCLE)
        {
            float totalRadius = this.getRadius() + s.getRadius();
            Vector2D v = this.getPosition().subtract(s.getPosition());
            float distance2 = v.length2();
            if(distance2 < (totalRadius*totalRadius)){
                CollisionResult result = new CollisionResult();
                float difference = totalRadius - (float)Math.sqrt(distance2);                
                result.mts = v.normalize().scale(difference);
                result.normal = result.mts.normalize();
                return result;
            }   
            else{
                return null;
            }                
        }
        //Polygon on Polygon Collision
        if(this.getType() == ShapeType.POLYGON && s.getType() == ShapeType.POLYGON)
        {
            //Apply SAT for convex polygons
            Vector2D [] axis1 = this.getNormals();
            Vector2D [] axis2 = s.getNormals();
            Vector2D [] vertex1 = this.getVertices();
            Vector2D [] vertex2 = s.getVertices();
            float minOverlap = Float.MAX_VALUE;
            Vector2D smallest =null;
            
            //Project onto First set of axes
            for (int i =0; i < axis1.length; i ++)
            {
                Projection proj1 = projectOntoAxis(vertex1,axis1[i]);                
                Projection proj2 = projectOntoAxis(vertex2,axis1[i]);     
                float overlap = proj1.getOverlap(proj2);
                g.drawString(proj1.toString() + " --- "+ proj2.toString() + " :        " +overlap +" :      " +axis1[i], 10,100+20*i);
                if(overlap>=0){
                    if(overlap < minOverlap){
                        minOverlap = overlap;
                        smallest = axis1[i];
                    }
                }
                else return null;   
            }
            
            //Project onto second set of axes
            for (int i =0; i < axis2.length; i ++)
            {
                Projection proj1 = projectOntoAxis(vertex1,axis2[i]);
                Projection proj2 = projectOntoAxis(vertex2,axis2[i]);                
                float overlap = proj1.getOverlap(proj2);
                g.drawString(proj1.toString() + " --- "+ proj2.toString() + " :        " +overlap+" :      " +axis1[i], 10,200+20*i);
                if(overlap>=0){
                    if(overlap <minOverlap){
                        minOverlap = overlap;
                        smallest = axis2[i];
                    }
                }
                else return null;               
            }
            g.drawString(smallest.toString(),10, 300);
            CollisionResult result = new CollisionResult();
            //Make sure the MTS is pointing the right way 
            if (s.getPosition().subtract(this.getPosition()).dot(smallest)<0)
                minOverlap *=-1;
            result.mts = new Vector2D(smallest).scale(minOverlap);
            result.normal = new Vector2D(smallest);
            return result;
        }
        return null;
    }    
    private Projection projectOntoAxis(Vector2D[] vertices, Vector2D axis)
    {       
        Projection proj = new Projection();
        proj.min = Float.MAX_VALUE;
        proj.max = -Float.MAX_VALUE;
        for (int i =0; i <vertices.length; i ++)
        {
            float projec = vertices[i].dot(axis);
            if(projec < proj.min) proj.min = projec;
            else if (projec > proj.max) proj.max = projec;            
        }
        return proj;
    }   
    
}
class Projection
{
    public float min;
    public float max;
    
    public float getOverlap(Projection p2){
        if(p2.min >= this.min && p2.min <=this.max){            
            return this.max - p2.min;
        }else if(this.min >= p2.min && this.min <= p2.max){            
           return p2.max - this.min;
        }
        return -1;
    }
    public String toString(){
        return "min: " + min + " max: " +max;
    }
    
}
