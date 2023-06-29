<HTML>
<HEAD>

</HEAD>

<BODY>
<%
if(request.getSession(true).getValue("CallStatus") == null)
{

	
	%>
	<P>
<B>Please choose a client (on the left) to start a call for...</B>
	<%
}else{

%>
	<P>
<B>Please choose a call type (on the left)...</B>
<%

}
%>
</BODY>
</HTML>