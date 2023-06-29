package com.owd.web.reports;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.managers.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 3, 2004
 * Time: 9:19:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReportsManager {
private final static Logger log =  LogManager.getLogger();

    public static ReportDefinition getReportDefForId(String reportID, String clientID) throws Exception {
        ReportDefinition rdef = null;
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        log.debug("getting reportdef for " + reportID + "," + clientID);
        if (!(clientID.equals("" + (new Integer(clientID).intValue())))) {
            throw new Exception("com.owd.api.client not valid");
        }


        try {
            cxn = ConnectionManager.getConnection();
            stmt = cxn.prepareStatement("select * from owd_reportdefs where id=?");
            stmt.setString(1, reportID);


            rs = stmt.executeQuery();
            if (rs.next()) {
                rdef = (ReportDefinition) Class.forName(rs.getString("def_class_name")).newInstance();
                rdef.setName(rs.getString("name"));
                rdef.setId(rs.getInt("id"));
                rdef.setDescription(rs.getString("description"));
                rdef.setClientID(rs.getString("client_fkey"));

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            try {
                rs.close();
            } catch (Exception exc) {
            }
            try {
                stmt.close();
            } catch (Exception exc) {
            }

            try {
                cxn.close();
            } catch (Exception exc) {
            }

        }

        return rdef;


    }

    private static List getReportList(String whereClause) {
        List reportList = new ArrayList();
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            cxn = ConnectionManager.getConnection();
            log.debug("select * from owd_reportdefs left outer join owd_client on client_id=client_fkey " + whereClause);
            stmt = cxn.prepareStatement("select * from owd_reportdefs " + whereClause);

            rs = stmt.executeQuery();
            while (rs.next()) {
                ReportDefinition rdef = (ReportDefinition) Class.forName(rs.getString("def_class_name")).newInstance();
                rdef.setName(rs.getString("name"));
                rdef.setId(rs.getInt("id"));
                rdef.setDescription(rs.getString("description"));
                rdef.setClientID(rs.getString("client_fkey"));
                reportList.add(rdef);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            try {
                rs.close();
            } catch (Exception exc) {
            }
            try {
                stmt.close();
            } catch (Exception exc) {
            }

            try {
                cxn.close();
            } catch (Exception exc) {
            }

        }

        return reportList;

    }

    public static List getPublicReportList() {
        return getReportList("where client_fkey=0");
    }

    public static List getClientReportList(String clientID) {
        return getReportList("where client_fkey <> 0 and client_fkey=" + clientID);

    }

    public static List getAllReportList() {
        return getReportList("");

    }


}
