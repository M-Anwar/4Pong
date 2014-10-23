/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.GUI;

/**
 *
 * @author muhammed.anwar
 */
public class ScrollBar extends Component
{
    Component parent;
    
    public ScrollBar(Component comp) {
        super(comp.finalPosition.x, comp.finalPosition.y,0,0);
        parent = comp;
    }
    
    @Override
    public void update(float delta)
    {
        position = parent.position;
        
    }
    
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
