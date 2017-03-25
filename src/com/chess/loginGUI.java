package com.chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Francis on 3/18/2017.
 */

/*
    1. The program first initializes GUI components -- initcomponents
    2. Once button is pressed network code is run in the functions -- see button listeners
    3. The chess client then writes out information to the server -- see button listeners
    4. A Listener thread is then created to read in information back from the server -- see listen thread and incoming reader class
 */
public class loginGUI extends JFrame{

    String user,pw;

    public JPanel login;
    private JButton registerButton;
    private JButton loginButton;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JLabel title;
    private JLabel username;
    private JLabel password;

    String address = "localhost";
    int port = 2222;
    boolean valid;
    boolean isConnected = false;
    BufferedReader reader;
    PrintWriter writer;

    Socket socket;


    public loginGUI(){
        username = new JLabel("Username");
        add(username);
        textField1 = new JTextField("Enter Your Username Here");
        add(textField1);
        password = new JLabel("Password");
        add(password);
        passwordField1 = new JPasswordField(10);
        add(passwordField1);
        registerButton = new JButton("Register");
        add(registerButton);
        loginButton = new JButton("Login");
        add(loginButton);

        //FLOW LAYOUT for GUI Components
        setLayout(new FlowLayout(FlowLayout.LEFT));
        Handlers handler = new Handlers();
        registerButton.addActionListener(handler);
        loginButton.addActionListener(handler);
        textField1.addActionListener(handler);
        passwordField1.addActionListener(handler);


    }


    private class Handlers implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            //if users clicks on login button
            if(e.getSource()== loginButton){
                    user = textField1.getText();
                    pw = new String(passwordField1.getPassword());
                    try{
                        socket = new Socket(address,port);
                        InputStreamReader streamreader = new InputStreamReader(socket.getInputStream());
                        reader = new BufferedReader(streamreader);
                        writer = new PrintWriter(socket.getOutputStream());
                        writer.println(user + ":"+ pw +":Login");
                        writer.flush();
                    }catch (Exception ex){
                        System.out.println("You cannot login, Try again");
                        ex.printStackTrace();
                    }

                    ListenThread();
            }

            //if user clicks on register button
            if(e.getSource() == registerButton){
                user = textField1.getText();
                pw = new String(passwordField1.getPassword());

                try{
                    socket = new Socket(address,port);
                    InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
                    reader = new BufferedReader(streamReader);
                    writer = new PrintWriter(socket.getOutputStream());
                    writer.println(user+":"+pw +":Register");
                    writer.flush();
                }catch (Exception ex){
                    System.out.println("You cannot register, Try again");
                    ex.printStackTrace();
                }

                ListenThread();
            }
        }
    }



    public void ListenThread()
    {
        Thread IncomingReader = new Thread(new loginGUI.IncomingReader());
        IncomingReader.start();
    }

    public class IncomingReader implements Runnable
    {
        @Override
        public void run()
        {
            String[] data;
            String stream,
                    done = "Done", connect = "Connect",
                    disconnect = "Disconnect", chat = "Message",
                    login = "Login";
            try {
                while ((stream = reader.readLine()) != null) {
                    data = stream.split(":");

                    if(data[1].equals(chat)){
                        System.out.println(stream);
                    }

                    if(data[1].equals(login)){
                        //System.out.println(stream);
                        //code to change login gui(or open new window) to profile gui
                        //and send this strings to that profile gui
                        String username = data[2];
                        String rank = data[3];
                        String win = data[4];
                        String loss = data[5];
                        String coins = data[6];
                        System.out.println("Username: " + username);
                        System.out.println("Rank: " + rank);
                        System.out.println("Win: " + win);
                        System.out.println("Loss: " + loss);
                        System.out.println("Coins: " + coins);
                    }

                }
                    //data = stream.split(":");

                    //if data is a chat message
                   /* if (data[2].equals(chat))
                    {
                        System.out.println(data[0] + ": " + data[1] + "\n");

                    }
                    //if its a connection message
                    else if (data[2].equals(connect))
                    {
                        userAdd(data[0]);
                    }
                    //if its a disconnection message
                    else if (data[2].equals(disconnect))
                    {
                        userRemove(data[0]);
                    }

                    //clears users
                    else if (data[2].equals(done))
                    {
                        //users.setText("");
                        writeUsers();
                        users.clear();
                    }
                }*/
            }catch(Exception ex) { }
        }
    }





}
