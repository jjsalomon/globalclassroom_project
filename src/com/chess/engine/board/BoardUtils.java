package com.chess.engine.board;

/**
 * Created by azkei on 28/02/2017.
 */
//this utils class will be useful for other pieces
public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = null;
    public static final boolean[] SECOND_COLUMN = null;
    public static final boolean[] SEVENTH_COLUMN = null;
    public static final boolean[] EIGHT_COLUMN = null;

    private BoardUtils(){
        throw new RuntimeException("Cant instantitate this!");
    }
    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < 64;
    }
}
