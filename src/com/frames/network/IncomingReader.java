package com.frames.network;

import com.frames.gui.*;
import com.frames.resource.UserOnline;
import com.sun.org.glassfish.gmbal.ParameterNames;

import javax.swing.*;
import java.io.BufferedReader;

/**
 * Created by azkei on 02/04/2017.
 * This class class is a Thread handler for information
 * coming into the client.
 */
public class IncomingReader implements Runnable
{
    BufferedReader breader;

    public IncomingReader(BufferedReader reader) {
        this.breader = reader;
    }


    @Override
    public void run()
    {
        UserOnline usersOnlineInstance = UserOnline.getInstance();
        System.out.println("IncomingReader: Instance ID" + System.identityHashCode(usersOnlineInstance));

        System.out.println("Thread running");
        String[] data;
        String stream,
                account = "Account",
                disconnect = "Disconnect", chat = "Message",
                login = "Login", add="Add", sending = "Sending",
                remove="Remove", invite="Invite", start = "START",
                declined="DECLINED";
        try {
            while ((stream = breader.readLine()) != null) {

                data = stream.split(":");


                if(data[1].equals(chat)){
                    System.out.println(stream);
                }

                //clear the existing buffer and add new data
                if(data[0].equals(sending)){
                    usersOnlineInstance.clearBuffer();
                    System.out.println("Incoming Reader: Buffer Cleared: "+usersOnlineInstance.getOnlineBuff());
                }

                //if server is streaming user data
                if(data[0].equals(add)){
                    String user = data[1];
                    usersOnlineInstance.addBuffer(user);
                    System.out.println("Incoming Reader: Added new Data: "+usersOnlineInstance.getOnlineBuff());
                }

                //if server is removing user data
                if(data[0].equals(remove)){
                    String user = data[1];
                    usersOnlineInstance.removeBuffer(user);
                    System.out.println("Incoming Reader: Remove Data: "+usersOnlineInstance.getOnlineBuff());
                }

                if(data[1].equals(login)){

                    //Show new account activity
                    System.out.println(stream);
                    //create GUI and pass account information.
                    Account accounts =  new Account(stream);
                    //whenever x button then terminate
                    accounts.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    accounts.setSize(700,500);
                    accounts.setVisible(true);
                }

                if(data[0].equals(invite)){
                    //PARAM: challenged, challenger
                    String challenged = data[1];
                    String challenger = data[2];
                    ShowInvitePane invitePane = new ShowInvitePane(challenged,challenger);
                    invitePane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    invitePane.setSize(300,250);
                    invitePane.setVisible(true);


                    System.out.println("You have been invited by: "+data[1]);
                    System.out.println(stream);
                }

                if(data[0].equals(start)){
                    //Create board here
                    System.out.println(stream);
                }

                if(data[0].equals(declined)){
                    System.out.println("User has declined");
                    System.out.println(stream);
                }

            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}