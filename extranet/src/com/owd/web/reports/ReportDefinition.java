package com.owd.web.reports;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateFogbugzSession;
import com.owd.hibernate.HibernateSession;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 3, 2004
 * Time: 9:16:26 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ReportDefinition {
private final static Logger log =  LogManager.getLogger();

    int id = 0;
    String clientID = "-1";
    String name = "Unknown";
    String description = "Unknown";
    int datasource = kDataSourceOWD;

    public int getDatasource() {
        return datasource;
    }

    public void setDatasource(int datasource) {
        this.datasource = datasource;
    }

    public static final int kDataSourceOWD = 1;
    public static final int kDataSourceFogBugz = 2;
    public static final int kDataSourceMLog = 3;


    public static final int kReportDataXMLType = 1;
    public static final int kReportDataCSVType = 2;
    public static final int kReportDataExcelType = 3;
    public static final int kReportDataHTMLType = 4;

    public void returnReportDataToBrowser(HttpServletRequest request, int userClientID) throws Exception
    {
        List reportList = new ArrayList();
               Connection cxn = null;
               PreparedStatement stmt = null;
               ResultSet rs = null;

               try {
                   cxn = getConnectionForDataSource(datasource);
                   stmt = cxn.prepareStatement(getPrepareSQL());
                   List params = getParameters();
                   for (int i = 0; i < params.size(); i++) {
                       ReportParameter param = (ReportParameter) params.get(i);
                       String val = request.getParameter(param.getFormValueName());
                       if (val == null) {
                           if(param.getParamDataType()==ReportParameter.kParamTypeCheckbox)
                           {
                               val = param.getDefaultValue();
                           }      else
                           {
                           throw new Exception("Could not run report. The required " + param.getDisplayName() + " parameter was missing.");
                           }
                       }
                       if (val.trim().length() < 1) {
                           val = param.getDefaultValue();
                       }
                       switch (param.getParamDataType()) {

                           case (ReportParameter.kParamTypeDate):
                               stmt.setString(i + 1, val);
                               break;
                           case (ReportParameter.kParamTypeText):
                               stmt.setString(i + 1, val);
                               break;
                               case (ReportParameter.kParamTypeCheckbox):
                               stmt.setInt(i + 1, Integer.decode(val).intValue());
                               break;
                           default:
                               stmt.setString(i + 1, val);

                       }


                   }

                   if (userClientID == 0) {
                       String setClientID = request.getParameter("internal_client_id");
                       if (setClientID == null) setClientID = "0";

                       userClientID = new Integer(setClientID).intValue();
                   }
                   stmt.setInt(params.size() + 1, userClientID);

                   log.debug("executing query");
                   rs = stmt.executeQuery();

                   int cols = rs.getMetaData().getColumnCount();
                   List colList = new ArrayList();

                   for (int i=1;i<=cols;i++)
                   {
                       colList.add(rs.getMetaData().getColumnName(i));
                   }

                   log.debug("getting dynaset");
                          RowSetDynaClass displayrs = new RowSetDynaClass(rs, false);
                   log.debug("got dynaset");
        request.setAttribute("reportrs",displayrs);
        request.setAttribute("reportrs-column-list",colList);
               } catch (SQLException ex) {
                   ex.printStackTrace();
                   throw new Exception("There was a problem running your report. Please check your parameters.");
               } finally {

                   closeConnectionForDataSource(datasource);

               }

               //   return reportList;



    }
    public static void returnReportDataToBrowser(ResultSet rs, HttpServletResponse resp, String name, String attName) throws Exception {

        int reportDataType = kReportDataCSVType;
        try {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            StringBuffer writer = new StringBuffer();
            resp.reset();
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + attName.trim().replace(' ', '_') + ".csv\"");
            if (reportDataType == kReportDataCSVType) {
                writer.append("\"" + name + "\"\n");
                DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.US);

                Calendar now = Calendar.getInstance();
                writer.append("\"Generated At:\",\"" + formatter.format(now.getTime()) + "\"\n");

                writer.append("\n");

            }
            for (int i = 1; i <= columnCount; i++) {
                if (reportDataType == kReportDataCSVType) {
                    writer.append((i > 1 ? ",\"" : "\"") + rsmd.getColumnName(i) + (i == columnCount ? "\"\n" : "\""));
                }
                log.debug("col type " + i + " is " + rsmd.getColumnType(i));
            }


            java.text.DecimalFormat decform = new java.text.DecimalFormat("######0.00");
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {

                    String val = (null == rs.getString(i) ? "" : rs.getString(i)).replace('\"', '\'');

                    switch (rsmd.getColumnType(i)) {
                        case (Types.REAL):
                        case (Types.FLOAT):
                        case (Types.DECIMAL):
                        case (Types.DOUBLE):
                        case (Types.NUMERIC):
                        case (Types.SMALLINT):
                        case (Types.TINYINT):
                        case (Types.BOOLEAN):
                        case (Types.BIT):
                        case (Types.BIGINT):
                        case (Types.INTEGER):
                            val = "=" + decform.format((null == rs.getString(i) ? 0.00d : rs.getDouble(i))) + "";
                            break;


                        default:
                            val = "=\"" + val.replace('\"', '\'') + val.replace(',', ' ').replaceAll("\r", "\\").replaceAll("\n", "\\") + "\"";

                    }
                    if (reportDataType == kReportDataCSVType) {
                        writer.append((i > 1 ? "," : "") + val + (i == columnCount ? "\n" : ""));
                    }
                }
            }
            if (reportDataType == kReportDataCSVType) {
            }
            log.debug("datalen=" + writer.length());
            resp.setContentLength(writer.length());
            resp.getOutputStream().write(writer.toString().getBytes());

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("There was a problem running your report. Please check your parameters.");
        } finally {


        }

        //   return reportList;


    }

    public void returnReportDataToBrowser(int reportDataType, HttpServletRequest req, HttpServletResponse resp, int userClientID) throws Exception {
        List reportList = new ArrayList();
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            cxn = getConnectionForDataSource(datasource);
            stmt = cxn.prepareStatement(getPrepareSQL());
            List params = getParameters();
            for (int i = 0; i < params.size(); i++) {
                ReportParameter param = (ReportParameter) params.get(i);
                String val = req.getParameter(param.getFormValueName());
                if (val == null) {
                    if(param.getParamDataType()==ReportParameter.kParamTypeCheckbox)
                    {
                        val = param.getDefaultValue();
                    }      else
                    {
                    throw new Exception("Could not run report. The required " + param.getDisplayName() + " parameter was missing.");
                    }
                }
                if (val.trim().length() < 1) {
                    val = param.getDefaultValue();
                }
                switch (param.getParamDataType()) {

                    case (ReportParameter.kParamTypeDate):
                        stmt.setString(i + 1, val);
                        break;
                    case (ReportParameter.kParamTypeText):
                        stmt.setString(i + 1, val);
                        break;
                        case (ReportParameter.kParamTypeCheckbox):
                        stmt.setInt(i + 1, Integer.decode(val).intValue());
                        break;
                    default:
                        stmt.setString(i + 1, val);

                }


            }

            if (userClientID == 0) {
                String setClientID = req.getParameter("internal_client_id");
                if (setClientID == null) setClientID = "0";

                userClientID = new Integer(setClientID).intValue();
            }
            stmt.setInt(params.size() + 1, userClientID);

            rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            StringBuffer writer = new StringBuffer();
            resp.reset();
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + getName().trim().replace(' ', '_') + ".csv\"");
            if (reportDataType == kReportDataCSVType) {
                writer.append("\"" + getName() + "\"\n");
                DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.US);

                Calendar now = Calendar.getInstance();
                writer.append("\"Generated At:\",\"" + formatter.format(now.getTime()) + "\"\n");
                for (int i = 0; i < params.size(); i++) {
                    ReportParameter param = (ReportParameter) params.get(i);
                    String val = req.getParameter(param.getFormValueName());
                    if (val == null) {
 if(param.getParamDataType()==ReportParameter.kParamTypeCheckbox)
                    {
                        val = param.getDefaultValue();
                    }      else
                    {
                    throw new Exception("Could not run report. The required " + param.getDisplayName() + " parameter was missing.");
                    }                    }
                    if (param.getParamDataType() == ReportParameter.kParamTypeOptions) {
                        writer.append("\"" + param.getDisplayName() + ":\",\"" + param.getOptionMap().get(val) + "\"\n");
                    } else {
                        writer.append("\"" + param.getDisplayName() + ":\",\"" + val + "\"\n");
                    }


                }
                writer.append("\n");

            } else if (reportDataType == kReportDataXMLType) {
                resp.setContentType("text/xml");
                writer.append("<? xml version=\"1.0\" ?>");
                writer.append("<OWD_REPORT generated_on=\"\" generated_by=\"\">");
                writer.append("<NAME>" + getName() + "</NAME>");
                writer.append("<DESCRIPTION>" + getDescription() + "</DESCRIPTION>");

            }
            for (int i = 1; i <= columnCount; i++) {
                if (reportDataType == kReportDataCSVType) {
                    writer.append((i > 1 ? ",\"" : "\"") + rsmd.getColumnName(i) + (i == columnCount ? "\"\n" : "\""));
                } else if (reportDataType == kReportDataXMLType) {
                    //    resp.getWriter().write((i>1?",\"":"\"")+rsmd.getColumnName(i)+(i==columnCount?"\"\n":"\""));

                }
                log.debug("col type " + i + " is " + rsmd.getColumnType(i));
            }


            java.text.DecimalFormat decform = new java.text.DecimalFormat("######0.00");
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {

                    String val = (null == rs.getString(i) ? "" : rs.getString(i)).replace('\"', '\'');

                    switch (rsmd.getColumnType(i)) {
                        case (Types.REAL):
                        case (Types.FLOAT):
                        case (Types.DECIMAL):
                        case (Types.DOUBLE):
                        case (Types.NUMERIC):
                        case (Types.SMALLINT):
                        case (Types.TINYINT):
                        case (Types.BOOLEAN):
                        case (Types.BIT):
                        case (Types.BIGINT):
                        case (Types.INTEGER):
                            val = "=" + decform.format((null == rs.getString(i) ? 0.00d : rs.getDouble(i))) + "";
                            break;


                        default:
                            val = "=\"" + val.replace(',', ' ') + "\"";

                    }
                    if (reportDataType == kReportDataCSVType) {
                        writer.append((i > 1 ? "," : "") + val + (i == columnCount ? "\n" : ""));
                    } else if (reportDataType == kReportDataXMLType) {
                        //    resp.getWriter().write((i>1?",\"":"\"")+rsmd.getColumnName(i)+(i==columnCount?"\"\n":"\""));

                    }
                }
            }
            if (reportDataType == kReportDataCSVType) {
            } else if (reportDataType == kReportDataXMLType) {
                writer.append("</OWD_REPORT>");

            }
            log.debug("datalen=" + writer.length());
            resp.setContentLength(writer.length());
            resp.getOutputStream().write(writer.toString().getBytes());

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("There was a problem running your report. Please check your parameters.");
        } finally {

            closeConnectionForDataSource(datasource);

        }

        //   return reportList;


    }


    //customized methods
    public abstract List getParameters();

    public abstract String getPrepareSQL();


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    protected static Connection getConnectionForDataSource(int ds) throws Exception {
        switch (ds) {
            case kDataSourceOWD:
                return  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
            case kDataSourceFogBugz:
                return    ((SessionImplementor)HibernateFogbugzSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
            case kDataSourceMLog:
                return  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
            default:
                return  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
        }
    }

    protected static void closeConnectionForDataSource(int ds) {
        switch (ds) {
            case kDataSourceOWD:
                HibernateSession.closeSession();
                break;
            case kDataSourceFogBugz:
                HibernateFogbugzSession.closeSession();
                break;
            case kDataSourceMLog:
                HibernateSession.closeSession();
                break;
        }
    }
}
