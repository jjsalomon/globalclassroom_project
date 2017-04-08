package com.frames.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by azkei on 01/04/2017.
 * Changes by Francis on 08/04/2017
 */
public class Account extends JFrame {

    public JPanel container;
    public JList online;
    public JScrollPane jspane;
    private JLabel username;
    private JLabel loss;
    private JLabel coins;
    private JButton skinsButton;
    private JLabel rank;
    private JLabel win;
    private JLabel dbcoin;
    private JLabel dbwin;
    private JLabel dbrank;
    private JLabel dbloss;
    private JButton Refresh;
    private JLabel OnlinePlayers;

    private ArrayList onlineStreams = new ArrayList();

    public Account(String stream){
        super("Chess Master - ");
        //splits stream into data[] username starts at data[2]
        String[] data = stream.split(":");

        //dummy variables for arraylist online players
        onlineStreams.add("Jelo");
        onlineStreams.add("John");
        onlineStreams.add("Francis");
        onlineStreams.add("Pamela");
        onlineStreams.add("Ben");
        onlineStreams.add("Mark");
        onlineStreams.add("Luke");
        onlineStreams.add("Mati");
        onlineStreams.add("Carl");
        onlineStreams.add("Hendry");
        onlineStreams.add("Sonna");
        onlineStreams.add("Queena");

        //Adding and setting up components
        container = new JPanel();
        container.setLayout(null);

        //cant access image??? or not showing...
        //tried getClass().getResource()
/*        background= new JLabel(new ImageIcon("com/frames/gui/images/loginbackground.jpg"));
        background.setBounds(0,0,500,500);
        container.add(background);*/

        username = new JLabel(data[2]);
//        username = new JLabel("Jsexrqiexe");
        username.setBounds(20,30,300,35);
        username.setFont(new Font("Lucida Handwriting", Font.BOLD, 30));
        container.add(username);

        OnlinePlayers = new JLabel("Online Players");
        OnlinePlayers.setBounds(270,70,250,20);
        OnlinePlayers.setFont(new Font("Lucida Handwriting", Font.PLAIN, 20));
        container.add(OnlinePlayers);

        Refresh = new JButton("Refresh");
        Refresh.setBounds(520,70,150,20);
        Refresh.setHorizontalAlignment(SwingConstants.CENTER);
        Refresh.setFont(new Font("Lucida Handwriting", Font.PLAIN, 20));
        container.add(Refresh);

        rank = new JLabel("Rank#");
        rank.setBounds(35,120,100,20);
        rank.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        container.add(rank);

        dbrank = new JLabel(data[3]); //user rank data from database
//        dbrank = new JLabel("12"); //user rank data from database
        dbrank.setBounds(120,120,100,20);
        dbrank.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        container.add(dbrank);

        win = new JLabel("Win");
        win.setBounds(35,170,100,20);
        win.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        container.add(win);

//        dbwin = new JLabel("15"); //user win data from database
        dbwin = new JLabel(data[4]); //user win data from database
        dbwin.setBounds(120,170,100,20);
        dbwin.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        container.add(dbwin);

        loss = new JLabel("Loss");
        loss.setBounds(35,220,100,20);
        loss.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        container.add(loss);

//        dbloss = new JLabel("55"); //user loss data from database
        dbloss = new JLabel(data[5]); //user loss data from database
        dbloss.setBounds(120,220,100,20);
        dbloss.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        container.add(dbloss);

        coins = new JLabel("Coins");
        coins.setBounds(35,270,100,20);
        coins.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        container.add(coins);

//        dbcoin = new JLabel("13224"); //user coin data from database
        dbcoin = new JLabel(data[6]); //user coin data from database
        dbcoin.setBounds(120,270,100,20);
        dbcoin.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        container.add(dbcoin);

/*        skin = new JLabel("Skins"); //server can only send to data[6]? give null pointer.. + this feature don't know how it works
        gridConstraints.gridy = 6;
        gridConstraints.gridx = 1;
        container.add(skins);*/

        //change this to onlineStreams
        online = new JList(onlineStreams.toArray());
//        //how many options can they see
//        online.setVisibleRowCount(5);
        //can only select one thing on the list at the time
        online.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jspane = new JScrollPane(online);
        jspane.setBounds(270,120,400,170);
        container.add(jspane);

        //add panel container to Jframe
        add(container);

        Account.Handlers handler = new Account.Handlers();
        Refresh.addActionListener(handler);
    }

    private class Handlers implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            //if user clicks on refresh button
            if(e.getSource() == Refresh){
                refreshList();
            }

            //if user clicks on a list item
            if(e.getSource() == online){
                System.out.println(onlineStreams.get(online.getSelectedIndex()));
                refreshList();
            }
        }
    }

    //
    public void refreshList() {
        online = new JList(onlineStreams.toArray());
        //how many options can they see
        online.setVisibleRowCount(5);
        //can only select one thing on the list at the time
        online.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        container.add(new JScrollPane(online));
        add(container);
    }
}