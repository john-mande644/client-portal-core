package com.owd.alittlePlaying.errorHandling;

import com.owd.LogableException;

/**
 * Created by danny on 1/9/2018.
 */
public class loggingTest {

    public static void main(String[] args){

        try{
            throw new LogableException("This is just a test", "ORderNum","55","sss", LogableException.errorTypes.UNDEFINED_ERROR);

        }catch (Exception e){

        }

    }
}
