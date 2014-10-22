/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.GUI;

import Engine.Graphics;
import Engine.Mouse;
import Engine.Keys;
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
public class TextBox extends Component{
    public boolean isHovered;
    public boolean isClicked = false;
    public String message = "";
    protected float border = 25;
    
    public TextBox(String text, float x, float y) {
        super(x, y,10,10);
        message = text;
        this.setFont("Arial", 30);       
        
        //this.height = this.txtHeight + border;
        //this.width = this.txtWidth + border;
        
       
    }
     @Override
    public void update(float delta){
        super.update(delta);
        isHovered = isHovering(Mouse.x, Mouse.y);
        if(isHovered && Mouse.isPressed()){ //Case where It is currently hovering over the TextBox and clicks on it
            isClicked = true;
            handleInput();
        }
        else if(!isHovered && Mouse.isPressed()){ //Case where it is not hovering over the textbox and clicks on something
            isClicked = false;
        }
        else if(!Mouse.isPressed() && isClicked){ // Case where mouse has not been clicked and the textbox was clicked previously
            handleInput();
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
        g.fillRect(this.position.x-border/2, this.position.y+border/2-this.height, width, height);
        g.setColor(Color.WHITE.getRGB());
        g.drawRect(this.position.x-border/2, this.position.y+border/2-this.height, width, height);
        g.drawLine(this.position.x-border/2, this.position.y-border/2+25, this.position.x-border/2 + width, this.position.y-border/2+25);
        
        g.drawString(message, this.position.x, this.position.y);                
    }
    
    public void handleInput(){
        if(message.length() >= 10)return; //Impose a limit of 10 Characters
        
        if(Keys.isPressed(Keys.W)){
            message = message + "W";
            System.out.println("Here");
        }
        if(Keys.isPressed(Keys.S)){
            message = message + "S";
        }
        if(Keys.isPressed(Keys.A)){
            message = message + "A";
        }
        if(Keys.isPressed(Keys.D)){
            message = message + "D";
        }
       // setText(message);
        
        
        
    }    

    @Override
    public void dispose() {
    }
    
}
