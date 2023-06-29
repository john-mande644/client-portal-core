<html>
<head>
<title>MasterWriter Survey</title>
</head>
<% 
com.masterwriter.MWData data = (com.masterwriter.MWData) 	request.getSession(true).getAttribute("mwdata");

if (data == null)
{
	data = new com.masterwriter.MWData();
	request.getSession(true).setAttribute("mwdata",data);
	}
	if(data.fname == null)
	{
		data = new com.masterwriter.MWData();
	request.getSession(true).setAttribute("mwdata",data);

	}	
%>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<FORM ACTION="q2.jsp" METHOD="POST"><INPUT TYPE=HIDDEN NAME=src value=qmain>
<table width="751" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr align="center">
    <td colspan="3"><img src="spacer.gif" width="751" height="23"></td>
  </tr>
  <tr align="center">
    <td colspan="3" align="left" height="0"><img src="mwart.jpg" width="126" height="334" align="left" vspace="0" hspace="0" border="0">
      <p align="left"><font face="Arial, Helvetica, sans-serif" size="2" color="#333333"><img src="spacer.gif" width="33" height="334" align="left" vspace="0" hspace="0" border="0"></font></p>
      <font color="#333333" size="4" face="Verdana, Arial, Helvetica, sans-serif"><center><b>MasterWriter Survey</b></center><HR></font>
        <font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><center>Thank you for participating in the MasterWriter survey!</center>
 <P><BR><P>Please enter the following information to begin:<p align="left"><font face="Arial, Helvetica, sans-serif" size="2" color="#333333"><img src="spacer.gif" width="33" height="334" align="left" vspace="0" hspace="0" border="0"></font></p>

 <table border=0 cellspacing=10>
<TR><TD align=RIGHT><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>First&nbsp;Name:&nbsp;</B></TD><TD ALIGN=LEFT><INPUT TYPE=TEXT NAME="fname" VALUE="<%= data.fname %>"></TD></TR>
<TR><TD align=RIGHT><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>Last&nbsp;Name:&nbsp;</B></TD><TD ALIGN=LEFT><INPUT TYPE=TEXT NAME="lname" VALUE="<%= data.lname %>"></TD></TR>
<TR><TD align=RIGHT><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>Phone&nbsp;Number:&nbsp;</B></TD><TD ALIGN=LEFT><INPUT TYPE=TEXT NAME="phone" VALUE="<%= data.phone %>"></TD></TR>

<TR><TD align=RIGHT><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>Email&nbsp;Address:&nbsp;</B></TD><TD ALIGN=LEFT><INPUT TYPE=TEXT NAME="email" VALUE="<%= data.email %>"></TD></TR>
<TR><TD align=LEFT COLSPAN=2><font color="red" size="3" face="Verdana, Arial, Helvetica, sans-serif"><B><%= request.getAttribute("errormessage")!=null?(String)request.getAttribute("errormessage"):"" %></B></TD></TR>
<TR><TD align=RIGHT></TD><TD ALIGN=RIGHT><INPUT TYPE=SUBMIT NAME="SUBMIT" VALUE="Begin the Survey"></TD></TR>
</TABLE>
      <p>&nbsp;</p>
    </td>
  </tr>
</table>
</FORM>
</body>

</html>