package com.chess.network;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by azkei on 16/03/2017.
 */
public class Server extends Network{

    private ServerSocket serverSocket;
    private final int listenPort;

    public Server(final int listen_port) {
        super("SERVER");
        listenPort = listen_port;
    }

    @Override
    public void run() {

        try{
            serverSocket = new ServerSocket(listenPort,1);

            try{
                listenForConnection();
                getStreams();
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void listenForConnection() throws  IOException{
        socket = serverSocket.accept();
        System.out.println("Connection received from: " +socket.getInetAddress().getHostName());
    }


}
