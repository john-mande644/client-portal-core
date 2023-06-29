package com.owd.core.business.order.externalWarehouse.Vanboxtel.Beans;

import com.thoughtworks.xstream.XStream;

import java.util.List;

/**
 * Created by danny on 4/24/2017.
 */
public class outboundOrder {

   private vanHeader HEADER;
    private List<vanLine> orderLines;


    public vanHeader getHEADER() {
        return HEADER;
    }

    public void setHEADER(vanHeader HEADER) {
        this.HEADER = HEADER;
    }

    public List<vanLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<vanLine> orderLines) {
        this.orderLines = orderLines;
    }

    public static XStream getXStream(){
        XStream x = new XStream();
        x.alias("OUTBOUND",outboundOrder.class);
        x.addImplicitCollection(outboundOrder.class,"orderLines");
        x.alias("LINES",vanLine.class);

        return x;
    }
}
