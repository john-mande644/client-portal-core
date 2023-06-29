<%@ page import="com.owd.core.Mailer" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
   <title>500: Page Not Found</title>
   <meta http-equiv="Content-type" content="text/html; charset=ISO-8859-1" />
</head>
<body>

<h1>500: Page Not Found</h1>
<!--<a href="#" onClick="history.go(-1)">Back</a>-->
<input type=button value="Back" onClick="javascript(history.go(-1))">
<p></p>
<a href="about:home">Home</a>  <br>
<%= request.getRequestURL().toString()%>
<%
    boolean handled = false; // Set to true after handling the error

    // Get the PageContext
    if(pageContext != null) {

        // Get the ErrorData
        ErrorData ed = null;
        try {
            ed = pageContext.getErrorData();
        } catch(NullPointerException ne) {
            // If the error page was accessed directly, a NullPointerException
            // is thrown at (PageContext.java:514).
            // Catch and ignore it... it effectively means we can't use the ErrorData
        }

        // Display error details for the user
        if(ed != null) {

            // Output this part in HTML just for fun
            %>
                <p />Error Data is available.
            <%

            // Output details about the HTTP error
            // (this should show error code 404, and the name of the missing page)

            out.println("<br />ErrorCode: " + ed.getStatusCode());
            out.println("<br />URL: " + ed.getRequestURI());
            out.println("<br />class:  " +ed.getClass());
              out.println("<br />URL: " +ed.getThrowable().getMessage())  ;
                      ed.getThrowable().printStackTrace();
            // Error handled successfully, set a flag
                        StringBuffer sb = new StringBuffer();
                        sb.append(request.getAttribute("loginName"));

                         sb.append("\r\nCode:  \r\n");
                        sb.append(ed.getStatusCode());
                        sb.append("\r\n URI:   \r\n");
                        sb.append(ed.getRequestURI());
                        sb.append("\r\nservlet:  \r\n");
                        sb.append(ed.getServletName());
                        sb.append("\r\n Stack:??  \r\n");

                        for (StackTraceElement el:exception.getStackTrace()){
                          
                            sb.append(el.toString());
                        }

                        
            handled = true;
                        Mailer.postMailMessage("Error page", sb.toString(), "dnickels@owd.com","dnickels@owd.com");
        }
    }

    // Check if the error was handled
    if(!handled) {
    %>
        <p />No information about this error was available.
    <%
    }
%>
</body>
</html>








