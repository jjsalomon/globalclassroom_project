package com.frames.gui;

import com.frames.network.ConnectListenHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Francis on 4/6/2017.
 * Reference:http://www.newthinktank.com/2012/03/java-video-tutorial-29/
 */
public class test123 extends JFrame {

    String user, pw;

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
    public test123() {
        connectListenHandler = new ConnectListenHandler();
        login = new JPanel();
        login.setLayout(new GridBagLayout());

        // You create a GridBagContraints object that defines
        // defaults for your components
        GridBagConstraints gridConstraints = new GridBagConstraints();
        // Define the x position of the component
        gridConstraints.gridx = 1;
        // Define the y position of the component
        gridConstraints.gridy = 1;
        // Number of columns the component takes up
        gridConstraints.gridwidth = 1;
        // Number of rows the component takes up
        gridConstraints.gridheight = 1;
        // Gives the layout manager a hint on how to adjust
        // component width (0 equals fixed)
        gridConstraints.weightx = 1;
        // Gives the layout manager a hint on how to adjust
        // component height (0 equals fixed)
        gridConstraints.weighty = 1;
        // Defines padding top, left, bottom, right
        gridConstraints.insets = new Insets(3, 3, 3, 3);
        // Defines where to place components if they don't
        // fill the space: CENTER, NORTH, SOUTH, EAST, WEST
        // NORTHEAST, etc.
        gridConstraints.anchor = GridBagConstraints.NORTH;
        // How should the component be stretched to fill the
        // space: NONE, HORIZONTAL, VERTICAL, BOTH
        gridConstraints.fill = GridBagConstraints.BOTH;

        title = new JLabel("Chess Master");
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        passwordField1 = new JPasswordField();
        textField1 = new JTextField();
        username = new JLabel("Username");
        password = new JLabel("Password");

        login.add(title,gridConstraints);
        gridConstraints.gridy = 2;
        login.add(username,gridConstraints);
        gridConstraints.gridy = 3;
        login.add(textField1,gridConstraints);
        gridConstraints.gridy = 4;
        login.add(password,gridConstraints);
        gridConstraints.gridy = 5;
        login.add(passwordField1,gridConstraints);
        gridConstraints.gridy = 6;
        login.add(registerButton,gridConstraints);
        gridConstraints.gridy = 7;
        login.add(loginButton,gridConstraints);
        gridConstraints.gridy = 8;
        add(login);
//        username = new JLabel("Username");
//        add(username);
//        textField1 = new JTextField("Enter Your Username Here");
//        add(textField1);
//        password = new JLabel("Password");
//        add(password);
//        passwordField1 = new JPasswordField(10);
//        add(passwordField1);
//        registerButton = new JButton("Register");
//        add(registerButton);
//        loginButton = new JButton("Login");
//        add(loginButton);
//
//        //FLOW LAYOUT for GUI Components
//        setLayout(new FlowLayout());
        //Handler for sending information
        test123.Handlers handler = new test123.Handlers();
        registerButton.addActionListener(handler);
        loginButton.addActionListener(handler);
        textField1.addActionListener(handler);
        passwordField1.addActionListener(handler);

        //Make initia
    }


    //Handler for sending data to the server
    private class Handlers implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            //if users clicks on login button
            if (e.getSource() == loginButton) {
                user = textField1.getText();
                pw = new String(passwordField1.getPassword());
                try {
                    connectListenHandler.writer.println(user + ":" + pw + ":Login");
                    connectListenHandler.writer.flush();
                    setVisible(false);
                } catch (Exception ex) {
                    System.out.println("You cannot login, Try again");
                    ex.printStackTrace();
                }
                //Read response information from server
                connectListenHandler.ListenThread();

            }

            //if user clicks on register button
            if (e.getSource() == registerButton) {
                user = textField1.getText();
                pw = new String(passwordField1.getPassword());
                try {
                    connectListenHandler.writer.println(user + ":" + pw + ":Register");
                    connectListenHandler.writer.flush();

                } catch (Exception ex) {
                    System.out.println("You cannot register, Try again");
                    ex.printStackTrace();
                }
                //Read response information from server
                connectListenHandler.ListenThread();

            }
        }
    }


}

