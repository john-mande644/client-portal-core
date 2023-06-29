package com.owd.dc.manifest.api.internal;

import com.owd.connectship.soap.*;
import com.owd.connectship.soap.test.MyHandlerResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.ws.handler.HandlerResolver;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 4/28/2017.
 */
public class AmpLabelUtilites {
    private final static Logger log =  LogManager.getLogger();

    static CoreXmlPort amp;


    public static void main(String[] args){



    }


    public static List<byte[]> getUSPSCN22ForSurePost(ResultSet rs) throws Exception{
        List<byte[]> labels = new ArrayList<byte[]>();
        AMPServices smp = new AMPServices();
        // Following two
        HandlerResolver myHanlderResolver = new MyHandlerResolver();
        smp.setHandlerResolver(myHanlderResolver);

        amp = smp.getAMPSoapService();
        PrintRequest pr = new PrintRequest();
        pr.setShipper(rs.getString("shipper_acct").trim());
        pr.setCarrier(rs.getString("carrier_code"));
        pr.setDocument("CONNECTSHIP_UPS_SUREPOST_CN22.STANDARD");
        PrintItemList pl = new PrintItemList();
        pl.getMsn().add(Integer.parseInt(rs.getString("msn")));
        pr.setItemList(pl);
        pr.setDestination("response");
        StockDescriptor stock = new StockDescriptor();
        stock.setSymbol("THERMAL_LABEL_8");
        pr.setStock(stock);
        pr.setOutput("Zebra.ZebraZ4Mplus");


        PrintResponse printResp = amp.print(pr);

        System.out.println(printResp.getResult().getMessage());
        java.util.List<DocumentResult> results = printResp.getResult().getResultData().getItem();

        for(DocumentResult r:results){
            labels.add(r.getOutput().getBinaryOutput());

        }


        return labels;
    }
}
