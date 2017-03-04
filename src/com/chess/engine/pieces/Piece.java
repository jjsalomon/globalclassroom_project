package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;
import java.util.List;

/**
 * Created by azkei on 28/02/2017.
 */
public abstract class Piece {
    //every piece has a tile position / coordinate
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected  final boolean isFirstMove;


    Piece( final Alliance pieceAlliance,final int piecePosition){
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        //TODO more work here
        this.isFirstMove = false;
    }

    public int getPiecePosition(){
        return this.piecePosition;
    }

    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }

    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);
}
