package com.owd.core.business.autoship;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrderAuto;
import com.owd.hibernate.generated.OwdOrderAutoItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Sep 29, 2008
 * Time: 11:27:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class RHLAutoshipImport {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args)
    {

        try{
               ResultSet rs = HibernateSession.getResultSet("select * from RHLDATA.rhldata.dbo.autoships order by CUSTEDP asc");
          String oldEDP = "";
             OwdOrderAuto auto = null;
            double subtotal = 0.00;


            while(rs.next())
            {

             if(!(oldEDP.equals(rs.getString("CUSTEDP"))))
             {
                 log.debug("");
                   if(auto!=null)
                   {
                 if (auto.getShipState().equals("CA")) {
                     auto.setSalesTax(new BigDecimal(0.0775d * (subtotal)));
                 } else  if (auto.getShipState().equals("SD")) {
                     auto.setSalesTax(new BigDecimal(0.0600d * (subtotal)));
                 } else {
                     auto.setSalesTax(new BigDecimal(0.00));
                 }

            HibernateSession.currentSession().saveOrUpdate(auto);
          HibernateSession.currentSession().flush();

                       subtotal = 0.00;
                       auto = null;

                   }



                 log.debug("New EDP:"+rs.getString("CUSTEDP"));

                  auto = AutoShipFactory.getNewAutoShip(rs.getInt("client_fkey"));

            auto.setBillName(rs.getString("BFNAME").trim()+" "+rs.getString("BLNAME").trim());
            auto.setBillPhone(rs.getString("BPHONE").trim());
            auto.setBillEmail(rs.getString("BEMAIL").trim());
            auto.setBillAddressOne(rs.getString("BSTREET1").trim());
            auto.setBillAddressTwo(rs.getString("BSTREET2").trim());
            auto.setBillCity(rs.getString("BCITY").trim());
            auto.setBillState(rs.getString("BSTATE").trim());
            auto.setBillZip(rs.getString("BZIP").trim());
            auto.setBillCountry(rs.getString("BCOUNTRY").trim());

            auto.setShipName(rs.getString("SFNAME").trim()+" "+rs.getString("SLNAME").trim());
            auto.setShipPhone("");
            auto.setShipEmail("");
            auto.setShipAddressOne(rs.getString("SSTREET").trim());
            auto.setShipAddressTwo(rs.getString("SSTREET2").trim());
            auto.setShipCity(rs.getString("SCITY").trim());
            auto.setShipState(rs.getString("SSTATE").trim());
            auto.setShipZip(rs.getString("SZIP").trim());
            auto.setShipCountry(rs.getString("SCOUNTRY").trim());

            // auto.setShipMethod(o.getShippingInfo().carr_service);

            auto.setRequirePayment(new Integer(1));
            auto.setShipCost(new BigDecimal(0.00));

            auto.setCreatedBy("IMPORT");
            auto.setCreated(Calendar.getInstance().getTime());
            auto.setStatus(new Integer(0));
            auto.setClientFkey(rs.getInt("client_fkey"));


            auto.setOrderSource("IMPORT");
           // Calendar expected = Calendar.getInstance();

            // expected.setTime(os.post_date);
         //   expected.add(Calendar.MONTH, 1);
                 Date nextd = rs.getDate("nextdate");
                 Date startd = rs.getDate("stopuntildate");
                 if(startd != null)
                 {
                     if(startd.getTime()>nextd.getTime())
                     {
                         nextd=startd;
                     }
                 }

            auto.setNextShipmentDate(nextd);


             auto.setCalendarUnit(rs.getString("intervaltype").trim());
             auto.setShipInterval(rs.getInt("intervalvalue")==0?4:rs.getInt("intervalvalue"));

            auto.setOrderSource("IMPORT");
            auto.setCcNum(rs.getString("cc").trim());
            auto.setCcExpMon(rs.getInt("ccmonth"));
            auto.setCcExpYear(rs.getInt("ccyear"));
            HibernateSession.currentSession().saveOrUpdate(auto);

            OwdOrderAutoItem autoitem = new OwdOrderAutoItem();
            autoitem.setSku(rs.getString("sku"));

            autoitem.setUnitPrice(new BigDecimal(rs.getDouble("price")));
            autoitem.setQuantity(rs.getInt("qty"));

            autoitem.setOwdOrderAuto(auto);
            auto.getOwdOrderAutoItems().add(autoitem);
            HibernateSession.currentSession().save(autoitem);





                     auto.setShipMethod(rs.getString("SHIPMETHOD"));


                 log.debug(">>"+rs.getString("sku")+"/"+rs.getString("DESCRIPTION")+"/"+rs.getString("qty")+"/"+rs.getString("price"));


                     oldEDP = rs.getString("CUSTEDP");
             }   else
             {
                // just a line item
                 log.debug(">>"+rs.getString("sku")+"/"+rs.getString("DESCRIPTION")+"/"+rs.getString("qty")+"/"+rs.getString("price"));

                 OwdOrderAutoItem autoitem = new OwdOrderAutoItem();
                 autoitem.setSku(rs.getString("sku"));

                 autoitem.setUnitPrice(new BigDecimal(rs.getDouble("price")));
                 autoitem.setQuantity(rs.getInt("qty"));

                 subtotal += (rs.getDouble("price")*rs.getDouble("qty"));
                 autoitem.setOwdOrderAuto(auto);
                 auto.getOwdOrderAutoItems().add(autoitem);
                 HibernateSession.currentSession().save(autoitem);

             }





            }

                if(auto!=null)
                   {
                 if (auto.getShipState().equals("CA")) {
                     auto.setSalesTax(new BigDecimal(0.0775d * (subtotal)));
                 } else  if (auto.getShipState().equals("SD")) {
                     auto.setSalesTax(new BigDecimal(0.0600d * (subtotal)));
                 } else {
                     auto.setSalesTax(new BigDecimal(0.00));
                 }

            HibernateSession.currentSession().saveOrUpdate(auto);
          HibernateSession.currentSession().flush();
      HibUtils.commit(HibernateSession.currentSession());

                       subtotal = 0.00;
                       auto = null;

                   }



      HibUtils.commit(HibernateSession.currentSession());
        }   catch(Exception ex)
        {
            try{
      HibUtils.rollback(HibernateSession.currentSession());  } catch(Exception exx){}

            ex.printStackTrace();
        }


    }
}
