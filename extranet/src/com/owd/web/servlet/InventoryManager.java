package com.owd.web.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.Supplier;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.generated.OwdInventory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

public class InventoryManager extends ExtranetManager {
private final static Logger log =  LogManager.getLogger();

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
    public static final String kCurrentItem = "invmgr.curritem";

    private static final String[] bulkLoadColumns = {"SKU", "Price", "Title", "Supplier", "Keyword", "Description", "Notes", "Color", "Size", "Reorder Level", "Harmonized Code", "Supplier Cost", "Supplier Name", "Weight (lbs)", "On Hand", "Backordered","Pack Instructions"};


    String[] tableColumnNames = {"ID", "Item Number (SKU)", "Available", "Description"};
    String[] tableLinkStarts = {kParamInvMgrAction + "=" + kParamInvEditAction + "&" + kparamInvID + "=", "", "", ""};
    String[] tableLinkEnds = {"</A>", "", "", ""};
    int[] tableLinkMids = {1, 0, 0, 0};
    String[] tableColumnDefs = {"inventory_id", "inventory_num", "ISNULL(o.qty_on_hand,0)", "description"};
    String tableFromStmt = "from owd_inventory i (NOLOCK) left outer join owd_inventory_oh o (NOLOCK) on (inventory_id = inventory_fkey) ";


    public String[] getManagerCreators(HttpServletRequest req) {
        String[] urls = {""};


        return urls;
    }

    public String[] getManagerActions(HttpServletRequest req) {
        String[] urls = {"", "", "", ""};

        urls[0] = "<A class=\"command\"  HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvExploreAction + "\" ><fontx COLOR=white>Search</fontx></A>";
        urls[1] = "<A class=\"command\"  HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvCreateAction + "\" ><fontx COLOR=white>Add New SKU</fontx></A>";
        urls[2] = "<A class=\"command\" HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvBulkLoadAction + "\" ><fontx COLOR=white>Batch Add/Update</fontx></A>";
        urls[3] = "<A class=\"command\" HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + ExtranetServlet.kParamDownloadAction + "\" ><fontx COLOR=white>Download All SKUs</fontx></A>";

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


            String sql = "select \' \'+max(i.inventory_num) as sku,max(ISNULL(i.price,0.00)) as 'mp',max(ISNULL(i.description,\'\')) as 'md',max(ISNULL(mfr_part_num,\'\')) as 'mpn',"
                    + " max(ISNULL(keyword,\'\')),i.inventory_id,"
                    + " max(ISNULL(CONVERT(varchar(255),notes),\'\')),max(ISNULL(i.item_color,\'\')),max(ISNULL(i.item_size,\'\')),max(ISNULL(qty_reorder,0)),"
                    + " max(ISNULL(harm_code,\'\')),max(ISNULL(supp_cost,0.0)),max(ISNULL(supp_fkey,0)),max(ISNULL(weight_lbs,0.0)),max(ISNULL(o.qty_on_hand,0)),sum(ISNULL(l.quantity_request,0)) as 'mi'"
                    + " from owd_inventory i (NOLOCK) left outer join special_instructions (NOLOCK) on external_id=inventory_id and activity_type='INVENTORY-PACK'"
                    + " left outer join owd_inventory_oh o (NOLOCK) on (i.inventory_id = inventory_fkey)"
                    + " left outer join owd_line_item l (NOLOCK)"
                    + " join owd_order ord (NOLOCK) on (l.order_fkey = ord.order_id and  ord.is_void=0 and ord.is_backorder=1 and ord.post_date IS NULL "
                    + " and  ord.is_future_ship=0 and ord.client_fkey =?)"
                    + " on (l.inventory_id = i.inventory_id and l.quantity_actual < 1 and l.quantity_request > 0)"
                    + " where i.client_fkey = ?"
                    + " group by i.inventory_id";


            stmt = cxn.prepareStatement(sql);
            log.debug(sql);
            log.debug(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
            stmt.setInt(1, new Integer(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue());
            stmt.setInt(2, new Integer(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue());

            stmt.executeQuery();
            log.debug("download sql");
            rs = stmt.getResultSet();

            resp.setContentType("application/x-download");
            resp.setHeader("Content-Disposition", "attachment; filename=\"inventory.csv\"");

            StringBuffer out = new StringBuffer();


            out.append("\"" + bulkLoadColumns[0] + "\",\"" + bulkLoadColumns[1] + "\",\"" + bulkLoadColumns[2] + "\",\"" +
                    bulkLoadColumns[3] + "\",\"" + bulkLoadColumns[4] + "\",\"" + bulkLoadColumns[5] + "\",\"" + bulkLoadColumns[6] + "\",\"" +
                    bulkLoadColumns[7] + "\",\"" + bulkLoadColumns[8] + "\",\"" + bulkLoadColumns[9] + "\",\"" + bulkLoadColumns[10] + "\",\"" +
                    bulkLoadColumns[11] + "\",\"" + bulkLoadColumns[12] + "\",\"" + bulkLoadColumns[13] + "\",\"" + bulkLoadColumns[14] + "\",\"" + bulkLoadColumns[15]+ "\",\"" + bulkLoadColumns[16] + "\"\r\n");
            while (rs.next()) {
                out.append("\"" + rs.getString(1) + "\",\"" + rs.getString(2) + "\",\"" + rs.getString(3) + "\",\"" +
                        rs.getString(4) + "\",\"" + rs.getString(5) + "\",\"" + getLongDescriptionForInventoryID(rs.getString(6)) + "\",\"" + rs.getString(7) + "\",\"" +
                        rs.getString(8) + "\",\"" + rs.getString(9) + "\",\"" + rs.getString(10) + "\",\"" + rs.getString(11) + "\",\"" + rs.getString(12) + "\",\"" + Supplier.getSupplierNameForID(rs.getString(13), true) + "\",\"" + rs.getString(14) + "\",\"" + rs.getString(15) + "\",\"" + rs.getString(16) + "\",\"" + rs.getString(17)+ "\"\r\n");
            }
            log.debug("download done");
            rs.close();
            stmt.close();
            cxn.close();


            resp.setContentLength(out.length());
            resp.getOutputStream().write(out.toString().getBytes());

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
        //log.debug("download exit");


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


        String currAction = ExtranetServlet.getStringParam(req, kParamInvMgrAction, kParamInvExploreAction);

        /*
        if(currAction.equals(kParamInvExploreAction) | currAction.equals(kParamInvFindAction))
        {
            out.write("<FORM METHOD=POST ACTION="+ExtranetServlet.kExtranetURLPath+
                    "><INPUT TYPE=HIDDEN NAME=\""+kParamInvMgrAction+"\" VALUE=\""+
                    kParamInvFindAction+"\">");
            out.write("Find Inventory:&nbsp;<INPUT TYPE=\"text\" NAME=\""+kParamInvQuickFind+"\" SIZE=\"30\"  onChange=\"this.form.submit()\">&nbsp;<INPUT TYPE=SUBMIT NAME=SUBMIT VALUE=GO>");
            out.write("<BR>");
            out.write("");
            //out.write("<A HREF=\""+ExtranetServlet.kExtranetURLPath+"?"+kParamInvMgrAction+"="+kParamInvFindAdvancedAction+"\" >Advanced</A>&nbsp;");
            out.write("");
        }
        */

        //do real work here
        if (currAction.equals(ExtranetServlet.kParamDownloadAction)) {

            download(req, resp);


        } else if (currAction.equals(kParamInvExploreAction)) {


            try {

                InventoryFinder of = null;

                if (null == req.getSession(true).getAttribute(kCurrentFinder)) {
                    of = new InventoryFinder(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
                    req.getSession(true).setAttribute(kCurrentFinder, of);
                } else {

                    of = (InventoryFinder) req.getSession(true).getAttribute(kCurrentFinder);
                }

                of.setTextSearchType(ExtranetServlet.getIntegerParam(req, "textSearchType", of.getTextSearchType()));
                of.setTextSearchValue(ExtranetServlet.getStringParam(req, "textSearchValue", of.getTextSearchValue()));

                of.setTextSearchField(ExtranetServlet.getStringParam(req, "textSearchField", of.getTextSearchField()));


                req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "findinventory.jsp").include(req, resp);

            } catch (Exception ex) {
                ex.printStackTrace();
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
                com.owd.core.MultipartRequest fileSource = new com.owd.core.MultipartRequest(req, ExtranetServlet.kBulkLoadSaveDir, 1024 * 1024);
                Enumeration files = fileSource.getFileNames();

                if (!files.hasMoreElements()) {
                    form.append("<fontx size=-1 color=\"red\"><B>No file was received!<BR>Please check your file and try again.</B></fontx><HR><P><A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvBulkLoadAction + "\" >Back to Bulk Loader</A>");

                } else {

                    uploadFile = fileSource.getFile((String) files.nextElement());

                    if (uploadFile == null) {
                        form.append("<fontx size=-1 color=\"red\"><B>No file was received!<BR>Please check your file and try again.</B></fontx><HR><P><A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvBulkLoadAction + "\" >Back to Bulk Loader</A>");
                    } else {

                        reader = new BufferedReader(new FileReader(uploadFile));
                        uploadFile.renameTo(new File(ExtranetServlet.kBulkLoadSaveDir + File.separator + com.owd.core.OWDUtilities.getCurrentUserName() + Calendar.getInstance().getTime().getTime() + ".csv"));

                        com.owd.core.CSVReader data = new com.owd.core.CSVReader(reader, true);


                        int importColumnCount = bulkLoadColumns.length - 2;

                        if (data.columnCount < importColumnCount) {
                            form.append("<fontx size=-1 color=\"red\"><B>There must be " + importColumnCount + " columns in the file; you only provided " + data.columnCount
                                    + " columns.<BR>Please check your file and try again. Click the link on the left to download your inventory to get a sample file with the correct format.</B></fontx><HR><P><A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvBulkLoadAction + "\" >Back to Bulk Loader</A>");

                        } else {
                            form.append("<fontx size=-1 ><B>Bulk Loader Preview</B><BR><TABLE border=1><TR>");
                            form.append("<TH><fontx color=\"#000000\" size=\"-1\">Action</TH>");
                            for (int i = 0; i < data.columnCount; i++) {
                                form.append("<TH><fontx color=\"#000000\" size=\"-2\">" + ("".equals(data.getColumn(i)) ? data.getColumn(i) : bulkLoadColumns[i]) + "</TH>");
                            }
                            form.append("</TR> ");
                            for (int row = 0; row < data.getRowCount(); row++) {

                                form.append("<TR> ");
                                for (int col = 0; col < data.columnCount; col++) {
                                    if (col == 0) {
                                        try {
                                            if (data.getRowSize(row) < importColumnCount)
                                                throw new Exception("Row rejected - wrong number of columns");

                                            form.append("<TD><fontx  color=\"#000000\" size=\"-2\"><B>"
                                                    + (ConnectionManager.InventoryExists(data.getStrValue(col, row, ""), clientID) ? "<fontx COLOR=green>UPDATE</fontx>" : "<fontx COLOR=red>NEW</fontx>") + "</B></TD>");
                                        } catch (Exception ex) {
                                            form.append("<TD><fontx  color=\"#000000\" size=\"-2\"><B>"
                                                    + "<fontx COLOR=red>[ERROR]<BR>" + ex.getMessage() + "</fontx>" + "</B></TD>");
                                        }
                                    }
                                    form.append("<TD><fontx  color=\"#000000\" size=\"-2\">" + data.getStrValue(col, row, "") + "</TD>");
                                }
                                form.append("</TR> ");
                            }
                            form.append("</TABLE><HR><FORM METHOD=POST ACTION=\"" + ExtranetServlet.kExtranetURLPath + "\">");
                            form.append("<INPUT TYPE=HIDDEN NAME=" + kParamInvMgrSubAction + " VALUE=" + kParamInvBulkLoadActionSave + ">");
                            form.append("<INPUT TYPE=HIDDEN NAME=" + kParamInvMgrAction + " VALUE=" + kParamInvBulkLoadAction + ">");
                            form.append("<INPUT TYPE=HIDDEN NAME=" + kParamInvBulkLoadFileName + " VALUE=\"" + uploadFile.getName() + "\">");
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
                    File fileRef = new File((new File(ExtranetServlet.kBulkLoadSaveDir)) + File.separator + fName);

                    int importColumnCount = bulkLoadColumns.length - 2;

                    if (fileRef.exists()) {
                        BufferedReader reader = new BufferedReader(new FileReader(fileRef));
                        com.owd.core.CSVReader data = new com.owd.core.CSVReader(reader, true);
                        //check and import data
                        Inventory item = null;


                        if (data.columnCount != importColumnCount) {
                            form.append("<fontx size=-1 color=\"red\"><B>There must be " + importColumnCount + " columns in the file; you only provided " + data.columnCount
                                    + " columns.<BR>Please check your file and try again.</B></fontx><HR><P><A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvBulkLoadAction + "\" >Back to Bulk Loader</A>");

                        } else {
                            form.append("<fontx size=-1 color=\"red\"><B>Changes Applied...</B><BR><TABLE border=1><TR>");
                            form.append("<TH><fontx face=\"Geneva, Verdana, Helvetica\" color=\"#000000\" size=\"-2\">Action</TH>");
                            for (int i = 0; i < data.columnCount; i++) {
                                form.append("<TH><fontx face=\"Geneva, Verdana, Helvetica\" color=\"#000000\" size=\"-2\">" + ("".equals(data.getColumn(i)) ? data.getColumn(i) : bulkLoadColumns[i]) + "</TH>");
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
                                    if (data.getRowSize(row) < importColumnCount)
                                        throw new Exception("Row rejected - wrong number of columns");

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

                                        }
                                    } else {
                                        //add new
                                        item = new Inventory("0",
                                                clientID,
                                                data.getStrValue(0, row, ""), //((String)rowData.elementAt(0)).trim(),
                                                "", //data.getStrValue(3,row,""),//((String)rowData.elementAt(3)).trim(),
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
                                                "0","","","0","0","","","","0","0","0","0","0","0","","", "", "","0","0","0","0","0","0.0","0.0");

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
                                            + errorMessage + "</fontx></B></TD>");
                                } else {
                                    form.append("<TD><fontx face=\"Geneva, Verdana, Helvetica\" color=\"#000000\" size=\"-2\"><B>"
                                            + (doUpdate ? "<fontx COLOR=green>UPDATED!</fontx>" : "<fontx COLOR=green>CREATED!</fontx>") + "</B></TD>");
                                }

                                for (int rowIndex = 0; rowIndex < data.columnCount; rowIndex++) {
                                    form.append("<TD><fontx face=\"Geneva, Verdana, Helvetica\" color=\"#000000\" size=\"-2\">" + data.getStrValue(rowIndex, row, "") + "</TD>");
                                }
                                form.append("</TR> ");


                            }
                            form.append("</TABLE>");
                        }

                        if (fileRef.exists())
                            fileRef.delete();

                    } else {
                        //file does not exist
                        form.append("<fontx size=-1 color=\"red\"><B>File not found - please start again...</B></fontx><HR><P><A HREF=\""
                                + ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvBulkLoadAction + "\" >Back to Bulk Loader</A>");
                    }

                    resp.getWriter().write(form.toString());
                }


            } else {

                form.append("<P><fontx size=-1 ><B>Bulk Loader Instructions</B><P>The input file must be in comma-delimited (also known as Comma-Separated Value) format. The simplest way to create such files");
                form.append(" is to edit the information in Excel and select Save As..., then choose the comma-delimited format (*.csv) to save. The ");
                form.append("file created for uploading should end in the extension \".csv\".<p>You can choose the <B>Download All</B> action at the left to obtain a sample file with your current inventory information.<P>");
                form.append("Please note that the files you download include columns for on hand and backorder quantities, but these columns are not used when uploading updates via this form.<HR> <P>");


                form.append("<FORM METHOD=POST  ENCTYPE=\"MULTIPART/FORM-DATA\" ACTION=\"" + ExtranetServlet.kExtranetURLPath + "?"
                        + com.owd.web.servlet.InventoryManager.kParamInvMgrAction + "=" + com.owd.web.servlet.InventoryManager.kParamInvBulkLoadAction + "&"
                        + com.owd.web.servlet.InventoryManager.kParamInvMgrSubAction + "=" + com.owd.web.servlet.InventoryManager.kParamInvBulkLoadActionProcess + "\">");

                form.append("Select the Browse button to select the file from your local drive:<BR>");

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

                Integer listindex = new Integer(ExtranetServlet.getIntegerParam(req, "listindex", 0));

                int listSize = ((List) req.getSession(true).getAttribute("inventoryfinderresults")).size();
                if (listindex.intValue() > (listSize - 1)) listindex = new Integer(listSize - 1);
                if (listindex.intValue() < 0) listindex = new Integer(0);

                req.setAttribute(kCurrentItem, com.owd.core.business.order.Inventory.getInventoryForID(cxn, ((OwdInventory) ((List) req.getSession(true).getAttribute("inventoryfinderresults")).toArray()[listindex.intValue()]).getInventoryId() + ""));

                req.getSession(true).setAttribute("inventorylistindex", listindex);

                try {
                    req.getSession(true).removeAttribute(kCurrentItem);
                } catch (Throwable th) {
                }
                req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editinventory.jsp").include(req, resp);


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
                req.setAttribute(kCurrentItem, item);


                req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editinventory.jsp").include(req, resp);

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

                cxn = com.owd.core.managers.ConnectionManager.getConnection();

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
                                "",
                                "",
                                "0",
                                "0",
                                "",
                                "",
                                "",
                                "0",
                                "0",
                                ExtranetServlet.getStringParam(req,Inventory.kdbLength,"0.0"),
                                ExtranetServlet.getStringParam(req,Inventory.kdbWidth,"0.0"),
                                ExtranetServlet.getStringParam(req,Inventory.kdbHeight,"0.0"),
                                ExtranetServlet.getStringParam(req,Inventory.kdbCubicFeet,"0.0"),
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
                        req.setAttribute(kCurrentItem, item);

                    } else {

                        item = com.owd.core.business.order.Inventory.getInventoryForID(cxn, ExtranetServlet.getStringParam(req, Inventory.kdbInventoryPKName, "0"));
                        req.setAttribute(kCurrentItem, item);

                        item.inventory_num = ExtranetServlet.getStringParam(req, Inventory.kdbInventoryRef, "");
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

                        item.length = new Float(ExtranetServlet.getStringParam(req,Inventory.kdbLength,"0.0"));
                        item.width = new Float(ExtranetServlet.getStringParam(req,Inventory.kdbWidth,"0.0"));
                        item.height = new Float(ExtranetServlet.getStringParam(req,Inventory.kdbHeight,"0.0"));
                        item.cubic_feet = new Float(ExtranetServlet.getStringParam(req,Inventory.kdbCubicFeet,"0.0"));


                    }
                    item.dbupdate(cxn);
                    cxn.commit();
                    req.setAttribute(kCurrentItem, item);
                    resp.sendRedirect(resp.encodeRedirectURL(ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvExploreAction));
                } else {
                    if (subaction.equals("Delete Item")) {

                        item = com.owd.core.business.order.Inventory.getInventoryForID(cxn, ExtranetServlet.getStringParam(req, Inventory.kdbInventoryPKName, "0"));
                        item.dbdelete(cxn);
                        cxn.commit();
                        resp.sendRedirect(resp.encodeRedirectURL(ExtranetServlet.kExtranetURLPath + "?" + kParamInvMgrAction + "=" + kParamInvExploreAction));
                    } else {

                        throw new Exception("Request not recognized");
                    }


                }
                //redirect to the explorer page with this item listed


            } catch (Exception ex) {
                ex.printStackTrace();
                try {
                    cxn.rollback();
                    req.setAttribute("formerror", ex.getMessage());
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

    /*public void showTable(HttpServletRequest req, HttpServletResponse resp,String[] criteria, String description)   throws IOException
    {


        WebTable table = new WebTable();



        table.setSQLDefs(tableColumnNames,tableColumnDefs,tableLinkStarts,tableLinkMids,tableLinkEnds,tableFromStmt);

        table.addCriterium("i.client_fkey="+ExtranetServlet.getSessionString(req,ExtranetServlet.kExtranetClientID));

        if(criteria!=null)
        {
            for(int i=0;i<criteria.length;i++)
            {
                table.addCriterium(criteria[i]);
            }
        }
        table.setDescription((description==null?"Showing All...":description));
            //check for find criteria

        int gotono = ExtranetServlet.getIntegerParam(req,WebTable.kTableShowPage,0);
        if(gotono!=0)
            table.setPageNum(gotono);
        else
            table.setPageNum(ExtranetServlet.getIntegerParam(req,WebTable.kTableShowPage,1));
        table.setOrderCol(ExtranetServlet.getIntegerParam(req,WebTable.kTableSortColumn,-1));

        table.getTable(req,resp);

    }*/

}
