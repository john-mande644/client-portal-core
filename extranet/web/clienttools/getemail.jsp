<%@ page import="com.owd.core.business.user.UserFactory,
                 com.owd.hibernate.HibUtils,
                 com.owd.hibernate.HibernateSession"%>
<%@ page import="com.owd.hibernate.generated.OwdUser" %><%


            String action = (String) request.getParameter("act");
                     String skipper = (String) request.getAttribute("skipper");
                     try
                     {
                     if(action != null && skipper==null)
                     {
                         OwdUser ou = UserFactory.getOwdUserFromLogin(request.getUserPrincipal().getName());

                         if("no".equals(action))
                         {
                             ou.setEmail("noop@owd.com");

                             HibernateSession.currentSession().saveOrUpdate(ou);
                             HibernateSession.currentSession().flush();
                             HibUtils.commit(HibernateSession.currentSession());
                             request.setAttribute("skipper","true");
                             request.getRequestDispatcher("index.jsp").forward(request, response);
                         }else if ("now".equals(action))
                         {
                            ou.setEmail((String)request.getParameter("user_email"));
                            ou.setName(request.getParameter("user_name"));
                             HibernateSession.currentSession().saveOrUpdate(ou);
                             HibernateSession.currentSession().flush();
                             HibUtils.commit(HibernateSession.currentSession());
                             request.setAttribute("skipper","true");
                             request.getRequestDispatcher("index.jsp").forward(request, response);
                         }else
                         {
                            ou.setEmail("noop@owd.com");
                             HibernateSession.currentSession().saveOrUpdate(ou);
                             HibernateSession.currentSession().flush();
                             HibUtils.commit(HibernateSession.currentSession());
                             request.setAttribute("skipper","true");
                             request.getRequestDispatcher("index.jsp").forward(request, response);
                         }


                     }

                     }catch(Exception ex)
                     {
                         ex.printStackTrace();
                     }   finally
                     {
                     HibernateSession.closeSession();
                     }

                 %>
<%@ page contentType="text/html; charset=iso-8859-1" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 TRANSITIONAL//EN"
       "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
    <title>[OWD Access Manager] User Data Check</title>
    <link rel="Stylesheet" href="/webapps/styles/screen_v2-0-2.css" type="text/css"  />

</head>
<% OwdUser user = UserFactory.getOwdUserFromLogin(request.getUserPrincipal().getName()); %>
<body class="login" onLoad="document.forms[0].elements[0].focus();">

	<div class="Container">



<div id="Dialog">

  <h1>Information Request</h1>
  <h3><P>We at One World are working to improve our online services, but we need your help.<P>In order to provide features like report delivery, automatic notifications and user preferences,
we need to collect an email address and a displayable name for each user login.<P>Your current login is "<%= user.getLogin() %>" - please type in your name and email address and click the "Sign in" button below.<P>If you do not want to provide this information, just click the "Not now" link below and you won't be bothered by this message again.<P>Thank you for your assistance!</h3>


  <div class="AlertGood">Please provide your name and email address</div>

    <form action="./getemail.jsp" method="POST" ><input type=hidden name="act" value="now">
		<dl>
		<dt>Your&nbsp;Name:</dt>
         <dd><!--<div class="AlertBad">Name cannot be left blank - please try again</div>--><input type="text" name="user_name" value="<%= user.getName()%>" ><BR>company, role or person's name</dd>
		<dt>Your&nbsp;Email:</dt>
		<dd><% if(request.getAttribute("error")!=null && skipper!=null ) {%><div class="AlertBad">Email address not valid - check and try again</div><% } %><input type="text" name="user_email" value="<%= user.getEmail()%>" ><BR>like "user@host.com" - enter only one address </dd>
        <dt></dt>
		<dd><input type="image" src="../images/b-sign_in.gif" alt="Save Data" /><P><A HREF="./getemail.jsp?act=no" >[Not&nbsp;now]</A></dd>
        </dl>
    </form>
</div>




  </div>

</body>
</html>
