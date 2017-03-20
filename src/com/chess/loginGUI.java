package com.chess;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Francis on 3/18/2017.
 */
public class loginGUI extends javax.swing.JFrame{
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
    int port = 222;
    boolean valid;
    boolean isConnected = false;
    BufferedReader reader;
    PrintWriter writer;

    Socket socket;

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
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            /*try
            {
                while ((stream = reader.readLine()) != null)
                {
                    data = stream.split(":");

                    if (data[2].equals(chat))
                    {
                        System.out.println(data[0] + ": " + data[1] + "\n");

                    }
                    else if (data[2].equals(connect))
                    {
                        userAdd(data[0]);
                    }
                    else if (data[2].equals(disconnect))
                    {
                        userRemove(data[0]);
                    }
                    else if (data[2].equals(done))
                    {
                        //users.setText("");
                        writeUsers();
                        users.clear();
                    }
                }
            }catch(Exception ex) { }*/
        }
    }

    public loginGUI(){
        initializeComponents();
    }

    private void initializeComponents(){
        registerButton = new JButton();
        loginButton = new JButton();
        textField1 = new JTextField();
        passwordField1 = new JPasswordField();
        title = new JLabel();
        username = new JLabel();
        password = new JLabel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Chess Client");
        setResizable(true);

        //Listener for action button
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                registerAction(actionEvent);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        setVisible(true);
        pack();

    }

    //function to send to server when register button is pressed
    private void registerAction(ActionEvent actionEvent){
        if(isConnected == false){
            user = textField1.getText();
            pw = passwordField1.getSelectedText();
            textField1.setEditable(false);
            passwordField1.setEditable(false);

            try{
                socket = new Socket(address,port);
                InputStreamReader streamreader = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(socket.getOutputStream());
                //to send data:
                //writer.write(variable);
                writer.println(username + ":has connected.:Connect");
                writer.flush();
                isConnected = true;
            }catch (Exception e){
                System.out.println("You cannot connect, Try again");
                textField1.setEditable(true);
                passwordField1.setEditable(true);
            }

            ListenThread();
        }
    }






}
