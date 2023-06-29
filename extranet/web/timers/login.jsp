<%@ page import="com.owd.core.business.user.UserManager" %>
<%


    try {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String version = request.getParameter("version");
        System.out.println("got username=" + username);
        System.out.println("got password=" + password);
        if (version != null) {
            if (version.startsWith("1.5.0")) {
                if (UserManager.isValidInternalUser(username, password)) {
                    System.out.println("got success!");
                    response.getWriter().write("SUCCESS\r\n");
                } else {

                    throw new Exception("Login not recognized");
                }
            } else {
              throw new Exception("You need to update. Go to http://wiki.owd.com:8080/think/x/1QRi to get the current version of the timer.");
            }
        } else {
            throw new Exception("You need to update. Go to http://wiki.owd.com:8080/think/x/1QRi to get the current version of the timer.");
        }


    } catch (Throwable th) {
        th.printStackTrace();

        response.getWriter().write("ERROR: " + th.getMessage());

    } finally {


    }
    response.getWriter().flush();
%>