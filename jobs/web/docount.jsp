<%@ page import="com.owd.hibernate.HibernateSession" %>
<%@ page import="com.owd.hibernate.HibUtils" %>
<%@ page import="com.owd.core.managers.InventoryManager" %>
<html>
<h2>Job for 335</h2>
<hr>
<%
try{
    InventoryManager.adjustInventoryItemsFromCountData(335, HibernateSession.currentSession());
    HibernateSession.currentSession().flush();
    HibUtils.commit(HibernateSession.currentSession());
    }catch(Exception ex)
    {
    %>Exception:<%= ex.getMessage() %><%
    }
%>
done

