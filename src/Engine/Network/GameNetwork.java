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
public class GameNetwork {
    public static final int TCPport = 53555;
    public static final int UDPport = 53777;
    
    public static void register(EndPoint endPoint)
    {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Network.ChatMessage.class);       
        kryo.register(Network.RegisterName.class);
        kryo.register(Network.RegisteredNames.class);
        kryo.register(String[].class);
        kryo.register(boolean[].class);
        kryo.register(Network.ReadyState.class);
        kryo.register(Network.BeginGame.class);
        kryo.register(PositionUpdate.class);
        kryo.register(InputUpdate.class);
        kryo.register(PlayerNumber.class);
        kryo.register(ScoreUpdate.class);
        kryo.register(int[].class);
    }    
    public static class PositionUpdate
    {
        public String ID;
        public float x;
        public float y;
    }
    public static class InputUpdate
    {
        public String ID;
        public int input;
    }
    public static class PlayerNumber
    {
        public int playerNum;
    }
    public static class ScoreUpdate
    {
        public int scores[];
    }
    
}
