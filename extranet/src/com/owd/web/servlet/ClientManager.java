package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;


public class ClientManager extends ExtranetManager {
private final static Logger log =  LogManager.getLogger();


    public static final String kParamClientMgrAction = "clmgr";

    public static final String kParamClientMgrSubAction = "sub";

    public static final String kParamClientQuery = "qryname";

    public static final String kParamClientExploreAction = "explore";

    public static final String kParamClientCreateAction = "new";

    public static final String kParamClientEditAction = "edit";

    public static final String kParamClientFindAction = "find";

    public static final String kParamClientSaveAction = "save";

    public static final String kParamClientDeleteAction = "delete";

    public static final String kParamClientFindAdvancedAction = "findadv";


    public static final String kParamClientQuickFind = "qf";

    public static final String kParamClientID = "clid";

    public static final String kCurrentClientName = "currentclient";


    String[] tableColumnNames = {"", "Name", "Active"};

    String[] tableLinkStarts = {kParamClientMgrAction + "=" + kParamClientEditAction + "&" + kParamClientID + "=", "", ""};

    String[] tableLinkEnds = {"Edit</A>", "", ""};

    int[] tableLinkMids = {1, 0, 0};

    String[] tableColumnDefs = {"client_id", "company_name", "is_active"};

    String tableFromStmt = "from owd_client ";


    public String[] getManagerCreators(HttpServletRequest req) {

        String[] urls = {""};


        urls[0] = "<A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamClientMgrAction + "=" + kParamClientCreateAction + "\" >New Client</A>";


        return urls;

    }


    public String[] getManagerActions(HttpServletRequest req) {

        String[] urls = {""};


        urls[0] = "<A class=\"command\" HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamClientMgrAction + "=" + kParamClientExploreAction + "\" >Explore</A>";


        return urls;

    }


    public String[] getManagerQueries(HttpServletRequest req) {

        String[] urls = {""};


        urls[0] = "<B>N/A</B>";


        return urls;

    }


    public String[] getManagerReports(HttpServletRequest req) {

        String[] urls = {""};


        urls[0] = "<B>N/A</B>";


        return urls;

    }


    public String getCurrentAction(HttpServletRequest req) {

        String currAction = ExtranetServlet.getStringParam(req, kParamClientMgrAction, kParamClientExploreAction);

        if (currAction.equals(kParamClientExploreAction)) {

            return "Exploring Clients";

        } else if (currAction.equals(kParamClientCreateAction)) {

            return "Creating Client";

        } else if (currAction.equals(kParamClientEditAction)) {


            return "Editing Client";

        } else if (currAction.equals(kParamClientFindAction)) {

            return "Client Search Results";

        }


        return "";

    }


    public void getManagerContent(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        String currAction = ExtranetServlet.getStringParam(req, kParamClientMgrAction, kParamClientExploreAction);



        //do real work here





        if (currAction.equals(kParamClientExploreAction)) {

            showTable(req, resp, null, "Show All...");


        } else if (currAction.equals(kParamClientFindAction)) {


            String qfstr = ExtranetServlet.getStringParam(req, kParamClientQuickFind, "");


            if (qfstr.equals("")) {

                if (false) //is advanced
                {


                } else {

                    //no criteria

                    showTable(req, resp, null, "(No search specified, showing all...)");

                }


            } else {

                String[] crits = {"(company_name like \'%" + qfstr + "%\')"};



                //if quickfind

                showTable(req, resp, crits, "Find clients with name containing " + qfstr);


            }

        } else if (currAction.equals(kParamClientEditAction)) {

            Connection cxn = null;


            try {


                cxn = com.owd.core.managers.ConnectionManager.getConnection();

                com.owd.core.business.Client client = com.owd.core.business.Client.getClientForID(cxn, ExtranetServlet.getIntegerParam(req, kParamClientID, 0) + "");

                getEditForm(client, req, resp);

            } catch (Exception ex) {


                ex.printStackTrace();

                //////log.debug(ex.getMessage());

            } finally {

                try {
                    cxn.close();
                } catch (Exception ex) {
                }

            }


        } else if (currAction.equals(kParamClientCreateAction)) {


            try {

                com.owd.core.business.Client client = new Client();

                getEditForm(client, req, resp);

            } catch (Exception ex) {


                ex.printStackTrace();

                //////log.debug(ex.getMessage());

            } finally {


            }


        } else if (currAction.equals(kParamClientSaveAction)) {

            Connection cxn = null;

            com.owd.core.business.Client client = null;

            ////log.debug("saving item");

            String subaction = ExtranetServlet.getStringParam(req, kParamClientMgrSubAction, "");


            try {


                cxn = com.owd.core.managers.ConnectionManager.getConnection();


                if (subaction.equals("Save New Client") || subaction.equals("Save Changes")) {

                    ////log.debug("yahoo="+ExtranetServlet.getStringParam(req,Client.kdbYahooNotify,"0"));

                    ////log.debug("yahoopass="+ExtranetServlet.getStringParam(req,Client.kdbYahooTrackPass,""));



                  //  TreeMap methodMap = new TreeMap();


                    /*Iterator iter = com.owd.core.csXml.OrderRater.getServiceCodes().iterator();


                    while (iter.hasNext()) {

                        String svc = (String) iter.next();

                        int method = ExtranetServlet.getIntegerParam(req, "svc" + svc, 0);

                        if (method == 1) {

                            methodMap.put(svc, new Float(ExtranetServlet.getStringParam(req, svc + "pct", "0.0")));

                        }

                    }*/


                    if (ExtranetServlet.getStringParam(req, Client.kdbClientPKName, "0").equals("0")) {

                        client = new Client("0", "", "", "", "",

                                ExtranetServlet.getStringParam(req, Client.kdbClientShipperSymbol, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbClientCompanyName, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbClientAddressOne, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbClientAddressTwo, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbClientCity, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbClientState, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbClientZip, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbClientCountry, ""),

                                "",

                                ExtranetServlet.getStringParam(req, Client.kdbClientPhone, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbClientFax, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbClientURL, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbClientEmail, ""),

                                "0",

                                "1",

                                "0",

                                "1",

                                "0",

                                "",

                                "1",

                                ExtranetServlet.getStringParam(req, Client.kdbClientActive, "1"),

                                ExtranetServlet.getStringParam(req, Client.kdbClientName, ""),

                                "",

                                "",

                                "",

                                ExtranetServlet.getStringParam(req, Client.kdbOrderShipEmail, "0"),

                                ExtranetServlet.getStringParam(req, Client.kdbOrderShipEmailFrom, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbOrderShipEmailFooter, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbOrderShipEmailCC, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbOrderShipEmailBCC, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbYahooNotify, "0"),

                                ExtranetServlet.getStringParam(req, Client.kdbOrderTrustNotify, "0"),

                                ExtranetServlet.getStringParam(req, Client.kdbYahooTrackPass, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbOrderTrustSourceCode, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbAutoShip, "0"),

                                ExtranetServlet.getStringParam(req, Client.kdbAutoShipFullType, "0"),

                                ExtranetServlet.getStringParam(req, Client.kdbAutoShipFullCarrier, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbAutoShipPartType, "0"),

                                ExtranetServlet.getStringParam(req, Client.kdbAutoShipPartCarrier, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbFedExAcct, ""),

                                ExtranetServlet.getStringParam(req, Client.kdbUPSAcct, ""),

                                "",

                                "",

                                "",

                                ExtranetServlet.getStringParam(req, Client.kdbUseDCForFirstclass, "0"),"0","","","");

                    } else {

                        //////log.debug("email="+""+ ExtranetServlet.getIntegerParam(req,Client.kdbOrderShipEmail,0));

                        client = com.owd.core.business.Client.getClientForID(cxn, ExtranetServlet.getStringParam(req, Client.kdbClientPKName, "0"));

                        client.shipper_symbol = ExtranetServlet.getStringParam(req, Client.kdbClientShipperSymbol, "");

                        client.company_name = ExtranetServlet.getStringParam(req, Client.kdbClientCompanyName, "");

                        client.address_one = ExtranetServlet.getStringParam(req, Client.kdbClientAddressOne, "");

                        client.address_two = ExtranetServlet.getStringParam(req, Client.kdbClientAddressTwo, "");

                        client.city = ExtranetServlet.getStringParam(req, Client.kdbClientCity, "");

                        client.state = ExtranetServlet.getStringParam(req, Client.kdbClientState, "");

                        client.zip = ExtranetServlet.getStringParam(req, Client.kdbClientZip, "");

                        client.country = ExtranetServlet.getStringParam(req, Client.kdbClientCountry, "");

                        client.primary_phone_num = ExtranetServlet.getStringParam(req, Client.kdbClientPhone, "");

                        client.primary_fax_num = ExtranetServlet.getStringParam(req, Client.kdbClientFax, "");

                        client.url_string = ExtranetServlet.getStringParam(req, Client.kdbClientURL, "");

                        client.primary_email_address = ExtranetServlet.getStringParam(req, Client.kdbClientEmail, "");

                        client.is_active = "" + ExtranetServlet.getIntegerParam(req, Client.kdbClientActive, 0);

                        client.contact_name = ExtranetServlet.getStringParam(req, Client.kdbClientName, "");

                        client.ship_email = "" + ExtranetServlet.getIntegerParam(req, Client.kdbOrderShipEmail, 0);

                        client.ship_email_from = "" + ExtranetServlet.getStringParam(req, Client.kdbOrderShipEmailFrom, "");

                        client.ship_email_ftr = ExtranetServlet.getStringParam(req, Client.kdbOrderShipEmailFooter, "");

                        client.ship_email_cc = ExtranetServlet.getStringParam(req, Client.kdbOrderShipEmailCC, "");

                        client.ship_email_bcc = ExtranetServlet.getStringParam(req, Client.kdbOrderShipEmailBCC, "");

                        client.tell_yahoo = "" + ExtranetServlet.getStringParam(req, Client.kdbYahooNotify, "0");

                        client.tell_ordertrust = "" + ExtranetServlet.getStringParam(req, Client.kdbOrderTrustNotify, "0");

                        client.yahoo_pass = ExtranetServlet.getStringParam(req, Client.kdbYahooTrackPass, "");

                        client.otrust_source = ExtranetServlet.getStringParam(req, Client.kdbOrderTrustSourceCode, "");

                        client.is_backship = ExtranetServlet.getStringParam(req, Client.kdbAutoShip, "0");

                        client.original_ship_type = "" + ExtranetServlet.getStringParam(req, Client.kdbAutoShipFullType, "0");

                        client.original_ship_carrier = "" + ExtranetServlet.getStringParam(req, Client.kdbAutoShipFullCarrier, "");

                        client.partial_ship_type = ExtranetServlet.getStringParam(req, Client.kdbAutoShipPartType, "0");

                        client.partial_ship_carrier = ExtranetServlet.getStringParam(req, Client.kdbAutoShipPartCarrier, "");

                        client.fedex_acct = ExtranetServlet.getStringParam(req, Client.kdbFedExAcct, "");

                        client.ups_acct = ExtranetServlet.getStringParam(req, Client.kdbUPSAcct, "");

                        client.usedc_firstclass = ExtranetServlet.getIntegerParam(req, Client.kdbUseDCForFirstclass, 0);

                    }

                //    client.setMethodMap(methodMap);

                    client.dbupdate(cxn);


                    cxn.commit();

                    resp.sendRedirect(resp.encodeRedirectURL(ExtranetServlet.kExtranetURLPath + "?" + kParamClientMgrAction + "=" + kParamClientFindAction + "&" +

                            kParamClientQuickFind + "=" + java.net.URLEncoder.encode(client.company_name,"UTF-8")));

                } else {

                    if (subaction.equals("Delete Client")) {


                        client = com.owd.core.business.Client.getClientForID(cxn, ExtranetServlet.getStringParam(req, Client.kdbClientPKName, "0"));

                        client.dbdelete(cxn);

                        cxn.commit();

                        resp.sendRedirect(resp.encodeRedirectURL(ExtranetServlet.kExtranetURLPath + "?" + kParamClientMgrAction + "=" + kParamClientExploreAction));

                    } else {


                        throw new Exception("Request not recognized");

                    }


                }

                //redirect to the explorer page with this item listed


            } catch (Exception ex) {

                ex.printStackTrace();

                try {
                    cxn.rollback();
                    getEditForm(client, req, resp, ex.getMessage());
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


        if (criteria != null) {

            for (int i = 0; i < criteria.length; i++) {

                table.addCriterium(criteria[i]);

            }

        }

        table.setDescription((description == null ? "Showing All..." : description));

        //check for find criteria



        table.setPageNum(ExtranetServlet.getIntegerParam(req, WebTable.kTableShowPage, 1));

        table.setOrderCol(ExtranetServlet.getIntegerParam(req, WebTable.kTableSortColumn, -1));


        resp.getWriter().write(table.getTable(req, resp));


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


    public void getEditForm(Client client, HttpServletRequest req, HttpServletResponse resp, String message) throws Exception {

        resp.getWriter().write("<HR><fontx color=red><B>ERROR: " + message + "</B></fontx><HR>");

        getEditForm(client, req, resp);

    }


    public void getEditForm(Client client, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        RequestDispatcher rd = req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "client_editor.jsp");

        req.setAttribute(kCurrentClientName, client);

        rd.include(req, resp);

    }

}
