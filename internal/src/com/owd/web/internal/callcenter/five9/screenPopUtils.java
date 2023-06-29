package com.owd.web.internal.callcenter.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClientCallcenter;
import com.owd.intranet.ClientList;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jul 8, 2009
 * Time: 10:38:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class screenPopUtils {
private final static Logger log =  LogManager.getLogger();
    private static String phpAgentId = "<?php echo $_GET[\"agentid\"] ?>";
         private static String phpCallId = "<?php echo $_GET[\"callid\"] ?>";
      private static String phpAgentName ="<?php echo $_GET[\"agentfname\"] ?>";
      private static String phpSmallAgent = "<?php echo $_GET[\"sagentid\"] ?>";
    private static String phpPath = "/var/www/html/screen/";
    private static String phpExt = ".php";

    public static void main(String[] args) {
        try {
            Session sess = HibernateSession.currentSession();
            //createAllFiles(sess);
            // log.debug(createScreenPopIndex(sess));
            // log.debug(createSingleFile("88",HibernateSession.currentSession()));
            //log.debug(getPHPHtml(loadBeanFromId("31",HibernateSession.currentSession())));
            // loadBeanFromId("1", HibernateSession.currentSession());
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static String getEmailTo(Session sess) {
        String s = new String();
        try {
            Query q = sess.createSQLQuery("select value from dbo.app_data where project = 'internal' and description = 'screenpops' and variable = 'emailto'");
            List l = q.list();
            if (l.size() > 0) {
                s = l.get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return s;
    }

    public static String getEmailToCanada(Session sess) {
        String s = new String();
        try {
            Query q = sess.createSQLQuery("select value from dbo.app_data where project = 'internal' and description = 'screenpops' and variable = 'canadaEmail'");
            List l = q.list();
            if (l.size() > 0) {
                s = l.get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return s;
    }

    public static OwdClientCallcenter loadBeanFromId(String id, Session sess) {
        return loadBeanFromId(Integer.valueOf(id), sess);
    }

    public static OwdClientCallcenter loadBeanFromId(Integer id, Session sess) {
        OwdClientCallcenter ccClient = new OwdClientCallcenter();
        try {
            ccClient = (OwdClientCallcenter) sess.load(OwdClientCallcenter.class, id);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return ccClient;
    }

    public static List getClientList() {

        log.debug("Doing list");
        return ClientList.getInstance().getclients();

    }

    public static List<screenPopBean> loadPageList(Session sess) {
        List<screenPopBean> pages = new ArrayList<screenPopBean>();
        Query q = sess.createSQLQuery("select id,Mlog_campaign_name,  company_name from owd_client_callcenter, owd_client where client_id = client_fkey order by company_name");
        List results = q.list();

        for (Object row : results) {
            Object[] data = (Object[]) row;
            screenPopBean spb = new screenPopBean();
            spb.setId(data[0].toString());
            spb.setCampaingnName(data[1].toString());
            spb.setClientName(data[2].toString());
            pages.add(spb);

        }


        return pages;
    }

    private static String agentName = "&lt;AGENT_NAME&gt;";
    private static String agentName2 = "<AGENT_NAME>";
    private static String agentId = "&lt;AGENT_ID&gt;";
    private static String agentId2 = "<AGENT_ID>";
    private static String callId = "&lt;CALL_ID&gt;";
    private static String callId2 = "<CALL_ID>";

    private static String replaceUrlVars(String s) {
        s = s.replace(agentName, phpAgentName);
        s = s.replace(agentName2, phpAgentName);
        if (s.indexOf("members.ordermotion.com/en/hyper_login.asp") >= 0) {
            s = s.replace(agentId, phpSmallAgent);
            s = s.replace(agentId2, phpSmallAgent);
        } else {
            s = s.replace(agentId, phpAgentId);
            s = s.replace(agentId2, phpAgentId);

        }
        s = s.replace(callId, phpCallId);
        s = s.replace(callId2, phpCallId);

        return s;
    }

    private static String replaceTextVars(String s) {
        s = s.replace(agentName, phpAgentName);
        s = s.replace(agentName2, phpAgentName);

        s = s.replace(agentId, phpAgentId);
        s = s.replace(agentId2, phpAgentId);


        s = s.replace(callId, phpCallId);
        s = s.replace(callId2, phpCallId);

        return s;
    }

    public static String createAllFiles(Session sess) {
        try {
            String qry = "select id from owd_client_callcenter";
            Query q = sess.createSQLQuery(qry);
            List results = q.list();

            for (Object row : results.toArray()) {
                 createSingleFile(row.toString(),sess);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return "Success";
    }

    public static String createSingleFile(Integer id, Session sess) {
        return createSingleFile(id + "", sess);
    }

    public static String createSingleFile(String id, Session sess) {
        try {
            OwdClientCallcenter ccClient = loadBeanFromId(id, sess);

            String s = getPHPHtml(ccClient);
              write(s,ccClient.getMlogCampaignName());
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "Success";
    }

    private static String getPHPHtml(OwdClientCallcenter ccClient) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n" +
                        "\"http://www.w3.org/TR/html4/strict.dtd\">\n" +
                        "<html><head>\n" +
                        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                        "<title>" + ccClient.getMlogCampaignName() + " Screen Pop</title>\n" +
                        "<style type=\"text/css\">\n" +

                       "body, html {\n" +
                "\n" +
                "          height: 100%;\n" +
                "          margin: 0;\n" +
                "          padding: 0\n" +
                "      }\n" +
                "\n" +
                "      #header {\n" +
                "          margin: 0;\n" +
                "      }\n" +
                "\n" +
                "      .testClass {\n" +
                "          background-color: #00aaee;\n" +
                "      }\n" +
                "      .body{\n" +
                "          background-color:#0065A2;\n" +
                "          padding-left:5px;\n" +
                "          padding-right:5px;\n" +
                "      }\n" +
                "      div{\n" +
                "          background-color:white;\n" +
                "          padding-left:3px;\n" +
                "          padding-left:3px;\n" +
                "      }\n" +
                "      #topscreen{\n" +
                "         height:500px;\n" +
                "          margin-top:10px;\n" +
                "          overflow:auto;\n" +
                "\n" +
                "      }\n" +
                "    .problemlinks{\n" +
                "        padding-bottom: 3px;\n" +
                "        background-color: #d0d0d0;\n" +
                "        text-align:center;\n" +
                "\n" +
                "    }\n" +
                "    .problemlinks a, .problemlinks a:visited{\n" +
                "        color: #000000;\n" +
                "        padding-left:60px;\n" +
                "        font-size:20px;\n" +
                "        padding-right:60px;\n" +
                "    }\n" +
                "    #bottomwrapper{\n" +
                "        margin-top:10px;\n" +
                "\n" +
                "    }"+
                        "</style>" +

                        "</head>\n" +
                        "<body class=\"body\">\n" +
                        "<div class=\"problemlinks\">\n" +
                        "        <a href=\"http://internal.owd.com:8080/callcenter/problemForm.do?client=" + ccClient.getMlogCampaignName() + "&callId=" + phpCallId + "\"\n" +
                        "           target=\"_blank\"\n" +
                        "           title=\"send problem form\">Escalation Form</a>\n" +

                        "       <!--<a href=\"http://internal.owd.com:8080/callcenter/caseSearch.do?clientId=" + ccClient.getClientFkey() + "\" target=\"_blank\">ProblemForm Search</a>-->\n" +

                        "  <a href=\"http://internal.owd.com/machform/view.php?id=10\" target=\"_blank\">Supervisor Escalation Form</a>      </center>\n" +

                        "  </div>" +
                        "<div  id=\"thetopithink\">\n" +

                        "        <table width=\"100%\"><tr>");
                if (ccClient.getUrlEntry().length() > 0) {
                    sb.append(
                            "<td><a href=\"" + replaceUrlVars(ccClient.getUrlEntry()) + "\" target=\"_blank\">"+replaceUrlVars(ccClient.getUrlEntryName())+"</a></td>\n"
                    );
                }

                if (ccClient.getUrlStatus().length() > 0) {
                    sb.append("<td><a href=\"" + replaceUrlVars(ccClient.getUrlStatus()) + "\" target=\"_blank\">"+replaceUrlVars(ccClient.getUrlStatusName())+"</a></td>");

                }
                if (ccClient.getUrlService().length() > 0) {
                    sb.append("<td><a href=\"" + replaceUrlVars(ccClient.getUrlService()) + "\" target=\"_blank\">"+replaceUrlVars(ccClient.getUrlServiceName())+"</a></td>"
                    );
                }
                if (ccClient.getUrlKbBase().length() > 0) {
                    sb.append("<td><a href=\"" + replaceUrlVars(ccClient.getUrlKbBase()) + "\" target=\"_blank\">Kbase</a></td>");

                }
                if (ccClient.getUrlQuickRef().length() > 0) {
                    sb.append("<td><a href=\"" + replaceUrlVars(ccClient.getUrlQuickRef()) + "\" target=\"_blank\">"+replaceUrlVars(ccClient.getUrlQuickRefName())+"</a></td>\n"
                    );
                }
                if (ccClient.getTopUrlSix().length() > 0) {
                    sb.append("<td><a href=\"" + replaceUrlVars(ccClient.getTopUrlSix()) + "\" target=\"_blank\">" + replaceUrlVars(ccClient.getTopUrlSixName()) + "</a></td>\n"
                    );
                }
                sb.append("</tr>\n" +
                        "        </table>\n" +

                        "</div>\n" +


                        "    <div  id=\"topscreen\" >\n" +
                        "                  " + replaceTextVars(ccClient.getAnnounceScript()) + "\n" +
                        "    </div>\n" +
                        "<div id=\"bottomwrapper\">" +
                        "    <div  id=\"leftscreen\"  style=\"width:48%;float:left\">\n" +
                        "           " + replaceTextVars(ccClient.getSideInfo()) + "\n" +
                        "            </div>\n" +
                        "    <div  id=\"rightscreen\"  style=\"width:48%;float:right;height:800px;\">\n" +
                        "        <iframe id=\"theframe\" src=\"" + replaceTextVars(ccClient.getFrameUrl()) + "\" width=\"98%\" height=\"98%\"></iframe>\n" +

                        "    </div>\n" +

                        "</div>\n" +

                        "</body>\n" +
                        "</html>");


        return sb.toString();
    }

    private static void write(String sb, String campaign) throws Exception {
        String fileName = campaign.replaceAll(" ", "_") + ".php";
        FileWriter fstream = new FileWriter(phpPath + fileName);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(sb);
        //Close the output stream
        out.close();
    }

    public static boolean createScreenPopIndex(Session sess) {
        boolean done = false;
        try {
            String qry = "select Mlog_campaign_name from owd_client_callcenter order by Mlog_campaign_name";
            Query q = sess.createSQLQuery(qry);
            List l = q.list();
            List<String> campaigns = new ArrayList<String>();
            for (Object data : l) {
                campaigns.add(data.toString());
            }
            createIndexFile(campaigns);
            done = true;
        } catch (Exception e) {
            e.printStackTrace();
            done = false;
        }
        return done;
    }

    private static void createIndexFile(List<String> campaigns) {
        StringBuffer sb = new StringBuffer();
        sb.append("<html><head>" +
                "" +
                "<style type=\"text/css\">\n" +
                "a:hover{\n" +
                "background:#ffaa22;\n" +
                "}\n" +
                "div.one{\n" +
                "border-style:dotted;\n" +
                "border-color:black;\n" +
                "border-width:0px 1px 0px 0px;\n" +
                "width:30%;\n" +
                "}\n" +
                "div.two{\n" +
                "position:absolute;\n" +
                "left:33%;\n" +
                "top:65px;\n" +
                "border-style:dotted;\n" +
                "border-color:black;\n" +
                "border-width:0px 1px 0px 0px;\n" +
                "width:30%;\n" +
                "}\n" +
                "a, a:visited{\n" +
                "text-decoration: none;\n" +
                "color: blue;\n" +
                "}\n" +
                "div.three{\n" +
                "position:absolute;\n" +
                "left:66%;\n" +
                "top:65px;\n" +
                "}\n" +
                "div.head {\n" +
                "font-weight:bold;\n" +
                "font-size:17;\n" +
                "padding-top:1px;\n" +
                "padding-bottom:1px;\n" +
                "margin:25px 0px 10px 0px;\n" +
                "padding:10px 0px 7px 20px;\n" +
                "align:middle;\n" +
                "border-style:solid;\n" +
                "border-color:black;\n" +
                "border-width:1px 0px; /* top and bottom borders: 1px; left and right borders: 0px */\n" +
                "line-height:11px;\n" +
                "background-color:#eee;" +
                "}\n" +
                "</style>" +
                "" +
                "<title>OWD Screen Pop Selection Page</title></title><body><div class=\"head\">Please select Screen Pop you need to open</div><div class=\"one\">");
        int i = 0;
        int a = campaigns.size() / 3;
        float f = campaigns.size() / 3f;
        if (f > a) a++;
        int b = a + a;

        for (String campaign : campaigns) {
            campaign = campaign.replaceAll(" ", "_");
            sb.append("            <a href=\"http://internal.owd.com/screen/" + campaign + ".php\" target=\"_blank\" >" + campaign + "</a><br>\n");
            i++;
            if (i == a) {
                sb.append("</div><div class=\"two\">");
            }
            if (i == b) {
                sb.append("</div><div class=\"three\">");
            }
        }
        sb.append("</div><div class=\"head\">&nbsp;</div></body></html>");
        try {
            write(sb.toString(), "index");
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(sb);
        }

    }

    public static String getFive9Script(OwdClientCallcenter ccClient) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n" +
                "\"http://www.w3.org/TR/html4/strict.dtd\">\n" +
                "<html><head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "<title>" + ccClient.getMlogCampaignName() + " Screen Pop</title>\n" +
                "<style type=\"text/css\">\n" +

                "    body, html {width: 100%;height: 100%;  margin: 0;padding: 0}\n" +

                "      #header {margin: 0;}\n" +

                "      .testClass {background-color: #00aaee;}\n" +
                "      .body{background-color:#CCE6FF;padding-left:5px;padding-right:5px;}\n" +
                "      div{background-color:white;padding-left:3px;padding-left:3px;}\n" +

                "    .problemlinks{padding: 3px;background-color: #3a8fc2;text-align:center;}\n" +
                "    .problemlinks a, .problemlinks a:visited{color:#ffffff; padding-left:180px;font-size:20px;}\n"
                +
                " #bottomwrapper{\n" +
                "        margin-top:10px;\n" +
                "    }"+
                "</style>" +

                "</head>\n" +
                "<body class=\"body\">\n" +
                "<div class=\"problemlinks\">\n" +
                "        <a href=\"http://internal.owd.com:8080/callcenter/problemForm.do?client=" + ccClient.getMlogCampaignName() + "&callId=" + phpCallId + "\"\n" +
                "           target=\"_blank\"\n" +
                "           title=\"send problem form\">Escalation Form</a>\n" +

                "       <!--<a href=\"http://internal.owd.com:8080/callcenter/caseSearch.do?clientId=" + ccClient.getClientFkey() + "\" target=\"_blank\">ProblemForm Search</a>-->\n" +

                "  <a href=\"http://internal.owd.com/machform/view.php?id=10\" target=\"_blank\">Supervisor Escalation Form</a>      </center>\n" +

                "  </div>" +
                "<div  id=\"thetopithink\">\n" +

                "        <table width=\"100%\"><tr>");
        if (ccClient.getUrlEntry().length() > 0) {
            sb.append(
                    "<td><a href=\"" + replaceUrlVars(ccClient.getUrlEntry()) + "\" target=\"_blank\">Place Order</a></td>\n"
            );
        }

        if (ccClient.getUrlStatus().length() > 0) {
            sb.append("<td><a href=\"" + replaceUrlVars(ccClient.getUrlStatus()) + "\" target=\"_blank\">Order Status</a></td>");

        }
        if (ccClient.getUrlService().length() > 0) {
            sb.append("<td><a href=\"" + replaceUrlVars(ccClient.getUrlService()) + "\" target=\"_blank\">Customer Service</a></td>"
            );
        }
        if (ccClient.getUrlKbBase().length() > 0) {
            sb.append("<td><a href=\"" + replaceUrlVars(ccClient.getUrlKbBase()) + "\" target=\"_blank\">Kbase</a></td>");

        }
        if (ccClient.getUrlQuickRef().length() > 0) {
            sb.append("<td><a href=\"" + replaceUrlVars(ccClient.getUrlQuickRef()) + "\" target=\"_blank\">AutoShip</a></td>\n"
            );
        }
        if (ccClient.getTopUrlSix().length() > 0) {
            sb.append("<td><a href=\"" + replaceUrlVars(ccClient.getTopUrlSix()) + "\" target=\"_blank\">" + replaceUrlVars(ccClient.getTopUrlSixName()) + "</a></td>\n"
            );
        }
        sb.append("</tr>\n" +
                "        </table>\n" +

                "</div>\n" +


                "    <div  id=\"topscreen\" >\n" +
                "                  " + replaceTextVars(ccClient.getAnnounceScript()) + "\n" +
                "    </div>\n" +
                "<div id=\"bottomwrapper\">" +
                "    <div  id=\"leftscreen\"  style=\"width:48%;float:left\">\n" +
                "           " + replaceTextVars(ccClient.getSideInfo()) + "\n" +
                "            </div>\n" +
                "    <div  id=\"rightscreen\"  style=\"width:48%;float:right;height:800px;\">\n" +
                "        <iframe id=\"theframe\" src=\"" + replaceTextVars(ccClient.getFrameUrl()) + "\" width=\"98%\" height=\"98%\"></iframe>\n" +

                "    </div>\n" +

                "</div>\n" +

                "</body>\n" +
                "</html>");


        return sb.toString();
    }

}
