/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.GUI;

import Engine.Vector2D;

/**
 *
 * @author muhammed.anwar
 */
public interface Scrollable {
    public Vector2D getPosition();
    public float getPrefferedWidth();
    public float getPrefferedHeight();
    public float getActualWidth();
    public float getActualHeight();
}
