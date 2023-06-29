package com.owd.dc.OnDemandPrinting.BollAndBranch;

import com.thoughtworks.xstream.XStream;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 7/10/14
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class giftMessage {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public static XStream getXStream(){
        XStream x = new XStream();
        x.alias("giftmessage",giftMessage.class);

        return x;
    }
}
