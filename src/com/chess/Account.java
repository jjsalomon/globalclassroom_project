package com.chess;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by azkei on 01/04/2017.
 */
public class Account extends JFrame {

    private JList list;
    private ArrayList onlineStreams = new ArrayList();
    private JButton btnRefresh;


    public Account(){

        super("Chess Master - Jelo");
        onlineStreams.add("jelo");
        onlineStreams.add("john");
        onlineStreams.add("francis");
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

        //choose something on the list
        list.addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        //send a request using
                        System.out.println(onlineStreams.get(list.getSelectedIndex()));

                    }
                }
        );

        btnRefresh.addActionListener(

        );


    }
}
