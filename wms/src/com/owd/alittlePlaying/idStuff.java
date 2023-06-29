package com.owd.alittlePlaying;

import com.owd.core.dbUtilities;

/**
 * Created by danny on 12/3/2016.
 */
public class idStuff {

    public static void main(String args[]){

        try{
            System.out.println(dbUtilities.getNextIDSession("Receive"));
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
