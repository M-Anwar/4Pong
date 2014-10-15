/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Geometry;

import Engine.Vector2D;

/**
 * A basic class that hold collision information
 * @author muhammed.anwar
 */
public class CollisionResult {
    /**
     * Minimum Translation Vector
     */
    public Vector2D mts;
    /**
     * Point of Collision (NOT IMPLEMENTED YET)
     */
    public Vector2D poc;
    /**
     * Collision normal
     */
    public Vector2D normal;
}
