/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

/**
 * An interface for a generic graphics object. Defines methods for
 * simple drawing.
 * @author muhammed.anwar
 */
public interface Graphics 
{    
    public static final String BOLD = "BOLD";
    public static final String PLAIN = "PLAIN";
    
    //Text functions    
    //<editor-fold defaultstate="collapsed" desc="drawString">
    /**
     * Draws the specified string on the screen
     * @param text text the text to be drawn
     * @param x the x-coordinate of the drawn text
     * @param y the y-coordinate of the drawn text
     * <p>The x,y coordinates define the upper left corner of the bounding rectangle</p>
     */
    public void drawString(String text,float x,float y);
//</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="setFont">
    /**
     * Sets the font of the graphics context for all subsequent text drawing
     * @param font the string name of th font to use
     * @param type either Graphics.BOLD or Graphics.Plain
     * @param size the size of the font
     */
    public void setFont(String font, String type, int size);
//</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="getFont">
    /**
     * Returns the name of the font
     * @return the text name of the font being used
     */
    public String getFont();
//</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="getFontDimension">
    /**
     * Gets the screen space size of the text, using the font settings
     * @param text the text to get the dimensions of
     * @return an int[] array where int[0]-> width and int[1]->height
     */
    public int[] getFontDimension(String text);
//</editor-fold>
        
    //Basic drawing primitives  
    //<editor-fold defaultstate="collapsed" desc="drawLine">
    /**
     * Draws a line starting from (x1,y1) to (x2,y2)
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawLine(float x1, float y1, float x2, float y2);
//</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="drawRect">
    /**
     * Draws the outline of a rectangle with upper left corner at (x,y) with
     * the given width and height
     * @param x upper left corner coordinate
     * @param y upper right corner coordinate
     * @param width of the rectangle
     * @param height of the rectangle
     */
    public void drawRect(float x, float y, float width, float height);
//</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="fillRect">
    /**
     * Draws a filled rectangle with upper left corner at (x,y) with
     * the given width and height
     * @param x upper left corner coordinate
     * @param y upper right corner coordinate
     * @param width of the rectangle
     * @param height of the rectangle
     */
    public void fillRect(float x, float y, float width, float height);
//</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="drawOval">
    /**
     * Draws the outline of an oval with upper left corner at (x,y) with
     * the given width and height (of the bounding box), width and height are
     * x,y radii
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void drawOval(float x, float y, float width, float height);
//</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="fillOval">
    /**
     * Draws a filled oval with upper left corner at (x,y) with
     * the given width and height (of the bounding box), width and height are
     * x,y radii
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void fillOval(float x, float y, float width, float height);
//</editor-fold>
    
    //Drawing Image 
    //<editor-fold defaultstate="collapsed" desc="drawImage">
    /**
     * Draws an image on the screen
     * @param x -coordinate for drawing the image (upper left)
     * @param y -coordinate for drawing the image (upper left)
     * @param i -the image to be drawn
     */
    public void drawImage(float x, float y,Image i);
//</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="drawImage(Scaled)">
    /**
     * Draws an image on the screen to fit in the given bounds
     * @param x -coordinate for drawing the image (upper left)
     * @param y -coordinate for drawing the image (upper left)
     * @param i -the image to be drawn
     * @param width - width of the scaled image to be drawn
     * @param height - height of the scaled image to be drawn
     */
    public void drawImage(float x, float y, Image i, int width, int height);
//</editor-fold>
    
    //Transform functions
    //<editor-fold defaultstate="collapsed" desc="rotate">
    /**
     * Rotates the whole the graphics context by the specified angle at the
     * specific location
     * @param angle - angle of rotation in radians
     * @param x - the x-coordinate origin of rotation
     * @param y - the y-coordinate origin of rotation
     */
    public void rotate(double angle, double x, double y);
//</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="translate">
    /**
     * Translates the whole graphics context
     * @param x - the amount to translate in the x direction
     * @param y - the amount to translate in the y direction
     */
    public void translate(double x, double y);
//</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="scale">
    /**
     * Scales the whole graphics context
     * @param dx - the amount to scale in the x direction
     * @param dy - the amount to scale in the y direction
     */
    public void scale(double dx, double dy);
//</editor-fold>
    
    //Getter and Setter for Color
    //<editor-fold defaultstate="collapsed" desc="setColor">
    /**
     * Sets the color of the graphics context
     * @param Color an integer with the RGBA Value
     */
    public void setColor(int Color);
//</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="getColor">
    /**
     * Gets the color of the graphics context
     * @return the color of the graphics context
     */
    public int getColor();
//</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="setThickness">
//Basic paint functions
    /**
     * Sets the thickness of the drawing context. I.e the line thickness.
     * @param thickness
     */
    public void setThickness(int thickness);
//</editor-fold>
    
    //Cliping functions
    //<editor-fold defaultstate="collapsed" desc="setClip">
    /**
     * Sets the drawing clip of the graphics context. Drawing does not occur
     * outside the clip
     * @param x - x-position of the clipping rectangle
     * @param y - y-position of the clipping rectangle
     * @param width - width of the clipping rectangle
     * @param height - height of the clipping rectangle
     */
    public void setClip(int x, int y, int width, int height);
//</editor-fold>  
 
}
