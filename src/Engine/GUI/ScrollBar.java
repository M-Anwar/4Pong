/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.GUI;

import Engine.Graphics;
import Engine.Mouse;
import Engine.Vector2D;
import G4Pong.GamePanel;
import java.awt.Color;

/**
 *
 * @author muhammed.anwar
 */
public class ScrollBar extends Component
{
    Scrollable parent;
    private float yPos;
    private boolean isHovered;
    
    public ScrollBar(Scrollable s) {
        super(s.getPosition().x, s.getPosition().y,40,40);
        this.width = 5;
        this.height = 5;
        parent = s;
    }
    
    @Override
    public void update(float delta)
    {
        position = parent.getPosition();   
        if(parent.getActualHeight() > parent.getPrefferedHeight()) return;
        height = parent.getActualHeight() * parent.getActualHeight()/parent.getPrefferedHeight();     
        isHovered = isHovering(Mouse.x,Mouse.y);
        if(isHovered)
        {
            if(Mouse.isPressed()){
                
            }
        }
    }
    @Override
    public void draw(Graphics g)
    {
        if(parent.getActualHeight() > parent.getPrefferedHeight()) return;
        if(isHovered)
        {
            g.setColor(Color.RED.getRGB());
        }
        else
            g.setColor(Color.WHITE.getRGB());
        g.fillRect(position.x, position.y+1+yPos, width, height);
    }
    
    
    public void dispose() {
    }

   
    
}
