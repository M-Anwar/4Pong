/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

/**
 * A simple 2D vector class with basic geometric methods.
 * @author muhammed.anwar
 */
public class Vector2D 
{
    public float x;
    public float y;
    
    //Constructors    
    public Vector2D(){this.x = 0; this.y=0;}
    public Vector2D(float x, float y){
        this.x = x;
        this.y = y;
    }
    public Vector2D(Vector2D v){
        this.x = v.x;
        this.y = v.y;
    }
    public Vector2D(float magnitude, float angle, float t)
    {
        this.x = (float)(magnitude * Math.cos(angle));
        this.y = (float)(magnitude * Math.sin(angle));
    }
    public Vector2D(int[] comp){
        if (comp.length !=2) {
            this.x = 0; this.y =0;
        }
        else{
            this.x = comp[0];
            this.y = comp[1];
        }           
    }
    
    //Vector operations
    /**
     * Adds two vectors together and stores the result in the current vector
     * @param v - vector to thisAdd 
     */
    public void thisAdd(Vector2D v){ 
        this.x += v.x;
        this.y += v.y;
    }
    /**
     * Adds two vectors and returns the new vector result
     * @param v the vector to add to the current one
     * @return the result of the addition
     */
    public Vector2D add(Vector2D v){
        return new Vector2D(x+v.x, y+v.y);        
    }
    /**
     * Adds the x and y components to the current vector
     * @param x - x component to thisAdd
     * @param y - y component to thisAdd
     */
    public void thisAdd(float x, float y){
        this.x += x;
        this.y += y;
    }    
    /**
     * Subtracts the current vector from the given vector
     * @param v the vector to thisSubtract
     */
    public void thisSubtract(Vector2D v){
        this.x -= v.x;
        this.y -= v.y;
    }
    /**
     * Subtracts two vectors and returns the new vector result
     * @param v the vector to subtract from the current one
     * @return the result of the subtraction
     */
    public Vector2D subtract(Vector2D v){
        return new Vector2D(x-v.x, y-v.y);
    }
    /**
     * Subtracts the current vector components with the given ones
     * @param x - x component to thisSubtract
     * @param y - y component to thisSubtract
     */
     public void thisSubtract(float x, float y){
        this.x -= x;
        this.y -= y;
    }
     
    /**
     * Returns the dot product of the current vector and another vector
     * @param v - the vector to perform the dot product with
     * @return the dot product (this.x*v.x + this.y*v.y)
     */
    public float dot(Vector2D v)
    {
        return this.x*v.x + this.y*v.y;
    }
    
    /**
     * Returns a new vector which is the scaled version of the current vector
     * Scales by multiplying both components by the scalar
     * @param s - the scaler to scale both components of the vector by
     * @return a new Vector2D object which is the scaled version
     */
    public Vector2D scale(float s){
        return new Vector2D(this.x*s, this.y*s);
    }
    
      /**
     * Returns a new vector which is the scaled version of the current vector
     * Scales by multiplying both components by the scalars
     * @param x - the x-component scalar
     * @param y - the y-component scalar
     * @return a new Vector2D object which is the scaled version
     */
    public Vector2D scale(float x, float y){
        return new Vector2D(this.x*x, this.y*y);
    }
    /**
     * Scales the current vector by multiplying both components by the scalar
     * @param s - the scaler to scale both components of the vector by
     */
    public void thisScale(float s){
        this.x *= s;
        this.y *= s;
    }
    
    /**
     * Scales the current vector by multiplying the x-component by one scaler
     * and the y-component by another scalar
     * @param x - the x-component scalar
     * @param y - the y-component scalar
     */
    public void thisScale(float x, float y){
        this.x *= x;
        this.y *= y;
    }
    
    /**
     * Returns the length of the current vector
     * @return the length of the current vector
     */
    public float length(){
        return (float)Math.sqrt(this.x*this.x + this.y*this.y);
    }
    
    /**
     * Returns the length of the vector squared. In other words, dosn't
     * perform the square root operation which is more efficient.
     * @return 
     */
    public float length2(){
        return this.x*this.x + this.y*this.y;
    }
    
    /**
     * Normalizes the current vector by dividing each component by the length.
     * (i.e makes the current vector a unit vector)
     */
    public void thisNormalize()
    {
        float length = length();
        this.x = this.x/length;
        this.y = this.y/length;
    }
    
    /**
     * Normalizes the current vector by dividing each component by the length.
     * (i.e makes the current vector a unit vector)
     * @return new normalized vector
     */
    public Vector2D normalize(){
        return new Vector2D(this.x/length(), this.y/length());
    }
    /**
     * Checks the equality between the current vector and another vector
     * @param v - the other vector to check equality with
     * @return - true if they are equal, false if they are not
     */
    public boolean equals(Vector2D v){
        return this.x == v.x && this.y == v.y;
    }
    
    /**
     * Returns the angle of the current vector
     * @return the angle in radians, the angle is between -pi and +pi. The exact
     * orientation of the angle can be found in the documentation Appendix. But
     * for short I will thisAdd it here:
     * <pre>      |-90      </pre>
     * <pre>180   |         </pre>
     * <pre>------------ 0  </pre>
     * <pre>-180  |         </pre>
     * <pre>      |90       </pre>
     */
    public float angle(){
        float a = (float)Math.toDegrees((float)Math.atan(y/x));        
        if (x<0 && y >0) {
            a += 180;
        }
        else if(x<0 && y<0){
            a -= 180;
        }        
        return (float)Math.toRadians(a);
    }
    
    /**
     * Rotates the current vector by the given angle,using the same angle convention
     * @param radians the amount of radians to rotate by
     */
    public void thisRotate(float radians)
    {
        float temp = this.x;
        this.x = x*(float)Math.cos(radians) - y*(float) Math.sin(radians);
        this.y = temp*(float)Math.sin(radians) + y*(float) Math.cos(radians);
    }
    
    /**
     * Rotates the current vector by the given angle and returns the new vector
     * @param radians the amount of radians to rotate by
     * @return the newly rotated vector
     */
    public Vector2D rotate(float radians)
    {
        Vector2D v = new Vector2D();
        v.x = x*(float)Math.cos(radians) - y*(float) Math.sin(radians);
        v.y = x*(float)Math.sin(radians) + y*(float) Math.cos(radians);
        return v;
    }
    /**
     * Returns the angle between two vectors. Using the same angle convention.
     * @param v - the vector to find the angle between
     * @return the angle in radians
     */
    public float angle(Vector2D v)
    {
        return (float)Math.acos((float)(dot(v)/(length()*v.length())));
    }
    
    /**
     * Returns a new vector which is perpendicular  to the current vector.
     * Returns an outward facing vector for a "clockwise" oriented vector.
     * @return the perpendicular vector
     */
    public Vector2D getPerpendicular ()
    {
        return new Vector2D(y,-x);
    }
    /**
     * Returns a new vector which is reflected with the given normal. 
     * <p>For more information look in the documentation appendix. </p>
     * <p>Uses r = d - ((2* d (dot) n)/|n|^2)*n to calculate the reflection
     * vector </p>
     * @param normal the normal of the reflecting surface
     * @return the reflection vector 
     */
    public Vector2D bounceNormal(Vector2D normal){       
        return this.subtract(normal.scale(2*(dot(normal))/normal.length2()));       
    }
    /**
     * Reflect the current vector with the given normal. 
     * <p>For more information look in the documentation appendix. </p>
     * <p>Uses r = d - ((2* d (dot) n)/|n|^2)*n to calculate the reflection
     * vector </p>
     * @param normal the normal of the reflecting surface  
     */
    public void thisBounceNormal(Vector2D normal){       
        this.thisSubtract(normal.scale(2*(dot(normal))/normal.length2()));       
    }
    public String toString(){
        return "[x: "+ x + " y: " + y + "]";
    }
}
