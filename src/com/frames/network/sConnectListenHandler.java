package com.frames.network;

import com.frames.resource.UserOnline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Francis on 4/26/2017.
 */
public class sConnectListenHandler {


    private static sConnectListenHandler firstInstance = null;
    static boolean firstThread = true;

    //Addressing information
    String address = "localhost";
    int port = 2222;

    //Socket information, writers, readers
    Socket socket;
    BufferedReader reader;
    public PrintWriter writer;

    public static sConnectListenHandler getInstance(){
        if(firstInstance == null){
            //This is here to test what happens if threads try
            //to create instances of this class
            if(firstThread){
                firstThread = false;
                try{
                    Thread.currentThread();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //Here we just use synchronized when the first object
            //is created
            synchronized (sConnectListenHandler.class){
                //if the first instance isnt needed it isnt created
                //This is known as lazy isntanciation
                if(firstInstance == null){
                    firstInstance = new sConnectListenHandler();
                }
            }
        }

        //Under either circumstance this returns the instance
        return  firstInstance;
    }


    private sConnectListenHandler(){
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
