<%@ page import="org.hibernate.Session,
                 com.owd.hibernate.HibernateSession,
                 java.sql.ResultSet" %>
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
                sql = "select * from w_location l, w_location_type t where l.ixloctype=t.ixloctype and ixparent=" + id + " order by locname";

        ResultSet rs = HibernateSession.getResultSet(sql);
        while (rs.next()) {
            xml.append("<tree text=\"" + rs.getString("description").toUpperCase() + ":" + rs.getString("locname") + "\" src=\"load.jsp?id=" + rs.getString("ixnode") + "\" action=\"detail.jsp?location=" + rs.getString("ixnode") + "\" target=\"locframe\"/>");
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