<%@ page import="com.owd.core.OWDUtilities"%>
<%@ page import="com.owd.core.business.Address" %>
<%@ page import="com.owd.core.business.Contact" %>
<%@ page import="com.owd.core.business.order.Order" %>
<%@ page import="com.owd.core.business.order.OrderStatus" %>
<%@ page import="com.owd.core.managers.ManifestingManager" %>
<%@ page import="com.owd.core.xml.OrderXMLDoc" %>
<%@ page import="com.owd.hibernate.HibernateSession" %>
<%@ page import="com.owd.hibernate.generated.OwdOrder" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<?xml version="1.0"?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
    <title>OWD UPS Return Labels By Email</title>
    <style type="text/css">
        body {
            font-family: Helvetica, Arial, sans-serif;

        }

    </style>
    <script type="text/javascript">
        function validatethisemail() {
            var test = isValidEMail(document.getElementById('ct_email').value);
            if (!test) {
                alert('Invalid email address - please correct and submit again');
                return false;
            }
            else
                return true;
        }

        function isValidEMail(email) {
            var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

            if (filter.test(email)) {
                return true;
            } else {
                return false;
            }
        }
    </script>
</head>
<body>
<H1>UPS Return Labels By Email</H1>
<HR>
<%

    String cid=request.getParameter("c")==null?"":request.getParameter("c");
    String cauth = request.getParameter("i")==null?"":request.getParameter("i");
    List<OwdOrder> orderMatches = new ArrayList<OwdOrder>();
    java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");
    String decryptCheck ="";
    String tracking = null;
    try{
     decryptCheck = OWDUtilities.decryptData(cauth);
    }  catch(Exception ex){}


    if(cid.equals(decryptCheck) && decryptCheck.length()>0)
    {
       String orderReference = request.getParameter("o")==null?"":request.getParameter("o").trim();

       if(null!=request.getParameter("labelrequest"))
       {
           String email = request.getParameter("ct_email");
           request.setAttribute("ct_email",email);
           try{


           orderMatches = (List<OwdOrder>) HibernateSession.currentSession().createQuery("from OwdOrder where orderNum = ? or orderRefnum=? " +
                            "and client.clientId=? and orderStatus='Shipped' order by orderNum asc").setString(0, orderReference).setString(1,orderReference).setInteger(2,Integer.parseInt(cid)).list();
           OrderStatus status = new OrderStatus(orderMatches.get(0).getOrderId()+"",
                                  cid);

                    Address pickupAddress = status.shipping.shipAddress;
        if(pickupAddress.isPOAPO())
        {
            pickupAddress.address_one="123 main St";
        }
               
                Contact pickupContact = status.shipping.shipContact;


                pickupContact.email = email;

               if(!(OWDUtilities.isValidEmailAddress(pickupContact.email)) )
               {
                   throw new Exception("Email address '"+pickupContact.email+"' is not valid. Please correct and retry.");
               }  

        System.out.println("creating new order");
        Order order = status.createCallTagEmailLabelOrder(pickupAddress, pickupContact, "UPS Ground", 1.35f);
        System.out.println("saving order");
                String reference = order.saveNewOrder(OrderXMLDoc.kPaymentStatusClientManaged, false);
        System.out.println("getting orderstatus");

                    OrderStatus calltag = new OrderStatus(order.orderID);
        System.out.println("calltag order status: "+calltag.getStatusString());

        System.out.println("shipping call tag order");

       status = ManifestingManager.shipCallTagOrder(calltag, 1.25f, status.orderReference,status.getLocation()) ;

         tracking = ((com.owd.core.business.order.Package)(status.packages.get(0))).tracking_no;
              %>

<B>Success!<P>A UPS return label will be sent to <%= email %> within 2-3 hours for UPS tracking number <%= tracking %>.<P>Please review the notes below with the customer so they know what to expect.</B><BR>
  <hr>
<FONT size=-1>UPS return shipments are shipped directly to the main warehouse via Ground service. Return shipments may only be sent from USA locations.<P>
Within 2-3 hours, the customer should receive an email from pkginfo@ups.com at the indicated email address.<P>The sender name on the email will be "ONE WORLD DIRECT OF CA".<P>The email will contain links to download a printable label image and dropoff instructions.
</P>
</FONT>

<HR>
<A HREF="getlabel.jsp?i=<%= cauth%>&c=<%= cid%>">Send a new label</A>
<%

           }catch(Exception ex)
           {
               request.setAttribute("formerror",ex.getMessage());
           }
       }   else
       {


       if(orderReference.length()>0)
       {
          orderMatches = (List<OwdOrder>) HibernateSession.currentSession().createQuery("from OwdOrder where orderNum = ? or orderRefnum=? " +
                   "and client.clientId=? and orderStatus='Shipped' order by orderNum asc").setString(0, orderReference).setString(1,orderReference).setInteger(2,Integer.parseInt(cid)).list();
       }
       }
       if(orderMatches.size()>0 && tracking==null)
       {
            OrderStatus status = new OrderStatus(orderMatches.get(0).getOrderId()+"",
                        cid);
           if(!(OWDUtilities.isValidEmailAddress(status.shipping.shipContact.email)) )
           {
               if(OWDUtilities.isValidEmailAddress(status.billContact.email))
               {
                   status.shipping.shipContact.email = status.billContact.email;
               }   else
               {
                   //don't throw because user may provide email
               }
           }
                request.setAttribute("ct_email",status.shipping.shipContact.email)  ;

                %>

<B>Email UPS Ground prepaid shipping return label for order <%= status.orderReference %> (<%= status.OWDorderReference %>/<%= status.billContact.getName() %>)</B><BR>
<HR><FONT COLOR=RED><B><%= request.getAttribute("formerror")==null?"":request.getAttribute("formerror") %></B></FONT>
<FORM METHOD=POST ACTION="getlabel.jsp" onsubmit="return validatethisemail();">
Verify/correct email address where label image should be sent:
<P></P>Email:&nbsp;<INPUT SIZE=50 TYPE=TEXT id="ct_email" NAME=ct_email VALUE="<%= request.getAttribute("ct_email") %>">
<INPUT TYPE=SUBMIT NAME=labelrequest VALUE="Send Return Label">
<INPUT TYPE="HIDDEN" NAME="o" VALUE="<%= orderReference %>" />
<INPUT TYPE="HIDDEN" NAME="c" VALUE="<%= cid %>" />
<INPUT TYPE="HIDDEN" NAME="i" VALUE="<%= cauth %>" />
    <hr>
<FONT size=-1>UPS return shipments are shipped directly to the main warehouse via Ground service. Return shipments may only be sent from USA locations.<P>
Within 2-3 hours, the customer should receive an email from pkginfo@ups.com at the indicated email address.<P>The sender name on the email will be "ONE WORLD DIRECT OF CA".<P>The email will contain links to download a printable label image and dropoff instructions.
</P>
</FONT>
</FORM>

<%

       }    else if(tracking==null)
       {
           if(orderReference.length()>0)
           {request.setAttribute("formerror","Order reference "+orderReference+" could not be found. Please correct and resubmit.<P>");}
                        %>
<FONT COLOR=RED><B><%= request.getAttribute("formerror")==null?"":request.getAttribute("formerror") %></B></FONT>
<FORM METHOD=POST ACTION="getlabel.jsp">
<B>Enter the original order reference for the order being returned:<p>
<INPUT SIZE=50 TYPE=TEXT NAME=o VALUE=""></B><INPUT TYPE=SUBMIT NAME=submit VALUE="Lookup Order Record">
<INPUT TYPE="HIDDEN" NAME="c" VALUE="<%= cid %>" />
<INPUT TYPE="HIDDEN" NAME="i" VALUE="<%= cauth %>" />

<P><FONT size=-1>You must provide the full, original order reference for the customer's order to generate a return label. If a matching order is found, the next page will allow you to edit the information for the label.

</FORM>

<%

       }



     }else
    {
     %>
You are not authorized to view this page.
<%
    }
%>
</body>
</html>