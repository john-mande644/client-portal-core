package com.owd.dc.timeclock;

import com.owd.dc.setup.selectList;
import com.owd.hibernate.HibernateSession;
import com.owd.core.WebResource;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Mar 13, 2008
 * Time: 4:10:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class timeclockUtil {

    public static void main(String[] args){
        try{
            timeclockForm tcf = new timeclockForm();
            tcf.setJobCode("9008");
            tcf.setTimeClockId("51");
            String ip = "172.16.66.255";
           System.out.println(doPunch(getPunchString(tcf,ip)));
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    public static String doPunch(String punch){
         String orderDownloadUrl = "http://timeclock.owd.com/qqest/NetClock/proccessNetClock.asp?newswipe="+punch;
     StringBuffer sb = new StringBuffer();
      try{
       //   WebResource server = new WebResource("http://timeclock.owd.com/clock.htm", WebResource.kPOSTMethod);
            WebResource server = new WebResource("http://timeclock.owd.com/qqest/Login/LoginCheck.asp", WebResource.kPOSTMethod);

          server.addParameter("username ","net1");
			server.addParameter("Password ","net1");
			server.addParameter("companycode ","owd");
        BufferedReader response =     server.getResource();
         int line = 0;
            line = (int) response.read();
            while (line != -1) {
                sb.append((char)line);
                line = response.read();
            }
            server.clearParameters();
          System.out.println(server.url);
           System.out.println(sb.toString());
          sb = new StringBuffer();
       //  server = new WebResource(orderDownloadUrl, WebResource.kGETMethod);
         server.url = "http://timeclock.owd.com/qqest/NetClock/processNetClock.asp";
           System.out.println(server.url);
          server.addParameter("newswipe","\""+punch+"\"");
             response = server.getResource();
              System.out.println(server.url);


            line = 0;
            line = (int) response.read();
            while (line != -1) {
                sb.append((char)line);
                line = response.read();
            }

      }catch(Exception e){
          e.printStackTrace();
      }

       return  sb.toString();
    }
    public static String getPunchString(timeclockForm tcf, String ip){
    StringBuffer sb = new StringBuffer();
         sb.append("<!--");
        sb.append(ip);
        sb.append("--><!--Job=1,");
        sb.append(tcf.getTimeClockId());
        sb.append(",");
        sb.append(timeclockUtil.getMMDDYYSlashed());
        sb.append(",");
        sb.append(timeclockUtil.getHHMMTime());
        sb.append(",");
        sb.append(tcf.getJobCode());
        sb.append(",0,0,0,I-->");

       return sb.toString();
    }

    public static List<selectList> getJobCodes() throws Exception{
        List<selectList> codes = new ArrayList();
         String sql = "select value, display from app_data where project = 'handhelds' and variable = 'job' order by value";

        ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(),sql);

        while(rs.next()){

            selectList sl = new selectList();
            sl.setAction(rs.getString("value") );
            sl.setDisplay(rs.getString("display"));
            codes.add(sl);

        }


        return codes;


    }
     static String getMMDDYYSlashed() {
     SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");


     return df.format(Calendar.getInstance().getTime());



 }
    static String getHHMMTime() {
     SimpleDateFormat df = new SimpleDateFormat("HH:mm");


     return df.format(Calendar.getInstance().getTime());



 }
}
