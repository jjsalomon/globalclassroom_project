package com.frames.gui;

import com.frames.network.ConnectListenHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Francis on 3/18/2017.
 * This class contains the GUI components which connectListenHandler to
 * network handlers that  send data to the server and handle
 * response from server
 */


public class LoginRegister extends JFrame{

    String user,pw;

    //GUI components
    public JPanel login;
    private JButton registerButton;
    private JButton loginButton;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JLabel title;
    private JLabel username;
    private JLabel password;

    ConnectListenHandler connectListenHandler;

    //Initialize components
    public LoginRegister(){
        connectListenHandler = new ConnectListenHandler();

        username = new JLabel("Username");
        add(username);
        textField1 = new JTextField("Enter Your Username Here");
        add(textField1);password = new JLabel("Password");
        add(password);
        passwordField1 = new JPasswordField(10);
        add(passwordField1);
        registerButton = new JButton("Register");
        add(registerButton);
        loginButton = new JButton("Login");
        add(loginButton);

        //FLOW LAYOUT for GUI Components
        setLayout(new FlowLayout());
        //Handler for sending information
        Handlers handler = new Handlers();
        registerButton.addActionListener(handler);
        loginButton.addActionListener(handler);
        textField1.addActionListener(handler);
        passwordField1.addActionListener(handler);

        //Make initia
    }


    //Handler for sending data to the server
    private class Handlers implements ActionListener{


        @Override
        public void actionPerformed(ActionEvent e) {
            //if users clicks on login button
            if(e.getSource() == loginButton){
                    user = textField1.getText();
                    pw = new String(passwordField1.getPassword());
                    try{
                        connectListenHandler.writer.println(user + ":"+ pw +":Login");
                        connectListenHandler.writer.flush();
                        setVisible(false);
                    }catch (Exception ex) {
                        System.out.println("You cannot login, Try again");
                        ex.printStackTrace();
                    }
                    //Read response information from server
                    connectListenHandler.ListenThread();

            }

            //if user clicks on register button
            if(e.getSource() == registerButton){
                user = textField1.getText();
                pw = new String(passwordField1.getPassword());
                try{
                    connectListenHandler.writer.println(user+":"+pw +":Register");
                    connectListenHandler.writer.flush();

                }catch (Exception ex){
                    System.out.println("You cannot register, Try again");
                    ex.printStackTrace();
                }
                //Read response information from server
                connectListenHandler.ListenThread();

            }
        }
    }


}
