<%@ page import="com.owd.core.business.Client" %>
<%@ page import="com.owd.core.business.order.OrderStatus" %><%


    String user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
	if(user_client_id == null) user_client_id = "-1";
	if(user_client_id.equals("")) user_client_id = "-1";

    OrderStatus os = new OrderStatus(request.getParameter("orderid"));


    
%>