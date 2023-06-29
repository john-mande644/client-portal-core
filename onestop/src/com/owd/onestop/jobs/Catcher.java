package com.owd.onestop.jobs;

/**
 * Created by stewartbuskirk1 on 2/23/14.
 */
public class Catcher  implements Thread.UncaughtExceptionHandler {
    public void uncaughtException(Thread t, Throwable e) {
        // Write the custom logic here
        System.out.println("UNCAUGHT EXCEPTION");
        e.printStackTrace();
    }
}