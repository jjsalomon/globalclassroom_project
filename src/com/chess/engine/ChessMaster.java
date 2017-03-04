package com.chess.engine;

import com.chess.engine.board.Board;

/**
 * Created by azkei on 04/03/2017.
 */
public class ChessMaster {

    public static void main(String[] args){
        Board board = Board.createStandardBoard();

        System.out.println(board);
    }
}
