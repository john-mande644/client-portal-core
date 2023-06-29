<%@ page import="com.owd.wsjstore.WsjEmailManager" %>
<html>
<%
    String providedEmailAddress=request.getParameter("emailAddress");
    if(providedEmailAddress == null)
    {
    providedEmailAddress = "";
}
providedEmailAddress = providedEmailAddress.trim();
    if(providedEmailAddress==null || "".equals(providedEmailAddress))
    {
        //no email address provided. Request email address to validate.
        providedEmailAddress = "";
        %><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Set Your E-Mail Privacy Preferences</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body bgcolor="white"  style="MARGIN-LEFT: 0px; MARGIN-TOP: 0px" marginheight = 0 marginwidth = 0 topmargin=0 leftmargin=0 >
<table cellspacing="0" cellpadding="0" border="0" align="center">
<tr></tr>
<tr>
	<td>
	<div align="center"><IMG align="middle" alt="WSJstore" border =0 src="http://www.wsjstore.com/skin/frontend/default/wsjd/images/email/wsjslogo2.jpg" ></div>
	</td>
</tr>
<tr>
	<td valign="top">

<!--body table-->
	<br>
	<form name="emailPreferencesForm" method="post" action="/wsjstore/optout.jsp">

	<input type="hidden" name="Product_Code" value="WSJSTORE">



	<div align="center"><table width="400" border="0" cellspacing="3" cellpadding="3">

<tr><td><td><tr>
<tr><td><td><tr>
<tr><td><td><tr>
<tr><td><td><tr>

    <td colspan="2"><font face="Arial" size="2">

            If you no longer wish to receive email marketing offers from WSJstore, please review all information below and click the "unsubscribe now" button at the bottom of this page.
	</font>
	</td>
</tr>
<tr><td><td><tr>
<tr><td><td><tr>
<tr><td><td><tr>





<tr>
    <td><font face="Arial" size="2">E-mail Address:</font></td>

    <td><font face="Arial" size="1"><input  name="emailAddress" size="30" maxlength="65"


    value="<%= providedEmailAddress %>"
						style="width: 145px" >

   </font></td>
</tr>
<tr><td><td><tr>
<tr><td><td><tr>
<tr><td><td><tr>

<tr>
      <td colspan="2"><font face="Arial" size="2">

           This message is being sent to you by WSJstore. If you would prefer not to receive further commercial messages from this sender, please click the unsubscribe button below to confirm your request.
     </font>
      </td>

</tr>
<tr><td><td><tr>
<tr><td><td><tr>
<tr><td><td><tr>

<tr>
	<td width="150" valign="top"><font face="Arial" size="2">Click once:&nbsp;<input type="submit" value="Unsubscribe Now" ></font>
	<br><br>
</tr>
</table></div>
<!--end body table-->
</table>
</form>
</body>

</html>
          <%
    }   else
    {
        //provided email address
        //check if present - if yes, update with optout flags. Otherwise, ignore and report success
        String test =request.getParameter("Product_Code");
        if(test == null) { test = "";}
        if((!("".equals(providedEmailAddress))) && "WSJSTORE".equals(test))
        {
        //add to remove list if not present
        WsjEmailManager.removeAddress(providedEmailAddress);
        %>
   <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Set Your E-Mail Privacy Preferences</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body bgcolor="white"  style="MARGIN-LEFT: 0px; MARGIN-TOP: 0px" marginheight = 0 marginwidth = 0 topmargin=0 leftmargin=0 >
<table cellspacing="0" cellpadding="0" border="0" align="center">
<tr></tr>
<tr>
	<td>
	<div align="center"><IMG align="middle" alt="WSJstore" border =0 src="http://www.wsjstore.com/skin/frontend/default/wsjd/images/email/wsjslogo2.jpg" ></div>
	</td>
</tr>

<tr>
<td valign="top">
<br><br>
<p><b>Thank you</b> </p><p>We will process your request within 24 hours.
</td>
</tr>
</table>
<p>&nbsp; </p>

</body>
</html>


<%

}
    }
%>


