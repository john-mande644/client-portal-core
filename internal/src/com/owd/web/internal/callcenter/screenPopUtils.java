package com.owd.web.internal.callcenter;

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
          public static void main(String[] args){
              try{
                  Session sess = HibernateSession.currentSession();
                  createAllFiles(sess);
                  log.debug(createScreenPopIndex(sess));
                  // log.debug(createSingleFile("88",HibernateSession.currentSession()));
                  //log.debug(getPHPHtml(loadBeanFromId("31",HibernateSession.currentSession())));
              // loadBeanFromId("1", HibernateSession.currentSession());
              } catch(Exception e){
                  e.printStackTrace();

              }

          }
    public static String getEmailTo(Session sess){
        String s = new String();
           try{
             Query q = sess.createSQLQuery("select value from dbo.app_data where project = 'internal' and description = 'screenpops' and variable = 'emailto'");
               List l = q.list();
               if(l.size()>0){
                   s = l.get(0).toString();
               }
           } catch(Exception e){
               e.printStackTrace();

           }
        return s;
    }
    public static String getEmailToCanada(Session sess){
        String s = new String();
           try{
             Query q = sess.createSQLQuery("select value from dbo.app_data where project = 'internal' and description = 'screenpops' and variable = 'canadaEmail'");
               List l = q.list();
               if(l.size()>0){
                   s = l.get(0).toString();
               }
           } catch(Exception e){
               e.printStackTrace();

           }
        return s;
    }
    public static OwdClientCallcenter loadBeanFromId(String id, Session sess){
        return loadBeanFromId(Integer.valueOf(id),sess);
    }
    public static OwdClientCallcenter loadBeanFromId(Integer id, Session sess){
             OwdClientCallcenter ccClient = new OwdClientCallcenter();
             try{
                  ccClient = (OwdClientCallcenter) sess.load(OwdClientCallcenter.class,id);

             } catch(Exception e){
                 e.printStackTrace();

             }
        return ccClient;
    }

    public static List getClientList(){

        log.debug("Doing list");
       return ClientList.getInstance().getclients();
        
    }

    public static List<screenPopBean> loadPageList(Session sess){
        List<screenPopBean> pages = new ArrayList<screenPopBean>();
                  Query q = sess.createSQLQuery("select id,Mlog_campaign_name,  company_name from owd_client_callcenter, owd_client where client_id = client_fkey order by company_name");
        List results = q.list();

        for(Object row:results){
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
    private static String agentId =  "&lt;AGENT_ID&gt;";
    private static String agentId2 = "<AGENT_ID>";
    private static String callId = "&lt;CALL_ID&gt;";
    private static String callId2 = "<CALL_ID>";

    private static String replaceUrlVars(String s){
        s = s.replace(agentName,phpAgentName);
        s = s.replace(agentName2,phpAgentName);
        if (s.indexOf("members.ordermotion.com/en/hyper_login.asp") >= 0){
               s=s.replace(agentId,phpSmallAgent);
        s=s.replace(agentId2,phpSmallAgent);
        } else{
        s=s.replace(agentId,phpAgentId);
        s=s.replace(agentId2,phpAgentId);

        }
        s = s.replace(callId,phpCallId);
        s = s.replace(callId2,phpCallId);

       return s;
    }

    private static String replaceTextVars(String s){
      s = s.replace(agentName,phpAgentName);
        s = s.replace(agentName2,phpAgentName);

        s=s.replace(agentId,phpAgentId);
        s=s.replace(agentId2,phpAgentId);


        s = s.replace(callId,phpCallId);
        s = s.replace(callId2,phpCallId);

       return s;
    }
    public static String createAllFiles(Session sess){
        try{
          String qry = "select id from owd_client_callcenter";
            Query q = sess.createSQLQuery(qry);
            List results = q.list();

            for (Object row :  results.toArray()){
                createSingleFile(row.toString(),sess);
            }
        } catch(Exception e){
            e.printStackTrace();
            return e.getMessage();
        }

        return "Success";
    }
    public static String createSingleFile(Integer id, Session sess){
     return createSingleFile(id+"",sess);
    }
    public static String createSingleFile(String id, Session sess){
        try{
            OwdClientCallcenter ccClient = loadBeanFromId(id,sess);

           String s = getPHPHtml(ccClient);
            write(s,ccClient.getMlogCampaignName());
        } catch(Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "Success";
    }

    private static String getPHPHtml (OwdClientCallcenter ccClient) throws Exception {
        StringBuffer sb = new StringBuffer();
           sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n" +
                   "\"http://www.w3.org/TR/html4/strict.dtd\">\n" +
                   "<html>\n" +
                   "<head>\n" +
                   "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                   "<title>"+ccClient.getMlogCampaignName()+ " Screen Pop</title>\n" +
                   "\n" +
                   "<style type=\"text/css\">\n" +
                   "    @import \"http://ajax.googleapis.com/ajax/libs/dojo/1.6.0/dijit/themes/soria/soria.css\";\n" +
                   "    @import \"http://ajax.googleapis.com/ajax/libs/dojo/1.6.0/dojo/resources/dojo.css\";\n" +
                   "    body, html {\n" +
                   "        width: 100%;\n" +
                   "        height: 100%;\n" +
                   "        margin: 0;\n" +
                   "        padding: 0\n" +
                   "    }\n" +
                   "\n" +
                   "    #header {\n" +
                   "        margin: 0;\n" +
                   "    }\n" +
                   "\n" +
                   "    .testClass {\n" +
                   "        background-color: #00aaee;\n" +
                   "    }\n" +
                   "\n" +
                   "</style>\n" +
                   "\n" +
                   "\n" +
                   "\n" +
                   "<!-- load the dojo toolkit base -->\n" +
                   "<script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/dojo/1.6.0/dojo/dojo.xd.js\"\n" +
                   "        djConfig=\"parseOnLoad:true,isDebug:false\"></script>\n" +
                   "\n" +
                   "<script type=\"text/javascript\">\n" +
                   "    dojo.require(\"dijit.layout.AccordionContainer\");\n" +

                   "    dojo.require(\"dijit.layout.BorderContainer\");\n" +
                   "    dojo.require(\"dijit.TitlePane\");\n" +
                   "    dojo.require(\"dojo.parser\");\n" +
                   "    dojo.require(\"dojox.layout.ContentPane\");\n" +
                   "    dojo.require(\"dijit._Calendar\");\n" +
                   "    dojo.addOnLoad(function() {\n" +
                   "\n" +
                   "\n" +
                   "        dojo.byId('cal').setAttribute(\"dojoType\", \"dijit._Calendar\");\n" +
                   "        dojo.parser.parse(dojo.byId('cal').parentNode)\n" +
                   "\n" +
                   "\n" +
                   "\n" +
                   "    });</script>\n" +
                   "      \n" +
                   "</head>\n" +
                   "<body class=\"soria\">\n" +
                   "\n" +
                   "\n" +
                   "<div id=\"main\" dojoType=\"dijit.layout.BorderContainer\" liveSplitters=\"false\" design=\"sidebar\"\n" +
                   "     style=\"width:100%; height:200%\">\n" +
                   "\n" +
                   "\n" +
                   "<div dojoType=\"dijit.layout.ContentPane\" region=\"top\" id=\"thetopithink\">\n" +
                   "\n" +
                   "\n" +
                   "        <table width=\"100%\"><tr>");
        if(ccClient.getUrlEntry().length()>0) {
                  sb.append(
                   "<td><a href=\""+replaceUrlVars(ccClient.getUrlEntry())+"\" target=\"_blank\">Place Order</a></td>\n"
                   );
        }

            if(ccClient.getUrlStatus().length()>0){
                sb.append(   "<td><a href=\""+replaceUrlVars(ccClient.getUrlStatus()) +"\" target=\"_blank\">Order Status</a></td>");

            }
        if(ccClient.getUrlService().length()>0){
               sb.append("<td><a href=\""+replaceUrlVars(ccClient.getUrlService())+"\" target=\"_blank\">Customer Service</a></td>"
                   );
            }
        if(ccClient.getUrlKbBase().length()>0){
         sb.append("<td><a href=\""+replaceUrlVars(ccClient.getUrlKbBase())+"\" target=\"_blank\">Kbase</a></td>");

        }
        if(ccClient.getUrlQuickRef().length()>0){
            sb.append( "<td><a href=\""+replaceUrlVars(ccClient.getUrlQuickRef())+"\" target=\"_blank\">AutoShip</a></td>\n"
                  );
        }
        if(ccClient.getTopUrlSix().length()>0){
            sb.append( "<td><a href=\""+replaceUrlVars(ccClient.getTopUrlSix())+"\" target=\"_blank\">"+replaceUrlVars(ccClient.getTopUrlSixName())+"</a></td>\n"
                  );
        }
         sb.append("</tr>\n" +
                   "        </table>\n" +
                   "\n" +
                   "\n" +
                   "</div>\n" +
                   "<div dojoType=\"dijit.layout.ContentPane\" style=\"width: 200px;\" id=\"leftAccordion\" region=\"leading\" splitter=\"false\">\n" +
                   "\n" +
                   "        <br>\n" +
                   "        <center>\n" +
                   "\n" +
                   "On Call:  <script language=\"JavaScript\">\n" +
                   "TargetDate = new Date();\n" +
                   "BackColor = \"white\";\n" +
                   "ForeColor = \"navy\";\n" +
                   "CountActive = true;\n" +
                   "CountStepper = 1;\n" +
                   "LeadingZero = true;\n" +
                   "DisplayFormat = \" %%M%%:%%S%% \";\n" +
                   "FinishMessage = \"It is finally here!\";\n" +
                 "OverCall = "+ccClient.getCallThreshold()+";" +
                   "</script>\n" +
                   "<script language=\"JavaScript\" src=\"/timer.js\"></script> <br>\n" +
                   "            \n" +
                   "            <b><s:property value=\"ccClient.mlogCampaignName\"/> </b>\n" +
                   "        </center>\n" +
                   "\n" +
                   "    <hr>\n" +
                   "    <div id=\"cal\"></div>\n" +
                   "    <br><br>\n" +
                   "    <b>SEND:</b><br>\n" +
                   "    <center>\n" +
                   "        <a href=\"http://internal.owd.com:8080/callcenter/problemForm.do?client="+ccClient.getMlogCampaignName()+"&callId="+phpCallId+"\"\n" +
                   "           target=\"_blank\"\n" +
                   "           title=\"send problem form\">Escalation Form</a>\n" +
                   "\n" +
                   "        <p><a href=\"http://internal.owd.com:8080/callcenter/caseSearch.do?clientId="+ccClient.getClientFkey()+"\" target=\"_blank\">ProblemForm Search</a>\n" +
                   "\n" +
                   "        <p>\n" +
                   "\n" +
                   "  <a href=\"http://internal.owd.com/machform/view.php?id=10\" target=\"_blank\">Escalation Problem Form</a>      </p></center>\n" +
                   "\n" +
                   "\n <iframe id=\"theframeside\" src=\""+ccClient.getSideLinks()+"\" width=\"98%\" height=\"98%\"></iframe>" +
                   "</div>\n" +
                   "<div dojoType=\"dijit.layout.BorderContainer\" liveSplitters=\"true\" region=\"center\" splitter=\"false\">\n" +
                   "\n" +
                   "    <div dojoType=\"dijit.layout.ContentPane\" id=\"topscreen\" region=\"top\" style=\"overflow:auto;height:300px\">\n" +
                   "                  " + replaceTextVars(ccClient.getAnnounceScript()) + "\n" +
                   "    </div>\n" +
                   "    <div dojoType=\"dijit.layout.ContentPane\" id=\"leftscreen\" region=\"left\" style=\"width:48%\">\n" +
                   "           " + replaceTextVars(ccClient.getSideInfo()) + "\n" +
                   "            </div>\n" +
                   "    <div dojoType=\"dojox.layout.ContentPane\" id=\"rightscreen\" region=\"right\" style=\"width:48%\">\n" +
                   "        <iframe id=\"theframe\" src=\"" + replaceTextVars(ccClient.getFrameUrl()) + "\" width=\"98%\" height=\"98%\"></iframe>\n" +
                   "\n" +
                   "\n" +
                   "    </div>\n" +
                   "\n" +
                   "\n" +
                   "</div>\n" +
                   "\n" +
                   "\n" +
                   "</div>\n" +
                   "<!--end Border Container -->\n" +
                   "\n" +
                   "\n" +
                   "</body>\n" +
                   "</html>");


        return sb.toString();
    }
    private static void write(String sb, String campaign) throws Exception{
       String fileName = campaign.replaceAll(" ","_")+".php";
     FileWriter fstream = new FileWriter(phpPath+fileName);
      BufferedWriter out = new BufferedWriter(fstream);
  out.write(sb);
  //Close the output stream
  out.close();
}
    public static boolean createScreenPopIndex(Session sess){
       boolean done = false;
                    try{
                  String qry = "select Mlog_campaign_name from owd_client_callcenter order by Mlog_campaign_name";
                        Query q = sess.createSQLQuery(qry);
                        List l = q.list();
                     List<String> campaigns = new ArrayList<String>();
                        for (Object data : l){
                              campaigns.add(data.toString());
                        }
                         createIndexFile(campaigns);
                        done = true;
                    } catch(Exception e){
                        e.printStackTrace();
                        done = false;
                    }
        return done;
    }
    private static void createIndexFile(List<String> campaigns){
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
        int i=0;
        int a = campaigns.size()/3;                   
        float f = campaigns.size()/3f;
        if(f>a)a++;
        int b = a+a;

        for(String campaign:campaigns){
              campaign = campaign.replaceAll(" ","_");
            sb.append("            <a href=\"http://internal.owd.com/screen/"+campaign+".php\" target=\"_blank\" >"+campaign+"</a><br>\n");
          i++;
          if(i==a){
            sb.append("</div><div class=\"two\">");
          }
          if(i==b){
              sb.append("</div><div class=\"three\">");
          }
        }
        sb.append("</div><div class=\"head\">&nbsp;</div></body></html>");
      try{
      write(sb.toString(),"index");
      }catch (Exception e){
          e.printStackTrace();
          log.debug(sb);
      }

    }

}
