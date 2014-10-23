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
 * @version 1.0
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
    
    public TextBox(String text, float x, float y) {
        super(x, y,StringBuilder.getWidth(text, "Arial", 20)+25,StringBuilder.getHeight(text, "Arial", 20)+25);
        message =text;
        Keys.addKeyListener(this);
        this.setFont("Arial", 20);     
       
    }
     @Override
    public void update(float delta){
        super.update(delta);
        isHovered = isHovering(Mouse.x, Mouse.y);   
        if (StringBuilder.getWidth(message, getFont(), (int)getFontSize())+25 > width){
            width = StringBuilder.getWidth(message, getFont(), (int)getFontSize())+25;
        }
        caretTime +=delta;
        if(caretTime >=5){
            caretTime =0;
            caretVisible = !caretVisible;
        }
    }
    
    @Override
    public void draw(Graphics g)
    {           
        if(isHovered)
            g.setColor(new Color(0,176,240).getRGB());
        else
            g.setColor(Color.BLACK.getRGB());
        g.setFont(this.getFont(), Graphics.BOLD, this.fontSize);
        g.fillRect(this.position.x, this.position.y, width, height);
        g.setColor(Color.WHITE.getRGB());
        g.drawRect(this.position.x, this.position.y, width, height);        
        g.drawString(message, this.position.x +border/2, this.position.y+height-border/2);   
        if(caretVisible && isFocused())
        {
            g.drawLine(this.position.x +border/2+1 + StringBuilder.getWidth(message, getFont(), (int)getFontSize()), this.position.y +border/2,
                       this.position.x +border/2+1 + StringBuilder.getWidth(message, getFont(), (int)getFontSize()), this.position.y +height -border/2);
        }
    }   
    public String getText(){return this.message;}
    public void setText(String text){this.message = text;}

    @Override
    public void dispose() {
        Keys.removeKeyListener(this);
    }

    @Override
    public void KeyTyped(int keyCode, char keyChar) {
        if(this.isFocused()){            
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
