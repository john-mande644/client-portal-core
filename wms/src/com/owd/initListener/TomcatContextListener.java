package com.owd.initListener;

import com.owd.hibernate.HibernateSession;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.net.DatagramSocket;
import java.net.InetAddress;

@WebListener
public class TomcatContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent){
        try(final DatagramSocket socket = new DatagramSocket()){
            boolean hibStatus =  HibernateSession.currentSession().isConnected();
//            OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class,55);
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            String ip = socket.getLocalAddress().getHostAddress();
            //          System.out.println("Loaded client: " + client.getCompanyName());
            System.out.println("IP address is: " + ip);
            System.out.println("Hostname is: " + InetAddress.getLocalHost().getHostName());
            System.out.println("Hibernate Connected: " + hibStatus);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent){

    }
}
