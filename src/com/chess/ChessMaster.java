package com.chess;

import com.chess.network.Client;
import com.chess.network.Server;

/**
 * Created by azkei on 04/03/2017.
 */
public class ChessMaster {

    public static void main(String[] args){

        Server server = new Server(222);
        Client client = new Client("localhost",222);
    }
}
