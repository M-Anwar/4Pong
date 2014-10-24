/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.GUI;

import Engine.Geometry.Rectangle;
import Engine.Graphics;
import Engine.KeyListener;
import Engine.Keys;
import Engine.Mouse;
import Engine.StringBuilder;
import Engine.Vector2D;
import G4Pong.GamePanel;
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
 * @author Jason Xu, I refuse to give you credit lily
 * Created by Jason Xu 
 * Version History: 
 * Version 1.0 - Basic Skeleton of the text box with simple inputs. 
 * Version 1.1 - Asynchronous key inputs and basic formatting added
 */
public class TextBox extends Component implements KeyListener, Scrollable{
    private boolean isHovered;    
    protected String message = "";
    protected float border = 25;
    private boolean caretVisible =false;
    private float caretTime=0;
    private Vector2D caretPos;
    private boolean isResize=false;
    private boolean isMultiLine= false;
    private boolean isEditable =true;
    private int maxChars;
    
    private float prefferedHeight;
    private float prefferedWidth;
    
    private ScrollBar scroll;
    
    private ArrayList<KeyListener> listeners;
    private ArrayList<ButtonListener> mouseListeners;
    
    public TextBox(String text, float x, float y) {
        super(x, y,StringBuilder.getWidth(text, "Arial", 30)+25,StringBuilder.getHeight(text, "Arial", 30)+25);
        message =text;        
        this.setFont("Arial", 30);            
        init();
    }
    public TextBox(float x, float y, float width, float height){
        super(x,y,width,height);
        message = "";
        this.setFont("Arial", 30);
        init();
    }
    private void init(){ 
        scroll = new ScrollBar(this);
        caretPos = new Vector2D();
        Keys.addKeyListener(this);
        listeners = new ArrayList<>(); 
        mouseListeners = new ArrayList<>();
    }
     @Override
    public void update(float delta){
        super.update(delta);
        scroll.update(delta);
        isHovered = isHovering(Mouse.x, Mouse.y);   
        if(isHovered){
            if(Mouse.isPressed()){
               if(mouseListeners!=null){              
                   for(ButtonListener b: mouseListeners)
                       b.buttonClicked();
               }
            }
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
        g.setClip((int)this.position.x, (int)this.position.y, (int)width+1, (int)height+1);        
        g.setFont(this.getFont(), Graphics.BOLD, this.fontSize);        
        //Border and background
        if(isHovered && isEditable())
            g.setColor(new Color(0,176,240).getRGB());
        else
            g.setColor(Color.BLACK.getRGB());        
        g.fillRect(this.position.x, this.position.y, width, height);
        g.setColor(Color.WHITE.getRGB());
        g.drawRect(this.position.x, this.position.y, width, height); 
        
        //Text Rendering
        int stringWidth=-1;
        int tempWidth;
        int stringHeight = (int)StringBuilder.getHeight("T", getFont(), (int)getFontSize());
        String []parts;
        if(isMultiLine()) parts = message.split("\n");
        else{
            parts = new String[]{message};
        }
        for(int i=0; i <parts.length;i++){
            g.drawString(parts[i], this.position.x +border/2, this.position.y+stringHeight+(stringHeight+10)*i+border/2);   
            tempWidth = g.getFontDimension(parts[i])[0];
            if(i == parts.length-1){
                caretPos.x = this.position.x + border/2+1 + tempWidth;
                caretPos.y = this.position.y+stringHeight+(stringHeight+10)*(i-1)+10+border/2;
            }
            if(tempWidth>stringWidth){
                stringWidth = tempWidth;
            }
        }       
        this.prefferedWidth = stringWidth;
        this.prefferedHeight = (stringHeight+10)* parts.length + border/2;
        //Dimensions 
        if(isResize){            
            if (stringWidth+border > width){
                width = stringWidth+border;
            }
        }
        //g.drawRect(position.x+border/2, position.y, prefferedWidth, prefferedHeight);
        //Caret Positioning -> Always at the end
        if(caretVisible && isFocused() && isEditable()){           
            g.drawLine(caretPos.x, caretPos.y, caretPos.x, caretPos.y + stringHeight);
        }
        scroll.draw(g);
        g.setClip(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }   
    
    //Getter and Setter Methods    
    public String getText(){return this.message;}    
    public boolean isResizeable(){ return this.isResize;}
    public boolean isMultiLine(){ return this.isMultiLine;}
    public boolean isEditable(){ return this.isEditable;}
    
    public void resizeHeight(){this.height = StringBuilder.getHeight(message, getFont(), (int)getFontSize())+border;}
    public void appendText(String message){this.message +=message;}
    public void setText(String text){this.message = text;}
    public void setResizeable(boolean resize){this.isResize = resize;}
    public void setMultiLine(boolean multi){this.isMultiLine = multi;}
    public void setEditable(boolean edit){this.isEditable = edit;}
    public void setWidth(float width){this.width = width;}
    public void setHeight(float height){this.height = height;}
    public void addKeyListener(KeyListener k){this.listeners.add(k);}
    public void addMouseListener(ButtonListener k){this.mouseListeners.add(k);}
    
    @Override
    public void dispose() {
        Keys.removeKeyListener(this);
    }

    @Override
    public void KeyTyped(int keyCode, char keyChar) {
        if(this.isFocused() && this.isEditable()){   
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

    @Override
    public float getPrefferedWidth() {
        return this.prefferedWidth;
    }

    @Override
    public float getPrefferedHeight() {
        return this.prefferedHeight;
    }

    @Override
    public float getActualWidth() {
        return this.getWidth();
    }

    @Override
    public float getActualHeight() {
        return this.getHeight();
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }
    
}
