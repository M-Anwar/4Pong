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
    public void add(Vector2D v){ 
        this.x += v.x;
        this.y += v.y;
    }
    public void add(float x, float y){
        this.x += x;
        this.y += y;
    }
    public float dot(Vector2D v)
    {
        return this.x*v.x + this.y*v.y;
    }
    public Vector2D scale(float s){
        return new Vector2D(this.x*s, this.y*s);
    }
    public void thisScale(float s){
        this.x *= s;
        this.y *= s;
    }
    public void thisScale(float x, float y){
        this.x *= x;
        this.y *= y;
    }
    public float length(){
        return (float)Math.sqrt(this.x*this.x + this.y*this.y);
    }
    public float length2(){
        return this.x*this.x + this.y*this.y;
    }
    public void normalize()
    {
        this.x = this.x/length();
        this.y = this.y/length();
    }
    
    public boolean equals(Vector2D v){
        if(this.x == v.x && this.y == v.y) return true;
        else return false;
    }
        
}
