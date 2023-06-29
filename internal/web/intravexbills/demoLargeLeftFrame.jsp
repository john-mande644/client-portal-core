<%@ page import="com.owd.hibernate.HibernateSession,
                 java.sql.ResultSet,
                 java.util.TreeMap,
                 java.util.Map,
                 java.text.SimpleDateFormat,
                 java.text.DateFormat,
                 java.util.Iterator,
                 com.owd.core.managers.UPSEbillManager,
                 com.owd.hibernate.generated.UpsEbill,
                 com.owd.web.internal.intravexbills.UploadIntravexBillData" %>
<!--
(Please keep all copyright notices.)
This frameset document includes the Treeview script.
Script found at: http://www.treeview.net
Author: Marcelino Alves Martins

See demoFramesetLeftFrame.jsp for instructions specific to the left frame
-->


<html>

<head>


<link REL="stylesheet" HREF="/internal/owd.css" TYPE="text/css">
<style>
    A {
        text-decoration: none;
        color: black
    }
</style>

<script src="tree.js"></script>
<script>


    //DEMO
    // You can find instructions for this file here:
    // http://www.treeview.net

    USETEXTLINKS = 1
    STARTALLOPEN = 1
    HIGHLIGHT = 1
    PRESERVESTATE = 1
    GLOBALTARGET = "R"



    foldersTree = gFld("UPS Invoices", "demoLargeRightFrame.jsp")
    <%


DateFormat dateReader = new SimpleDateFormat("MMddyyyy");
DateFormat dateWriter = new SimpleDateFormat("MMMM d, yyyy");
try
{
 Map invoiceMap = new TreeMap();

  ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(),"select ups.invoiceNumber, count(*),"
+"min(ups.billDate),sum(convert(money,ups.netCharges)) ,sum(convert(money,ups.incentive)),"
+"max(ups.importUser),max(ups.importFileName),max(ups.importDate) from ups_ebill ups  where ups.invoiceNumber = \'"+request.getSession(true).getAttribute("treeinvoice")+"\' group by ups.invoiceNumber order by min(ups.billDate) desc");

while(rs.next())
{
  invoiceMap.put(rs.getString(1),rs.getString(3));

}
HibernateSession.closeSession();

Iterator invoices = invoiceMap.keySet().iterator();

if(invoices.hasNext())
{


String inum = (String) invoices.next();

rs = HibernateSession.getResultSet(HibernateSession.currentSession(),"select company_name, ups.transactioncode, count(*), client_id"
+" from ups_ebill ups  left outer join  owd_client on client_id=client_fkey where invoicenumber=\'"+inum+"\' group by company_name,ups.transactioncode,client_id order by company_name asc,ups.transactioncode asc");


System.out.println("got rs"+rs);
    %>

    inv<%= inum %> = gFld("<%= dateWriter.format(dateReader.parse((String) invoiceMap.get(inum))) %>", "javascript:parent.op()")

    foldersTree.addChild(inv<%= inum %>)
    <%
int currIndex=0;
int currCodeIndex=0;
String oldCompany = "xxxnotacompanyxxx";
String clientKey = "";

while(rs.next())
{
 // System.out.println("in rs");
 // System.out.println("rs 1="+rs.getString(1)+" rs 2="+rs.getString(2));
  String company = rs.getString(1);
  if (company == null)   company = "Unknown";
  company = company.replace('\'','_');
  if(!(oldCompany.equals(company)))
  {
 // System.out.println("in new company");
  %><%= currIndex==0?"":"])\n" %>
    client<%= currIndex %> = gFld("<%= company %>", "javascript:parent.op()")
    inv<%= inum %>.addChild(client<%= currIndex %>)
    client<%= currIndex %>.addChildren([<%
                  oldCompany = company;
                  currCodeIndex=0;
                  currIndex++;
                  clientKey = rs.getString(4);
                  if (clientKey == null) clientKey = "0";
                  if(clientKey.length()<1) clientKey = "0";

              }

                %><%= currCodeIndex==0?"":"," %>["<%= UploadIntravexBillData.UPSCodeMap.get(rs.getString(2))==null?"Unknown ("+rs.getString(2).trim()+")":UploadIntravexBillData.UPSCodeMap.get(rs.getString(2)) %>&nbsp;(<%= rs.getString(3) %>)","billsforclienttype.jsp?invoice=<%= inum %>&tcode=<%= rs.getString(2) %>&client_key=<%= clientKey %>"]<%
                currCodeIndex++;
            }
            %><%= currIndex==0?"":"])\n" %><%
        }


    }  catch (Exception e) {
            throw e;
        } finally {
            HibernateSession.closeSession();
        }
	%>


            foldersTree.treeID = "L1"

</script>

</head>

<body topmargin=16 marginheight=16>

<!-- By making any changes to this code you are violating your user agreement.
     Corporate users or any others that want to remove the link should check
	 the online FAQ for instructions on how to obtain a version without the link -->
<!-- Removing this link will make the script stop from working -->
<!-- Removing this link will make the script stop from working -->
<div style="position:absolute; top:0; left:0; ">
    <table border=0><tr><td><font size=-2><a style="font-size:7pt;text-decoration:none;color:silver"
                                             href="http://www.treemenu.net/" target=_blank>JavaScript Tree Menu</a>
    </font></td></tr></table>
</div>
<!-- Build the browser's objects and display default view of the
     tree. -->
<script>initializeDocument()</script>


</html>
