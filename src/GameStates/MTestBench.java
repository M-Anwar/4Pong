/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameStates;

import Engine.GameState;
import Engine.GameStateManager;
import Engine.Geometry.Circle;
import Engine.Geometry.CollisionResult;
import Engine.Geometry.Rectangle;
import Engine.Graphics;
import Engine.Keys;
import G4Pong.GamePanel;
import GUI.ButtonListener;
import GUI.GameButton;
import java.awt.Color;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MUHAMMEDS TEST BENCH... you can look but DO NOT TOUCH... EVER...
 * @author muhammed.anwar
 */
public class MTestBench extends GameState
{
    private GameButton btnClose;   
    Rectangle rect;
    Circle circle;
    Circle newCircle;
    Circle circle2;
    int state = 1;
    
    public MTestBench(GameStateManager gsm) {
        super(gsm);
        init();       
    }

    @Override
    public void init() {        
        btnClose = new GameButton("X", GamePanel.WIDTH - 50, 50);       
        addComponent(btnClose);      
        btnClose.addButtonListener(new ButtonListener(){//X button to go back to options
            @Override
            public void buttonClicked() {
                gsm.setState(GameStateManager.OPTION_STATE);
            }
        });        
        rect = new Rectangle(GamePanel.WIDTH/2-100, GamePanel.HEIGHT/2-200, 80,20);        
        circle = new Circle(GamePanel.WIDTH/2-50, GamePanel.HEIGHT/2,40);
        circle2 = new Circle(GamePanel.WIDTH/2+50, GamePanel.HEIGHT/2,40);
        newCircle = new Circle(GamePanel.WIDTH/2, GamePanel.HEIGHT/2,40);
    }
    
    public void update(float delta)
    {
        super.update(delta);
        handleInput();
    }
    public void draw(Graphics g){
        
        if (state ==1){
            //<editor-fold defaultstate="collapsed" desc="SAT Polygon vs. Circle Test">
            g.setColor(Color.WHITE.getRGB());
            g.setFont("Arial",Graphics.BOLD,25);
            g.drawString("Separating Axis Theorem: Circle vs. Polygon", 10, 40);
            g.setFont("Arial",Graphics.PLAIN,15);
            g.drawString("W S A D to move the rectangle - Arrow Keys (Up & Down) to rotate", 10, 60);
            
            newCircle.setPosition(circle.getPosition());
            rect.debugDraw(g);
            circle.debugDraw(g);
            circle2.debugDraw(g);
            CollisionResult s;
            if((s=rect.collides(circle))!=null){
                g.setColor(Color.RED.getRGB());
                newCircle.setPosition(newCircle.getPosition().add(s.mts));
                newCircle.debugDraw(g);
            }
            if((s=rect.collides(circle2))!=null){
                circle2.setPosition(circle2.getPosition().add(s.mts));
            }
//</editor-fold>
        }
        else if (state ==2)
        {
            
        }
        super.draw(g);
    }
    @Override
    public void handleInput() {
        if(Keys.isDown(Keys.W)) rect.getPosition().thisAdd(0,-1);
        if(Keys.isDown(Keys.S)) rect.getPosition().thisAdd(0,1);
        if(Keys.isDown(Keys.A)) rect.getPosition().thisAdd(-1,0);
        if(Keys.isDown(Keys.D)) rect.getPosition().thisAdd(1,0);
        
        if(Keys.isDown(Keys.UP)) rect.setRotation(rect.getRotation()+(float)Math.toRadians(1));
        if(Keys.isDown(Keys.DOWN)) rect.setRotation(rect.getRotation()-(float)Math.toRadians(1));
    }
}
