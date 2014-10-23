/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package G4Pong;

import Engine.Audio.Audio;
import Engine.Audio.JavaAudio;
import Engine.GameStateManager;
import Engine.Graphics;
import Engine.Java2DGraphics;
import Engine.Keys;
import Engine.Mouse;
import Entity.MenuBall;
import Engine.GUI.ButtonListener;
import Engine.GUI.Component;
import Engine.GUI.GameButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * Platform dependant game screen. Contains the main game loop and event handling.
 * @author muhammed.anwar
 */
public class GamePanel extends JPanel implements Runnable,MouseListener, MouseMotionListener, KeyListener
{
    //Width and Height of the Panel
    public static final int WIDTH =1024;
    public static final int HEIGHT = 720;
    
    //The dimensions of the actual pong game (defined here for convenience)
    public static final int GAMEWIDTH = HEIGHT-10;
    public static final int GAMEHEIGHT = HEIGHT-10;
    
    //Game loop speeds
    public static final int NORMAL =    100000000;
    public static final int SLOMO =     500000000;
    public static final int FAST =      10000000;
    
    //Game-loop variables
    private static int speed = NORMAL;
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private float actualFPS;
    private long targetTime = 1000/FPS;
    
    //Graphics Variables
    private BufferedImage image;    
    private Engine.Graphics g;  
    
    //The game state manager resposible for delegating drawing and updating
    GameStateManager gsm;
    
    //Audio Engine Handle
    private static Audio gameAudio;
    
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setFocusable(true);
        this.requestFocus();       
    }
    @Override
    public void addNotify() {
        super.addNotify();
        if(thread == null) {			
                addMouseListener(this);	
                addMouseMotionListener(this);
                addKeyListener(this);
                thread = new Thread(this);
                thread.start();
        }
    }
    
    private void init() {		
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();        
        g = new Java2DGraphics(g2d);
        running = true;	        
        gameAudio = new JavaAudio();
            try {
            //Load Audio here - Might be changed to a loader in the future
                gameAudio.load("/res/blip.wav", "BLIP");
                gameAudio.load("/res/forcebewith.wav","FORCE");
                gameAudio.load("/res/button2.wav","BUTTON");
                gameAudio.load("/res/bgmusic1.wav","MENU");
                gameAudio.setMute(true);
            } catch (Exception ex) {
                System.out.println("Unable to load audio: " + ex.getMessage());
            }
        
        gsm = new GameStateManager();
    }    
    
    @Override
    public void run() {
        init();
		
        long start;
        long elapsed=0;
        long wait;
        long delta=0;
        
        //Main game loop
        while(running) {
            start = System.nanoTime();
            
            update(delta);
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = targetTime - elapsed / 1000000;
            if(wait < 0) wait = 0;
            try {Thread.sleep(wait);}
            catch(Exception e) {e.printStackTrace();}
            
            delta = System.nanoTime()- start;
            actualFPS = (1000000000/(float)delta);            
        }        
    }
    public void update(long delta)
    {       
        float deltaf = delta;
        deltaf = deltaf/speed;       
        
        //Update code goes here   
        gsm.update(deltaf);
        Mouse.update();
        Keys.update();
    }
    
    public void draw()
    {
        g.setColor(Color.BLACK.getRGB());
        g.fillRect(0, 0, WIDTH, HEIGHT);  
        gsm.draw(g);        
        
        /*
        g.setColor(Color.BLACK.getRGB());
        g.fillRect(0, 0, 300, 40);
        g.setColor(Color.WHITE.getRGB());
        g.drawString("FPS: "+ actualFPS, 10, 20);*/
                
    }
    public void drawToScreen()
    {
        Graphics2D g2 = (Graphics2D)getGraphics();       
        g2.drawImage(image, 0, 0, null);               
        g2.dispose();       
    }
    
    /**
     * Set the game loop speed. Scales the value of the update delta parameter
     * @param playSpeed GamePanel.NORMAL, GamePanel.FAST or GamePanel.SLOMO
     */
    public static void setPlaySpeed(int playSpeed){speed = playSpeed;}
    
    /**
     * Returns the audio engine of the game
     * @return 
     */
    public static Audio getAudio(){return gameAudio;}
    
    //Mouse Events
    @Override
    public void mousePressed(MouseEvent me) {         
         Mouse.setAction(Mouse.PRESSED);         
    }
    @Override
    public void mouseReleased(MouseEvent me) {
        Mouse.setAction(Mouse.RELEASED);
    }   
    @Override
    public void mouseDragged(MouseEvent me) {
        Mouse.setAction(Mouse.PRESSED);
	Mouse.setPosition(me.getX(), me.getY());
    }
    @Override
    public void mouseMoved(MouseEvent me) {
        Mouse.setPosition(me.getX(), me.getY());           
    }
    @Override
    public void keyPressed(KeyEvent ke) {
        Keys.keySet(ke.getKeyCode(),true);
    }
    @Override
    public void keyReleased(KeyEvent ke) {
         Keys.keySet(ke.getKeyCode(),false);
         Keys.keyTyped(ke);
    }
    
    public void mouseEntered(MouseEvent me) {}    
    public void mouseExited(MouseEvent me) {}
    public void mouseClicked(MouseEvent me) {}
    public void keyTyped(KeyEvent ke){}
    

   
}
