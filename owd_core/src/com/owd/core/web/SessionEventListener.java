package com.owd.core.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Apr 12, 2005
 * Time: 9:55:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class SessionEventListener  implements HttpSessionListener {
private final static Logger log =  LogManager.getLogger();

        private static int activeSessions = 0;
        private static Map sessionMap = new TreeMap();

        public void sessionCreated(HttpSessionEvent se) {
                sessionMap.put(se.getSession().getId(),se.getSession());
                activeSessions++;
        }

        public void sessionDestroyed(HttpSessionEvent se) {
                if(activeSessions > 0)
                        activeSessions--;

            sessionMap.remove(se.getSession().getId());
        }

        public static int getActiveSessionCount() {
                return activeSessions;
        }
     public static Collection getActiveSessions() {
                return sessionMap.values();
        }

    public static HttpSession getActiveSessionForID(String id) {
                return (HttpSession) sessionMap.get(id);
        }
}
