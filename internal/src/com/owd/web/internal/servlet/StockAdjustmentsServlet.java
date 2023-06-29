package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdReceive;
import com.owd.web.internal.navigation.UIUtilities;
import com.owd.web.internal.warehouse.admin.adjustments.AdjustmentBean;
import com.owd.web.internal.warehouse.admin.adjustments.AdjustmentUtilities;
import com.owd.web.internal.warehouse.admin.adjustments.selectList;
import org.apache.commons.beanutils.RowSetDynaClass;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 14, 2006
 * Time: 1:35:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class StockAdjustmentsServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();

    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);

    }

    public void destroy() {
        super.destroy();

    }

    //GET requests not supported
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        try {
            doPost(req, resp);

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException {
        AdjustmentBean adbean = null;
        if (null == req.getSession(true).getAttribute("adbean")) {
            adbean = new AdjustmentBean();
        } else {
            adbean = (AdjustmentBean) req.getSession(true).getAttribute("adbean");
        }
        String action = null;
        if (null != req.getParameter("action")) {
            //log.debug("SettingAction");
            action = req.getParameter("action").toString();
        }
        //log.debug(action);
        try {
            if (action == null) {
                 Enumeration em = req.getSession(true).getAttributeNames();
                while (em.hasMoreElements()) {
                    req.getSession(true).setAttribute((String) em.nextElement(), null);
                }
                UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, "Sku Adjustments", req);
                req.getSession(true).setAttribute("adbean", new AdjustmentBean());
                req.setAttribute("clientList", AdjustmentUtilities.getClientList());
                req.getRequestDispatcher("adjustments/index2.jsp").forward(req, response);

                //search
            } else if (action.equals("search")) {
                Enumeration em = req.getSession(true).getAttributeNames();
                while (em.hasMoreElements()) {
                    req.getSession(true).setAttribute((String) em.nextElement(), null);
                }
                String clientFkey = (String) req.getParameter("clientFkey");
                String toDate = (String) req.getParameter("toDate");
                String fromDate = (String) req.getParameter("fromDate");
                String field = (String) req.getParameter("field");
                String type = (String) req.getParameter("type");
                String dateRes = (String) req.getParameter("dateRes");
                String data = (String) req.getParameter("data");

                StringBuffer sb = new StringBuffer();
                //fisrt part of query
                if(field.equals("Inventory Id")||field.equals("Inventory Num")) {
                    //log.debug("setting query with no items");
                  sb.append("SELECT     owd_receive.receive_id, owd_receive.transaction_num, owd_receive.receive_date,  owd_receive.type, owd_receive.receive_user, \n" +
                        "                       owd_receive.is_void as 'void',ISNULL(owd_receive.facility_code,'UNKNOWN') as 'facility_code'\n" +
                        "FROM         owd_receive INNER JOIN\n" +
                        "                      owd_receive_item ON owd_receive.receive_id = owd_receive_item.receive_fkey\n" +
                        "WHERE     (owd_receive.client_fkey = " + clientFkey + ")");
                }else{
                sb.append("SELECT     owd_receive.receive_id, owd_receive.transaction_num, owd_receive.receive_date,  owd_receive.type, owd_receive.receive_user, \n" +
                        "                      COUNT(owd_receive_item.receive_item_id) AS 'Items', owd_receive.is_void as 'void',ISNULL(owd_receive.facility_code,'UNKNOWN') as 'facility_code'\n" +
                        "FROM         owd_receive INNER JOIN\n" +
                        "                      owd_receive_item ON owd_receive.receive_id = owd_receive_item.receive_fkey\n" +
                        "WHERE     (owd_receive.client_fkey = " + clientFkey + ")");
                }
                    //getfield info and add it to the query

                if (!field.equals("All")) {
                    String crit = (String) AdjustmentUtilities.getFeilds().get(field);
                    if (type.equals("contains")) {
                        sb.append("AND " + crit + " LIKE '%" + data + "%'");
                    }
                    if (type.equals("is")) {
                        sb.append("AND " + crit + "= '" + data + "' ");
                    }
                } else {
                    data = "";
                }
                //check and add dates to query

                if ("yes".equals(dateRes)) {
                    sb.append("AND (owd_receive.receive_date BETWEEN CONVERT(DATETIME, '" + fromDate + " 00:00:00', 102) AND CONVERT(DATETIME, \n" +
                            "                      '" + toDate + " 00:00:00', 102)) \n");
                }
                sb.append("GROUP BY owd_receive.receive_id, owd_receive.transaction_num, owd_receive.receive_user, owd_receive.receive_date, owd_receive.type, owd_receive.is_void \n"+
                        "ORDER BY owd_receive.receive_date");

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
                UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, "Sku Adjustments", req);
                req.getSession(true).setAttribute("receiverp", displayrsc);
                req.getSession(true).setAttribute("columlist", colList);
                //  req.getSession(true).setAttribute("fkey",clientFkey);

                adbean.setData(data);
                adbean.setDateRes(dateRes);
                adbean.setFromDate(fromDate);
                adbean.setToDate(toDate);
                adbean.setField(field);
                adbean.setType(type);
                adbean.setFkey(clientFkey);
                req.getSession(true).setAttribute("adbean", adbean);

                req.setAttribute("clientList", AdjustmentUtilities.getClientList());
                req.getRequestDispatcher("adjustments/index2.jsp").forward(req, response);

 //Load Rec Action--------------------------------------------------------------------------------------------------
            } else if (action.equals("loadRec")) {
                String recId = (String) req.getParameter("recId");
                /*ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "SELECT     inventory_id, inventory_num, description, quantity, item_status\n" +
                        "FROM         owd_receive_item\n" +
                        "WHERE     (receive_fkey = " + recId + ")");
                int cols = rs.getMetaData().getColumnCount();
                List colList = new ArrayList();

                for (int i = 1; i <= cols; i++) {
                    selectList sl = new selectList();
                    sl.setAction(rs.getMetaData().getColumnName(i).toString());
                    colList.add(sl);
                }

                //log.debug("getting dynaset");
                RowSetDynaClass displayrsc = new RowSetDynaClass(rs, false);
                //log.debug("got dynaset");*/

             //   req.getSession(true).setAttribute("receiverit", displayrsc);
              //  req.getSession(true).setAttribute("columlist2", colList);
               // req.setAttribute("recId",recId);
                UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, "Sku Adjustments", req);
                req.setAttribute("clientList", AdjustmentUtilities.getClientList());
                OwdReceive rec = (OwdReceive) HibernateSession.currentSession().load(OwdReceive.class, Integer.valueOf(recId));


                req.getSession(true).setAttribute("rec",rec);
                req.getRequestDispatcher("adjustments/index2.jsp").forward(req, response);

//MORE INFO Action----------------------------------------------------------------------------------------------------------------
            }else if (action.equals("moreInfo")){
                if(null==req.getSession(true).getAttribute("rec")){
                   String recId = (String) req.getParameter("recId");
                    OwdReceive rec = (OwdReceive) HibernateSession.currentSession().load(OwdReceive.class, Integer.valueOf(recId));
                req.getSession(true).setAttribute("rec",rec);
                }
             UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, "Sku Adjustments / View Adjustment", req);
              req.getRequestDispatcher("adjustments/moreInfo.jsp").forward(req, response);
            }
//Start Return Action----------------------------------------------------------------------------------------------------------------------
            else if (action.equals("doReturn")){
                req.setAttribute("clientList", AdjustmentUtilities.getClientList());

                req.getRequestDispatcher("adjustments/return/return.jsp").forward(req,response);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("error",ex.getMessage());
            try{
            req.getRequestDispatcher("adjustments/index2.jsp").forward(req, response);}
            catch (Exception e){
                //log.debug("umable to do anything for some silly reason");
            }
        } finally {
            HibernateSession.closeSession();
        }


    }
}
