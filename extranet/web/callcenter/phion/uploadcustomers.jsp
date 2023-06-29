<%@ page import="com.owd.core.DelimitedReader"%>


<%
	DelimitedReader rdr = (DelimitedReader) request.getAttribute("importReader");

	%><HTML>
<HEAD>
<link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">
<TITLE>PhIon Customer Upload Page</TITLE>
</HEAD>

<BODY>
<P><B>PhION Customer Bulk Loader Instructions</B>
<P><font color=red><B><%= (request.getAttribute("errormessage")!=null?request.getAttribute("errormessage"):"") %></B></font>
<P>The input file must be in comma-delimited (also known as Comma-Separated Value) format. The simplest way to create such files
is to edit the information in Excel and select Save As..., then choose the comma-delimited format (*.csv) to save. The
file created for uploading should end in the extension ".csv".<p>
<P>

<FORM METHOD=POST ENCTYPE="MULTIPART/FORM-DATA" ACTION="/webapps/callcenter/phion/phion.jsp?action=uploadcustomers&containsfile=1">
<INPUT TYPE=HIDDEN NAME="containsfile" VALUE="1" >
<INPUT TYPE=HIDDEN NAME="action" VALUE="uploadcustomers" >
Click the Browse button to select the file from your local drive:<HR>
<INPUT TYPE="FILE" size="60" NAME="UploadFile"><BR><P><HR>
<INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Load New/Updated Customer File"></FORM>
<HR>
<% if (rdr != null)
{
	%>Import Results<HR>Imported <%= rdr.getRowCount() %> customers!<HR><%

}
	%>
	</BODY></HTML>
