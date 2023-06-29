//revised on June 12, 2002
package com.owd.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import org.hibernate.criterion.Restrictions;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ChatbotServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();


    public void init(ServletConfig config)
            throws ServletException {
        ////log.debug("in API init");
        super.init(config);

    }

    public void destroy() {
        ////log.debug("in API destroy");
        super.destroy();

    }

    //GET requests not supported
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        doGet(req,resp);

        //all requests should be POST
    }
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {

        String response = "";

        try {
            req.setCharacterEncoding("UTF-8");


        //    log.debug("in API post2");

        try {
            resp.setCharacterEncoding("UTF-8");

            String command = req.getParameter("command");
            String args = req.getParameter("text").trim();

            int argsInt = 0;
            try{
                argsInt = Integer.parseInt(args);
            } catch(Exception exxx){}

            if(command.equals("/owdstock"))
            {

                String encryptValue = req.getParameter("token");
                log.debug("auth="+encryptValue);
                String decryptValue = "waNwANJ1WebBBGlZms2TAziC";
                log.debug("decrypt="+decryptValue);
                if (!(encryptValue.equals(decryptValue))) {
                    throw new Exception("Unauthorized");
                }
                List<OwdInventory> items = HibernateSession.currentSession().createCriteria(OwdInventory.class).setMaxResults(10000)
                .add(Restrictions.or(Restrictions.eq("inventoryId", argsInt),Restrictions.eq("inventoryNum", args))).list();
                if(items.size()==0)
                {
                   throw new Exception("No matching items found. Sorry!");
                } else {
                    for(OwdInventory item:items)
                    {
                        response=response+item.getInventoryNum()+":"+item.getDescription()+"\n";
                    }
                }
            }else if(command.equals("/owdtest"))
            {

                String encryptValue = req.getParameter("token");
                log.debug("auth="+encryptValue);
                String decryptValue = "6uS14ERv9KQGrtJY9GN3B4CI";
                log.debug("decrypt="+decryptValue);
                if (!(encryptValue.equals(decryptValue))) {
                    throw new Exception("Unauthorized");
                }
                response="{\nattachments[" +
                        "    \"fallback\": \"Required plain-text summary of the attachment.\",\n" +
                        "\n" +
                        "    \"color\": \"#36a64f\",\n" +
                        "\n" +
                        "    \"pretext\": \"Optional text that appears above the attachment block\",\n" +
                        "\n" +
                        "    \"author_name\": \"Bobby Tables\",\n" +
                        "    \"author_link\": \"http://flickr.com/bobby/\",\n" +
                        "    \"author_icon\": \"http://flickr.com/icons/bobby.jpg\",\n" +
                        "\n" +
                        "    \"title\": \"Slack API Documentation\",\n" +
                        "    \"title_link\": \"https://api.slack.com/\",\n" +
                        "\n" +
                        "    \"text\": \"Optional text that appears within the attachment\",\n" +
                        "\n" +
                        "    \"fields\": [\n" +
                        "        {\n" +
                        "            \"title\": \"Priority\",\n" +
                        "            \"value\": \"High\",\n" +
                        "            \"short\": false\n" +
                        "        }\n" +
                        "    ]" +
                        "]\n" +
                        "}";
            }



        } catch (Throwable ex) {
response = "ERROR:"+ex.getMessage();
        }

            resp.setCharacterEncoding("UTF-8");

         //   resp.setContentType("text/xml;charset=UTF-8");


                resp.setCharacterEncoding("UTF-8");
                byte[] respBytes = response.getBytes("UTF-8");

                resp.getOutputStream().write(respBytes);

        } catch (Exception exxx) {
            exxx.printStackTrace();
        }
    }

    public String getServletInfo() {
        return "One World Scan Retrieval Server v" + API.getVersionString();
    }


    public static void main(String[] args) {
        try {

        } catch (Exception ex) {
            log.debug("");
            ex.printStackTrace();
        }

    }


}
