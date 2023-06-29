package com.owd.intranet.adjustments.view;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.RowSetDynaClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.intranet.forms.viewAdjustForm;
import com.owd.intranet.beans.selectList;
import com.owd.intranet.util;
import com.owd.hibernate.HibernateSession;


import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 16, 2006
 * Time: 11:34:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class searchAdjustAction extends Action {
private final static Logger log =  LogManager.getLogger();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            viewAdjustForm af = (viewAdjustForm) form;
            StringBuffer sb = new StringBuffer();
                           //fisrt part of query
                           if(af.getField().equals("Inventory Id")||af.getField().equals("Inventory Num")) {
                               //log.debug("setting query with no items");
                             sb.append("SELECT     owd_receive.receive_id, owd_receive.transaction_num, owd_receive.receive_date,  owd_receive.type, owd_receive.receive_user, \n" +
                                   "                       owd_receive.is_void as 'void',ISNULL(owd_receive.facility_code,'UNKNOWN') as 'facility_code'\n" +
                                   "FROM         owd_receive INNER JOIN\n" +
                                   "                      owd_receive_item ON owd_receive.receive_id = owd_receive_item.receive_fkey\n" +
                                   "WHERE     (owd_receive.client_fkey = " + af.getFkey() + ")");
                           }else{
                           sb.append("SELECT     owd_receive.receive_id, owd_receive.transaction_num, owd_receive.receive_date,  owd_receive.type, owd_receive.receive_user, \n" +
                                   "                      COUNT(owd_receive_item.receive_item_id) AS 'Items', owd_receive.is_void as 'void',ISNULL(owd_receive.facility_code,'UNKNOWN') as 'facility_code'\n" +
                                   "FROM         owd_receive INNER JOIN\n" +
                                   "                      owd_receive_item ON owd_receive.receive_id = owd_receive_item.receive_fkey\n" +
                                   "WHERE     (owd_receive.client_fkey = " + af.getFkey() + ")");
                           }
                               //getfield info and add it to the query

                           if (!af.getField().equals("All")) {
                               String crit = (String) util.getFeilds().get(af.getField());
                               if (af.getType().equals("contains")) {
                                   sb.append("AND " + crit + " LIKE '%" + af.getData() + "%'");
                               }
                               if (af.getType().equals("is")) {
                                   sb.append("AND " + crit + "= '" + af.getData() + "' ");
                               }
                           }
                           //check and add dates to query
                              //log.debug(af.getDateRes());
                           if ("yes".equals(af.getDateRes())) {
                               sb.append("AND (owd_receive.receive_date BETWEEN CONVERT(DATETIME, '" + af.getFromDate() + " 00:00:00', 102) AND CONVERT(DATETIME, \n" +
                                       "                      '" + af.getToDate() + " 00:00:00', 102)) \n");
                           }else{
                               af.setToDate("");
                               af.setFromDate("");
                           }
                           sb.append("GROUP BY owd_receive.receive_id, owd_receive.transaction_num, owd_receive.receive_user, owd_receive.receive_date, owd_receive.type, owd_receive.is_void,owd_receive.facility_code \n"+
                                   "ORDER BY owd_receive.receive_date desc");

                           //log.debug(sb.toString());
                           ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), sb.toString());


                           /* ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "SELECT     owd_receive.receive_id, owd_receive.transaction_num, owd_receive.receive_date,  owd_receive.type, owd_receive.receive_user, \n" +
                                    "                      COUNT(owd_receive_item.receive_item_id) AS 'Items', owd_receive.is_void as 'void'\n" +
                                    "FROM         owd_receive INNER JOIN\n" +
                                    "                      owd_receive_item ON owd_receive.receive_id = owd_receive_item.receive_fkey\n" +
                                    "WHERE     (owd_receive.client_fkey = "+clientFkey+") AND (owd_receive.receive_date BETWEEN CONVERT(DATETIME, '"+fromDate+" 00:00:00', 102) AND CONVERT(DATETIME, \n" +
                                    "                      '"+toDate+" 00:00:00', 102)) \n" +
                                    "GROUP BY owd_receive.receive_id, owd_receive.transaction_num, owd_receive.receive_user, owd_receive.receive_date, owd_receive.type, owd_receive.is_void");*/
                           int cols = rs.getMetaData().getColumnCount();
                           List colList = new ArrayList();

                           for (int i = 1; i <= cols; i++) {
                               selectList sl = new selectList();
                               sl.setAction(rs.getMetaData().getColumnName(i).toString());
                               colList.add(sl);
                           }

                           //log.debug("getting dynaset");
                           RowSetDynaClass displayrsc = new RowSetDynaClass(rs, false);
                           //log.debug("got dynaset");

                           request.getSession(true).setAttribute("receiverp", displayrsc);
                           request.getSession(true).setAttribute("columlist", colList);

                           request.getSession(true).setAttribute("rec",null);


            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("error");

        }
    }
}
