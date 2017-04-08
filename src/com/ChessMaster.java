package com;

import com.frames.gui.*;


import javax.swing.*;


/**
 * Created by azkei on 04/03/2017.
 */
public class ChessMaster {

    public static void main(String[] args) {

       /* Board board = Board.createStandardBoard();

        System.out.println(board);

        Table.get().show();*/


        //passed in server host: localhost, server port number: 222
        LoginRegister login =  new LoginRegister();
        //whenever x button then terminate
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setSize(500,500);
        login.setVisible(true);

//        Account accounts =  new Account("test");
//        //whenever x button then terminate
//        accounts.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        accounts.setSize(700,500);
//        accounts.setVisible(true);
    }
}
