package com.chess.engine;

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
    };

    //return directionality
    public abstract  int getDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();
}
