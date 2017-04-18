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

    public ShowInvitePane(String challenged, String challenger){
        super("Game Invite");
        this.challenged = challenged;
        this.challenger = challenger;

        connectListenHandler = new ConnectListenHandler();
        //Adding and setting up components
        setResizable(false);

        setLayout(new FlowLayout());

        message = new JLabel("You have been invited to play by: "+challenger);
        add(message);

        yesBtn = new JButton("Accept");
        noBtn = new JButton("Decline");
        add(yesBtn);
        add(noBtn);
        //handler class
        Handlers handler = new Handlers();

        yesBtn.addActionListener(handler);
        noBtn.addActionListener(handler);
    }

    //handler for sending data back to the server
    private class Handlers implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == yesBtn){
                connectListenHandler.writer.println("Accept"+":"+challenger+":"+challenged);
                connectListenHandler.writer.flush();
                setVisible(false);
            }

            if(e.getSource() == noBtn){
                connectListenHandler.writer.println("Decline"+":"+challenger+":"+challenged);
                connectListenHandler.writer.flush();
                setVisible(false);
            }
        }
    }
}
