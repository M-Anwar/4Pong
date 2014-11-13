/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Geometry;

import Engine.Graphics;
import Engine.Vector2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Generic shape definition used for collision detection and shape representation. 
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
 * 
 * TODO: need a way of returning or offline calculating the point of collision
 * (or collision manifold using GJK algorithm). 
 * 
 * @author muhammed.anwar
 *  With helpful contributions from Narayan Jat (collision normals!!).
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
                result.mts = v.normalize().scale(-difference);
                result.normal = result.mts.normalize();
                result.poc[0] = this.getPosition().add(result.normal.scale(radius));
                result.poc[1]= null;
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
                if(overlap>0){
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
                if(overlap>0){
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
            result.normal = result.mts.normalize();
            //findPOC(result,vertex1, vertex2);
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
                if(overlap>0){
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
            if(overlap>0){
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
            result.normal = result.mts.normalize();
            if(this.getType()== ShapeType.POLYGON)
                result.poc[0] = circle.getPosition().add(result.normal.scale(-circle.radius));
            else
                result.poc[0] = circle.getPosition().add(result.normal.scale(circle.radius));
            result.poc[1]= null;
            return result;
        }        
        
        return null;
    }    
    
    //<editor-fold defaultstate="collapsed" desc="Collide Function Test">
    public CollisionResult collidesTest(Shape s,Graphics g)
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
                result.mts = v.normalize().scale(-difference);
                result.normal = result.mts.normalize();
                result.poc[0] = this.getPosition().add(result.normal.scale(radius));
                result.poc[1]= null;
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
                if(overlap>0){
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
                if(overlap>0){
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
            result.normal = result.mts.normalize();
            findPOC(result,vertex1, vertex2,g);
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
                if(overlap>0){
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
            if(overlap>0){
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
            result.normal = result.mts.normalize();
            if(this.getType()== ShapeType.POLYGON)
                result.poc[0] = circle.getPosition().add(result.normal.scale(-circle.radius));
            else
                result.poc[0] = circle.getPosition().add(result.normal.scale(circle.radius));
            result.poc[1]= null;
            return result;
        }
        
        return null;
    }
//</editor-fold>
    /**
     * Calculates the collision manifold for the intersection of two convex
     * polygons. The collision result should be passed as a reference and the 
     * collision manifold will be added to the CollisionResult. The method used
     * is Contact point clipping, similar to the one used in Box2D. A
     * detailed explanation can be found here:
     * http://www.codezealot.org/archives/394
     * @param result the collision result to store the data in
     */
    private void findPOC(CollisionResult result,Vector2D[] verticesA, Vector2D[] verticesB,Graphics g)
    {
        //Find the farthest vertex in the polygon along the seperation normal
        g.setColor(Color.CYAN.getRGB());
        g.drawLine(verticesA[0].x,verticesA[0].y,verticesA[0].add(result.normal.scale(10)).x,verticesA[0].add(result.normal.scale(10)).y);
        
        Vector2D[] bestA = getBestEdge(verticesA,result.normal);
        Vector2D[] bestB = getBestEdge(verticesB, result.normal.scale(-1));
        Vector2D e1 = bestA[1].subtract(bestA[0]);
        Vector2D e2 = bestB[1].subtract(bestB[0]);
        
        Vector2D[] ref, inc;
        Vector2D refDir;
        boolean flip = false;
        if(Math.abs(e1.dot(result.normal))<=Math.abs(e2.dot(result.normal))){
            ref = bestA; inc = bestB;
            refDir = e1.normalize();
        }else{
            ref = bestB; inc = bestA; flip= true;
            refDir = e2.normalize();
        }
        
        g.setColor(Color.GREEN.getRGB());
        g.drawLine(ref[0].x, ref[0].y, ref[1].x, ref[1].y);
        g.setColor(Color.YELLOW.getRGB());
        g.drawLine(inc[0].x, inc[0].y, inc[1].x, inc[1].y);
        
        //Begin Clipping
        float offset = refDir.dot(ref[0]);
        ArrayList<Vector2D> cp = clip(inc[0],inc[1],refDir,offset);
        if(cp.size()<2)return;
        offset = refDir.dot(ref[1]);
        cp = clip(cp.get(0),cp.get(1),refDir.scale(-1), -offset);
        if(cp.size()<2)return;
        
        Vector2D refNorm = refDir.getPerpendicular().scale(-1);
        if(flip)refNorm.scale(-1);
        float max = refNorm.dot(ref[0]);
        if(refNorm.dot(cp.get(0))-max <0){   
            cp.set(0, null);            
        }
        if(refNorm.dot(cp.get(1))-max <0){
           cp.set(0,null);
        }
        try{
            result.poc[0] = cp.get(0);
            result.poc[1] = cp.get(1);
        }catch(Exception e){}
    }
    private ArrayList<Vector2D> clip(Vector2D v1, Vector2D v2, Vector2D n, float o)
    {
        ArrayList<Vector2D> cp = new ArrayList<>();
        float d1 = n.dot(v1)-o;
        float d2 = n.dot(v2)-o;
        if(d1>=0)cp.add(v1);
        if(d2>=0)cp.add(v2);
        if(d1*d2<0)
        {
            Vector2D e = v2.subtract(v1);
            float u = d1/(d1-d2);
            e.thisScale(u);
            e.thisAdd(v1);
            cp.add(e);
        }
        return cp;
    }
    private Vector2D[] getBestEdge(Vector2D[] vertices,Vector2D norm){
        
        //Find the farthest vertex in the polygon along the seperation normal
        float max = norm.dot(vertices[0]);        
        int index=0;
        for(int i =1; i <vertices.length; i ++){
            float proj = norm.dot(vertices[i]);
            if(proj>max){
                max = proj;
                index =i;
            }
        }        
        Vector2D v = vertices[index];
        Vector2D v1 = vertices[(index+1)%vertices.length];
        if(index-1<0)index = vertices.length-1;
        Vector2D v0 = vertices[index];
        
        Vector2D l = v.subtract(v1);
        Vector2D r = v.subtract(v0);
        l.thisNormalize();
        r.thisNormalize();
        if(r.dot(norm)<=l.dot(norm)){return new Vector2D[]{new Vector2D(v0),new Vector2D(v)};}
        else return new Vector2D[]{new Vector2D(v),new Vector2D(v1)};
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
    
    /**
     * Returns whether or not the given point is contained in the current shape
     * @param point The point to check against
     * @return true if the point is contained, false if not. If the point is on
     * the edge of the shape the return might be true or false.
     */
    public boolean containsPoint(Vector2D point)
    {
        /*Algorithm provided by http://alienryderflex.com/polygon/ without 
         pre-calc. Works for both concave and convex polygons and circles.*/
        if(this.type == ShapeType.POLYGON){
            Vector2D[] vertices = this.getVertices();
            int i, j = vertices.length-1;
            boolean oddnodes = false;
            for(i=0; i < vertices.length; i++)
            {
                if( (vertices[i].y < point.y && vertices[j].y >= point.y || vertices[j].y < point.y && vertices[i].y >= point.y) 
                        &&(vertices[i].x <=point.x || vertices[j].x <=point.x)){
                    oddnodes ^=(vertices[i].x +(point.y-vertices[i].y)/(vertices[j].y-vertices[i].y)*(vertices[j].x-vertices[i].x) <point.x);
                }
                j = i;
            }            
            return oddnodes;
        }
        /* If the point is at a distance smaller then the radius of the cirle,
        then it is contained.
        */
        if(this.type == ShapeType.CIRCLE){
            float rad = this.getRadius();
            Vector2D pos = this.getPosition();
            if(point.subtract(pos).length2() < rad*rad) return true;
            else return false;
        }       
        return false;
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
