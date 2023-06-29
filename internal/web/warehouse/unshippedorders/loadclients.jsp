<%@ page import="org.hibernate.Session,
                 com.owd.hibernate.HibernateSession,
                 java.sql.ResultSet,
                 com.owd.hibernate.HibernateSession,
                 java.net.URLEncoder" %>
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
                sql = "select client_id,company_name,count(*) as 'orders'\n" +
                "from owd_order o join owd_client on client_id=client_fkey\n" +
                "where o.ship_packs<1 and o.is_void=0 and post_date >='2000-1-1'\n" +
                "group by client_id,company_name\n" +
                "order by company_name asc, count(*) desc";

        ResultSet rs = HibernateSession.getResultSet(sql);
        while (rs.next()) {
            xml.append("<tree text=\"" + rs.getString("orders") + ":" + rs.getString("company_name") + "\" action=\"clientdetail.jsp?name=" + URLEncoder.encode(rs.getString("company_name")) + "&amp;id=" + rs.getString("client_id") + "\" target=\"locframe\" />");
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