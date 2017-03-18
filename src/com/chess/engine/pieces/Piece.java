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
    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected  final boolean isFirstMove;

    protected final int cachedHashCode;



    Piece( final PieceType pieceType,
            final Alliance pieceAlliance,final int piecePosition){
        this.pieceType = pieceType;
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        //TODO more work here
        this.isFirstMove = false;
        this.cachedHashCode = computeHashCode();

    }


    private int computeHashCode() {

        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;

    }

    @Override
    public boolean equals(final  Object other)
    {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.piecePosition && pieceType == otherPiece.pieceType &&
                pieceAlliance == otherPiece.pieceAlliance && isFirstMove == otherPiece.isFirstMove;
    }
    @Override
    public  int hashCode()
    {

       return this.cachedHashCode;

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

    public PieceType getPieceType(){
        return this.pieceType;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public abstract Piece movePiece(Move move);

    public enum PieceType {

        PAWN(100, "P") {
            @Override
            public boolean isPawn() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT(320, "N") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP(330, "B") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK(500, "R") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN(900, "Q") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING(10000, "K") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return true;
            }
        };

        private final int value;
        private final String pieceName;

        public int getPieceValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        PieceType(final int val, final String pieceName) {
            this.value = val;
            this.pieceName = pieceName;
        }

        public abstract boolean isPawn();
        public abstract boolean isRook();
        public abstract boolean isKing();

    }
}
