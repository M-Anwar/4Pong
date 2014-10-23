/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.GUI;

import Engine.Graphics;
import Engine.KeyListener;
import Engine.Keys;
import Engine.Mouse;
import Engine.StringBuilder;
import java.awt.Color;
import java.util.ArrayList;

/**
 * A basic text box which supports many basic text box operations.
 * The textbox does not support:
 *  - arbritrary text position (i.e there is no caret
 *    that represents the current editing position of the text [technically limited]).
 *  - Copy/Paste from clipboard.
 *  - Selection Highlighting (no selected text)
 *  - Multiple fonts and font sizes.
 *  - Integrated images of any kind
 * 
 * The textbox does support:
 *  - Multiline text
 *  - Resizing component window (i.e scroll bars as viewport).
 *  - Basic event reporting, such as enter key pressed or text changed events.    
 *  - Maximum character count.
 * 
 * @version 1.1
 * @author Jason Xu and Muhammed Anwar
 * Created by Jason Xu since version 1.0, revised by Muhammed Anwar
 * Version History: 
 * Version 1.0 - Basic Skeleton of the text box with simple inputs. * 
 */
public class TextBox extends Component implements KeyListener{
    private boolean isHovered;    
    protected String message = "";
    protected float border = 25;
    private boolean caretVisible =false;
    private float caretTime=0;
    private boolean isResize;
    private boolean isMultiLine;
    private boolean isEditable;
    
    private ArrayList<KeyListener> listeners;
    
    public TextBox(String text, float x, float y) {
        super(x, y,StringBuilder.getWidth(text, "Arial", 30)+25,StringBuilder.getHeight(text, "Arial", 30)+25);
        message =text;
        Keys.addKeyListener(this);
        listeners = new ArrayList<>();
        this.setFont("Arial", 30);     
       
    }
     @Override
    public void update(float delta){
        super.update(delta);
        isHovered = isHovering(Mouse.x, Mouse.y);   
        
        caretTime +=delta;
        if(caretTime >=5){
            caretTime =0;
            caretVisible = !caretVisible;
        }
    }
    
    @Override
    public void draw(Graphics g)
    {           
        g.setFont(this.getFont(), Graphics.BOLD, this.fontSize);
        int stringWidth = g.getFontDimension(message)[0];
        if (stringWidth+25 > width){
            width = stringWidth+25;
        }
        if(isHovered)
            g.setColor(new Color(0,176,240).getRGB());
        else
            g.setColor(Color.BLACK.getRGB());
        
        g.fillRect(this.position.x, this.position.y, width, height);
        g.setColor(Color.WHITE.getRGB());
        g.drawRect(this.position.x, this.position.y, width, height);        
        g.drawString(message, this.position.x +border/2, this.position.y+height-border/2);   
        if(caretVisible && isFocused())
        {           
            g.drawLine(this.position.x +border/2+1 + stringWidth, this.position.y +border/2,
                       this.position.x +border/2+1 + stringWidth, this.position.y +height -border/2);
        }
    }   
    public String getText(){return this.message;}
    public void setText(String text){this.message = text;}
    public void addKeyListener(KeyListener k){this.listeners.add(k);}
    
    @Override
    public void dispose() {
        Keys.removeKeyListener(this);
    }

    @Override
    public void KeyTyped(int keyCode, char keyChar) {
        if(this.isFocused()){    
            for(KeyListener k: listeners){
                k.KeyTyped(keyCode, keyChar);
            }
            if(keyCode == Keys.VK_BACK_SPACE){
                if(message.length()>=1)
                    message = message.substring(0, message.length()-1);
            }            
            else if(keyCode == Keys.VK_SHIFT){/*Nothing*/}
            else{                
                message = message+ keyChar;                
            }
        }
    }
    
}
