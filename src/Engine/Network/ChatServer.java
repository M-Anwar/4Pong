/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Network;

import Engine.Network.Network.ChatMessage;
import Engine.Network.Network.RegisterName;
import Engine.Network.Network.RegisteredNames;
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
    public static void main(String[] args) 
    {   
        try {
            System.out.println("Starting Server");
            server  = new Server(){ 
                protected Connection newConnection(){
                    return new ChatConnection();
                }                    
            };
            Network.register(server);
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
                        ChatMessage response = new ChatMessage();
                        response.text = name + " connected.";
                        System.out.println(response.text);
                        server.sendToAllExceptTCP(connection.getID(), response);
                        updateNames();
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
                        updateNames();
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
                }                
                server.close();
                server.stop();
            }            
        }.start();
    }    
    private static void updateNames()
    {
        Connection[] connections = server.getConnections();
        ArrayList names = new ArrayList(connections.length);
        for (int i = connections.length - 1; i >= 0; i--) {
                ChatConnection curConnection = (ChatConnection)connections[i];
                names.add(curConnection.name);
        }
        // Send the names to everyone.
        RegisteredNames updateNames = new RegisteredNames();
        updateNames.names = (String[])names.toArray(new String[names.size()]);
        server.sendToAllTCP(updateNames);
    }
}
class ChatConnection extends Connection {
    public String name;
}

