package com.frames.resource;

import java.util.ArrayList;

/**
 * Created by azkei on 11/04/2017.
 * This class acts as a global shared resource Singleton
 */
public final class UserOnline {

    private static UserOnline firstInstance = null;
    private ArrayList<String> onlineBuff = new ArrayList<>();

    static boolean firstThread = true;
    private UserOnline(){}

    //lazy instantiation
    public static UserOnline getInstance(){
        if(firstInstance == null){
            //This is here to test what happens if threads try
            //to create instances of this class
            if(firstThread){
                firstThread = false;
                try{
                    Thread.currentThread();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //Here we just use synchronized when the first object
            //is created
            synchronized (UserOnline.class){
                //if the first instance isnt needed it isnt created
                //This is known as lazy isntanciation
                if(firstInstance == null){
                    firstInstance = new UserOnline();
                }
            }
        }

        //Under either circumstance this returns the instance
        return  firstInstance;
    }

    public void addBuffer(String data){
        onlineBuff.add(data);
    }

    public void removeBuffer(String data) {onlineBuff.remove(data);}

    public ArrayList<String> getOnlineBuff(){
        return onlineBuff;
    }

    public void clearBuffer(){ onlineBuff.clear();}


}
