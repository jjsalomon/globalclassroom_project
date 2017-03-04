package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by azkei on 04/03/2017.
 */
public abstract class Player {

    protected final Board board;

    //keeping track of king
    protected final King playerKing;

    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves){

        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
    }

    public King getPlayerKing(){
        return this.playerKing;
    }

    public Collection<Move> getLegalMoves(){
        return this.legalMoves;
    }

    private static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {

        final List<Move> attackMoves = new ArrayList<>();
        for(final Move move : moves){

            if(piecePosition == move.getDestinationCoordinate()){
                attackMoves.add(move);
            }
        }

        return ImmutableList.copyOf(attackMoves);
    }

    private King establishKing() {

        for(final Piece piece : getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }

        throw new RuntimeException("Should not reach here! Not a valid board");
    }

    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck(){
        return this.isInCheck();
    }

    public boolean isInCheckMate(){
        return this.isInCheck && !hasEscapeMoves();
    }

    protected boolean hasEscapeMoves() {

        for(final Move move: this.legalMoves){
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus().isDone()){
                return true;
            }
        }

        return false;

    }

    public boolean isInStaleMate(){
        //king is in check and has no escape moves
        return !this.isInCheck && !hasEscapeMoves();
    }

    public boolean isCastled(){
        return false;
    }

    //very important method
    public MoveTransition makeMove(final Move move){

        //if move is illegal - return same board that we came in on.
        if(!isMoveLegal(move)){
            return new MoveTransition(this.board,move,MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();

        //calculate attacks on tile for current players tile current king
        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionBoard.currentPlayer().getLegalMoves());

        //helper method - if move attacks are empty, return this board
        if(!kingAttacks.isEmpty()){
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        //otherwise return move transition board
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }


    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
}
