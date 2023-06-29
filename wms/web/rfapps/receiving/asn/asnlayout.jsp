<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<HTML>
  <HEAD>
  <META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<jsp:include page="/rfapps/buttonmeta.jsp" />
    <title><tiles:getAsString name="title"/></title>


<link href="<html:rewrite page="/rfapps/receiving/asn/asn.css" />" rel="stylesheet" type="text/css">
  </HEAD>
<body>

<table cellspacing="5">
<tr>
  <td><tiles:insert attribute="header" /></td>
</tr>
<tr>
  <td>
  <DIV class="asn">
    <tiles:insert attribute="body" />
  </div>
  </td>
</tr>
</table>

</body>
</html>