package com.frames.gui;

import com.frames.network.ConnectListenHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Francis on 4/5/2017.
 */
public class SignInUp extends JFrame {

    String user,pw;

    private JPanel Sign;
    private JTextField usertextfield;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel title;
    private JLabel username;
    private JLabel Password;

    ConnectListenHandler connectListenHandler;

    public SignInUp() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                user = usertextfield.getText();
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
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                user = usertextfield.getText();
                pw = new String(passwordField1.getPassword());
                try{
                    connectListenHandler.writer.println(user + ":"+ pw +":Login");
                    connectListenHandler.writer.flush();

                }catch (Exception ex) {
                    System.out.println("You cannot login, Try again");
                    ex.printStackTrace();
                }
                //Read response information from server
                connectListenHandler.ListenThread();
            }
        });
    }
}
