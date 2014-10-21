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
 *
 * @author Jason Xu
 */
public class TextBox extends Component{
    public boolean isHovered;
    public boolean isClicked = false;
    public String message = "";
    
    public TextBox(String text, float x, float y) {
        super(text, x, y);
        this.setFont("Arial", 30);       
        
        this.height = this.txtHeight + border;
        this.width = this.txtWidth + border;
        
       
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
        //g.drawRect(this.position.x-border/2, this.position.y+border/2-this.height, width, height);
        g.drawLine(this.position.x-border/2, this.position.x-border/2+25, this.position.x-border/2 + width, this.position.x-border/2+25);
        
        g.drawString(this.getText(), this.position.x, this.position.y);                
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
        setText(message);
        
        
        
    }
    
    @Override
    public void setText(String text)
    {
        super.setText(text);
        //this.width = this.txtWidth +border; Commented this out to get rid of bug where it would resize on first click
        //this.height = this.txtHeight + border;               
    }
}
