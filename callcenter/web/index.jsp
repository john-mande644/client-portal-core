<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
<body onload="toggleVisibility('throb','hidden','hidden','hidden')">
<link href="<html:rewrite page="/css/intranet.css" module=""/>" rel="stylesheet" type="text/css">


<script type="text/javascript" src="<html:rewrite page="/js/Ajax.js" module=""/>"></script>


<div id="header"><p class="header">Callcenter Application Links</p></div>

<div id="cccontent">
     <ul>
         <li><a HREF=<%= request.getContextPath() + "/caseSearch.do" %> >Search Casetracker (Old Problem Forms)</A></li>
         <li><a HREF=<%= request.getContextPath() + "/callPages.do" %> >Search for Calls</A></li>
         <li><a HREF=<%= request.getContextPath() + "/problemForm.do" %> >New Problem Form</A></li>
         <li><a HREF=<%= request.getContextPath() + "/changeOutcome.do" %> >Edit Outcomes</A></li>
                 </ul>



</div>
 <div id="throb"> <img src="<html:rewrite page="/images/indicator_white.gif" module=""/>" alt="" /> </div>
</body>
</html>