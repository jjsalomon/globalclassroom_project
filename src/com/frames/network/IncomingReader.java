package com.frames.network;

/**
 * Created by azkei on 02/04/2017.
 * This class class is a Thread handler for information
 * coming into the client.
 */
public class IncomingReader extends NetworkHandler implements Runnable
{
    @Override
    public void run()
    {

        System.out.println("Thread running");
        String[] data;
        String stream,
                account = "Account",
                disconnect = "Disconnect", chat = "Message",
                login = "Login";
        try {
            while ((stream = reader.readLine()) != null) {
                data = stream.split(":");

                if(data[1].equals(chat)){
                    System.out.println(stream);
                }

                if(data[1].equals(login)){
                    System.out.println(stream);
                }
            }
        }catch(Exception ex) { }
    }
}