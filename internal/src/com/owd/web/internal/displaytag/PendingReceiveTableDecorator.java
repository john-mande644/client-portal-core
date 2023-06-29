package com.owd.web.internal.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.Receive;
import com.owd.hibernate.generated.ScanReceive;
import org.apache.commons.beanutils.DynaBean;
import org.displaytag.decorator.TableDecorator;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 11, 2006
 * Time: 3:41:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class PendingReceiveTableDecorator extends TableDecorator {
private final static Logger log =  LogManager.getLogger();

    public String getDelete()  {
      try{
       DynaBean bean = (DynaBean) getCurrentRowObject();

            if (bean == null) return "";

          return "<A HREF=\"" + ((HttpServletRequest) getPageContext().getRequest()).getContextPath() +
                            "/warehouse/admin/receives/?deleteId="+ bean.get("id")+"\" onclick=\"javascript:return confirm('Are you sure you want to delete "+bean.get("id")+"?')\">Remove Pending Receive</a>";
      } catch(Exception e){
          e.printStackTrace();
          return "ERROR";
      }

    }

    public String getSla()  {
        try{
            DynaBean bean = (DynaBean) getCurrentRowObject();

            if (bean == null) return "";

            return "<A HREF=\"" + ((HttpServletRequest) getPageContext().getRequest()).getContextPath() +
                    "/warehouse/admin/receives/?deleteId="+ bean.get("id")+"\" onclick=\"javascript:return confirm('Are you sure you want to delete "+bean.get("id")+"?')\">Remove Pending Receive</a>";
        } catch(Exception e){
            e.printStackTrace();
            return "ERROR";
        }

    }


    public String getScans() {
        try {

            DynaBean bean = (DynaBean) getCurrentRowObject();

            if (bean == null) return "";

            //log.debug("id=" + bean.get("id"));

            Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class, new Integer("" + bean.get("id")));
            StringBuffer sb = new StringBuffer();

            Collection scans = rcv.getScanDocs();


            if (scans != null) {
                Iterator it = scans.iterator();
                while (it.hasNext()) {
                    ScanReceive sr = (ScanReceive) it.next();

                    String typeicon = "text.gif";
                    if (sr.getName().toUpperCase().endsWith(".PDF")) {
                        typeicon = "acrobat.gif";
                    }
                    sb.append("<A HREF=\"" + ((HttpServletRequest) getPageContext().getRequest()).getContextPath() +
                            "/getscan/receive?auth=" + URLEncoder.encode(OWDUtilities.encryptData(rcv.getId()
                            + "/" + sr.getName() + "/" + rcv.getClientFkey()), "UTF-8") + "\"><IMG SRC=\"" +
                            ((HttpServletRequest) getPageContext().getRequest()).getContextPath() + "/images/" + typeicon + " \" border=\"0\">&nbsp;" + sr.getName() + "</A>" +
                            (it.hasNext() ? "<BR>" : ""));


                }
            }


            return sb.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR";
        }
    }


}
