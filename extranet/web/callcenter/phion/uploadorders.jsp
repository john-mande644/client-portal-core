<%@ page import="java.util.Iterator,
				 java.util.List"%>


<%
	List resultsList = (List) request.getAttribute("resultList");
	%>

<HTML>  <link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">
<TITLE>PhIon Order Upload Page</TITLE>
<BODY>
<P><B>PhION MonsterCommerce Order Bulk Loader Instructions</B>
<P><font color=red><B><%= (request.getAttribute("errormessage")!=null?request.getAttribute("errormessage"):"") %></B></font>
<P>The input file must be in comma-delimited (also known as Comma-Separated Value) format. The simplest way to create such files
is to edit the information in Excel and select Save As..., then choose the comma-delimited format (*.csv) to save. The
file created for uploading should end in the extension ".csv".<p>
<P>

<FORM METHOD=POST ENCTYPE="MULTIPART/FORM-DATA"
ACTION="/webapps/callcenter/phion/phion.jsp?containsfile=1&action=uploadorders">

Click the Browse button to select the file from your local drive:<HR>

<INPUT TYPE="FILE" size="60" NAME="UploadFile"><BR><P><HR>
<INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Load New Orders File"></FORM>
<HR>
<% if (resultsList != null)
{
	Iterator it = resultsList.iterator();
	%>Import Results<HR><TABLE><TR><TH>Monster Order #</TH><TH>Results</TH></TR><%
	while(it.hasNext())
	{
		List result = (List) it.next();
		%><TR><TD><%= result.get(0) %></TD><TD><%= result.get(1) %></TR><%

	}
	%></TABLE><HR><%
}

	%>

	</BODY></HTML>
