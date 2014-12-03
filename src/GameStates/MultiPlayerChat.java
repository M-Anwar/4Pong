/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import Engine.GUI.ButtonListener;
import Engine.GUI.GameButton;
import Engine.GUI.TextBox;
import Engine.GameState;
import Engine.GameStateManager;
import Engine.Graphics;
import Engine.Java2DImage;
import Engine.KeyListener;
import Engine.Keys;
import Engine.Network.Network;
import Engine.Network.Network.BeginGame;
import Engine.Network.Network.ChatMessage;
import Engine.Network.Network.ReadyState;
import Engine.Network.Network.RegisterName;
import Engine.Network.Network.RegisteredNames;
import Entity.ImageLoader;
import G4Pong.GamePanel;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Jason Xu and Muhammed Anwar
 */
public class MultiPlayerChat extends GameState{   
    private GameButton btnExit;   
    private GameButton btnMinimize;  
    private GameButton btnReady;
    private TextBox chatInput;
    private TextBox chatLog;    
    private boolean firstClick = true;
    
    public static String userName;
    public String [] users;
    public boolean []ready;
    Client client;
    
    public MultiPlayerChat(GameStateManager gsm) {
        super(gsm);           
    }
    
    @Override
    public void init(){        
        btnExit = new GameButton("X",GamePanel.WIDTH-60,10);
        addComponent(btnExit);
        btnExit.addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
                client.close();
                client.stop();
               setState(GameStateManager.MULTI_PLAYER_STATE);
            }                
        });
        btnMinimize = new GameButton("_",GamePanel.WIDTH-110,10);
        addComponent(btnMinimize);
        btnMinimize.addButtonListener(new ButtonListener(){          
            public void buttonClicked() {
                GamePanel.parent.setState(JFrame.ICONIFIED);
            }                
        });
        btnReady = new GameButton("Ready!",GamePanel.WIDTH-100,GamePanel.HEIGHT-50);
        btnReady.setFont("Arial", 16);
        addComponent(btnReady);
        btnReady.addButtonListener(new ButtonListener(){
            public void buttonClicked() {
                ReadyState r= new ReadyState();
                r.clientReady=true;
                client.sendTCP(r);
                btnReady.setEnabled(false);
            }
            
        });
        
        chatInput = new TextBox("Enter text here...",10,GamePanel.HEIGHT-300+90);
            chatInput.setFocus(false);
            chatInput.setMultiLine(false);
            chatInput.setWidth(GamePanel.WIDTH-300);
            chatInput.setFont("Arial", 12);
            chatInput.resizeHeight();
            
            chatInput.addKeyListener(new KeyListener(){
                @Override
                public void KeyTyped(int keyCode, char keyChar) {
                    if(keyCode == Keys.VK_ENTER){
                        ChatMessage request = new ChatMessage();
                        String toSend = chatInput.getText().trim();
                        if(toSend.length()==0)return;
                        request.text =toSend;
                        client.sendTCP(request);
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
            
            chatLog= new TextBox(10,85,GamePanel.WIDTH-300,GamePanel.HEIGHT-300);
            chatLog.setFont("Arial", 12);
            chatLog.setEditable(false);
            chatLog.setMultiLine(true);
            chatLog.setText("WELCOME TO 4 P O N G chat lobby:\n");
            
            addComponent(chatInput);
            addComponent(chatLog);
            
            //Network code
            users = new String[0];
            client = new Client();
            Network.register(client);

            new Thread(client).start();
            
            
            client.addListener(new Listener(){
                public void connected(Connection c)
                {
                    chatLog.appendText("Connected to Chat!\n");
                    RegisterName registerName = new RegisterName();
                    registerName.name = userName;
                    client.sendTCP(registerName);
                }
                public void received(Connection connection, Object object){
                    
                    if(object instanceof ChatMessage)
                    {
                        ChatMessage response = (ChatMessage)object;
                        chatLog.appendText(response.text+"\n");
                    }
                    if(object instanceof RegisteredNames)
                    {
                        RegisteredNames names = (RegisteredNames)object;
                        users = names.names;
                        ready = names.ready;                        
                    }
                    if(object instanceof BeginGame)
                    {
                        client.close();
                        client.stop();
                        setState(GameStateManager.MULTI_PLAYER_GAME_STATE);
                    }
                }
                public void disconnected(Connection c)
                {
                    setState(GameStateManager.MULTI_PLAYER_STATE);
                }
            });  
            
            new Thread(){
                public void run(){   
                    try{                                
                        chatLog.appendText("Connecting to server \n");          
                        client.connect(5000, "67.188.28.76", Network.TCPport,Network.UDPport);                        
                    }catch(IOException ex){chatLog.appendText(ex.getMessage());
                        chatLog.appendText("\nConnecting to LAN server \n");                              
                        try{
                            InetAddress addr  = client.discoverHost(Network.UDPport, 10000);
                            client.connect(5000, addr, Network.TCPport,Network.UDPport);
                        }catch(Exception e){chatLog.appendText(e.getMessage());setState(GameStateManager.MULTI_PLAYER_STATE);}
                    }              

                }                
            }.start();
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(77,77,77).getRGB());
        g.fillRect(0, 0, GamePanel.WIDTH, 80);
        g.setColor(Color.WHITE.getRGB());
        g.setFont("Courier New", Graphics.BOLD, 70);
        g.drawString("4PONG Lobby", GamePanel.WIDTH/2-g.getFontDimension("4PONG Lobby")[0]/2, 60);
       
        g.setColor(Color.WHITE.getRGB());
        g.setFont("Arial",Graphics.BOLD, 20);
        g.drawString("Connected Users:", 10+GamePanel.WIDTH-300+10, 105);
        g.setFont("Arial",Graphics.PLAIN, 12);
        for(int i =0; i <users.length; i ++)
        {
            if(users[i].equals(userName))g.setColor(Color.GREEN.getRGB());
            else g.setColor(Color.WHITE.getRGB());
            if(ready[i]==true){g.setColor(Color.CYAN.getRGB());}
            g.drawString(users[i], 10+GamePanel.WIDTH-300+10,130+20*i);
        }
        g.setColor(Color.WHITE.getRGB());
        g.setFont("Arial", Graphics.BOLD,14);
        g.drawString("Commands:", 10, GamePanel.HEIGHT-150);
        g.setFont("Arial", Graphics.BOLD,12);
        g.drawString("Whisper -> /whisper 'target' 'message' : (/whisper John Hi how are you) : Allows you to whisper to a particular person without "
                + "everyone else seeing",20,GamePanel.HEIGHT-135);
    }

    @Override
    public void handleInput() {
    
    }
}
