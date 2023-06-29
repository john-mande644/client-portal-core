package com.owd.core.managers

import com.owd.core.OWDUtilities
import com.owd.hibernate.HibernateFogbugzSession

import java.sql.ResultSet

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 1/28/12
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
class CaseTrackerManager {

  static String apiUrl = "http://casetracker.owd.com/casetracker/api.asp";

    private static final String login="donotreply@owd.com";
    private static final String password="badhorse57601";

  static public void main(String[] args) {

    println "hello"
    def token = getLoginToken();
    //  setFilter("232", token);
    //    createNewCaseForClient("testing case", "the body", "441", null, token)
    //  createNewCaseForProject("testing case","the body","14",token)
    println createNewCaseForClient("testing case", "hello world", "489", null, token)
    //  createNewCaseForClient("testing case", "message bodyCR\rmessage bodyLF\nmessage bodyCRLF\r\nmessage body end", "112", null, token)
  }

    static public String createNewCaseForClient(String title, String body, String clientID) {
        return   createNewCaseForClient( title,  body,  clientID,  null, getLoginToken())
    }

  static public String createNewCaseForClient(String title, String body, String clientID, String email) {
    return   createNewCaseForClient( title,  body,  clientID,  email, getLoginToken())
  }

  static public String createNewCaseForClient(String title, String body, String clientID, String email, String token) {
    String newCaseLink = apiUrl + "?cmd=new&token=" + token + "&sTitle=" + URLEncoder.encode(title) + "&sEvent=" + URLEncoder.encode(body) + "&cols=ixBug,sTitle,sProject,ixProject,sArea,sPersonAssignedTo,sStatus";

    if (email != null) {
      newCaseLink = newCaseLink + "&sCustomerEmail=" + URLEncoder.encode(email);
    }

    String sql = "select top 1 pro.ixProject as pProject,m.* from project pro join mailbox m on m.ixProject=pro.ixProject join   vw_client_projects on pid=pro.ixProject where cid=" + clientID + "; ";

    println sql
    ResultSet rs = HibernateFogbugzSession.getResultSet(sql);
    if (rs.next()) {



      if (!("0".equals(rs.getString("ixMailbox")))) {
          newCaseLink = newCaseLink + "&ixProject=" + rs.getString("ixProject") + "&ixMailbox=" + rs.getString("ixMailbox");
        newCaseLink = newCaseLink + "&ixArea=" + rs.getString("ixArea") + "&ixPriority=" + rs.getString("ixPriority") +
                "&ixPersonAssignedTo=" + rs.getString("ixPersonAssignedTo");
      } else {
        newCaseLink = newCaseLink + "&ixProject=" + rs.getString("pProject")
      }

    }else
    {
        newCaseLink = newCaseLink + "&ixProject=13";
    }

    rs.close();
    HibernateFogbugzSession.closeSession();



    String caseID = sendCasetrackerRequest(newCaseLink)
     return caseID

  }

  static public String createNewCaseForProject(String title, String body, String projectID, String token) {
    String newCaseLink = apiUrl + "?cmd=new&token=" + token + "&ixProject=" + projectID + "&sTitle=" + URLEncoder.encode(title) + "&sEvent=" + URLEncoder.encode(body) + "&cols=ixBug,sTitle,sProject,ixProject,sArea,sPersonAssignedTo,sStatus";

    String caseID = sendCasetrackerRequest(newCaseLink)
     return caseID


  }

  static public String createNewSendEmailCaseForClient(String title, String body, String clientID, String toEmail, String token) {
    String newCaseLink = apiUrl + "?cmd=new&token=" + token + "&sTitle=" + URLEncoder.encode(title) + "&sEvent=" + URLEncoder.encode("Placeholder case for outbound email");
    String sendMailCaseLink = apiUrl + "?cmd=email&token=" + token + "&sSubject=" + URLEncoder.encode(title) + "&sBCC=" + "&sTo=" + URLEncoder.encode(toEmail) + "&sEvent=" + URLEncoder.encode(body);
    String closeCaseLink = apiUrl + "?cmd=close&token=" + token;
    String resolveCaseLink = apiUrl + "?cmd=resolve&sEvent=" + URLEncoder.encode("Closing email placeholder to await reply") + "&token=" + token;

    //newCaseLink = newCaseLink + "&sCustomerEmail=" + URLEncoder.encode(toEmail);


    String sql = "select top 1 pro.ixProject as pProject,m.* from project pro join mailbox m on m.ixProject=pro.ixProject join   vw_client_projects on pid=pro.ixProject where cid=" + clientID + "; ";

    println sql
    ResultSet rs = HibernateFogbugzSession.getResultSet(sql);
    if (rs.next()) {

      newCaseLink = newCaseLink + "&ixProject=" + rs.getString("ixProject") + "&ixMailbox=" + rs.getString("ixMailbox");

      if (!("0".equals(rs.getString("ixMailbox")))) {
        newCaseLink = newCaseLink + "&ixArea=" + rs.getString("ixArea") + "&ixPriority=" + rs.getString("ixPriority") +
                "&ixPersonAssignedTo=" + rs.getString("ixPersonAssignedTo");
        sendMailCaseLink = sendMailCaseLink + "&sFrom=" + URLEncoder.encode(rs.getString("sEmail"))
      } else {
        newCaseLink = newCaseLink + "&ixProject=" + rs.getString("pProject")
      }

    }

    rs.close();
    HibernateFogbugzSession.closeSession();

    String caseID = sendCasetrackerRequest(newCaseLink)
    caseID = sendCasetrackerRequest(sendMailCaseLink + "&ixBug=" + caseID)
    caseID = sendCasetrackerRequest(resolveCaseLink + "&ixBug=" + caseID)
    caseID = sendCasetrackerRequest(closeCaseLink + "&ixBug=" + caseID)

    return caseID
  }

  static String sendCasetrackerRequest(String requestUrl) {
    URL newCaseUrl = new URL(requestUrl);

    URLConnection testConnection = (URLConnection) newCaseUrl.openConnection();
    testConnection.setReadTimeout(120000);
    testConnection.setConnectTimeout(120000);

    String response = OWDUtilities.parseISToString(testConnection.getInputStream())
    println response
    Integer caseID = Integer.parseInt(new XmlParser().parseText(response).case[0].'@ixBug');

    return caseID;

    //returns case ID or throws exception
  }

  static void setFilter(String filterID, String token) {
    def listerUrl = apiUrl + "?cmd=listFilters&token=" + token;
    URL allFiltersUrl = new URL(listerUrl);

    URLConnection testConnection = (URLConnection) allFiltersUrl.openConnection();
    testConnection.setReadTimeout(120000);
    testConnection.setConnectTimeout(120000);

    println OWDUtilities.parseISToString(testConnection.getInputStream())

    //      Node respf = new XmlParser().parse(new BufferedReader(new InputStreamReader(testConnection.getInputStream())))


    def serviceUrl = apiUrl + "?cmd=saveFilter&sFilter=" + filterID + "&token=" + token;

    println serviceUrl
    URL testUrl = new URL(serviceUrl);

    testConnection = (URLConnection) testUrl.openConnection();
    testConnection.setReadTimeout(120000);
    testConnection.setConnectTimeout(120000);

    Node resp = new XmlParser().parse(new BufferedReader(new InputStreamReader(testConnection.getInputStream())))

  }

  static String getLoginToken() {

//http://www.example.com/api.asp?cmd=logon&email=xxx@example.com&password=BigMac

    def serviceUrl = apiUrl + "?cmd=logon&email=" + login + "&password=" + password


    URL testUrl = new URL(serviceUrl);

    URLConnection testConnection = (URLConnection) testUrl.openConnection();
    testConnection.setReadTimeout(120000);
    testConnection.setConnectTimeout(120000);

    Node resp = new XmlParser().parse(new BufferedReader(new InputStreamReader(testConnection.getInputStream())))

    println "response OK:" + resp.token.text();

    return resp.token.text();

  }

}
