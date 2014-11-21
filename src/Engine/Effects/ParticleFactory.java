/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Effects;

import Engine.Graphics;
import Engine.Vector2D;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author muhammed.anwar
 */
public class ParticleFactory 
{
    public ArrayList<Particle> particles;
    private ArrayList<Particle>remove;
    public ParticleFactory()
    {
        particles = new ArrayList<>();
        remove = new ArrayList<>();
    }
    public void createExplosion(float x, float y, int numParticles)
    {
        for(int i =0; i <numParticles; i ++)
        {
            Particle p = new Particle();
            p.position = new Vector2D(x,y);
            p.velocity = new Vector2D((float)Math.random()*(30+30)-30,(float)Math.random()*(30+30)-30);
            DefaultParticleRenderer r= new DefaultParticleRenderer();
            r.color= Color.WHITE.getRGB();
            p.renderer = r;
            p.lifeTime = (float)Math.random()*(10-5)+5;
            p.size =(float)Math.random()*(100-5)+5;
            particles.add(p);
        }
    }
    public void update(float delta)
    {
        
        for(Particle p: particles){
            p.update(delta);
            if(!p.isAlive)
                remove.add(p);
        }
        for(Particle p: remove)particles.remove(p);
    }
    public void draw(Graphics g){
        for(Particle p: particles)p.draw(g);

    }
}
