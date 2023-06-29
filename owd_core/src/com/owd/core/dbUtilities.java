package com.owd.core;


import com.owd.core.business.ChoiceListManager;
import com.owd.core.business.owdChoiceList;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SQLQuery;

import javax.management.Query;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;


public class dbUtilities {
private final static Logger log =  LogManager.getLogger();


    public static owdChoiceList getChoiceList(Connection cxn, String listName) throws Exception {

        return ChoiceListManager.getChoiceListManager().getList(listName);


    }
    
    static public String getOrderKeyID(Connection cxn, String orderNum) throws Exception {

        String rString = null;


        Statement stmt = cxn.createStatement();

        String esql = "exec get_order_key \"" + orderNum + "\"";


        ResultSet rs = stmt.executeQuery(esql);


        while (rs.next()) {

            rString = rs.getString(1);

        }


        rs.close();


        stmt.close();


        return rString;


    }


    static public void setQuotedIdentifier(Connection cxn, boolean on) throws Exception {

        String rString = null;


        Statement stmt = cxn.createStatement();


        String esql = "SET QUOTED_IDENTIFIER OFF";

        if (on == true)

            esql = "SET QUOTED_IDENTIFIER ON";


        boolean hasResultSet = stmt.execute(esql);


        stmt.close();


    }


    static public String getNextIDSession(String module) throws Exception{
        String rString = null;

        String esql = "exec get_next_id \"" + module + "\"";

         SQLQuery  q =HibernateSession.currentSession().createSQLQuery(esql);
            List l = q.list();
        if(l.size()>0){
            rString = l.get(0).toString();
            HibUtils.commit(HibernateSession.currentSession());
        }







        if (rString != null) {

            if (rString.length() < 7) {

                for (int i = 0; i <= (7 - rString.length()); i++)

                    rString = "0" + rString;

            }


        }

        return rString;

    }



    static public String getNextID(Connection cxn, String module) throws Exception {

        String rString = null;


        Statement stmt = cxn.createStatement();

        String esql = "exec get_next_id \"" + module + "\"";


        ResultSet rs = stmt.executeQuery(esql);


        while (rs.next()) {

            rString = rs.getString(1);

        }


        rs.close();


        stmt.close();


        cxn.commit();


        if (rString != null) {

            if (rString.length() < 7) {

                for (int i = 0; i <= (7 - rString.length()); i++)

                    rString = "0" + rString;

            }


        }

        return rString;


    }


}
