<%@ page import="java.util.List,
                 com.owd.hibernate.*,
                 java.util.ArrayList,
                 java.util.Iterator"%>
<%@ page import="com.owd.hibernate.generated.ReceiveStatusItems" %>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="<html:rewrite page="/rfapps/receiving/asn/asn.css" />" rel="stylesheet" type="text/css">
<table class="selectsku">
<%
    List items = (List) request.getAttribute("items");
    Iterator it = items.listIterator();
    while(it.hasNext()){
    ReceiveStatusItems rcvitem  = (ReceiveStatusItems) it.next();
      %>

    <tr>
  <td class="selectsku1"><%=rcvitem.getInventoryNum()%> / <%=rcvitem.getInventoryFkey()%>
 <a href="/wms/do/receiveSku?sku=<%=rcvitem.getInventoryFkey()%>">Rcv</a></td>

    <%
    }

%>
</table>
