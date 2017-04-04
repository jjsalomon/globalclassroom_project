package com.frames.gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by azkei on 01/04/2017.
 */
public class Account extends JFrame {

    private JList list;

    private JButton btnRefresh;
    private JLabel username;
    private JLabel rank;
    private JLabel coins;


    private ArrayList onlineStreams = new ArrayList();

    public Account(String stream){
        super("Chess Master - Jelo");
        //splits stream into data[] username starts at data[2]
        String[] data = stream.split(":");
        //dummy variables
        onlineStreams.add("jelo");
        onlineStreams.add("john");
        onlineStreams.add("francis");
        username = new JLabel(data[2]);
        add(username);
        rank = new JLabel(data[3]);
        add(rank);
        coins = new JLabel(data[5]);
        add(coins);
        setLayout(new FlowLayout());

        btnRefresh = new JButton("Refresh");
        add(btnRefresh);
        //change this to onlineStreams
        list = new JList(onlineStreams.toArray());
        //how many options can they see
        list.setVisibleRowCount(4);
        //can only select one thing on the list at the time
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(list));

        Handlers handler = new Handlers();
        btnRefresh.addActionListener(handler);


    }

    private class Handlers implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            //if user clicks on refresh button
            if(e.getSource() == btnRefresh){
                refreshList();
            }

            //if user clicks on a list item
            if(e.getSource() == list){
                System.out.println(onlineStreams.get(list.getSelectedIndex()));
                refreshList();
            }
        }
    }

    //
    public void refreshList() {
        list = new JList(onlineStreams.toArray());
        //how many options can they see
        list.setVisibleRowCount(4);
        //can only select one thing on the list at the time
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(list));
    }




}
