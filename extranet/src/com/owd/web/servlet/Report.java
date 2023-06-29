package com.owd.web.servlet;


//import org.apache.crimson.tree.ElementNode;
//import org.apache.crimson.tree.XmlDocument;




import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;


public class Report {
private final static Logger log =  LogManager.getLogger();

    //IDs are zero if the item is new

    public static final int kDaily = 1;

    public static final int kWeekly = 2;

    public static final int kMonthly = 4;

    public static final int kQuarterly = 8;

    public static final int kYearly = 16;



    //Attributes

    public static final String kdbReportPKName = "report_id";

    public int report_id = 0;


    public static final String kdbReportClientFkey = "client_id";

    public int client_id = 0;


    public static final String kdbReportName = "name";

    public String name = "";


    public static final String kdbReportDescription = "description";

    public String description = "";


    public static final String kdbReportPeriod = "period";

    public int period = kDaily;


    public static final String kdbReportSQL = "sql_text";

    public String sql_text = "";


    public static final String kdbDeliverTo = "deliver_to";

    public String deliver_to = "";


    public static final String kdbIsActive = "is_active";

    public int is_active = 0;


    public static final String kdbReportDefID = "report_def_id";

    public int report_def_id = 0;


    private static final String loadStatement = "select report_id," +

            " name," +

            " description," +

            " sql_text," +

            " period," +

            " ISNULL(deliver_to,\"\")," +

            " ISNULL(is_active,\"\")," +

            " ISNULL(report_def_id,0) " +

            " from r_reports left outer join r_report_defs " +

            "on (report_id = report_fkey) where report_id = ? and (client_id=? or client_id IS NULL)";


    private static final String updateStatement = "update r_report_defs set deliver_to = ?," +

            " is_active = ?  where report_def_id = ?";


    private static final String createStatement = "insert into r_report_defs (deliver_to," +

            "is_active,report_fkey) VALUES (?,?,?)";


    private boolean needsUpdate = false;


    public Report(int aid,

                  String aname,

                  String adescription,

                  String asql_text,

                  int aperiod,

                  int aclient_id,

                  String adeliver_to,

                  int ais_active,

                  int areport_def_id) {

        name = aname;

        description = adescription;

        sql_text = asql_text;

        period = aperiod;

        report_id = aid;


        client_id = aclient_id;

        deliver_to = adeliver_to;

        is_active = ais_active;

        report_def_id = areport_def_id;


    }


    public static Report getReportForIDs(Connection cxn, String ID, String clientID) throws Exception {

        //load existing package

        return dbload(cxn, ID, clientID);

    }


    public boolean isModified() {

        return needsUpdate;

    }


    public boolean isNew() {

        if (0 == (report_def_id))

            return true;


        return false;

    }


    public Vector getColumnNames(Connection cxn) {

        PreparedStatement stmt = null;

        ResultSet rs = null;

        Vector names = new Vector();


        try {

            stmt = cxn.prepareStatement(sql_text);

            stmt.executeQuery();

            rs = stmt.getResultSet();

            ResultSetMetaData desc = rs.getMetaData();

            int cols = desc.getColumnCount();

            for (int i = 1; i <= cols; i++) {

                names.addElement(desc.getColumnName(i));

            }


        } catch (Exception ex) {
        } finally {

            try {
                rs.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                stmt.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }


        return names;


    }


    public Vector getColumnNames() {


        Connection cxn = null;

        Vector names = new Vector();

        try {

            cxn = com.owd.core.managers.ConnectionManager.getConnection();


            names = getColumnNames(cxn);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            try {
                cxn.close();
            } catch (Exception ex) {
            }

        }


        return names;


    }


    public Node toXml(Document doc) throws IOException {


        //return Line Item node

        Node root = doc.createElement("xxx");


        return root;


    }


    public void dbsave(Connection cxn) throws Exception {


        PreparedStatement ps = null;


        try {

            ps = cxn.prepareStatement(createStatement);

            ps.setString(1, deliver_to);

            ps.setInt(2, is_active);

            ps.setInt(3, report_id);


            if (!(ps.executeUpdate() > 0)) {

                throw new Exception("Could not save report!");

            }


            ps.close();


        } catch (Exception ex) {


            ex.printStackTrace();

            throw ex;

        } finally {

            try {
                ps.close();
            } catch (Exception exc) {
            }


        }


    }


    public void dbupdate(Connection cxn) throws Exception {

        ////log.debug("Report.dbupdate");

        if (isNew()) {

            dbsave(cxn);

        } else {

            PreparedStatement ps = null;

            try {

                ps = cxn.prepareStatement(updateStatement);

                ps.setString(1, deliver_to);

                ps.setInt(2, is_active);

                ps.setInt(3, report_id);


                int rowsUpdated = ps.executeUpdate();


                if (rowsUpdated < 1)

                    throw new Exception("Could not update report " + name);


            } catch (Exception ex) {

                ex.printStackTrace();

                throw ex;

            } finally {

                try {
                    ps.close();
                } catch (Exception exc) {
                }


            }


        }


    }


    private static Report dbload(Connection cxn, String ID, String clientID) throws Exception {


        PreparedStatement stmt = null;

        ResultSet rs = null;

        Report pack = null;


        try {

            stmt = cxn.prepareStatement(loadStatement);

            stmt.setInt(1, new Integer(ID).intValue());

            stmt.setInt(2, new Integer(clientID).intValue());

            stmt.executeQuery();

            rs = stmt.getResultSet();

            if (rs.next()) {

                pack = new Report(rs.getInt(1),

                        rs.getString(2),

                        rs.getString(3),

                        rs.getString(4),

                        rs.getInt(5),

                        new Integer(clientID).intValue(),

                        rs.getString(6),

                        rs.getInt(7),

                        rs.getInt(8));

            } else {

                throw new Exception("Could not load report!");

            }


        } catch (Exception ex) {

            throw ex;

        } finally {

            try {
                rs.close();
            } catch (Exception exc) {
            }

            try {
                stmt.close();
            } catch (Exception exc) {
            }


        }


        return pack;

    }


}
