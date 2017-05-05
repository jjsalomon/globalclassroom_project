package com.frames.resource;

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
    String sendTo = null;
    String sendFrom = null;
    String challenger = null;
    String challenged = null;
    static boolean hasboardupdated;

    //empty constructor
    private MoveBuffer(){

        hasboardupdated =false;
    }
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
    public void addIncomingMove(String to, String from,
                                String readSourceTile,
                                String readDestinationTile){
        //helps alternation
        sendTo = from;
        sendFrom = to;
        sourceTile = readSourceTile;
        destinationTile = readDestinationTile;
        System.out.println("Added sourceTile: "+sourceTile+
                " and DestinationTile: "+destinationTile+
                " into the move buffer singleton");
    }

    public void setChallenge(String Challenger,String Challenged)
    {
        challenger = Challenger;
        challenged = Challenged;

    }
    public void addPlayers(String to, String from){
        sendTo = to;
        sendFrom = from;
    }

    public void setSwitchboolean(boolean switchE)
    {

        hasboardupdated =switchE;
    }

    public static boolean getBoolean()
    {
        return hasboardupdated;
    }

    //function to fetch buffer data
    public String getSourceTile(){return sourceTile;}

    public String getDestinationTile(){return destinationTile;}

    public String getSend(){return sendTo;}

    public String getFrom(){return sendFrom;}

    public  String getChallenger(){return challenger;}
    public  String getChallenged(){return  challenged;}

}