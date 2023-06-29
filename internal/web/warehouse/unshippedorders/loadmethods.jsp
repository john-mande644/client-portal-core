<%@ page import="org.hibernate.Session,
                 com.owd.hibernate.HibernateSession,
                 java.sql.ResultSet,
                 com.owd.hibernate.HibernateSession" %>
<%
    String id = request.getParameter("id");
    StringBuffer xml = new StringBuffer();
    System.out.println("in load.jsp, id=" + id);
    try {
        xml.append("<?xml version=\"1.0\"?>\n" +
                "\n" +
                "<tree>");

        Session sess = HibernateSession.currentSession();
        String
                sql = "select carr_service,count(*) as 'orders'\n" +
                "from owd_order o join owd_order_ship_info s on s.order_fkey=order_id \n" +
                "where o.ship_packs<1 and o.is_void=0 and post_date >='2000-1-1'\n" +
                "group by carr_service\n" +
                "order by carr_service asc, count(*) desc";

        ResultSet rs = HibernateSession.getResultSet(sql);
        while (rs.next()) {
            xml.append("<tree text=\"" + rs.getString("orders") + ":" + rs.getString("carr_service") + "\" />");//src=\"loadclient.jsp?id="+rs.getString("client_id")+"\" action=\"detail.jsp?type=client&id="+ rs.getString("client_id")+"\" target=\"locframe\"/>");
        }

        xml.append("</tree>");


    } catch (Exception ex) {
        ex.printStackTrace();
    } finally {
        HibernateSession.closeSession();
    }
    response.setContentType("text/xml");
    response.getWriter().write(xml.toString());
%>