package com.owd.jobs.jobobjects;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk1
 * Date: 10/20/13
 * Time: 12:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class JobThreadInfo {

    public Thread jobThread;

    public void stopJobThread()
    {
        final Thread t = jobThread;


        if (t != null && t.isAlive() && !t.isInterrupted()){
            try{
                new Thread(){
                    public void run(){
                        try{
                            t.sleep(3000);//SLEEP INSIDE THE NEW THREAD
                        }
                        catch(InterruptedException ex){}
                    }
                }.start();

                t.interrupt();//INTERRUPT OUTSIDE THE NEW STARTED THREAD
            }
            catch(Exception e){}
        }
    }


}
