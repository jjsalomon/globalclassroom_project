package com.frames.network;

import com.frames.gui.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Created by azkei on 02/04/2017.
 * This class class is a Thread handler for information
 * coming into the client.
 */
public class IncomingReader implements Runnable
{
    BufferedReader breader;
    ArrayList<String>tempList = new ArrayList<>();
    ArrayList<String>onlineUsers;

    public IncomingReader(BufferedReader reader) {
        this.breader = reader;
    }

    @Override
    public void run()
    {

        System.out.println("Thread running");
        String[] data;
        String stream,
                account = "Account",
                disconnect = "Disconnect", chat = "Message",
                login = "Login", add="Add",done="Done";
        try {
            while ((stream = breader.readLine()) != null) {
                data = stream.split(":");

                if(data[1].equals(chat)){
                    System.out.println(stream);
                }

                //if server is sending users data
                if(data[0].equals(add)){
                    String user = data[1];
                    tempList.add(user);
                }

                //if server is finished sending users data
                if(data[0].equals(done)){
                    onlineUsers = (ArrayList<String>)tempList.clone();
                    tempList.clear();
                }

                if(data[1].equals(login)){
                    System.out.println(stream);
                    //create GUI and pass account information.
                    Account accounts =  new Account(stream,onlineUsers);
                    //whenever x button then terminate
                    accounts.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    accounts.setSize(700,500);
                    accounts.setVisible(true);
                    onlineUsers.clear();
                }
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}