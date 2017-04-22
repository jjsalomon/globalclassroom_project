package com.frames.network;

import com.chess.engine.board.Board;
import com.chess.gui.Table;
import com.frames.gui.*;
import com.frames.resource.MoveBuffer;
import com.frames.gui.SingletonLogin;
import com.frames.resource.UserOnline;

import javax.swing.*;
import java.io.BufferedReader;

/**
 * Created by azkei on 02/04/2017.
 * This class class is a Thread handler for information
 * coming into the client.
 */


public class IncomingReader implements Runnable {

    BufferedReader breader;

    public IncomingReader(BufferedReader reader) {
        this.breader = reader;
    }


    @Override
    public void run() {
        UserOnline usersOnlineInstance = UserOnline.getInstance();
        MoveBuffer moveBuffer = MoveBuffer.getFirstInstance();
        SingletonLogin sglogin = SingletonLogin.getFirstInstance();
        SingletonAccount sgaccount;
        System.out.println("IncomingReader: Instance ID: " + System.identityHashCode(usersOnlineInstance));

        System.out.println("Thread running");
        String[] data;
        String stream,
                account = "Account",
                disconnect = "Disconnect", chat = "Message",
                login = "Login", add = "Add", sending = "Sending",
                remove = "Remove", invite = "Invite", start = "START",
                declined = "DECLINED"  , move = "Move" ,checklogin= "CheckLogin";

        try {
            while ((stream = breader.readLine()) != null) {
                data = stream.split(":");


                if (data[1].equals(chat)) {
                    System.out.println(stream);
                }

                //clear the existing buffer and add new data
                if (data[0].equals(sending)) {
                    usersOnlineInstance.clearBuffer();
                    System.out.println("Incoming Reader: Buffer Cleared: " + usersOnlineInstance.getOnlineBuff());
                }

                //if server is streaming user data
                if (data[0].equals(add)) {
                    String user = data[1];
                    usersOnlineInstance.addBuffer(user);
                    System.out.println("Incoming Reader: Added new Data: " + usersOnlineInstance.getOnlineBuff());
                }

                //if server is removing user data
                if (data[0].equals(remove)) {
                    String user = data[1];
                    usersOnlineInstance.removeBuffer(user);
                    System.out.println("Incoming Reader: Remove Data: " + usersOnlineInstance.getOnlineBuff());
                }

                if (data[1].equals(login)) {
                    sglogin.setVisible(false); //set login gui false/hide it
                    //Show new account activity
                    System.out.println(stream);
                    /*stores in the users data information from the server to the singleton usersOnlineInstance.setStream(stream);
                        to give easier access when using SingletonAccount in different java classes */
                    usersOnlineInstance.setStream(stream);

                    //access user info. in the usersOnlineInstance.getStream() and pass it to the SingletonAccount, then create the account gui
                    sgaccount = SingletonAccount.getFirstInstance(usersOnlineInstance.getStream());

                    sgaccount.setGuiWindow();       //set up the gui visible and size

                    //this comment are the gui class not singleton
                    /*Account accounts = new Account(stream);
                    //whenever x button then terminate
                    accounts.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    accounts.setSize(700, 500);
                    accounts.setVisible(true);*/
                }

                if (data[1].equals(checklogin)) {
                    //opens up a messageDialog if user is already log in.
                    JOptionPane.showMessageDialog(sglogin,
                             data[0] + " is already log in",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                if (data[0].equals(invite)) {
                    //PARAM: challenged, challenger
                    String challenged = data[1];
                    String challenger = data[2];
                    ShowInvitePane invitePane = new ShowInvitePane(challenged, challenger);
                    invitePane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    invitePane.pack();
                    invitePane.setVisible(true);
                    System.out.println("You have been invited by: " + data[1]);
                    System.out.println(stream);
                }

                if (data[0].equals(start)) {

                    String challenger = data[1];
                    String challenged = data[2];

                    moveBuffer.addPlayers(challenger,challenged);

                   //System.out.println("I am challenger!: "+ getChallenger());
                    //System.out.println("I am the challenged!: "+getChallenged());
                    System.out.println(stream);

                    //Create board here
                    Board board = Board.createStandardBoard();
                    System.out.println(board);
                    Table.get().show();

                }

                if (data[0].equals(declined)) {
                    System.out.println("User has declined");
                    System.out.println(stream);
                }

                if(data[0].equals(move)){
                    //data coming in into readable strings
                    //challenger
                    String fromClient = data[1];
                    //challenged
                    String toClient = data[2];
                    String sourceTile = data[3];
                    String destinationTile = data[4];

                    System.out.println("from Client"+fromClient);
                    System.out.println("to Client"+toClient);
                    System.out.println("source "+sourceTile);
                    System.out.println("Dest "+destinationTile);

                    moveBuffer.addIncomingMove(fromClient, toClient,sourceTile,destinationTile);
                }
            }
            }catch(Exception ex){
                ex.printStackTrace();
            }
    }

}
