package com.owd.alittlePlaying.StringThings;

import org.apache.commons.lang.StringUtils;

/**
 * Created by danny on 4/27/2017.
 */
public class parameterTesting {


    public static void main(String[] args){

  /*  test("Hello","v");
        test("Hello","h");
        test("Hello","H");
        test("Hello","");*/

        test2("Hello", new String[]{"v"});
        test2("Hello", new String[]{"h"});
        test2("Hello", new String[]{"H"});
        test2("Hello", new String[0]);
        test2("Hello", new String[]{"v","H"});

    }

    public static void test2(String value, String[] beginFilter){

        if((beginFilter.length>0&& StringUtils.startsWithAny(value,beginFilter))||beginFilter.length==0){
            System.out.println("Success");
        }else{
            System.out.println("no Go");
        }

    }

    public static void test(String value, String begin){

        if((begin.length()>0 && value.startsWith(begin))|| begin.length()==0){

            System.out.println("Success");
        }else{
            System.out.println("no Go");
        }


    }
}
