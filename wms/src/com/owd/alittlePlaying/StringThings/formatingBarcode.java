package com.owd.alittlePlaying.StringThings;

/**
 * Created by danny on 11/17/2017.
 */
public class formatingBarcode {

    public static void main(String args[]){
       /* StringBuilder b = new StringBuilder();
        b.append("92001901000704181853394748");
        int i = 4;

        while (i < b.length()){
            b.insert(i," ");
            i = i + 5;
        }

        System.out.println(b.toString());*/
       System.out.println(String.format("%019d",Long.parseLong("68904"+"12345678")));

    }
}
