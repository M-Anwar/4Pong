package Engine.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jason.xu
 */
public class ClientSender extends Thread{
    private PrintWriter mOut;
 
    public ClientSender(PrintWriter aOut)
    {
            mOut = aOut;
    }

    public void run()
    {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (!isInterrupted()) {
                String message = in.readLine();
                mOut.println(message);
                mOut.flush();
            }
        } catch (IOException ioe) {
                    // Communication is broken
            }
    }
}
