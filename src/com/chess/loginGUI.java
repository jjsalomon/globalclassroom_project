package com.chess;

import com.chess.network.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Francis on 3/18/2017.
 */
public class loginGUI {

    Client client = new Client("localhost",222);
    public JPanel login;
    private JButton registerButton;
    private JButton loginButton;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JLabel title;
    private JLabel username;
    private JLabel password;
    boolean valid;

    public loginGUI() {

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = textField1.getText();
                String password = String.valueOf(passwordField1.getPassword());

            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = textField1.getText();
                String password = String.valueOf(passwordField1.getPassword());
                if (valid) {
                    System.out.println("Welcome " + username);
                    JOptionPane.showMessageDialog(null, "Welcome " + username);
                    //!!add the main chess game
                } else {
                    System.out.println("Invalid login details");
                    JOptionPane.showMessageDialog(null, "Incorrect login details");
                }
            }
        });
    }
}
