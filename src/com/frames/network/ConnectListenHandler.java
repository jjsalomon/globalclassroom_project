package com.frames.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by azkei on 02/04/2017.
 */
public class ConnectListenHandler {



    //Addressing information
    String address = "localhost";
    int port = 2222;

    //Socket information, writers, readers
    Socket socket;
    BufferedReader reader;
    public PrintWriter writer;

    public ConnectListenHandler(){
        try {
            socket = new Socket(address,port);
            writer = new PrintWriter(socket.getOutputStream());
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(streamReader);
            System.out.println("Connected to the Server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void ListenThread(){
        Thread IncomingReader = new Thread(new IncomingReader(reader));
        IncomingReader.start();
    }
}
