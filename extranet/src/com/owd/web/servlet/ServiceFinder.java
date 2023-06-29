package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ServiceFinder {
private final static Logger log =  LogManager.getLogger();


    String[] tableColumnNames = {"", "Service Name", "Description", "Billed At"};

    String[] tableLinkStarts = {ServiceManager.kParamSvcMgrAction + "=" + ServiceManager.kParamSvcEditAction + "&" + ServiceManager.kparamSvcID + "=", "", "", ""};

    String[] tableLinkEnds = {"Edit</A>", "", "", ""};

    int[] tableLinkMids = {1, 0, 0, 0};

    int[] tableRenders = {WebTable.kRenderString, WebTable.kRenderString, WebTable.kRenderString, WebTable.kRenderString};

    String[] tableColumnDefs = {Service.kdbServicePKName, Service.kdbServiceName, Service.kdbServiceDesc, "\'$\'+CONVERT(varchar,"

            + Service.kdbServiceCharge + ",1)+\' \'+CASE  WHEN " + Service.kdbServiceIsTimed + "=1 AND " + Service.kdbServiceTimeUnit + "=0 THEN \'per minute\' "

            + "WHEN " + Service.kdbServiceIsTimed + "=1 AND " + Service.kdbServiceTimeUnit + "=1 THEN \'per hour\' "

            + "  ELSE \'per event\' END"};

    String tableFromStmt = "from " + Service.kdbServiceTable + " c ";


    public ServiceFinder() {


    }


    public static final String kBlank = "";


    private String name = null;


    private int nameType = 1;


    private static final int kIsType = 1;

    private static final int kInType = 2;


    public static final String kNameSearchFlag = "doname";

    public static final String kNameSearchValue = "namevalue";

    public static final String kNameSearchType = "nametype";


    public static final String kSubmitType = "findsvcs";


    public int getNameType() {

        return nameType;

    }


    public static ServiceFinder parseRequest(HttpServletRequest req, HttpServletResponse resp) {

        ServiceFinder finder = new ServiceFinder();


        if (ExtranetServlet.getIntegerParam(req, kNameSearchFlag, 0) == 1) {

            //name Search

            finder.setName(ExtranetServlet.getStringParam(req, kNameSearchValue, ""), ExtranetServlet.getIntegerParam(req, kNameSearchType, 1));


        }


        if ("Show All".equals(ExtranetServlet.getStringParam(req, kSubmitType, ""))) {

            //customerSearch

            finder.setShowAll(true);

        }


        return finder;

    }


    public void setShowAll(boolean all) {

        showAll = all;

    }


    public void setName(String value, int findType) {

        if (value == null) return;


        name = value;

        nameType = findType;

    }


    public String getName() {

        if (isNameSearch())

            return name;

        else

            return kBlank;

    }


    public boolean isNameSearch() {

        if (name == null)

            return false;

        else

            return true;

    }


    private boolean showAll = false;


    public String showResults(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        WebTable table = new WebTable();

        boolean gotSearch = false;

        table.setSQLDefs(tableColumnNames, tableColumnDefs, tableLinkStarts, tableLinkMids, tableLinkEnds, tableRenders, tableFromStmt);


        if (showAll) {

            gotSearch = true;

        } else {

            if (isNameSearch()) {
                gotSearch = true;

                switch (nameType) {

                    case kIsType:

                        table.addCriterium("(service_name = \"" + name + "\")");

                        break;

                    case kInType:

                        table.addCriterium("(service_name like \"%" + name + "%\")");

                        break;


                    default:

                        table.addCriterium("(service_name = \"" + name + "\")");

                        nameType = kIsType;

                }

            }


        }

        //if(gotSearch)

        //{

        //	table.addCriterium("client_fkey="+ExtranetServlet.getSessionString(req,ExtranetServlet.kExtranetClientID));



        //}else

        //{

        //	table.addCriterium("client_fkey=0");

        //}



        showAll = false;

        table.setDescription("");

        //check for find criteria



        table.setPageNum(ExtranetServlet.getIntegerParam(req, WebTable.kTableShowPage, 1));

        table.setOrderCol(ExtranetServlet.getIntegerParam(req, WebTable.kTableSortColumn, -1));


        return table.getTable(req, resp);


    }


}
