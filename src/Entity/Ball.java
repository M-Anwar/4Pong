/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Engine.Geometry.Circle;
import Engine.Graphics;
import Engine.Vector2D;
import G4Pong.GamePanel;

/**
 *
 * @author ali.allahverdi
 */
public class Ball extends GameObject
{
    public Ball()
    {
        this.bounds = new Circle(GamePanel.GAMEWIDTH/2, GamePanel.GAMEHEIGHT/2,40);
        this.setPosition(new Vector2D(GamePanel.GAMEWIDTH/2,GamePanel.GAMEHEIGHT/2));
    }
    @Override
    public void update(float delta) {
    }

    @Override
    public void draw(Graphics g) {
        
    }
    
}
