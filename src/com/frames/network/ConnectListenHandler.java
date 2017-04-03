package com.frames.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by azkei on 02/04/2017.
 */
public class ConnectListenHandler extends NetworkHandler {

    public PrintWriter writer;

    public ConnectListenHandler(){
        connectToServer();
    }

    private void connectToServer() {
        try {
            socket = new Socket(address,port);
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("Connected to Server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ListenThread(){
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }
}
