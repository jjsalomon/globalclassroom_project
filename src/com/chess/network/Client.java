package com.chess.network;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by azkei on 16/03/2017.
 */
public class Client extends Thread implements Runnable {
    //Objects for data to send
    private ObjectOutputStream dos;
    private ObjectInputStream dis;
    //Socket for connections
    private Socket socket;
    private Scanner in = new Scanner(System.in);
    private String hostName;
    private int serverPort;
    private Thread thread;
    private String username;

    private boolean connection = false;

    public Client(final String host, final int port){
        super("CLIENT");
        hostName = host;
        serverPort = port;
        //connect to server when main runs
        connectToServer();
        thread = new Thread(this,"Client Thread");
        thread.start();

    }

    public void run() {
        System.out.println("CLIENT: Thread communication working");
        connectToServer();
    }

    private void connectToServer() {
        try{
            socket = new Socket(InetAddress.getByName(hostName), serverPort);
            System.out.println("CLIENT: Successfully connected to: "+socket.getInetAddress().getHostName()+":"+serverPort);
            dos = new ObjectOutputStream(socket.getOutputStream());
            dos.flush();
            dis = new ObjectInputStream(socket.getInputStream());
            sendData("Thanks for letting me connect");

            connection = true;
            if(connection == true){
                try {
                    Object message = dis.readObject();
                    System.out.println(message);
                    connection = false;
                }catch(IOException e){
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }catch(IOException e){
            System.out.println("CLIENT: Failed to connect to: "+hostName);
        }
    }

    private void sendData(final Object obj_send){
        try{
            dos.writeObject(obj_send);
            dos.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try{
            if(dos != null){
                dos.close();
            }
            if(dis != null){
                dis.close();
            }
            if(socket != null){
                socket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}