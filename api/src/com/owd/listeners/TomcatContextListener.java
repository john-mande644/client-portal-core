package com.owd.listeners;


import com.google.gson.Gson;
import com.owd.hibernate.HibernateSession;
import com.squareup.okhttp.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TomcatContextListener implements ServletContextListener {

    private final String URL = "https://apibalancer.owd.com/receivers";
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent){
        try{
            boolean hibStatus =  HibernateSession.currentSession().isConnected();
            System.out.println("Hibernate Connected: " + hibStatus);
            if(!sendCard(new ServerCard())){
                throw new Exception("Load Balancer returned an error");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent){

    }

    private boolean sendCard(ServerCard card)throws IOException {
        boolean result = true;
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),card.toJson());
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if(response.code()!=200){
            result = false;
        }
        return result;
    }

    private class ServerCard{
        private String name;
        private String address;
        private String path = "/api/api.jsp";
        private int port = 8080;
        private int max_connections = 20;
        private int count = 0;
        private boolean testing = false;

        public ServerCard(){
            try(final DatagramSocket socket = new DatagramSocket()){
                System.out.println("creating server card");
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                address = socket.getLocalAddress().getHostAddress();
                System.out.println("IP address is: " + address);
                name = InetAddress.getLocalHost().getHostName();
                System.out.println("Hostname is: " + name);
                if(System.getProperty("com.owd.environment") != null && System.getProperty("com.owd.environment").equals("test")){
                    testing=true;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        public String toJson(){
            Gson gson = new Gson();
            try {
                return gson.toJson(this);
            }catch(Exception e){
                e.printStackTrace();
                return "";
            }
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getMax_connections() {
            return max_connections;
        }

        public void setMax_connections(int max_connecitons) {
            this.max_connections = max_connecitons;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isTesting() {
            return testing;
        }

        public void setTesting(boolean testing) {
            this.testing = testing;
        }
    }
}