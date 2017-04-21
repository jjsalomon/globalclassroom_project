package com.frames.resource;


import java.util.ArrayList;

/**
 * Created by azkei on 21/04/2017.
 */

//Singleton Class for moves
public final class MoveBuffer{

    //to ensure first instance only
    private static MoveBuffer firstInstance = null;
    static boolean firstThread = true;

    String sourceTile = null;
    String destinationTile = null;
    String challenger = null;
    String challenged = null;

    //empty constructor
    private MoveBuffer(){}

    //lazy instantiation
    public static MoveBuffer getFirstInstance(){
        if(firstInstance == null){
            if(firstThread){
                firstThread = false;
                try{
                    Thread.currentThread();
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            //Here we sync when the first object is created
            synchronized (MoveBuffer.class){
                //if the first instance isnt needed it isnt created
                if(firstInstance == null){
                    firstInstance = new MoveBuffer();
                }
            }
        }

        return firstInstance;
    }

    //adds the move strings here / sets the data into this buffer
    //each addition overwrites the existing one
    public void addIncomingMove(String readChallenger,
                                String readChallenged,
                                String readSourceTile,
                                String readDestinationTile){
        challenger = readChallenger;
        challenged = readChallenged;
        sourceTile = readSourceTile;
        destinationTile = readDestinationTile;
        System.out.println("Added sourceTile: "+sourceTile+
                " and DestinationTile: "+destinationTile+
                " into the move buffer singleton");
    }


    public void addPlayers(String inChallenger, String inChallenged){
        challenger = inChallenger;
        challenged = inChallenged;
    }

    //function to fetch buffer data
    public String getSourceTile(){return sourceTile;}

    public String getDestinationTile(){return destinationTile;}

    public String getChallenger(){return challenger;}

    public String getChallenged(){return challenged;}

}