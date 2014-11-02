/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameStates.TestBench;

import Engine.Camera;
import Engine.GUI.ButtonListener;
import Engine.GUI.GameButton;
import Engine.GUI.TextBox;
import Engine.GameState;
import Engine.GameStateManager;
import Engine.Geometry.Circle;
import Engine.Geometry.CollisionResult;
import Engine.Geometry.Rectangle;
import Engine.Graphics;
import Engine.KeyListener;
import Engine.Keys;
import Engine.Vector2D;
import Entity.ImageLoader;
import G4Pong.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
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
    
    private TextBox chatInput;
    private TextBox chatLog;    
    private boolean firstClick = true;
          
    Camera cam;
    int state;
    
    public MTestBench(GameStateManager gsm) {
        super(gsm);               
    }

    @Override
    public void init() {    
        state = 1;
        
        btnClose = new GameButton("X", GamePanel.WIDTH - 50, 20);       
        addComponent(btnClose);      
        btnClose.addButtonListener(new ButtonListener(){//X button to go back to options
            @Override
            public void buttonClicked() {                
                setState(GameStateManager.OPTION_STATE);
            }
        });   
        
        cam = new Camera(new Vector2D(GamePanel.WIDTH/2,GamePanel.HEIGHT/2),new Rectangle(0,0,GamePanel.WIDTH,GamePanel.HEIGHT));
        rect = new Rectangle(GamePanel.WIDTH/2-100, GamePanel.HEIGHT/2-200, 80,20);        
        circle = new Circle(GamePanel.WIDTH/2-50, GamePanel.HEIGHT/2,40);
        circle2 = new Circle(GamePanel.WIDTH/2+50, GamePanel.HEIGHT/2,40);
        newCircle = new Circle(GamePanel.WIDTH/2, GamePanel.HEIGHT/2,40);
        
        if(state ==2){
            //<editor-fold defaultstate="collapsed" desc="State 2">
            chatInput = new TextBox("Enter text here...",10,390);
            chatInput.setFocus(true);
            chatInput.setMultiLine(false);
            chatInput.setWidth(400);
            chatInput.setFont("Arial", 12);
            chatInput.resizeHeight();
            
            chatInput.addKeyListener(new KeyListener(){
                @Override
                public void KeyTyped(int keyCode, char keyChar) {
                    if(keyCode == Keys.VK_ENTER){
                        chatLog.appendText(chatInput.getText());
                        chatInput.setText("");
                    }
                }
            });
            chatInput.addMouseListener(new ButtonListener(){
                @Override
                public void buttonClicked() {
                    if(firstClick){
                        firstClick = false;
                        chatInput.setText("");
                    }
                }
            });
            
            chatLog= new TextBox(10,85,400,300);
            chatLog.setFont("Arial", 12);
            chatLog.setEditable(false);
            chatLog.setMultiLine(true);
            chatLog.setText("WELCOME TO 4 P O N G chat lobby:\n");
            
            addComponent(chatInput);
            addComponent(chatLog);
//</editor-fold>            
        }       
      
        
    }
    
    @Override
    public void update(float delta)
    {
        cam.update(delta);
        cam.moveTo(rect.getPosition());
        handleInput();
    }
    @Override
    public void draw(Graphics g){
        
        if (state ==1){
            //<editor-fold defaultstate="collapsed" desc="SAT Polygon vs. Circle Test">
            g.setColor(new Color(77,77,77).getRGB());
            g.fillRect(0, 0, GamePanel.WIDTH, 80);
            g.setColor(Color.WHITE.getRGB());
            g.setFont("Arial",Graphics.BOLD,25);
            g.drawString("Separating Axis Theorem: Circle vs. Polygon", 10, 40);
            g.setFont("Arial",Graphics.PLAIN,15);
            g.drawString("W S A D to move the rectangle - Arrow Keys (Up & Down) to rotate", 10, 60);
            g.drawString("Camera: " + cam.getPosition() + " Rect: " + rect.getPosition(), 10,100);
            g.fillOval(cam.getPosition().x-2, cam.getPosition().y-2, 4, 4);
            
            cam.applyCamera(g);
            g.drawRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);            
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
            cam.unApplyCamera(g);
            
            //</editor-fold>
        }
        else if (state ==2)
        {
            //<editor-fold defaultstate="collapsed" desc="Text Box testing">
            g.drawImage(0, 0, ImageLoader.BACKGROUND, GamePanel.WIDTH,GamePanel.HEIGHT);
            g.setColor(new Color(77,77,77).getRGB());
            g.fillRect(0, 0, GamePanel.WIDTH, 80);
            g.setColor(Color.WHITE.getRGB());
            g.setFont("Arial",Graphics.BOLD,25);
            g.drawString("Text Box Test bench", 10, 40);
            g.setFont("Arial",Graphics.PLAIN,15);
            g.drawString("Basic Chat infrastructure - has two text boxes which communicate with each other", 10, 60);
//</editor-fold>
        }        
        
                        
    }
    @Override
    public void handleInput() {
        if(Keys.isDown(Keys.W)) rect.getPosition().thisAdd(0,-1);
        if(Keys.isDown(Keys.S)) rect.getPosition().thisAdd(0,1);
        if(Keys.isDown(Keys.A)) rect.getPosition().thisAdd(-1,0);
        if(Keys.isDown(Keys.D)) rect.getPosition().thisAdd(1,0);
        
        //if(Keys.isDown(Keys.UP)) rect.setRotation(rect.getRotation()+(float)Math.toRadians(1));
        //if(Keys.isDown(Keys.DOWN)) rect.setRotation(rect.getRotation()-(float)Math.toRadians(1));
        if(Keys.isDown(Keys.UP)) cam.moveCamera(new Vector2D(0,-5));
        if(Keys.isDown(Keys.DOWN)) cam.moveCamera(new Vector2D(0,5));
        if(Keys.isDown(Keys.LEFT)) cam.moveCamera(new Vector2D(-5,0));
        if(Keys.isDown(Keys.RIGHT)) cam.moveCamera(new Vector2D(5,0));
        
    }   
}
