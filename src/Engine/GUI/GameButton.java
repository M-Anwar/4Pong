/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.GUI;

import Engine.Graphics;
import Engine.Mouse;
import G4Pong.GamePanel;
import java.awt.Color;
import Engine.StringBuilder;

/**
 * A simple button which supports click events
 * @author muhammed.anwar
 */
public class GameButton extends Component
{   
    private boolean isHovered;    
    protected ButtonListener listener;
    protected float border = 25;
    private boolean forceClick;
    //The Data stored in the button
    protected String buttonText;
    
    public GameButton(String text, float x, float y) {       
        super(x,y,10,10);
        setText(text);
        this.setFont("Arial", 30);       
        this.setEnabled(true);
        this.height = StringBuilder.getHeight(text,getFont(), (int)getFontSize()) + border;
        this.width = StringBuilder.getWidth(text,getFont(), (int)getFontSize()) + border;   
        listener = null; 
        forceClick = false;
    }


    public void addButtonListener(ButtonListener l){
        this.listener = l;
    }
    @Override
    public void update(float delta)
    {
        super.update(delta);
        isHovered = isHovering(Mouse.x,Mouse.y);
        if(listener!=null && forceClick==true){listener.buttonClicked(); forceClick = false;}
        if(isHovered && isEnabled()){
            if(Mouse.isPressed()){
               if(listener!=null){              
                   GamePanel.getAudio().play("BUTTON");
                   listener.buttonClicked();
               }
            }
        }
    }   
    public void setText(String text)
    {
        buttonText = text;
        this.height = StringBuilder.getHeight(text, getFont(), (int)getFontSize()) + border;
        this.width = StringBuilder.getWidth(text, getFont(), (int)getFontSize()) + border;               
    }
    public void setFont(String font, int size){
       super.setFont(font, size);
       setText(buttonText);
    }
    
    public String getText(){return this.buttonText;}   
    
    @Override
    public void draw(Graphics g)
    {           
        if(isHovered && this.isEnabled())
            g.setColor(new Color(0,176,240).getRGB());
        else
            g.setColor(Color.BLACK.getRGB());
        g.setFont(this.getFont(), Graphics.BOLD, this.fontSize);
        g.fillRect(this.position.x, this.position.y, width, height);
        g.setColor(Color.WHITE.getRGB());
        g.drawRect(this.position.x, this.position.y, width, height);
        if(this.isEnabled()==false){g.setColor(Color.GRAY.getRGB());}
        else{g.setColor(Color.WHITE.getRGB());}
        g.drawString(this.getText(), this.position.x+border/2, this.position.y+height-border/2);                
    }
    public void doClick(){forceClick=true;}
    @Override
    public void dispose() {        
    }
    
}
