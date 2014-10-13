/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import Engine.Graphics;
import Engine.Mouse;
import G4Pong.GamePanel;
import java.awt.Color;

/**
 * A simple button which supports click events
 * @author muhammed.anwar
 */
public class GameButton extends Component
{   
    private boolean isHovered;    
    protected ButtonListener listener;
    
    public GameButton(String text, float x, float y) {       
        super(text, x, y);
        this.setFont("Arial", 30);       
        
        this.height = this.txtHeight + border;
        this.width = this.txtWidth + border;   
        listener = null;       
    }


    public void addButtonListener(ButtonListener l){
        this.listener = l;
    }
    @Override
    public void update(float delta)
    {
        super.update(delta);
        isHovered = isHovering(Mouse.x,Mouse.y);
        if(isHovered){
            if(Mouse.isPressed()){
               if(listener!=null){              
                   GamePanel.getAudio().play("BUTTON");
                   listener.buttonClicked();
               }
            }
        }
    }
    @Override
    public void setText(String text)
    {
        super.setText(text);
        this.width = this.txtWidth +border;
        this.height = this.txtHeight + border;               
    }
    
   
    
    @Override
    public void draw(Graphics g)
    {           
        if(isHovered)
            g.setColor(new Color(0,176,240).getRGB());
        else
            g.setColor(Color.BLACK.getRGB());
        g.setFont(this.getFont(), Graphics.BOLD, this.fontSize);
        g.fillRect(this.position.x-border/2, this.position.y-border/2-this.txtHeight, width, height);
        g.setColor(Color.WHITE.getRGB());
        g.drawRect(this.position.x-border/2, this.position.y-border/2-this.txtHeight, width, height);
        
        g.drawString(this.getText(), this.position.x, this.position.y);                
    }
    
}
