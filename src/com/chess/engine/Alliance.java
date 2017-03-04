package com.chess.engine;

import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

/**
 * Created by azkei on 28/02/2017.
 */

//define behaviour in enum
public enum Alliance {

    BLACK {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer,
                                   final BlackPlayer blackPlayer){
            return whitePlayer;
        }
    },
    WHITE{
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer,
                                   final BlackPlayer blackPlayer){
            return blackPlayer;
        }
    };

    //return directionality
    public abstract  int getDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();

    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}
