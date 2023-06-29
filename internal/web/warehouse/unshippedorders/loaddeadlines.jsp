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
                sql = "select [SLA Ship By] as 'deadline',datediff(minute,getdate(),[SLA Ship By]) as 'minutes', count(*) as 'orders'\n" +
                "from vw_order_ship_sla v join owd_order o \n" +
                "on order_id=v.order_fkey and v.shipped_on is null and o.is_void=0\n" +
                "group by [SLA Ship By]\n" +
                "order by [SLA Ship By] asc";

        ResultSet rs = HibernateSession.getResultSet(sql);
        while (rs.next()) {
            xml.append("<tree " + (rs.getInt("minutes") <= 0 ? "icon=\"images/red-bullet.gif\"" : "") + " text=\"" + rs.getString("deadline") + "\" action=\"deadlinedetail.jsp?name=" + URLEncoder.encode(rs.getString("deadline")) + "&amp;id=" + URLEncoder.encode(rs.getString("deadline")) + "\" target=\"locframe\" />");
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