package com;


//import com.frames.gui.LoginRegister;
import com.frames.gui.AccProfile;
import com.frames.gui.LoginRegister;
import com.frames.gui.SignInUp;
import com.frames.gui.test123;


import javax.swing.*;


/**
 * Created by azkei on 04/03/2017.
 */
public class ChessMaster {

    public static void main(String[] args){

       /* Board board = Board.createStandardBoard();

        System.out.println(board);

        Table.get().show();*/
//
/*        JFrame frame = new JFrame("Login");
        frame.setContentPane(new SignInUp().Sign);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);*/

//        AccProfile acc =  new AccProfile("est");
//        acc.setContentPane(new AccProfile("est").accprof);
////        acc.setLocationRelativeTo(null);
//        //whenever x button then terminate
//        acc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        acc.pack();
////        login.setSize(500,300);
//        acc.setVisible(true);

/*      SignInUp login =  new SignInUp();
        login.setContentPane(new SignInUp().Sign);
        login.setLocationRelativeTo(null);
        //whenever x button then terminate
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        login.pack();
        login.setSize(500,300);
        login.setVisible(true);*/

        test123 login =  new test123();
        //whenever x button then terminate
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.pack();
        login.setVisible(true);

        //passed in server host: localhost, server port number: 222
/*        LoginRegister login =  new LoginRegister();
        //whenever x button then terminate
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setSize(500,500);
        login.setVisible(true);*/

       /* Account accounts =  new Account("test");
        //whenever x button then terminate
        accounts.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accounts.setSize(500,500);
        accounts.setVisible(true);*/
    }
}
