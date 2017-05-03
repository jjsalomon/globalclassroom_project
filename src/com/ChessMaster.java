package com;

import com.frames.network.sConnectListenHandler;
import com.frames.resource.MoveBuffer;
import com.frames.gui.SingletonLogin;
import com.frames.resource.UserOnline;

public class ChessMaster {
    public static void main(String[] args) {
        //First instantiation of the singletons
        UserOnline usersOnlineInstance = UserOnline.getInstance();
        sConnectListenHandler sclh = sConnectListenHandler.getInstance();
        MoveBuffer moveBuffer = MoveBuffer.getFirstInstance();
        System.out.println("Main: Instance ID" + System.identityHashCode(usersOnlineInstance));

        //Create the login gui window
        SingletonLogin sgl = SingletonLogin.getFirstInstance();
        //set up the gui visible and size
        sgl.setGuiWindow();
    }
}
