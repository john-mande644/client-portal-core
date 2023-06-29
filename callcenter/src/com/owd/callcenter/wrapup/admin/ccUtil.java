package com.owd.callcenter.wrapup.admin;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.ccOutcomeOptions;
import com.owd.hibernate.HibUtils;
import com.owd.callcenter.beans.selectList;
import com.owd.callcenter.forms.wrapup.recordOutcomeForm;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 18, 2006
 * Time: 3:16:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class ccUtil {
    private static String kCallType = "calltype";
    private static String kOutcome = "outcome";


    public static List getCallType(String campaign) {


        List clientList = new ArrayList();
        String query = "SELECT     id, info\n" +
                "FROM         cc_outcome_options\n" +
                "WHERE     (type = 'calltype') AND (campaign = '" + campaign + "') order by info";
        try {
            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), query);
            int i = 0;
            while (rs.next()) {
                System.out.println(rs.getString(2));
                selectList btn = new selectList();
                btn.setAction(rs.getString(1));
                btn.setDisplay(rs.getString(2));
                clientList.add(i, btn);
                i++;
                //   System.out.println(rs.getString(1));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("done getting calltypes");
        return clientList;
    }

    public static List getOutcome(String id) {


        List clientList = new ArrayList();
        selectList sl = new selectList();
        sl.setAction("");
        sl.setDisplay("");

        clientList.add(0, sl);
        String query = "SELECT     id,info\n" +
                "FROM         cc_outcome_options\n" +
                "WHERE     (type_id = " + id + ") order by info";
        try {
            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), query);
            int i = 1;
            while (rs.next()) {
                System.out.println(rs.getString(1));
                selectList btn = new selectList();
                btn.setAction(rs.getString(1));
                btn.setDisplay(rs.getString(2));
                clientList.add(i, btn);
                i++;
                //   System.out.println(rs.getString(1));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("done getting outcomes");
        return clientList;
    }

    public static void addCallType(Session sess, String campaign, String info) throws Exception {
        ccOutcomeOptions cco = new ccOutcomeOptions();

        cco.setCampaign(campaign);
        cco.setInfo(info);
        cco.setType(kCallType);
        sess.save(cco);
        sess.flush();
        HibUtils.commit(sess);

    }

    public static String getDoOrderBox(Session sess, String id) throws Exception {
             ccOutcomeOptions cco = (ccOutcomeOptions) sess.load(ccOutcomeOptions.class, Integer.valueOf(id));


           String doOrderValue;
           Integer doOrder = cco.getDoOrder();
           sess.flush();
           HibUtils.commit(sess);
           if(doOrder==null){
               doOrder = new Integer(0);
           }


           if(doOrder.equals((Integer.valueOf("1")))){
               doOrderValue="1";//Boolean.valueOf(true);
                }else{
               doOrderValue="0";//Boolean.valueOf(false);
           }

          return doOrderValue;
    }

    public static void setDoOrder(Session sess, String id, String doOrder) throws Exception {
        ccOutcomeOptions cco = (ccOutcomeOptions) sess.load(ccOutcomeOptions.class, Integer.valueOf(id));

        if(null==doOrder){
                 doOrder="0";
                 }

        Integer doOrderValue;
           if(doOrder=="1"){
                   doOrderValue = new Integer("1");
            } else {
                   doOrderValue = new Integer("0");
           }

        cco.setDoOrder(doOrderValue);
        sess.save(cco);
        sess.flush();
        HibUtils.commit(sess);
    }

    public static void setDefaultText(Session sess, String id, String defaultText) throws Exception {
        ccOutcomeOptions cco = (ccOutcomeOptions) sess.load(ccOutcomeOptions.class, Integer.valueOf(id));

        cco.setDefaultText(defaultText);
        sess.save(cco);
        sess.flush();
        HibUtils.commit(sess);
        /*String update = "UPDATE    cc_outcome_options\n" +
                "SET         default_text = " + "'" + defaultText + "'\n" +
                "WHERE     (id = " + id + ")";


          Connection sess =   ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();

          PreparedStatement  stmt = sess.prepareStatement(update);

            int rows = stmt.executeUpdate();
            sess.commit();
            sess.close();
          */
    }

    public static String getDefaultText(Session sess, String id) throws Exception {
          ccOutcomeOptions cco = (ccOutcomeOptions) sess.load(ccOutcomeOptions.class, Integer.valueOf(id));

        String defaultText = cco.getDefaultText();
        sess.flush();
        HibUtils.commit(sess);


        /*
        String defaultText = new String();

           String query = "SELECT    default_text\n" +
                "FROM         cc_outcome_options\n" +
                "WHERE     (id = " + id + ")";



        try {
            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), query);
            int i = 1;
            while (rs.next()) {
            defaultText = rs.getString(1);
            i++;
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

          */
        return defaultText;

    }

    public static void addOutcomeType(Session sess, String campaign, String info, String id) throws Exception {
        ccOutcomeOptions cco = new ccOutcomeOptions();

        cco.setCampaign(campaign);
        cco.setInfo(info);
        cco.setType(kOutcome);
        cco.setTypeId(Integer.valueOf(id));
        sess.save(cco);
        sess.flush();
        HibUtils.commit(sess);

    }

    public static void removeOutcomeType(Session sess, String id) throws Exception {
        ccOutcomeOptions cco = (ccOutcomeOptions) sess.load(ccOutcomeOptions.class, Integer.valueOf(id));
        sess.delete(cco);
        sess.flush();
        HibUtils.commit(sess);
    }

    public static void removeCallType(Session sess, String id) throws Exception {
        ccOutcomeOptions cco = (ccOutcomeOptions) sess.load(ccOutcomeOptions.class, Integer.valueOf(id));
        sess.delete(cco);
        Criteria crit = sess.createCriteria(ccOutcomeOptions.class);
        crit.setMaxResults(100);
        crit.add(Restrictions.eq("typeId", Integer.valueOf(id)));

        List list = crit.list();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ccOutcomeOptions coo = (ccOutcomeOptions) it.next();
            sess.delete(coo);
        }
        sess.flush();
        HibUtils.commit(sess);
    }

    public static List loadNoContactTypes() {
        List l = new ArrayList();
        List stat = new ArrayList();
        l.add("Disconnected");
        l.add("Wrong Number");
        l.add("Rerouted");
        l.add("error message");
        for (int i = 0; i < l.size(); i++) {
            selectList sl = new selectList();
            sl.setAction(l.get(i).toString());
            sl.setDisplay(l.get(i).toString());
            stat.add(i, sl);
        }


        return stat;
    }
    public static String getDoOrder(Session sess,String campaign) throws Exception{
        PreparedStatement stat =  ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection().prepareStatement("select do_orderref_entry from owd_client_callcenter where Mlog_campaign_name = ?");
stat.setString(1,campaign);
        ResultSet rs = stat.executeQuery();
        rs.next();
        return rs.getString(1);


    }


    public static void insertOutcomeRecord(Session sess, recordOutcomeForm rof) throws Exception {
        String outcome = "";

        Iterator it = rof.getTypeList().iterator();

        while (it.hasNext()) {
            selectList sl = (selectList) it.next();
            if (sl.getAction().equals(rof.getCallTypeId())) {
                rof.setCallType(sl.getDisplay());
                break;
            }
        }

        if (null != rof.getOutcome() && !"".equals(rof.getOutcome())) {
            Iterator it2 = rof.getOutcomeList().iterator();


            while (it2.hasNext()) {
                selectList sl = (selectList) it2.next();
                if (sl.getAction().equals(rof.getOutcome())) {
                    outcome = sl.getDisplay();
                    break;
                }
            }
        }
        int crossSell = 0;
        try{
            if(rof.getCrosssell().equals("1")) crossSell=1;

        } catch(Exception e)
        {}
        PreparedStatement stmt =  ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into cc_outcome (contact_id,result,call_type,outcome,notes,customer_id,cross_sell) " +
                "values (?,?,?,?,?,?,?)");
        stmt.setString(1, rof.getSource()+rof.getCallId());
        stmt.setString(2, rof.getResult());
        stmt.setString(3, rof.getCallType());
        stmt.setString(4, outcome);
        stmt.setString(5, rof.getNotes());
        stmt.setString(6,rof.getCustomerId());
        stmt.setInt(7,crossSell);
        // Statement stmt =  ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection().createStatement();
        /*  String sqlQuery = "insert into cc_outcome (contact_id,result,call_type,outcome,notes) " +
        "values ('" + rof.getCallId() + "','" + rof.getResult() + "', '"+rof.getCallType()
        +"','"+outcome+"','"+rof.getNotes()+"')";*/
        //System.out.println(sqlQuery);
        int rows = stmt.executeUpdate();
        System.out.println("rows " + rows);
        HibUtils.commit(sess);
        stmt.close();
    }
    public static void recordCCOrder(Session sess, recordOutcomeForm rof) throws Exception{

         double subtotal = 0.00;
        try
        {
          subtotal = Double.parseDouble(rof.getSubtotal());
        }catch(Exception ex)
        {

        }
           int client = getClientId(sess ,rof.getCampaign());
           String uqhandle = rof.getCallId();
           String order_refnum = rof.getOrderId();

            System.out.println(uqhandle);
            System.out.println(order_refnum);
            System.out.println(client);
            System.out.println(subtotal);


          Query stmt = sess.createSQLQuery("insert into dbo.callcenter_orders (client_fkey, order_refnum, order_subtotal, uqcontact_id) values (?,?,?,?)")
            .setInteger(0, client)
            .setString(1, order_refnum)
            .setDouble(2, subtotal)
            .setString(3, uqhandle);

            int rows = stmt.executeUpdate();
            HibUtils.commit(sess);



    }

    public static int getClientId(Session sess,String campaign) throws Exception{
         Connection cxn =  ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection();

          PreparedStatement  stmt = cxn.prepareStatement("select client_fkey from owd_client_callcenter where Mlog_campaign_name = ?");
         stmt.setString(1,campaign);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) return rs.getInt((1));

     throw new Exception("no valid  client_fkey for campaign");
    }
}
