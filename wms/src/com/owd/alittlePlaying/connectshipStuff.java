package com.owd.alittlePlaying;

import com.owd.connectship.soap.*;
import com.owd.connectship.soap.test.MyHandlerResolver;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.ws.handler.HandlerResolver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by danny on 6/16/2016.
 */
public class connectshipStuff {
    private final static Logger log =  LogManager.getLogger();

    static CoreXmlPort amp;

    public static void main(String[] args){

        try{
            AMPServices smp = new AMPServices();
            // Following two
            HandlerResolver myHanlderResolver = new MyHandlerResolver();
            smp.setHandlerResolver(myHanlderResolver);
            log.debug("myHandlerResolver has been set.");
            amp = smp.getAMPSoapService();
            ListCarriersResponse response = amp.listCarriers(new ListCarriersRequest());
            System.out.println(response);
            ListServicesResponse r = amp.listServices(new ListServicesRequest());
            IdentityListResult ir = r.getResult();
            System.out.println(ir);
            IdentityList il = ir.getResultData();
            System.out.println(il);
            System.out.println(il.getItem());
            for(Identity i:il.getItem()){
                System.out.println(i.getName()+ " : "+i.getSymbol());

            }
          /*  DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            String sqlQuery = "execute sp_loadPackageShipLabelData ?";
            HibernateSession.currentSession().flush();
            PreparedStatement ps = HibernateSession.getPreparedStatement(sqlQuery);
            ps.setString(1, "p9608391*20518714*b1");
            ResultSet rs = ps.executeQuery();
            rs.next();*/
           // StringBuilder xmlString = new StringBuilder();

           /* PrintRequest pr = new PrintRequest();
            pr.setShipper(rs.getString("shipper_acct").trim());
            pr.setCarrier(rs.getString("carrier_code"));
            pr.setDocument(rs.getString("label_code"));
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
               System.out.println(new sun.misc.BASE64Encoder().encode(r.getOutput().getBinaryOutput()));
            }
*/







        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
