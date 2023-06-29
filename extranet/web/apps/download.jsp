<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
<head>
    <style type="text/css">
<!--
 body{
     background-color: #f0f0f0;
     text-align: center;
      padding: 100px 75px 0 75px;
 }
 td {
   vertical-align:middle;
     height:150px;
}
span.errors {
    color:red;
    font-weight:bold;
    text-align:center;
}
#down{
    margin: 0 auto;
    background-image:url('/webapps/images/apps/downback.png');
     height: 150px;
    width: 60%;
    background-color: white;
    vertical-align:middle;
    border: 1px solid #ccc
}
a{
    color:red;
    text-decoration:none;
}
a:hover {
    text-decoration:underline;
    font-weight:bold;
}
-->
</style>
</head>

<body>
<div id="down">
<center><span class=errors>${error}</span>
${outcome}
<c:if test="${error==null}">
    <table><tr><td>
<html:link action="apps/downloadCall.do?id=${downloadCallForm.id}&doDown=1" module="apps"><img src="/webapps/images/apps/call.png" alt="" border="0"/></html:link>
    </td></tr></table>
    </c:if>
</center>
    </div>

</body></html>