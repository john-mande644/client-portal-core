package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ReportFinder {
private final static Logger log =  LogManager.getLogger();


    String[] tableColumnNames = {" ", "Name", "Active", "Deliver To", "Description"};

    String[] tableColumnDefs = {"report_id", "name", "CASE WHEN is_active = 1 THEN \"Yes\" ELSE \"No\" END",

                                "ISNULL(deliver_to,\"\")",

                                "description"};

    String tableFromStmt = "from r_reports r left outer join r_report_defs on (report_id = report_fkey)  ";

    String[] tableLinkStarts = {ReportsManager.kParamReportMgrAction + "=" + ReportsManager.kParamReportEditAction + "&" + Report.kdbReportPKName + "=", "", "", "", ""};

    String[] tableLinkEnds = {"Edit</A>", "", "", "", ""};

    int[] tableLinkMids = {1, 0, 0, 0, 0};


    public ReportFinder() {


    }



    //where report_id = ? and (client_id=? or client_id IS NULL)



    public static final String kBlank = "";


    public static ReportFinder parseRequest(HttpServletRequest req, HttpServletResponse resp) {

        ReportFinder finder = new ReportFinder();


        return finder;

    }


    public String showResults(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        WebTable table = new WebTable();


        table.setSQLDefs(tableColumnNames, tableColumnDefs, tableLinkStarts, tableLinkMids, tableLinkEnds, tableFromStmt);


        table.addCriterium("(client_id=" + ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID) + " or client_id IS NULL)");


        String raction = ExtranetServlet.getStringParam(req, ReportsManager.kParamReportMgrAction, ReportsManager.kParamReportExploreAction);


        if (raction.equals(ReportsManager.kParamReportDailyAction)) {

            //refSearch

            table.addCriterium("period=" + Report.kDaily);


        } else if (raction.equals(ReportsManager.kParamReportWeeklyAction)) {

            //refSearch

            table.addCriterium("period=" + Report.kWeekly);


        } else if (raction.equals(ReportsManager.kParamReportMonthlyAction)) {

            //refSearch

            table.addCriterium("period=" + Report.kMonthly);


        }


        table.setDescription("");

        //check for find criteria



        table.setPageNum(ExtranetServlet.getIntegerParam(req, WebTable.kTableShowPage, 1));

        table.setOrderCol(ExtranetServlet.getIntegerParam(req, WebTable.kTableSortColumn, -1));


        return table.getTable(req, resp);


    }


}
