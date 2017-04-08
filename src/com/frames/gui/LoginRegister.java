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


public class LoginRegister extends JFrame {

    String user, pw;

    //GUI components
    public JPanel container;
    public JPanel login;
    private JButton registerButton;
    private JButton loginButton;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JLabel title;
    private JLabel username;
    private JLabel password;
    private JLabel background;

    ConnectListenHandler connectListenHandler;

    //Initialize components
    public LoginRegister() {
        connectListenHandler = new ConnectListenHandler();

        //Adding and setting up components
        //Container panel = main panel
        container = new JPanel();
        container.setLayout(null);

        //login panel contains login/register form inside container panel
        login = new JPanel();
        login.setLayout(null);
        login.setBounds(5,100,120,180);
        login.setBackground(new Color(0,0,0,64));

        //cant access image??? or not showing...
        //tried getClass().getResource()
/*        background= new JLabel(new ImageIcon("com/frames/gui/images/loginbackground.png"));
        background.setBounds(0,0,500,500);
        container.add(background);*/

        title = new JLabel("Chess Master");
        title.setBounds(10,10,300,35);
        title.setFont(new Font("Lucida Handwriting", Font.BOLD, 30));
        container.add(title);

        username = new JLabel("Username");
        username.setBounds(5,5,100,20);
        username.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
        login.add(username);

        textField1 = new JTextField();
        textField1.setBounds(5,30,100,20);
        login.add(textField1);

        password = new JLabel("Password");
        password.setBounds(5,55,100,20);
        password.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
        login.add(password);

        passwordField1 = new JPasswordField();
        passwordField1.setBounds(5,80,100,20);
        login.add(passwordField1);

        registerButton = new JButton("Register");
        registerButton.setBounds(5,110,100,25);
        registerButton.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
        login.add(registerButton);

        loginButton = new JButton("Login");
        loginButton.setBounds(5,150,100,25);
        loginButton.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
        login.add(loginButton);


        container.add(login);
        //add panel login to Jframe
        add(container);

        LoginRegister.Handlers handler = new LoginRegister.Handlers();
        registerButton.addActionListener(handler);
        loginButton.addActionListener(handler);
        textField1.addActionListener(handler);
        passwordField1.addActionListener(handler);
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
