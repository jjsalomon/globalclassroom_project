package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by azkei on 28/02/2017.
 */

//cant instantiate this class
public abstract class Tile{

    //can only be set once at construction time
    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile>EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer,EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer,EmptyTile> emptyTileMap = new HashMap<>();

        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            emptyTileMap.put(i,new EmptyTile(i));
        }

        //Immutable Map - after construction of map, dont change it
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece){
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    public int getTileCoordinate() {
        return this.tileCoordinate;
    }

    Tile(final int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile{
        EmptyTile(final int coordinate){

            super(coordinate);
        }

        @Override
        public String toString(){
            //for empty tile return -
            return "-";
        }
        @Override
        public boolean isTileOccupied(){

            return false;
        }

        @Override
        public Piece getPiece(){

            return null;
        }
    }

    public static final class OccupiedTile extends Tile{

        //private, no way to reference outside
        private final Piece pieceOnTile;
        OccupiedTile(int tileCoordinate, final Piece pieceOnTile){
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public String toString(){
            //if tile is occupied print out the piece
            return getPiece().getPieceAlliance().isBlack() ?
                    getPiece().toString().toLowerCase() :
                    getPiece().toString();
        }

        @Override
        public boolean isTileOccupied(){

            return true;
        }

        @Override
        public Piece getPiece(){

            return this.pieceOnTile;
        }
    }
}
