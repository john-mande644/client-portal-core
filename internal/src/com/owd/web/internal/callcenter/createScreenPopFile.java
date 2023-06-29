package com.owd.web.internal.callcenter;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.HibUtils;
import com.owd.intranet.beans.selectList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.io.FileWriter;
import java.io.BufferedWriter;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Mar 10, 2008
 * Time: 4:48:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class createScreenPopFile {
private final static Logger log =  LogManager.getLogger();
       private static String agentId = "<?php echo $_GET[\"agentid\"] ?>";
       private static String callId = "<?php echo $_GET[\"callid\"] ?>";
    private static String agentName ="<?php echo $_GET[\"agentfname\"] ?>";
    private static String smallAgent = "<?php echo $_GET[\"sagentid\"] ?>";
    private static String path = "/var/www/html/screen/";
    private static String ext = ".php";


    public static void main(String[] args){
          try{
            updateAll();

          }   catch(Exception e){

          }
    }
    public static void createIndexFile(Set<String> campaigns){
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
                "<title>OWD Escalation Form Selection Page</title></title><body><div class=\"head\">Please select Campaign to send Escalation form for</div><div class=\"one\">");
        int i=0;
        int a = campaigns.size()/3;
        float f = campaigns.size()/3f;
        if(f>a)a++;
        int b = a+a;
        
        for(String campaign:campaigns){
            sb.append("            <a href=\"http://internal.owd.com:8080/callcenter/problemForm.do?client="+campaign+"\" target=\"_blank\" title=\"send problem form\">"+campaign+"</a><br>\n");
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
      write(sb,"index");
      }catch (Exception e){

      }

    }
    public static void updateAll() throws Exception{

       String sql = "select id from owd_client_callcenter";
        PreparedStatement st = HibernateSession.getPreparedStatement(sql);
        ResultSet rs = st.executeQuery();
        List<String> ids = new ArrayList();
       Set<String> campaigns = new TreeSet();

        while(rs.next()){
            ids.add(rs.getString(1));
        }
        HibernateSession.closeSession();

      for(String id:ids){
        updateSingle(id,campaigns);
      }
      createIndexFile(campaigns);

    }
     public static void updateSingle(String id,Set campaigns) throws Exception{
               spForm sp =new spForm();
        lookUpInfo(id,sp);

        createFile(sp);
           campaigns.add(sp.getCampaign());


    }
    public static void updateSingle(String id) throws Exception{
               spForm sp =new spForm();
        lookUpInfo(id,sp);

        createFile(sp);



    }
    public static void lookUpInfo(String id, spForm sf){
        try {



            //load info
            String sql = "select * from owd_client_callcenter where id = ?";
            PreparedStatement stmt =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(sql);
            stmt.setString(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
              sf.setCampaign(rs.getString("Mlog_campaign_name"));
                sf.setScript(rs.getString("announce_script"));
                // do_entry = rs.getInt("do_entry");
                // do_status = rs.getInt("do_status");
                // do_service = rs.getInt("do_service");
                sf.setClientFkey(rs.getInt("client_fkey") + "");
                sf.setUrlEntry(rs.getString("url_entry"));
                sf.setUrlStatus(rs.getString("url_status"));
                sf.setUrlService(rs.getString("url_service"));
                log.debug("kb");
                sf.setKb(rs.getString("url_kb_base"));
                log.debug("qf");
                //   do_orderref_entry = rs.getInt("do_orderref_entry");
                sf.setQuickRef(rs.getString("url_quick_ref"));
                log.debug("w");
                sf.setDoScript(rs.getInt("script") + "");
                log.debug("before all");
                sf.setScript(StringUtils.replace(sf.getScript(), "<AGENT_NAME>", agentName));
                //  sf.setScript(sf.getScript().replaceAll("<AGENT_NAME>",agentName));
                sf.setScript(StringUtils.replace(sf.getScript(), "<CALL_ID>",callId));
                sf.setScript(StringUtils.replace(sf.getScript(), "<AGENT_ID>", agentId));

                sf.setUrlEntry(StringUtils.replace(sf.getUrlEntry(), "<AGENT_NAME>", agentName));
                sf.setUrlEntry(StringUtils.replace(sf.getUrlEntry(), "<CALL_ID>",callId));
                 if (sf.getUrlEntry().indexOf("members.ordermotion.com/en/hyper_login.asp") >= 0) {
                    sf.setUrlEntry(StringUtils.replace(sf.getUrlEntry(), "<AGENT_ID>", smallAgent));
                }else{
                    sf.setUrlEntry(StringUtils.replace(sf.getUrlEntry(), "<AGENT_ID>", agentId));
                 }
                log.debug("middle");
                sf.setUrlStatus(StringUtils.replace(sf.getUrlStatus(), "<AGENT_NAME>", agentName));
                sf.setUrlStatus(StringUtils.replace(sf.getUrlStatus(), "<CALL_ID>",callId));
               if (sf.getUrlService().indexOf("members.ordermotion.com/en/hyper_login.asp") >= 0) {
                    sf.setUrlService(StringUtils.replace(sf.getUrlService(), "<AGENT_ID>", smallAgent));
               }   else{
                    sf.setUrlService(StringUtils.replace(sf.getUrlService(), "<AGENT_ID>", agentId));
               }

                sf.setUrlService(StringUtils.replace(sf.getUrlService(), "<AGENT_NAME>", agentName));
                sf.setUrlService(StringUtils.replace(sf.getUrlService(), "<CALL_ID>",callId));

                log.debug("where are we");
                String ex[] = sf.getScript().split("<EXTRA\\|");
                log.debug(ex.length);
                if (ex.length > 1) {
                    log.debug("Dooing extra stuff, yeah for progrss");
                    sf.setScript(ex[0]);
                    List transfer = new ArrayList();
                    for (int i = 1; i < ex.length; i++) {
                        log.debug("Doing  " + i + " xxxxxxxxxx");
                        String parts[] = ex[i].split(">");
                        String title[] = parts[0].split(":");
                        log.debug(parts[0]);
                        parts[1] = StringUtils.replace(parts[1], "&br&", "<br>");
                        log.debug(parts[1]);
                        selectList sl = new selectList();
                        sl.setAction(title[0]);
                        sl.setDisplay(parts[1]);
                        if (title.length > 1) {
                            sl.setColor(title[1]);
                            log.debug("success");
                        } else {
                            sl.setColor("black");
                            log.debug("default to black");
                        }
                        transfer.add(i - 1, sl);
                    }
                    sf.setExtras(transfer);
                }


            } else {
                sf.setScript(sf.getCampaign() + " is not a valid Campaign setup for screenpops");
            }


        } catch (Exception e) {
            e.printStackTrace();



        }finally{
            HibernateSession.closeSession();
        }




    }


    public static void createFile(spForm sf) throws Exception{
     StringBuffer sb = new StringBuffer();

      sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Strict//EN\">" +
              "<html smlns=\"http://www.w3.org/1999/xhtml\">\n" +
              "<head>\n" +
              "<link type=\"text/css\" rel=\"stylesheet\" href=\"screenpop.css\">\n" +
              "</link>\n" +
               "<script type=\"text/javascript\">" +
              "function switchMenu(obj) {\n" +
              "\tvar el = document.getElementById(obj);\n" +
              "   \n" +
              "    if ( el.style.display != \"none\" ) {\n" +
              "\t\tel.style.display = 'none';\n" +
              "\t}\n" +
              "\telse {\n" +
              "\t\tel.style.display = '';\n" +
              "\t}}" +
              "" +
              ""+
              "</script>"+

              "</head>\n" +
              "<body>");

        sb.append("<!----- Left side menu ---------------------------------------------- -->\n" +
                "<div class=\"box\">\n" +
                "    <div class=\"boxtop\">\n" +
                "        <div></div>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"content\">\n" +
                "        <!-- menu content -->\n" +
                "        <center><script language=\"JavaScript\">\n" +
                "TargetDate = new Date();\n" +
                "BackColor = \"white\";\n" +
                "ForeColor = \"navy\";\n" +
                "CountActive = true;\n" +
                "CountStepper = 1;\n" +
                "LeadingZero = true;\n" +
                "DisplayFormat = \" %%M%%:%%S%% \";\n" +
                "FinishMessage = \"It is finally here!\";\n" +
                "</script>\n" +
                "On Call: <script language=\"JavaScript\" src=\"timer.js\" module=\"\"/></script><br><b>"+sf.getCampaign()+"</b></center>\n" +
                "\n" +
                "        <br><br>\n" +
                "        <center>\n" +
                "            <iframe style=\"width: 139px; height: 139px;\" name=\"gToday:mini:agenda.js\" id=\"gToday:mini:agenda.js\" src=\"screenpops/mini/iflateng.htm\" scrolling=\"no\" frameborder=\"0\">\n" +
                "            </iframe>\n" +
                "        </center>\n" +
                "        <br><br>\n" +
                "        <b>SEND:</b><br>\n" +
                "        <center>\n" +
                "            <a href=\"http://internal.owd.com:8080/callcenter/problemForm.do?client="+sf.getCampaign()+"&callId="+callId+"\" target=\"_blank\" title=\"send problem form\">Escalation Form</a>\n" +
                "            <p>" +
                "<a href=\"http://internal.owd.com:8080/callcenter/caseSearch.do\" target=\"_blank\">Escalation Form Search</a>\n" +
                "            <p>\n" +
                "        </p></center>\n" +
                "        <span id=\"doscript\">\n" +
                "\n" +
                "        \n" +
                "       </span>\n" +
                "    </div>\n" +
                "    <div class=\"boxbottom\">\n" +
                "        <div></div>\n" +
                "    </div>\n" +
                "\n" +
                "</div>");
           sb.append("<!------------------  Header links and stuff ----------------------------->\n" +
                   "<div class=\"tbox\">\n" +
                   "    <div class=\"tboxtop\">\n" +
                   "        <div></div>\n" +
                   "    </div>\n" +
                   "    <div class=\"tcontent\">\n" +
                   "      <span id=\"links\">\n" +
                   "       <!--  link content ---------->\n" +
                   "          <center>\n" +
                   "              <table width=\"100%\" class=\"linkss\">\n" +
                   "                  <tr>");

             if(sf.getUrlEntry()!=null && sf.getUrlEntry().length()>1){
                 sb.append("<td>\n" +
                         "<a href=\""+sf.getUrlEntry()+"\" target=\"_blank\">Place Order</a>" +
                         "</td>");
                 }
            if(sf.getUrlStatus()!=null && sf.getUrlStatus().length()>1){
                 sb.append("<td>\n" +
                         "<a href=\""+sf.getUrlStatus()+"\" target=\"_blank\">Order Status</a>" +
                         "</td>");
                 }
        if(sf.getUrlService()!=null && sf.getUrlService().length()>1){
                        sb.append("<td>\n" +
                                "<a href=\""+sf.getUrlService()+"\" target=\"_blank\">Customer Service</a>" +
                                "</td>");
                        }

            if(sf.getKb()!=null && sf.getKb().length()>1){
                        sb.append("<td>\n" +
                                "<a href=\""+sf.getKb()+"\" target=\"_blank\">Kbase</a>" +
                                "</td>");
                        }
            if(sf.getQuickRef()!=null && sf.getQuickRef().length()>1){
                        sb.append("<td>\n" +
                                "<a href=\""+sf.getQuickRef()+"\" target=\"_blank\">Quick ref</a>" +
                                "</td>");
                        }

       sb.append("\n" +
               "                  </tr>\n" +
               "              </table>\n" +
               "\n" +
               "\n" +
               "          </center>\n" +
               "\n" +
               "\n" +
               "\n" +
               "      </span></div>  <div class=\"tboxbottom\">\n" +
               "        <div></div>\n" +
               "    </div></div>");
        sb.append("<!--- main script area-->\n" +
                "\n" +
                "<div class=\"sbox\">\n" +
                "    <div class=\"sboxtop\">\n" +
                "        <div></div>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"scontent\">\n" +
                "          <span id=\"script\">\n" +
                "              <center>\n" +
                "                  <div class=\"script\">\n" +

                "\n" +
                "                      "+sf.getScript()+"\n" +
                "                  </div>\n" +
                "              </center>\n" +
                "      </span>") ;

          sb.append("<span id=\"extras\">\n" +
                  "        <center>")  ;

        if(sf.getExtras()!= null){
            sb.append(" <div id=\"extras\">");
            for(selectList sl:sf.getExtras()){

            sb.append(" <a onclick=\"javascript:switchMenu('"+sl.getAction()+"');\"><font color=\""+sl.getColor()+"\">"+sl.getAction()+"</font><img src=\"callcenter/images/arrow.gif\">  </a>\n" +
                    "                         \n" +
                    "                        <div id=\""+sl.getAction()+"\" class=\"switch\" style=\"display: none;\">");

             sb.append(sl.getDisplay());

           sb.append(" </div>\n" +
                   "                        <br>");

            }
           sb.append("</div>");
        }
     sb.append(" </center>\n" +
             "          </span>\n" +
             "    </div>\n" +
             "    <div class=\"sboxbottom\">\n" +
             "        <div></div>\n" +
             "    </div>\n" +
             "\n" +
             "</div>\n" +
             "\n" +

             "</body>\n" +
             "</html>");
         write(sb,sf.getCampaign());

    }

 public static void write(StringBuffer sb, String campaign) throws Exception{
       String fileName = campaign.replaceAll(" ","_")+".php";
     FileWriter fstream = new FileWriter(path+fileName);
      BufferedWriter out = new BufferedWriter(fstream);
  out.write(sb.toString());
  //Close the output stream
  out.close();





 }
}
