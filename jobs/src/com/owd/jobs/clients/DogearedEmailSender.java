package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import ipworks.Htmlmailer;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: May 1, 2008
 * Time: 2:39:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class DogearedEmailSender {
private final static Logger log =  LogManager.getLogger();

    static Map<String, String> narvarCodeMap = new HashMap<String, String>();

    static {
        narvarCodeMap.put("UPS 2nd Day Air ","E2");
        narvarCodeMap.put("UPS Ground","003");
        narvarCodeMap.put("UPS Next Day Air","E1");
        narvarCodeMap.put("UPS Next Day Air Saver","013");
        narvarCodeMap.put("USPS First-Class Mail","FC");
        narvarCodeMap.put("USPS First-Class Mail International","LC");
        narvarCodeMap.put("USPS Priority Mail","PM");
        narvarCodeMap.put("USPS Priority Mail Express","EM");
        narvarCodeMap.put("USPS Priority Mail Express International","IE");

    }

    public static void main(String[] args) {
        try {
            if (OWDUtilities.isValidEmailAddress("Ashleyachandler@yahoo.com")) {
                log.debug("email valid");
            }
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, 7665544);
            Vector bcc = new Vector();

              sendHTMLEmailConfirmation(order,"owditadmin@owd.com","service@dogeared.com",null,bcc.toArray());

          //  sendHTMLEmailConfirmation(order, "taniajgb@gmail.com", "service@dogeared.com", null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }

    public static String getOrderRef(OwdOrder order) {
        if (order.getOrderRefnum().length() > 1) return order.getOrderRefnum();
        return order.getOrderNum();

    }

    private static String commaSeperate(Object[] a) {
        if (null != a) {
            StringBuffer sb = new StringBuffer();
            int i = 1;

            for (Object o : a) {
                sb.append(o.toString());
                if (i < a.length) {
                    sb.append(",");
                }

                i++;
            }
            log.debug(sb.toString() + " bccing that addresses");
            return sb.toString();
        }
        return "";
    }


    //  public static void setEmail(Htmlmailer email) {
    //      DogearedEmailSender.email = email;
    //  }

    //  static  Htmlmailer email = new Htmlmailer();
    private static String mailServer = "internal.owd.com";
    private static int mailPort = 25;

    public static String getMailServer() {
        return mailServer;
    }

    public static void setMailServer(String mailServer) {
        DogearedEmailSender.mailServer = mailServer;
    }

    public static int getMailPort() {
        return mailPort;
    }

    public static void setMailPort(int mailPort) {
        DogearedEmailSender.mailPort = mailPort;
    }

    public static void sendHTMLEmailConfirmation(OwdOrder order, String toAddress, String fromAddress, Object[] ccs, Object[] bccs) throws Exception {


        Htmlmailer email = new Htmlmailer();
        email.setMailServer(getMailServer());
        email.setMailPort(getMailPort());
        email.setRuntimeLicense(OWDUtilities.getIPWorksRuntimeLicense());

        StringBuffer body = new StringBuffer();
        body.append(getEmailStart());
        body.append(getOrderNumberPart(getOrderRef(order)));
        body.append(getItems(order));
        body.append(getShippingDetails(order));

        log.debug(order.getOrderRefnum());


        email.setFrom(fromAddress);


        email.setSendTo(toAddress);
        email.setCc(commaSeperate(ccs));
        email.setBCc(commaSeperate(bccs));

        email.setSubject("Dogeared Shipping Confirmation (Order Reference #: " + getOrderRef(order) + ")");

        System.out.println(body);
        email.setMessageHTML(body.toString());


        email.send();

        email.disconnect();


    }

    private static String getOrderNumberPart(String orderNum) {

        return "   <tr>\n" +
                "                      <td><strong style=\"color:#000; font-size:15px; line-height:36px;\">Order #" + orderNum + "</strong></td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td valign=\"top\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-top:#333 dotted 1px; border-bottom:#333 dotted 1px;\">\n" +
                "                          <tr>\n" +
                "                            <td align=\"left\" valign=\"top\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                <tr>\n" +
                "                                  <td width=\"40\" align=\"left\"><strong>QTY</strong></td>\n" +
                "                                  <td width=\"80\">&nbsp;</td>\n" +
                "                                  <td width=\"345\" align=\"left\" valign=\"top\"><strong>NAME</strong></td>\n" +
                "                                  <td width=\"120\" align=\"left\"><strong>CODE</strong></td>\n" +
                "                                </tr>\n" +
                "                              </table></td>\n" +
                "                          </tr>\n" +
                "                        </table><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                          <tr>\n" +
                "                            <td valign=\"top\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";



    }

    private static String getEmailStart() throws Exception {

        InputStream stream = null;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = new DogearedEmailSender().getClass().getClassLoader();
        }


        String emailStart = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "<title>Your Order Has Shipped!</title>\n" +
                "<style type=\"text/css\">\n" +
                "body {\n" +
                "\tfont-family: 'Helvetica Neue', Helvetica, Arial, 'Lucida Grande', sans-serif; \n" +
                "\tfont-weight: 300;\n" +
                "\tfont-size: 14px;\n" +
                "\tline-height: 26px;\n" +
                "\tcolor:#000000\n" +
                "}\n" +
                "a {\n" +
                "\tfont-family: 'Helvetica Neue', Helvetica, Arial, 'Lucida Grande', sans-serif; \n" +
                "\tfont-weight: 300;\n" +
                "\tfont-size: 12px;\n" +
                "\tcolor:#333333;\n" +
                "\ttext-decoration:none;\n" +
                "}\n" +
                ".snap-tag{\n" +
                "    width:715px;\n" +
                "    height:247px;\n" +
                "}\n" +
                "</style><style> @media only screen and (max-width : 480px) {.mobileCenter { text-align: center; margin: auto; float: none; } .mobileHide { display: none; } .snap-tag{width:100%;height:auto;}}</style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<table width=\"715\" border=\"0\" align=\"center\" cellpadding=\"2\" cellspacing=\"0\">\n" +
                "  <tr>\n" +
                "    <td valign=\"top\"><table width=\"720\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\">\n" +
                "<table border='0' cellpadding='0' cellspacing='0' align='center' valign='top' style=\"border-bottom: 1px #000 solid; margin-top: 10px; width: 100%; max-width: 720px;\">\n" +
                "    <tr>\n" +
                "        <td class=\"mobileCenter\" style=\"text-align: center; margin: 0 auto\">\n" +
                "            <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; margin: 15px auto 30px;\">\n" +
                "                <tbody>\n" +
                "                    <tr>\n" +
                "                        <td align=\"center\">\n" +
                "                            <a href=\"http://www.dogeared.com/\" title=\"Dogeared\" target=\"_blank\">\n" +
                "                                <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/logo2.png\" width=\"212\" height=\"107\" alt=\"Dogeared\" border=\"0\" />\n" +
                "                            </a>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "            <table class=\"mobileCenter mobileHide\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; margin-bottom: -10px;\" >\n" +
                "                <tbody>\n" +
                "                    <tr style=\"text-align: center;\">\n" +
                "                        <td valign=\"top\" nowrap=\"nowrap\" style=\"line-height:16px; text-align: center;\" height=\"50\">\n" +
                "                            <a href=\"http://www.dogeared.com/necklaces\" title=\"NECKLACES\" target=\"_blank\" style=\"padding:15px 25px;\">\n" +
                "                                <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/navi-necklaces.jpg\" alt=\"Necklaces\" width=\"94\" height=\"14\" border=\"0\" />\n" +
                "                            </a>\n" +
                "                        </td>\n" +
                "                        <td valign=\"top\" nowrap=\"nowrap\">\n" +
                "                            <a href=\"http://www.dogeared.com/bracelets\" title=\"BRACELETS\" target=\"_blank\" style=\"padding:15px 25px;\">\n" +
                "                                <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/navi-bracelets.jpg\" alt=\"Bracelets\" width=\"80\" height=\"14\" border=\"0\" />\n" +
                "                            </a>\n" +
                "                        </td>\n" +
                "                        <td valign=\"top\" nowrap=\"nowrap\">\n" +
                "                            <a href=\"http://www.dogeared.com/rings\" title=\"RINGS\" target=\"_blank\" style=\"padding:15px 25px;\">\n" +
                "                                <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/navi-rings.jpg\" alt=\"Rings\" width=\"50\" height=\"14\" border=\"0\" />\n" +
                "                            </a>\n" +
                "                        </td>\n" +
                "                        <td valign=\"top\" nowrap=\"nowrap\">\n" +
                "                            <a href=\"http://www.dogeared.com/earrings\" title=\"EARRINGS\" target=\"_blank\" style=\"padding:15px 25px;\">\n" +
                "                                <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/navi-earrings.jpg\" alt=\"Earrings\" width=\"78\" height=\"14\" border=\"0\" />\n" +
                "                            </a>\n" +
                "                        </td>\n" +
                "                        <td valign=\"top\" nowrap=\"nowrap\">\n" +
                "                            <a href=\"http://www.dogeared.com/sale\" title=\"SALE\" target=\"_blank\" style=\"padding:15px 25px;\">\n" +
                "                                <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/navi-sale.jpg\" alt=\"Sale\" width=\"35\" height=\"14\" border=\"0\" />\n" +
                "                            </a>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table></td>\n" +
                "              </tr>\n" +
                "              <tr>\n" +
                "                <td align=\"center\" valign=\"top\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; color:#262626;\"><img src=\"http://www.dogeared.com/on/demandware.static/Sites-Dogeared-Site/Sites-Dogeared-Library/default/emails/general/shipping/shipped_happening.gif\" alt=\"These Items Have Shipped\" width=\"623\" height=\"134\" border=\"0\" style=\"margin-top:70px; margin-bottom:40px; \"/></td>\n" +
                "              </tr>\n" +
                "              <tr>\n" +
                "                <td valign=\"top\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";

        return emailStart;
    }


    private static String getItems(OwdOrder order) {
        StringBuffer sb = new StringBuffer();
        for (OwdLineItem item : order.getLineitems()) {


            sb.append("<tr>\n" +
                    "                                  <td width=\"40\" align=\"right\">&nbsp;&nbsp;&nbsp;" + item.getQuantityActual() + "</td>\n" +
                    "                                  <td width=\"80\">&nbsp;</td>\n" +
                    "                                  <td width=\"345\" align=\"left\" valign=\"top\" style=\"line-height:30px; \">" + item.getDescription() + "</td>\n" +
                    "                                  <td width=\"120\" align=\"left\">" + item.getInventoryNum() + "</td>\n" +
                    "                                </tr>");


        }

        return sb.toString();
    }


    private static String getShippingDetails(OwdOrder order){
        StringBuffer sb = new StringBuffer();

        sb.append("</table></td>\n" +
                "                          </tr>\n" +
                "                    <tr>\n" +
                "                      <td>&nbsp;</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td valign=\"top\"><strong style=\"color:#000; font-size:15px;\">Shipment Details</strong></td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td valign=\"top\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100\" align=\"right\" valign=\"top\">Ship Date</td>\n" +
                "                            <td width=\"20\" valign=\"top\">&nbsp;</td>\n" +
                "                            <td width=\"465\" valign=\"top\">"+order.getShippedDate()+"</td>\n" +
                "                          </tr>\n" +
                "                          <tr>\n" +
                "                            <td align=\"right\" valign=\"top\">Method</td>\n" +
                "                            <td valign=\"top\">&nbsp;</td>\n" +
                "                            <td valign=\"top\">"+order.getShipinfo().getCarrService()+"</td>\n" +
                "                          </tr>\n" +
                "                          <tr>\n" +
                "                            <td align=\"right\" valign=\"top\">Tracking</td>\n" +
                "                            <td valign=\"top\">&nbsp;</td>\n" +
                "                    <td valign=\"top\"><u>");

        String carrier = "fedex";
        if(order.getShipinfo().getCarrService().contains("USPS")){
            carrier = "usps";
        }
        if(order.getShipinfo().getCarrService().contains("UPS")){
            carrier = "ups";
        }

        String shipmethod = order.getShipinfo().getCarrService();

        String extraInfo = "" ;

                   if(narvarCodeMap.keySet().contains(shipmethod)) {
                               extraInfo = "&service="+narvarCodeMap.get(shipmethod)+"&ozip=57601&dzip=" + order.getShipinfo().getShipZip();

                   }
        sb.append("<a href=\"http://dogeared.narvar.com/tracking/dogeared/"+carrier+"?tracking_numbers="+order.getTrackingNums()+extraInfo+"\" target=\"_blank\">"+order.getTrackingNums()+"</a>");

        sb.append("</u> (click link to track your package)</td>\n" +
                "                          </tr>\n" +
                "                          <tr>\n" +
                "                            <td align=\"right\" valign=\"top\">Package</td>\n" +
                "                            <td valign=\"top\">&nbsp;</td>\n" +
                "                            <td valign=\"top\">"+order.getPackagesShipped()+"</td>\n" +
                "                          </tr>\n" +
                "                        </table></td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\"><br />\n" +
                "                        <br />\n" +
                "                             <img src=\"http://dogearedimage.s3.amazonaws.com/signature.gif\" alt=\"XO, Dogeared\" width=\"177\" height=\"41\" border=\"0\" /><br />\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\"><br />\n" +
                "                             <a class=\"mobileHide\" href=\"http://www.dogeared.com/must-have-top-trending\" title=\"Top Trending\" target=\"_blank\"><img src=\"http://www.dogeared.com/on/demandware.static/Sites-Dogeared-Site/Sites-Dogeared-Library/default/emails/general/shipping/current_happenings.jpg\" alt=\"Top Trending\" border=\"0\" width=\"357px\" height=\"165px\"/></a>\n" +
                "                             <a class=\"mobileHide\" href=\"http://www.dogeared.com/must-have-bestseller\" title=\"Best Sellers\" target=\"_blank\"><img src=\"http://www.dogeared.com/on/demandware.static/Sites-Dogeared-Site/Sites-Dogeared-Library/default/emails/general/shipping/most_loved.jpg\" alt=\"Most Loved, Best Sellers\" border=\"0\" width=\"358px\" height=\"165px\"/></a>\n" +
                "                        <br /></td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\"><br />\n" +
                "                             <a href=\"http://www.dogeared.com/gallery.html\" title=\"Snap, Tag, Share\" target=\"_blank\"><img class=\"snap-tag\" src=\"http://www.dogeared.com/on/demandware.static/Sites-Dogeared-Site/Sites-Dogeared-Library/default/emails/general/shipping/snap_tag_share.jpg\" alt=\"Snap, Tag, Share\" width=\"715\" height=\"247\" border=\"0\" /></a><br />\n" +
                "                        <br /></td>\n" +
                "                    </tr>\n" +
                "                     <tr>\n" +
                "          <td align=\"center\" valign=\"top\"><style>\n" +
                "    .left { float: left; position: relative; }\n" +
                "    .right { float: right; position: relative; }\n" +
                "    .mobileShow { display: none; }\n" +
                "\n" +
                "    @media only screen and (max-width : 480px) {\n" +
                "        .mobileCenter { text-align: center; margin: auto; float: none; }\n" +
                "        .mobileShow { display: block; }\n" +
                "        .mobileShow tr td img { width: 100%; }\n" +
                "        .left, .right { float: none; position: inherit; margin-top: 10px; width: 100%; }\n" +
                "        .left { left: 0; }\n" +
                "        .right { right: 0; }\n" +
                "        .mobileShow, .mobileShow tbody, .mobileShow tr, .mobileShow td { width: auto !important; max-height: inherit !important; overflow: visible !important; display: block !important; float: none !important; }\n" +
                "            .mobileShow img { width: 100% !important; height: auto !important; max-height: inherit !important; overflow: visible !important; display: block !important; float: none !important; }\n" +
                "    }\n" +
                "</style>\n" +
                "<table class=\"mobileCenter\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" border=\"0\" style=\"width: 100%; margin-top: 10px; max-width: 640px;\">\n" +
                "    <tr>\n" +
                "        <td>\n" +
                "            <table class=\"mobileCenter\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" border=\"0\" style=\"width: 100%;\">\n" +
                "                <tr>\n" +
                "                    <td style=\"border-top: 1px solid #acacac; border-bottom: 1px solid #acacac;\" align=\"center\">\n" +
                "                        <table cellspacing=\"0\" cellpadding=\"0\" align=\"center\" border=\"0\" style=\"margin: 0 auto; padding: 0; \">\n" +
                "                            <tr>\n" +
                "                                <td align=\"center\" style=\"padding: 16px 0;\">\n" +
                "                                    <a href=\"http://www.dogeared.com/about-us.html\" target=\"_blank\" title=\"About Us\" style=\"text-decoration: none;\">\n" +
                "                                        <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/handcrafted.png\" height=\"54\" width=\"141\" alt=\"About Us\" border=\"0\" />\n" +
                "                                    </a>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "            <!-- MOBILE Nav w/ Imgs BEGIN -->\n" +
                "            <table class=\"mobileShow\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"margin-top: -1px; width:0; max-height:0; overflow:hidden; display:none; float:left;\">\n" +
                "                <tr style=\"width:0; max-height:0; overflow:hidden; display:none; float:left;\">\n" +
                "                    <td align=\"center\" style=\"width:0; max-height:0; overflow:hidden; display:none; float:left;\">\n" +
                "                        <div style=\"padding:0 0 2px;\">\n" +
                "                            <a href=\"http://www.dogeared.com/necklaces\" title=\"Necklaces\">\n" +
                "                                <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/mnavi-necklaces.jpg\" alt=\"Necklaces\" style=\"width: 100%; max-width: 100%;\" />\n" +
                "                            </a>\n" +
                "                        </div>\n" +
                "                        <div style=\"padding: 2px 0;\">\n" +
                "                            <a href=\"http://www.dogeared.com/bracelets\" title=\"Bracelets\">\n" +
                "                                <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/mnavi-bracelets.jpg\" alt=\"Bracelets\" style=\"width: 100%\" />\n" +
                "                            </a>\n" +
                "                        </div>\n" +
                "                        <div style=\"padding: 2px 0;\">\n" +
                "                            <a href=\"http://www.dogeared.com/rings\" title=\"Rings\">\n" +
                "                                <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/mnavi-rings.jpg\" alt=\"Rings\" style=\"width: 100%\" />\n" +
                "                            </a>\n" +
                "                        </div>\n" +
                "                        <div style=\"padding: 2px 0;\">\n" +
                "                            <a href=\"http://www.dogeared.com/earrings\" title=\"Earrings\">\n" +
                "                                <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/mnavi-earrings.jpg\" alt=\"Earrings\" style=\"width: 100%\" />\n" +
                "                            </a>\n" +
                "                        </div>\n" +
                "                        <div style=\"padding: 2px 0 0;\">\n" +
                "                            <a href=\"http://www.dogeared.com/sale\" title=\"Sale\">\n" +
                "                                <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/mnavi-sale.jpg\" alt=\"Sale\" style=\"width: 100%\" />\n" +
                "                            </a>\n" +
                "                        </div>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "            <!-- MOBILE Nav w/ Imgs END -->\n" +
                "            <table class=\"mobileCenter\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" border=\"0\" style=\"margin: 0 auto; padding: 0; width: 100%; \">\n" +
                "                <tr>\n" +
                "                    <td class=\"contentPadding\" valign=\"bottom\" style=\"padding: 20px 0 0; width: 100%; margin: 0 auto;\">\n" +
                "                        <table class=\"mobileCenter left\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse; height: 30px; \">\n" +
                "                            <tr>\n" +
                "                                <td>\n" +
                "                                    <table class=\"mobileCenter\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse; \">\n" +
                "                                        <tr>\n" +
                "                                            <td class=\"mobileCenter left\" style=\"padding-top: 5px;\">\n" +
                "                                                <a href=\"http://www.dogeared.com/about-us.html\" target=\"_blank\" title=\"About Us\" style=\"text-decoration: none;\">\n" +
                "                                                    <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/bnavi-about.png\" alt=\"About Us\" height=\"13\" width=\"46\" border=\"0\" />\n" +
                "                                                </a>\n" +
                "                                                <a href=\"http://www.dogeared.com/customerservice?aid=customer-service\" title=\"Customer Support\" target=\"_blank\" style=\"text-decoration: none;\">\n" +
                "                                                    <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/bnavi-support.png\" alt=\"Customer Support\" height=\"13\" width=\"91\" border=\"0\" />\n" +
                "                                                </a>\n" +
                "                                                <a href=\"http://www.dogeared.com/giftcertificates?aid=home\" title=\"Gift Cards\" target=\"_blank\" style=\"text-decoration: none; \">\n" +
                "                                                    <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/bnavi-gc.png\" alt=\"Gift Cards\" height=\"13\" width=\"54\" border=\"0\" />\n" +
                "                                                </a>\n" +
                "                                                <a href=\"http://www.dogeared.com/on/demandware.store/Sites-Dogeared-Site/default/CustomerService-ContactUs\" target=\"_blank\" title=\"Contact Us\" style=\"text-decoration: none; \">\n" +
                "                                                    <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/bnavi-contact.png\" alt=\"Contact Us\" height=\"13\" width=\"56\" border=\"0\" />\n" +
                "                                                </a>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                        <table class=\"mobileCenter right\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse: collapse; width:53%; \">\n" +
                "                            <tr>\n" +
                "                                <td>\n" +
                "                                    <table class=\"mobileCenter\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse; width: 100%; height: 30px;\">\n" +
                "                                        <tr>\n" +
                "                                            <td class=\"mobileCenter\">\n" +
                "                                                <table class=\"mobileCenter left\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;\">\n" +
                "                                                    <tr>\n" +
                "                                                        <td class=\"mobileCenter\" height=\"20\" style=\"padding-top: 6px;\">\n" +
                "                                                            <img class=\"mobileCenter\" src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/social-message.jpg\" height=\"13\" width=\"132\" alt=\"Stay Connected\" border=\"0\" />\n" +
                "                                                        </td>\n" +
                "                                                    </tr>\n" +
                "                                                </table>\n" +
                "                                                <table class=\"mobileCenter right\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse; height: 30px;\">\n" +
                "                                                    <tr>\n" +
                "                                                        <td style=\"padding-right:8px;\">\n" +
                "                                                            <a href=\"http://www.instagram.com/dogearedjewelry\" title=\"Instagram\" target=\"_blank\">\n" +
                "                                                                <img class=\"mobileCenter\" src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/social-instagram.jpg\" width=\"19\" height=\"21\" border=\"0\" />\n" +
                "                                                            </a>\n" +
                "                                                        </td>\n" +
                "                                                        <td style=\"padding-right:8px;\">\n" +
                "                                                            <a href=\"https://www.facebook.com/dogearedjewelry\" title=\"Facebook\" target=\"_blank\">\n" +
                "                                                                <img class=\"mobileCenter\" src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/social-facebook.jpg\" width=\"12\" height=\"21\" border=\"0\" />\n" +
                "                                                            </a>\n" +
                "                                                        </td>\n" +
                "                                                        <td style=\"padding-right:8px;\">\n" +
                "                                                            <a href=\"https://www.twitter.com/dogearedjewelry\" title=\"Twitter\" target=\"_blank\">\n" +
                "                                                                <img class=\"mobileCenter\" src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/social-twitter.jpg\" width=\"20\" height=\"21\" border=\"0\" />\n" +
                "                                                            </a>\n" +
                "                                                        </td>\n" +
                "                                                        <td style=\"padding-right:8px;\">\n" +
                "                                                            <a href=\"http://www.pinterest.com/dogearedjewelry/\" title=\"Pinterest\" target=\"_blank\">\n" +
                "                                                                <img class=\"mobileCenter\" src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/social-pinterest.jpg\" width=\"18\" height=\"21\" border=\"0\" />\n" +
                "                                                            </a>\n" +
                "                                                        </td>\n" +
                "                                                        <td style=\"padding-right:8px;\">\n" +
                "                                                            <a href=\"https://plus.google.com/111542429932345753333/posts\" title=\"Google+\" target=\"_blank\">\n" +
                "                                                                <img class=\"mobileCenter\" src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/social-google.jpg\" width=\"19\" height=\"21\" border=\"0\" />\n" +
                "                                                            </a>\n" +
                "                                                        </td>\n" +
                "                                                        <td style=\"padding-right:5px;\">\n" +
                "                                                            <img class=\"mobileCenter\" src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/social-div.jpg\" width=\"1\" height=\"27\" border=\"0\" />\n" +
                "                                                        </td>\n" +
                "                                                        <td style=\"padding-top: 3px;\">\n" +
                "                                                            <a href=\"http://blog.dogeared.com/\" title=\"Blog\" target=\"_blank\">\n" +
                "                                                                <img class=\"mobileCenter\" src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/social-blog.jpg\" width=\"61\" height=\"18\" border=\"0\" />\n" +
                "                                                            </a>\n" +
                "                                                        </td>\n" +
                "                                                    </tr>\n" +
                "                                                </table>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table class=\"mobileCenter\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse; width: 100%;\">\n" +
                "                                        <tr>\n" +
                "                                            <td class=\"mobileCenter right\" style=\"padding-top:5px;\" align=\"right\">\n" +
                "                                                <a href=\"http://www.dogeared.com/gallery.html\" target=\"_blank\" style=\"text-decoration: none;\" title=\"Good Things Happen\">\n" +
                "                                                    <img src=\"http://demandware.edgesuite.net/aaok_prd/on/demandware.static/Sites-Dogeared-Site/-/default/v1414078394606/images/mail/goodthingshappen.jpg\" border=\"0\" height=\"14\" width=\"111\" alt=\"Good Things Happen\" />\n" +
                "                                                </a>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table></td>\n" +
                "        </tr>\n" +
                "                  </table></td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "            <br />\n" +
                "            <br /></td>\n" +
                "        </tr>\n" +
                "      </table></td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>");

        return sb.toString();
    }
}
