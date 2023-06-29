package com.owd.jobs.jobobjects.corecommerce

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: Jun 2, 2010
 * Time: 1:32:09 PM
 * To change this template use File | Settings | File Templates.
 */
class CoreCommerceOrderService
{
    private final static Logger log =  LogManager.getLogger();


    def static Node getOrderList(String serviceUrl, String login, String password, String store, String key, int daysBack, int skip, int limit) throws Exception
    {

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def request = {
            mkp.xmlDeclaration()
            Request(version: "1.0") {
                Authentication {
                    Username(login)
                    Password(password)
                    StoreName(store)
                    XmlKey(key)
                }
                Action("ACTION_TYPE_ORDER_LIST")
                Limit(""+limit)
                Skip(""+skip)
                SearchCriteria(type: "Exact")
                        {
                            DateRanges {
                                DateRange {
                                    From((long) (new Date().minus(daysBack).time * 0.001))
                                    To((long) (new Date().plus(2).time * 0.001))
                                }
                            }
                            //   Statuses {
                            //     Status('Approved')
                            //}
                            /*
                            <DateRanges>
                   <DateRange>
            <Day>27</Day>
           <Month>05</Month>
           <Year>2010</Year>
                   </DateRange>
               </DateRanges>
              <Statuses>
                   <Status>APPROVED</Status>
                       </Statuses>
                       Statuses {
                                Status('APPROVED')
                            }*/

                        }
            }
        }


        println "sending"

        URL testUrl = new URL(serviceUrl);

        URLConnection testConnection = (URLConnection) testUrl.openConnection();
        testConnection.setReadTimeout(120000);
        testConnection.setConnectTimeout(120000);
        testConnection.setRequestProperty("Content-Type", "text/xml");
        testConnection.setRequestMethod("POST");
        testConnection.setDoOutput(true);

    /*    PrintWriter  p_out2 = new PrintWriter(System.out);
        p_out2 << builder.bind(request)
        p_out2.close();*/
        PrintWriter p_out = new PrintWriter(testConnection.getOutputStream());
        p_out << builder.bind(request)
        p_out.close();

        def BufferedReader inr = new BufferedReader(new InputStreamReader(testConnection.getInputStream()));

        log.debug("Reading stream");
        String line = "";
        String resultStr = "";

        int i = 0;
        int c = 0;
        inr.mark(10); //shouldn't have to check more than 10 chars before hitting the XML start
        while ((c = inr.read()) != -1)
        {
            log.debug("checking");
            if (c == '<')
            {
                break;
            } else
            {
                inr.mark(10);  //reset mark to next char
            }
        }
        inr.reset();  //rewind to last mark


        return new XmlParser().parse(inr)

    }

    public static void main(String[] args) throws Exception
    {
        // mrhoades: change per case 1039640
        // def orderList = CoreCommerceOrderService.getOrderList("https://store.brazillivecoral.com/admin/_callback.php", "oneworld", "1979159a", "algaecalinc893", "1979159a", 4,0,1);
        def orderList = CoreCommerceOrderService.getOrderList("https://store.brazillivecoral.com/admin/_callback.php", "oneworld", "1979159a", "brazilliveco154", "1979159a", 4,0,1);

        println orderList    }
}
