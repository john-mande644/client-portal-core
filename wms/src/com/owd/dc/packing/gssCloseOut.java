package com.owd.dc.packing;

import com.owd.dc.manifest.gssEndOfDay;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jun 9, 2010
 * Time: 9:04:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class gssCloseOut {
    public static String getXml(String gssAction, String dispatchId,String facility){
        String result = "";

          try{
              Session sess = HibernateSession.currentSession();
             if(gssAction.equals("list")) result = getDispsatchList(sess,facility);
             if (gssAction.equals("closeout")) result = closeoutGss(sess,facility);
             if (gssAction.equals("getreport")) result = getReportForDispatch(dispatchId,sess,facility);

           } catch (Exception e){

                return "Error: "+ e.getMessage();

              }



           if (result.equals("")) result = "Error, something not quite right";
        return result;
    }
    private static String getReportForDispatch(String dispatchId, Session sess,String facility) throws Exception{
          gssEndOfDay gss = new gssEndOfDay(facility);
          return gss.getReportsInMapJustLinksXML(dispatchId,sess);



    }
    private static String closeoutGss(Session sess,String facility) throws Exception{
       gssEndOfDay gss = new gssEndOfDay(facility);
        return gss.closeDistatchandGetReportsXml(sess);

    }
    private static String getDispsatchList(Session sess, String facility) throws Exception{
        gssEndOfDay gss = new gssEndOfDay(facility);
        return gss.getDispatchIdXml(sess);


    }
}
