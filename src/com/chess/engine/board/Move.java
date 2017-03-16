package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

/**
 * Created by azkei on 28/02/2017.
 */
public abstract class Move {

    //track board where we moved on
    final Board board;
    //track piece that was moved
    final Piece movedPiece;
    //track where piece moved
    final int destinationCoordinate;

    Move(final Board board,
         final Piece movedPiece,
         final int destinationCoordinate){
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public int getDestinationCoordinate(){
        return this.destinationCoordinate;
    }

    public abstract Board execute();

    //declare sub classes
    public static final class MajorMove extends Move{

        public MajorMove(final Board board, final Piece movedPiece, final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    public static final class AttackMove extends Move{

        //tracking attacked piece
        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        //making move on board - materializing new board not mutating a new board
        @Override
        public Board execute() {

            final Board.Builder builder = new Board.Builder();

            for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                //TODO hashcode and equals for pieces
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }

            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            //move the moved piece
            builder.setPiece(null);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

            return builder.build();
        }
    }


}
