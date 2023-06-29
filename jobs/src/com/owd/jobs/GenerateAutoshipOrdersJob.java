package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.MailAddressValidator;
import com.owd.core.Mailer;
import com.owd.core.business.autoship.AutoShipManager;
import com.owd.core.business.client.ClientManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 30, 2004
 * Time: 4:14:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenerateAutoshipOrdersJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public GenerateAutoshipOrdersJob() {
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Interface.
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    /**
     * <p/>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a <code>{@link org.quartz.Trigger}</code>
     * fires that is associated with the <code>Job</code>.
     * </p>
     *
     * @throws JobExecutionException if there is an exception while executing the job.
     */

    public void internalExecute() {
        try {

            List clientList = HibernateSession.currentSession().createQuery("from OwdClient as clients where is_active=1").list();
            ////log.debug("Found " + clientList.size() + " clients to check for autoship orders to create...");
            Iterator clients = clientList.iterator();
            while (clients.hasNext()) {

                try {
                    OwdClient client = (OwdClient) clients.next();
                     if(true)//(client.getClientId()==402)
                              {
                     log.debug("attempting ship suborders");
                    //TODO check on CC retries for lifespan
                    List results = AutoShipManager.shipPendingSubscriptionOrders(client.getClientId() + "");
                    
                        log.debug("got shipsuborders results:"+results+"::"+results.toString());
                    if (results.size() > 0) {
                        StringBuffer sb = new StringBuffer();
                        Iterator it = results.iterator();
                        while (it.hasNext()) {
                            ////log.debug("getting message!");
                            String message = (String) it.next();
                          log.debug("got message "+message);
                            sb.append(message + "\n");

                        }

                         ////log.debug("getting amemail");
                        String amEmail = "owditadmin@owd.com";
                        if (client != null) {
                            if (client.getAmEmail().length() > 5) {
                                amEmail = client.getAmEmail();

                            }
                        }
                        // //log.debug("got amemail:"+amEmail);
                        String clientAddresses = client.getAutoShipEmail();
                        // //log.debug("got clientAddresses:"+clientAddresses);
                        ArrayList ccs = new ArrayList();


                        if (clientAddresses.indexOf("@") > 0) {

                            StringTokenizer tokens = new StringTokenizer(clientAddresses, ",");

                            while (tokens.hasMoreTokens()) {

                                String addr = tokens.nextToken();

                                try {
                                    MailAddressValidator.validate(addr);
                                } catch (Exception ea) {
                                    addr = null;
                                }

                                if (addr != null)
                                {
                                    ccs.add(addr);
                                }
                            }

                        }


                                  try
                                  {
                                                        Mailer.sendMail("OWD Subscription Order Creation Report for " + client.getCompanyName(),
                                                        "\r\nNew Subscription Order creation results:\r\n\r\n" + sb.toString(), ClientManager.getClientSupportBoxEmail(HibernateSession.currentSession(),client.getClientId()), ccs.size()>0?ccs.toArray():null, null, "autoships-do-not-reply@owd.com");
                                  }catch(Exception ex)
                                  {
                                      
                                  }


                        Mailer.sendMail("OWD Subscription Order Creation Report for " + client.getCompanyName(),
                                "\r\nNew Subscription Order creation results:\r\n\r\n" + sb.toString(), amEmail, ccs.size()>0?ccs.toArray():null, null, "autoships-do-not-reply@owd.com");


                        Mailer.sendMail("OWD Subscription Order Creation Report for " + client.getCompanyName(),
                                                       "\r\nNew Subscription Order creation results:\r\n\r\n" + sb.toString(), "owditadmin@owd.com",  "autoships-do-not-reply@owd.com");


                    }

                              }
                } catch (Exception ex) {
                       ex.printStackTrace();
                } finally {
                    HibernateSession.closeSession();
                }
            }
        } catch (Exception ex) {
                ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }

    public static  void main(String[] args) {
       run();



    }


}
