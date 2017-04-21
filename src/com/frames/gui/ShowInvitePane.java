package com.frames.gui;

import com.frames.network.ConnectListenHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by azkei on 18/04/2017.
 */
public class ShowInvitePane extends JFrame {

    private JLabel message;
    private JPanel container;
    private JButton yesBtn;
    private JButton noBtn;
    private String challenged;
    private String challenger;

    ConnectListenHandler connectListenHandler;

    public ShowInvitePane(String challenged, String challenger) {
        super("Game Invite");
        this.challenged = challenged;
        this.challenger = challenger;

        connectListenHandler = new ConnectListenHandler();
        //Adding and setting up components
        setResizable(false);

        setLayout(new GridLayout(0,1,3,3));
        message = new JLabel("You have been invited to play by: " + challenger);
        message.setFont(new Font("San Serif", Font.PLAIN, 14));
        add(message);

        yesBtn = new JButton("Accept");
        noBtn = new JButton("Decline");
        add(yesBtn);
        add(noBtn);
        //handler class
        Handlers handler = new Handlers();

        yesBtn.addActionListener(handler);
        noBtn.addActionListener(handler);

        //when close button is press it wont close until accept or decline is picked.
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        });

    }

    //handler for sending data back to the server
    private class Handlers implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == yesBtn) {
                connectListenHandler.writer.println("Accept" + ":" + challenger + ":" + challenged);
                connectListenHandler.writer.flush();
                setVisible(false);
            }

            if (e.getSource() == noBtn) {
                connectListenHandler.writer.println("Decline" + ":" + challenger + ":" + challenged);
                connectListenHandler.writer.flush();
                setVisible(false);
            }
        }
    }
}
