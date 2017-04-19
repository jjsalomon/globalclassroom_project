package com.frames.gui;

import com.frames.network.ConnectListenHandler;
import com.frames.resource.UserOnline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by azkei on 01/04/2017.
 * Changes by Francis on 08/04/2017
 */
public class Account extends JFrame {

    UserOnline onlineBuffer;
    DefaultListModel lmodel;

    //network
    ConnectListenHandler connectListenHandler;

    private  JLabel background;
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
    private JButton Play;
    private JLabel OnlinePlayers;


    public Account(String stream){
        super("Chess Master - ");
        UserOnline onlineBuff = UserOnline.getInstance();
        this.onlineBuffer = onlineBuff;
        //network


        DefaultListModel listModel = new DefaultListModel();
        this.lmodel = listModel;
        //splits stream into data[] username starts at data[2]
        String[] data = stream.split(":");

        // pass down validated message and the username for mapping
        connectListenHandler = new ConnectListenHandler();

        //Adding and setting up components
        setResizable(false);

        container = new JPanel();
        container.setLayout(null);

//        username = new JLabel(data[2]);
        username = new JLabel(data[2]);
        username.setBounds(20,30,300,35);
        username.setFont(new Font("Lucida Handwriting", Font.BOLD, 30));
        username.setForeground(new Color(245, 245, 245));
        container.add(username);

        OnlinePlayers = new JLabel("Online Players");
        OnlinePlayers.setBounds(270,70,250,20);
        OnlinePlayers.setFont(new Font("Lucida Handwriting", Font.PLAIN, 20));
        OnlinePlayers.setForeground(new Color(245, 245, 245));
        container.add(OnlinePlayers);

        Refresh = new JButton("Refresh");
        Refresh.setBounds(520,70,150,25);
        Refresh.setHorizontalAlignment(SwingConstants.CENTER);
        Refresh.setFont(new Font("Lucida Handwriting", Font.PLAIN, 20));
        Refresh.setToolTipText("Refresh Online list");
        container.add(Refresh);

        rank = new JLabel("Rank");
        rank.setBounds(35,120,100,20);
        rank.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        rank.setForeground(new Color(245, 245, 245));
        container.add(rank);

        dbrank = new JLabel("#"+data[3]); //user rank data from database
//        dbrank = new JLabel("12"); //dummy data
        dbrank.setBounds(120,120,100,20);
        dbrank.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        dbrank.setForeground(new Color(245, 245, 245));
        container.add(dbrank);

        win = new JLabel("Win");
        win.setBounds(35,170,100,20);
        win.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        win.setForeground(new Color(245, 245, 245));
        container.add(win);

//        dbwin = new JLabel("15"); //dummy data
        dbwin = new JLabel(data[4]); //user win data from database
        dbwin.setBounds(120,170,100,20);
        dbwin.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        dbwin.setForeground(new Color(245, 245, 245));
        container.add(dbwin);

        loss = new JLabel("Loss");
        loss.setBounds(35,220,100,20);
        loss.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        loss.setForeground(new Color(245, 245, 245));
        container.add(loss);

//        dbloss = new JLabel("55"); //dummy data
        dbloss = new JLabel(data[5]); //user loss data from database
        dbloss.setBounds(120,220,100,20);
        dbloss.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        dbloss.setForeground(new Color(245, 245, 245));
        container.add(dbloss);

        coins = new JLabel("Coins");
        coins.setBounds(35,270,100,20);
        coins.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        coins.setForeground(new Color(245, 245, 245));
        container.add(coins);

//        dbcoin = new JLabel("13224"); //dummy data
        dbcoin = new JLabel(data[6]); //user coin data from database
        dbcoin.setBounds(120,270,100,20);
        dbcoin.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
        dbcoin.setForeground(new Color(245, 245, 245));
        container.add(dbcoin);

        Play = new JButton("Play");
        Play.setBounds(570,300,100,25);
        Play.setHorizontalAlignment(SwingConstants.CENTER);
        Play.setFont(new Font("Lucida Handwriting", Font.PLAIN, 20));
        Play.setEnabled(false);
        Play.setToolTipText("Select a player to challenge and then press play");
        container.add(Play);

/*        skin = new JLabel("Skins"); //server can only send to data[6]? give null pointer.. + this feature don't know how it works
        gridConstraints.gridy = 6;
        gridConstraints.gridx = 1;
        container.add(skins);*/

        //remove current elements to make sure its clear before adding
        lmodel.removeAllElements();
        //add global user online data to Listmodel
        for(int i =0; i<onlineBuff.getOnlineBuff().size();i++){
            lmodel.addElement(onlineBuffer.getOnlineBuff().get(i));
        }
        //add into the JList
        online = new JList(lmodel);
        //how many options can they see
        online.setVisibleRowCount(5);
        //can only select one thing on the list at the time
        online.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        online.setForeground(new Color(245, 245, 245));
        online.setBackground(new Color(0, 0, 0));
        jspane = new JScrollPane(online);
        jspane.setBounds(270,120,400,170);
        container.add(jspane);

        background= new JLabel(new ImageIcon(getClass().getClassLoader().getResource("com/frames/gui/images/loginbackground.png")));
        background.setBounds(0,0,700,500);
        container.add(background);

        //add panel container to Jframe
        add(container);

        Account.Handlers handler = new Account.Handlers();
        Refresh.addActionListener(handler);
        Play.addActionListener(handler);

        //user wants to challenge other user
        MouseListener mouselistener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {

                    if (online.getSelectedIndex() == -1) {
                        //No selection, disable Play button.
                        Play.setEnabled(false);

                    } else {
                        //Selection, enable the Play button.
                        Play.setEnabled(true);
                    }
                }
            }

        };
        online.addMouseListener(mouselistener);

        //when user wants to log out by X close button
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                int confirm = JOptionPane.showOptionDialog(Account.this,
                        "Are you sure you want to log out?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        connectListenHandler.writer.println(username.getText() + ":to" + ":Disconnect");
                        connectListenHandler.writer.flush();
                        //Read response information from server
                        connectListenHandler.ListenThread();
                        System.exit(0);
                    } catch (Exception ex) {
                        System.out.println("You cannot log out, Try again");
                        ex.printStackTrace();
                    }

                }
                if (confirm == JOptionPane.NO_OPTION) {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }


    private class Handlers implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            //if user clicks on refresh button
            if(e.getSource() == Refresh){
                refreshList();
            }
            //if user clicks on play button
            if(e.getSource() == Play){
                String selectedItem = (String) online.getSelectedValue();
                System.out.print(selectedItem);
                Play.setEnabled(false);
                try {
                    connectListenHandler.writer.println(selectedItem +":"+username.getText()+ ":"+"Challenge");
                    connectListenHandler.writer.flush();
                    //Read response information from server
                    connectListenHandler.ListenThread();
                } catch (Exception ex) {
                    System.out.println("You cannot challenge " + selectedItem + "please try again later");
                    ex.printStackTrace();
                }
            }
        }
    }

    //
    public void refreshList(){
        lmodel.removeAllElements();
       /* String strArray[] = new String[onlineBuffer.getOnlineBuff().size()];
        for(int i =0;i<strArray.length;i++){
            strArray[i] = onlineBuffer.getOnlineBuff().toString();
            System.out.println(strArray[i]+"Added to lmodel");
        }*/
       for(int i = 0;i<onlineBuffer.getOnlineBuff().size();i++){
           lmodel.addElement(onlineBuffer.getOnlineBuff().get(i));
       }
       //re-set the listmodel
       online.setModel(lmodel);
       Play.setEnabled(false);
    }
}