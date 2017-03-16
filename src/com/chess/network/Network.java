package com.chess.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by azkei on 16/03/2017.
 */
public abstract class Network extends Thread {

    //ObjectStreams are good for sending class objects rather than primitive types
    protected ObjectOutputStream outputStream;
    protected ObjectInputStream inputStream;
    //Socket for connections
    protected Socket socket;
    protected Object receivedMessage;

    Network(final String name){
        super(name);
    }

    //thread run method
    public abstract void run();

    public void getStreams() throws IOException{
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.flush();
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void closeConnection(){
        try{
            if(outputStream != null){
                outputStream.close();
            }
            if(inputStream != null){
                inputStream.close();
            }
            if(socket != null){
                socket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void sendData(final Object obj_send){
        try{
            outputStream.writeObject(obj_send);
            outputStream.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }





}
