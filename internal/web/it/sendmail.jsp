<%@ taglib prefix="s" uri="/struts-tags" %>
<HTML>
<HEAD>
    <s:head theme="xhtml" />
    <link rel="Stylesheet" href="<s:url value="/css/callcenter.css" />" type="text/css"/>
    <TITLE>${title}</TITLE>

<CENTER><H2>${title}</H2>
</CENTER>
<HR>
    <s:actionerror />
<s:form name="sendmail" action="sendmail!send"  >
    <s:hidden name="toAddress" value="%{toAddress}" />
    <s:hidden name="subject" value="%{subject}" />
    <tr><td></td><td>${instructions}<P></p><s:textarea theme="simple" name="body"  rows="10" cols="50" value="%{body}" /></td></tr>
    <s:submit name="submit" value="Send Notification" />

    
</s:form>
<HR>

<P></P>
</BODY></HTML>
