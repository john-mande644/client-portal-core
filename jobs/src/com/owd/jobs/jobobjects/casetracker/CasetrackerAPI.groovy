package com.owd.jobs.jobobjects.casetracker

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.managers.CaseTrackerManager
import com.owd.hibernate.HibernateFogbugzSession

import java.sql.ResultSet

/**
 * Created by IntelliJ IDEA.
 * User: StewartBuskirk
 * Date: Sep 3, 2010
 * Time: 9:23:28 AM
 * To change this template use File | Settings | File Templates.
 */

class CasetrackerAPI extends CaseTrackerManager{


  static Map<String,String> dcWorkOrderMap;
  static {
    dcWorkOrderMap = new HashMap<String,String>();
    dcWorkOrderMap.put("DC1","81");
    dcWorkOrderMap.put("DC6","103");
    dcWorkOrderMap.put("DC7","108");

  }

  static public void main(String[] args) {

    println "hello"
    def token = getLoginToken();
    println token

   // println injectWorkOrder("test", "a body", "test", "DC1")
    //  setFilter("232", token);
    //    createNewCaseForClient("testing case", "the body", "441", null, token)
    //  createNewCaseForProject("testing case","the body","14",token)
  //  createNewSendEmailCaseForClient("testing case", "hello world", "437", "owditadmin@owd.com", token)
    //  createNewCaseForClient("testing case", "message bodyCR\rmessage bodyLF\nmessage bodyCRLF\r\nmessage body end", "112", null, token)
  }

  static public String createCasetrackerCaseForWarehouse(String title, String body, int clientId, String dcLocCode)
  {
    String newCaseLink = apiUrl + "?cmd=new&token=" + getLoginToken() + "&sTitle=" + URLEncoder.encode(title) + "&sEvent=" + URLEncoder.encode(body) + "&cols=ixBug,sTitle,sProject,ixProject,sArea,sPersonAssignedTo,sStatus";


    String sql = "select top 1 pro.ixProject as pProject,m.* from project pro join mailbox m on m.ixProject=pro.ixProject join   vw_client_projects on pid=pro.ixProject where cid="+clientId+"; ";

    println sql
    ResultSet rs = HibernateFogbugzSession.getResultSet(sql);
    if (rs.next()) {




      newCaseLink = newCaseLink + "&ixProject=" + rs.getString("ixProject")+
              "&ixPersonAssignedTo=" +(dcWorkOrderMap.containsKey(dcLocCode)?dcWorkOrderMap.get(dcLocCode):"UNKNOWN")+
              "&ixCategory=3&plugin_customfields_at_fogcreek_com_workxorderxlocationm7a="+dcLocCode+
              "&plugin_customfields_at_fogcreek_com_billingxdepartmentx63=Warehouse";
      //


    }else
    {
      newCaseLink = newCaseLink + "&ixProject=13";
    }

    rs.close();
    HibernateFogbugzSession.closeSession();



    String caseID = sendCasetrackerRequest(newCaseLink)
    return caseID
  }

    static public void createCasetrackerCase(String title, String body, int clientId)
    {
        createNewCaseForClient(title, body, ""+clientId, null, getLoginToken())
    }


  static public String injectWorkOrder(String title, String body, String brandGroup, String facilityCode, int clientId) {


    //temp to handle transition
    if(clientId==0){
      clientId=489;
    }
    String newCaseLink = apiUrl + "?cmd=new&token=" + getLoginToken() + "&sTitle=" + URLEncoder.encode(title) + "&sEvent=" + URLEncoder.encode(body) + "&cols=ixBug,sTitle,sProject,ixProject,sArea,sPersonAssignedTo,sStatus,plugin_customfields_at_fogcreek_com_symphonyxbrands3b";


    String sql = "select top 1 pro.ixProject as pProject,m.* from project pro join mailbox m on m.ixProject=pro.ixProject join   vw_client_projects on pid=pro.ixProject where cid="+clientId+"; ";

    println sql
    ResultSet rs = HibernateFogbugzSession.getResultSet(sql);
    if (rs.next()) {




      newCaseLink = newCaseLink + "&ixProject=" + rs.getString("ixProject")+
              "&ixPersonAssignedTo=" +(dcWorkOrderMap.containsKey(facilityCode)?dcWorkOrderMap.get(facilityCode):"UNKNOWN")+"&plugin_customfields_at_fogcreek_com_symphonyxbrands3b="+ URLEncoder.encode(brandGroup)+
              "&ixCategory=3&plugin_customfields_at_fogcreek_com_workxorderxlocationm7a="+facilityCode+
              "&plugin_customfields_at_fogcreek_com_billingxdepartmentx63=Warehouse";
        //


    }else
    {
      newCaseLink = newCaseLink + "&ixProject=13";
    }

    rs.close();
    HibernateFogbugzSession.closeSession();



    String caseID = sendCasetrackerRequest(newCaseLink)
    return caseID

  }


}
