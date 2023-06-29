package com.owd.connectship.soap.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import java.util.ArrayList;
import java.util.List;

public class MyHandlerResolver implements HandlerResolver {
private final static Logger log =  LogManager.getLogger();

    public List getHandlerChain(PortInfo portInfo) {
        ArrayList<MyHandler> handlerChain = new ArrayList<MyHandler>();
        handlerChain.add(new MyHandler());
        return handlerChain;
    }

}
