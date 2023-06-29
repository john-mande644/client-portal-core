<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN">
<HTML>
<HEAD>

<jsp:include page="/rfapps/includes/androidStuff.jsp"/>

<META HTTP-Equiv="Signal" content="Show">
<META HTTP-Equiv="Signal" content="y=265">
<META HTTP-Equiv="Signal" content="x=210">
<META HTTP-Equiv="Signal" content="Bottom">
<META HTTP-Equiv="Battery" content="Show">
<META HTTP-Equiv="Battery" content="y=265">
<META HTTP-Equiv="Battery" content="x=225">
<META HTTP-Equiv="Battery" content="Bottom">
 <META HTTP-Equiv="QuitButton" content="show">
 <META HTTP-Equiv="Scanner" content="Disabled">
 <meta http-equiv="volume" content="0x4444">
    <meta HTTP-Equiv="HomeKey" content="Enabled">
    <meta HTTP-Equiv="KeyState" content="Show">
  <meta name="HandheldFriendly" content="true" />
<meta name="viewport" content="width=device-width, height=device-height" />
    

</HEAD>
<BODY bgcolor="#FFFFFF">
<h3>Please log in.....</h3>

<%
    if(request.getAttribute("error")!=null)
    {
      %><font color=red size=+1><B><%= request.getAttribute("error")%></B></font>
<script>
var gen = new ActiveXObject("SymbolBrowser.Generic");
gen.PlayWave("\\err.wav",0);
</script>   <%
    }
%>
<%--<html:form action="login" focus="username">
<bean:message key="label.login"/>
<html:text property="username" value=""/> <br>
<html:submit value="Login"/>

</html:form>--%>
<form name="loginForm" method="post" action="/wms/do/login">
<span class="label">Timeclock ID Number:</span>
<input type="number" name="username" value="" id="autoSubmit"> <br>
<input type="submit" value="Login" class="submit">

</form>

<!--<form method="POST" NAME="loginform" action="/wms/" >
Timeclock ID Number:&nbsp;<BR><input type="text" name="tc_id">
<BR>
<BR><INPUT TYPE=SUBMIT NAME=submit VALUE=Login>
</form>-->
<HR ALIGN="center" SIZE="5" NOSHADE>
<!--<a href="about:home">quit</a>-->
<!--<html:link action="quit"><bean:message key="link.Quit"/></html:link>-->
 <script>
 try{
  //   alert(document.body.offsetWidth);
 //   document.getElementById("autoSubmit").type = 'number'

       javautil.delayedFocus();
    }catch(err){
       document.forms[0].elements[0].focus();
    }
    function focusButton(){
         document.getElementById("autoSubmit").focus();


        }
</script>
</BODY>

</HTML>
