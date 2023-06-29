package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.WebResource;
import com.owd.jobs.OWDStatefulJob;

import java.io.BufferedReader;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Nov 13, 2008
 * Time: 10:55:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class DrCherryCatalogRequestJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args)
    {
        run();
    }
    public void internalExecute()
    {

        try
        {
        WebResource server = new WebResource("http://www.drcherry.com/downloads/catalogs.asp",WebResource.kPOSTMethod);



                    StringBuffer sb = new StringBuffer();
                    server.addParameter("fordate", OWDUtilities.getSQLDateNoTimeForToday());


                    //log.debug(sb.toString());

                    BufferedReader rdr = server.getResource();

            StringBuffer sb2 = new StringBuffer();

            int line = 0;

                line = (int)rdr.read();

                while (line != -1)

                {
                    //Uncomment the next block if displaying result in HTML
                    sb2.append((char)line);
                    line = rdr.read();

                }

                if(sb2.toString().indexOf("error")>=0)
                {

                }else
                {
                    Mailer.sendMailWithAttachment("Dr Cherry Website catalog requests for "+OWDUtilities.getYYYYMMDDDateForToday(),"Attached is the file containing the new catalog request from www.drcherry.com for "+OWDUtilities.getMMDDYYSlashedDateForToday()
                            +"\n\nPlease contact owditadmin@owd.com with any problems or questions about this file.","",sb2.toString().getBytes(),"drcherrycatalog"+OWDUtilities.getYYYYMMDDDateForToday(),"text/plain");
                    log.debug(sb2.toString());
                }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
}
}
