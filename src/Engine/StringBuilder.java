/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;

/**
 *
 * @author muhammed.anwar
 */
public class StringBuilder 
{
    public static float getWidth(String text, String font, int size)
    {
        AffineTransform at = new AffineTransform();
        Font f = new Font(font,Font.BOLD,size);
        FontRenderContext frc = new FontRenderContext(at, true, true);
        GlyphVector gv = f.createGlyphVector(frc, text);
        return gv.getPixelBounds(null, 0, 0).width;      
    }
    public static float getHeight(String text, String font, int size)
    {
        AffineTransform at = new AffineTransform();
        Font f = new Font(font,Font.BOLD,size);
        FontRenderContext frc = new FontRenderContext(at, true, true);
        GlyphVector gv = f.createGlyphVector(frc, text);
        return gv.getPixelBounds(null, 0, 0).height;      
    }
}
