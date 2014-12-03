/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameStates;

import Engine.GUI.ButtonListener;
import Engine.GUI.GameButton;
import Engine.GameState;
import Engine.GameStateManager;
import Engine.Geometry.CollisionResult;
import Engine.Graphics;
import Engine.Keys;
import Engine.Network.ChatServer;
import Engine.Network.GameNetwork;
import Engine.Network.GameNetwork.InputUpdate;
import Engine.Network.GameNetwork.PlayerNumber;
import Engine.Network.GameNetwork.PositionUpdate;
import Engine.Network.GameNetwork.ScoreUpdate;
import Engine.Network.Network;
import Engine.Vector2D;
import Entity.Ball;
import Entity.Paddle;
import Entity.PaddleAI;
import Entity.PlayerInputGenerator;
import G4Pong.GamePanel;
import static GameStates.MultiPlayerChat.userName;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author muhammed.anwar
 */
public class MultiPlayerGame extends GameState {
     private GameButton btnExit;
    private Paddle player;
    private Paddle player2;
    private Paddle player3;
    private Paddle player4;
    public ArrayList<Paddle> players;
    public Ball ball;    
    
    public Paddle lastHit;
    public int[]scores;
    
    public Client client;
     public String [] users;
     public int playerNum;
     
    private float rotation = 0;
    private boolean gameOver;
    private int winner;
    private float counter;
    public MultiPlayerGame(GameStateManager gsm) {
        super(gsm);       
    }

    @Override
    public void init() 
    {      
            client = new Client();
            GameNetwork.register(client);

            new Thread(client).start();            
            
            client.addListener(new Listener(){
                public void connected(Connection c)
                {                                 
                    System.out.println("Connected to GameServer");                    
                    Network.RegisterName registerName = new Network.RegisterName();
                    registerName.name = userName;
                    client.sendTCP(registerName);
                }
                public void received(Connection connection, Object object){
                    
                    if(object instanceof PlayerNumber)
                    {
                        PlayerNumber p = (PlayerNumber)object;
                        playerNum = p.playerNum;
                    }
                    if(object instanceof Network.ChatMessage)
                    {
                        Network.ChatMessage response = (Network.ChatMessage)object;                        
                    }
                    if(object instanceof Network.RegisteredNames)
                    {
                        Network.RegisteredNames names = (Network.RegisteredNames)object;
                        users = names.names;                                               
                    }                   
                    if(object instanceof PositionUpdate){
                        PositionUpdate p = (PositionUpdate)object;
                        if(p.ID.equals("BALL")){
                            
                            ball.setPosition(new Vector2D(p.x,p.y));
                        }
                        if(p.ID.equals("PLAYER0")){
                            players.get(0).setPosition(new Vector2D(p.x,p.y));
                        }
                        if(p.ID.equals("PLAYER1")){
                            players.get(1).setPosition(new Vector2D(p.x,p.y));
                        }
                        if(p.ID.equals("PLAYER2")){
                            players.get(2).setPosition(new Vector2D(p.x,p.y));
                        }
                        if(p.ID.equals("PLAYER3")){
                            players.get(3).setPosition(new Vector2D(p.x,p.y));
                        }
                    }
                    if(object instanceof ScoreUpdate)
                    {
                        ScoreUpdate scup = (ScoreUpdate)object;
                        scores = scup.scores;
                        for(int i =0; i <scores.length;i++)
                        {
                            if(scores[i]>ChatServer.maxScore){
                                gameOver=true;
                                winner = i;
                            }
                        }
                    }
                }
                public void disconnected(Connection c)
                {
                    setState(GameStateManager.MULTI_PLAYER_STATE);
                }
            });  
         try {               
              InetAddress addr  = client.discoverHost(GameNetwork.UDPport, 5000);
              System.out.println("Connecting: " + addr.getHostAddress());
              client.connect(5000, addr, GameNetwork.TCPport,GameNetwork.UDPport);
         } catch (IOException ex) {
         }
        player = new Paddle(Paddle.PaddlePosition.BOTTOM, new PlayerInputGenerator());
        player2 = new Paddle(Paddle.PaddlePosition.RIGHT, new PlayerInputGenerator());
        player3 = new Paddle(Paddle.PaddlePosition.TOP, new PlayerInputGenerator());
        player4= new Paddle(Paddle.PaddlePosition.LEFT, new PlayerInputGenerator());
        
        players = new ArrayList<>();
        players.add(player);
        players.add(player2);
        players.add(player3);
        players.add(player4);        
        
        ball = new Ball();        
        scores = new int[4];
        
        //Game over variable initialization
        counter=0;
        gameOver=false;
        
        btnExit = new GameButton("X",GamePanel.WIDTH-60,20);
        addComponent(btnExit);
        btnExit.addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
                client.close();
                client.stop();
               setState(GameStateManager.INTRO_STATE);
            }                
        });        
    }
   
    @Override
    public void draw(Graphics g) {             
        
        
   
        //Draw Score Card
        g.setFont("Arial", Graphics.BOLD, 25);
        g.setColor(Color.WHITE.getRGB());        
        g.drawString("Score:",GamePanel.GAMEWIDTH+20,70);        
        g.setFont("Arial",Graphics.PLAIN,15);
        if(users!=null){
            for(int i =0; i<users.length;i++)
            {
                if(i==0)g.setColor(new Color(146,208,80).getRGB());
                else if(i==1)g.setColor(Color.CYAN.getRGB());
                else if(i==2)g.setColor(Color.RED.getRGB());
                else if(i==3)g.setColor(Color.ORANGE.getRGB());
                g.drawString(users[i] +": "+scores[i],GamePanel.GAMEWIDTH+40,90+20*i);
                g.setColor(Color.WHITE.getRGB());
            }
        }        

        //Sets the game area
        g.drawRect(5, 5, GamePanel.GAMEWIDTH,GamePanel.GAMEHEIGHT);
        g.setClip(5, 5, GamePanel.GAMEWIDTH, GamePanel.GAMEHEIGHT);   
        g.translate(5, 5);      
        if(gameOver){
            g.setColor(Color.WHITE.getRGB());
            g.setFont("Arial", Graphics.BOLD,60);            
            g.drawString("GameOver!", GamePanel.GAMEWIDTH/2-g.getFontDimension("GameOver!")[0]/2, 100);
            g.drawString(users[winner]+" won the game!", GamePanel.GAMEWIDTH/2-g.getFontDimension(users[winner]+" won the game!")[0]/2, 200);
            g.drawString("", GamePanel.GAMEWIDTH-g.getFontDimension("GameOver!")[0], 100);            
            if(counter>=80){
                client.close();
                client.stop();
                setState(GameStateManager.MULTIPLAYER_CHAT_STATE);
            }
        }
        else{            
            g.rotate(Math.toRadians(90*playerNum), GamePanel.GAMEWIDTH/2+5, GamePanel.GAMEHEIGHT/2+5);        
            for(Paddle p: players) p.draw(g);
            ball.draw(g);       
            g.rotate(Math.toRadians(-90*playerNum), GamePanel.GAMEWIDTH/2+5, GamePanel.GAMEHEIGHT/2+5);
        }
        g.translate(-5, -5);
        g.setClip(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);
        
     
    }    

    @Override
    public void handleInput() {
        InputUpdate i = new InputUpdate();     
        i.input =-1;
        if(Keys.isDown(Keys.W)){i.input = Keys.W;client.sendTCP(i);}
        else if(Keys.isDown(Keys.S)){i.input = Keys.S;client.sendTCP(i);}
        else if(Keys.isDown(Keys.A)){i.input = Keys.A;client.sendTCP(i);}
        else if(Keys.isDown(Keys.D)){i.input = Keys.D;client.sendTCP(i);}
        else client.sendTCP(i);
    }

    @Override
    public void update(float delta) {
        handleInput();
        if(gameOver){counter+=delta;}
    }
}
