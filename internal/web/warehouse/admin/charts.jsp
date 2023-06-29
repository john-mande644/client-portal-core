<%@ page import="com.owd.web.internal.warehouse.admin.WarehouseStatus,
                 java.text.DecimalFormat,
                 java.util.*,
                 java.text.DateFormat,
                 com.owd.core.managers.ConnectionManager,
                 java.text.SimpleDateFormat,
                 java.sql.ResultSet,
                 com.owd.hibernate.HibernateSession,
                 org.hibernate.Session" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!doctype html public "-//w3c/dtd HTML 4.0//en">
<%

    try {
        WarehouseStatus status = null;
        Session sess = HibernateSession.currentSession();
        String currentLocation = WarehouseStatus.getCurrentLocation(request);

        synchronized (ConnectionManager.getConnectionManager()) {
            if (WarehouseStatus.getCreationTime(currentLocation) < (Calendar.getInstance().getTime().getTime() - (.5 * 1000 * 60))) //5 minute interval
            {

                if (WarehouseStatus.getOldStatus(currentLocation) != null) {
                    WarehouseStatus.getOldStatus(currentLocation).setOldChart(null);
                    WarehouseStatus.getOldStatus(currentLocation).setOldChart2(null);
                }
                WarehouseStatus.setOldStatus(currentLocation,null);
                status = new WarehouseStatus(currentLocation);

            } else {
                status = WarehouseStatus.getOldStatus(currentLocation);

            }
        }

        List clientStatus = status.getClientStatusList();
        List methodStatus = status.getMethodStatusList();
        Map ordersWaitingToPrint = status.getOrdersWaitingToPrint();
        int ordersPosted = status.getPostedOrders();
        int ordersShipped = status.getShippedOrders();
        Date currentDate = status.getReferenceDate();
        Iterator clientIT = clientStatus.iterator();
        Iterator methodIT = methodStatus.iterator();
        String bgcolor = "#ffffff";
        DecimalFormat twoPlaceNumFormatter = new DecimalFormat("00");


        String updateTime = DateFormat.getDateTimeInstance().format(new Date(WarehouseStatus.getCreationTime(currentLocation)));

%>
<html>

<head>
    <link REL="stylesheet" HREF="/internal/owd.css" TYPE="text/css">
    <title>OWD Warehouse Current Status</title>
</head>

<body>
<CENTER><H2>OWD Warehouse <%= currentLocation %> Current Status</H2><BR><B>Last Updated: <%= updateTime %></B></CENTER>
<HR>
<CENTER><IMG SRC="orderHistoryChart.jsp"></CENTER>     <HR>
<CENTER><IMG SRC="orderPickHistoryChart.jsp"></CENTER>   <HR>
<CENTER><IMG SRC="orderShipHistoryChart.jsp"></CENTER>
<HR>

</body></html>
<% } catch (Exception ex) {
    ex.printStackTrace();
} finally {
    HibernateSession.closeSession();
}%>