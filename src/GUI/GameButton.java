/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import Engine.Graphics;
import Engine.Mouse;
import java.awt.Color;

/**
 *
 * @author muhammed.anwar
 */
public class GameButton extends Component
{
    protected float width;
    protected float height;
    private boolean isHovered;
    private float border;
    
    ButtonListener listener;
    
    public GameButton(String text, float x, float y) {
        super(text, x, y);
        this.setFont("Arial", 30);        
        border = 25;
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
    
    public boolean isHovering(int x, int y) {       
        return x > this.position.x-border/2 && x < this.position.x-border/2+ width && 
                y > this.position.y-border/2-this.txtHeight  && y < this.position.y -border/2-this.txtHeight+ height;        
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
    public float getWidth(){return this.width;}
    public float getHeight(){return this.height;}
}
