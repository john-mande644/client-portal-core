package com.owd.dc.manifest.ExternalApis.OWDEasyPost;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by danny on 6/9/2017.
 */
public class moreLyftStuff {



    public static void main(String[] args){
        List<Integer> l = new ArrayList<Integer>();
        l.add(12449725);
        l.add(12449768);
        l.add(12449771);
        l.add(12449772);
        l.add(12449776);
        l.add(12449777);
        l.add(12449778);
        l.add(12449780);
        l.add(12449781);
        l.add(12449785);
        l.add(12449788);
        l.add(12449789);
        l.add(12449790);
        l.add(12449792);
        l.add(12449793);
        l.add(12449794);
        l.add(12449795);
        l.add(12449797);
        l.add(12449799);
        l.add(12449801);
        l.add(12449802);
        l.add(12449808);
        l.add(12449811);
        l.add(12449814);
        l.add(12449815);
        l.add(12449817);
        l.add(12449819);
        l.add(12449820);
        l.add(12449821);
        l.add(12449822);
        l.add(12449826);
        l.add(12449827);
        l.add(12449830);
        l.add(12449832);
        l.add(12449833);
        l.add(12449834);
        l.add(12449836);
        l.add(12449837);
        l.add(12449840);
        l.add(12449842);
        l.add(12449843);
        l.add(12449844);
        l.add(12449845);
        l.add(12449846);
        l.add(12449853);
        l.add(12449857);
        l.add(12449858);
        l.add(12449860);
        l.add(12449863);
        l.add(12449864);
        l.add(12449865);
        l.add(12449866);
        l.add(12449870);
        l.add(12449872);
        l.add(12449874);
        l.add(12449875);
        l.add(12449877);
        l.add(12449878);
        l.add(12450043);
        l.add(12450044);
        l.add(12450045);
        l.add(12450046);
        l.add(12450048);
        l.add(12450052);
        l.add(12450053);
        l.add(12450054);
        l.add(12450055);
        l.add(12450057);
        l.add(12450060);
        l.add(12450061);



        try{
            runThis(l);
        }catch (Exception e){
            e.printStackTrace();
        }

    }





    public static void runThis(List<Integer> l) throws  Exception{


        ExecutorService exec = Executors.newFixedThreadPool(9);
        for(Integer i:l){
           exec.submit(new SingleLyftShipper(i,2));
         //  exec.submit(new SingleLyftRater(i));
        }
        exec.shutdown();
        System.out.println("waiting executor");
        exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        System.out.println("done");


    }
}
