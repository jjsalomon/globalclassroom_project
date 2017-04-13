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
        setResizable(false);
        //Adding and setting up components
        //Container panel = main panel
        container = new JPanel();
        container.setLayout(null);

        //login panel contains login/register form inside container panel
        login = new JPanel();
        login.setLayout(null);
        login.setBounds(50,150,200,180);
        login.setBackground(new Color(255,255,255,0));

        //add all form component to login panel
        username = new JLabel("Username");
        username.setBounds(5,5,100,20);
        username.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
        username.setForeground(new Color(245, 245, 245));
        login.add(username);

        textField1 = new JTextField();
        textField1.setBounds(5,30,170,20);
        textField1.setToolTipText("Enter your username");
        login.add(textField1);

        password = new JLabel("Password");
        password.setBounds(5,55,100,20);
        password.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
        password.setForeground(new Color(245, 245, 245));
        login.add(password);

        passwordField1 = new JPasswordField();
        passwordField1.setBounds(5,80,170,20);
        passwordField1.setToolTipText("Enter your password");
        login.add(passwordField1);

        registerButton = new JButton("Register");
        registerButton.setBounds(5,110,170,25);
        registerButton.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
        registerButton.setToolTipText("Enter a new username and password to register");
        login.add(registerButton);

        loginButton = new JButton("Login");
        loginButton.setBounds(5,150,170,25);
        loginButton.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
        loginButton.setToolTipText("Enter your username and password to login");
        login.add(loginButton);

        //add the inner panel (login) to the main panel (container)
        container.add(login);

        title = new JLabel("Chess Master");
        title.setBounds(30,90,300,35);
        title.setFont(new Font("Lucida Handwriting", Font.BOLD, 35));
        title.setForeground(new Color(245, 245, 245));
        container.add(title);

        background= new JLabel(new ImageIcon(getClass().getClassLoader().getResource("com/frames/gui/images/loginbackground.png")));
        background.setBounds(0,0,500,500);
        container.add(background);

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
