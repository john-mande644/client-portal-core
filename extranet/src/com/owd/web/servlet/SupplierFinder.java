package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SupplierFinder {
private final static Logger log =  LogManager.getLogger();


    String[] tableColumnNames = {" ", "Name", "SKUs", "Units"};

    String[] tableColumnDefs = {"supplier_id", "MAX(supp_name)", "count(distinct(inventory_id))",

                                "sum(qty_on_hand)"};

    String tableFromStmt = "from owd_supplier s left outer join owd_inventory join owd_inventory_oh on inventory_fkey = inventory_id on supp_fkey = supplier_id ";

    String[] tableLinkStarts = {SupplierManager.kParamSupplierMgrAction + "=" + SupplierManager.kParamSupplierEditAction + "&" + SupplierManager.kparamSupplierID + "=", "", "", "", ""};

    String[] tableLinkEnds = {"Edit</A>", "", "", "", ""};

    int[] tableLinkMids = {1, 0, 0, 0, 0};


    public SupplierFinder() {


    }


    public static final String kBlank = "";


    private static final int kIsType = 1;

    private static final int kInType = 2;

    private static final int kGreaterThanType = 3;

    private static final int kLessThanType = 4;


    public static final String kNameSearchFlag = "doname";

    public static final String kNameSearchValue = "namevalue";

    public static final String kNameSearchType = "nametype";


    private int nameType = 1;


    public static SupplierFinder parseRequest(HttpServletRequest req, HttpServletResponse resp) {

        SupplierFinder finder = new SupplierFinder();


        return finder;

    }


    public String showResults(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        WebTable table = new WebTable();


        table.setSQLDefs(tableColumnNames, tableColumnDefs, tableLinkStarts, tableLinkMids, tableLinkEnds, tableFromStmt);


        table.addCriterium("s.client_fkey=" + ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));


        table.setGroupBy("supplier_id");

        table.setDescription("");

        //check for find criteria



        table.setPageNum(ExtranetServlet.getIntegerParam(req, WebTable.kTableShowPage, 1));

        table.setOrderCol(ExtranetServlet.getIntegerParam(req, WebTable.kTableSortColumn, -1));


        return table.getTable(req, resp);


    }


}
