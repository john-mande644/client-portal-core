
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<% String step = (String) request.getAttribute("step");%>
<% if (step.equals("start")){%>
<tiles:insert page="/rfapps/receiving/asn/asnlayout.jsp" flush="true">
  <tiles:put name="title"  value="Asn Main" />
  <tiles:put name="header" value="/rfapps/receiving/asn/header.jsp" />
  <tiles:put name="body"   value="/rfapps/receiving/asn/mainBody.jsp" />
</tiles:insert>
<%}%>

<% if (step.equals("selectSku")){%>
<tiles:insert page="/rfapps/receiving/asn/asnlayout.jsp" flush="true">
  <tiles:put name="title"  value="Asn Select Sku" />
  <tiles:put name="header" value="/rfapps/receiving/asn/header.jsp" />
  <tiles:put name="body"   value="/rfapps/receiving/asn/selectSku.jsp" />
</tiles:insert>
<%}%>

