package com.owd.web.callcenter;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.ftp.FtpBean;
import com.owd.core.ftp.FtpException;
import com.owd.hibernate.HibernateSession;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Nov 19, 2007
 * Time: 10:21:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class santaCallAction extends LookupDispatchAction
{
private final static Logger log =  LogManager.getLogger();
       static String ftpHost = "wiki.owd.com";
    static String ftpUser = "calls";
    static String ftpPass = "owdcalls";
      protected Map getKeyMethodMap() {
        log.debug("getKeyMethodMap invoked..");
        Map map = new HashMap();
        map.put("button.search", "search");
        map.put("button.download", "down");


        return map;
    }

    public ActionForward  down(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            santaCallForm sf = (santaCallForm) form;
            log.debug("ssssssssssssssssssssssssssssssssssssssssssssssss");

                //downloadCall(response,sf.getCallId());
             byte[] data = null;
        try {
            data = getSantaCall(sf.getCallId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        if (data.length == 0) throw new Exception("File does not exist");
        response.setContentType("audio/x-wav");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + sf.getCallId() + ".mp3\"");

        log.debug("datalen=" + data.length);
        response.setContentLength(data.length);
        response.getOutputStream().write(data);

              log.debug("after xxxxxxxxxxxxxxxxxxxxxxxxxxx");

          return null;
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());


            return mapping.findForward("error");

        }

    }

     public ActionForward search(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
           santaCallForm sf = (santaCallForm) form;
            getCallSearchResults(sf);
           return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            return mapping.findForward("error");

        }
    }

    public static void main(String[] args)  throws Exception
    {
        santaCallForm sf = new santaCallForm();
        sf.setPhoneNumber("17324626278");
        getCallSearchResults(sf);


    }
    private static void getCallSearchResults(santaCallForm sf)
            throws Exception
    {
        if(sf.getPhoneNumber().length()==10)sf.setPhoneNumber("1"+sf.getPhoneNumber());
        log.debug("lalslsllsallldlalldlldlalldla");
        //todo search calls
        log.debug("phone NUmber: "+sf.getPhoneNumber());
        sf.setPhoneNumber(sf.getPhoneNumber().replaceAll("-*\\s*",""));
        //  if(sf.getPhoneNumber().length()<6) throw new Exception("Phone number is not long enough");
        String sql= "select call_id, phone_number from zzSantaCalls where phone_number like ? and insert_date > '2008-1-1'";
        PreparedStatement ps = HibernateSession.getPreparedStatement(sql);

        ps.setString(1,"%"+sf.getPhoneNumber()+"%");
        ResultSet rs = ps.executeQuery();

        int cols = rs.getMetaData().getColumnCount();
        List colList = new ArrayList();


        RowSetDynaClass displayrsc = new RowSetDynaClass(rs, false);
        log.debug("found "+displayrsc.getRows().size()+" calls!");
        sf.setSearchResults(displayrsc);
        sf.setColumns(colList);
    }

    public ActionForward unspecified(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
          
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            return mapping.findForward("error");

        }
    }

    private static byte[] getSantaCall(String callId) throws FtpException, IOException {

        FtpBean ftp = new FtpBean();

        ftp.ftpConnect(ftpHost, ftpUser, ftpPass);
        ftp.setDirectory("/calls/outbound/santa/2008/");




        byte[]   data = ftp.getBinaryFile( callId + ".mp3");


        log.debug("datalen=" + data.length);

        return data;

    }
   private static void downloadCall(HttpServletResponse resp, String callId) throws Exception {


        byte[] data = null;
        try {
            data = getSantaCall(callId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        if (data.length == 0) throw new Exception("File does not exist");
        resp.setContentType("audio/x-wav");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + callId + ".mp3\"");

        log.debug("datalen=" + data.length);
        resp.setContentLength(data.length);
        resp.getOutputStream().write(data);

    }
}
