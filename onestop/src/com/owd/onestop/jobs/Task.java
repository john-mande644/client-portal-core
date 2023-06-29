package com.owd.onestop.jobs;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 3/14/12
 * Time: 4:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class Task  implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new OneStopHourlyJob(), 0, 1, TimeUnit.HOURS);
    }

    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

}


