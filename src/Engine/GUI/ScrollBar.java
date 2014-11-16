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
 * Handles the scrolling of a custom component.
 * The usage of a scrollbar is as follows:
 *  - the component class must implement the Scrollable interface;
 *  - the component then must instantiate an instance of the ScrollBar object
 *  - the ScrollBar instance needs to be updated (update) and drawn (draw) in the
 *      appropriate methods within the component class. the draw method
 *      should be called at the very end of the component rendering or else
 *      the scroll bars may be drawn over.
 *  - To get the amount that the drawn component must displace its contents can be 
 *      found by invoking the getHorizontalOffset() and getVerticalOffset().
 * 
 * 
 * @author muhammed.anwar
 */
public class ScrollBar extends Component
{
    Scrollable parent;
    private float yPos;
    private boolean isHovered;
    private HorizontalScrollBar horScroll;
    private VerticalScrollBar verScroll;
    public ScrollBar(Scrollable s) {
        super(s.getPosition().x, s.getPosition().y,40,40);
        this.width = 5;
        this.height = 5;
        parent = s;
        horScroll = new HorizontalScrollBar(5,s);
        verScroll = new VerticalScrollBar(5,s);
    }
    
    @Override
    public void update(float delta)
    {
        position = parent.getPosition();   
        horScroll.update(delta);
        verScroll.update(delta);        
    }
    @Override
    public void draw(Graphics g)
    {
        horScroll.draw(g);
        verScroll.draw(g);    
    }
    
    
    public void dispose() {
    }
    public float getHorizontalOffset(){return horScroll.getScrollOffset();}
    public void setHorizontalToEnd(){horScroll.setScrollToEnd();}
    public float getVerticalOffset(){return verScroll.getScrollOffset();}
    public void setVerticalToEnd(){verScroll.setScrollToEnd();}
    public void setHorizontalToBeg(){horScroll.setScrollToBegin();}
    public void setVerticalToBeg(){verScroll.setScrollToBegin();}
   
    
}
class VerticalScrollBar extends Component{

    private Scrollable area;
    private float verOffset;
    private float oldPos;
    private boolean isHovered;
    private float scrollJump;
    private boolean firstPress = true;
    private float mouseY;
    
    public VerticalScrollBar(float width, Scrollable s) {
        super(0,0,width,s.getActualHeight());
        area =s;
        verOffset =0;
    }
    public void update(float delta){
        this.position = area.getPosition().add(new Vector2D(area.getActualWidth()-width,verOffset));
        if(area.getActualHeight() > area.getPrefferedHeight()) return;
        if(position.y < area.getPosition().y){verOffset =0;}
        if(position.y + height >area.getPosition().y + area.getActualHeight()){verOffset = area.getActualHeight()-height;}
        this.height = area.getActualHeight() * area.getActualHeight()/area.getPrefferedHeight();
        scrollJump = area.getPrefferedHeight()/area.getActualHeight();
        isHovered = isHovering(Mouse.x,Mouse.y);
        if(isHovered)
        {
            if(Mouse.isDown()){
                if(firstPress){
                    mouseY = Mouse.y;
                    firstPress = false;
                }else{
                    verOffset = Mouse.y - mouseY + oldPos;
                    if(verOffset<0){verOffset =0;}
                    if(verOffset > area.getActualHeight()-height){verOffset = area.getActualHeight()-height;}
                }
            }else{ oldPos = verOffset;firstPress = true;}
        }
        else{
            if(!firstPress){
                verOffset = Mouse.y - mouseY + oldPos;
                if(verOffset<0){verOffset =0;}
                if(verOffset > area.getActualHeight()-height){verOffset = area.getActualHeight()-height;}
            }
            if(!Mouse.isDown()){
                oldPos = verOffset;firstPress = true;
            }
        }
    }
    public void draw(Graphics g){
        if(area.getActualHeight() > area.getPrefferedHeight()) return;
        if(isHovered){
            g.setColor(new Color(166,166,166).getRGB());
        }
        else{
            g.setColor(Color.WHITE.getRGB());
        }
        g.fillRect(position.x, position.y, width, height);
    }
    @Override
    public void dispose() {
    }
    
     public float getScrollOffset(){
        if(area.getActualHeight() > area.getPrefferedHeight()) return 0;
        return -verOffset*scrollJump;

    }
    public void setScrollToEnd(){update(0);verOffset = area.getActualHeight()-height;}
    public void setScrollToBegin(){verOffset = 0;}

    
}
class HorizontalScrollBar extends Component{

    private Scrollable area;
    private float horOffset;
    private float oldPos;
    private boolean isHovered;
    private float scrollJump;
    private boolean firstPress = true;
    private float mouseX;
    public HorizontalScrollBar(float height,Scrollable s) {
        super(0, 0, s.getActualWidth(), height);              
        area =s;
        horOffset =0;
    }
    public void update(float delta){
        this.position = area.getPosition().add(new Vector2D(horOffset,area.getActualHeight()-height));
        if(area.getActualWidth() > area.getPrefferedWidth()) return;
        if(position.x < area.getPosition().x){horOffset =0;}
        if(position.x + width >area.getPosition().x + area.getActualWidth()){horOffset = area.getActualWidth()-width;}
        this.width = area.getActualWidth() * area.getActualWidth()/area.getPrefferedWidth();
        scrollJump = area.getPrefferedWidth()/area.getActualWidth();
        isHovered = isHovering(Mouse.x,Mouse.y);
        if(isHovered)
        {
            if(Mouse.isDown()){
                if(firstPress){
                    mouseX = Mouse.x;
                    firstPress = false;
                }else{
                    horOffset = Mouse.x - mouseX + oldPos;
                    if(horOffset<0){horOffset =0;}
                    if(horOffset > area.getActualWidth()-width){horOffset = area.getActualWidth()-width;}
                }
            }else{ oldPos = horOffset;firstPress = true;}
        }
        else{
            if(!firstPress){
                horOffset = Mouse.x - mouseX + oldPos;
                if(horOffset<0){horOffset =0;}
                if(horOffset > area.getActualWidth()-width){horOffset = area.getActualWidth()-width;}
            }
            if(!Mouse.isDown()){
                oldPos = horOffset;firstPress = true;
            }
        }
    }
    public void draw(Graphics g)
    {
        if(area.getActualWidth() > area.getPrefferedWidth()) return;
        if(isHovered){
            g.setColor(new Color(166,166,166).getRGB());
        }
        else{
            g.setColor(Color.WHITE.getRGB());
        }
        g.fillRect(position.x, position.y, width, height);
    }
    public float getScrollOffset(){
        if(area.getActualWidth() > area.getPrefferedWidth()) return 0;
        return -horOffset*scrollJump;

    }
    public void setScrollToEnd(){update(0);horOffset = area.getActualWidth()-width;}
    public void setScrollToBegin(){horOffset = 0;}
    @Override
    public void dispose() {
    }
    
}
