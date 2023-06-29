package com.owd.web;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperExportManager;
import com.owd.hibernate.HibernateSession;
import com.owd.core.Mailer;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 3, 2006
 * Time: 10:15:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class OneOff {
private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args)
    {



        try{


            ResultSet rs = HibernateSession.getResultSet("select bill_email_address from owd_order join owd_line_item on order_id=order_fkey\n" +
                    "where \n" +
                    "client_fkey in (401,402) and is_backorder=1 and is_future_ship=0 and quantity_request>0 and inventory_num='BN300'\n" +
                    "and bill_email_address<>'' and bill_email_address<>'0001'");
            while(rs.next())
            {
                try
                {
                StringBuffer sb = new StringBuffer();
                sb.append("\r\nTo our Valued Customers;\n" +
                        " \n" +
                        "We wanted to make you aware that we are currently experiencing a delay in shipping Dr. Cherry's Basic Nutrition Support supplements.  We expect to begin shipping again by the end of October.\n" +
                        " \n" +
                        "We apologize for any inconvenience this backorder may cause you and we want you to rest assured that we are working very hard to ship you this great product as quickly as possible.\n" +
                        " \n" +
                        "Thank you for your patronage,\n" +
                        "\n" +
                        "Natural Alternatives Customer Service");

             //   Mailer.sendMail("An important update regarding your Dr. Cherry order",sb.toString(),rs.getString(1),"drcherryproducts@owd.com");
                }catch(Exception exxx)
                {
                    exxx.printStackTrace();
                }
            }

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }                        finally
        {
            HibernateSession.closeSession();
        }
    }
}
