/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Geometry;

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
    public CollisionResult collides(Shape s)
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
        if(this.getType() == ShapeType.POLYGON && s.getType() == ShapeType.POLYGON)
        {
            //Apply SAT for convex polygons
            Vector2D [] axis1 = this.getNormals();
            Vector2D [] axis2 = s.getNormals();
            Vector2D [] vertex1 = this.getVertices();
            Vector2D [] vertex2 = s.getVertices();
            
            //Project onto First set of axes
            for (int i =0; i < axis1.length; i ++)
            {
                float [] maxMin1 = projectOntoAxis(vertex1,axis1[i]);
                System.out.println("Axis: " + axis1[i].toString());
                System.out.println(Arrays.toString(maxMin1));
                float [] maxMin2 = projectOntoAxis(vertex2,axis1[i]);
                if(projectionOverlap(maxMin1,maxMin2) < 0) return null;                
            }
            
            //Project onto second set of axes
            for (int i =0; i < axis2.length; i ++)
            {
                float [] maxMin1 = projectOntoAxis(vertex1,axis2[i]);
                float [] maxMin2 = projectOntoAxis(vertex2,axis2[i]);
                if(projectionOverlap(maxMin1,maxMin2) < 0) return null;
            }
            return new CollisionResult();
        }
        return null;
    }
    private float projectionOverlap(float[] minMax1, float [] minMax2)
    {
        //max1 > min2 
        if (minMax1[1] > minMax2[0]){
            return minMax1[1]-minMax2[0];
        }
        //max2 > min1
        else if(minMax2[1] > minMax1[0]){
            return minMax2[1]-minMax1[0];
        }
        
        return -1;
    }
    private float[] projectOntoAxis(Vector2D[] vertices, Vector2D axis)
    {
        //[0] -> min : [1] -> max
        float [] minMax = new float[2];
        minMax[0] = Float.MAX_VALUE;
        minMax[1] = Float.MIN_VALUE;
        for (int i =0; i <vertices.length; i ++)
        {
            float proj = vertices[i].dot(axis);
            if(proj < minMax[0]) minMax[0] = proj;
            else if (proj > minMax[1]) minMax[1] = proj;            
        }
        return minMax;
    }
    
    
    
}
