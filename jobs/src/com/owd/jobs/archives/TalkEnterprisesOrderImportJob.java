package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.LogableException;
import com.owd.core.WebResource;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;

import java.io.BufferedReader;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: eric
 * Date: Aug 27, 2008
 * Time: 4:01:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class TalkEnterprisesOrderImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception {

        run();


    }
    


    public void internalExecute() {




            try {

                WebResource server = new WebResource("https://ultimatevoicebuilder.com/owd/", WebResource.kGETMethod);

                server.addHeader("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
              //  server.addHeader("accept-encoding","gzip, deflate, sdch");
                server.addHeader("accept-language","en-US,en;q=0.8");
                server.addHeader("cache-control","max-age=0");
                server.addHeader("upgrade-insecure-requests","1");
                server.addHeader("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36");

                BufferedReader response = server.getResource();

                if(response == null)  //ignore connection errors
                {
                    throw new Exception("Unable to get response from talk enterprises server!");
                }



                StringBuffer sb = new StringBuffer();
                int line = 0;
                line = (int) response.read();
                while (line != -1) {
                    sb.append((char)line);
                    line = response.read();
                }
                log.debug("RESPONSE:"+sb.toString());
                if(sb.toString().length()>4 && (!(sb.toString().contains("0 ORDERS"))) && (sb.toString().contains("[ ERROR ]") || (!(sb.toString().contains("SENT"))))){
                    log.debug("RESPONSE:"+sb.toString());

            }


            /* if(1==1){
                 throw new Exception("just testing");
             }*/


            } catch (Exception ex) {
                ex.printStackTrace();
                LogableException l =   new LogableException(ex, ex.getMessage(),
                        "TS:"+Calendar.getInstance().getTimeInMillis(),
                        "504",
                        this.getClass().getName(),
                        LogableException.errorTypes.ORDER_IMPORT);

            } finally {
                HibernateSession.closeSession();
            }

        }





}
