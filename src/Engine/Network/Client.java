/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Jason Xu
 */
public class Client {
    public static final String SERVER_HOSTNAME = "localhost";
    public static final int SERVER_PORT = 5000;
 
    public void init()
    {
        BufferedReader in = null;
        PrintWriter out = null;
        Socket socket = null;
        try {
           // Connect to Nakov Chat Server
           socket = new Socket(SERVER_HOSTNAME, SERVER_PORT);
           in = new BufferedReader(
               new InputStreamReader(socket.getInputStream()));
           out = new PrintWriter(
               new OutputStreamWriter(socket.getOutputStream()));
           System.out.println("Connected to server " +
              SERVER_HOSTNAME + ":" + SERVER_PORT);
        } catch (IOException ioe) {
           System.err.println("Can not establish connection to " +
               SERVER_HOSTNAME + ":" + SERVER_PORT);
           ioe.printStackTrace();
           System.exit(-1);
        }
 
        // Create and start Sender thread
        ClientSender sender = new ClientSender(out);
        sender.setDaemon(true);
        sender.start();
 
        try {
           // Read messages from the server and print them
            String message;
           while ((message=in.readLine()) != null) {
               System.out.println(message);
               if(message.contains("host")){
                   System.out.println("Socket closing");
                   socket.close();
                   break;
               }
               else if(message.contains("client")){
                    System.out.println("Socket closing");
                   socket.close();
                   break;
               }
           }
           System.out.println("Done");
        } catch (IOException ioe) {
           System.err.println("Connection to server broken.");
           ioe.printStackTrace();
        }
 
    }
}
