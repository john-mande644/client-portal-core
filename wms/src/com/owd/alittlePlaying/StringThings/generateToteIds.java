package com.owd.alittlePlaying.StringThings;

/**
 * Created by danny on 9/20/2017.
 */
public class generateToteIds {

    public static void main(String[] args){

        int start = 2500;
        int number = 2000;
        String s = "//tote-";
        String ss = "tote-";
        int times = 1;


        while (number > 0){
            int t = 0;
            while (t<times){
                System.out.println(s+start+"\t"+ss+start);
                t++;
            }

            start++;
            number--;
        }

    }
}
