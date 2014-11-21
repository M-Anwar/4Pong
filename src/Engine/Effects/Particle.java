/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Effects;

import Engine.Graphics;
import Engine.Vector2D;

/**
 * A object that represents a particle in space
 * @author muhammed.anwar
 */
public class Particle {
    public Vector2D position;
    public Vector2D velocity;
    public float rotation;
    public float size;
    public float lifeTime;  
    public boolean isAlive;
    private float curLife;
    ParticleRenderer renderer;
    public Particle()
    {
        position= new Vector2D();
        velocity = new Vector2D();
        lifeTime = 10;
        size = 10;
        isAlive = true;
        curLife=0;
    }
    public void update(float delta)
    {
        this.position.thisAdd(velocity.scale(delta));
        this.velocity.thisScale((float)Math.pow(0.90f, delta));
        curLife+=delta;
        if(curLife>=lifeTime)isAlive = false;
    }
    public void draw(Graphics g)
    {
        renderer.draw(this,g);
    }   
}
