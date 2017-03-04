package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

/**
 * Created by azkei on 01/03/2017.
 */
public class Queen extends Piece {

    //rook and bishop candidate move vectors
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen( final Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.QUEEN,pieceAlliance, piecePosition);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES){

            int candidateDestinationCoordinate = this.piecePosition;

            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){


                //if edge cases are triggered
                if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isEightColumnExclusion(candidateDestinationCoordinate,candidateCoordinateOffset)){
                    break;
                }

                candidateDestinationCoordinate += candidateCoordinateOffset;
                //is position valid ?
                if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if(!candidateDestinationTile.isTileOccupied()){
                        legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));
                    }else{
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        //if enemy piece - attack move
                        if(this.pieceAlliance != pieceAlliance){
                            legalMoves.add(new AttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination));
                        }
                        //break exists to stop the piece from continuing on the move ie stop where a friendly or enemy is
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public  String toString(){
        return PieceType.QUEEN.toString();
    }

    //edge cases - these are the positions where the bishop rules fall apart.
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1 || candidateOffset == -9 ||candidateOffset == 7);
    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == 7 || candidateOffset == 1 || candidateOffset == 9);
    }
}
