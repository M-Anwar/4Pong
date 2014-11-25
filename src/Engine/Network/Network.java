/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

/**
 *
 * @author muhammed.anwar
 */
public class Network {
    public static final int TCPport = 54555;
    public static final int UDPport = 54777;
    
    public static void register(EndPoint endPoint)
    {
        Kryo kryo = endPoint.getKryo();
        kryo.register(ChatMessage.class);       
        kryo.register(RegisterName.class);
        kryo.register(RegisteredNames.class);
        kryo.register(String[].class);
        kryo.register(boolean[].class);
        kryo.register(ReadyState.class);
        kryo.register(BeginGame.class);
    }    
    public static class RegisterName {
        public String name;
    }
    public static class ChatMessage{
        public String text;
    }
    public static class RegisteredNames{
        public String[] names;
        public boolean[] ready;
    }
    public static class ReadyState{
        public boolean clientReady;
    }   
    public static class BeginGame{
        public boolean beginGame;
    }
    
}
