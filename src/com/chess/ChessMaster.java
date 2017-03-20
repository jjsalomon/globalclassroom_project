package com.chess;

import com.chess.network.Client;

import javax.swing.*;
import java.util.Scanner;

/**
 * Created by azkei on 04/03/2017.
 */
public class ChessMaster {

    public static void main(String[] args){



        //Board board = Board.createStandardBoard();

        //  System.out.println(board);

        //Table table = new Table();

        //passed in server host: localhost, server port number: 222

        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new loginGUI().setVisible(true);
            }
        });


    }
}
