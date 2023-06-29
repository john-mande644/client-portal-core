<html>
<head >
	<title>One World Direct Call Center Web</title>
</head>
<% 
if(("ENDCALL").equals(request.getParameter("act")))
{
	request.getSession(true).removeValue("CallStatus");
	
	//ConnectionManager.endCall(request.getSession(true).getValue("callid"),(String)request.getParameter("callnotes"));
	//request.getSession(true).removeValue("callid");

}else if(("STARTCALL").equals(request.getParameter("act")))
{
	request.getSession(true).putValue("CallStatus","ONCALL");
	
	//request.getSession(true).putValue("callid",ConnectionManager.startCall(OWDUtilities.getCurrentUser()));

}
%>
<frameset cols="200,*" frameborder="yes" border="1" name="_top">

	<frame src="nav.jsp" name="nav"
		   scrolling="yes"
		   marginwidth="2"
		   marginheight="2">
	<frame src="main.jsp" name="avmain"
		   scrolling="yes"
		   marginwidth="10"
		   marginheight="10">

</frameset>


<noframes>
</noframes>
</html>