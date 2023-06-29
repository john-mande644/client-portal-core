<%@ taglib uri="struts/bean-el" prefix="bean" %>
<%@ taglib uri="struts/html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
<body>
<br><br>
<center><b>${param.campaign}</b></center>

<br><br>
<iframe name="gToday:mini:agenda.js" id="gToday:mini:agenda.js" src="mini/iflateng.htm" scrolling="no" frameborder="0">
</iframe>
<br><br>
<b>SEND:</b><br>
<center>
<html:link action="problemForm?client=${param.campaign}&callId=${param.callId}" target="_blank" title="send problem form">Problem Form</html:link>
<p>
 </center>

    <c:if test="${param.do_script == 1}">
<b>Scripts:</b><br>
 <center>
    <html:link href="http://internal.owd.com/think/display/CC/${param.campaign}" target="_blank">Up Sell</html:link>
    </center>
    </c:if>

  
</body>
</html>
