package com.owd.casetracker

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.ResultSet
import com.owd.hibernate.HibernateFogbugzSession
import com.owd.core.OWDUtilities

/**
 * Created by IntelliJ IDEA.
 * User: StewartBuskirk
 * Date: Sep 3, 2010
 * Time: 9:23:28 AM
 * To change this template use File | Settings | File Templates.
 */
class CasetrackerAPI {

    static String apiUrl = "http://casetracker.owd.com/casetracker/api.asp";

    static public void main(String[] args)
    {

        println "hello"
        def token = getLoginToken("owditadmin@owd.com", "torque");
        setFilter("232",token);
        createNewCaseForClient("testing case","the body","441",null,token)
      //  createNewCaseForProject("testing case","the body","14",token)


    }


     static public void createNewCaseForClient(String title, String body, String clientID, String email, String token)
    {
         String newCaseLink = apiUrl+"?cmd=new&token="+ token+"&sTitle="+URLEncoder.encode(title)+"&sEvent="+URLEncoder.encode(body)+"&cols=ixBug,sTitle,sProject,ixProject,sArea,sPersonAssignedTo,sStatus";

        if(email != null)
       {
           newCaseLink = newCaseLink+"&sCustomerEmail="+URLEncoder.encode(email);
        }

         String sql = "select top 1 p.ixProject as pproject,ISNULL(m.ixMailbox,0) as ixMailbox,m.ixProject,m.ixArea,m.ixPriority,m.ixPersonAssignedTo "+
                " from  FogBUGZ.dbo.Project p join FogBUGZ.dbo.Groups g on g.ixGroup=p.ixGroup" +
                " and convert(varchar,g.sNotes)=convert(varchar," + clientID + ") left outer join FogBUGZ.dbo.Mailbox m on p.ixProject=m.ixProject ";
        println sql
        ResultSet rs = HibernateFogbugzSession.getResultSet(sql);
        if (rs.next()) {

        newCaseLink = newCaseLink+"&ixProject="+rs.getString("ixProject")+"&ixMailbox="+rs.getString("ixMailbox");

            if(!("0".equals(rs.getString("ixMailbox"))))
            {
                newCaseLink = newCaseLink+"&ixArea="+rs.getString("ixArea")+"&ixPriority="+rs.getString("ixPriority")+
            "&ixPersonAssignedTo="+rs.getString("ixPersonAssignedTo");
            } else
        {
             newCaseLink = newCaseLink+"&ixProject="+rs.getString("pProject")
        }

        }

        rs.close();
        HibernateFogbugzSession.closeSession();

          println newCaseLink
                URL newCaseUrl = new URL(newCaseLink);

                       URLConnection testConnection = (URLConnection) newCaseUrl.openConnection();
                       testConnection.setReadTimeout(120000);
                       testConnection.setConnectTimeout(120000);

                println OWDUtilities.parseISToString(testConnection.getInputStream())



    }

    static public void createNewCaseForProject(String title, String body, String projectID,  String token)
   {
        String newCaseLink = apiUrl+"?cmd=new&token="+ token+"&ixProject="+projectID+"&sTitle="+URLEncoder.encode(title)+"&sEvent="+URLEncoder.encode(body)+"&cols=ixBug,sTitle,sProject,ixProject,sArea,sPersonAssignedTo,sStatus";

               URL newCaseUrl = new URL(newCaseLink);

                      URLConnection testConnection = (URLConnection) newCaseUrl.openConnection();
                      testConnection.setReadTimeout(120000);
                      testConnection.setConnectTimeout(120000);

               println OWDUtilities.parseISToString(testConnection.getInputStream())



   }


     static void setFilter(String filterID, String token)
    {
        def listerUrl = apiUrl+"?cmd=listFilters&token=" + token;
        URL allFiltersUrl = new URL(listerUrl);

               URLConnection testConnection = (URLConnection) allFiltersUrl.openConnection();
               testConnection.setReadTimeout(120000);
               testConnection.setConnectTimeout(120000);

        println OWDUtilities.parseISToString(testConnection.getInputStream())

         //      Node respf = new XmlParser().parse(new BufferedReader(new InputStreamReader(testConnection.getInputStream())))


        def serviceUrl = apiUrl+"?cmd=saveFilter&sFilter=" + filterID + "&token=" + token;

        println serviceUrl
        URL testUrl = new URL(serviceUrl);

        testConnection = (URLConnection) testUrl.openConnection();
        testConnection.setReadTimeout(120000);
        testConnection.setConnectTimeout(120000);

        Node resp = new XmlParser().parse(new BufferedReader(new InputStreamReader(testConnection.getInputStream())))

    }

      static String getLoginToken(String username, String password)
    {

//http://www.example.com/api.asp?cmd=logon&email=xxx@example.com&password=BigMac

        def serviceUrl = apiUrl+"?cmd=logon&email=" + username + "&password=" + password


        URL testUrl = new URL(serviceUrl);

        URLConnection testConnection = (URLConnection) testUrl.openConnection();
        testConnection.setReadTimeout(120000);
        testConnection.setConnectTimeout(120000);

        Node resp = new XmlParser().parse(new BufferedReader(new InputStreamReader(testConnection.getInputStream())))

        println "response OK:" + resp.token.text();

        return resp.token.text();

         }
}
