<%@ taglib prefix="s" uri="/struts-tags" %>
<HTML>
<HEAD>
    <s:head theme="xhtml" />
    <link rel="Stylesheet" href="<s:url value="/css/callcenter.css" />" type="text/css"/>
    <TITLE>${title}</TITLE>
<font color=red>
    <B><%= (request.getAttribute("errormessage") != null ? "<div class=\"AlertBad\">" + request.getAttribute("errormessage") + "</div>" : "") %></B>
</font>
<CENTER><H2>Notification Sent!</H2>
</CENTER>
<HR>
<center>
<FORM><INPUT TYPE="button" VALUE="CLICK HERE TO CLOSE WINDOW" onClick="window.close()"></FORM></center>
<P></P>
</BODY></HTML>
