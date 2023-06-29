<%@ taglib uri="struts/bean-el" prefix="bean" %>
<%@ taglib uri="struts/html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>

<link href="<html:rewrite page="/css/intranet.css" module=""/>" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<html:rewrite page="/js/Ajax.js" module=""/>"></script>


<body class="wrapup" onload="toggleVisibility('throb','hidden','hidden','hidden')">
<div id="header"><p class="header">Call Wrap Up</p></div>

<div id="ccOutcontent">
   Wrapup Recorded.  Please close this window and click next call to continue to take calls.
    

   <br><p>
</div>
<div id="heads"><img src="<html:rewrite page="/images/heads.gif" module=""/>" alt=""/> </div>
 <div id="throb"> <img src="<html:rewrite page="/images/indicator_white.gif" module=""/>" alt="" /> </div>
</body>
</html>