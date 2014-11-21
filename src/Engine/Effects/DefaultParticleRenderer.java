/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Effects;

import Engine.Graphics;
import Entity.ImageLoader;

/**
 *
 * @author muhammed.anwar
 */
public class DefaultParticleRenderer implements ParticleRenderer{

    public int color;
    @Override
    public void draw(Particle p, Graphics g) {      
        g.setColor(color);        
       // g.drawImage(p.position.x-p.size/2,p.position.y-p.size/2,ImageLoader.SMOKE1,(int)p.size,(int)p.size);
        g.fillOval(p.position.x-p.size/2, p.position.y-p.size/2, p.size, p.size);
    }              
    
}
