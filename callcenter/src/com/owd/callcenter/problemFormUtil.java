package com.owd.callcenter;

import com.owd.callcenter.forms.problemFormForm;
import com.owd.core.OWDConstants;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.hibernate.Session;

import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Sep 18, 2006
 * Time: 9:34:42 AM
 */
public class problemFormUtil {
    private static String csa = "customerservice@owd.com";

    public static OwdClient getClient(Session sess, String campaign) throws Exception {
        System.out.println("gettingcampaign");
        String sql = "select client_fkey  from owd_client_callcenter where \n" +
                "Mlog_campaign_name = '" + campaign + "'";
        System.out.println(sql);
        ResultSet rs = HibernateSession.getResultSet(sess, sql);
        OwdClient client =  (OwdClient) sess.load(OwdClient.class, new Integer(55));
        if(rs.next())
        {
        client = (OwdClient) sess.load(OwdClient.class, new Integer(rs.getInt((1))));
        }
        rs.close();
        System.out.println("done campaign");
        return client;
    }

    public static void main(String[] args)    throws Exception
    {
     OwdClient cl =   getClient(HibernateSession.currentSession(),"DEB_SHOPS");
        System.out.println(cl.getClientId());
        System.out.println(getClientSupportBoxEmail(null,485));
    }
    public static String getClientSupportBoxEmail(Session sess, int clientID) throws Exception {
        System.out.println("getting email");
        String address = "customerservice@owd.com";

        try
        {
        String sql = "select address from vw_client_mailboxes where cid=convert(varchar,"+clientID+")";
            System.out.println(sql);
        ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(),sql);
            System.out.println("got rs");
        if(rs.next())
        {
            System.out.println("getting addr");
            address = rs.getString(1);
        }
            System.out.println("closing rs");
        rs.close();

        System.out.println("done email");
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }   finally
        {
            HibernateSession.closeSession();

        }
        return address;
    }



    public static String getEmail(problemFormForm pf) throws Exception {
        String email = new String();
        if ("yes".equals(pf.getCsa())) {
            email = csa;
        } else {
            email = getClient(HibernateSession.currentSession(), pf.getClient()).getAmEmail();
            if (email.equals("") || null == email) {
                email = csa;
            }

        }

        return email;
    }

    public static problemFormForm sendProblemForm(problemFormForm pf, HttpServletRequest request) throws Exception {

        Vector emails = new Vector();
        String email = getClientSupportBoxEmail(HibernateSession.currentSession(),getClient(HibernateSession.currentSession(), pf.getClient()).getClientId().intValue());
        System.out.println("email to " + email);
        String priority = pf.getHigh();
        emails.addElement(email);
        //emails.addElement("dnickels@owd.com");
        Vector bcc = new Vector();
        String csr = getCsrEmail(request, HibernateSession.currentSession());
        Vector cc = new Vector();
       // cc.addElement(csr);

        pf.setDto(email);
        pf.setDfrom(csr);
        StringBuffer body = new StringBuffer();
                body.append("CSR NAME: " + getCsrName(request, HibernateSession.currentSession()) + "\n" +
                "\n" +
                "CLIENT: " + getClient(HibernateSession.currentSession(), pf.getClient()).getCompanyName() + "\n" +

                "CAMPAIGN: " + pf.getClient() + "\n" +
                "\n" +
                "OWD ORDER NUMBER: " + pf.getOwdOrder()  +
                "\n" +
                "CLIENT REFERENCE #: " + pf.getClientReference()  +
                "\n" +
                "CUSTOMER NAME: " + pf.getName()  +
                "\n" +
                "CUSTOMER E-MAIL: " + pf.getEmail().trim() +
                "\n" +
                "CUSTOMER PASSWORD: " + pf.getPassword()  +
                "\n" +
                "PHONE: " + pf.getPhone().trim() + "\n" +
                "\n" +
                "CONTACT METHOD: " + pf.getContactMethod() + "\n" +
                "\n" +
                "TYPE: \n" + pf.getType() + "\n\n" +
                "ISSUE: \n" + pf.getIssue() + "\n\n" +
                "REQUEST:\n" +
                pf.getRequest() + "\n\n"+
                "NEEDS:\n" +
                pf.getNeeds() + "\n\n"
                );
         if(pf.getCallId().length()>0){
             body.append("\n \nCALL ID (link):");
             body.append(pf.getCallId()+" (http://service.owd.com/webapps/apps/downloadCall.do?id="+pf.getCallId()+")");
         }
postMailMessage("[CC]"+pf.getType()+":"+(pf.getHigh().equals("yes")?"!:":"")+ pf.getSubject(), body.toString(), emails, cc, bcc, csr, priority);
        pf.setDbody(body.toString());
        return pf;
    }

    public static String getCsrEmail(HttpServletRequest request, Session sess) throws Exception {
        try
        {
        System.out.println(request.getRemoteUser());
        String sql = "select email \n" +
                " from owd_users where  login='" + request.getRemoteUser() + "'";
        ResultSet rs = HibernateSession.getResultSet(sess, sql);
        rs.next();
        return rs.getString(1);
        }catch(Exception ex)
        {
          return "donotreply@owd.com";
        }

    }

    public static String getCsrName(HttpServletRequest request, Session sess) throws Exception {
       try
       {
           System.out.println(request.getRemoteUser());

        String sql = "select name \n" +
                " from owd_users where  login='" + request.getRemoteUser() + "'";
        ResultSet rs = HibernateSession.getResultSet(sess, sql);
        rs.next();
        return rs.getString(1);
         }catch(Exception ex)
        {
          return "unknown";
        }
    }

    public static void postMailMessage(String subject, String message, Vector toAddresses, Vector ccAddresses, Vector bccAddresses, String from, String priority) throws Exception {
                     try
        {
            Object[] ccs = null;
            if(ccAddresses != null)
            {
                ccs = (Object[]) ccAddresses.toArray();
            }
            Object[] bccs = null;
            if(bccAddresses != null)
            {
                bccs = (Object[]) bccAddresses.toArray();
            }
          sendMail(subject,message,toAddresses.toArray(),ccs,bccs,from,priority);
        }catch(Exception ex)
        {
                              System.out.println("FAILED POSTMAILMESSAGE!!!");
            ex.printStackTrace();
        }
    }

    public static void sendMail(String subject, String message, Object[] toAddresses, Object[] ccs, Object[] bccs, String from,String priority) throws Exception {
           Properties props = new Properties();
           props.put("mail.smtp.host", OWDConstants.SMTPServer);

           javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, null);

           System.out.println("sending msg through " + props.get("mail.smtp.host"));

           // create a message
           MimeMessage msg = new MimeMessage(session);
           msg.setFrom(new InternetAddress(from));
         if(priority.equals("yes")){
            msg.setHeader("X-Priority","High");
        }
           InternetAddress[] address = new InternetAddress[java.lang.reflect.Array.getLength(toAddresses)];
           for (int i = 0; i < java.lang.reflect.Array.getLength(toAddresses); i++) {
               try {
                   address[i] = new InternetAddress((String)toAddresses[i]);
               } catch (AddressException ae) {
                   address[i] = new InternetAddress(OWDConstants.DefaultToAddress);
               }
           }
           if (ccs != null) {
               InternetAddress[] ccaddress = new InternetAddress[java.lang.reflect.Array.getLength(ccs)];
               for (int i = 0; i < java.lang.reflect.Array.getLength(ccs); i++) {
                   try {
                       ccaddress[i] = new InternetAddress((String) ccs[i]);
                   } catch (AddressException ae) {
                       ccaddress[i] = new InternetAddress(OWDConstants.DefaultToAddress);
                   }
               }

               msg.setRecipients(javax.mail.Message.RecipientType.CC, ccaddress);
           }
           if (bccs != null) {
               InternetAddress[] bccaddress = new InternetAddress[java.lang.reflect.Array.getLength(bccs)];
               for (int i = 0; i < java.lang.reflect.Array.getLength(bccs); i++) {
                   try {
                       bccaddress[i] = new InternetAddress((String) bccs[i]);
                   } catch (AddressException ae) {
                       bccaddress[i] = new InternetAddress(OWDConstants.DefaultToAddress);
                   }
               }

               msg.setRecipients(javax.mail.Message.RecipientType.BCC, bccaddress);
           }
           msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
           msg.setSubject(subject);

           // add the Multipart to the message
                if(message.startsWith("<"))
           {
                      msg.setContent(message,"text/html");
           }   else
           {
                    msg.setText(message);
           }

           // set the Date: header
           msg.setSentDate(new Date());

           // send the message
           Transport.send(msg);


       }

}