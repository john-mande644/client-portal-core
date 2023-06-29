package com.owd.alittlePlaying.booleans;


import junit.framework.*;



import java.util.List;

/**
 * Created by danny on 4/17/2018.
 */
public class xOutOfYTrue {










    public static boolean xNumberTrue(List<Boolean> bools, int minimumTrue ){

        int i = 0;
        for(Boolean b : bools){
            if(b){
                i++;
            }
        }


        return i>= minimumTrue;

    }


}
