package com.chess;

import com.chess.network.Client;

import javax.swing.*;
import java.util.Scanner;

/**
 * Created by azkei on 04/03/2017.
 */
public class ChessMaster {

    public static void main(String[] args){

        JFrame frame = new JFrame("loginGUI");
        frame.setContentPane(new loginGUI().login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        //Board board = Board.createStandardBoard();

        //  System.out.println(board);

        //Table table = new Table();

        //passed in server host: localhost, server port number: 222

    }
}
