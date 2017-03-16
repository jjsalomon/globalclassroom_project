package com.chess.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by azkei on 16/03/2017.
 */
public class Client extends Network {
    private String hostName;
    private int serverPort;

    public Client(final String host, final int port){
        super("CLIENT");
        hostName = host;
        serverPort = port;
    }

    @Override
    public void run() {
        try{
            connectToServer();
            getStreams();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            closeConnection();
        }
    }

    private void connectToServer() {
        try{
            socket = new Socket(InetAddress.getByName(hostName), serverPort);
            System.out.println("Successfully connected to: "+socket.getInetAddress().getHostName());
        }catch(IOException e){
            System.out.println("Failed to connect to: "+hostName);
        }
    }
}
