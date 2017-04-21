package com.frames.network;

import com.ChessMaster;
import com.chess.engine.board.Board;
import com.chess.gui.Table;
import com.frames.gui.*;
import com.frames.resource.UserOnline;
import com.sun.org.glassfish.gmbal.ParameterNames;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Created by azkei on 02/04/2017.
 * This class class is a Thread handler for information
 * coming into the client.
 */


public class IncomingReader implements Runnable {
    private static String Challenger;
    private static String Challenged;






    public IncomingReader() {


       // this.Challenger = Challenger;
        //this.Challenged = Challenged ;
    }

    public static String getChallenger() {
        return Challenger;
    }

    public static void setChallenger(String challenger) {
        Challenger = challenger;
    }

    public static String getChallenged() {
        return Challenged;
    }

    public static void setChallenged(String challenged) {
        Challenged = challenged;
    }



    BufferedReader breader;




    public IncomingReader(BufferedReader reader) {
        this.breader = reader;
    }


    @Override
    public void run() {
        UserOnline usersOnlineInstance = UserOnline.getInstance();
        System.out.println("IncomingReader: Instance ID" + System.identityHashCode(usersOnlineInstance));

        System.out.println("Thread running");
        String[] data;
        String stream,
                account = "Account",
                disconnect = "Disconnect", chat = "Message",
                login = "Login", add = "Add", sending = "Sending",
                remove = "Remove", invite = "Invite", start = "START",
                declined = "DECLINED"  , move = "Move" /*,islogin= "islogin"*/;

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

                    //Show new account activity
                    System.out.println(stream);
                    //create GUI and pass account information.
                    Account accounts = new Account(stream);
                    //whenever x button then terminate
                    accounts.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    accounts.setSize(700, 500);
                    accounts.setVisible(true);

                }
/*

                if (data[1].equals(islogin)) {
                    JOptionPane.showMessageDialog(//should be the login gui frame,
                             " is already log in",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                            //how to set login gui to setvisible true
                }
*/

                if (data[0].equals(invite)) {
                    //PARAM: challenged, challenger
                    String challenged = data[1];
                    String challenger = data[2];
                    ShowInvitePane invitePane = new ShowInvitePane(challenged, challenger);
                    invitePane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                    invitePane.setSize(300, 250);
                    invitePane.pack();
                    invitePane.setVisible(true);


                    System.out.println("You have been invited by: " + data[1]);
                    System.out.println(stream);
                }

                if (data[0].equals(start)) {

                                      //Person who sent the invite
                    //Challenger= data[1];
                    //Person who received the invite
                     //Challenged = data[2];

                    setChallenger(data[1]);
                    setChallenged(data[2]);

                    System.out.println("I am challenger!: "+ getChallenger());
                    System.out.println("I am the challenged!: "+getChallenged());
                    System.out.println(stream);

                    //Create board here
                    Board board = Board.createStandardBoard();
                   System.out.println(board);
                    Table.get().show();


                }

                if (data[0].equals(declined)) {
                    System.out.println("User has declined");
                    System.out.println(stream);
                }else if(data[0].equals(move)){
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

                    //sending to the other client
                    //PrintWriter toClientWriter = singleton.fetchSocket(toClient);
                   // toClientWriter.println("Move"+":"+fromClient+":"+toClient+":"+sourceTile+":"+destinationTile);
                    //toClientWriter.flush();

                }
            }
            }catch(Exception ex){
                ex.printStackTrace();
            }
    }


    public String GetChallenger()
    {
        return Challenger;
    }
    public String GetChallenged()
    {
        return Challenged;
    }
}
