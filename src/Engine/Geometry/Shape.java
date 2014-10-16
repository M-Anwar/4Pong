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
    
    public abstract void debugDraw(Graphics g);
    
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
    
    /**
     * Return the outward facing normals of the shape
     * @return an array of NORMALIZED normals.
     */
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
     * @return the collision result of the check. The MTS denotes the minimum
     * translation vector required to move the COLLIDING SHAPE (parameter s)
     * out of the current shape.
     */
    public CollisionResult collides(Shape s)
    {
        //Circle on Circle Collision
        /*
            Algorithm: If the distance between the centers of the circles are
                less then the sum of their radii, there is a collision. To 
                seperate them, simply calculate the overlap (radii - distance)
                and move them away in the direction of their centers;        
        */
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
        /*
            Algorithm: Modified Separating Axis Theorem (look at documentation)
        */
        if(this.getType() == ShapeType.POLYGON && s.getType() == ShapeType.POLYGON)
        {
            //Apply SAT for convex polygons
            
            //Normals and Vertices of polygons
            Vector2D [] axis1 = this.getNormals();
            Vector2D [] axis2 = s.getNormals();
            Vector2D [] vertex1 = this.getVertices();
            Vector2D [] vertex2 = s.getVertices();
            if (axis1 == null  || axis2 == null) return null;             
                
            //Keep track of minimum overlap value and axis
            float minOverlap = Float.MAX_VALUE;
            Vector2D smallest =null;
            
            //Project onto First set of axes
            for (int i =0; i < axis1.length; i ++)
            {
                Projection proj1 = projectOntoAxis(vertex1,axis1[i]);                
                Projection proj2 = projectOntoAxis(vertex2,axis1[i]);     
                float overlap = proj1.getOverlap(proj2);
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
                if(overlap>=0){
                    if(overlap <minOverlap){
                        minOverlap = overlap;
                        smallest = axis2[i];
                    }
                }
                else return null;               
            }
            CollisionResult result = new CollisionResult();
            //Make sure the MTS is pointing the right way by checking which 
            //direction the centers of the polygons are. If the projection onto
            //the MTS is positive, we have the correct direction, else flip it.
            if (s.getPosition().subtract(this.getPosition()).dot(smallest)<0)
                minOverlap *=-1;
            
            result.mts = new Vector2D(smallest).scale(minOverlap);
            result.normal = new Vector2D(smallest);
            return result;
        }
        //Polygon and Circle Collision
        /*
            Algoritm: Modified Separating Axis Theorem. The axis to check is now
                the axis formed by the vector from the center of the circle
                to the nearest vertex of the polygon (Also need to project on the
                normals of the polygon).
        */
        if (this.getType() ==ShapeType.POLYGON && s.getType() == ShapeType.CIRCLE ||
                this.getType() == ShapeType.CIRCLE && s.getType() == ShapeType.POLYGON)
        {            
            
            Shape polygon= null;
            Shape circle= null;
            
            if(this.getType()==ShapeType.POLYGON){
                polygon = this;               
            }else if(s.getType() == ShapeType.POLYGON){
                polygon = s;
            }            
            if(this.getType() == ShapeType.CIRCLE){
                circle = this;
            }else if(s.getType() == ShapeType.CIRCLE){
                circle = s;               
            }
            
            Vector2D [] axis1 = polygon.getNormals();           
            Vector2D [] vertex1 = polygon.getVertices();                       
            Vector2D axis2 = null;
            
            //Keep track of minimum overlap value and axis
            float minOverlap = Float.MAX_VALUE;
            Vector2D smallest =null;
            
            //Get the axis and the distance to the closest vertex on the polygon  
            Vector2D vec = vertex1[0].subtract(circle.getPosition());
            float dist = vec.length2();
            float minDist = dist;           
            axis2 = vec.normalize();
            for(int i =1; i  <vertex1.length; i++){
                vec = vertex1[i].subtract(circle.getPosition());                
                dist = vec.length2();
                if(dist< minDist){
                    minDist = dist;
                    axis2 = vec.normalize();                        
                }
            }          
            
            for(int i =0; i <axis1.length; i ++)
            {
                Projection proj1 = projectOntoAxis(vertex1,axis1[i]);
                Projection proj2 = projectCircleOntoAxis(circle,axis1[i]);
                float overlap = proj1.getOverlap(proj2);                
                if(overlap>=0){
                    if(overlap <minOverlap){
                        minOverlap = overlap;
                        smallest = axis1[i];
                    }
                }
                else return null; 
            }
            Projection proj1 = projectOntoAxis(vertex1,axis2);
            Projection proj2 = projectCircleOntoAxis(circle,axis2);
            float overlap = proj1.getOverlap(proj2);
            if(overlap>=0){
                if(overlap <minOverlap){
                    minOverlap = overlap;
                    smallest = axis2;
                }
            }
            else return null; 
            
            CollisionResult result = new CollisionResult();
            //Make sure the MTS is pointing the right way by checking which 
            //direction the centers of the shapes are. If the projection onto
            //the MTS is positive, we have the correct direction, else flip it.
            if (s.getPosition().subtract(this.getPosition()).dot(smallest)<0)
                minOverlap *=-1;
            
            result.mts = new Vector2D(smallest).scale(minOverlap);
            result.normal = new Vector2D(smallest);
            return result;
        }
        return null;
    }    
    
    /**
     * Project the given vertices onto the given axis and return the max-min
     * projections in a Projection data structure. 
     * @param vertices and array of vertices to project
     * @param axis the axis unto which the projection takes place (normalized)
     * @return a Projection data structure with the min-max projections.
     */
    private Projection projectOntoAxis(Vector2D[] vertices, Vector2D axis)
    {       
        Projection proj = new Projection();
        proj.min = proj.max = vertices[0].dot(axis);
        for (int i =1; i <vertices.length; i ++)
        {
            float projec = vertices[i].dot(axis);
            if(projec < proj.min) proj.min = projec;
            else if (projec > proj.max) proj.max = projec;            
        }
        return proj;
    }   
    
    /**
     * Projects a circle onto a given axis. 
     * @param s - the circle type shape to project
     * @param axis - the normalized vector onto which to project
     * @return a Projection data structure with the min-max projections.
     */
    private Projection projectCircleOntoAxis(Shape s, Vector2D axis)
    {
        Projection proj = new Projection();
        //Remember to add the position offset in the direction of the projected axis
        proj.min = -s.getRadius() + s.getPosition().dot(axis);
        proj.max = s.getRadius() + s.getPosition().dot(axis);
        return proj;
    }
    
}
class Projection
{
    public float min;
    public float max;
    
    /**
     * The amount of overlap between two projection intervals
     * @param p2 the other projection interval 
     * @return a positive value indicating the amount of overlap in the given
     * projections.
     */
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
