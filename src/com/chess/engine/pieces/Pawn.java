package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by azkei on 01/03/2017.
 */
public class Pawn extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATE = {8, 16, 9, 7};

    public Pawn( final Alliance pieceAlliance,final int piecePosition) {
        super(PieceType.PAWN,pieceAlliance, piecePosition);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE){

            //for white apply -8, for white apply 8
            int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);

            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            //if tile moving forward is not occupied
            if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                //TODO more work here -- consider pawn promotion and en passant!
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                // PAWN jump
            }else if(currentCandidateOffset == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())){

                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                //if its not occupied behind and the candidate is not occupied
                if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()){

                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));

                }
            //if eight column and not these conditions
            }else if(currentCandidateOffset == 7 &&
                    !(BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.getPieceAlliance().isWhite() ||
                    (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()) )){

                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                        //TODO add attack move
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }
            }else if(currentCandidateOffset == 9 &&
                    !(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceAlliance().isWhite() ||
                            (BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()) )){

                    if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){

                        final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                        if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                            //TODO add attack move
                            legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                        }
                    }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public  String toString(){
        return PieceType.PAWN.toString();
    }
}
