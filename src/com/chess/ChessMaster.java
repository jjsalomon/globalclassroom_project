package com.chess;

import com.chess.engine.board.Board;
import com.chess.gui.Table;
import com.frames.gui.*;


import javax.swing.*;


public class ChessMaster {

    public static void main(String[] args) {

       Board board = Board.createStandardBoard();

        System.out.println(board);

        Table.get().show();


        //passed in server host: localhost, server port number: 222
       // LoginRegister login =  new LoginRegister();
        //whenever x button then terminate
        //login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // login.setSize(500,500);
        //login.setVisible(true);

    }
}
