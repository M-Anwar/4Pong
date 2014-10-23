package Engine;

import Engine.Geometry.Rectangle;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 * A Java2D implementation of the graphics object
 * Adds functionality more closely related to the java2D graphics context
 * @author muhammed.anwar
 */
public class Java2DGraphics implements Graphics
{    
    private Graphics2D g;
    public Java2DGraphics(Graphics2D g) //Takes the Java2D Graphics2D context
    {
        this.g = g;
       
        this.g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );
        this.g.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING, 
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON            
        );
        this.g.setStroke(new BasicStroke(1));
        
    }
    
    /**
     * Draws the specified string on the screen
     * @param text text the text to be drawn
     * @param x the x-coordinate of the drawn text 
     * @param y the y-coordinate of the drawn text
     * <p>The x,y coordinates define the upper left corner of the bounding rectangle</p>
     */
    @Override  
    public void drawString(String text, float x, float y) {
        g.drawString(text, x, y);        
    }

    /**
     * Draws a line starting from (x1,y1) to (x2,y2)
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    @Override
    public void drawLine(float x1, float y1, float x2, float y2) {
        g.drawLine((int)x1,(int) y1, (int)x2,(int) y2);
    }

    /**
     * Draws the outline of a rectangle with upper left corner at (x,y) with
     * the given width and height
     * @param x upper left corner coordinate
     * @param y upper right corner coordinate
     * @param width of the rectangle
     * @param height of the rectangle
     */
    @Override
    public void drawRect(float x, float y, float width, float height) {
        g.drawRect((int)x, (int) y, (int)width, (int)height);
    }

    /**
     * Draws a filled rectangle with upper left corner at (x,y) with
     * the given width and height
     * @param x upper left corner coordinate
     * @param y upper right corner coordinate
     * @param width of the rectangle
     * @param height of the rectangle
     */
    @Override
    public void fillRect(float x, float y, float width, float height) {
        g.fillRect((int)x, (int)y, (int)width, (int)height);
    }

    /**
     * Draws the outline of an oval with upper left corner at (x,y) with
     * the given width and height (of the bounding box), width and height are
     * x,y radii
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @Override
    public void drawOval(float x, float y, float width, float height) {
        g.drawOval((int)x, (int)y, (int)width, (int)height);
    }

    /**
     * Draws a filled oval with upper left corner at (x,y) with
     * the given width and height (of the bounding box), width and height are
     * x,y radii
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @Override
    public void fillOval(float x, float y, float width, float height) {
        g.fillOval((int)x, (int)y, (int)width, (int)height);
    }

     /**
      * Sets the color of the graphics context
      * @param Color an integer with the RGBA Value
      */
    @Override
    public void setColor(int Color) {
        Color c = new Color(Color,true);        
        g.setColor(c);        
    }
    /**
     * Gets the color of the graphics context
     * @return the color of the graphics context
     */
    @Override
    public int getColor(){ return g.getColor().getRGB(); }

    /**
     * Draws an image on the screen
     * @param x -coordinate for drawing the image (upper left)
     * @param y -coordinate for drawing the image (upper left)
     * @param i -the image to be drawn
     */
    @Override
    public void drawImage(float x, float y, Image i) {
        Java2DImage j2dImage = (Java2DImage)i;
        g.drawImage(j2dImage.image, (int)x, (int)y, null);
    }
    
     /**
     * Draws an image on the screen to fit in the given bounds
     * @param x -coordinate for drawing the image (upper left)
     * @param y -coordinate for drawing the image (upper left)
     * @param i -the image to be drawn
     * @param width - width of the scaled image to be drawn
     * @param height - height of the scaled image to be drawn
     */
    @Override
    public void drawImage(float x, float y, Image i, int width, int height) {
        Java2DImage j2dImage = (Java2DImage)i;
        g.drawImage(j2dImage.image, (int)x, (int)y, width, height, null);
    }

    /**
     * Rotates the whole the graphics context by the specified angle at the 
     * specific location
     * @param angle - angle of rotation in radians
     * @param x - the x-coordinate origin of rotation 
     * @param y - the y-coordinate origin of rotation
     */
    @Override
    public void rotate(double angle, double x, double y) {
        g.rotate(angle,x,y);
    }

    /**
     * Translates the whole graphics context
     * @param x - the amount to translate in the x direction
     * @param y - the amount to translate in the y direction
     */
    @Override
    public void translate(double x, double y) {
        g.translate(x, y);
    }

    /**
     * Scales the whole graphics context
     * @param dx - the amount to scale in the x direction
     * @param dy - the amount to scale in the y direction
     */
    @Override
    public void scale(double dx, double dy) {
        g.scale(dx, dy);
    }

    /**
     * Sets the font of the graphics context for all subsequent text drawing
     * @param font the string name of th font to use
     * @param type either Graphics.BOLD or Graphics.Plain
     * @param size the size of the font 
     */
    @Override
    public void setFont(String font, String type, int size) {
        if (type.equals(Graphics.BOLD)){
            g.setFont(new Font(font,Font.BOLD,size));
        }else if (type.equals(Graphics.PLAIN)){
            g.setFont(new Font(font,Font.PLAIN,size));
        }
    }

    /**
     * Returns the name of the font
     * @return the text name of the font being used
     */
    @Override
    public String getFont() {
        return g.getFont().getName();
    }

    /**
     * Gets the screen space size of the text, using the font settings
     * @param text the text to get the dimensions of
     * @return an int[] array where int[0]-> width and int[1]->height 
     */
    @Override
    public int[] getFontDimension(String text) {
        int [] dimension = new int[2];
        FontMetrics f = g.getFontMetrics();       
        dimension[0] = (int)f.getStringBounds(text, g).getWidth();
        dimension[1] = (int)f.getStringBounds(text, g).getHeight();
        return dimension;
    }

    /**
     * Sets the thickness of the drawing context. I.e the line thickness.
     * @param thickness
     */
    @Override
    public void setThickness(int thickness) {
        g.setStroke(new BasicStroke(thickness));
    }

    @Override
    /**
     * Sets the drawing clip of the graphics context. Drawing does not occur
     * outside the clip
     * @param x - x-position of the clipping rectangle
     * @param y - y-position of the clipping rectangle
     * @param width - width of the clipping rectangle
     * @param height - height of the clipping rectangle
     */
    public void setClip(int x, int y, int width, int height) {
        g.setClip(x,y,width,height);       
    }

    @Override    
    public Rectangle getClip() {
        java.awt.Rectangle rect = g.getClipBounds();
        if(rect ==null)return null;
        return new Rectangle(rect.x,rect.y,rect.width,rect.height);
   }
    
    
    
}
