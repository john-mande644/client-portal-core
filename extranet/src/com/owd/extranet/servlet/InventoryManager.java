package com.owd.extranet.servlet;

import com.owd.core.managers.LotManager;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdInventory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CSVReader;
import com.owd.core.MultipartRequest;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.Supplier;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdFacility;
import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Map;

import static com.owd.core.managers.InventoryManager.checkAndSetBulkySkuForOwdInventory;

public class InventoryManager extends ExtranetManager {
    private final static Logger log = LogManager.getLogger();

    public static final String kParamInvMgrAction = "imgr";
    public static final String kParamInvMgrSubAction = "sub";
    public static final String kParamInvQuery = "qryname";
    public static final String kParamInvExploreAction = "explore";
    public static final String kParamInvCreateAction = "new";
    public static final String kParamInvEditAction = "edit";
    public static final String kParamInvFindAction = "find";
    public static final String kParamInvSaveAction = "save";
    public static final String kParamInvDeleteAction = "delete";
    public static final String kParamInvFindAdvancedAction = "findadv";
    public static final String kParamInvBulkLoadAction = "bulkload";
    public static final String kParamInvBulkLoadActionProcess = "bulkloadprocess";
    public static final String kParamInvBulkLoadActionSave = "bulkloadsave";
    public static final String kParamInvBulkLoadFileName = "bulkloadname";


    public static final String kParamInvQuickFind = "qf";
    public static final String kparamInvID = "iid";

    public static final String kCurrentFinder = "currInvFinder";

    private static final String[] bulkLoadColumns = {"SKU", "Price", "Title", "Supplier", "Keyword",
            "Description", "Notes", "Color", "Size", "Reorder Level",
            "Harmonized Code", "Supplier Cost", "Supplier Name", "Weight (lbs)", "Customs Description",
            "Customs Value", "On Hand", "Backordered", "Packing Instructions", "Bulky Pick", "Bulky Pack"};


    String[] tableColumnNames = {"ID", "Item Number (SKU)", "Available", "Description"};
    String[] tableLinkStarts = {kParamInvMgrAction + "=" + kParamInvEditAction + "&" + kparamInvID + "=", "", "", ""};
    String[] tableLinkEnds = {"</A>", "", "", ""};
    int[] tableLinkMids = {1, 0, 0, 0};
    String[] tableColumnDefs = {"inventory_id", "inventory_num", "ISNULL(o.qty_on_hand,0)", "description"};
    String tableFromStmt = "from owd_inventory i left outer join owd_inventory_oh o on (inventory_id = inventory_fkey) ";


    public String[] getManagerCreators(HttpServletRequest req) {
        String[] urls = {""};


        return urls;
    }

    public String[] getManagerActions(HttpServletRequest req) {
        String[] urls = {"", "", "", "", ""};

        urls[0] = "<A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + ExtranetServlet.kParamAdminAction + "=" + ExtranetServlet.kParamChangeMgrAction + "&" + ExtranetServlet.kParamChangeMgrTo + "=" + ExtranetManager.kInvMgrName + "&" + kParamInvMgrAction + "=" + kParamInvExploreAction + "\" >Search</A>";
        urls[1] = "<A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + ExtranetServlet.kParamAdminAction + "=" + ExtranetServlet.kParamChangeMgrAction + "&" + ExtranetServlet.kParamChangeMgrTo + "=" + ExtranetManager.kInvMgrName + "&" + kParamInvMgrAction + "=" + kParamInvCreateAction + "\" >Add New SKU</A>";
        urls[2] = "<A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + ExtranetServlet.kParamAdminAction + "=" + ExtranetServlet.kParamChangeMgrAction + "&" + ExtranetServlet.kParamChangeMgrTo + "=" + ExtranetManager.kInvMgrName + "&" + kParamInvMgrAction + "=" + kParamInvBulkLoadAction + "\" >Batch Add/Update</A>";
        urls[3] = "<A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "/inventory.csv?" + ExtranetServlet.kParamChangeMgrTo
                + "=" + ExtranetManager.kInvMgrName + "&" + ExtranetServlet.kParamAdminAction + "=" + ExtranetServlet.kParamDownloadAction + "\" >Download All SKUs</A>";
        if (ExtranetServlet.getSessionFlag(req, ExtranetServlet.kExtranetInternalFlag)) {

        } else {
            urls[4] = "<a href=\"https://reports.owd.com/LogiAdHoc/rdPage.aspx?rdReport=ahReport509\" target=\"_blank\">Inventory by Facility</a>";

        }

        return urls;
    }

    public String[] getManagerQueries(HttpServletRequest req) {
        String[] urls = {"", "", ""};

        urls[0] = "<A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvFindAction
                + "&" + kParamInvQuery + "=Low Inventory" + "\" >Low Inventory</A>";
        urls[1] = "<A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvFindAction
                + "&" + kParamInvQuery + "=Backordered" + "\" >Backordered</A>";
        urls[2] = "<A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvFindAction
                + "&" + kParamInvQuery + "=Sales" + "\" >Sales</A>";

        return urls;
    }

    public String[] getManagerReports(HttpServletRequest req) {
        String[] urls = {""};

        urls[0] = "<B>N/A</B>";

        return urls;
    }

    public void download(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("download begin");
        resp.setContentType("application/comma-separated-values");
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            cxn = ConnectionManager.getConnection();

				/*
			String sql = "select \" \"+ISNULL(inventory_num,\"\") as sku,ISNULL(price,0),ISNULL(description,\"\"),ISNULL(mfr_part_num,\"\"),"
			+"ISNULL(keyword,\"\"),long_desc,"
			+"ISNULL(CONVERT(varchar(255),notes),\"\"),ISNULL(item_color,\"NA\"),ISNULL(item_size,\"\"),ISNULL(qty_reorder,0),"
			+"ISNULL(harm_code,\"\"),ISNULL(supp_cost,0.0),ISNULL(supp_fkey,0),ISNULL(weight_lbs,0.0)"
			+" from owd_inventory join owd_inventory_oh on inventory_id = inventory_fkey where client_fkey = "
			+ExtranetServlet.getSessionString(req,ExtranetServlet.kExtranetClientID);
			*/


            String sql = "select max(i.inventory_num) as sku,max(ISNULL(i.price,0.00)),max(ISNULL(i.description,'')),max(ISNULL(mfr_part_num,'')),"
                    + " max(ISNULL(keyword,'')),i.inventory_id,"
                    + " max(ISNULL(CONVERT(varchar(255),notes),'')),max(ISNULL(i.item_color,'')),max(ISNULL(i.item_size,'')),max(ISNULL(qty_reorder,0)),"
                    + " max(ISNULL(harm_code,'')),max(ISNULL(supp_cost,0.0)),max(ISNULL(supp_fkey,0)),max(ISNULL" +
                    "(weight_lbs,0.0)),max(ISNULL(i.customs_desc,'')),max(ISNULL(i.customs_value,0.00)),max(ISNULL(o.qty_on_hand,0)),sum(ISNULL(l.quantity_request,0)),max(ISNULL(instructions,'')),"
                    + " i.is_bulky_pick, i.is_bulky_pack"
                    + " from owd_inventory i (NOLOCK) left outer join special_instructions (NOLOCK) on external_id=inventory_id and activity_type='INVENTORY-PACK'"
                    + " left outer join owd_inventory_oh o (NOLOCK) on (i.inventory_id = inventory_fkey)"
                    + " left outer join owd_line_item l (NOLOCK)"
                    + " join owd_order ord (NOLOCK) on (l.order_fkey = ord.order_id and  ord.is_void=0 and ord.is_backorder=1 and ord.post_date IS NULL "
                    + " and  ord.is_future_ship=0 and ord.client_fkey =?)"
                    + " on (l.inventory_id = i.inventory_id and l.quantity_actual = 0 and l.quantity_request > 0)"
                    + " where i.client_fkey = ? and is_active = 1 "
                    + " group by i.inventory_id, i.is_bulky_pick, i.is_bulky_pack";

            log.debug(sql);
            stmt = cxn.prepareStatement(sql);
            stmt.setInt(1, new Integer(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue());
            stmt.setInt(2, new Integer(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue());

            stmt.executeQuery();
            log.debug("download sql");
            rs = stmt.getResultSet();
            PrintWriter out = resp.getWriter();
            out.write("\"" + bulkLoadColumns[0] + "\",\"" + bulkLoadColumns[1] + "\",\"" + bulkLoadColumns[2] + "\",\"" +
                    bulkLoadColumns[3] + "\",\"" + bulkLoadColumns[4] + "\",\"" + bulkLoadColumns[5] + "\",\"" + bulkLoadColumns[6] + "\",\"" +
                    bulkLoadColumns[7] + "\",\"" + bulkLoadColumns[8] + "\",\"" + bulkLoadColumns[9] + "\",\"" + bulkLoadColumns[10] + "\",\"" +
                    bulkLoadColumns[11] + "\",\"" + bulkLoadColumns[12] + "\",\"" + bulkLoadColumns[13] + "\",\"" + bulkLoadColumns[14] + "\",\"" + bulkLoadColumns[15] + "\",\"" + bulkLoadColumns[16] + " (ignored for import)\",\"" + bulkLoadColumns[17] + " (ignored for import)\",\"" + bulkLoadColumns[18] +
                    " (ignored for import)\",\"" + bulkLoadColumns[19] + " (ignored for import)\",\"" + bulkLoadColumns[20] +"\"\r\n");
            while (rs.next()) {
                out.write("\"" + rs.getString(1).replace('"', ' ') + "\",\"" + rs.getString(2).replace('"', ' ') + "\",\"" + rs.getString(3).replace('"', ' ') + "\",\"" +
                        rs.getString(4).replace('"', ' ') + "\",\"" + rs.getString(5).replace('"', ' ') + "\",\"" + getLongDescriptionForInventoryID(rs.getString(6)).replace('"', ' ').replace('\r', ' ').replace('\n', ' ') + "\",\"" + rs.getString(7).replace('"', ' ').replace('\r', ' ').replace('\n', ' ') + "\",\"" +
                        rs.getString(8).replace('"', ' ') + "\",\"" + rs.getString(9).replace('"', ' ') + "\",\"" + rs.getString(10).replace('"', ' ') + "\",\"" + rs.getString(11).replace('"', ' ') + "\",\"" +
                        rs.getString(12).replace('"', ' ') + "\",\"" + Supplier.getSupplierNameForID(rs.getString(13).replace('"', ' '), true) +
                        "\",\"" + rs.getString(14).replace('"', ' ') + "\",\"" + rs.getString(15).replace('"', ' ') + "\",\"" + OWDUtilities.roundFloat(new Float(rs.getString(16)).floatValue()) +
                        "\",\"" + rs.getString(17).replace('"', ' ') + "\",\"" + rs.getString(18).replace('"', ' ') + "\",\"" + rs.getString(19).replace('"', ' ') +
                        "\",\"" + rs.getString(20).replace('"', ' ') + "\",\"" + rs.getString(21).replace('"', ' ') +"\"\r\n");
            }
            log.debug("download done");
            rs.close();
            stmt.close();
            cxn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception ex) {
            }
        }
        log.debug("download exit");


    }

    String getLongDescriptionForInventoryID(String inventoryID) {
        Connection cxn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String desc = "";

        try {
            cxn = ConnectionManager.getConnection();

            String sql = "select long_desc from owd_inventory where inventory_id = " + inventoryID;


            stmt = cxn.createStatement();

            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                desc = rs.getString(1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception ex) {
            }
        }

        return desc;

    }

    public String getCurrentAction(HttpServletRequest req) {
        String currAction = ExtranetServlet.getStringParam(req, kParamInvMgrAction, kParamInvExploreAction);
        if (currAction.equals(kParamInvExploreAction)) {
            return "Searching Inventory";
        } else if (currAction.equals(kParamInvCreateAction)) {
            return "Creating Inventory Item";
        } else if (currAction.equals(kParamInvEditAction)) {

            return "Editing Inventory Item";
        } else if (currAction.equals(kParamInvFindAction)) {
            return "Inventory Search Results";
        }

        return "";

    }


    public void getManagerContent(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter out = resp.getWriter();
        out.write("<!-- content header -->");
        out.write("<TABLE WIDTH=100%><TR><TD align=left width=50%><fontx SIZE=\"-1\"><B>" + getCurrentAction(req) + "</B></FONT>");
        out.write("</TD>");
        out.write("<TD NOWRAP WIDTH=\"50%\" ALIGN=RIGHT><fontx SIZE=\"-1\">");
        out.write("");
        String currAction = ExtranetServlet.getStringParam(req, kParamInvMgrAction, kParamInvExploreAction);
		
		/*
		if(currAction.equals(kParamInvExploreAction) | currAction.equals(kParamInvFindAction))
		{
			out.write("<FORM METHOD=POST ACTION="+req.getContextPath()+ExtranetServlet.kExtranetURLPath+
					"><INPUT TYPE=HIDDEN NAME=\""+kParamInvMgrAction+"\" VALUE=\""+
					kParamInvFindAction+"\">");
			out.write("Find Inventory:&nbsp;<INPUT TYPE=\"text\" NAME=\""+kParamInvQuickFind+"\" SIZE=\"30\"  onChange=\"this.form.submit()\">&nbsp;<INPUT TYPE=SUBMIT NAME=SUBMIT VALUE=GO>");
			out.write("<BR>");
			out.write("");
			//out.write("<A HREF=\""+req.getContextPath()+ExtranetServlet.kExtranetURLPath+"?"+kParamInvMgrAction+"="+kParamInvFindAdvancedAction+"\" >Advanced</A>&nbsp;");
			out.write("");
		}
		*/
        out.write("</FONT>");
        out.write("</TD></FORM>");
        out.write("</TR>");
        out.write("");
        out.write("<!-- main content area --></TABLE>");

        //do real work here


        if (currAction.equals(kParamInvExploreAction)) {
            try {
                req.setAttribute(kCurrentFinder, InventoryFinder.parseRequest(req, resp));
                req.getRequestDispatcher("findinventory.jsp").include(req, resp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (currAction.equals(kParamInvFindAction)) {

            String qfstr = ExtranetServlet.getStringParam(req, kParamInvQuickFind, "");
            String qrystr = ExtranetServlet.getStringParam(req, kParamInvQuery, "");


            if (qrystr.equals("Low Inventory")) {
                WebTable table = new WebTable();


                String[] qtableColumnNames = {"ID", "Item Number (SKU)", "Count", "Reorder At", "Description"};
                String[] qtableLinkStarts = {kParamInvMgrAction + "=" + kParamInvEditAction + "&" + kparamInvID + "=", "", "", "", ""};
                String[] qtableLinkEnds = {"</A>", "", "", "", ""};
                int[] qtableLinkMids = {1, 0, 0, 0, 0};
                String[] qtableColumnDefs = {"i.inventory_id", "i.inventory_num", "qty_on_hand", "qty_reorder", "i.description"};
                String qtableFromStmt = "from owd_inventory i join owd_inventory_oh h "
                        + "on (inventory_id = inventory_fkey and qty_on_hand <= qty_reorder) ";

                table.setSQLDefs(qtableColumnNames, qtableColumnDefs, qtableLinkStarts, qtableLinkMids, qtableLinkEnds, qtableFromStmt);

                table.addCriterium("i.client_fkey=" + ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));


                table.setDescription("Items Below Reorder Levels");
                //check for find criteria

                table.setPageNum(ExtranetServlet.getIntegerParam(req, WebTable.kTableShowPage, 1));
                table.setOrderCol(ExtranetServlet.getIntegerParam(req, WebTable.kTableSortColumn, -1));

                table.getTable(req, resp);
            } else if (qrystr.equals("Backordered")) {
                WebTable table = new WebTable();


                String[] qtableColumnNames = {"ID", "Reference", "Backordered", "# Orders", "Description"};
                String[] qtableLinkStarts = {kParamInvMgrAction + "=" + kParamInvEditAction + "&" + kparamInvID + "=", "", "", "", ""};
                String[] qtableLinkEnds = {"</A>", "", "", "", ""};
                int[] qtableLinkMids = {1, 0, 0, 0, 0};
                String[] qtableColumnDefs = {"i.inventory_id", "i.inventory_num", "sum(quantity_request)", "count(distinct(order_id))", "i.description"};
                String qtableFromStmt = "from owd_inventory i join owd_line_item l "
                        + "join owd_order o on (o.order_id = l.order_fkey "
                        + "and o.is_void = 0 and o.is_backorder=1 and o.is_future_ship = 0 and o.post_date IS NULL) "
                        + "on (i.inventory_id = l.inventory_id and l.quantity_back = 0 and l.quantity_actual = 0)";

                table.setSQLDefs(qtableColumnNames, qtableColumnDefs, qtableLinkStarts, qtableLinkMids, qtableLinkEnds, qtableFromStmt);

                table.addCriterium("i.client_fkey=" + ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));


                table.setDescription("Currently Backordered Items");
                table.setGroupBy("i.inventory_id, i.inventory_num, i.description");
                //check for find criteria

                table.setPageNum(ExtranetServlet.getIntegerParam(req, WebTable.kTableShowPage, 1));
                table.setOrderCol(ExtranetServlet.getIntegerParam(req, WebTable.kTableSortColumn, -1));

                table.getTable(req, resp);
            } else if (qrystr.equals("Sales")) {
                WebTable table = new WebTable();

                String[] qtableColumnNames = {"ID", "Item Number (SKU)", "Sold", "Description"};
                String[] qtableLinkStarts = {kParamInvMgrAction + "=" + kParamInvEditAction + "&" + kparamInvID + "=", "", "", ""};
                String[] qtableLinkEnds = {"</A>", "", "", ""};
                int[] qtableLinkMids = {1, 0, 0, 0};
                String[] qtableColumnDefs = {"l.inventory_id", "MAX(l.inventory_num)", "sum(quantity_actual)", "MAX(l.description)"};
                String qtableFromStmt = "from owd_order o join owd_line_item l "
                        + "on (o.order_id = l.order_fkey and quantity_actual>0)";

                table.setSQLDefs(qtableColumnNames, qtableColumnDefs, qtableLinkStarts, qtableLinkMids, qtableLinkEnds, qtableFromStmt);

                table.addCriterium("o.client_fkey=" + ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
                table.addCriterium("o.is_void = 0");
                table.addCriterium("o.post_date IS NOT NULL");


                table.setDescription("Sales To Date");
                table.setGroupBy("l.inventory_id");
                //check for find criteria

                int gotono = ExtranetServlet.getIntegerParam(req, WebTable.kTableShowPage, 0);
                if (gotono != 0)
                    table.setPageNum(gotono);
                else
                    table.setPageNum(ExtranetServlet.getIntegerParam(req, WebTable.kTableShowPage, 1));
                table.setOrderCol(ExtranetServlet.getIntegerParam(req, WebTable.kTableSortColumn, -1));

                table.getTable(req, resp);
            } else if (qfstr.equals("")) {
                if (false) //is advanced
                {

                } else {
                    //no criteria
                    showTable(req, resp, null, "(No search specified, showing all...)");
                }


            } else {
                String[] crits = {"(inventory_num like \'%" + qfstr + "%\' OR description like \'%" + qfstr + "%\')"};

                //if quickfind
                showTable(req, resp, crits, "Find items with SKU or Title containing " + qfstr);


            }
        } else if (currAction.equals(kParamInvBulkLoadAction)) {
            String clientID = ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID);
            String subAction = ExtranetServlet.getStringParam(req, kParamInvMgrSubAction, "");
            StringBuffer form = new StringBuffer();
            form.append("<fontx size=-1><B>Inventory Bulk Loader</B><HR>");

            if (subAction.equals(kParamInvBulkLoadActionProcess)) {
                ////log.debug("processing inv bulk load");
                File uploadFile = null;
                BufferedReader reader = null;
                String tomcatPath = System.getProperty("catalina.base");
                if (tomcatPath == null) tomcatPath = "";
                if (tomcatPath.length() < 1) tomcatPath = ".";

                MultipartRequest fileSource = new MultipartRequest(req, tomcatPath + File.separator + "uploads", 1024 * 1024);
                Enumeration files = fileSource.getFileNames();

                if (!files.hasMoreElements()) {
                    form.append("<fontx size=-1 color=\"red\"><B>No file was received!<BR>Please check your file and try again.</B></FONT><HR><P><A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvBulkLoadAction + "\" >Back to Bulk Loader</A>");

                } else {

                    uploadFile = fileSource.getFile((String) files.nextElement());

                    if (uploadFile == null) {
                        form.append("<fontx size=-1 color=\"red\"><B>No file was received!<BR>Please check your file and try again.</B></FONT><HR><P><A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvBulkLoadAction + "\" >Back to Bulk Loader</A>");
                    } else {

                        reader = new BufferedReader(new FileReader(uploadFile));
                        String newfilename = req.getUserPrincipal().getName() + Calendar.getInstance().getTime().getTime() + ".csv";
                        uploadFile.renameTo(new File(tomcatPath + File.separator + "uploads" + File.separator + newfilename));

                        CSVReader data = new CSVReader(reader, true);


                        int importColumnCount = bulkLoadColumns.length;

                        if (data.columnCount < (importColumnCount - 3)) {
                            form.append("<fontx size=-1 color=\"red\"><B>There must be at least " + (importColumnCount - 3) + " columns in the file; you only provided " + data.columnCount
                                    + " columns. You can download your current inventory list using the link at the left to obtain a sample file in the correct format.<BR>Please check your file and try again.</B></FONT><HR><P><A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvBulkLoadAction + "\" >Back to Bulk Loader</A>");

                        } else {
                            form.append("<fontx size=-1 ><B>Bulk Loader Preview</B><BR><TABLE border=1><TR>");
                            form.append("<TH><fontx color=\"#000000\" size=\"-1\">Action</TH>");
                            for (int i = 0; i < importColumnCount; i++) {
                                if (i != 16 && i != 17) {
                                    form.append("<TH><fontx color=\"#000000\" size=\"-2\">" + (bulkLoadColumns[i]) + "</TH>");
                                }
                            }
                            form.append("</TR> ");
                            for (int row = 0; row < data.getRowCount(); row++) {

                                form.append("<TR> ");
                                for (int col = 0; col < importColumnCount; col++) {
                                    if (col != 16 && col != 17) {
                                        if (col == 0) {
                                            try {
                                                if (data.getRowSize(row) < (importColumnCount - 3))
                                                    throw new Exception("Row rejected - wrong number of columns. You can download your current inventory list using the link at the left to obtain a sample file in the correct format.");

                                                form.append("<TD><fontx  color=\"#000000\" size=\"-2\"><B>"
                                                        + (ConnectionManager.InventoryExists(data.getStrValue(col, row, ""), clientID) ? "<fontx COLOR=green>UPDATE</FONT>" : "<fontx COLOR=red>NEW</FONT>") + "</B></TD>");
                                            } catch (Exception ex) {
                                                form.append("<TD><fontx  color=\"#000000\" size=\"-2\"><B>"
                                                        + "<fontx COLOR=red>[ERROR]<BR>" + ex.getMessage() + "</FONT>" + "</B></TD>");
                                            }
                                        }
                                        form.append("<TD><fontx  color=\"#000000\" size=\"-2\">" + data.getStrValue(col, row, "") + "</TD>");
                                    }
                                }
                                form.append("</TR> ");
                            }
                            form.append("</TABLE><HR><FORM METHOD=POST ACTION=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "\">");
                            form.append("<INPUT TYPE=\"HIDDEN\" name=\"" + ExtranetServlet.kParamAdminAction + "\" value=\"" + ExtranetServlet.kParamChangeMgrAction + "\" />\n" +
                                    "    <INPUT TYPE=\"HIDDEN\" name=\"" + ExtranetServlet.kParamChangeMgrTo + "\" value=\"" + ExtranetManager.kInvMgrName + "\" />\n");
                            form.append("<INPUT TYPE=HIDDEN NAME=" + kParamInvMgrSubAction + " VALUE=" + kParamInvBulkLoadActionSave + ">");
                            form.append("<INPUT TYPE=HIDDEN NAME=" + kParamInvMgrAction + " VALUE=" + kParamInvBulkLoadAction + ">");
                            form.append("<INPUT TYPE=HIDDEN NAME=" + kParamInvBulkLoadFileName + " VALUE=\"" + newfilename + "\">");
                            form.append("<INPUT TYPE=SUBMIT NAME=SUBMIT VALUE=\"Save These Changes\"></FORM>");
                        }
                    }
                }
                resp.getWriter().write(form.toString());


            } else if (subAction.equals(kParamInvBulkLoadActionSave)) {

                Connection cxn = null;
                String fName = ExtranetServlet.getStringParam(req, kParamInvBulkLoadFileName, "");

                if (fName.equals("")) {

                } else {
                    String tomcatPath = System.getProperty("catalina.base");
                    if (tomcatPath == null) tomcatPath = "";
                    if (tomcatPath.length() < 1) tomcatPath = ".";
                    File fileRef = new File(tomcatPath + File.separator + "uploads" + File.separator + fName);

                    log.debug("fileref=" + fileRef);

                    int importColumnCount = bulkLoadColumns.length;

                    if (fileRef.exists()) {
                        BufferedReader reader = new BufferedReader(new FileReader(fileRef));
                        CSVReader data = new CSVReader(reader, true);
                        //check and import data
                        Inventory item = null;


                        if (data.columnCount < (importColumnCount - 3)) {
                            form.append("<fontx size=-1 color=\"red\"><B>There must be " + (importColumnCount - 3) + " columns in the file; you only provided " + data.columnCount
                                    + " columns. You can download your current inventory list using the link at the left to obtain a sample file in the correct format.<BR>Please check your file and try again.</B></FONT><HR><P><A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvBulkLoadAction + "\" >Back to Bulk Loader</A>");

                        } else {
                            form.append("<fontx size=-1 color=\"red\"><B>Changes Applied...</B><BR><TABLE border=1><TR>");
                            form.append("<TH><fontx face=\"Geneva, Verdana, Helvetica\" color=\"#000000\" size=\"-2\">Action</TH>");
                            for (int i = 0; i < importColumnCount; i++) {
                                if (i != 16 && i != 17) {
                                    form.append("<TH><fontx face=\"Geneva, Verdana, Helvetica\" color=\"#000000\" size=\"-2\">" + (bulkLoadColumns[i]) + "</TH>");
                                }
                            }
                            form.append("</TR> ");
                            boolean rowError = false;
                            String errorMessage = "";
                            for (int row = 0; row < data.getRowCount(); row++) {
                                rowError = false;

                                form.append("<TR> ");
                                boolean doUpdate = false;
                                try {
                                    //verify size
                                    if (data.getRowSize(row) < (importColumnCount - 3))
                                        throw new Exception("Row rejected - wrong number of columns - there should be at least " + (importColumnCount - 3) + " columns in each row. You can download your current inventory list using the link at the left to obtain a sample file in the correct format.");

                                    cxn = ConnectionManager.getConnection();
                                    doUpdate = (ConnectionManager.InventoryExists(data.getStrValue(0, row, ""), clientID));


                                    //add/update inventory
                                    if (doUpdate) {

                                        item = Inventory.dbloadByPart(cxn, data.getStrValue(0, row, ""), clientID);
                                        if (item == null) {
                                            //error loading
                                            throw new Exception("Error loading item information; internal");
                                        } else {
                                            //do update
                                            item.mfr_part_num = data.getStrValue(3, row, "");
                                            item.keyword = data.getStrValue(4, row, "");
                                            item.price = data.getFloatValue(1, row, (float) 0.0);
                                            item.item_color = data.getStrValue(7, row, "");
                                            item.item_size = data.getStrValue(8, row, "");
                                            item.notes = data.getStrValue(6, row, "");
                                            item.description = data.getStrValue(2, row, "");
                                            item.long_desc = data.getStrValue(5, row, "");
                                            item.qty_reorder = data.getIntValue(9, row, 0);
                                            item.harm_code = data.getStrValue(10, row, "");
                                            item.supp_cost = data.getFloatValue(11, row, (float) 0.0);
                                            item.weight_lbs = data.getFloatValue(13, row, (float) 0.0);
                                            item.supp_fkey = new Integer(Supplier.getSupplierIDForName(cxn, data.getStrValue(12, row, ""), clientID, true)).intValue();
                                            item.customs_desc = data.getStrValue(14, row, "");
                                            item.customs_value = data.getFloatValue(15, row, (float) 0.0);
                                            item.packing_instructions = data.getStrValue(18, row, "");
                                            item.url = data.getStrValue(19, row, "");
                                        }
                                    } else {
                                        //add new
                                        item = new Inventory("0",
                                                clientID,
                                                data.getStrValue(0, row, ""), //((String)rowData.elementAt(0)).trim(),
                                                "",//data.getStrValue(3,row,""),//((String)rowData.elementAt(3)).trim(),
                                                data.getStrValue(3, row, ""), //((String)rowData.elementAt(4)).trim(),
                                                data.getStrValue(4, row, ""), //((String)rowData.elementAt(5)).trim(),
                                                data.getStrValue(1, row, "0.000"), //((String)rowData.elementAt(1)).trim(),
                                                data.getStrValue(7, row, ""), //((String)rowData.elementAt(8)).trim(),
                                                data.getStrValue(8, row, ""), //((String)rowData.elementAt(9)).trim(),
                                                "0",
                                                data.getStrValue(6, row, ""), //((String)rowData.elementAt(7)).trim(),
                                                data.getStrValue(2, row, ""), //((String)rowData.elementAt(2)).trim(),
                                                data.getStrValue(5, row, ""), //((String)rowData.elementAt(6)).trim(),
                                                data.getStrValue(9, row, "0"), //((String)rowData.elementAt(10)).trim(),
                                                "1",
                                                "UNKNOWN",
                                                "0",
                                                "",
                                                "",
                                                "",
                                                "",
                                                data.getStrValue(10, row, ""),
                                                data.getStrValue(11, row, "0.0"),
                                                Supplier.getSupplierIDForName(cxn, data.getStrValue(12, row, ""), clientID, true),
                                                data.getStrValue(13, row, "0.0"),
                                                "0",
                                                "",
                                                "",
                                                "",
                                                "",
                                                "0",
                                                data.getStrValue(14, row, ""),
                                                data.getStrValue(15, row, "0.0"),
                                                "0",
                                                "0",
                                                data.getStrValue(18, row, ""), "", "", "0", "0", "0", "0", "0", "0", "", "", "","", "0","0","0","0","0","0.0","0.0"
                                        );

                                    }

                                    item.dbupdate(cxn);

                                    cxn.commit();

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    try {
                                        cxn.rollback();
                                    } catch (Exception e) {
                                    }
                                    rowError = true;
                                    errorMessage = ex.getMessage();
                                } finally {
                                    try {
                                        cxn.close();
                                    } catch (Exception exce) {
                                    }

                                }

                                if (rowError) {
                                    form.append("<TD><fontx face=\"Geneva, Verdana, Helvetica\" color=\"#000000\" size=\"-2\"><B><fontx COLOR=red>[ERROR]<BR>"
                                            + errorMessage + "</FONT></B></TD>");
                                } else {
                                    form.append("<TD><fontx face=\"Geneva, Verdana, Helvetica\" color=\"#000000\" size=\"-2\"><B>"
                                            + (doUpdate ? "<fontx COLOR=green>UPDATED!</FONT>" : "<fontx COLOR=green>CREATED!</FONT>") + "</B></TD>");
                                }

                                for (int rowIndex = 0; rowIndex < importColumnCount; rowIndex++) {
                                    if (rowIndex != 16 && rowIndex != 17) {
                                        form.append("<TD><fontx face=\"Geneva, Verdana, Helvetica\" color=\"#000000\" size=\"-2\">" + data.getStrValue(rowIndex, row, "") + "</TD>");
                                    }
                                }
                                form.append("</TR> ");


                            }
                            form.append("</TABLE>");
                        }

                        if (fileRef.exists())
                            fileRef.delete();

                    } else {
                        //file does not exist
                        form.append("<fontx size=-1 color=\"red\"><B>File not found - please start again...</B></FONT><HR><P><A HREF=\""
                                + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvBulkLoadAction + "\" >Back to Bulk Loader</A>");
                    }

                    resp.getWriter().write(form.toString());
                }


            } else {

                form.append("<P><fontx size=-1 ><B>Bulk Loader Instructions</B><P>The input file must be in comma-delimited (also known as Comma-Separated Value) format. The simplest way to create such files");
                form.append(" is to edit the information in Excel and select Save As..., then choose the comma-delimited format (*.csv) to save. You can download your current inventory list using the link at the left to obtain a sample file in the correct format. The ");
                form.append("file created for uploading should end in the extension \".csv\".<p>You can choose the <B>Download All</B> action at the left to obtain a sample file with your current inventory information.<P>");
                form.append("Please note that the files you download include columns for on hand and backorder quantities, but these columns are not used when uploading updates via this form.<HR> <P>");


                form.append("<FORM METHOD=POST  ENCTYPE=\"MULTIPART/FORM-DATA\" ACTION=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?"
                        + com.owd.extranet.servlet.InventoryManager.kParamInvMgrAction + "=" + com.owd.extranet.servlet.InventoryManager.kParamInvBulkLoadAction + "&"
                        + com.owd.extranet.servlet.InventoryManager.kParamInvMgrSubAction + "=" + com.owd.extranet.servlet.InventoryManager.kParamInvBulkLoadActionProcess + "\">");

                form.append("Select the Browse button to select the file from your local drive:<BR>");
                form.append("<INPUT TYPE=\"HIDDEN\" name=\"" + ExtranetServlet.kParamAdminAction + "\" value=\"" + ExtranetServlet.kParamChangeMgrAction + "\" />\n" +
                        "    <INPUT TYPE=\"HIDDEN\" name=\"" + ExtranetServlet.kParamChangeMgrTo + "\" value=\"" + ExtranetManager.kInvMgrName + "\" />\n");

                form.append("<fontx SIZE=\"2\"><INPUT TYPE=\"FILE\" size=\"60\" NAME=\"UploadFile\"><BR>");
                form.append("<fontx SIZE=\"-2\">Note: If a button labeled &quot;Browse...&quot; does not appear above, then your web browser does not support File Upload.<BR>");
                form.append("In that case, please upgrade your browser to a newer version.<P>");
                form.append("<INPUT TYPE=SUBMIT NAME=SUBMIT VALUE=\"Load New/Updated Inventory File\"></FORM>");


                resp.getWriter().write(form.toString());

            }


        } else if (currAction.equals(kParamInvEditAction)) {
            Connection cxn = null;

            try {

                cxn = com.owd.core.managers.ConnectionManager.getConnection();
                com.owd.core.business.order.Inventory item = com.owd.core.business.order.Inventory.getInventoryForID(cxn, ExtranetServlet.getIntegerParam(req, kparamInvID, 0) + "");
                getEditForm(item, req, resp);
            } catch (Exception ex) {

                ex.printStackTrace();
                ////log.debug(ex.getMessage());
            } finally {
                try {
                    cxn.close();
                } catch (Exception ex) {
                }
            }


        } else if (currAction.equals(kParamInvCreateAction)) {


            try {
                com.owd.core.business.order.Inventory item = new Inventory(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
                getEditForm(item, req, resp);
            } catch (Exception ex) {

                ex.printStackTrace();
                ////log.debug(ex.getMessage());
            } finally {

            }


        } else if (currAction.equals(kParamInvSaveAction)) {
            Connection cxn = null;
            com.owd.core.business.order.Inventory item = null;
            //log.debug("saving item");
            String subaction = ExtranetServlet.getStringParam(req, kParamInvMgrSubAction, "");

            try {

                cxn = ConnectionManager.getConnection();

                if (subaction.equals("Save New Item") || subaction.equals("Save Changes")) {

                    if (ExtranetServlet.getStringParam(req, Inventory.kdbInventoryPKName, "0").equals("0")) {
                        item = new Inventory("0",
                                ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID),
                                ExtranetServlet.getStringParam(req, Inventory.kdbInventoryRef, ""),
                                "",
                                ExtranetServlet.getStringParam(req, Inventory.kdbSupplierRef, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbKeyword, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbPriceEach, "0.0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbColor, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbSize, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbClientKey, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbNotes, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbDescription, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbLongDescription, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbReorder, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbIsActive, "0"), //is_active
                                "UNKNOWN",
                                "0",
                                "",
                                "",
                                "",
                                "",
                                ExtranetServlet.getStringParam(req, Inventory.kdbHarmonizedCode, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbSupplierCost, "0.0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbSupplierFKey, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbWeight, "0.0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbForceShipment, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbShipMethodUS, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbShipMethodPO, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbShipMethodCAN, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbShipMethodINT, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbIsDocuments, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbCustomsDesc, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbCustomsValue, "0.00"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbIsAutoInventory, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbIsNoScanRequired, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbPackingInstructions, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbUrl, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbDefaultFacility, ""),
                                ExtranetServlet.getStringParam(req, Inventory.kdbIsInsert, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbIsLotTracked, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbLength, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbWidth, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbHeight, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbCubicFeet, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbGroupName, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbUpcCode, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbIsbnCode, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbCaseQty, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbIsShipSystemWeight, "0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbIsBulkyPick,"0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbIsBulkyPack,"0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbBulkyPickOverride,"0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbBulkyPackOverride,"0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbBulkyPickFeeOverride,"0.0"),
                                ExtranetServlet.getStringParam(req, Inventory.kdbBulkyPackFeeOverride,"0.0"));
                        item.cubic_feet = (item.length/12) * (item.width/12) * (item.height/12);
                    } else {

                        item = com.owd.core.business.order.Inventory.getInventoryForID(cxn, ExtranetServlet.getStringParam(req, Inventory.kdbInventoryPKName, "0"));
                        //item.inventory_num = ExtranetServlet.getStringParam(req,Inventory.kdbInventoryRef,"");
                        //item.barcode_no = ExtranetServlet.getStringParam(req,Inventory.kdbInventoryBarcode,"");
                        item.mfr_part_num = ExtranetServlet.getStringParam(req, Inventory.kdbSupplierRef, "");
                        item.keyword = ExtranetServlet.getStringParam(req, Inventory.kdbKeyword, "");
                        item.price = new Float(ExtranetServlet.getStringParam(req, Inventory.kdbPriceEach, "0.0")).floatValue();
                        item.item_color = ExtranetServlet.getStringParam(req, Inventory.kdbColor, "");
                        item.item_size = ExtranetServlet.getStringParam(req, Inventory.kdbSize, "");
                        item.client_item_key = new Integer(ExtranetServlet.getStringParam(req, Inventory.kdbClientKey, "0")).intValue();
                        item.notes = ExtranetServlet.getStringParam(req, Inventory.kdbNotes, "");
                        item.description = ExtranetServlet.getStringParam(req, Inventory.kdbDescription, "");
                        item.long_desc = ExtranetServlet.getStringParam(req, Inventory.kdbLongDescription, "");
                        item.qty_reorder = new Integer(ExtranetServlet.getStringParam(req, Inventory.kdbReorder, "0")).intValue();
                        item.harm_code = ExtranetServlet.getStringParam(req, Inventory.kdbHarmonizedCode, "");
                        item.supp_cost = new Float(ExtranetServlet.getStringParam(req, Inventory.kdbSupplierCost, "0.0")).floatValue();
                        item.supp_fkey = new Integer(ExtranetServlet.getStringParam(req, Inventory.kdbSupplierFKey, "0")).intValue();
                        item.weight_lbs = new Float(ExtranetServlet.getStringParam(req, Inventory.kdbWeight, "0.0")).floatValue();
                        item.is_active = new Integer(ExtranetServlet.getStringParam(req, Inventory.kdbIsActive, "0")).intValue();
                        item.force_shipment = new Integer(ExtranetServlet.getStringParam(req, Inventory.kdbForceShipment, "0")).intValue();
                        item.force_method_us = ExtranetServlet.getStringParam(req, Inventory.kdbShipMethodUS, "");
                        item.force_method_po = ExtranetServlet.getStringParam(req, Inventory.kdbShipMethodPO, "");
                        item.force_method_can = ExtranetServlet.getStringParam(req, Inventory.kdbShipMethodCAN, "");
                        item.force_method_int = ExtranetServlet.getStringParam(req, Inventory.kdbShipMethodINT, "");
                        item.is_documents = new Integer(ExtranetServlet.getStringParam(req, Inventory.kdbIsDocuments, "0")).intValue();
                        item.customs_desc = ExtranetServlet.getStringParam(req, Inventory.kdbCustomsDesc, "");
                        item.customs_value = new Float(ExtranetServlet.getStringParam(req, Inventory.kdbCustomsValue, "0.0")).floatValue();
                        //volumetric billing check
                        //only OWD internal users can update.
                        if(ExtranetServlet.getSessionFlag(req, ExtranetServlet.kExtranetInternalFlag)) {
                            if (item.length > 0 && new Float(ExtranetServlet.getStringParam(req, Inventory.kdbLength, "0.0")).floatValue() == 0f) {
                                throw new Exception("Length cannot be 0 once set. Enter Value Greater than 0");
                            }
                            if (item.width > 0 && new Float(ExtranetServlet.getStringParam(req, Inventory.kdbWidth, "0.0")).floatValue() == 0f) {
                                throw new Exception("Width cannot be 0 once set. Enter Value Greater than 0");
                            }
                            if (item.height > 0 && new Float(ExtranetServlet.getStringParam(req, Inventory.kdbHeight, "0.0")).floatValue() == 0f) {
                                throw new Exception("Height cannot be 0 once set. Enter Value Greater than 0");
                            }
                            item.length = new Float(ExtranetServlet.getStringParam(req, Inventory.kdbLength, "0.0")).floatValue();
                            item.width = new Float(ExtranetServlet.getStringParam(req, Inventory.kdbWidth, "0.0")).floatValue();
                            item.height = new Float(ExtranetServlet.getStringParam(req, Inventory.kdbHeight, "0.0")).floatValue();
                            item.cubic_feet = (item.length / 12) * (item.width / 12) * (item.height / 12);
                        }
                        //editing previously saved record for this value is no longer allowed once it has become true
                        if (item.is_auto_inventory == 0) {
                            item.is_auto_inventory = new Integer(ExtranetServlet.getStringParam(req, Inventory.kdbIsAutoInventory, "0")).intValue();
                        }

                        item.no_scan = new Integer(ExtranetServlet.getStringParam(req, Inventory.kdbIsNoScanRequired, "0")).intValue();
                        item.packing_instructions = ExtranetServlet.getStringParam(req, Inventory.kdbPackingInstructions, "");
                        item.url = ExtranetServlet.getStringParam(req, Inventory.kdbUrl, "");
                        item.isInsert = Integer.parseInt(ExtranetServlet.getStringParam(req, Inventory.kdbIsInsert, "0"));
                        item.isLotTracked = Integer.parseInt(ExtranetServlet.getStringParam(req, Inventory.kdbIsLotTracked, "0"));
                        item.isShipSystemWeight = Integer.parseInt(ExtranetServlet.getStringParam(req, Inventory.kdbIsShipSystemWeight, "0"));



                       item.isBulkyPick = Integer.parseInt( ExtranetServlet.getStringParam(req, Inventory.kdbIsBulkyPick,"0"));
                       item.isBulkyPack = Integer.parseInt(ExtranetServlet.getStringParam(req, Inventory.kdbIsBulkyPack,"0"));
                       item.bulkyPickOverride = Integer.parseInt(ExtranetServlet.getStringParam(req, Inventory.kdbBulkyPickOverride,"0"));
                       item.bulkyPackOverride = Integer.parseInt(ExtranetServlet.getStringParam(req, Inventory.kdbBulkyPackOverride,"0"));
                       item.bulkyPickFeeOverride = new Float(ExtranetServlet.getStringParam(req, Inventory.kdbBulkyPickFeeOverride,"0.0"));
                       item.bulkyPackFeeOverride = new Float(ExtranetServlet.getStringParam(req, Inventory.kdbBulkyPackFeeOverride,"0.0"));

                    }
                    item.dbupdate(cxn);
                    cxn.commit();

                    OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, item.inventory_id);

                    checkAndSetBulkySkuForOwdInventory(inv);

                    resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvExploreAction));
                } else {
                    if (subaction.equals("Delete Item")) {

                        item = com.owd.core.business.order.Inventory.getInventoryForID(cxn, ExtranetServlet.getStringParam(req, Inventory.kdbInventoryPKName, "0"));
                        item.dbdelete(cxn);
                        cxn.commit();
                        resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvExploreAction));
                    } else {

                        throw new Exception("Request not recognized");
                    }


                }
                //redirect to the explorer page with this item listed


            } catch (Exception ex) {
                ex.printStackTrace();
                try {
                    cxn.rollback();
                    getEditForm(item, req, resp, ex.getMessage());
                } catch (Exception e) {
                }
            } finally {
                try {
                    cxn.close();
                } catch (Exception ex) {
                }
            }


        }


    }

    public void showTable(HttpServletRequest req, HttpServletResponse resp, String[] criteria, String description) throws IOException {


        WebTable table = new WebTable();


        table.setSQLDefs(tableColumnNames, tableColumnDefs, tableLinkStarts, tableLinkMids, tableLinkEnds, tableFromStmt);

        table.addCriterium("i.client_fkey=" + ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));

        if (criteria != null) {
            for (int i = 0; i < criteria.length; i++) {
                table.addCriterium(criteria[i]);
            }
        }
        table.setDescription((description == null ? "Showing All..." : description));
        //check for find criteria

        int gotono = ExtranetServlet.getIntegerParam(req, WebTable.kTableShowPage, 0);
        if (gotono != 0)
            table.setPageNum(gotono);
        else
            table.setPageNum(ExtranetServlet.getIntegerParam(req, WebTable.kTableShowPage, 1));
        table.setOrderCol(ExtranetServlet.getIntegerParam(req, WebTable.kTableSortColumn, -1));

        table.getTable(req, resp);

    }

    public void getManagerHeader(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter out = resp.getWriter();
        out.write("<TD VALIGN=\"TOP\">");
    }

    public void getManagerFooter(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter out = resp.getWriter();
        out.write("</TD></TR>");
        out.write("</TABLE>");
    }

    public void getEditForm(Inventory item, HttpServletRequest req, HttpServletResponse resp, String message) throws Exception {
        resp.getWriter().write("<HR><fontx color=red><B>ERROR: " + message + "</B></FONT><HR>");
        getEditForm(item, req, resp);
    }

    public void getEditForm(Inventory item, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        StringBuffer form = new StringBuffer();
        form.append("<TABLE border=0><FORM METHOD=POST ACTION=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?"
                + com.owd.extranet.servlet.InventoryManager.kParamInvMgrAction + "="
                + com.owd.extranet.servlet.InventoryManager.kParamInvSaveAction + "\">");
        form.append("<INPUT TYPE=\"HIDDEN\" name=\"" + ExtranetServlet.kParamAdminAction + "\" value=\"" + ExtranetServlet.kParamChangeMgrAction + "\" />\n" +
                "    <INPUT TYPE=\"HIDDEN\" name=\"" + ExtranetServlet.kParamChangeMgrTo + "\" value=\"" + ExtranetManager.kInvMgrName + "\" />\n");

        form.append("<TR><TD COLSPAN=4><HR><INPUT TYPE=HIDDEN NAME=" + Inventory.kdbInventoryPKName + " VALUE=" + item.inventory_id + ">");
        form.append("<INPUT TYPE=SUBMIT NAME=\"" + com.owd.extranet.servlet.InventoryManager.kParamInvMgrSubAction + "\" VALUE=\""
                + (item.isNew() ? "Save New Item" : "Save Changes")
                + "\">" + (item.isNew() ? "" : "&nbsp;&nbsp;<INPUT TYPE=SUBMIT NAME=\""
                + com.owd.extranet.servlet.InventoryManager.kParamInvMgrSubAction
                + "\" VALUE=\"Delete Item\">") + "<HR></TD></TR>");

        form.append("<TR><TD ALIGN=RIGHT><fontx size=\"-2\"><b>");
        form.append("SKU:&nbsp;</B></TD><TD ALIGN=LEFT>");
        if (item.isNew()) {
            form.append("<INPUT TYPE=TEXT NAME=inventory_num VALUE=\"" + StringEscapeUtils.escapeHtml(item.inventory_num) + "\" >");

        } else {
            form.append(StringEscapeUtils.escapeHtml(item.inventory_num));


        }

        form.append("</TD>");
        form.append("<TD ALIGN=RIGHT ><fontx  color=\"#000000\" size=\"-2\"><b>");
        form.append("Supplier:&nbsp;</B></TD><TD ALIGN=LEFT width=100%>");
        form.append("<INPUT TYPE=TEXT NAME=mfr_part_num VALUE=\"" + StringEscapeUtils.escapeHtml(item.mfr_part_num) + "\" >");
        form.append("</TD></TR>");

        form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
        form.append("Price:&nbsp;</B></TD><TD ALIGN=LEFT>");
        form.append("<INPUT TYPE=TEXT NAME=price VALUE=\"" + item.price + "\" >");
        form.append("</TD>");
        form.append("<TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
        form.append("Cost Price:&nbsp;</B></TD><TD ALIGN=LEFT>");
        form.append("<INPUT TYPE=TEXT NAME=supp_cost VALUE=\"" + item.supp_cost + "\" >");
        form.append("</TD></TR>");

        form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
        form.append("Supplier SKU:&nbsp;</B></TD><TD ALIGN=LEFT>");
        form.append("<INPUT TYPE=TEXT NAME=harm_code VALUE=\"" + StringEscapeUtils.escapeHtml(item.harm_code) + "\" >");
        form.append("</TD>");
        form.append("<TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
        form.append("Keyword:&nbsp;</B></TD><TD ALIGN=LEFT>");
        form.append("<INPUT TYPE=TEXT NAME=keyword VALUE=\"" + StringEscapeUtils.escapeHtml(item.keyword) + "\" >");
        form.append("</TD></TR>");


        form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
        form.append("Title:&nbsp;</B></TD><TD ALIGN=LEFT COLSPAN=3>");
        form.append("<INPUT TYPE=TEXT NAME=description SIZE=60 VALUE=\"" + StringEscapeUtils.escapeHtml(item.description) + "\">");
        form.append("</TD></TR>");
        form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
        form.append("Notes:&nbsp;</B></TD><TD ALIGN=LEFT COLSPAN=3>");
        form.append("<INPUT TYPE=TEXT NAME=notes SIZE=60 VALUE=\"" + StringEscapeUtils.escapeHtml(item.notes) + "\" >");
        form.append("</TD></TR>");

        form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
        form.append("Description:&nbsp;</B></TD><TD ALIGN=LEFT COLSPAN=3>");
        form.append("<fontx color=\"#000000\" size=\"-2\"><TEXTAREA NAME=long_desc rows=4 cols=60>" + StringEscapeUtils.escapeHtml(item.long_desc) + "</TEXTAREA>");
        form.append("</TD></TR>");


        form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
        form.append("Color:&nbsp;</B></TD><TD ALIGN=LEFT>");
        form.append("<INPUT TYPE=TEXT NAME=item_color VALUE=\"" + item.item_color + "\" >");
        form.append("</TD>");
        form.append("<TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
        form.append("Size:&nbsp;</B></TD><TD ALIGN=LEFT>");
        form.append("<INPUT TYPE=TEXT NAME=item_size VALUE=\"" + item.item_size + "\" >");
        form.append("</TD></TR>");

        form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
        form.append("Reorder&nbsp;At:&nbsp;</B></TD><TD ALIGN=LEFT>");
        form.append("<INPUT TYPE=TEXT NAME=qty_reorder VALUE=\"" + item.qty_reorder + "\" >");
        form.append("</TD>");
        form.append("<TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
        form.append("Weight&nbsp;(lbs):&nbsp;</B></TD><TD ALIGN=LEFT>");
        form.append("<INPUT TYPE=TEXT NAME=weight_lbs VALUE=\"" + item.weight_lbs + "\" >");
        form.append("</TD></TR>");
        if ((item.length == 0.00f && item.width == 0.00f && item.height == 0.00f && item.cubic_feet == 0.00f) || ExtranetServlet.getSessionFlag(req, ExtranetServlet.kExtranetInternalFlag)) {
            form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Length:&nbsp;</B></TD><TD ALIGN=LEFT>");
            form.append("<INPUT TYPE=TEXT NAME=i_length VALUE=\"" + item.length + "\" >");
            form.append("</TD>");
            form.append("<TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Width:&nbsp;</B></TD><TD ALIGN=LEFT>");
            form.append("<INPUT TYPE=TEXT NAME=i_width VALUE=\"" + item.width + "\" >");
            form.append("</TD></TR>");

            form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Height:&nbsp;</B></TD><TD ALIGN=LEFT>");
            form.append("<INPUT TYPE=TEXT NAME=i_height VALUE=\"" + item.height + "\" >");
            form.append("</TD>");
            form.append("<TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Cubic&nbsp;Feet:&nbsp;</B></TD><TD ALIGN=LEFT>");
            form.append("<INPUT TYPE=TEXT NAME=cubic_feet VALUE=\"" + item.cubic_feet + "\" >");
            form.append("</TD></TR>");
        } else {
            form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Length:&nbsp;</B></TD><TD ALIGN=LEFT>");
            form.append(item.length);
            form.append("</TD>");
            form.append("<TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Width:&nbsp;</B></TD><TD ALIGN=LEFT>");
            form.append(item.width);
            form.append("</TD></TR>");

            form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Height:&nbsp;</B></TD><TD ALIGN=LEFT>");
            form.append(item.height);
            form.append("</TD>");
            form.append("<TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Cubic&nbsp;Feet:&nbsp;</B></TD><TD ALIGN=LEFT>");
            form.append(item.cubic_feet);
            form.append("</TD></TR>");
        }
        form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
        form.append("Customs&nbsp;Desc:&nbsp;</B></TD><TD ALIGN=LEFT>");
        form.append("<INPUT TYPE=TEXT NAME=customs_desc maxlength=\"2048\" VALUE=\"" + StringEscapeUtils.escapeHtml(item.customs_desc) + "\" >");
        form.append("</TD>");
        form.append("<TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
        form.append("Customs&nbsp;Value&nbsp;($):&nbsp;</B></TD><TD ALIGN=LEFT>");
        form.append("<INPUT TYPE=TEXT NAME=customs_value VALUE=\"" + item.customs_value + "\" >");
        form.append("</TD></TR>");


        form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
        form.append("Contains Documents Only:&nbsp;</B></TD><TD ALIGN=LEFT>");
        form.append("<INPUT TYPE=CHECKBOX NAME=is_documents VALUE=\"1\" " + (item.is_documents == 1 ? "CHECKED" : "") + ">");
        form.append("</TD>");
        form.append("<TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
        form.append("Packing Instructions:&nbsp;</B></TD><TD ALIGN=LEFT>");
        form.append("<TEXTAREA NAME=packing_instructions rows=4 cols=30>" + StringEscapeUtils.escapeHtml(item.packing_instructions) + "</TEXTAREA>");
        form.append("</TD></TR>");

        form.append("<TR><TD><B>Active:&nbsp;</B></TD><TD><INPUT TYPE=CHECKBOX NAME=is_active VALUE=\"1\" " + (item.is_active == 1 ? "CHECKED" : "") + "></TD><TD align=right><B> Pack URL</B> </td>");
        form.append("<TD align=LEFT><INPUT type=TEXT NAME=url VALUE=" + item.url + ">");
        form.append("</TD></TR>");
        form.append("<tr><td ALIGN=RIGHT><b>Group Name:</b></td><td>" + item.groupName + "</td><td ALIGN=RIGHT><b>Case Qty:</b></td><td>" + item.caseQty + "</td></tr>");
        form.append("<tr><td ALIGN=RIGHT><b>UPC:</b></td><td>" + item.upcCode + "</td><td ALIGN=RIGHT><b>ISBN:</b></td><td>" + item.isbnCode + "</td></tr>");

        String clientID = ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID);

        if (!(item.isNew())) {

            form.append("<TR><TD COLSPAN=1 ALIGN=RIGHT><B>Additional IDs:&nbsp;</B><BR></TD><TD COLSPAN=3 ALIGN=LEFT><a href='http://warehouse.owd.com/clients/" + clientID  + "/inventory/" + item.inventory_id + "/additionalIds' target=\"_blank\">open IDs</a> (opens in new tab)</TD></TR>");

            Map<String, Integer> locMap = com.owd.core.managers.InventoryManager.getStockMapByLocation(item.inventory_id);

            form.append("<TR><TD COLSPAN=4><B>Available Stock Levels</B><BR>");
            form.append("<TABLE style=\"border: 1px solid black;table-layout: fixed;width: 690px;\"><TR><TH>Facility</TH><TH>Units Available</TH></TR>");

            form.append("</TR><TR>");
            for (OwdFacility f : FacilitiesManager.getActivePublicFacilities()) {
                form.append("<TR><TD ALIGN=LEFT style=\"border: 1px solid black;\">" + FacilitiesManager.getFacilityDisplayLabel(f) + "</TD><TD ALIGN=RIGHT style=\"border: 1px solid black;\">" + locMap.get(f.getFacilityCode()) + "</TD></TR>");
            }

            form.append("</TABLE></TD></TR>");
            if (LotManager.isInventoryIdLotControlled(item.inventory_id)) {
                Map<String, Map<String, Integer>> lotStock = com.owd.core.managers.InventoryManager.getStockMapByLocationAndLot(item.inventory_id);

                form.append("<TR><TD COLSPAN=4><B>Known Lot Quantities</B><BR>");
                form.append("<TABLE style=\"border: 1px solid black;table-layout: fixed;width: 690px;\"><TR><TH>Facility</TH><TH>Lot Value</TH><TH>Units On Hand</TH></TR>");

                form.append("</TR><TR>");
                for (OwdFacility f : FacilitiesManager.getActivePublicFacilities()) {
                    int fi = 0;
                    if (lotStock.get(f.getFacilityCode()) != null) {
                        for (String lot : lotStock.get(f.getFacilityCode()).keySet()) {
                            form.append("<TR><TD ALIGN=LEFT style=\"border: 1px solid black;\">" + (fi > 0 ? "" : FacilitiesManager.getFacilityDisplayLabel(f)) + "</TD><TD ALIGN=LEFT style=\"border: 1px solid black;\">" + lot + "</TD><TD ALIGN=RIGHT style=\"border: 1px solid black;\">" + lotStock.get(f.getFacilityCode()).get(lot) + "</TD></TR>");
                            fi++;
                        }
                    }
                }

                form.append("</TABLE></TD></TR>");

            }
        }
        form.append("<tr><td>&nbsp;</td></tr>");
        form.append("<TR><TD align=right><fontx color=\"#000000\" size=\"-2\"><b>Bulky Sku Info: </td></tr>");
        form.append("<tr><td align = right><b>Bulky Pick: </b></td><td>");
        if(item.isBulkyPick ==1){
            form.append("YES");
            form.append("<INPUT TYPE=HIDDEN NAME=is_bulky_pick VALUE=\"1\" >");
        }else{
            form.append("NO");
            form.append("<INPUT TYPE=HIDDEN NAME=is_bulky_pick VALUE=\"0\" >");
        }
        form.append("</td>");
        form.append("<td align=right><b>Bulky Pack: </b></td><td>");
        if(item.isBulkyPack ==1){
            form.append("YES");
            form.append("<INPUT TYPE=HIDDEN NAME=is_bulky_pack VALUE=\"1\" >");
        }else{
            form.append("NO");
            form.append("<INPUT TYPE=HIDDEN NAME=is_bulky_pack VALUE=\"0\" >");
        }
        form.append("</td></tr>");




//Begin internal only section
        if (ExtranetServlet.getSessionFlag(req, ExtranetServlet.kExtranetInternalFlag)) {
            form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Override Bulky Pick:&nbsp;</B></TD><TD ALIGN=LEFT>");

            form.append("<INPUT TYPE=CHECKBOX NAME=bulky_pick_override VALUE=\"1\" " + (item.bulkyPickOverride == 1 ? "CHECKED" : "") + ">");

            form.append("</TD>");
            form.append("<TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Override Bulky Pack:&nbsp;</B></TD><TD ALIGN=LEFT>");
            form.append("<INPUT TYPE=CHECKBOX NAME=bulky_pack_override VALUE=\"1\" " + (item.bulkyPackOverride == 1 ? "CHECKED" : "") + ">");

            form.append("</TD></TR>");

            form.append("<TR><TD ALIGN=RIGHT style=\"min-width:200px\"><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Bulky Pick Fee Price Override:&nbsp;($):&nbsp;</B></TD><TD ALIGN=LEFT>");
            form.append("<INPUT TYPE=TEXT NAME=bulky_pick_fee_override maxlength=\"2048\" VALUE=\"" + item.bulkyPickFeeOverride + "\" >");
            form.append("</TD>");
            form.append("<TD ALIGN=RIGHT style=\"min-width:200px\"><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Bulky Pack Fee Price Override&nbsp;($):&nbsp;</B></TD><TD ALIGN=LEFT>");
            form.append("<INPUT TYPE=TEXT NAME=bulky_pack_fee_override VALUE=\"" + item.bulkyPackFeeOverride + "\" >");
            form.append("</TD></TR>");




            form.append("<tr><td>&nbsp;</td></tr>");
            form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Auto-Inventory:&nbsp;</B></TD><TD ALIGN=LEFT>");
            if (item.is_auto_inventory == 0 || item.isNew()) {
                form.append("<INPUT TYPE=CHECKBOX NAME=is_auto_inventory VALUE=\"1\" " + (item.is_auto_inventory == 1 ? "CHECKED" : "") + ">");
            } else {
                form.append("Virtual Item - Unlimited Inventory. Not Editable.");
            }
            form.append("</TD>");
            form.append("<TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Scan Not Required:&nbsp;</B></TD><TD ALIGN=LEFT>");
            form.append("<INPUT TYPE=CHECKBOX NAME=no_scan VALUE=\"1\" " + (item.no_scan == 1 ? "CHECKED" : "") + ">");

            form.append("</TD></TR>");
            form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Is Insert:&nbsp;</B></TD><TD ALIGN=LEFT>");

            form.append("<INPUT TYPE=CHECKBOX NAME=is_insert VALUE=\"1\" " + (item.isInsert == 1 ? "CHECKED" : "") + ">");


	form.append("</TD>");
	form.append("<TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
	form.append("Track Lots:&nbsp;</B></TD><TD ALIGN=LEFT>");

	if(item.isLotTracked == 1) {
		form.append("YES");
        form.append("<INPUT Type=Hidden name = lot_tracking value=\"1\">");
	}else{
		form.append("<INPUT TYPE=CHECKBOX NAME=lot_tracking VALUE=\"1\" " + (item.isLotTracked == 1 ? "CHECKED" : "") + ">");
	}


            form.append("</TD></TR>");

            form.append("<TR><TD ALIGN=RIGHT><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Ship System Weight:&nbsp;</B></TD><TD ALIGN=LEFT>");

            form.append("<INPUT TYPE=CHECKBOX NAME=ship_system_weight VALUE=\"1\" " + (item.isShipSystemWeight == 1 ? "CHECKED" : "") + ">");

            form.append("</TD>");

            form.append("</TR>");

        } else {
            form.append("<TR><TD ALIGN=RIGHT COLSPAN=4>");
            if (item.is_auto_inventory == 1) {
                //no longer editable once field is true
                //  form.append("<INPUT TYPE=HIDDEN NAME=is_auto_inventory VALUE=\"1\" >");

            } else {

                form.append("<INPUT TYPE=HIDDEN NAME=is_auto_inventory VALUE=\"0\" >");
            }
            if (item.no_scan == 1) {
                form.append("<INPUT TYPE=HIDDEN NAME=no_scan VALUE=\"1\" >");

            } else {

                form.append("<INPUT TYPE=HIDDEN NAME=no_scan VALUE=\"0\" >");
            }
            if (item.isInsert == 1) {
                form.append("<INPUT TYPE=HIDDEN NAME=is_insert VALUE=\"1\" >");

            } else {

                form.append("<INPUT TYPE=HIDDEN NAME=is_insert VALUE=\"0\" >");
            }
            if (item.isLotTracked == 1) {
                form.append("<INPUT TYPE=HIDDEN NAME=lot_tracking VALUE=\"1\" >");

            } else {

                form.append("<INPUT TYPE=HIDDEN NAME=lot_tracking VALUE=\"0\" >");
            }
            if (item.isShipSystemWeight == 1) {
                form.append("<INPUT TYPE=HIDDEN NAME=ship_system_weight VALUE=\"1\" >");

            } else {

                form.append("<INPUT TYPE=HIDDEN NAME=ship_system_weight VALUE=\"0\" >");
            }

            form.append("</TD></TR>");
        }
        if (item.is_auto_inventory == 1) {
            form.append("<TR><TD ALIGN=RIGHT ><fontx color=\"#000000\" size=\"-2\"><b>");
            form.append("Kit Components:&nbsp;</B></TD><TD ALIGN=LEFT COLSPAN=3>");
            form.append("<TABLE border=1><TR><TH>Component SKU</TH><TH>Quantity</TH></TR>");


            try {

                ResultSet rs = HibernateSession.getResultSet("select inventory_num, req_inventory_quant from owd_inventory_required_skus s\n" +
                        "join owd_inventory on inventory_id=req_inventory_fkey where s.inventory_fkey=" + item.inventory_id);
                while (rs.next()) {
                    form.append("<TR><TD ALIGN=LEFT>" + rs.getString(1) + "</TD><TD ALIGN=RIGHT>" + rs.getInt(2) + "</TD></TR>");
                }


            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                HibernateSession.closeSession();
            }

            form.append("<TABLE></TD></TR>");

        }
        form.append("</TD></TR>");

        form.append("</FORM></TABLE>");

        resp.getWriter().write(form.toString());
    }
}
