package com.owd.core.business.order.externalWarehouse;

import com.owd.core.business.order.externalWarehouse.Vanboxtel.Beans.outboundOrder;
import com.owd.core.business.order.externalWarehouse.Vanboxtel.Beans.vanHeader;
import com.owd.core.business.order.externalWarehouse.Vanboxtel.Beans.vanLine;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import com.thoughtworks.xstream.XStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by danny on 4/24/2017.
 */
public class externalWarehouseUtilites {



    public static void main(String[] args){

        System.out.println(getVanOutboundXml("12294120"));

    }



    public static String getVanOutboundXml(String orderId){
        String s = "";
        try{
            outboundOrder vanOrder = new outboundOrder();

            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(orderId));
            vanHeader header = new vanHeader();
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            DateFormat dfDate = new SimpleDateFormat("yyyyMMdd");

            header.setTIMESTAMP(df.format(Calendar.getInstance().getTime()));
            header.setCLIENT(order.getClientFkey());
            OwdOrderShipInfo sInfo = order.getShipinfo();
            header.setDELCODECL(sInfo.getShipCompanyName());
            header.setDELNAME(sInfo.getShipFirstName() + " " + sInfo.getShipLastName());
            header.setDELADDRESS(sInfo.getShipAddressOne());
            header.setDELADDRESS2(sInfo.getShipAddressTwo());
            header.setDELZIP(sInfo.getShipZip());
            header.setDELCITY(sInfo.getShipCity());
            header.setCOUNTRY(sInfo.getShipCountry());
            header.setREFERENCE1(order.getOrderNum());
            header.setREFERENCE2(order.getOrderRefnum());
            header.setDELDATE("20170424");
            header.setCOLLECT("j");
            header.setREMARK("none");
            header.setEMAIL(sInfo.getShipEmailAddress());
            header.setPHONE(sInfo.getShipPhoneNum());
            header.setURLINVOICE("http://test.com/invoice.pdf");

            vanOrder.setHEADER(header);

            List<vanLine> lines = new ArrayList<vanLine>();
            for(OwdLineItem item:order.getLineitems()){
                vanLine vl = new vanLine();
                vl.setARTICLECODE(item.getOwdInventory().getInventoryNum());
                vl.setORDERED(item.getQuantityActual());
                lines.add(vl);


            }
            vanOrder.setOrderLines(lines);

            XStream x =outboundOrder.getXStream();


            s = "<?xml version=\"1.0\"?>\n<MAIN>\n" + x.toXML(vanOrder) + "\n</MAIN>";





        }catch (Exception e){
            e.printStackTrace();
        }



        return s;
    }
}
