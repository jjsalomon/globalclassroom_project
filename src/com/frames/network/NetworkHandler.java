package com.frames.network;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by azkei on 02/04/2017.
 */
public abstract class NetworkHandler {

    //Addressing information
    protected String address = "localhost";
    protected int port = 2222;

    //Socket information, writers, readers
    protected Socket socket;
    protected BufferedReader reader;

}
