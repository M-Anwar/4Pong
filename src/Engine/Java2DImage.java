/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

import java.awt.image.BufferedImage;

/**
 * A concrete implementation of the image class which used the BufferedImage
 * @author muhammed.anwar
 */
public class Java2DImage implements Engine.Image
{
    public BufferedImage image;
    public Java2DImage(BufferedImage i){
        image = i;
    }
    @Override
    public int getWidth(){ return image.getWidth(); }

    @Override
    public int getHeight(){ return image.getHeight(); }
}
