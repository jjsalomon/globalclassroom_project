package com;

//import com.chess.engine.board.Board;
//import com.chess.gui.Table;
import com.frames.resource.MoveBuffer;
import com.frames.gui.SingletonLogin;
import com.frames.resource.UserOnline;


public class ChessMaster {

    public static void main(String[] args) {

<<<<<<< HEAD
     // Board board = Board.createStandardBoard();

        //System.out.println(board);

        //Table.get().show();

=======
>>>>>>> e9522fba73efc2b3d57462d4195ccd300d1adacf
        UserOnline usersOnlineInstance = UserOnline.getInstance();
        MoveBuffer moveBuffer = MoveBuffer.getFirstInstance();
        System.out.println("Main: Instance ID" + System.identityHashCode(usersOnlineInstance));

        //Create the login gui window
        SingletonLogin sgl = SingletonLogin.getFirstInstance();
        //set up the gui visible and size
        sgl.setGuiWindow();

        //passed in server host: localhost, server port number: 222
    /*    LoginRegister login =  new LoginRegister();
        //whenever x button then terminate
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setSize(500,500);
        login.setVisible(true);*/
    }
}
