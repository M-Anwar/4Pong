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
     * @param v - vector to add 
     */
    public void add(Vector2D v){ 
        this.x += v.x;
        this.y += v.y;
    }
    
    /**
     * Adds the x and y components to the current vector
     * @param x - x component to add
     * @param y - y component to add
     */
    public void add(float x, float y){
        this.x += x;
        this.y += y;
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
    public void normalize()
    {
        this.x = this.x/length();
        this.y = this.y/length();
    }
    
    /**
     * Checks the equality between the current vector and another vector
     * @param v - the other vector to check equality with
     * @return - true if they are equal, false if they are not
     */
    public boolean equals(Vector2D v){
        if(this.x == v.x && this.y == v.y) return true;
        else return false;
    }
        
}
