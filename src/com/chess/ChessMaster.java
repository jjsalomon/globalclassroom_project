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

        //System.out.println(board);

        //Table table = new Table();

        //passed in server host: localhost, server port number: 222


        /*loginGUI login =  new loginGUI();
        //whenever x button then terminate
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setSize(500,500);
        login.setVisible(true);*/

        Account account = new Account();
        account.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        account.setSize(500,500);
        account.setVisible(true);

    }
}
