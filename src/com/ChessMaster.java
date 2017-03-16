package com;

import com.network.*;

/**
 * Created by azkei on 16/03/2017.
 */
public class ChessMaster {
    public static void main(String[] args){
        Server server = new Server("localhost",222);
        Client client = new Client("localhost",222);
    }
}
