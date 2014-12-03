/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Network;

import Engine.Geometry.CollisionResult;
import Engine.Network.GameNetwork.InputUpdate;
import Engine.Network.GameNetwork.PlayerNumber;
import Engine.Network.GameNetwork.PositionUpdate;
import Engine.Network.GameNetwork.ScoreUpdate;
import Engine.Network.Network.BeginGame;
import Engine.Network.Network.ChatMessage;
import Engine.Network.Network.ReadyState;
import Engine.Network.Network.RegisterName;
import Engine.Network.Network.RegisteredNames;
import Engine.Vector2D;
import Entity.Ball;
import Entity.ClientInputGenerator;
import Entity.Paddle;
import Entity.PaddleAI;
import Entity.PlayerInputGenerator;
import G4Pong.GamePanel;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author muhammed.anwar
 */
public class ChatServer {
    public static Server server;
    public static Server gameServer;
    public final static int players =4;
    public final static int maxScore=15;
    public final static boolean debug = true;
    public static Runnable gameThread;
    public static int playerCount=0;
    public static void main(String[] args) 
    {   
        try {
            System.out.println("Starting Server [type 'help' for list of commands]");
            server  = new Server(){ 
                protected Connection newConnection(){
                    return new ChatConnection();
                }                    
            };
            Network.register(server);
           
            gameServer = new Server(){
                protected Connection newConnection(){ 
                    return new GameConnection();
                }
            };
            GameNetwork.register(gameServer);
            gameServer.bind(GameNetwork.TCPport,GameNetwork.UDPport);
            gameServer.addListener(new Listener(){
                 public void received(Connection c, Object object){
                    GameConnection connection = (GameConnection)c;
                    if(object instanceof RegisterName)
                    {
                        if(connection.name!=null)return;
                        String name = ((RegisterName)object).name;
                        if(name==null)return;
                        name = name.trim();
                        connection.name = name;
                        connection.ready = false;
                        connection.playerNum = playerCount;
                        playerCount++;
                        PlayerNumber player = new PlayerNumber();
                        player.playerNum= connection.playerNum;
                        gameServer.sendToTCP(c.getID(), player);
                        ChatMessage response = new ChatMessage();
                        response.text = name + " connected.";
                        System.out.println(gameServer.toString()+ ": " + response.text);
                        gameServer.sendToAllExceptTCP(connection.getID(), response);
                        updateNames(gameServer);
                        return;                        
                    }
                 }
                public void connected(Connection connection)
                {
                    System.out.println(gameServer.toString()+": Connection Received: " + connection.getRemoteAddressTCP().getAddress());
                    if(gameServer.getConnections().length>players){System.out.println("Closing Connection");connection.close(); }
                }
                public void disconnected(Connection c)
                {
                    ChatConnection connection = (ChatConnection)c;
                    if(connection.name!=null)
                    {                       
                        System.out.println(gameServer.toString() + ": " +connection.name +" disconnected");
                        playerCount--;
                        return;
                    }
                } 
            });
            gameServer.start();
            
            gameThread =new GameThread(gameServer);
            new Thread(gameThread).start();
            
            
            server.start();            
            server.bind(Network.TCPport,Network.UDPport);
            server.addListener(new Listener(){
                public void received(Connection c, Object object){
                    ChatConnection connection = (ChatConnection)c;
                    if(object instanceof RegisterName)
                    {
                        if(connection.name!=null)return;
                        String name = ((RegisterName)object).name;
                        if(name==null)return;
                        name = name.trim();
                        connection.name = name;
                        connection.ready = false;
                        ChatMessage response = new ChatMessage();
                        response.text = name + " connected.";
                        System.out.println(response.text);
                        server.sendToAllExceptTCP(connection.getID(), response);
                        updateNames(server);
                        return;                        
                    }
                    if(object instanceof ChatMessage){
                        if(connection.name==null)return;
                        ChatMessage request = (ChatMessage)object;
                        ChatMessage response = new ChatMessage();          
                        String [] components = request.text.split(" ");
                        if(components.length > 2){
                            if(components[0].equals("/whisper")){
                                for(Connection con: server.getConnections())
                                {
                                    ChatConnection ccon = (ChatConnection)con;
                                    if(ccon.name.equals(components[1])){
                                        String newText="";
                                        for(int i =2; i<components.length;i++){newText+= components[i]+ " " ;}
                                        request.text = connection.name + ": WHISPERS-> "+ ccon.name + ": " +newText;
                                        server.sendToTCP(ccon.getID(), request);
                                        server.sendToTCP(connection.getID(),request);
                                        return;
                                    }
                                }
                            }
                        }
                        response.text = connection.name + ": " +request.text; 
                        System.out.println(response.text);
                        server.sendToAllTCP(response);  
                        return;
                    }
                    if(object instanceof ReadyState)
                    {
                        if(connection.name==null)return;
                        ReadyState state = (ReadyState)object;
                        connection.ready=state.clientReady;
                        updateNames(server);
                        Connection[] connections = server.getConnections();
                        ArrayList<Connection> readyCon = new ArrayList<>();                        
                        for(int i =0; i <connections.length;i++){
                            ChatConnection ccon = (ChatConnection)connections[i];
                            if(ccon.ready==true){                               
                                readyCon.add(ccon);
                            }
                        }   
                        if(readyCon.size()>=players){
                            System.out.println(players + " People are ready and match made: " + readyCon.toString());
                            BeginGame b = new BeginGame();
                            b.beginGame=true;
                            
                            for(Connection d: readyCon){
                                server.sendToTCP(d.getID(), b);
                            }
                        }
                    }
                }  
                public void connected(Connection connection)
                {
                    System.out.println("Connection Received: " + connection.getRemoteAddressTCP().getAddress());
                }
                public void disconnected(Connection c)
                {
                    ChatConnection connection = (ChatConnection)c;
                    if(connection.name!=null)
                    {                       
                        ChatMessage response = new ChatMessage();
                        response.text = connection.name + " disconnected.";
                        System.out.println(response.text);
                        server.sendToAllTCP(response);
                        updateNames(server);
                        return;
                    }
                }               
            });
            
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        new Thread(){
            public void run()
            {
                Scanner scan = new Scanner(System.in);
                String input;
                while(!(input =scan.nextLine().trim()).equals("exit")){
                    if(input.equals("connections")){
                        Connection[] connections = server.getConnections(); 
                        System.out.printf("Connections [%s]:\n", connections.length);
                        for (int i = connections.length - 1; i >= 0; i--) {
                                ChatConnection connection = (ChatConnection)connections[i];
                                System.out.println(connection.name + " : " + connection.getRemoteAddressTCP());                                
                        }  
                        connections = gameServer.getConnections(); 
                        System.out.printf(gameServer.toString()+" : Connections [%s]:\n", connections.length);
                        for (int i = connections.length - 1; i >= 0; i--) {
                                ChatConnection connection = (ChatConnection)connections[i];
                                System.out.println(connection.name + " : " + connection.getRemoteAddressTCP());                                
                        }  
                    }
                    else if(input.contains("discong"))
                    {
                        String[]args = input.split(" ");
                        if(args.length==2){
                            Connection[] connections = gameServer.getConnections();                         
                            for (int i = connections.length - 1; i >= 0; i--) {
                                ChatConnection connection = (ChatConnection)connections[i];
                                if(connection.name.equals(args[1])){
                                    System.out.println("Disconnecting: " + connection.name + " : " + connection.getRemoteAddressTCP());  
                                    connection.close();    
                                }                                                  
                            }
                        }
                    } 
                    else if(input.equals("disconallg")){
                        Connection[] connections = gameServer.getConnections(); 
                        System.out.printf("Disconnecting [%s]:\n", connections.length);
                        for (int i = connections.length - 1; i >= 0; i--) {
                                ChatConnection connection = (ChatConnection)connections[i];
                                System.out.println(connection.name + " : " + connection.getRemoteAddressTCP());  
                                connection.close();                                
                        }                       
                    }
                    else if(input.equals("disconall")){
                        Connection[] connections = server.getConnections(); 
                        System.out.printf("Disconnecting [%s]:\n", connections.length);
                        for (int i = connections.length - 1; i >= 0; i--) {
                                ChatConnection connection = (ChatConnection)connections[i];
                                System.out.println(connection.name + " : " + connection.getRemoteAddressTCP());  
                                connection.close();                                
                        }                       
                    }
                    else if(input.contains("discon"))
                    {
                        String[]args = input.split(" ");
                        if(args.length==2){
                            Connection[] connections = server.getConnections();                         
                            for (int i = connections.length - 1; i >= 0; i--) {
                                ChatConnection connection = (ChatConnection)connections[i];
                                if(connection.name.equals(args[1])){
                                    System.out.println("Disconnecting: " + connection.name + " : " + connection.getRemoteAddressTCP());  
                                    connection.close();    
                                }                                                  
                            }
                        }
                    }  
                    else if (input.contains("help"))
                    {
                        System.out.println("Commands possible: " );
                        System.out.println("\texit: stops the server and game server and exits the application");
                        System.out.println("\tconnections: shows a list of all the current connections to the chat and game server");
                        System.out.println("\tdisconall: disconnect all clients from the chat server");
                        System.out.println("\tdiscon <name>: disconnect the specified person from the chat server (argument passed with a space)");
                        System.out.println("\tdisconallg: disconnect all clients from the game server");
                        System.out.println("\tdiscong <name>: disconnect the specified person from the game server (argument passed with a space)");
                        System.out.println("\t\tHelpful Tip: Always try to stop the server using the 'exit' command");
                    }
                    
                    
                }                
                server.close();
                server.stop();
                gameServer.close();
                gameServer.stop();
                GameThread g = (GameThread)gameThread;
                g.isRunning=false;
                
            }            
        }.start();
    }    
    private static void updateNames(Server s)
    {
        Connection[] connections = s.getConnections();
        ArrayList names = new ArrayList(connections.length);
        boolean [] ready = new boolean[connections.length];
        for (int i = connections.length - 1; i >= 0; i--) {
                ChatConnection curConnection = (ChatConnection)connections[i];
                names.add(curConnection.name);
                ready[Math.abs(connections.length-1-i)]=curConnection.ready;
        }
        // Send the names to everyone.
        RegisteredNames updateNames = new RegisteredNames();
        updateNames.names = (String[])names.toArray(new String[names.size()]);
        updateNames.ready = ready; 
        s.sendToAllTCP(updateNames);
    }
}
class ChatConnection extends Connection
{
    public String name;
    public boolean ready;
}

class GameConnection extends ChatConnection
{
    public int playerNum;
}

class GameThread implements Runnable
{
    Server server;
    public Ball ball;
    public ArrayList<Paddle> players;
    public ArrayList<ClientInputGenerator> inputs;
    public boolean isRunning = true;
    public Paddle lastHit;
    public int scores[];
    public boolean gameOver;
    public GameThread(Server server)
    {
        this.server = server;
                
        ball = new Ball();
        inputs = new ArrayList<>();
        inputs.add(new ClientInputGenerator(Paddle.PaddlePosition.BOTTOM));
        inputs.add(new ClientInputGenerator(Paddle.PaddlePosition.RIGHT));
        inputs.add(new ClientInputGenerator(Paddle.PaddlePosition.TOP));
        inputs.add(new ClientInputGenerator(Paddle.PaddlePosition.LEFT));
        
        players = new ArrayList<>();
        players.add(new Paddle(Paddle.PaddlePosition.BOTTOM, inputs.get(0)));
        players.add(new Paddle(Paddle.PaddlePosition.RIGHT, inputs.get(1)));
        players.add(new Paddle(Paddle.PaddlePosition.TOP, inputs.get(2)));
        players.add(new Paddle(Paddle.PaddlePosition.LEFT, inputs.get(3)));
        for(Paddle p:players)p.update(0);
        
        scores = new int[4];
        server.addListener(new Listener(){
            public void received(Connection c, Object object){
                GameConnection connection = (GameConnection)c;
                if(object instanceof InputUpdate)
                {
                    InputUpdate i = (InputUpdate)object;
                    inputs.get(connection.playerNum).setInput(i.input);
                    return;                        
                }
            }        
        });
        gameOver= false;
    }
    @Override
    public void run() {
        while(server.getConnections().length<ChatServer.players && isRunning){try {Thread.sleep(100);} catch (InterruptedException ex) {}   }
        while(isRunning){
            if(server.getConnections().length==ChatServer.players){
                if(gameOver==false){
                    PositionUpdate pos = new PositionUpdate();

                    //Game Logic run on server
                    ball.update(0.1547197f);
                    for(Paddle p: players)p.update(0.15f);
                    if(ball.hitWall==true){
                        ball.hitWall=false;
                        ball.setPosition(new Vector2D(GamePanel.GAMEWIDTH/2, GamePanel.GAMEHEIGHT/2));                   
                        ball.setVelocity(new Vector2D((float)Math.random()*(15-10)+10,(float)Math.toRadians(Math.random()*360), 1));
                        if(lastHit == players.get(0) && ball.wallHit!=1)scores[0]++;
                        if(lastHit == players.get(1) && ball.wallHit!=2)scores[1]++;
                        if(lastHit == players.get(2) && ball.wallHit!=3)scores[2]++;
                        if(lastHit == players.get(3) && ball.wallHit!=4)scores[3]++;
                        lastHit =null;
                        for(int i =0;i <scores.length;i++)
                        {
                            if(scores[i]>ChatServer.maxScore){
                                gameOver=true;
                            }
                        }
                        ScoreUpdate scup = new ScoreUpdate();
                        scup.scores = this.scores;
                        server.sendToAllTCP(scup);
                    }
                     CollisionResult s;        
                    for(Paddle p: players){                    
                        if((s=p.getShape().collides(ball.getShape()))!=null)
                        {              
                            lastHit = p;
                            ball.getPosition().thisAdd(s.mts);                                 
                            ball.getVelocity().thisBounceNormal(s.normal);
                            ball.getVelocity().thisAdd(p.getVelocity());
                            Vector2D norm = s.normal.getPerpendicular();
                            float rel1 = ball.getVelocity().dot(norm);
                            float rel2 = p.getVelocity().dot(norm);
                            float dir = ball.getVelocity().dot(p.getVelocity());
                            if(dir!=0)
                                dir = dir/Math.abs(dir);
                            float amount = rel1-rel2;
                            ball.setAngularVelocity(Math.abs(amount)*dir*4);

                        }                    
                    }
                    //Server side code for client side rendering
                    pos.ID = "BALL";     
                    pos.x = ball.getPosition().x;
                    pos.y = ball.getPosition().y;       
                    server.sendToAllTCP(pos);
                    int i =0;
                    for(Paddle p: players){
                        pos.ID = "PLAYER"+i; i++;                    
                        pos.x = p.getPosition().x;
                        pos.y = p.getPosition().y;
                        server.sendToAllTCP(pos);                            
                    }
                }
            }
            else{
                try {Thread.sleep(5000);} catch (InterruptedException ex) {}            
                ball.setPosition(new Vector2D(GamePanel.GAMEWIDTH/2, GamePanel.GAMEHEIGHT/2));
                ball.setVelocity(new Vector2D((float)Math.random()*20,(float)Math.random()*20));
                gameOver= false;
                scores = new int[4];
            }
            try {Thread.sleep(10);} catch (InterruptedException ex) {}            
        }
    }    
}
