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
    public void drawString(String text,float x,float y);
    public void setFont(String font, String type, int size);
    public String getFont();
    public int[] getFontDimension(String t);
    
    //Basic drawing primitives    
    public void drawLine(float x1, float y1, float x2, float y2);
    public void drawRect(float x, float y, float width, float height);
    public void fillRect(float x, float y, float width, float height);
    public void drawOval(float x, float y, float width, float height);
    public void fillOval(float x, float y, float width, float height);
    
    //Drawing Image 
    public void drawImage(float x, float y,Image i);
    public void drawImage(float x, float y, Image i, int width, int height);
    
    //Transform functions
    public void rotate(double angle, double x, double y);
    public void translate(double x, double y);
    public void scale(double dx, double dy);
    
    //Getter and Setter for Color
    public void setColor(int Color);
    public int getColor();
    
    //Basic paint functions
    public void setThickness(int thickness);
    
    
    
    
 
}
